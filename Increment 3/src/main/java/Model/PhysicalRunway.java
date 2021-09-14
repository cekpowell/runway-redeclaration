package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Represents a physical runway within an airport, which could have one or two logical runways.
 */
public class PhysicalRunway {

    private String name; // name of the physical runway (e.g. "02R/20L")
    private ObservableList<LogicalRunway> logicalRunways; // logical runways associated with this runway.

    /**
     * Default constructor method for the class.
     */
    public PhysicalRunway(){
        this.logicalRunways = FXCollections.observableArrayList();
    }

    /**
     * Constructor method for the class. Takes in the name and logical runways associated with the
     * physical runway.
     * @param name The name of the physical runway.
     * @param logicalRunways The logical runways associated with this physical runway.
     */
    public PhysicalRunway(String name, ArrayList<LogicalRunway> logicalRunways){
        this.name = name;
        this.logicalRunways = FXCollections.observableArrayList(logicalRunways);
    }

    /**
     * Constructor method for the class. Takes in the name of the physical runway.
     * @param name The name of the physical runway.
     */
    public PhysicalRunway(String name) {
        this.name = name;
        this.logicalRunways = FXCollections.observableArrayList(logicalRunways);
    }

    /**
     * Adds a logical runway to the list of logical runways (as long as less than 2 have been added).
     * @param logicalRunway The logical runway to be added.
     * @return True if the runway can be added, false if not.
     */
    public boolean addLogicalRunway(LogicalRunway logicalRunway) {
        if(this.logicalRunways.size() >= 2){
            return false;
        }
        else {
            this.logicalRunways.add(logicalRunway);
            return true;
        }
    }

    /**
     * Getters and setters.
     */

    public String getName() {
        return this.name;
    }

    public ObservableList<LogicalRunway> getLogicalRunways() {
        return this.logicalRunways;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogicalRunways(ObservableList<LogicalRunway> logicalRunways) {
        if(logicalRunways.size() <= 2){
            this.logicalRunways = logicalRunways;
        }
    }

    /**
     * Returns a String representation of the physical runway (i.e., the name of the runway).
     * @return String representation of the physical runway.
     */
    @Override
    public String toString(){
        return this.name; // the name of the runway represents the runway.
    }
}