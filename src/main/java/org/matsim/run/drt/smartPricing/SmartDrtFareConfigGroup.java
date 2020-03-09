/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2017 by the members listed in the COPYING,        *
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

package org.matsim.run.drt.smartPricing;

import org.matsim.core.config.ReflectiveConfigGroup;

/**
 * @author : zmeng
 * @date : 19.Feb
 */
public class SmartDrtFareConfigGroup extends ReflectiveConfigGroup {
    public static final String GROUP_NAME = "smartDrtPricing";
    private static final String DRT_MODE = "drtMode";
    private static final String PENALTY = "penalty";
    private static final String RATIO_THRESHOLD = "ratioThreshold";
    private static final String WRITE_LOG = "writeLog";
    private static final String WRITE_LOG_INTERVAL = "writeLogInterval";

    public SmartDrtFareConfigGroup() {
        super(GROUP_NAME);
    }

    private String drtMode = "drt";
    private double penalty = 10.0;
    private double ratioThreshold = 3.;
    private boolean writeLog = true;
    private int writeLogInterval = 2;

    @StringSetter(WRITE_LOG_INTERVAL)
    public void setWriteLogInterval(int writeLogInterval) { this.writeLogInterval = writeLogInterval; }
    @StringGetter(WRITE_LOG_INTERVAL)
    public int getWriteLogInterval() { return writeLogInterval; }
    @StringGetter(DRT_MODE)
    public String getDrtMode(){ return drtMode; }
    @StringSetter(DRT_MODE)
    public void setDrtMode(String drtMode) { this.drtMode = drtMode; }
    @StringGetter(PENALTY)
    public double getPenalty() { return penalty; }
    @StringSetter(PENALTY)
    public void setPenalty(double penalty) { this.penalty = penalty; }
    @StringGetter(RATIO_THRESHOLD)
    public double getRatioThreshold() { return ratioThreshold; }
    @StringSetter(RATIO_THRESHOLD)
    public void setRatioThreshold(double ratioThreshold) { this.ratioThreshold = ratioThreshold; }
    @StringGetter(WRITE_LOG)
    public boolean isWriteLog() { return writeLog; }
    @StringSetter(WRITE_LOG)
    public void setWriteLog(boolean writeLog) { this.writeLog = writeLog; }
}