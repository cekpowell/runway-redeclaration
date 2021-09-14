package View;

import Controller.SystemController;
import Model.Notification;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main dashboard within the system.
 */
public class Dashboard extends BorderPane {

    // member variables
    private CalculationPanel calculationPanel;
    private RunwayView runwayView;
    private CalculationBreakdownView calculationBreakdownView;
    private NotificationPanel notificationPanel;

    /**
     * Constructor for the class. Creates a new dashboard.
     * @param titleName The project title.
     * @param authorName The project author.
     */
    public Dashboard(String titleName, String authorName) {
        // initialising member variables
        this.calculationPanel = new CalculationPanel(this);
        this.runwayView = new RunwayView(this);
        this.calculationBreakdownView = new CalculationBreakdownView(this);


        if(SystemController.existingNotifications){
            this.notificationPanel = new NotificationPanel(this);
            for(Notification n:SystemController.existingnoti){
                this.notificationPanel.displayNotification(n);
            }
        }
        else {
            this.notificationPanel = new NotificationPanel(this);
        }


        // adding componets to dashboard
        this.setLeft(this.calculationPanel);
        this.setCenter(this.runwayView);
        this.setRight(this.calculationBreakdownView);
        this.setBottom(this.notificationPanel);
    }

    /**
     * Getters.
     */

    public CalculationPanel getCalculationPanel() {
        return this.calculationPanel;
    }

    public RunwayView getRunwayView() {
        return this.runwayView;
    }

    public CalculationBreakdownView getCalculationBreakdownView() {
        return this.calculationBreakdownView;
    }

    public NotificationPanel getNotificationPanel() {
        return this.notificationPanel;
    }
}

