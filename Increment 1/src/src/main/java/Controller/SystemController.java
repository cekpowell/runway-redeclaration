package Controller;

import IO.Wrapper;
import Model.*;
import View.Dashboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SystemController {

    //TODO will auto save changes upon changes, so if the app crashes state is preserved
    public static void autoSave() throws IOException {
        Airport[] ap = airports.toArray(new Airport[0]);
        Obstacle[] ob = obstacles.toArray(new Obstacle[0]);
            Wrapper<Airport> wrap = new Wrapper<Airport>(Airport.class);
            wrap.setWrappedObj(ap);
            XMLHandler.saveObjectAsXML(wrap, "airports.xml");

            Wrapper<Obstacle> wrap2 = new Wrapper<Obstacle>(Obstacle.class);
            wrap2.setWrappedObj(ob);
            XMLHandler.saveObjectAsXML(wrap2,"obstacles.xml");

        System.out.println("SAVING, this is just a message for now");
    }


    public static ObservableList<Airport> getAirports() {
        return airports;
    }

    public static void removeAirport(Airport a){
        airports.remove(a);
        try{
            autoSave();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void removeObstacle(Obstacle o){
        obstacles.remove(o);
        try{
            autoSave();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void setAirports(ObservableList<Airport> airports) {
        SystemController.airports = airports;
    }

    public static ObservableList<Obstacle> getObstacles() {
        return obstacles;
    }

    public static <T> boolean addObstacles(Collection<Obstacle> coll){
        return obstacles.addAll(coll);
    }

    public static void setObstacles(ObservableList<Obstacle> obstacles) {
        SystemController.obstacles = obstacles;
    }

    static ObservableList<Airport> airports = FXCollections.observableArrayList();
    static ObservableList<Obstacle> obstacles = FXCollections.observableArrayList();

    public static void addObstacle(Obstacle o){
        //chekcing here.?
        obstacles.add(o);
        try {
            autoSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addAirport(Airport a){
        //checking?
        airports.add(a);
        try {
            autoSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeObstacle(int obstacleid){
        Iterator<Obstacle> it = obstacles.iterator();
        while(it.hasNext()) {
            Obstacle o = it.next();
            if (o.getId() == obstacleid) {
                it.remove();
                return;
            }
        }
        try {
            autoSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Shouldnt reach here, exception thrown here il customize later

    }

    public void removeAirport(String IATA){
        Iterator<Airport> it = airports.iterator();
        while(it.hasNext()) {
            Airport o = it.next();
            if (o.getIATA() == IATA) {
                it.remove();
                return;
            }
        }
        try {
            autoSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Shouldnt reach here, exception thrown here il customize later

    }

    public static String[] getAirportNames(){
        String[] names = new String[airports.size()];
        for(int i = 0; i < names.length;i++){
            names[i] = airports.get(i).getIATA();
        }
        return names;
    }

    public static String[] getObstacleNames(){
        String[] names = new String[obstacles.size()];
        for(int i = 0; i < names.length;i++){
            names[i] = obstacles.get(i).getName() + " (" + String.valueOf(obstacles.get(i).getId()) + " )";
        }
        return names;
    }

    public static Airport getAirport(String IATA){
        if(airports.size() == 0){
            return null;
        }
        for(Airport a: airports){
            if(a.getIATA() == IATA) {
                return a;
            }
        }
        //TODO
        //NO IATA BY NAME THROW EXCEPTION
        return null;
    }

    /**
     * Performs the calculations for a runway revision, and displays the results in the runway view associated with the
     * given dashboard.
     * @param dashboard
     * @param logicalRunway
     * @param obstacle
     * @param placement
     * @param flightMethod
     */
    public static void performRunwayRevision(Dashboard dashboard, LogicalRunway logicalRunway, Obstacle obstacle, ObstaclePosition placement, FlightMethod flightMethod){
        // performing the runway revision
        RevisedLogicalRunway revisedLogicalRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway, obstacle, placement, flightMethod);

        // getting the calculation breakdown
        Node calculationBreakdown = RunwayReviser.getCalculationBreakdown(logicalRunway, obstacle, placement, flightMethod);

        // displaying the runway revision within the runway view
        dashboard.getRunwayView().displayLogicalRunwayRevision(logicalRunway, revisedLogicalRunway);

        // displaying the calculation breakdown within the calculation breakdown view
        dashboard.getCalculationBreakdownView().displayCalculationBreakdown(calculationBreakdown);
    }
}
