package View;

import Controller.Validator;
import Model.*;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Form that allows the user to add a new airport to the system.
 */
public class AddNewPhysicalRunwayForm extends Alert {

    // declaring the button types of the alert window
    private static final ButtonType add = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    private TextField physicalRunwayName;
    private ToggleGroup numberOfLogicalRunways;
    private RadioButton oneLogicalRunway;
    private RadioButton twoLogicalRunway;
    private AddNewLogicalRunwayForm logicalRunwayForm1;
    private AddNewLogicalRunwayForm logicalRunwayForm2;

    /**
     * Constructor for the class. Creates a new form.
     */
    public AddNewPhysicalRunwayForm(){
        // setting up the window
        super(Alert.AlertType.CONFIRMATION, "", add, cancel);

        // configuring the dialog pane
        this.setTitle("Add New Runway");
        this.setHeaderText("Add New Runway");
        this.getDialogPane().setPrefSize(620,800);

        // initialising member variables
        this.physicalRunwayName = new TextField();
        this.numberOfLogicalRunways = new ToggleGroup();
        this.oneLogicalRunway = new RadioButton();
        this.oneLogicalRunway.setUserData(1);
        this.oneLogicalRunway.setToggleGroup(this.numberOfLogicalRunways);
        this.twoLogicalRunway = new RadioButton();
        this.twoLogicalRunway.setUserData(2);
        this.twoLogicalRunway.setToggleGroup(this.numberOfLogicalRunways);
        this.numberOfLogicalRunways.selectToggle(this.twoLogicalRunway);
        this.logicalRunwayForm1 = new AddNewLogicalRunwayForm("Logical Runway 1");
        this.logicalRunwayForm2 = new AddNewLogicalRunwayForm("Logical Runway 2");

        // adding functionality to radio buttons
        this.oneLogicalRunway.setOnAction(((e) -> {
                this.logicalRunwayForm2.setDisable(true);
        }));
        this.twoLogicalRunway.setOnAction(((e) -> {
            this.logicalRunwayForm2.setDisable(false);
        }));


        // creating labels
        Label physicalRunwayNameLabel = new Label("Physical Runway Name : ");
        Label numberOfLogicalRunwaysLabel = new Label("Number of Logical Runways");
        Label oneLogicalRunwayLabel = new Label("One : ");
        Label twoLogicalRunwayLabel = new Label("Two : ");

        // supplementary container for physical runway
        HBox physicalRunwayNameContainer = new HBox();
        physicalRunwayNameContainer.getChildren().addAll(physicalRunwayNameLabel, this.physicalRunwayName);
        physicalRunwayNameContainer.setSpacing(20);
        physicalRunwayNameContainer.setAlignment(Pos.CENTER);

        // supplementary container for number of logical runways
        VBox numberOfLogicalRunwaysContainer = new VBox();
        HBox logicalRunwayRadioButtons = new HBox();
        logicalRunwayRadioButtons.getChildren().addAll(oneLogicalRunwayLabel, this.oneLogicalRunway,
                                                             twoLogicalRunwayLabel, this.twoLogicalRunway);
        logicalRunwayRadioButtons.setSpacing(20);
        logicalRunwayRadioButtons.setAlignment(Pos.CENTER);
        numberOfLogicalRunwaysContainer.getChildren().addAll(numberOfLogicalRunwaysLabel, logicalRunwayRadioButtons);
        numberOfLogicalRunwaysContainer.setAlignment(Pos.CENTER);
        numberOfLogicalRunwaysContainer.setSpacing(20);

        // seperator for the two runway views
        Separator inputFormSeparator = new Separator();
        inputFormSeparator.setOrientation(Orientation.VERTICAL);

        // supplementary container for logical runway input forms
        HBox logicalRunwayInputContainer = new HBox();
        logicalRunwayInputContainer.getChildren().addAll(this.logicalRunwayForm1, inputFormSeparator, this.logicalRunwayForm2);
        logicalRunwayInputContainer.setSpacing(15);
        logicalRunwayInputContainer.setAlignment(Pos.CENTER);

        // main form container
        VBox container = new VBox();
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(20);
        container.getChildren().addAll( physicalRunwayNameContainer,
                                        numberOfLogicalRunwaysContainer,
                                        logicalRunwayInputContainer
        );

        // adding main container to a scroll pane
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);

        // adding main container to form
        this.getDialogPane().setContent(scrollPane);
    }

    /**
     * Displays the form to the screen as a pop-up window, and returns the physical runway the user creates
     * and displays any errors if there are any.
     */
    public PhysicalRunway showWindow(){
        Optional<ButtonType> result = this.showAndWait();

        // dealing with case where user presses to add object
        if(result.get() == add){
            // checking that the inputs are valid
            try{
                // testing if the input is valid
                Validator validator = new Validator();

                // testing how many logical runways the user has selected
                if((Integer) this.numberOfLogicalRunways.getSelectedToggle().getUserData() == 1){
                    // one logical runway to be added

                    // getting + validating logical runways
                    LogicalRunway logicalRunway1 = logicalRunwayForm1.getLogicalRunway();

                    // adding logical runways to list
                    ArrayList<LogicalRunway> logicalRunways = new ArrayList<LogicalRunway>();
                    logicalRunways.add(logicalRunway1);

                    // validating physical runway
                    validator.validatePhysicalRunwayInput(this.physicalRunwayName.getText(), logicalRunways);

                    // validation successful - creating and returning physical runway object
                    PhysicalRunway newPhysicalRunway = new PhysicalRunway(this.physicalRunwayName.getText(), logicalRunways);
                    return newPhysicalRunway;
                }
                else {
                    // two logical runways to be added

                    // getting + validating logical runways
                    LogicalRunway logicalRunway1 = logicalRunwayForm1.getLogicalRunway();
                    LogicalRunway logicalRunway2 = logicalRunwayForm2.getLogicalRunway();

                    // adding logical runways to list
                    ArrayList<LogicalRunway> logicalRunways = new ArrayList<LogicalRunway>();
                    logicalRunways.add(logicalRunway1);
                    logicalRunways.add(logicalRunway2);

                    // validating physical runway
                    validator.validatePhysicalRunwayInput(this.physicalRunwayName.getText(), logicalRunways);

                    // validation successful - creating and returning physical runway object
                    PhysicalRunway newPhysicalRunway = new PhysicalRunway(this.physicalRunwayName.getText(), logicalRunways);
                    return newPhysicalRunway;
                }
            }
            catch(ValidationException e){
                // validation error occured, so need to display error message
                ErrorAlert errorAlert = new ErrorAlert(e);
                errorAlert.showAndWait();
                return null;
            }
        }
        else{
            // user did not try to add anything, nothing to do - so returning null.
            return null;
        }
    }
}