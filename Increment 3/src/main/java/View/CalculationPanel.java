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

    // member variables
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
        this.airportSelection.setId("airportselect");
        this.physicalRunwaySelection = new ComboBox();
        this.physicalRunwaySelection.setId("runwayselection");
        this.logicalRunwaySelection = new ComboBox();
        this.logicalRunwaySelection.setId("logicalselection");
        this.obstacleSelection = new ComboBox();
        this.obstacleSelection.setId("obstacleselect");
        this.position = new ToggleGroup();
        this.leftPosition = new RadioButton("LEFT");
        this.leftPosition.setId("l");
        this.leftPosition.setToggleGroup(this.position);
        this.leftPosition.setUserData("L");
        this.rightPosition = new RadioButton("RIGHT");
        this.rightPosition.setId("r");
        this.rightPosition.setToggleGroup(this.position);
        this.rightPosition.setUserData("R");
        this.distanceFromThreshold = new TextField();
        this.distanceFromThreshold.setId("threshold");
        this.distanceFromCentreLine = new TextField();
        this.distanceFromCentreLine.setId("center");
        this.flightMethodSelection = new ComboBox();
        this.flightMethodSelection.setId("flightmethod");
        this.performRevision = new Button("PERFORM REVISION");
        this.performRevision.setId("performrevision");

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

        // disabling combo-boxes at start
        this.airportSelection.setDisable(SystemController.getAirports().isEmpty());
        this.physicalRunwaySelection.setDisable(true);
        this.logicalRunwaySelection.setDisable(true);
        this.obstacleSelection.setDisable(SystemController.getObstacles().isEmpty());

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
        this.setPadding(new Insets(20, 0, 0, 20));
        this.setOrientation(Orientation.VERTICAL);


        // adding action to combo boxes //

        this.airportSelection.setOnAction((e) -> {
            // clearing previous selection
            this.physicalRunwaySelection.setItems(null);
            this.logicalRunwaySelection.setItems(null);

            this.physicalRunwaySelection.setDisable(true);
            this.logicalRunwaySelection.setDisable(true);

            Airport selectedAirport = (Airport) this.airportSelection.getValue();

            // handling case where there is no selection
            if(selectedAirport != null) {
                if (selectedAirport.getRunways() != null) {
                    this.physicalRunwaySelection.setItems(selectedAirport.getRunways());

                    // disabling/enabling combo box
                    if(selectedAirport.getRunways().isEmpty()){
                        this.physicalRunwaySelection.setDisable(true);
                    }
                    else{
                        this.physicalRunwaySelection.setDisable(false);
                    }
                } else {
                    // clearing the logical runway selection
                    this.logicalRunwaySelection.setItems(null);
                }
            }
        });

        this.physicalRunwaySelection.setOnAction((e) -> {
            PhysicalRunway physelected = (PhysicalRunway) this.physicalRunwaySelection.getValue();
            if(physelected != null){
                this.logicalRunwaySelection.setDisable(false);
                this.logicalRunwaySelection.setItems(physelected.getLogicalRunways());
            }
            else{
                this.logicalRunwaySelection.setItems(null);
            }
        });

        this.logicalRunwaySelection.setOnAction(e -> {
            LogicalRunway runway = (LogicalRunway) this.logicalRunwaySelection.getValue();
            //TopDownMap.setRunway(runway);
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
                Validator inputValidator = new Validator();
                inputValidator.validateCalculationPanelInput(airport,
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

                // validating result (making sure result is not negative)
                Validator calculationValidator = new Validator();
                calculationValidator.validateCalculationPanelResult(logicalRunway, obstacle, obstaclePosition, flightMethod);

                // calculation result valid - performing the calculation via the system controller
                SystemController.performRunwayRevision(this.dashboard,
                                                       airport,
                                                       logicalRunway,
                                                       obstacle,
                                                       obstaclePosition,
                                                       flightMethod);
                //TopDownMap.setNewDisplacement();
            }
            catch(ValidationException ex){
                // Display error message as inputs not valid
                ErrorAlert errorAlert = new ErrorAlert(ex);
                Button butt = (Button) errorAlert.getDialogPane().lookupButton(ButtonType.OK);
                butt.setId("calculationpanel"+"error");
                errorAlert.showWindow(this.getScene().getWindow());
            }
        });
    }

    /**
     * Refreshes the selection of items within the calculation panel. Used when the objects have been edited, and so the
     * changes will only be observed if the selections are refreshed.
     */
    public void refreshSelections(){
        // clearing the current selection
        this.airportSelection.getSelectionModel().clearSelection();
        this.physicalRunwaySelection.getSelectionModel().clearSelection();
        this.logicalRunwaySelection.getSelectionModel().clearSelection();
        this.obstacleSelection.getSelectionModel().clearSelection();

        // disabling combo boxes
        this.airportSelection.setDisable(SystemController.getAirports().isEmpty());
        this.physicalRunwaySelection.setDisable(true);
        this.logicalRunwaySelection.setDisable(true);
        this.obstacleSelection.setDisable(SystemController.getObstacles().isEmpty());

        // clearing the phsyical and logical runway boxes
        this.physicalRunwaySelection.setItems(null);
        this.logicalRunwaySelection.setItems(null);

        // refreshing the airports and obstacles
        this.airportSelection.setItems(SystemController.getAirports());
        this.obstacleSelection.setItems(SystemController.getObstacles());
    }
}
