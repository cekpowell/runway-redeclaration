package View;

import Model.FlightMethod;
import Model.LogicalRunway;
import Model.RevisedLogicalRunway;
import Utils.AngleDelta;
import Utils.TopDownViewDetails;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TopDownMap extends BorderPane {

    private final Rectangle runwayRep;
    private final Group root;
    private Text[] runwayLabels;

    private boolean dragDisabled = false;
    private double rot = 0;

    private AngleDelta angleDelta;

    private static LogicalRunway runway;
    private final RevisedLogicalRunway revisedRunway;

    private double maxHeightScale;

    void addCreatedRec(double runWayLen) {
        double startX = runwayRep.getX() + runwayRep.getWidth();
        double startY = runwayRep.getY();

        double clearWayLen = runWayLen * (revisedRunway.getParameters().getToda() - revisedRunway.getParameters().getTora()) / runway.getParameters().getTora();

        double stopWayLen = runWayLen * (revisedRunway.getParameters().getAsda() - revisedRunway.getParameters().getTora()) / runway.getParameters().getTora();

        Rectangle stopWayTo = new Rectangle(startX, startY, stopWayLen, runwayRep.getHeight());
        stopWayTo.setFill(Color.TRANSPARENT);
        stopWayTo.setStroke(Color.BLACK);

        double height = clearWayLen == 0 ? 0 : runwayRep.getHeight() * 2;
        Rectangle clearWay = new Rectangle(startX, startY - runwayRep.getHeight() / 2, clearWayLen, height);
        clearWay.setFill(Color.TRANSPARENT);
        clearWay.setStroke(Color.GRAY);

        Rectangle stopWayFrom = new Rectangle(runwayRep.getX() - stopWayLen, startY, stopWayLen, runwayRep.getHeight());
        stopWayFrom.setFill(Color.TRANSPARENT);
        stopWayFrom.setStroke(Color.BLACK);

        root.getChildren().addAll(stopWayTo, stopWayFrom, clearWay);
    }

    void reorientLabels() {
        if(rot > 90 && rot < 270) {
            for(Text t : runwayLabels) {
                t.setRotate(180);
            }
        }
        else {
            for(Text t : runwayLabels) {
                t.setRotate(0);
            }
        }
    }

    void setUpScaling(double width, double height) {
        maxHeightScale = height / width;
        System.out.println(maxHeightScale);
    }

    void provisionalZoomForRotation() {
        double normRot = (rot + 90) % 180;
        double diffRot = 90 - Math.abs(normRot - 90);

        double scaleRot = diffRot / 90;

        double scale = maxHeightScale + scaleRot * (1 - maxHeightScale);
        root.setScaleX(scale);
        root.setScaleY(scale);

        System.out.println("SC ->" + scale);
    }

    void gradeArea() {
        double runwayPos = runwayRep.getX();
        double runwayLen = runwayRep.getWidth();

        Rectangle grade = new Rectangle(runwayPos, 150, 0, runwayRep.getHeight());
        grade.setFill(Color.DARKSALMON);

        addCreatedRec(runwayRep.getWidth());

        if(revisedRunway.getFlightMethod() == FlightMethod.LANDING_OVER) {
            double fullLen = runway.getParameters().getTora();
            double displacement = revisedRunway.getParameters().getLda();
            double recalculatedWidth = runwayLen * displacement / fullLen;
            if(fullLen == 0)
                recalculatedWidth = runwayLen;
            double offset = runwayLen - recalculatedWidth;
            grade.setX(offset + grade.getX());
            grade.setWidth(recalculatedWidth);

            double yOffset = grade.getY() - 10;

            System.out.println();

            runwayLabels = new Text[5];

            TopDownArrow arrow = new TopDownArrow(grade.getX(), grade.getX() + recalculatedWidth, yOffset, yOffset, grade.getY());
            Text label = new Text();
            label.setText(String.format("LDA %dm", (int) displacement));
            label.setX(grade.getX() + recalculatedWidth / 2);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[0] = label;

            double obsLen = runwayLen * (revisedRunway.getObstacle().getWidth() / runway.getParameters().getTora());
            double obsPos = grade.getX() - obsLen;
            arrow = new TopDownArrow(obsPos, obsPos + obsLen, yOffset, yOffset, grade.getY());
            label = new Text();
            label.setText(String.format("%dm", (int) revisedRunway.getObstacle().getWidth()));
            label.setX(obsPos);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[1] = label;

            double resaLen = runwayLen * (revisedRunway.getResaDistance() / runway.getParameters().getTora());
            double resaPos = obsPos - resaLen;
            arrow = new TopDownArrow(resaPos, resaPos + resaLen, yOffset, yOffset, grade.getY());
            label = new Text();
            label.setText(String.format("RESA %dm", (int) revisedRunway.getResaDistance()));
            label.setX(resaPos);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[2] = label;

            yOffset -= 25;

            arrow = new TopDownArrow(runwayRep.getX(), runwayRep.getX() + runwayRep.getWidth(), yOffset, yOffset, runwayRep.getY());
            label = new Text();
            label.setText(String.format("TORA %dm", (int) fullLen));
            label.setX(runwayRep.getX() + runwayLen / 2);
            label.setY(yOffset - 10);

            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[3] = label;

            yOffset -= 25;

            double todaLen = runwayLen * (revisedRunway.getParameters().getToda() / revisedRunway.getParameters().getTora());
            arrow = new TopDownArrow(runwayRep.getX(), runwayRep.getX() + todaLen, yOffset, yOffset, runwayRep.getY() - runwayRep.getHeight() / 2);
            label = new Text();
            label.setText(String.format("TORA %dm", (int) revisedRunway.getParameters().getToda()));
            label.setX(runwayRep.getX() + todaLen / 2);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[4] = label;
        }
        else if(revisedRunway.getFlightMethod() == FlightMethod.LANDING_TOWARDS) {
            double fullLen = runway.getParameters().getTora();
            double displacement = revisedRunway.getParameters().getLda();
            double recalculatedWidth = runwayLen * displacement / fullLen;
            if(fullLen == 0)
                recalculatedWidth = runwayLen;
            double offset = runwayLen - recalculatedWidth;
            grade.setX(grade.getX());
            grade.setWidth(recalculatedWidth);

            double yOffset = grade.getY() - 10;

            System.out.println();

            runwayLabels = new Text[5];

            TopDownArrow arrow = new TopDownArrow(grade.getX(), grade.getX() + recalculatedWidth, yOffset, yOffset, grade.getY());
            Text label = new Text();
            label.setText(String.format("LDA %dm", (int) displacement));
            label.setX(grade.getX() + recalculatedWidth / 2);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[0] = label;

            double obsLen = runwayLen * (revisedRunway.getObstacle().getWidth() / runway.getParameters().getTora());
            double obsPos = grade.getX() + recalculatedWidth;
            arrow = new TopDownArrow(obsPos, obsPos + obsLen, yOffset, yOffset, grade.getY());
            label = new Text();
            label.setText(String.format("%dm", (int) revisedRunway.getObstacle().getWidth()));
            label.setX(grade.getX() + recalculatedWidth);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[1] = label;

            double resaLen = runwayLen * (revisedRunway.getResaDistance() / runway.getParameters().getTora());
            double resaPos = obsPos + obsLen;
            arrow = new TopDownArrow(resaPos, resaPos + resaLen, yOffset, yOffset, grade.getY());
            label = new Text();
            label.setText(String.format("RESA %dm", (int) revisedRunway.getResaDistance()));
            label.setX(resaPos + resaLen / 2);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[2] = label;

            /*
            double resaLength = 500f * revisedRunway.getResaDistance() / fullLen;

            arrow = new TopDownArrow(runwayRep.getX(), resaLength, yOffset, yOffset, grade.getY());
            label = new Text();
            label.setText(String.format("RESA %dm", (int) revisedRunway.getResaDistance()));
            label.setX(runwayRep.getX() + resaLength / 2);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            */

            yOffset -= 25;

            arrow = new TopDownArrow(runwayRep.getX(), runwayRep.getX() + runwayRep.getWidth(), yOffset, yOffset, runwayRep.getY());
            label = new Text();
            label.setText(String.format("TORA %dm", (int) fullLen));
            label.setX(runwayRep.getX() + runwayLen / 2);
            label.setY(yOffset - 10);

            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[3] = label;

            yOffset -= 25;

            double todaLen = runwayLen * (revisedRunway.getParameters().getToda() / revisedRunway.getParameters().getTora());
            arrow = new TopDownArrow(runwayRep.getX(), runwayRep.getX() + todaLen, yOffset, yOffset, runwayRep.getY() - runwayRep.getHeight() / 2);
            label = new Text();
            label.setText(String.format("TODA %dm", (int) revisedRunway.getParameters().getToda()));
            label.setX(runwayRep.getX() + todaLen / 2);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[4] = label;
        }
        else if(revisedRunway.getFlightMethod() == FlightMethod.TAKEOFF_TOWARDS) {
            double fullLen = runway.getParameters().getTora();
            double displacement = revisedRunway.getParameters().getTora();
            double recalculatedWidth = runwayLen * displacement / fullLen;
            if(fullLen == 0)
                recalculatedWidth = runwayLen;
            double offset = runwayLen - recalculatedWidth;
            grade.setWidth(recalculatedWidth);

            double yOffset = grade.getY() - 10;

            System.out.println();

            runwayLabels = new Text[4];

            TopDownArrow arrow = new TopDownArrow(grade.getX(), grade.getX() + recalculatedWidth, yOffset, yOffset, grade.getY());
            Text label = new Text();
            label.setText(String.format("TORA-TODA-ASDA %dm", (int) displacement));
            label.setX(grade.getX());
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[0] = label;

            double obsLen = runwayLen * (revisedRunway.getObstacle().getWidth() / runway.getParameters().getTora());
            double obsPos = grade.getX() + recalculatedWidth;
            arrow = new TopDownArrow(obsPos, obsPos + obsLen, yOffset, yOffset, grade.getY());
            label = new Text();
            label.setText(String.format("%dm", (int) revisedRunway.getObstacle().getWidth()));
            label.setX(grade.getX() + recalculatedWidth);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[1] = label;

            double resaLen = runwayLen * (revisedRunway.getResaDistance() / runway.getParameters().getTora());
            double resaPos = obsPos + obsLen;
            arrow = new TopDownArrow(resaPos, resaPos + resaLen, yOffset, yOffset, grade.getY());
            label = new Text();
            label.setText(String.format("RESA %dm", (int) revisedRunway.getResaDistance()));
            label.setX(resaPos + resaLen / 2);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[2] = label;

            yOffset -= 25;

            arrow = new TopDownArrow(runwayRep.getX(), runwayRep.getX() + runwayRep.getWidth(), yOffset, yOffset, runwayRep.getY());
            label = new Text();
            label.setText(String.format("OLD-TORA %dm", (int) fullLen));
            label.setX(runwayRep.getX() + runwayLen / 2);
            label.setY(yOffset - 10);

            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[3] = label;
        }
        else if(revisedRunway.getFlightMethod() == FlightMethod.TAKEOFF_AWAY) {
            double fullLen = runway.getParameters().getTora();
            double displacement = revisedRunway.getParameters().getTora();
            double recalculatedWidth = runwayLen * displacement / fullLen;
            if(fullLen == 0)
                recalculatedWidth = runwayLen;
            double offset = runwayLen - recalculatedWidth;
            grade.setX(grade.getX() + offset);
            grade.setWidth(recalculatedWidth);

            double yOffset = grade.getY() - 10;

            System.out.println();

            runwayLabels = new Text[3];

            TopDownArrow arrow = new TopDownArrow(grade.getX(), grade.getX() + recalculatedWidth, yOffset, yOffset, grade.getY());
            Text label = new Text();
            label.setText(String.format("TORA-TODA-ASDA %dm", (int) displacement));
            label.setX(grade.getX());
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[0] = label;

            double engineLen = runwayLen * (revisedRunway.getEngineBlastAllowance() / runway.getParameters().getTora());
            arrow = new TopDownArrow(grade.getX() - engineLen, grade.getX(), yOffset, yOffset, grade.getY());
            label = new Text();
            label.setText(String.format("Engine blast-off %dm", (int) revisedRunway.getEngineBlastAllowance()));
            label.setX(grade.getX() - engineLen - engineLen);
            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[1] = label;

            yOffset -= 25;

            arrow = new TopDownArrow(runwayRep.getX(), runwayRep.getX() + runwayRep.getWidth(), yOffset, yOffset, runwayRep.getY());
            label = new Text();
            label.setText(String.format("OLD-TORA %dm", (int) fullLen));
            label.setX(runwayRep.getX() + runwayLen / 2);
            label.setY(yOffset - 10);

            label.setY(yOffset - 10);

            root.getChildren().addAll(arrow, label);
            runwayLabels[2] = label;
        }
        root.getChildren().add(grade);
    }

    static Polygon drawTriangle(Point2D origin, double baseLength) {
        Polygon triangle = new Polygon(
                0d, 0d,
                (baseLength * Math.tan(120)), -baseLength,
                -(baseLength * Math.tan(120)), -baseLength
        );

        triangle.setLayoutX(origin.getX());
        triangle.setLayoutY(origin.getY());
        return triangle;
    }

    static double changeCo(double one, double two) {
        return one - two / 2;
    }

    static double normaliseHeading(double in) {
        return in * 10 - 90;
    }

    public TopDownMap(LogicalRunway original, RevisedLogicalRunway revisedLogicalRunway, double initWidth, double initHeight) {

        this.setMinSize(initWidth, initHeight);
        this.setPrefSize(initWidth, initHeight);
        //Sets runways for further use
        runway = original;
        revisedRunway = revisedLogicalRunway;

        //Adding all the elements to the path
        double runwayLen = initWidth * 8f / 10f; // according to calculations
        Rectangle rectangle = new Rectangle(runwayLen / 8, 150, runwayLen, 0.1 * initHeight);
        runwayRep = rectangle;

        setUpScaling(initWidth, initHeight);

        //Create arrow
        Polygon leftArrow = drawTriangle(new Point2D(25, 188), 25);

        leftArrow.setRotate(-90);

        Polygon rightArrow = drawTriangle(new Point2D(runwayLen + (runwayLen * 2f / 8f) - 25, 188), 25);
        rightArrow.setRotate(-90);
        //Creating a Group object
        // root = new Group(TopDownViewDetails.getGradedArea(rectangle), TopDownViewDetails.getClearedArea(rectangle), rectangle);
        root = new Group(TopDownViewDetails.getGradedArea(rectangle), TopDownViewDetails.getClearedArea(rectangle), rectangle, leftArrow, rightArrow);
        root.setScaleX(1);
        root.setScaleY(1);

        //root.setRotate(normaliseHeading(runway.getHeading()));
        root.setRotate(normaliseHeading(9));  // starting rotation of 90 degrees (sideways)



        gradeArea();

        HBox buttonTray = new HBox();
        Button setToTop = new Button("Normalize Rotation");
        setToTop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                double heading = normaliseHeading(9);
                root.setRotate(heading);
                rot = heading;
                reorientLabels();
                provisionalZoomForRotation();
            }
        });

        Button setToHeading = new Button("Rotate to Heading");
        setToHeading.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                double heading = normaliseHeading((float) runway.getHeading());
                root.setRotate(heading);
                rot = heading;
                reorientLabels();
                provisionalZoomForRotation();
            }
        });


        Button lockRotation = new Button("Lock drag rotation");

        lockRotation.setOnAction(e -> {
            dragDisabled = !dragDisabled;
            if(dragDisabled)
                lockRotation.setText("Unlock drag rotation");
            else
                lockRotation.setText("Lock drag rotation");
        });

        Button[] allButtons = {setToTop, setToHeading, lockRotation};

        for(Button button : allButtons) {
            button.setMaxWidth(Double.MAX_VALUE);
        }

        buttonTray.getChildren().addAll(setToHeading, setToTop, lockRotation);
        buttonTray.setAlignment(Pos.CENTER);
        buttonTray.setSpacing(20);
        setUpDrag(rectangle, root);

        this.setCenter(root);
        this.setBottom(buttonTray);
    }

    void setUpDrag(Rectangle rectangle, Group root) {

        Text[] headingMarkers = new Text[]{TopDownViewDetails.getHeading(rectangle, runway.getHeading(), 65, true),
                TopDownViewDetails.getHeading(rectangle, runway.getHeading(), 90, false)};


        root.getChildren().addAll(TopDownViewDetails.getLines(rectangle, 10,50, 2));
        root.getChildren().addAll(TopDownViewDetails.getLines(rectangle, (int)rectangle.getWidth() - 60,50, 2));
        root.getChildren().addAll(TopDownViewDetails.getLine(rectangle, 100));
        root.getChildren().addAll(headingMarkers[0]);
        root.getChildren().addAll(headingMarkers[1]);

        //Creating a scene object
        TopDownMap ref = this;
        //baby's first resize
        ref.widthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                root.relocate((newSceneWidth.doubleValue() - rectangle.getWidth()) / 2, (ref.getHeight() - rectangle.getHeight()) / 2);
            }
        });

        ref.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                root.relocate((ref.getWidth() - rectangle.getWidth()) / 2, (newSceneHeight.doubleValue() - rectangle.getHeight()) / 2);
            }
        });

        rot = normaliseHeading(runway.getHeading());
        reorientLabels();
        provisionalZoomForRotation();

        angleDelta = new AngleDelta(ref);
        ref.setOnMouseDragged(e -> {
            if(dragDisabled)
                return;
            root.setRotate(rot += angleDelta.calculateDeltaStep(e.getX(), e.getY()));
            if(rot < 0) {
                rot += 360;
            }
            rot %= 360;

            reorientLabels();
            provisionalZoomForRotation();
        });

        ref.setOnScroll(e -> {
            double curScale = root.getScaleX();
            if(e.getDeltaY() > 0) {
                curScale += 0.05;
            }
            else {
                curScale -= 0.05;
            }
            curScale = Math.max(0.55, curScale);
            curScale = Math.min(0.95, curScale);

            // currently disabled
            // root.setScaleX(curScale);
            // root.setScaleY(curScale);
            // System.out.println(curScale);
        });
    }
}  