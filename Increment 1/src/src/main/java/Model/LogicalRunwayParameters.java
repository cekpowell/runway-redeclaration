package Model;

/**
 * Class to represent the paramreters of a logical runway.
 */
public class LogicalRunwayParameters {

    // runway parameters
    private double tora;
    private double toda;
    private double asda;
    private double lda;
    private double displacedThreshold;
    private double minimumAngleOfAscentDescent;

    /**
     * Default constructor for the class.
     */
    public LogicalRunwayParameters(){}

    /**
     * Constructor for the class. Takes in each of the runway parameters.
     */
    public LogicalRunwayParameters(double tora, double toda, double asda, double lda, double displacedThreshold, double minimumAngleOfAscentDescent) {
        this.tora = tora;
        this.toda = toda;
        this.asda = asda;
        this.lda = lda;
        this.displacedThreshold = displacedThreshold;
        this.minimumAngleOfAscentDescent = minimumAngleOfAscentDescent;
    }

    /**
     * Constructor for the class. Takes in a runway parameters object and instantiates a new instance with the same values.
     * @param parameters
     */
    public LogicalRunwayParameters(LogicalRunwayParameters parameters){
        this.tora = parameters.getTora();
        this.toda = parameters.getToda();
        this.asda = parameters.getAsda();
        this.lda = parameters.getLda();
        this.displacedThreshold = parameters.getDisplacedThreshold();
        this.minimumAngleOfAscentDescent = parameters.getMinimumAngleOfAscentDescent();
    }

    /**
     * Getters and setters.
     */

    public double getTora() {
        return this.tora;
    }

    public double getToda() {
        return this.toda;
    }

    public double getAsda() {
        return this.asda;
    }

    public double getLda() {
        return this.lda;
    }

    public double getDisplacedThreshold() {
        return this.displacedThreshold;
    }

    public double getClearway() {

        return this.toda - this.tora;
    }

    public double getStopway() {

        return this.asda - this.toda;
    }

    public double getMinimumAngleOfAscentDescent(){return this.minimumAngleOfAscentDescent;}

    public void setTora(double tora) {
        this.tora = tora;
    }

    public void setToda(double toda) {
        this.toda = toda;
    }

    public void setAsda(double asda) {
        this.asda = asda;
    }

    public void setLda(double lda) {
        this.lda = lda;
    }

    public void setDisplacedThreshold(double displacedThreshold) {
        this.displacedThreshold = displacedThreshold;
    }

    public void setMinimumAngleOfAscentDescent(double minimumAngleOfAscentDescent){this.minimumAngleOfAscentDescent = minimumAngleOfAscentDescent;}
}
