package View;

import Controller.Validator;
import Model.Airport;
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
 * Form that allows for the user to edit an existing physical runway. Very similar to 'AddPhysicalRunwayForm', but takes
 * in a physical runway as an argument and displays the physical runway's details into the form.
 */
public class EditPhysicalRunwayForm extends InputForm {

    // member variables
    private EditAirportForm editAirportForm;
    private Airport airport;
    private PhysicalRunway physicalRunway;
    private TextField physicalRunwayName;
    private ToggleGroup numberOfLogicalRunways;
    private RadioButton oneLogicalRunway;
    private RadioButton twoLogicalRunway;
    private AddLogicalRunwayContainer logicalRunwayForm1;
    private AddLogicalRunwayContainer logicalRunwayForm2;
    private static String formname = "EditPhysicalRunway";

    /**
     * Class consructor
     * @param editAirportForm The edit airport form that opened this window.
     * @param physicalRunway The physical runway being edited.
     */
    public EditPhysicalRunwayForm(EditAirportForm editAirportForm, Airport airport, PhysicalRunway physicalRunway){
        super("Edit Physical Runway", 640, 750, "Submit", "Cancel",formname);

        // initialising member variables
        this.editAirportForm = editAirportForm;
        this.airport = airport;
        this.physicalRunway = physicalRunway;
        this.physicalRunwayName = new TextField();
        this.numberOfLogicalRunways = new ToggleGroup();
        this.oneLogicalRunway = new RadioButton();
        this.oneLogicalRunway.setUserData(1);
        this.oneLogicalRunway.setToggleGroup(this.numberOfLogicalRunways);
        this.twoLogicalRunway = new RadioButton();
        this.twoLogicalRunway.setUserData(2);
        this.twoLogicalRunway.setToggleGroup(this.numberOfLogicalRunways);
        this.logicalRunwayForm1 = new AddLogicalRunwayContainer("Logical Runway 1",formname+"logicalform1");
        this.logicalRunwayForm1.setId(formname+"logicalform1");
        this.logicalRunwayForm2 = new AddLogicalRunwayContainer("Logical Runway 2",formname+"logicalform2");
        this.logicalRunwayForm2.setId(formname+"logicalform2");

        // adding functionality to radio buttons
        this.oneLogicalRunway.setOnAction(((e) -> {
            this.logicalRunwayForm2.setDisable(true);
        }));
        this.twoLogicalRunway.setOnAction(((e) -> {
            this.logicalRunwayForm2.setDisable(false);
        }));

        // enabling forms based on the number of logical runways
        if(physicalRunway.getLogicalRunways().size() == 1){
            this.numberOfLogicalRunways.selectToggle(this.oneLogicalRunway);
            this.logicalRunwayForm2.setDisable(true);
        }
        else {
            this.numberOfLogicalRunways.selectToggle(this.twoLogicalRunway);
            this.logicalRunwayForm2.setDisable(false);
        }

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

        // inserting the values of the physical runway into the form
        this.insertPhysicalRunway();
    }

