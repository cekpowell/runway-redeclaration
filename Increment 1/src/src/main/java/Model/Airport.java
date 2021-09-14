package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Hold information of an Airport with references to its runways and position
 */
public class Airport {
    String IATA;

    Position pos;

    ObservableList<PhysicalRunway> runways;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public Airport() {
        runways = FXCollections.observableArrayList();
    }

    public Airport(String IATA, Position pos) {
        super();
        this.IATA = IATA;
        runways = FXCollections.observableArrayList();
        this.pos = pos;
    }

    /**
     * Add new runway to the airport list
     * @param runway - new runway to add
     */
    public Boolean addNewRunway(PhysicalRunway runway) {
        if(getRunwayByName(runway.getName()) == null){
            return this.runways.add(runway);
        }
        else{
            return false;
        }
    }

    /**
     * Find a physical runway by name
     * @param name - Name of the runway to find
     * @return Physical runway with a specific name or null if not found
     */
    public PhysicalRunway getRunwayByName(String name) {

        for(PhysicalRunway runway : runways) {
            if(runway.getName().equals(name)) {
                return runway;
            }
        }

        return null;
    }

    /**
     * Find physical runway by name and remove it
     * @param name - Name of the runway to remove
     * @throws Exception - if a runway is not found
     */
    public void removeByName(String name) throws Exception {
        PhysicalRunway runway = getRunwayByName(name);

        if(runway == null) {
            throw new Exception("Runway ID not found in array!");
        }

        runways.remove(runway);
    }

    /**
     * Getter function for the IATA of an airport
     * @return IATA of an airport
     */
    public String getIATA() {return IATA;}

    /**
     * Setter function for the IATA of an airport
     * @param IATA - the unique tag of an airport
     */

    public void setIATA(String IATA) {this.IATA = IATA;}

    /**
     * Getter for airport's position
     * @return position of the airport
     */
    public Position getPos() {return pos;}

    /**
     * Setter for an airport's position
     * @param pos - new position of an airport
     */
    public void setPos(Position pos) {this.pos = pos;}

    public ObservableList<PhysicalRunway> getRunways() {
        return runways;
    }

    public void setRunways(ObservableList<PhysicalRunway> runways) {
        this.runways = runways;
    }

    @Override
    public String toString(){
        return this.name + " (" + this.IATA + ")";
    }
}
