package View;

import Controller.SystemController;
import Controller.Validator;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

/**
 * Form that allows for the user to edit an existing airport.
 */
public class EditAirportForm extends AirportInputForm {

    // member variables
    private ComboBox airportSelection;
    private Airport selectedAirport;
    private ConfirmationButton removeAirportButton;
    private TextField airportNameTextField;
    private Button editAirportNameButton;
    private TextField airportIATATextField;
    private Button editAirportIATAButton;
    private ComboBox runwaySelection;
    private PhysicalRunway selectedRunway;
    private Button editRunwayButton;
    private ConfirmationButton removeRunwayButton;
    private Button addNewRunwayButton;
    private ObservableList<Airport> airportsList;
    private ObservableList<PhysicalRunway> selectedAirportRunwayList;
    private ArrayList<PhysicalRunway> newRunways;
    private VBox newRunwaysList;
    public static String formname = "EditAirport";
    // static variables
    private static final Image editImage = new Image("edit.png");
    private static final Image removeImage = new Image("del.png");

    /**
     * Class constructor
     */
    public EditAirportForm() {
        // running super constructor
        super("Edit Airport", 700, 600, "Finish", "Cancel",formname);

        // initialising member variables
        this.airportSelection = new ComboBox();
        this.airportSelection.setId(formname+"select");
        this.selectedAirport = null;
        this.selectedRunway = null;
        this.removeAirportButton = new ConfirmationButton("", new ImageView(removeImage),"Removing Airport", "Are you sure you want to remove the airport?");
        this.removeAirportButton.setId(formname+"removeairport");
        this.removeAirportButton.setDisable(true);
        this.airportNameTextField = new TextField();
        this.airportNameTextField.setId(formname+"name");
        this.editAirportNameButton = new Button("", new ImageView(editImage));
        this.editAirportNameButton.setId(formname+"nameenable");
        this.airportIATATextField = new TextField();
        this.airportIATATextField.setId(formname+"iata");
        this.editAirportIATAButton = new Button("", new ImageView(editImage));
        this.editAirportIATAButton.setId(formname+"iataenable");
        this.runwaySelection = new ComboBox();
        this.runwaySelection.setId(formname+"runselect");
        this.editRunwayButton = new Button("Edit Runway");
        this.editRunwayButton.setId(formname+"editrunway");
        this.removeRunwayButton = new ConfirmationButton("Remove Runway", "Removing Runway", "Are you sure you want to remove the runway?");
        this.removeRunwayButton.setId(formname+"removerun");
        this.addNewRunwayButton = new Button ("Add New Runway");
        this.addNewRunwayButton.setId(formname+"addRunway");
        this.airportSelection.setItems(SystemController.getAirports());
        this.selectedAirportRunwayList = FXCollections.observableArrayList();
        this.newRunways = new ArrayList<PhysicalRunway>();
        this.newRunwaysList = new VBox();

        // labels
        Label airportSelectionLabel = new Label("Select Airport");
        Label airportNameLabel = new Label("Name");
        Label airportIATALabel = new Label("IATA");
        Label runwaySelectionLabel = new Label("Select Runway");
        Label newRunwaysLabel = new Label("New Runways");

        // airport selection container
        HBox airportSelectionContainer = new HBox();
        airportSelectionContainer.getChildren().addAll(airportSelectionLabel, this.airportSelection, this.removeAirportButton);
        airportSelectionContainer.setSpacing(50);
        airportSelectionContainer.setAlignment(Pos.CENTER);

        // airport name container
        HBox airportNameContainer = new HBox();
        airportNameContainer.getChildren().addAll(airportNameLabel, this.airportNameTextField, this.editAirportNameButton);
        airportNameContainer.setSpacing(50);
        airportNameContainer.setAlignment(Pos.CENTER);
        airportNameContainer.setDisable(true); // disabled at start

        // airport IATA container
        HBox airportIATAContainer = new HBox();
        airportIATAContainer.getChildren().addAll(airportIATALabel, this.airportIATATextField, this.editAirportIATAButton);
        airportIATAContainer.setSpacing(50);
        airportIATAContainer.setAlignment(Pos.CENTER);
        airportIATAContainer.setDisable(true); // disabled at start

        // runway selection container
        HBox runwaySelectionContainer = new HBox();
        runwaySelectionContainer.getChildren().addAll(runwaySelectionLabel, this.runwaySelection, this.editRunwayButton, this.removeRunwayButton);
        runwaySelectionContainer.setSpacing(50);
        runwaySelectionContainer.setAlignment(Pos.CENTER);
        runwaySelectionContainer.setDisable(true); // disabled at start

        // new runways container
        this.newRunwaysList.getChildren().add(newRunwaysLabel);
        this.newRunwaysList.setSpacing(20);
        this.newRunwaysList.setAlignment(Pos.CENTER);

        // container for list of runways and add new runway button
        VBox runwaysContainer = new VBox();
        runwaysContainer.getChildren().addAll(this.newRunwaysList, this.addNewRunwayButton);
        runwaysContainer.setSpacing(20);
        runwaysContainer.setAlignment(Pos.CENTER);
        runwaysContainer.setDisable(true);

        // container for all containers
        VBox form = new VBox();
        form.getChildren().addAll(airportSelectionContainer,
                                  airportNameContainer,
                                  airportIATAContainer,
                                  runwaySelectionContainer,
                                  runwaysContainer
                                  );
        form.setSpacing(50);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.TOP_CENTER);

