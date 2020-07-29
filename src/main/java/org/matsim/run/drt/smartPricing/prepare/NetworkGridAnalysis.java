//todo: write analysis-code for output-data from smart drt module
//package org.matsim.run.drt.smartPricing.prepare;
//
//import org.apache.log4j.Logger;
//import org.locationtech.jts.geom.Coordinate;
//import org.locationtech.jts.geom.Geometry;
//import org.locationtech.jts.geom.Point;
//import org.matsim.analysis.od.ODTrip;
//import org.matsim.api.core.v01.Coord;
//import org.matsim.api.core.v01.Id;
//import org.matsim.api.core.v01.network.Link;
//import org.matsim.api.core.v01.population.Person;
//import org.matsim.core.utils.geometry.geotools.MGC;
//import org.matsim.core.utils.gis.PolygonFeatureFactory;
//import org.matsim.core.utils.gis.PolylineFeatureFactory;
//import org.matsim.core.utils.gis.ShapeFileReader;
//import org.matsim.core.utils.gis.ShapeFileWriter;
//import org.opengis.feature.simple.SimpleFeature;
//import org.opengis.referencing.crs.CoordinateReferenceSystem;
//
//import java.io.*;
//import java.util.*;
//
///**
// * @author : zmeng
// * @date :
// */
//public class
//NetworkGridAnalysis {
//    private static final Logger log = Logger.getLogger(ProfitUtility.class);
//    public static void main(String[] args) throws IOException {
////        printGridShapeFile("/Users/zhuoxiaomeng/Forschung/Berlin-Shp-File/shp-files/hexagon-grid-2500/hexagon-grid-2500.shp",
////                "/Users/zhuoxiaomeng/Forschung/Berlin-Shp-File/shp-files/grid-2500-id/hexagon-grid-2500.shp");
//
//
//        var dic = "/Users/zhuoxiaomeng/Forschung/result/10pct/wait_time_grid/";
//        var odDic = "/Users/zhuoxiaomeng/Forschung/result/10pct/OD/drt_trips/district/";
//        var reward = "reward";
//        var penalty = "penalty";
//        var peRe = "final";
//        var base = "base";
//        var features = ShapeFileReader.getAllFeatures("/Users/zhuoxiaomeng/Forschung/Berlin-Shp-File/shp-files/grid-2500-Id/hexagon-grid-2500.shp");
//        List<String> files = List.of(reward,penalty,peRe,base);
//        for (String file :
//                files) {
//            printODTrips(dic + file+"_drt.csv",features,"ID",dic+file+"_district_od.csv");
//        }
//
////        var features = ShapeFileReader.getAllFeatures("/Users/zhuoxiaomeng/Forschung/Berlin-Shp-File/shp-files/shp-berlin-hundekopf-areas/berlin_hundekopf.shp");
////        String outputDict = "/Users/zhuoxiaomeng/Forschung/result/10pct/OD/trips/hundekopf/";
////        String base = "/Users/zhuoxiaomeng/Forschung/result/10pct/base/it.120/berlin-v5.5-10pct-3.120.trips.csv";
////        String pe = "/Users/zhuoxiaomeng/Forschung/result/10pct/penalty/p1.0/it.100/berlin-v5.5-10pct-4.100.trips.csv";
////        String re = "/Users/zhuoxiaomeng/Forschung/result/10pct/reward/r_0.8/it.30/berlin-v5.5-10pct-3.output_trips.csv";
////        String pe_re = "/Users/zhuoxiaomeng/Forschung/result/10pct/penalty_reward/1.0_0.8/it.20/berlin-v5.5-10pct-3.20.trips.csv";
////        List<String> files = List.of(base,pe,re,pe_re);
////        String[] name = new String[]{"base","pe","re","pe_re"};
////        int a = 0;
////        for (String file :
////                files) {
////            printODTrips(file,features,"SCHLUESSEL",outputDict + "OD_"+name[a]+".csv");
////            a++;
////        }
//    }
//
//    private static void printGridShapeFile(String shapeFile, String fileName){
//        CoordinateReferenceSystem crs;
//        crs = MGC.getCRS("EPSG:31468");
//
//        var features = ShapeFileReader.getAllFeatures(shapeFile);
//        PolygonFeatureFactory factory = (new PolygonFeatureFactory.Builder()).setCrs(crs).setName("grid").addAttribute("ID", String.class).create();
//        Collection<SimpleFeature> features2print = new ArrayList<>();
//        int num = 1;
//        for (SimpleFeature s : features) {
//
//            Geometry geometry = (Geometry) s.getDefaultGeometry();
//            SimpleFeature simpleFeature = factory.createPolygon(geometry.getCoordinates(), new Object[]{num},null);
//            num ++;
//            features2print.add(simpleFeature);
//        }
//        ShapeFileWriter.writeGeometries(features2print,fileName);
//    }
//
//    private static void printODTrips(String tripsFile, Collection<SimpleFeature> features,String ID, String outputFile) throws IOException {
//        Map<String,Geometry> id2geo = new HashMap<>();
//        for (SimpleFeature simpleFeature : features) {
//            Geometry geo = (Geometry)simpleFeature.getDefaultGeometry();
//            String id = (String) simpleFeature.getAttribute(ID);
//            id2geo.put(id,geo);
//        }
//        File trips = new File(tripsFile);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(tripsFile)));
//        String line = bufferedReader.readLine();
//        String[] head = line.split(";");
//        int start_x = printArray(head,"fromX");
//        int start_y = printArray(head,"fromY");
//        int end_x = printArray(head,"toX");
//        int end_y = printArray(head,"toY");
//
//        File file = new File(outputFile);
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
//        bufferedWriter.write(line + ";start;end");
//
//        while ((line = bufferedReader.readLine()) != null){
//            String[] trip = line.split(";");
//            var startCoord = new Coord(Double.valueOf(trip[start_x]),Double.valueOf(trip[start_y]));
//            var endCoord = new Coord(Double.valueOf(trip[end_x]),Double.valueOf(trip[end_y]));
//            String start = getDistrictId(id2geo,startCoord);
//            String end = getDistrictId(id2geo,endCoord);
//            bufferedWriter.newLine();
//            bufferedWriter.write(line + ";"+start+";"+end);
//        }
//        bufferedReader.close();
//        bufferedWriter.close();
//    }
//
//    public static int printArray(String[] array,String value){
//        for(int i = 0;i<array.length;i++){
//            if(array[i].equals(value)){
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    private static List<DrtODTrip> readDrtTripsFile(String fileName) throws IOException {
//        List<DrtODTrip> drtODtrips = new ArrayList<>();
//        File file = new File(fileName);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//        String line = bufferedReader.readLine();
//       while ((line = bufferedReader.readLine()) != null){
//
//           DrtODTrip drtODTrip = new DrtODTrip();
//           String[] drt = line.split(",");
//           Id<Person> personId = Id.createPersonId(drt[1]);
//           Id<Link> fromLinkId = Id.createLinkId(drt[2]);
//           Id<Link> toLinkId = Id.createLinkId(drt[3]);
//           double departureTime = Double.valueOf(drt[4]);
//           double arrivalTime= Double.valueOf(drt[5]);
//           var unsharedDrtTime= Double.valueOf(drt[6]);
//           var unsharedDrtDistance= Double.valueOf(drt[7]);
//           var estimatePTTime = Double.valueOf(drt[8]);
//           var ratio = Double.valueOf(drt[9]);
//           var penaltyPerMeter = Double.valueOf(drt[10]);
//           var penalty= Double.valueOf(drt[11]);
//           var penaltyRatioThreshold= Double.valueOf(drt[12]);
//           var rewardPerMeter = Double.valueOf(drt[13]);
//           var reward= Double.valueOf(drt[14]);
//           var rewardRatioThreshold= Double.valueOf(drt[15]);
//           var fromX = Double.valueOf(drt[16]);
//           var fromY = Double.valueOf(drt[17]);
//           var toX = Double.valueOf(drt[18]);
//           var toY = Double.valueOf(drt[19]);
//           drtODTrip.setRatio(ratio);
//           drtODTrip.setDepartureTime(departureTime);
//           drtODTrip.setMode("drt");
//           drtODTrip.setPersonId(personId);
//           drtODTrip.setOriginCoord(new Coord(fromX,fromY));
//           drtODTrip.setDestinationCoord(new Coord(toX,toY));
//           drtODTrip.setArrivalTime(arrivalTime);
//           drtODTrip.setUnsharedDrtDistance(unsharedDrtDistance);
//           drtODTrip.setUnsharedDrtTime(unsharedDrtTime);
//           drtODTrip.setEstimatePTTime(estimatePTTime);
//           drtODTrip.setPenaltyPerMeter(penaltyPerMeter);
//           drtODTrip.setPenalty(penalty);
//           drtODTrip.setPenaltyRatioThreshold(penaltyRatioThreshold);
//           drtODTrip.setRewardPerMeter(rewardPerMeter);
//           drtODTrip.setReward(reward);
//           drtODTrip.setRewardRatioThreshold(rewardRatioThreshold);
//           drtODtrips.add(drtODTrip);
//
//
//       }
//       return drtODtrips;
//    }
//
//    private static void printODResult(List<DrtODTrip> drtODTrips, String outputFileName, String shapeFileName, String ID) throws IOException {
//        File file = new File(outputFileName);
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
//        bufferedWriter.write("personID,departureTime,fromId,toId,ratio,unsharedDrtDistance,relativeRatio");
//        var features = ShapeFileReader.getAllFeatures(shapeFileName);
//        Map<String,Geometry> id2geo = new HashMap<>();
//        for (SimpleFeature simpleFeature : features) {
//            Geometry geo = (Geometry)simpleFeature.getDefaultGeometry();
//            String id = (String) simpleFeature.getAttribute(ID);
//            id2geo.put(id,geo);
//        }
//        for (DrtODTrip drtODTrip : drtODTrips) {
//            String fromId = getDistrictId(id2geo, drtODTrip.getOriginCoord());
//            String toId = getDistrictId(id2geo, drtODTrip.getDestinationCoord());
//            bufferedWriter.newLine();
//            bufferedWriter.write(drtODTrip.getPersonId() + "," +
//                    drtODTrip.getDepartureTime() + "," +
//                    fromId + "," +
//                    toId + "," +
//                    drtODTrip.getRatio() +"," +
//                    drtODTrip.getUnsharedDrtDistance() + "," +
//                    drtODTrip.getRatio() / drtODTrip.getPenaltyRatioThreshold());
//        }
//        bufferedWriter.close();
//    }
//
//    private static String getDistrictId(Map<String, Geometry> districts, Coord coord) {
//        Point point = MGC.coord2Point(coord);
//        Iterator var4 = districts.keySet().iterator();
//
//        String nameDistrict;
//        Geometry geo;
//        do {
//            if (!var4.hasNext()) {
//                return "other";
//            }
//
//            nameDistrict = (String)var4.next();
//            geo = (Geometry)districts.get(nameDistrict);
//        } while(!geo.contains(point));
//
//        return nameDistrict;
//    }
//
//
//    private static void printODLinesForEachAgent(List<DrtODTrip> filteredTrips, String fileName) throws IOException {
//        CoordinateReferenceSystem crs;
//        crs = MGC.getCRS("EPSG:31468");
//
//        PolylineFeatureFactory factory = (new PolylineFeatureFactory.Builder()).setCrs(crs).setName("trip").addAttribute("personId", String.class).addAttribute("O", String.class).addAttribute("D", String.class).addAttribute("depTime", Double.class).addAttribute("ratio",Double.class).addAttribute("penalty_meter",Double.class).create();
//        Collection<SimpleFeature> features = new ArrayList();
//        Iterator var6 = filteredTrips.iterator();
//
//        while(var6.hasNext()) {
//            DrtODTrip trip = (DrtODTrip)var6.next();
//            SimpleFeature feature = factory.createPolyline(new Coordinate[]{new Coordinate(trip.getOriginCoord().getX(), trip.getOriginCoord().getY()), new Coordinate(trip.getDestinationCoord().getX(), trip.getDestinationCoord().getY())}, new Object[]{trip.getPersonId(), trip.getOrigin(), trip.getDestination(), trip.getDepartureTime(), trip.getRatio(),trip.getRatioThreshold()}, (String)null);
//            features.add(feature);
//        }
//
//        if (!features.isEmpty()) {
//            try {
//                ShapeFileWriter.writeGeometries(features, fileName);
//            } catch (Exception var9) {
//                log.warn("Shape file was not written out: " + fileName);
//            }
//        } else {
//            log.warn("Shape file was not written out (empty).");
//        }
//
//    }
//
//    private static class DrtODTrip extends ODTrip {
//        double ratio;
//        double arrivalTime;
//        double unsharedDrtTime;
//        double unsharedDrtDistance;
//        double travelDistance;
//        double penaltyPerMeter;
//        double penalty;
//        double estimatePTTime;
//        double ratioThreshold;
//        private Double penaltyRatioThreshold;
//        private Double rewardPerMeter;
//        private Double reward;
//        private Double rewardRatioThreshold;
//
//        public Double getPenaltyRatioThreshold() {
//            return penaltyRatioThreshold;
//        }
//
//        public Double getRewardPerMeter() {
//            return rewardPerMeter;
//        }
//
//        public Double getReward() {
//            return reward;
//        }
//
//        public Double getRewardRatioThreshold() {
//            return rewardRatioThreshold;
//        }
//
//        public double getEstimatePTTime() {
//            return estimatePTTime;
//        }
//
//        public void setEstimatePTTime(double estimatePTTime) {
//            this.estimatePTTime = estimatePTTime;
//        }
//
//        public double getPenaltyPerMeter() { return penaltyPerMeter; }
//
//        public void setPenaltyPerMeter(double penaltyPerMeter) {
//            this.penaltyPerMeter = penaltyPerMeter;
//        }
//
//        public double getPenalty() {
//            return penalty;
//        }
//
//        public void setPenalty(double penalty) {
//            this.penalty = penalty;
//        }
//
//        public void setRatio(double ratio) {
//            this.ratio = ratio;
//        }
//
//        public double getRatio() {
//            return ratio;
//        }
//
//        public double getArrivalTime() {
//            return arrivalTime;
//        }
//
//        public void setArrivalTime(double arrivalTime) {
//            this.arrivalTime = arrivalTime;
//        }
//
//        public double getUnsharedDrtTime() {
//            return unsharedDrtTime;
//        }
//
//        public void setUnsharedDrtTime(double unsharedDrtTime) {
//            this.unsharedDrtTime = unsharedDrtTime;
//        }
//
//        public double getUnsharedDrtDistance() {
//            return unsharedDrtDistance;
//        }
//
//        public void setUnsharedDrtDistance(double unsharedDrtDistance) {
//            this.unsharedDrtDistance = unsharedDrtDistance;
//        }
//
//        public double getTravelDistance() {
//            return travelDistance;
//        }
//
//        public void setTravelDistance(double travelDistance) {
//            this.travelDistance = travelDistance;
//        }
//
//        public void setRatioThreshold(double ratioThreshold) {
//            this.ratioThreshold = ratioThreshold;
//        }
//
//        public double getRatioThreshold() {
//            return ratioThreshold;
//        }
//
//        @Override
//        public String toString() {
//            return "DrtODTrip{" +
//                    "ratio=" + ratio +
//                    '}';
//        }
//
//        public void setPenaltyRatioThreshold(Double penaltyRatioThreshold) {
//            this.penaltyRatioThreshold = penaltyRatioThreshold;
//        }
//
//        public void setRewardPerMeter(Double rewardPerMeter) {
//            this.rewardPerMeter = rewardPerMeter;
//        }
//
//        public void setReward(Double reward) {
//            this.reward = reward;
//        }
//
//        public void setRewardRatioThreshold(Double rewardRatioThreshold) {
//            this.rewardRatioThreshold = rewardRatioThreshold;
//        }
//    }
//}
