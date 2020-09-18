/* *********************************************************************** *
 * project: org.matsim.*
 * Controler.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.run.ev;

import com.google.inject.Singleton;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.contrib.ev.EvConfigGroup;
import org.matsim.contrib.ev.EvModule;
import org.matsim.contrib.ev.charging.VehicleChargingHandler;
import org.matsim.contrib.ev.fleet.ElectricFleetReader;
import org.matsim.contrib.ev.fleet.ElectricFleetSpecification;
import org.matsim.contrib.ev.fleet.ElectricFleetSpecificationImpl;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigGroup;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.PlansCalcRouteConfigGroup;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.mobsim.qsim.AbstractQSimModule;
import org.matsim.run.RunBerlinScenario;

class RunEVWithUrbanEVTripsPlanner {

	public static void main(String[] args) {
		if(args.length == 0){
			args = new String[1];
			args[0] = "D:/ev-test/berlin-v5.5-1pct.config-ev-test.xml";
		}
		Config config = RunBerlinScenario.prepareConfig(args, new EvConfigGroup());
//		config.plansCalcRoute().setAccessEgressType(PlansCalcRouteConfigGroup.AccessEgressType.accessEgressModeToLink);
//		config.qsim().setUsePersonIdForMissingVehicleId(false);

		config.controler().setOutputDirectory("./output-berlin-v5.5-1pct/evTest-withMobsimInitialitzedListener");
		config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.overwriteExistingFiles);
		config.controler().setLastIteration(1);

		//TODO
		config.plansCalcRoute().setAccessEgressType(PlansCalcRouteConfigGroup.AccessEgressType.none);

		//register interaction activities for car
		config.planCalcScore().addActivityParams(
				new PlanCalcScoreConfigGroup.ActivityParams(TransportMode.car + UrbanVehicleChargingHandler.PLUGOUT_INTERACTION)
				.setScoringThisActivityAtAll(false));
		config.planCalcScore().addActivityParams(
				new PlanCalcScoreConfigGroup.ActivityParams(TransportMode.car + UrbanVehicleChargingHandler.PLUGIN_INTERACTION)
						.setScoringThisActivityAtAll(false));

		config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.overwriteExistingFiles);

		Scenario scenario = RunBerlinScenario.prepareScenario(config);
		Controler controler = RunBerlinScenario.prepareControler(scenario);

		//TODO: this only works if you first bind EVModule and then override it with our custom stuff. create a custom UrbanEV module instead!
		controler.addOverridingModule(new EvModule());
		controler.addOverridingModule(new AbstractModule() {
			@Override
			public void install() {
				bind(MATSimVehicleWrappingEVSpecificationProvider.class).in(Singleton.class);
				bind(ElectricFleetSpecification.class).toProvider(MATSimVehicleWrappingEVSpecificationProvider.class);
				addControlerListenerBinding().to(MATSimVehicleWrappingEVSpecificationProvider.class);

				addMobsimListenerBinding().to(UrbanEVTripsPlanner.class).in(Singleton.class);
				installQSimModule(new AbstractQSimModule() {
					@Override
					protected void configureQSim() {
						//this is responsible for charging vehicles according to person activity start and end events..
						bind(UrbanVehicleChargingHandler.class).in(Singleton.class);
						addMobsimScopeEventHandlerBinding().to(UrbanVehicleChargingHandler.class);
					}
				});
			}
		});
		controler.configureQSimComponents(components -> components.addNamedComponent(EvModule.EV_COMPONENT));

		controler.run();

	}
}