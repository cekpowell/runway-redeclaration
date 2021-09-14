package Model;

/**
 * Represents the position of an obstacle on a logical runway.
 */
public class ObstaclePosition {

    private double distanceFromThreshold; // distance from logical runway start to obstacle
    private double distanceFromCentreLine; // distance between logical runway centre line and obstacle (+ for right, - for left).

    /**
     * Default constructor method for the class.
     */
    public ObstaclePosition(){}

    /**
     * Constructor for the class. Takes in the the distance from the threshold and distance from the centre line of the
     * object.
     * @param distanceFromThreshold Distance from the runway start to the obstacle location.
     * @param distanceFromCentreLine Distance from the runway centre-line to the obstacle location.
     */
    public ObstaclePosition(double distanceFromThreshold, double distanceFromCentreLine) {
        this.distanceFromThreshold = distanceFromThreshold;
        this.distanceFromCentreLine = distanceFromCentreLine;
    }

    /**
     * Getters and setters.
     */

    public double getDistanceFromThreshold() {
        return distanceFromThreshold;
    }

    public double getDistanceFromCentreLine() {
        return distanceFromCentreLine;
    }

    public void setDistanceFromThreshold(double distanceFromThreshold) {
        this.distanceFromThreshold = distanceFromThreshold;
    }

    public void setDistanceFromCentreLine(double distanceFromCentreLine) {
        this.distanceFromCentreLine = distanceFromCentreLine;
    }
}
