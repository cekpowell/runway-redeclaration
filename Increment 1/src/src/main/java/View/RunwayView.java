package View;

import Controller.RunwayDisplayer;
import Model.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

/**
 * The Runway view section of the dashboard.
 */
public class RunwayView extends BorderPane {

    private Dashboard dashboard;
    private VBox runwayContainer; // houses the actual runway information (can change so need member variable for it)

    /**
     * Constructor for the class. Creates a new Runway View.
     */
    public RunwayView(Dashboard dashboard){
        this.dashboard = dashboard;
        this.runwayContainer = new VBox();

        // configuring title label
        Label titleLabel = new Label("RUNWAY VIEW");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        // adding main container to view
        this.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        this.setCenter(this.runwayContainer);
        BorderPane.setAlignment(this.runwayContainer, Pos.CENTER);
        this.setPadding(new Insets(20,20,20,20));

        // showing the initial view to the user
        this.displayInitialView();
    }

    /**
     * Displays the initial screen view to the user.
     */
    private void displayInitialView(){
        // initial Label
        Label initialLabel = new Label("Nothing to display!\nUse the Calculation Panel to perform a revision...");
        initialLabel.setFont((Font.font("Verdana", FontPosture.ITALIC, 15)));
        initialLabel.setTextAlignment(TextAlignment.CENTER);
        //initialLabel.setFill(Color.GREY);

        // adding initial label to the view
        this.runwayContainer.getChildren().add(initialLabel);
        this.runwayContainer.setAlignment(Pos.CENTER);
    }

    /**
     * Displays the given logical runway and revised logical runway onto the view.
     * @param logicalRunway The logical runway to be displayed.
     * @param revisedLogicalRunway The revised logical runway to be displayed.
     */
    public void displayLogicalRunwayRevision(LogicalRunway logicalRunway, RevisedLogicalRunway revisedLogicalRunway){
        // removing old stuff from runway container
        this.runwayContainer.getChildren().clear();

        // LOGICAL RUNWAY DISPLAY

        // logical runway view label
        Label logicalRunwayLabel = new Label("Original Runway");
        logicalRunwayLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        logicalRunwayLabel.setUnderline(true);

        // getting the logical runway display
        Node logicalRunwayDisplay = RunwayDisplayer.getDisplayOfLogicalRunway(logicalRunway);

        // container for logical runnway
        VBox logicalRunwayView = new VBox();
        logicalRunwayView.setAlignment(Pos.TOP_CENTER);
        logicalRunwayView.setSpacing(25);
        logicalRunwayView.getChildren().addAll(logicalRunwayLabel, logicalRunwayDisplay);

        // REVISED LOGICAL RUNWAY

        // revised logical runway view label
        Label revisedlLogicalRunwayLabel = new Label("Revised Runway");
        revisedlLogicalRunwayLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        revisedlLogicalRunwayLabel.setUnderline(true);

        // getting the revised logical runway display
        Node revisedLogicalRunwayDisplay = RunwayDisplayer.getDisplayOfRevisedLogicalRunway(logicalRunway, revisedLogicalRunway);

        // container for revised logical runway
        VBox revisedLogicalRunwayView = new VBox();
        revisedLogicalRunwayView.setAlignment(Pos.TOP_CENTER);
        revisedLogicalRunwayView.setSpacing(25);
        revisedLogicalRunwayView.getChildren().addAll(revisedlLogicalRunwayLabel, revisedLogicalRunwayDisplay);

        // ADDING LOGICAL AND REVISED LOGICAL INTO CONTAINER

        // separator for the two runway views
        Separator runwaySeparator = new Separator();
        runwaySeparator.setOrientation(Orientation.VERTICAL);

        // supplementary container for the displays
        HBox runwayDisplays = new HBox();
        runwayDisplays.getChildren().addAll(logicalRunwayView, runwaySeparator, revisedLogicalRunwayView);
        runwayDisplays.setAlignment(Pos.TOP_CENTER);
        runwayDisplays.setSpacing(50);

        // scroll pane for the runway displays
        //ScrollPane scrollRunwayContainer = new ScrollPane(runwayDisplays);
        //scrollRunwayContainer.setPadding(new Insets(20, 20, 20, 20));
        //scrollRunwayContainer.setFitToWidth(true);

        // adding scroll pane to runway view
        this.runwayContainer.getChildren().add(runwayDisplays);
    }
}