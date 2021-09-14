package View;

import Controller.SystemController;
import Controller.Validator;
import Model.Obstacle;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.Optional;

/**
 * Form that allows the user to edit an existing obstacle within the system.
 */
public class EditObstacleAlert extends Alert{

    // declaring the button types of the alert window
    private static final ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    ObstacleForm form;
    /**
     * Constructor for the class. Creates a new form.
     */
    public EditObstacleAlert(){
        // setting up the window
        super(AlertType.CONFIRMATION, "", confirm, cancel);

        // configuring the dialog pane
        this.setTitle("Edit Obstacle");
        this.setHeaderText("Edit Obstacle");
        this.getDialogPane().setPrefSize(600,500);
        form = new ObstacleForm();
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
                //Obstacle o =form.getObstacle();
                form.updateCurrent();
                ObservableList<Obstacle> obs = form.getObstaclelist();
                SystemController.setObstacles(obs);
                //TODO prop
                //TEMP BUGFIX
                SystemController.autoSave();
            } catch(Exception e){
                e.printStackTrace();
                ErrorAlert errorAlert = new ErrorAlert(e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }


    /**
     * Checks if the current selection of inputs into the panel is valid.
     * @return True if the inputs are valid, false if not.
     */
    public boolean isValidInput(){
        //TODO...
        // Should check that the current selection of inputs into the panel is valid, returns the result.
        // At the moment, it just returns true regardless of input.
        boolean validInput =  true;

        if(validInput){
            return true;
        }
        else{
            return false;
        }
    }

    public void setFields(Obstacle o){
    }
}
