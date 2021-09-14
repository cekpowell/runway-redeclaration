package View;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.util.Optional;

/**
 * Button that asks for confirmation before carrying out the action.
 */
public class ConfirmationButton extends Button {

    // member variables
    private String label;
    private String confirmationTitle;
    private String confirmationMessage;

    // button types
    private static final ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);


    /**
     * Class constructor
     * @param label The label for the button.
     * @param confirmationTitle The title of the confirmation window.
     * @param confirmationMessage The message displayed in the confirmation window.
     */
    public ConfirmationButton(String label, String confirmationTitle, String confirmationMessage){
        super(label);

        // initializinig member variables
        this.confirmationTitle = confirmationTitle;
        this.confirmationMessage = confirmationMessage;
    }

    /**
     * Class constructor.
     * @param label The label for the button.
     * @param image The button image.
     * @param confirmationTitle The title of the confirmation window.
     * @param confirmationMessage The message displayed in the confirmation window.
     */
    public ConfirmationButton(String label, ImageView image, String confirmationTitle, String confirmationMessage){
        super(label, image);

        // initializinig member variables
        this.confirmationTitle = confirmationTitle;
        this.confirmationMessage = confirmationMessage;
    }

    /**
     * Dsplays the confirmation window to the screen and returns the result.
     * @return True if the user selected confirm, false if selected cancel.
     */
    public boolean showConfirmationWindow(String formname){
        // setting up the window
        Alert confirmationWindow = new Alert(Alert.AlertType.CONFIRMATION, "", confirm, cancel);
        final Button okButton = (Button) confirmationWindow.getDialogPane().lookupButton(confirm);
        okButton.setId(formname+"confirm");
        confirmationWindow.setTitle(this.confirmationTitle);
        confirmationWindow.setHeaderText(this.confirmationMessage);
        confirmationWindow.getDialogPane().setPrefSize(300,100);

        // displaying the window and getting the result
        Optional<ButtonType> result = confirmationWindow.showAndWait();

        // user presses confirm
        if(result.get() == confirm){
            return true;
        }
        // user presses cancel (or closes window)
        else{
            return false;
        }
    }
}
