package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;

/**
 * The section of the application that displays a breakdown of the runway re-declaration calculations.
 */
public class CalculationBreakdownView extends VBox {

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
        this.setPadding(new Insets(20, 20, 20, 20));
    }

    public void displayCalculationBreakdown(Node calculationBreakdown){
        this.calculationBreakdown.setContent(calculationBreakdown);
    }
}
