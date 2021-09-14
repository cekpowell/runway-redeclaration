package View;

import Model.PhysicalRunway;

/**
 * Represents a type of input form that gathers information for airports.
 * Made to handle physical runway's being added and stored in the form during input.
 */
public abstract class AirportInputForm extends InputForm {

    /**
     * Constructor for the class
     * @param title The title of the window
     * @param width The width of the window.
     * @param height The height of the window.
     */
    public AirportInputForm(String title, int width, int height, String finishLabel, String cancelLabel,String formname){
        // calling super constructor
        super(title, width, height, finishLabel, cancelLabel,formname);
    }

    /**
     * Method run when a new physical runway is submitted. The new runway is added to the list of
     * current runways, and shown in the input form.
     * @param newRunway The new runway to be added to the form.
     */
    public abstract void addNewPhysicalRunway(PhysicalRunway newRunway);
}