    /**
     * Inserts the physical runway given to the form into the controls of the form so that it's details can be
     * edited.
     */
    private void insertPhysicalRunway(){
        // setting the name of the runway into the name field
        this.physicalRunwayName.setText(this.physicalRunway.getName());

        // always at least one logical runway, so can add the first

        // setting the radio button to select one.
        this.numberOfLogicalRunways.selectToggle(this.oneLogicalRunway);
        this.logicalRunwayForm2.setDisable(true);

        // getting the logical runway
        LogicalRunway logicalRunway1 = this.physicalRunway.getLogicalRunways().get(0);

        // ADDING LOGICAL RUNWAY DETAILS INTO THE FORM

        // setting heading
        this.logicalRunwayForm1.getHeading().setText(String.valueOf(logicalRunway1.getHeading()));

        // setting runway position
        if(logicalRunway1.getPosition() == 'L'){
            this.logicalRunwayForm1.getPosition().selectToggle(this.logicalRunwayForm1.getLeftPosition());
        }
        else if(logicalRunway1.getPosition() == 'C'){
            this.logicalRunwayForm1.getPosition().selectToggle(this.logicalRunwayForm1.getCenterPosition());
        }
        else if(logicalRunway1.getPosition() == 'R'){
            this.logicalRunwayForm1.getPosition().selectToggle(this.logicalRunwayForm1.getRightPosition());
        }
        else if(logicalRunway1.getPosition() == 'N'){
            this.logicalRunwayForm1.getPosition().selectToggle(this.logicalRunwayForm1.getNoPosition());
        }

        // setting parameters
        this.logicalRunwayForm1.getTora().setText(String.valueOf(logicalRunway1.getParameters().getTora()));
        this.logicalRunwayForm1.getToda().setText(String.valueOf(logicalRunway1.getParameters().getToda()));
        this.logicalRunwayForm1.getAsda().setText(String.valueOf(logicalRunway1.getParameters().getAsda()));
        this.logicalRunwayForm1.getLda().setText(String.valueOf(logicalRunway1.getParameters().getLda()));
        this.logicalRunwayForm1.getDisplacedThreshold().setText(String.valueOf(logicalRunway1.getParameters().getDisplacedThreshold()));
        this.logicalRunwayForm1.getMinimumAngleOfAscentDescent().setText(String.valueOf(logicalRunway1.getParameters().getMinimumAngleOfAscentDescent()));

        // only adding the second one if it exists
        if(this.physicalRunway.getLogicalRunways().size() > 1){
            // setting the radio button to select one.
            this.numberOfLogicalRunways.selectToggle(this.twoLogicalRunway);
            this.logicalRunwayForm2.setDisable(false);

            // getting the logical runway
            LogicalRunway logicalRunway2 = this.physicalRunway.getLogicalRunways().get(1);

            // ADDING LOGICAL RUNWAY DETAILS INTO THE FORM

            // setting heading
            this.logicalRunwayForm2.getHeading().setText(String.valueOf(logicalRunway2.getHeading()));

            // setting runway position
            if(logicalRunway2.getPosition() == 'L'){
                this.logicalRunwayForm2.getPosition().selectToggle(this.logicalRunwayForm2.getLeftPosition());
            }
            else if(logicalRunway2.getPosition() == 'C'){
                this.logicalRunwayForm2.getPosition().selectToggle(this.logicalRunwayForm2.getCenterPosition());
            }
            else if(logicalRunway2.getPosition() == 'R'){
                this.logicalRunwayForm2.getPosition().selectToggle(this.logicalRunwayForm2.getRightPosition());
            }
            else if(logicalRunway2.getPosition() == 'N'){
                this.logicalRunwayForm2.getPosition().selectToggle(this.logicalRunwayForm2.getNoPosition());
            }

            // setting parameters
            this.logicalRunwayForm2.getTora().setText(String.valueOf(logicalRunway2.getParameters().getTora()));
            this.logicalRunwayForm2.getToda().setText(String.valueOf(logicalRunway2.getParameters().getToda()));
            this.logicalRunwayForm2.getAsda().setText(String.valueOf(logicalRunway2.getParameters().getAsda()));
            this.logicalRunwayForm2.getLda().setText(String.valueOf(logicalRunway2.getParameters().getLda()));
            this.logicalRunwayForm2.getDisplacedThreshold().setText(String.valueOf(logicalRunway2.getParameters().getDisplacedThreshold()));
            this.logicalRunwayForm2.getMinimumAngleOfAscentDescent().setText(String.valueOf(logicalRunway2.getParameters().getMinimumAngleOfAscentDescent()));
        }
    }

    /**
     * Submits the user's inputs to edit the given physical runway.
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
                validator.validatePhysicalRunwayEdit(this.airport, this.physicalRunway.getName(), this.physicalRunwayName.getText(), logicalRunways);

                // validation successful - creating new phsyical runway and sending it back to be edited
                PhysicalRunway newPhysicalRunway = new PhysicalRunway(this.physicalRunwayName.getText(), logicalRunways);
                this.editAirportForm.editPhysicalRunway(newPhysicalRunway);

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
                validator.validatePhysicalRunwayEdit(this.airport, this.physicalRunway.getName(), this.physicalRunwayName.getText(), logicalRunways);

                // validation successful - creating new phsyical runway and sending it back to be edited
                PhysicalRunway newPhysicalRunway = new PhysicalRunway(this.physicalRunwayName.getText(), logicalRunways);
                this.editAirportForm.editPhysicalRunway(newPhysicalRunway);

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

