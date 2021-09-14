package Controller;

import IO.Wrapper;
import Model.*;
import View.Dashboard;
import View.NotificationPanel;
import View.Toolbar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class SystemController {

    // member variables
    static ObservableList<Airport> airports = FXCollections.observableArrayList();
    static ObservableList<Obstacle> obstacles = FXCollections.observableArrayList();
    static Dashboard dashboard;
    static Toolbar toolbar;

    public static String airportDecleration = "Airport %s successfully added";
    public static String airportRemoval = "Airport %s successfully removed";
    public static String airpnameedit = "Airport name successfully edited from %s to %s";
    public static String airpiataedit = "Airport IATA successfully edited from  %s to  %s";
    public static String addrunway = "Runway %s successfully added";
    public static String removerun = "Runway %s successfully removed";
    public static String runwayedit = "Runway %s successfully edited";
    public static String obstacleadd = "Obstacle %s successfully added";
    public static String obstacleremove = "Obstacle %s successfully removed";
    public static String obsnameedit = "Obstacle name successfully edited from %s to %s";
    public static String obswidthedit = "Obstacle %s width successfully edited";
    public static String obslengthedit = "Obstacle  %s length successfully edited";
    public static String obsheightedit = "Obstacle  %s height successfully edited";
    public static String runwayrevision = "Runway revision successfully performed on runway %s of airport %s";

    //Error message
    public static String obstacleiderror = "Obstacle id already exists";

    public static boolean existingNotifications;
    public static Notification[] existingnoti;


    ////////////////////////////
    /**
     * SYSTEM MAINTAINENCE
     */
    ////////////////////////////

    /**
     *
     */
    //TODO will auto save changes upon changes, so if the app crashes state is preserved
    public static void autoSave() {
        try{
            Airport[] ap = airports.toArray(new Airport[0]);
            Obstacle[] ob = obstacles.toArray(new Obstacle[0]);

            ArrayList<Notification> notis = dashboard.getNotificationPanel().getNotifications();
            Notification[] not = (Notification[]) notis.toArray(new Notification[0]);

            Wrapper<Notification> wrap0 = new Wrapper<Notification>(Notification.class);
            wrap0.setWrappedObj(not);
            XMLHandler.saveObjectAsXML(wrap0,"notifications.xml");

            Wrapper<Airport> wrap = new Wrapper<Airport>(Airport.class);
            wrap.setWrappedObj(ap);
            XMLHandler.saveObjectAsXML(wrap, "airports.xml");

            Wrapper<Obstacle> wrap2 = new Wrapper<Obstacle>(Obstacle.class);
            wrap2.setWrappedObj(ob);
            XMLHandler.saveObjectAsXML(wrap2,"obstacles.xml");

            System.out.println("SAVING, this is just a message for now");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void clearNotifications(){
        dashboard.getNotificationPanel().clearPanel();
        autoSave();
    }

    public static void clearObstacles(){
        if(obstacles != null){
            obstacles.clear();
        }
    }

    public static void clearAirports(){
        if(airports != null){
            airports.clear();
        }
    }

    ////////////////////////////
    /**
     * GETTERS AND SETTERS
     */
    ////////////////////////////

    /**
     *
     * @return
     */
    public static ObservableList<Airport> getAirports() {
        return airports;
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

    public static String[] getAirportNames(){
        String[] names = new String[airports.size()];
        for(int i = 0; i < names.length;i++){
            names[i] = airports.get(i).getIATA();
        }
        return names;
    }

    /**
     *
     * @return
     */
    public static ObservableList<Obstacle> getObstacles() {
        return obstacles;
    }

    public static String[] getObstacleNames(){
        String[] names = new String[obstacles.size()];
        for(int i = 0; i < names.length;i++){
            names[i] = obstacles.get(i).getName() + " (" + String.valueOf(obstacles.get(i).getId()) + " )";
        }
        return names;
    }

    /**
     *
     * @return
     */
    public static Dashboard getDashboard() {
        return dashboard;
    }

    /**
     *
     * @param airports
     */
    public static void setAirports(ObservableList<Airport> airports) {
        SystemController.airports = airports;
    }

    /**
     *
     * @param obstacles
     */
    public static void setObstacles(ObservableList<Obstacle> obstacles) {
        SystemController.obstacles = obstacles;
    }

    /**
     *
     * @param dashboard
     */
    public static void setDashboard(Dashboard dashboard) {
        SystemController.dashboard = dashboard;
    }

    public static void setToolbar(Toolbar toolbar){
        SystemController.toolbar = toolbar;
    }

    ////////////////////////////
    /**
     * CONFIGURING AIRPORTS
     */
    ////////////////////////////

    /**
     *
     * @param a
     */
    public static void addAirport(Airport a){
        // removing airport
        airports.add(a);
        SystemController.dashboard.getCalculationPanel().refreshSelections();

        // managing system
        dashboard.getNotificationPanel().displayNotification(new Notification(String.format(airportDecleration, a.getName()), LocalDateTime.now(), Notification.Type.ADD));
        autoSave();
    }


    /**
     *
     * @param a
     */
    public static void removeAirport(Airport a){
        // removing airport
        airports.remove(a);
        String oldName = a.getName();

        SystemController.dashboard.getCalculationPanel().refreshSelections();

        // managing system
        dashboard.getNotificationPanel().displayNotification(new Notification(String.format(airportRemoval, oldName), LocalDateTime.now(), Notification.Type.REMOVE));
        autoSave();
    }

    /**
     *
     * @param IATA
     */
    public void removeAirport(String IATA){
        Iterator<Airport> it = airports.iterator();
        while(it.hasNext()) {
            Airport o = it.next();
            if (o.getIATA() == IATA) {
                it.remove();
                return;
            }
        }
        autoSave();
        //Shouldnt reach here, exception thrown here il customize later
    }

    /**
     *
     * @param airport
     * @param newName
     * @param newIATA
     */
    public static void editAirport(Airport airport, String newName, String newIATA){
        // only editing name if value has changed
        if(!(airport.getName().equals(newName))){
            String oldName = airport.getName();
            // changing the airport name
            airport.setName(newName);

            // adding edit notification
            SystemController.dashboard.getNotificationPanel().displayNotification(new Notification(String.format(airpnameedit, oldName, newName), LocalDateTime.now(), Notification.Type.EDIT));
        }

        // only editing IATA if value has changes
        if(!(airport.getIATA().equals(newIATA))){
            String oldIATA = airport.getIATA();
            // changinig the airport IATA
            airport.setIATA(newIATA);

            // adding notification
            SystemController.dashboard.getNotificationPanel().displayNotification(new Notification(String.format( airpiataedit, oldIATA, newIATA), LocalDateTime.now(), Notification.Type.EDIT));
        }

        // saving changes
        autoSave();
        SystemController.dashboard.getCalculationPanel().refreshSelections();
    }

    /**
     *
     * @param airport
     * @param runway
     */
    public static void addAirportRunway(Airport airport, PhysicalRunway runway){
        // adding the runway to the airport
        airport.addNewRunway(runway); // adding the runway
        SystemController.dashboard.getCalculationPanel().refreshSelections();

        // adding notification
        SystemController.getDashboard().getNotificationPanel().displayNotification(new Notification(String.format(addrunway, runway.getName()) , LocalDateTime.now(), Notification.Type.ADD));
        autoSave(); // saving the changes to persist
    }

    /**
     *
     * @param airport
     * @param runway
     */
    public static void removeAirportRunway(Airport airport, PhysicalRunway runway){
        String oldName = runway.getName();
        // removing the runway to the airport
        airport.removeRunway(runway);
        SystemController.dashboard.getCalculationPanel().refreshSelections();

        // adding notification
        SystemController.getDashboard().getNotificationPanel().displayNotification(new Notification(String.format(removerun, oldName), LocalDateTime.now(), Notification.Type.REMOVE));
        autoSave();
    }

    /**
     *
     * @param airport
     * @param oldRunway
     * @param newRunway
     */
    public static void editAirportRunway(Airport airport, PhysicalRunway oldRunway, PhysicalRunway newRunway){
        // changing the old runway to be the new one
        airport.getRunways().set(airport.getRunways().indexOf(oldRunway), newRunway);
        SystemController.dashboard.getCalculationPanel().refreshSelections();

        // adding notification
        SystemController.getDashboard().getNotificationPanel().displayNotification(new Notification(String.format( runwayedit,oldRunway.getName()), LocalDateTime.now(), Notification.Type.EDIT));
        autoSave();
    }

    ////////////////////////////
    /**
     * CONFIGURING OBSTACLES
     */
    ////////////////////////////

    /**
     *
     * @param o
     */
    public static void addObstacle(Obstacle o){
        //adding obstacle
        obstacles.add(o);

        //TODO enforce system id
        // managing system
        dashboard.getNotificationPanel().displayNotification(new Notification(String.format( obstacleadd, o.getName()), LocalDateTime.now(), Notification.Type.ADD));
        autoSave();
    }

    /**
     *
     * @param coll
     * @param <T>
     * @return
     */
    public static <T> boolean addObstacles(Collection<Obstacle> coll){
        return obstacles.addAll(coll);
    }

    /**
     *
     * @param o
     */
    public static void removeObstacle(Obstacle o){
        String oldName = o.getName();

        obstacles.remove(o);
        dashboard.getNotificationPanel().displayNotification(new Notification(String.format( obstacleremove, oldName), LocalDateTime.now(), Notification.Type.REMOVE));

        autoSave();
    }

    /**
     *
     * @param obstacleid
     */
    public void removeObstacle(int obstacleid){
        Iterator<Obstacle> it = obstacles.iterator();
        while(it.hasNext()) {
            Obstacle o = it.next();
            if (o.getId() == obstacleid) {
                it.remove();
                return;
            }
        }
        autoSave();
        //Shouldnt reach here, exception thrown here il customize later
    }

    /**
     *
     * @param obstacle
     * @param newName
     * @param newWidth
     * @param newLength
     * @param newHeight
     */
    public static void editObstacle(Obstacle obstacle, String newName, double newWidth, double newLength, double newHeight){
        // only changing name if different
        if(!(obstacle.getName().equals(newName))){
            String oldName  = obstacle.getName();

            // updating value
            obstacle.setName(newName);

            // notification for change
            SystemController.getDashboard().getNotificationPanel().displayNotification(new Notification(String.format( obsnameedit, oldName, obstacle.getName()), LocalDateTime.now(), Notification.Type.EDIT));
        }

        // only changing width if different
        if(obstacle.getWidth() != newWidth){
            // updating value
            obstacle.setWidth(newWidth);

            // notification for change
            SystemController.getDashboard().getNotificationPanel().displayNotification(new Notification(String.format( obswidthedit, obstacle.getName()), LocalDateTime.now(), Notification.Type.EDIT));
        }

        // only changing length if different
        if(obstacle.getLength() != newLength){
            // updating value
            obstacle.setLength(newLength);

            // notification for change
            SystemController.getDashboard().getNotificationPanel().displayNotification(new Notification(String.format(obslengthedit, obstacle.getName()), LocalDateTime.now(), Notification.Type.EDIT));
        }

        // only changing height if different
        if(obstacle.getHeight() != newHeight){
            // updating value
            obstacle.setHeight(newHeight);

            // notification for change
            SystemController.getDashboard().getNotificationPanel().displayNotification(new Notification(String.format(obsheightedit, obstacle.getName()), LocalDateTime.now(), Notification.Type.EDIT));
        }

        // saving changes
        autoSave();
        SystemController.dashboard.getCalculationPanel().refreshSelections();
    }

    ////////////////////////////
    /**
     * RUNWAY REVISION METHODS
     */
    ////////////////////////////

    /**
     * Performs the calculations for a runway revision, and displays the results in the runway view associated with the
     * given dashboard.
     * @param dashboard
     * @param logicalRunway
     * @param obstacle
     * @param placement
     * @param flightMethod
     */
    public static void performRunwayRevision(Dashboard dashboard, Airport airport, LogicalRunway logicalRunway, Obstacle obstacle, ObstaclePosition placement, FlightMethod flightMethod){
        // performing the runway revision
        RevisedLogicalRunway revisedLogicalRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway, obstacle, placement, flightMethod);

        // getting the calculation breakdown
        Node calculationBreakdown = RunwayReviser.getCalculationBreakdown(airport, logicalRunway, obstacle, placement, flightMethod);

        // displaying the runway revision within the runway view
        dashboard.getRunwayView().displayLogicalRunwayRevision(logicalRunway, revisedLogicalRunway);

        // displaying the calculation breakdown within the calculation breakdown view
        dashboard.getCalculationBreakdownView().displayCalculationBreakdown(calculationBreakdown);
        toolbar.allowRevisionExport();
        SystemController.dashboard.getNotificationPanel().displayNotification(new Notification(String.format(runwayrevision, logicalRunway.toString() , airport.getName()) , LocalDateTime.now(), Notification.Type.REVISION));
        autoSave();
    }
}
