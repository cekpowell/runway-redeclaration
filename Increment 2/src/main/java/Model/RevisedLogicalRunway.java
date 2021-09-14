package Model;

/**
 * Represents a logical runway that's parameters have been updated according to the placement of an obstacle.
 */
public class RevisedLogicalRunway extends LogicalRunway {

    private Obstacle obstacle; // the obstacle placed on this runway
    private ObstaclePosition obstaclePosition; // the position of the obstacle on the runway
    private FlightMethod flightMethod;
    private double alstocs;
    private double resaDistance;
    private double stripEndDistance;
    private double engineBlastAllowance;

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
     * @param obstaclePosition Represents the position of the obstacle on the runway.
     */
    public RevisedLogicalRunway(String designation, int heading, char position,
                                LogicalRunwayParameters parameters, double alstocs,
                                double resaDistance, double stripEndDistance, double engineBlastAllowance,
                                Obstacle obstacle, ObstaclePosition obstaclePosition, FlightMethod flightMethod){
        super(designation, heading, position, parameters);
        this.alstocs = alstocs;
        this.resaDistance = resaDistance;
        this.stripEndDistance = stripEndDistance;
        this.engineBlastAllowance = engineBlastAllowance;
        this.obstacle = obstacle;
        this.obstaclePosition = obstaclePosition;
        this.flightMethod = flightMethod;
    }

    /**
     * Getters and setters.
     */

    public double getAlstocs() {
        return alstocs;
    }

    public double getResaDistance() {
        return resaDistance;
    }

    public double getStripEndDistance() {
        return stripEndDistance;
    }

    public double getEngineBlastAllowance() {
        return engineBlastAllowance;
    }

    public Obstacle getObstacle() {
        return this.obstacle;
    }

    public ObstaclePosition getObstaclePosition() {
        return this.obstaclePosition;
    }

    public FlightMethod getFlightMethod() {
        return flightMethod;
    }

    public void setAlstocs(double alstocs) {
        this.alstocs = alstocs;
    }

    public void setResaDistance(double resaDistance) {
        this.resaDistance = resaDistance;
    }

    public void setStripEndDistance(double stripEndDistance) {
        this.stripEndDistance = stripEndDistance;
    }

    public void setEngineBlastAllowance(double engineBlastAllowance) {
        this.engineBlastAllowance = engineBlastAllowance;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public void setObstaclePosition(ObstaclePosition placement) {
        this.obstaclePosition = placement;
    }

    public void setFlightMethod(FlightMethod flightMethod) {
        this.flightMethod = flightMethod;
    }
}
