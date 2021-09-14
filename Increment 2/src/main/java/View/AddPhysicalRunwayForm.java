package View;

import Controller.Validator;
import Model.LogicalRunway;
import Model.PhysicalRunway;
import Model.ValidationException;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

/**
 * Form that allows the user to add a new physical runway to aan airport.
 */
public class AddPhysicalRunwayForm extends InputForm{

    // member variables
    private AirportInputForm airportInputForm;
    private TextField physicalRunwayName;
    private ToggleGroup numberOfLogicalRunways;
    private RadioButton oneLogicalRunway;
    private RadioButton twoLogicalRunway;
    private AddLogicalRunwayContainer logicalRunwayForm1;
    private AddLogicalRunwayContainer logicalRunwayForm2;
    private static String formname = "AddPhysicalRunway";

    /**
     * Class constructor.
     */
    public AddPhysicalRunwayForm(AirportInputForm airportInputForm){
        // calling super constructor
        super("Add New Physical Runway", 640, 750, "Submit", "Cancel",formname);

        // initialising member variables
        this.airportInputForm = airportInputForm;
        this.physicalRunwayName = new TextField();
        this.physicalRunwayName.setId(formname+"name");
        this.numberOfLogicalRunways = new ToggleGroup();
        this.oneLogicalRunway = new RadioButton();
        this.oneLogicalRunway.setId(formname+"oneLogicalRunway");
        this.oneLogicalRunway.setUserData(1);
        this.oneLogicalRunway.setToggleGroup(this.numberOfLogicalRunways);
        this.twoLogicalRunway = new RadioButton();
        this.twoLogicalRunway.setUserData(2);
        this.twoLogicalRunway.setId(formname+"twoLogicalRunway");
        this.twoLogicalRunway.setToggleGroup(this.numberOfLogicalRunways);
        this.numberOfLogicalRunways.selectToggle(this.twoLogicalRunway);
        this.logicalRunwayForm1 = new AddLogicalRunwayContainer("Logical Runway 1",formname+"logicalRunwayForm1");
        this.logicalRunwayForm1.setId(formname+"logicalRunwayForm1");
        this.logicalRunwayForm2 = new AddLogicalRunwayContainer("Logical Runway 2",formname +"logicalRunwayForm2");
        this.logicalRunwayForm2.setId(formname +"logicalRunwayForm2");
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
        VBox form = new VBox();
        form.setAlignment(Pos.TOP_CENTER);
        form.getChildren().addAll( physicalRunwayNameContainer,
                numberOfLogicalRunwaysContainer,
                logicalRunwayInputContainer
        );
        form.setSpacing(20);
        form.setPadding(new Insets(20));

        // Adding content to the border pane
        BorderPane container = super.getContainer();
        container.setCenter(form);
    }

    /**
     * Verifies the user's inputs are valid. Creates a new object if they are, and displays an error message if they
     * are not.
     */
    public void submit(){
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

                // validation successful - creating new physical runway and adding it to the airport form
                PhysicalRunway newPhysicalRunway = new PhysicalRunway(this.physicalRunwayName.getText(), logicalRunways);
                this.airportInputForm.addNewPhysicalRunway(newPhysicalRunway);

                // submission complete, closing window
                this.close();
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

                // validation successful - creating new phsyical runway and adding it to the airport form
                PhysicalRunway newPhysicalRunway = new PhysicalRunway(this.physicalRunwayName.getText(), logicalRunways);
                this.airportInputForm.addNewPhysicalRunway(newPhysicalRunway);

                // submission complete, closing window
                this.close();
            }
        }
        // handling error thrown
        catch(ValidationException e){
            // validation error occured, so need to display error message
            ErrorAlert errorAlert = new ErrorAlert(e);
            Button butt = (Button) errorAlert.getDialogPane().lookupButton(ButtonType.OK);
            butt.setId(formname+"error");
            errorAlert.showAndWait();
        }
    }
}
