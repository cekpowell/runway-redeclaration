package Utils;

import View.TopDownMap;
import javafx.scene.Scene;

/**
 * Class for calculating the angle between two positions.
 */
public class AngleDelta {
    private double previousAngle = 0;
    private TopDownMap scene;
    private double spinSensitivity = 0.5;

    /**
     * Uses scene height and width for calculations
     * @param scene The Scene
     */
    public AngleDelta (TopDownMap scene) {
        this.scene = scene;
    }

    private double findMiddle(double one, double two) {
        return one - two / 2;
    }

    /**
     * Calculate the difference between the previous angle and the one on the (x,y) position.
     * @param x The x coordinate
     * @param y The y coordinate
     * @return angle difference in degrees
     */
    public double calculateDelta(double x, double y) {
        double yCo = findMiddle(y, scene.getHeight());
        double negyCo = yCo * -1;
        double xCo = findMiddle(x, scene.getWidth());
        double expr = negyCo / Math.sqrt(Math.pow(xCo, 2) + Math.pow(negyCo, 2));
        double angle = Math.toDegrees(Math.asin(expr));
        if (y < scene.getHeight()/2 && x < scene.getWidth()/2) {
            angle = 180 - angle;
        }
        if (y > scene.getHeight()/2 && x < scene.getWidth()/2) {
            angle = 180 + Math.abs(angle);
        }
        if (y < scene.getHeight()/2 && x > scene.getWidth()/2) {}
        if (y > scene.getHeight()/2 && x > scene.getWidth()/2) {
            angle = 360 - Math.abs(angle);
        }
        double delta = angle - previousAngle;
        previousAngle = angle;
        return delta;
    }

    /**
     * Utility function that uses calculateDelta.
     * @param x The x coordinate
     * @param y The y coordinate
     * @return Negative spinSensitivity if delta is positive and positive spinSensitivity if negative.
     */
    public double calculateDeltaStep(double x, double y) {
        if (calculateDelta(x, y) > 0) {
            return -spinSensitivity;
        } else {
            return spinSensitivity;
        }
    }

    public double getSpinSensitivity() {
        return spinSensitivity;
    }

    public void setSpinSensitivity(double spinSensitivity) {
        this.spinSensitivity = spinSensitivity;
    }
}
