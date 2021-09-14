package View;

import Controller.SystemController;
import Controller.Validator;
import Model.Obstacle;
import Model.ValidationException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Form that allows for the user to edit an existing obstacle.
 */
public class EditObstacleForm extends InputForm {

    // member variables
    private ComboBox obstacleSelection;
    private ConfirmationButton removeObstacleButton;
    private Obstacle selectedObstacle;
    private TextField nameTextField;
    private Button editNameButton;
    private TextField widthTextField;
    private Button editWidthButton;
    private TextField lengthTextField;
    private Button editLengthButton;
    private TextField heightTextField;
    private Button editHeightButton;


    // static variables
    private static final Image editImage = new Image("edit.png");
    private static final Image removeImage = new Image("del.png");
    private static String formname = "EditObstacle";

    /**
     * Class constructor.
     */
    public EditObstacleForm(){
        // calling the super constructor
        super("Edit Obstacle", 700, 600, "Finish", "Cancel",formname);

        // initialising member variables
        this.obstacleSelection = new ComboBox();
        this.obstacleSelection.setId(formname+"select");
        this.obstacleSelection.setItems(SystemController.getObstacles());
        this.removeObstacleButton = new ConfirmationButton("", new ImageView(removeImage), "Removing Obstacle", "Are you sure you want to remove this obstacle?");
        this.removeObstacleButton.setId(formname+"remove");
        this.removeObstacleButton.setDisable(true); // disabled at start
        this.selectedObstacle = null;
        this.nameTextField = new TextField();
        this.nameTextField.setId(formname+"name");
        this.editNameButton = new Button("", new ImageView(editImage));
        this.editNameButton.setId(formname+"nameenable");
        this.widthTextField = new TextField();
        this.widthTextField.setId(formname+"width");
        this.editWidthButton = new Button("", new ImageView(editImage));
        this.editWidthButton.setId(formname+"widthenable");
        this.lengthTextField = new TextField();
        this.lengthTextField.setId(formname+"length");
        this.editLengthButton = new Button("", new ImageView(editImage));
        this.editLengthButton.setId(formname+"lengthenable");
        this.heightTextField = new TextField();
        this.heightTextField.setId(formname+"height");
        this.editHeightButton = new Button("", new ImageView(editImage));
        this.editHeightButton.setId(formname+"heightenable");

        // labels
        Label obstacleSelectionLabel = new Label("Select Obstacle");
        Label nameLabel = new Label("Name");
        Label widthLabel = new Label("Width");
        Label lengthLabel = new Label("Length");
        Label heightLabel = new Label("Height");

        // obstacle selection container
        HBox obstacleSelectionContainer = new HBox();
        obstacleSelectionContainer.getChildren().addAll(obstacleSelectionLabel, this.obstacleSelection, this.removeObstacleButton);
        obstacleSelectionContainer.setSpacing(50);
        obstacleSelectionContainer.setAlignment(Pos.CENTER);

        // name container
        HBox nameContainer = new HBox();
        nameContainer.getChildren().addAll(nameLabel, this.nameTextField, this.editNameButton);
        nameContainer.setSpacing(50);
        nameContainer.setAlignment(Pos.CENTER);
        nameContainer.setDisable(true); // disabled at start

        // name container
        HBox widthContainer = new HBox();
        widthContainer.getChildren().addAll(widthLabel, this.widthTextField, this.editWidthButton);
        widthContainer.setSpacing(50);
        widthContainer.setAlignment(Pos.CENTER);
        widthContainer.setDisable(true); // disabled at start

        // name container
        HBox lengthContainer = new HBox();
        lengthContainer.getChildren().addAll(lengthLabel, this.lengthTextField, this.editLengthButton);
        lengthContainer.setSpacing(50);
        lengthContainer.setAlignment(Pos.CENTER);
        lengthContainer.setDisable(true); // disabled at start

        // name container
        HBox heightContainer = new HBox();
        heightContainer.getChildren().addAll(heightLabel, this.heightTextField, this.editHeightButton);
        heightContainer.setSpacing(50);
        heightContainer.setAlignment(Pos.CENTER);
        heightContainer.setDisable(true); // disabled at start

        // container for all containers
        VBox form = new VBox();
        form.getChildren().addAll(obstacleSelectionContainer,
                                  nameContainer,
                                  widthContainer,
                                  lengthContainer,
                                  heightContainer
                                 );
        form.setSpacing(50);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);

