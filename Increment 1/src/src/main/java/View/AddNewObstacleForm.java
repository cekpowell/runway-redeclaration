package View;

import Controller.SystemController;
import Model.ValidationException;
import Controller.Validator;
import Model.Obstacle;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Optional;

/**
 * Form that allows for the user to add a new obstacle to the system.
 */
public class AddNewObstacleForm extends Alert {

    // declaring the button types of the alert window
    private static final ButtonType add = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    private TextField name;
    private TextField id;
    private TextField width;
    private TextField length;
    private TextField height;
    private TextArea properties;

    /**
     * Constructor for the class, creates a new form to allow the user to add a new obstacle to the system.
     */
    public AddNewObstacleForm(){
        // setting up the window
        super(AlertType.CONFIRMATION, "", add, cancel);

        // configuring the dialog pane
        this.setTitle("Add New Obstacle");
        this.setHeaderText("Add New Obstacle");
        this.getDialogPane().setPrefSize(700,600);

        VBox container = new VBox();

        this.name = new TextField();
        this.id = new TextField();
        this.width = new TextField();
        this.length = new TextField();
        this.height = new TextField();
        this.properties = new TextArea();

        this.name.setMaxWidth(300);
        this.id.setMaxWidth(300);
        this.width.setMaxWidth(300);
        this.length.setMaxWidth(300);
        this.height.setMaxWidth(300);
        this.properties.setMaxWidth(300);

        Label obstacleNameLabel = new Label("Obstacle Name: ");
        Label idLabel = new Label("ID: ");
        Label widthLabel = new Label("Width: ");
        Label lengthLabel = new Label("Length: ");
        Label heightLabel = new Label("Height:");
        Label propertiesLabel = new Label("Properties:");

        obstacleNameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        idLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        widthLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        lengthLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        heightLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        propertiesLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));

        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);



        container.getChildren().addAll( obstacleNameLabel,
                                        this.name,
                                        idLabel,
                                        this.id,
                                        widthLabel,
                                        this.width,
                                        lengthLabel,
                                        this.length,
                                        heightLabel,
                                        this.height,
                                        propertiesLabel,
                                        this.properties
        );

        this.getDialogPane().setContent(container);
    }

    /**
     * Displays the form to the screen as a pop-up window, displays errors if there are any with the user input.
     */
    public void showWindow(){
        Optional<ButtonType> result = this.showAndWait();

        // dealing with case where user presses to add object
        if(result.get() == add){
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
            }
            catch(ValidationException e){
                // validation error occured, so need to display error message
                ErrorAlert errorAlert = new ErrorAlert(e);
                errorAlert.showAndWait();
            }
        }
    }
}
