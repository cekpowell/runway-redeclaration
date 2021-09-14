package View;

import Controller.SystemController;
import Controller.Validator;
import Model.Obstacle;
import Model.ValidationException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 * Form that allows the user to add a new obstacle to the system.
 */
public class AddObstacleForm extends InputForm {

    // member variables
    private TextField name;
    private TextField id;
    private TextField width;
    private TextField length;
    private TextField height;

    private static String formname = "Obstacle";

    /**
     * Class constructor.
     */
    public AddObstacleForm(){
        // calling super constructor
        super("Add New Obstacle", 700, 600, "Submit", "Cancel",formname);

        // initializing member variables
        this.name = new TextField();
        this.name.setId(formname+"name");
        this.id = new TextField();
        this.id.setId(formname+"id");
        this.width = new TextField();
        this.width.setId(formname+"width");
        this.length = new TextField();
        this.length.setId(formname+"length");
        this.height = new TextField();
        this.height.setId(formname+"height");

        // configuring member variables
        this.name.setMaxWidth(300);
        this.id.setMaxWidth(300);
        this.width.setMaxWidth(300);
        this.length.setMaxWidth(300);
        this.height.setMaxWidth(300);

        Label obstacleNameLabel = new Label("Obstacle Name: ");
        Label idLabel = new Label("ID: ");
        Label widthLabel = new Label("Width: ");
        Label lengthLabel = new Label("Length: ");
        Label heightLabel = new Label("Height:");

        obstacleNameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        idLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        widthLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        lengthLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        heightLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));

        // form to house input controls
        VBox form = new VBox();
        form.setAlignment(Pos.CENTER);
        form.getChildren().addAll( obstacleNameLabel,
                this.name,
                idLabel,
                this.id,
                widthLabel,
                this.width,
                lengthLabel,
                this.length,
                heightLabel,
                this.height
        );
        form.setSpacing(10);
        form.setPadding(new Insets(20));

        // addinng form to container
        super.getContainer().setCenter(form);
    }

    /**
     * Verifies the user's inputs are valid. Creates a new object if they are, and displays an error message if they
     * are not.
     */
    public void submit() {
        // validating user input
        try{
            // testing if input is valid
            Validator validator = new Validator();
            validator.validateObstacleInput(this.name.getText(), this.id.getText(), this.width.getText(), this.height.getText(), this.length.getText());

            // input is valid, so creating obstacle object using information
            Obstacle newObstacle = new Obstacle();
            newObstacle.setName(this.name.getText());
            newObstacle.setId(Integer.valueOf(this.id.getText()));
            newObstacle.setWidth(Double.valueOf(this.width.getText()));
            newObstacle.setLength(Double.valueOf(this.length.getText()));
            newObstacle.setHeight(Double.valueOf(this.height.getText()));

            // adding airport to system
            SystemController.addObstacle(newObstacle);

            // submission complete, closing window
            this.close();
        }
        // handling invalid input
        catch(ValidationException e){
            // validation error occured, so need to display error message
            ErrorAlert errorAlert = new ErrorAlert(e);
            Button butt = (Button) errorAlert.getDialogPane().lookupButton(ButtonType.OK);
            butt.setId(formname+"error");
            errorAlert.showAndWait();
        }
    }
}
