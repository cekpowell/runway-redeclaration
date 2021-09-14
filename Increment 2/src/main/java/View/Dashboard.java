package View;

import javafx.scene.layout.BorderPane;

/**
 * The main dashboard within the system.
 */
public class Dashboard extends BorderPane {

    // member variables
    private Title title;
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
        this.title = new Title(titleName, authorName);
        this.calculationPanel = new CalculationPanel(this);
        this.runwayView = new RunwayView(this);
        this.calculationBreakdownView = new CalculationBreakdownView(this);
        this.notificationPanel = new NotificationPanel(this);

        // adding componets to dashboard
        this.setTop(this.title);
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

