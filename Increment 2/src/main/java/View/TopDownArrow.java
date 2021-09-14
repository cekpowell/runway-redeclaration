package View;

import javafx.scene.shape.Line;

import java.util.Optional;

/**
 * Extended Arrow class to have a line going to the base of the runway
 */
public class TopDownArrow extends Arrow {

    TopDownArrow(double startX,double endX, double startY, double endY, double baseY) {
        super(startX, endX, startY, endY, false, Optional.of(true));

        Line baseLine = new Line(startX, baseY, startX, startY - 10);
        Line endLine = new Line(endX, baseY, endX, startY - 10);

        this.getChildren().addAll(baseLine, endLine);
    }
}
