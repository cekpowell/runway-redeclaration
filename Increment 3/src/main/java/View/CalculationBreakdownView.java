package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import java.io.File;

/**
 * The section of the application that displays a breakdown of the runway re-declaration calculations.
 */
public class CalculationBreakdownView extends VBox {

    // member variables
    private Dashboard dashboard;
    private ScrollPane calculationBreakdown;

    /**
     * Constructor for the class. Creates a new Calculation Breakdown view.
     */
    public CalculationBreakdownView(Dashboard dashboard){
        this.dashboard = dashboard;
        this.calculationBreakdown = new ScrollPane();

        // defining the title
        Label titleLabel = new Label("CALCULATION BREAKDOWN");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        this.calculationBreakdown.setMaxWidth(250);
        this.calculationBreakdown.setFitToWidth(true);
        VBox.setVgrow(this.calculationBreakdown, Priority.ALWAYS);

        // adding components to view
        this.getChildren().addAll(titleLabel, this.calculationBreakdown);

        // formatting
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(300);
        this.setPadding(new Insets(20, 20, 0, 0));
    }

    /**
     * Takes the calculation breakdown and displays it to the panel.
     * @param calculationBreakdown 
     */
    public void displayCalculationBreakdown(Node calculationBreakdown){
        this.calculationBreakdown.setContent(calculationBreakdown); // displaying the breakdown
    }

    /**
     * Getter for the calculation breakdown.
     * @return The Calculation breakdown.
     */
    public ScrollPane getCalculationBreakdown(){
        return this.calculationBreakdown;
    }
}
