package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * View that handles the display of both side-on and top-down runway views simultaneously.
 */
public class SimultaneousRunwayView extends BorderPane {

    public static SimultaneousRunwayView instance;

    // member variables
    private SideWayView sideWayView;
    private TopDownMap topDownMap;
    private VBox viewContainer;
    // static variables
    private static final double scaleFactor = 0.8;

    /**
     * Class Constructor
     */
    public SimultaneousRunwayView(SideWayView sideWayView, TopDownMap topDownMap){

        instance = this;
        // initialising member variables
        this.sideWayView = sideWayView;
        this.topDownMap = topDownMap;

        // changing the scale of the top down view
        this.topDownMap.setScaleX(scaleFactor);
        this.topDownMap.setScaleY(scaleFactor);

        // adding the views to a container
        viewContainer = new VBox();
        viewContainer.getChildren().addAll(this.sideWayView, this.topDownMap);
        viewContainer.setAlignment(Pos.CENTER);
        viewContainer.setSpacing(120);

        // formatting
        BorderPane.setAlignment(this.sideWayView, Pos.CENTER);

        // adding container and button to border pane
        this.setCenter(viewContainer);
        this.setBottom(topDownMap.getButtonTray());
    }

    public WritableImage focusedSnapshot() {
        SnapshotParameters param = new SnapshotParameters();
        param.setDepthBuffer(true);
        return viewContainer.snapshot(param, null);
    }
}
