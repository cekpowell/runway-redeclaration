package Model;

/**
 * Represents a logical runway within a physical runway.
 */
public class LogicalRunway {

    private int heading; // heading of the runway in degrees
    private char position; // position of the runway for parallel runways (i.e., L, R)
    private String designation; // designator/name for the runway
    private LogicalRunwayParameters parameters; // parameters of the runway

    /**
     * Default constructor method for the class.
     */
    public LogicalRunway(){}

    /**
     * Constructor for the class. Takes in the logical runway heading and position.
     * @param heading Heading of the runway.
     * @param position Position of the runway.
     * @param parameters Parameters of the logical runway.
     */
    public LogicalRunway(String designation, int heading, char position, LogicalRunwayParameters parameters){
        this.heading = heading;
        this.position = position;
        this.designation = designation;
        this.parameters = parameters;
    }

    /**
     * Getters and setters.
     */

    public int getHeading() {
        return this.heading;
    }

    public char getPosition() {
        return position;
    }

    public String getDesignation() {
        return designation;
    }

    public LogicalRunwayParameters getParameters() {
        return this.parameters;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public void setPosition(char position) {
        this.position = position;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setParameters(LogicalRunwayParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns a String representation of the physical runway (i.e., the name of the runway).
     * @return String representation of the logical runway.
     */
    @Override
    public String toString(){
        return this.designation; // the designation is the string representation of the runway.
    }
}
