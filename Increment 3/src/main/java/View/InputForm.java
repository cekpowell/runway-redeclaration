package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Asbtract class that represents the formatting of a window used to gather user input.
 */
public abstract class InputForm extends Stage {

    // member variables
    private BorderPane container;
    private ConfirmationButton finishButton;
    private ConfirmationButton cancelButton;
    private String formname;

    /**
     * Class constructor.
     * @param title The title of the window.
     */
    public InputForm(String title, int width, int height, String finishLabel, String cancelLabel,String formname){
        // configuring the input form
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle(title);
        this.formname = formname;

        // initialising member variables
        this.container = new BorderPane();
        this.finishButton = new ConfirmationButton (finishLabel, "Confirmation", "Are you sure you want to " +  finishLabel.toLowerCase() + "?");
        this.finishButton.setId(formname+"submit" );
        this.cancelButton = new ConfirmationButton (cancelLabel, "Confirmation", "Are you sure you want to " +  cancelLabel.toLowerCase() + "?");
        this.cancelButton.setId(formname + "cancel");
        // container for submit and cancel buttons
        HBox controlsContainer = new HBox();
        controlsContainer.getChildren().addAll(this.cancelButton, this.finishButton);
        controlsContainer.setAlignment(Pos.BASELINE_RIGHT);
        controlsContainer.setSpacing(10);
        controlsContainer.setPadding(new Insets(20));

        // container for divider and controls
        VBox separatorContainer = new VBox();
        Separator seperator = new Separator();
        separatorContainer.getChildren().addAll(seperator, controlsContainer);

        // adding functionality to 'submit' button
        this.finishButton.setOnAction(((e) -> {
            // displaying confirmation window
            boolean confirmed = finishButton.showConfirmationWindow(formname, this.getScene().getWindow());

            // submitting if the user confirmed the submit
            if(confirmed){
                // running the submit method
                this.submit();
            }
        }));

        // adding functionality to 'cancel' button
        this.cancelButton.setOnAction(((e) -> {
            // displaying confirmation window
            boolean confirmed = cancelButton.showConfirmationWindow(formname, this.getScene().getWindow());

            // closing if the user confirmed
            if(confirmed){
                // closing the application
                this.close();
            }
        }));

        // adding control buttons to form
        this.container.setBottom(separatorContainer);

        // creating new scene with container
        Scene scene = new Scene(container, width, height);

        // setting scene to stage
        this.setScene(scene);
    }

    /**
     * Getter method for the container (the actual form contents).
     * @return The container for the form.
     */
    public BorderPane getContainer(){
        return this.container;
    }

    /**
     * Verifies the user's inputs are valid. Creates a new object if they are, and displays an error message if they
     * are not.
     */
    public abstract void submit();
}
