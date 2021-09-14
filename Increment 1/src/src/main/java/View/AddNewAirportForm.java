package View;

import Controller.SystemController;
import Controller.Validator;
import Model.Airport;
import Model.PhysicalRunway;
import Model.Position;
import Model.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Form that allows the user to add a new airport to the system.
 */
public class AddNewAirportForm extends Alert{

    // declaring the button types of the alert window
    private static final ButtonType add = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    private TextField name;
    private TextField IATA;
    private TextField xPos;
    private TextField yPos;
    private Button addPhysicalRunwayButton;
    private ArrayList<PhysicalRunway> physicalRunways;
    private Boolean noRunwaysAdded;

    /**
     * Constructor for the class. Creates a new form.
     */
    public AddNewAirportForm(){
        // setting up the window

        super(Alert.AlertType.CONFIRMATION, "", add, cancel);

        // configuring the dialog pane
        this.setTitle("Add New Airport");
        this.setHeaderText("Add New Airport");
        this.getDialogPane().setPrefSize(700,600);

        // initialising member variables
        this.name = new TextField();
        this.IATA = new TextField();
        this.xPos = new TextField();
        this.yPos = new TextField();

        this.name.setMaxWidth(300);
        this.IATA.setMaxWidth(300);
        this.xPos.setMaxWidth(300);
        this.yPos.setMaxWidth(300);

        this.physicalRunways = new ArrayList<PhysicalRunway>();
        this.addPhysicalRunwayButton = new Button("Add New Runway");

        // creating labels for the form
        Label airportNameLabel = new Label("Airport Name: ");
        Label IATALabel = new Label("IATA: ");
        Label xPosLabel = new Label("x Coordinate: ");
        Label yPosLabel = new Label("y Coordinate: ");
        Label runwaysLabel = new Label("Runways: ");

        // formatting the labels
        airportNameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        IATALabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        xPosLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        yPosLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        runwaysLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));

        // supplementary container for runways
        //ScrollPane runwaysScrollContainer = new ScrollPane();
        VBox runwaysContainer = new VBox();
        runwaysContainer.setAlignment(Pos.CENTER);
        runwaysContainer.setSpacing(10);

        //runwaysScrollContainer.setContent(runwaysContainer);

        // adding functionality to 'addNewRunway' button
        addPhysicalRunwayButton.setOnAction(((e) -> {
            // opening the add new runway form
            AddNewPhysicalRunwayForm addNewRunwayForm = new AddNewPhysicalRunwayForm();
            PhysicalRunway newRunway = addNewRunwayForm.showWindow();

            // testing if a runway was returned and adding it to the list if it was
            if(!(newRunway == null)){
                // adding the new runway to the list, and to the forms
                this.physicalRunways.add(newRunway); // adding new runway to the list
                Label newRunwayLabel = new Label(newRunway.getName());
                runwaysContainer.getChildren().add(newRunwayLabel);
            }
        }));

        // adding items to form
        VBox container = new VBox();
        container.getChildren().addAll( airportNameLabel,
                                        this.name,
                                        IATALabel,
                                        this.IATA,
                                        //xPosLabel, // NOT NEEDED FOR FIRST INCREMENT
                                        //this.xPos, // NOT NEEDED FOR FIRST INCREMENT
                                        //yPosLabel, // NOT NEEDED FOR FIRST INCREMENT
                                        //this.yPos, // NOT NEEDED FOR FIRST INCREMENT
                                        runwaysLabel,
                                        runwaysContainer,
                                        addPhysicalRunwayButton
        );
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(10);

        // adding main container to scroll pane
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);

        // setting content of the alert
        this.getDialogPane().setContent(scrollPane);
    }

    /**
     * Displays the form to the screen as a pop-up window, displays errors if there are any with the user input.
     */
    public void showWindow(){
        Optional<ButtonType> result = this.showAndWait();

        // dealing with case where user presses to add object
        if(result.get() == add){
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


            } catch(ValidationException e) {
                // validation error occured, so need to display error message
                ErrorAlert errorAlert = new ErrorAlert(e);
                errorAlert.showAndWait();
            }
        }
    }
}