        // airport selection functionality
        this.airportSelection.setOnAction((e) -> {
            if(this.airportSelection.getValue() != null && (Airport) this.airportSelection.getValue() != this.selectedAirport) {
                // updating the previously selected airport if there is one
                if(this.selectedAirport != null){
                    this.updateSelectedAirport();
                }

                // updating the form controls
                this.removeAirportButton.setDisable(false);
                this.selectedAirport = (Airport) this.airportSelection.getValue();
                this.airportNameTextField.setText(this.selectedAirport.getName());
                this.airportIATATextField.setText(this.selectedAirport.getIATA());
                this.selectedAirportRunwayList.clear();
                this.selectedAirportRunwayList.addAll(this.selectedAirport.getRunways());
                this.runwaySelection.setItems(this.selectedAirportRunwayList);

                // configuring airport name controls
                airportNameContainer.setDisable(false);
                airportNameTextField.setDisable(true);

                // configuring airport IATA controls
                airportIATAContainer.setDisable(false);
                airportIATATextField.setDisable(true);

                // configuring runway selection controls
                runwaySelectionContainer.setDisable(false);
                runwaysContainer.setDisable(false);
                this.editRunwayButton.setDisable(true);
                this.removeRunwayButton.setDisable(true);
                this.addNewRunwayButton.setDisable(false);
            }
        });

        // removing airport functionality
        this.removeAirportButton.setOnAction((e) -> {
            // displaying confirmation window
            boolean removeConfirmed = this.removeAirportButton.showConfirmationWindow(formname);

            // removing if the user confirmed the removal
            if(removeConfirmed){
                // removing the airport from the system
                SystemController.removeAirport(this.selectedAirport);
                this.selectedAirport = null;
                this.newRunways.clear();

                // resetting the controls
                this.airportSelection.getSelectionModel().clearSelection();
                this.removeAirportButton.setDisable(true);
                airportNameContainer.setDisable(true);
                this.airportNameTextField.setText("");
                airportIATAContainer.setDisable(true);
                this.airportIATATextField.setText("");
                runwaySelectionContainer.setDisable(true);
                runwaysContainer.setDisable(true);
            }
        });

        // edit airport name functionality
        this.editAirportNameButton.setOnAction((e) -> {
            this.airportNameTextField.setDisable(false);
        });

        // edit airport IATA functionality
        this.editAirportIATAButton.setOnAction((e) -> {
            this.airportIATATextField.setDisable(false);
        });

        // runway selection functionality
        this.runwaySelection.setOnAction((e) -> {
            if(this.runwaySelection.getValue() != null){
                this.selectedRunway = (PhysicalRunway) this.runwaySelection.getValue();
                this.editRunwayButton.setDisable(false);
                this.removeRunwayButton.setDisable(false);
            }
        });

        // editing runway functionality
        this.editRunwayButton.setOnAction((e -> {
            // opening the form to edit the runway
            EditPhysicalRunwayForm editRunwayForm = new EditPhysicalRunwayForm(this, this.selectedAirport, this.selectedRunway);
            editRunwayForm.initOwner(this.getScene().getWindow());
            editRunwayForm.show();
        }));

