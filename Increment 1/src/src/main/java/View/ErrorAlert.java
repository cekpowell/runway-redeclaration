package View;

import Model.ValidationException;
import javafx.scene.control.Alert;

/**
 * Alert window that displays an error message.
 */
public class ErrorAlert extends Alert {

    /**
     * Constructor for the class. Creates a new alert window with a ValidationException as the error.
     * @param exception The exception for this error.
     */
    public ErrorAlert(ValidationException exception){
        super(AlertType.ERROR, exception.getValidationErrors().toString());
        this.setTitle("Input Error");
        this.setHeaderText("Input Error");
    }

    /**
     * Constructor for the class. Creates a new alert window with a string as the error message.
     * @param errorMessage
     */
    public ErrorAlert(String errorMessage){
        super(AlertType.ERROR, errorMessage);
        this.setHeaderText("Error");
    }

    /**
     * Displays the alert window.
     */
    public void showWindow(){
        this.showAndWait();
    }
}
