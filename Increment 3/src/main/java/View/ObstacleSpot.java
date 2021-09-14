package View;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class ObstacleSpot extends Group {

    public ObstacleSpot(double posX, double posY, double len, Color color) {
        super();

        Line bottomToTop = new Line(-len + posX, posY, len + posX, posY);
        Line topToBottom = new Line(posX, -len + posY, posX, len + posY);

        bottomToTop.setFill(color);
        bottomToTop.setStroke(color);
        bottomToTop.setStrokeWidth(4f);

        topToBottom.setFill(color);
        topToBottom.setStroke(color);
        topToBottom.setStrokeWidth(4f);

        this.setRotate(45f);

        this.getChildren().addAll(bottomToTop, topToBottom);
    }
}
