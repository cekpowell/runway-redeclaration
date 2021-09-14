package View;

import Controller.RunwayDisplayer;
import Model.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
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
    private TabPane views; // houses the actual runway information (can change so need member variable for it)
    private Tab sideOnView;
    private Tab topDownView;
    private Tab simultaneousView;

    /**
     * Constructor for the class. Creates a new Runway View.
     */
    public RunwayView(Dashboard dashboard){
        this.dashboard = dashboard;
        this.views = new TabPane();
        this.sideOnView = new Tab("Side-On View");
        this.sideOnView.setClosable(false);
        this.topDownView = new Tab("Top-Down View");
        this.topDownView.setClosable(false);
        this.simultaneousView = new Tab("Simultaneous View");
        this.simultaneousView.setClosable(false);
        this.views.getTabs().addAll(this.sideOnView,this.topDownView,this.simultaneousView);

        // configuring title label
        Label titleLabel = new Label("RUNWAY VIEW");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        // adding main container to view
        this.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        this.setCenter(this.views);
        BorderPane.setAlignment(this.views, Pos.CENTER);
        this.setPadding(new Insets(20,20,20,20));

        // showing the initial view to the user
        this.displayInitialView();
    }

    /**
     * Displays the initial screen view to the user.
     */
    private void displayInitialView(){
        //this.setCenter(new TopDownMap());

        // initial Label
        Label initialLabel = new Label("Nothing to display!\nUse the Calculation Panel to perform a revision...");
        initialLabel.setFont((Font.font("Verdana", FontPosture.ITALIC, 15)));
        initialLabel.setTextAlignment(TextAlignment.CENTER);

        // initial container to house the initial label
        VBox initialContainer = new VBox(initialLabel);
        initialContainer.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(initialContainer, Pos.CENTER);

        // adding initial label to the border pane
        this.setCenter(initialContainer);
    }

    /**
     * Displays the given logical runway and revised logical runway onto the view.
     * @param revisedLogicalRunway The revised logical runway to be displayed.
     */
    public void displayLogicalRunwayRevision(LogicalRunway originalRunway, RevisedLogicalRunway revisedLogicalRunway){
        // creating new instance of the side-on view
        SideWayView sideWayView = new SideWayView(originalRunway,revisedLogicalRunway, this.getWidth(), this.getHeight(), true);
        this.sideOnView.setContent(sideWayView);

        // creating new instance of top-down view
        TopDownMap topDownMap = new TopDownMap(originalRunway, revisedLogicalRunway, this.getWidth()/1.25, this.getHeight()/1.25);
        this.topDownView.setContent(topDownMap);

        // creating new views for simultaneous tab
        SideWayView sideWayViewSimultaneous = new SideWayView(originalRunway,revisedLogicalRunway, this.getWidth()/1.25, this.getHeight()/3, false);
        TopDownMap topDownMapSimultaneous = new TopDownMap(originalRunway, revisedLogicalRunway, this.getWidth()/1.5, this.getHeight()/1.5);

        SimultaneousRunwayView simultaneousRunwayView = new SimultaneousRunwayView(sideWayViewSimultaneous, topDownMapSimultaneous);
        this.simultaneousView.setContent(simultaneousRunwayView);

        // adding tabpane to border pane
        this.setCenter(this.views);
    }
}