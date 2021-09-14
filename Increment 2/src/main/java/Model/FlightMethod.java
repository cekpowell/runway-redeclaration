package Model;

/**
 * Represents the different types of flight method possible. A flight method represents how the plane interacts with the
 * obstacle during its take-off/landing.
 */
public enum FlightMethod {


    LANDING_OVER("Landing Over Obstacle"), // landing over the obstacle
    LANDING_TOWARDS("Landing Towards Obstacle"), // landing towards the obstacle
    TAKEOFF_TOWARDS("Taking-Off Towards Obstacle"), // taking-off towards the obstacle
    TAKEOFF_AWAY("Taking-Off Away Obstacle"); // taking-off away from the obstacle

    private String flightMethod;

    private FlightMethod(String flightMethod){
        this.flightMethod = flightMethod;
    }

    /**
     * Converts the flight method to a string.
     * @return String equivalent of the flight method..
     */
    @Override
    public String toString(){
        return this.flightMethod;
    }

    /**
     * Gathers the flight method enum from the given string
     * @param text
     * @return
     */
    public static FlightMethod fromString(String text) {
        for (FlightMethod method : FlightMethod.values()) {
            if (method.flightMethod.equalsIgnoreCase(text)) {
                return method;
            }
        }
        return null;
    }
}