        // obstacle selection functionality
        this.obstacleSelection.setOnAction((e) -> {
            if(this.obstacleSelection.getValue() != null) {
                // updating the previously selected obstacle if there is one
                if(this.selectedObstacle != null){
                    this.updateSelectedObstacle();
                }

                // updating the form controls
                this.removeObstacleButton.setDisable(false);
                this.selectedObstacle = (Obstacle) this.obstacleSelection.getValue();
                this.nameTextField.setText(this.selectedObstacle.getName());
                this.widthTextField.setText(String.valueOf(this.selectedObstacle.getWidth()));
                this.lengthTextField.setText(String.valueOf(this.selectedObstacle.getLength()));
                this.heightTextField.setText(String.valueOf(this.selectedObstacle.getHeight()));

                // enabling obstacle name controls
                nameContainer.setDisable(false);
                this.nameTextField.setDisable(true);

                // enabling obstacle width controls
                widthContainer.setDisable(false);
                this.widthTextField.setDisable(true);

                // enabling obstacle length controls
                lengthContainer.setDisable(false);
                this.lengthTextField.setDisable(true);

                // enabling obstacle height controls
                heightContainer.setDisable(false);
                this.heightTextField.setDisable(true);
            }
        });

        // removing obstacle functionality
        this.removeObstacleButton.setOnAction((e) -> {
            // displaying confirmation window
            boolean removeConfirmed = this.removeObstacleButton.showConfirmationWindow(formname);

            // removing if the user confirmed the removal
            if(removeConfirmed){
                // removing the obstacle from the system
                SystemController.removeObstacle(this.selectedObstacle);
                this.selectedObstacle = null;

                // resetting the controls
                this.obstacleSelection.getSelectionModel().clearSelection();
                this.removeObstacleButton.setDisable(true);
                this.nameTextField.setText("");
                nameContainer.setDisable(true);
                widthContainer.setDisable(true);
                this.widthTextField.setText("");
                lengthContainer.setDisable(true);
                this.lengthTextField.setText("");
                heightContainer.setDisable(true);
                this.heightTextField.setText("");
            }
        });

        // edit name functionality
        this.editNameButton.setOnAction((e) -> {
            this.nameTextField.setDisable(false);
        });

        // edit width functionality
        this.editWidthButton.setOnAction((e) -> {
            this.widthTextField.setDisable(false);
        });

        // edit length functionality
        this.editLengthButton.setOnAction((e) -> {
            this.lengthTextField.setDisable(false);
        });

        // edit height functionality
        this.editHeightButton.setOnAction((e) -> {
            this.heightTextField.setDisable(false);
        });

        // adding main container to window
        super.getContainer().setCenter(form);
    }

    /**
     * Attempts to update the selected obstacle with the given values.
     * @return True if the obstacle was updated correctly, false if not.
     */
    private boolean updateSelectedObstacle(){
        // only updating if there is an obstacle currently selected.
        if(this.selectedObstacle != null){
            // updating selected obstacle
            try {
                // testing if the input is valid
                Validator validator = new Validator();
                validator.validateObstacleEdit(this.selectedObstacle,
                        this.nameTextField.getText(),
                        this.widthTextField.getText(),
                        this.lengthTextField.getText(),
                        this.heightTextField.getText());

                // input valid

                // gathering values
                String newName = this.nameTextField.getText();
                double newWidth = Double.valueOf(this.widthTextField.getText());
                double newLength = Double.valueOf(this.lengthTextField.getText());
                double newHeight = Double.valueOf(this.heightTextField.getText());

                // editing the obstacle
                SystemController.editObstacle(this.selectedObstacle, newName, newWidth, newLength, newHeight);

                // editing was successful, so returning true
                return true;
            }
            // handling invalid input
            catch(ValidationException e) {
                // validation error occured, so need to display error message
                ErrorAlert errorAlert = new ErrorAlert(e);
                Button butt = (Button) errorAlert.getDialogPane().lookupButton(ButtonType.OK);
                butt.setId(formname+"error");
                errorAlert.showAndWait();

                // editing failed, so returning false
                return false;
            }
        }
        else{
            // returning true as there is nothing to update
            return true;
        }
    }

    /**
     * Verifies the user's inputs are valid. Edit's the object's if they are, and displays an error message if they
     * are not.
     */
    public void submit(){
        // only closing if the selected airport was updated successfully.
        if(this.updateSelectedObstacle()){
            this.close();
        }
    }
}
