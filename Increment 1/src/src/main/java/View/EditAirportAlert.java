package View;

import Controller.SystemController;
import Controller.Validator;
import Model.Airport;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Form that allows the user to edit an existing airport within the system.
 */
public class EditAirportAlert extends Alert{

    // declaring the button types of the alert window
    private static final ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    AirportForm form;

    /**
     * Constructor for the class. Creates a new form.
     */
    public EditAirportAlert(){
        // setting up the window
        super(AlertType.CONFIRMATION, "", confirm, cancel);

        // configuring the dialog pane
        form = new AirportForm();
        this.setTitle("Edit Airport");
        this.setHeaderText("Edit Airport");
        this.getDialogPane().setPrefSize(600,500);
        this.getDialogPane().setContent(form);
    }

    /**
     * Displays the form to the screen as a pop-up window.
     */
    public void showWindow(){
        Optional<ButtonType> result = this.showAndWait();

        // dealing with case where user presses to confirm changes
        if(result.get() == confirm){
            try{
                form.updateAirport();
                SystemController.setAirports(form.getAirport());
                //TODO temp bugfix,
                SystemController.autoSave();
            }catch(Exception e){
                e.printStackTrace();
                ErrorAlert errorAlert = new ErrorAlert("EXAMPLE ERROR");
                errorAlert.showAndWait();
            }
        }
    }

}
