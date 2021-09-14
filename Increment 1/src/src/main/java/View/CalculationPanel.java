package View;

import Controller.SystemController;
import Controller.Validator;
import Model.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 * Represents the calculation panel within the application.
 */
public class CalculationPanel extends FlowPane {

    private Dashboard dashboard;
    private ComboBox airportSelection;
    private ComboBox physicalRunwaySelection;
    private ComboBox logicalRunwaySelection;
    private ComboBox obstacleSelection;
    private ToggleGroup position;
    private RadioButton leftPosition;
    private RadioButton rightPosition;
    private TextField distanceFromThreshold;
    private TextField distanceFromCentreLine;
    private ComboBox flightMethodSelection;
    private Button performRevision;

    /**
     * Constructor for the class. Initialises a new calculation panel.
     */
    public CalculationPanel(Dashboard dashboard){
        // initialising member variables
        this.dashboard = dashboard;
        this.airportSelection = new ComboBox();
        this.physicalRunwaySelection = new ComboBox();
        this.logicalRunwaySelection = new ComboBox();
        this.obstacleSelection = new ComboBox();
        this.position = new ToggleGroup();
        this.leftPosition = new RadioButton("LEFT");
        this.leftPosition.setToggleGroup(this.position);
        this.leftPosition.setUserData("L");
        this.rightPosition = new RadioButton("RIGHT");
        this.rightPosition.setToggleGroup(this.position);
        this.rightPosition.setUserData("R");
        this.distanceFromThreshold = new TextField();
        this.distanceFromCentreLine = new TextField();
        this.flightMethodSelection = new ComboBox();
        this.performRevision = new Button("PERFORM REVISION");

        // initialising labels for the control panel
        Label titleLabel = new Label("CALCULATION PANEL");
        Label airportLabel = new Label("AIRPORT");
        Label physicalRunwayLabel = new Label("PHYSICAL RUNWAY");
        Label logicalRunwayLabel = new Label("LOGICAL RUNWAY");
        Label obstacleLabel = new Label("OBSTACLE");
        Label positionLabel = new Label("L/R FROM CENTRE LINE ");
        Label distanceFromThresholdLabel = new Label("DISTANCE FROM THRESHOLD" + " (m)");
        Label distanceFromCentreLineLabel = new Label("DISTANCE FROM CENTRE LINE" + " (m)");
        Label flightMethodSelectionLabel = new Label("FLIGHT METHOD");

        // formatting the labels
        // TODO... Formatting should be done with CSS file...
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        airportLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        physicalRunwayLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        logicalRunwayLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        obstacleLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        positionLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        distanceFromThresholdLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        distanceFromCentreLineLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        flightMethodSelectionLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));

        // creating suplementary containers
        HBox positionsContainer = new HBox();
        positionsContainer.getChildren().addAll(this.leftPosition, this.rightPosition);
        positionsContainer.setAlignment(Pos.CENTER);
        positionsContainer.setSpacing(10);

        // adding controls to the panel
        this.getChildren().addAll(titleLabel,
                                      airportLabel,
                                      this.airportSelection,
                                      physicalRunwayLabel,
                                      this.physicalRunwaySelection,
                                      logicalRunwayLabel,
                                      this.logicalRunwaySelection,
                                      obstacleLabel,
                                      this.obstacleSelection,
                                      distanceFromThresholdLabel,
                                      this.distanceFromThreshold,
                                      positionLabel,
                                      positionsContainer,
                                      distanceFromCentreLineLabel,
                                      this.distanceFromCentreLine,
                                      flightMethodSelectionLabel,
                                      this.flightMethodSelection,
                                      this.performRevision);

        // formatting the control panel
        this.setAlignment(Pos.CENTER);
        this.setColumnHalignment(HPos.CENTER);
        this.setVgap(10);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setOrientation(Orientation.VERTICAL);


        // adding contents to the combo boxes
        this.airportSelection.setOnAction((e) -> {
            Airport selected = (Airport) this.airportSelection.getValue();
            this.physicalRunwaySelection.setItems(selected.getRunways());
        });

        this.physicalRunwaySelection.setOnAction((e) -> {
            PhysicalRunway physelected = (PhysicalRunway) this.physicalRunwaySelection.getValue();
            this.logicalRunwaySelection.setItems(physelected.getLogicalRunways());
        });

        this.airportSelection.setItems(SystemController.getAirports());
        this.obstacleSelection.setItems(SystemController.getObstacles());
        this.flightMethodSelection.getItems().setAll(FlightMethod.values());


        // configuring the perform calcualtion button
        this.performRevision.setOnAction((e) -> {
            try{
                // gathering the objects that have been selected (needed for validation)
                Airport airport = (Airport) this.airportSelection.getValue();
                PhysicalRunway physicalRunway = (PhysicalRunway) this.physicalRunwaySelection.getValue();
                LogicalRunway logicalRunway = (LogicalRunway) this.logicalRunwaySelection.getValue();
                Obstacle obstacle = (Obstacle) this.obstacleSelection.getValue();
                Toggle position = (Toggle) this.position.getSelectedToggle();
                FlightMethod flightMethod = (FlightMethod) this.flightMethodSelection.getValue();

                // validating input
                Validator validator = new Validator();
                validator.validateCalculationPanelInput(airport,
                                                        physicalRunway,
                                                        logicalRunway,
                                                        obstacle,
                                                        this.distanceFromThreshold.getText(),
                                                        position,
                                                        this.distanceFromCentreLine.getText(),
                                                        flightMethod);

                // input valid - creating the objects needed for the calculation
                double distanceFromThreshold = Double.valueOf(this.distanceFromThreshold.getText());
                double distanceFromCenterLine;
                if(this.position.getSelectedToggle() == this.rightPosition){
                    distanceFromCenterLine = Double.valueOf(this.distanceFromCentreLine.getText());
                }
                else{
                    distanceFromCenterLine =  - Double.valueOf(this.distanceFromCentreLine.getText());
                }
                ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCenterLine);

                // performing the calculation via the system controller
                SystemController.performRunwayRevision(this.dashboard,
                                                       logicalRunway,
                                                       obstacle,
                                                       obstaclePosition,
                                                       flightMethod);
            }
            catch(ValidationException exception){
                // Display error message as inputs not valid
                ErrorAlert errorAlert = new ErrorAlert(exception);
                errorAlert.showAndWait();
            }
        });
    }
}
