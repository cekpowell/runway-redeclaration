package Model;

/**
 * Represents a logical runway that's parameters have been updated according to the placement of an obstacle.
 */
public class RevisedLogicalRunway extends LogicalRunway {

    private Obstacle obstacle; // the obstacle placed on this runway
    private ObstaclePosition obtaclePosition; // the position of the obstacle on the runway

    /**
     * Default constructor for the class.
     */
    public RevisedLogicalRunway(){}

    /**
     * Constructor for the class. Takes in the logical runway heading, position, parameters, obstacle and placement of
     * the obstacle.
     * @param heading Heading of the runway.
     * @param position Position of the runway.
     * @param parameters The parameters of the logical runway.
     * @param obstacle The obstacle placed on this runway.
     * @param obtaclePosition Represents the position of the obstacle on the runway.
     */
    public RevisedLogicalRunway(String designation, int heading, char position, LogicalRunwayParameters parameters, Obstacle obstacle, ObstaclePosition obtaclePosition){
        super(designation, heading, position, parameters);
        this.obstacle = obstacle;
        this.obtaclePosition = obtaclePosition;
    }

    /**
     * Getters and setters.
     */

    public Obstacle getObstacle() {
        return this.obstacle;
    }

    public ObstaclePosition getPlacement() {
        return this.obtaclePosition;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public void setPlacement(ObstaclePosition placement) {
        this.obtaclePosition = placement;
    }
}