        // removing runway functionality
        this.removeRunwayButton.setOnAction((e) -> {
            // displaying confirmation window
            boolean removeConfirmed = this.removeRunwayButton.showConfirmationWindow(formname);

            // removing if the user confirmed the removal
            if(removeConfirmed){
                // removing the runway from the airport
                SystemController.removeAirportRunway(this.selectedAirport, this.selectedRunway); // NEW WAY
                this.runwaySelection.setItems(this.selectedAirport.getRunways());

                // resetting the controls
                this.selectedRunway = null;
                this.runwaySelection.getSelectionModel().clearSelection();
                this.editRunwayButton.setDisable(true);
                this.removeRunwayButton.setDisable(true);
            }
        });

        // adding new runway functionality
        this.addNewRunwayButton.setOnAction((e) -> {
            // opening a new dialog for the runway input form
            AddPhysicalRunwayForm addPhysicalRunwayForm = new AddPhysicalRunwayForm(this);
            addPhysicalRunwayForm.initOwner(this.getScene().getWindow());
            addPhysicalRunwayForm.show();
        });

        // scroll container
        ScrollPane scrollContainer = new ScrollPane(form);
        scrollContainer.setFitToWidth(true);

        // adding main container to window
        super.getContainer().setCenter(scrollContainer);
    }

    /**
     * Attempts to update the selected airport with the given values.
     * @return True if the airport was updated correctly, false if not.
     */
    private boolean updateSelectedAirport(){
        // only updating if an airport is selected
        if(this.selectedAirport != null){
            // updating selected airport
            try {
                // testing if the input is valid
                Validator validator = new Validator();
                validator.validateAirportEdit(this.selectedAirport, this.airportNameTextField.getText(), this.airportIATATextField.getText(), this.newRunways);

                // input valid

                // edting airport details
                SystemController.editAirport(this.selectedAirport, this.airportNameTextField.getText(), this.airportIATATextField.getText());

                // adding new runways to airport
                for(PhysicalRunway runway : this.newRunways){
                    SystemController.addAirportRunway(this.selectedAirport, runway); // NEW WAY
                }

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
        else {
            return true;
        }
    }

    /**
     * Method run when a new physical runway is submitted. The new runway is added to the list of
     * current runways, and shown in the input form.
     * @param newRunway The new runway to be added to the
     */
    public void addNewPhysicalRunway(PhysicalRunway newRunway){
        // adding the new runway to the list
        this.newRunways.add(newRunway);

        // creating a label and button for the runway
        Label newRunwayLabel = new Label(newRunway.getName());
        ConfirmationButton removeButton = new ConfirmationButton("Discard", "Discard New Runway", "Are you sure you want to discard this new runway?");

        // placing label and button in container
        HBox container = new HBox();
        container.getChildren().addAll(newRunwayLabel, removeButton);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        // adding container to list of runways
        this.newRunwaysList.getChildren().add(container);

        // adding functionality to remove button
        removeButton.setOnAction((e) -> {
            // displaying confirmation window
            boolean removeConfirmed = removeButton.showConfirmationWindow(formname);

            // removing if the user confirmed the removal
            if(removeConfirmed){
                // removing the runway
                this.newRunwaysList.getChildren().remove(container);
                this.newRunways.remove(newRunway);
            }
        });
    }

    /**
     * Method run when a physical runway is edited. The new runway is submitted to the system controller to be changed.
s     * @param newRunway The new runway that the old runway needs to be changed to.
     */
    public void editPhysicalRunway(PhysicalRunway newRunway){
        // edit the runway through the system controller
        SystemController.editAirportRunway(this.selectedAirport, this.selectedRunway, newRunway);

        // re-select the airport runways so that the update is present.
        this.runwaySelection.setItems(null);
        this.runwaySelection.setItems(this.selectedAirport.getRunways());

        // resetting the controls
        this.selectedRunway = null;
        this.editRunwayButton.setDisable(true);
        this.removeRunwayButton.setDisable(true);
    }

    /**
     * Verifies the user's inputs are valid. Edit's the object's if they are, and displays an error message if they
     * are not.
     */
    public void submit() {
        // only closing if the selected airport was updated successfully.
        if(this.updateSelectedAirport()){
            this.close();
        }
    }
}
