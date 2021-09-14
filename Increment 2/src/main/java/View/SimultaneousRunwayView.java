package View;

import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;

/**
 * View that handles the display of both side-on and top-down runway views simultaneously.
 */
public class SimultaneousRunwayView extends VBox {

    // member variables
    private SideWayView sideWayView;
    private TopDownMap topDownMap;

    // static variables
    private static final double scaleFactor = 0.8;

    /**
     * Class Constructor
     */
    public SimultaneousRunwayView(SideWayView sideWayView, TopDownMap topDownMap){
        // initialising member variables
        this.sideWayView = sideWayView;
        this.topDownMap = topDownMap;

        // changing the scale of the side on view
        //this.sideWayView.setScaleX(scaleFactor);
        //this.sideWayView.setScaleY(scaleFactor);

        // changing the scale of the top down view
        this.topDownMap.setScaleX(scaleFactor);
        this.topDownMap.setScaleY(scaleFactor);

        // adding the views to the container
        this.getChildren().addAll(this.sideWayView, this.topDownMap);

        // formatting
        this.setAlignment(Pos.CENTER);
        this.setSpacing(130);
    }
}
