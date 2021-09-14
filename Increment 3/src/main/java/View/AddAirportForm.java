package View;

import Controller.SystemController;
import Controller.Validator;
import Model.Airport;
import Model.PhysicalRunway;
import Model.ValidationException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;

/**
 * Form that allows the user to add a new airport to the system.
 */
public class AddAirportForm extends AirportInputForm {

    // member variables
    private TextField name;
    private TextField IATA;
    private Button addPhysicalRunwayButton;
    private ArrayList<PhysicalRunway> physicalRunways;
    private Boolean noRunwaysAdded;
    private VBox runwaysContainer;
    public static String formname = "AddAirport";
    /**
     * Class constructor.
     */
    public AddAirportForm(){
        // calling super constructor
        super("Add New Airport", 700, 600, "Submit", "Cancel",formname);

        // initialising member variables
        this.name = new TextField();
        this.name.setId(formname+"name");
        this.IATA = new TextField();
        this.IATA.setId(formname+"IATA");
        this.name.setMaxWidth(300);
        this.IATA.setMaxWidth(300);
        this.physicalRunways = new ArrayList<PhysicalRunway>();
        this.addPhysicalRunwayButton = new Button("Add New Runway");
        this.addPhysicalRunwayButton.setId(formname+"addRunway");
        this.runwaysContainer = new VBox();
        this.runwaysContainer.setAlignment(Pos.CENTER);
        this.runwaysContainer.setSpacing(10);

        // creating labels for the form
        Label airportNameLabel = new Label("Airport Name: ");
        Label IATALabel = new Label("IATA: ");
        Label runwaysLabel = new Label("Runways: ");

        // formatting the labels
        airportNameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        IATALabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        runwaysLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));

        // container for input fields
        VBox form = new VBox();
        form.getChildren().addAll( airportNameLabel,
                this.name,
                IATALabel,
                this.IATA,
                runwaysLabel,
                this.runwaysContainer,
                this.addPhysicalRunwayButton
        );
        form.setAlignment(Pos.TOP_CENTER);
        form.setSpacing(10);
        form.setPadding(new Insets(20));

        // adding functionality to 'addNewRunway' button
        this.addPhysicalRunwayButton.setOnAction(((e) -> {
            // opening a new dialog for the runway input form
            AddPhysicalRunwayForm addPhysicalRunwayForm = new AddPhysicalRunwayForm(this);
            addPhysicalRunwayForm.initOwner(this.getScene().getWindow());
            addPhysicalRunwayForm.show();
        }));

        // scroll container
        ScrollPane scrollContainer = new ScrollPane(form);
        scrollContainer.setFitToWidth(true);

        // Adding content to the border pane
        BorderPane container = super.getContainer();
        container.setCenter(scrollContainer);
    }

    /**
     * Method run when a new physical runway is submitted. The new runway is added to the list of
     * current runways, and shown in the input form.
     * @param newRunway The new runway to be added to the
     */
    public void addNewPhysicalRunway(PhysicalRunway newRunway){
        // adding the new runway to the list
        this.physicalRunways.add(newRunway);

        // creating a label and button for the runway
        Label newRunwayLabel = new Label(newRunway.getName());
        ConfirmationButton removeButton = new ConfirmationButton("Discard", "Discard New Runway", "Are you sure you want to discard this new runway?");

        // placing label and button in container
        HBox container = new HBox();
        container.getChildren().addAll(newRunwayLabel, removeButton);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        // adding container to list of runways
        this.runwaysContainer.getChildren().add(container);

        // adding functionality to remove button
        removeButton.setOnAction((e) -> {
            // displaying confirmation window
            boolean removeConfirmed = removeButton.showConfirmationWindow(formname, this.getScene().getWindow());

            // removing if the user confirmed the removal
            if(removeConfirmed){
                // removing the runway
                this.runwaysContainer.getChildren().remove(container);
                this.physicalRunways.remove(newRunway);
            }
        });
    }

    /**
     * Verifies the user's inputs are valid. Creates a new object if they are, and displays an error message if they
     * are not.
     */
    public void submit(){
        // validating user input
        try{
            // testing if the input is valid
            Validator validator = new Validator();
            validator.validateAirportInput(this.name.getText(), this.IATA.getText(), this.physicalRunways);

            // airport is valid, so creating airport object using information
            Airport newAirport = new Airport();
            newAirport.setName(this.name.getText());
            newAirport.setIATA(this.IATA.getText());
            newAirport.setRunways(FXCollections.observableArrayList(this.physicalRunways));

            // adding airport to system
            SystemController.addAirport(newAirport);

            // submission complete, closing the window
            this.close();
        }
        // handling invalid input
        catch(ValidationException e) {
            // validation error occured, so need to display error message
            ErrorAlert errorAlert = new ErrorAlert(e);
            Button butt = (Button) errorAlert.getDialogPane().lookupButton(ButtonType.OK);
            butt.setId(formname+"error");
            errorAlert.showWindow(this.getScene().getWindow());
        }
    }
}
