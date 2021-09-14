package RunwayRevisions;

import Controller.RunwayReviser;
import Model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing the correctness of the program when re-calculating runway parameters, using the scenarios provided
 * with the project specification.
 */
public class RunwayRevision {

    // minimum angle of ascent descent used in the runways
    private static final double minAngleAscentDescent = 1.14;

    // Runway 09R
    private static final LogicalRunwayParameters parameters09r = new LogicalRunwayParameters(3660,
                                                                                             3660,
                                                                                            3660,
                                                                                              3353,
                                                                                  307,
                                                                                                  minAngleAscentDescent);
    private static final LogicalRunway logicalRunway09r = new LogicalRunway("09R",
                                                                               9,
                                                                               'R',
                                                                                       parameters09r);

    // Runway 27L
    private static final LogicalRunwayParameters parameters27l = new LogicalRunwayParameters(3660,
                                                                                             3660,
                                                                                             3660,
                                                                                              3660,
                                                                                  0,
                                                                                                  minAngleAscentDescent);
    private static final LogicalRunway logicalRunway27l = new LogicalRunway("27L",
                                                                               27,
                                                                               'L',
                                                                                       parameters27l);

    // Runway 09L
    private static final LogicalRunwayParameters parameters09l = new LogicalRunwayParameters(3902,
                                                                                             3902,
                                                                                             3902,
                                                                                              3595,
                                                                                  307,
                                                                                                   minAngleAscentDescent);
    private static final LogicalRunway logicalRunway09l = new LogicalRunway("09L",
                                                                               9,
                                                                               'L',
                                                                                       parameters09l);

    // Runway 27R
    private static final LogicalRunwayParameters parameters27r = new LogicalRunwayParameters(3884,
                                                                                            3962,
                                                                                            3884,
                                                                                             3884,
                                                                                 0,
                                                                                                 minAngleAscentDescent);
    private static final LogicalRunway logicalRunway27r = new LogicalRunway("27R",
                                                                               27,
                                                                              'R',
                                                                                      parameters27r);

    ///////////////////////
    /**
     * TESTING SCENARIO 1
     */
    //////////////////////

    @Test
    public void scenario1Runway09LTakeOffAway(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(12);

        // distance from threshold
        double distanceFromThreshold = -50;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09L
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway09l, obstacle, obstaclePosition, FlightMethod.TAKEOFF_AWAY);

        // expected values
        double expectedTORA = 3345;
        double expectedASDA = 3345;
        double expectedTODA = 3345;

        // actual values
        double actualTORA = revisedRunway.getParameters().getTora();
        double actualASDA = revisedRunway.getParameters().getAsda();
        double actualTODA = revisedRunway.getParameters().getToda();

        // Testing the TORA, ASDA and TODA
        assertTrue( actualTORA == expectedTORA, "Expected TORA =  " + expectedTORA + " Actual TORA = " + actualTORA);
        assertTrue( actualASDA == expectedASDA, "Expected ASDA =  " + expectedASDA + " Actual ASDA = " + actualASDA);
        assertTrue( actualTODA == expectedTODA, "Expected TODA =  " + expectedTODA + " Actual TODA = " + actualTODA);
    }

    @Test
    public void scenario1Runway09LLandingOver(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(12);

        // distance from threshold
        double distanceFromThreshold = -50;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09L
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway09l, obstacle, obstaclePosition, FlightMethod.LANDING_OVER);

        // expected value
        double expectedLDA = 2982;

        // actual value
        double actualLDA = Math.round(revisedRunway.getParameters().getLda());

        // Testing the LDA
        assertTrue(actualLDA == expectedLDA, "Expected LDA =  " + expectedLDA + " Actual LDA = " + actualLDA);
    }

    @Test
    public void scenario1Runway27RTakeOffTowards(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(12);

        // distance from threshold
        double distanceFromThreshold = 3646;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 27R
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway27r, obstacle, obstaclePosition, FlightMethod.TAKEOFF_TOWARDS);

        // expected values
        double expectedTORA = 2983;
        double expectedASDA = 2983;
        double expectedTODA = 2983;

        // actual values
        double actualTORA = Math.round(revisedRunway.getParameters().getTora());
        double actualASDA = Math.round(revisedRunway.getParameters().getAsda());
        double actualTODA = Math.round(revisedRunway.getParameters().getToda());

        // Testing the TORA, ASDA and TODA
        assertTrue( actualTORA == expectedTORA, "Expected TORA =  " + expectedTORA + " Actual TORA = " + actualTORA);
        assertTrue( actualASDA == expectedASDA, "Expected ASDA =  " + expectedASDA + " Actual ASDA = " + actualASDA);
        assertTrue( actualTODA == expectedTODA, "Expected TODA =  " + expectedTODA + " Actual TODA = " + actualTODA);
    }

    @Test
    public void scenario1Runway27RLandingTowards(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(12);

        // distance from threshold
        double distanceFromThreshold = 3646;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 27L
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway27r, obstacle, obstaclePosition, FlightMethod.LANDING_TOWARDS);

        // expected value
        double expectedLDA = 3346;

        // actual value
        double actualLDA = Math.round(revisedRunway.getParameters().getLda());

        // Testing the LDA
        assertTrue(actualLDA == expectedLDA, "Expected LDA =  " + expectedLDA + " Actual LDA = " + actualLDA);
    }

    ///////////////////////
    /**
     * TESTING SCENARIO 2
     */
    //////////////////////

    @Test
    public void scenario2Runway09RTakeOffTowards(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(25);

        // distance from threshold
        double distanceFromThreshold = 2853;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09R
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway09r, obstacle, obstaclePosition, FlightMethod.TAKEOFF_TOWARDS);

        // expected values
        double expectedTORA = 1844;
        double expectedASDA = 1844;
        double expectedTODA = 1844;

        // actual values
        double actualTORA = Math.round(revisedRunway.getParameters().getTora());
        double actualASDA = Math.round(revisedRunway.getParameters().getAsda());
        double actualTODA = Math.round(revisedRunway.getParameters().getToda());

        // Testing the TORA, ASDA and TODA
        assertTrue( actualTORA == expectedTORA, "Expected TORA =  " + expectedTORA + " Actual TORA = " + actualTORA);
        assertTrue( actualASDA == expectedASDA, "Expected ASDA =  " + expectedASDA + " Actual ASDA = " + actualASDA);
        assertTrue( actualTODA == expectedTODA, "Expected TODA =  " + expectedTODA + " Actual TODA = " + actualTODA);
    }

    @Test
    public void scenario2Runway09RLandingTowards(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(25);

        // distance from threshold
        double distanceFromThreshold = 2853;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09R
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway09r, obstacle, obstaclePosition, FlightMethod.LANDING_TOWARDS);

        // expected value
        double expectedLDA = 2553;

        // actual value
        double actualLDA = Math.round(revisedRunway.getParameters().getLda());

        // Testing the LDA
        assertTrue(actualLDA == expectedLDA, "Expected LDA =  " + expectedLDA + " Actual LDA = " + actualLDA);
    }

    @Test
    public void scenario2Runway27LTakeOffAway(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(25);

        // distance from threshold
        double distanceFromThreshold = 500;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09L
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway27l, obstacle, obstaclePosition, FlightMethod.TAKEOFF_AWAY);

        // expected values
        double expectedTORA = 2860;
        double expectedASDA = 2860;
        double expectedTODA = 2860;

        // actual values
        double actualTORA = revisedRunway.getParameters().getTora();
        double actualASDA = revisedRunway.getParameters().getAsda();
        double actualTODA = revisedRunway.getParameters().getToda();

        // Testing the TORA, ASDA and TODA
        assertTrue( actualTORA == expectedTORA, "Expected TORA =  " + expectedTORA + " Actual TORA = " + actualTORA);
        assertTrue( actualASDA == expectedASDA, "Expected ASDA =  " + expectedASDA + " Actual ASDA = " + actualASDA);
        assertTrue( actualTODA == expectedTODA, "Expected TODA =  " + expectedTODA + " Actual TODA = " + actualTODA);
    }

    @Test
    public void scenario2Runway27LLandingOver(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(25);

        // distance from threshold
        double distanceFromThreshold = 500;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09L
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway27l, obstacle, obstaclePosition, FlightMethod.LANDING_OVER);

        // expected value
        double expectedLDA = 1844;

        // actual value
        double actualLDA = Math.round(revisedRunway.getParameters().getLda());

        // Testing the LDA
        assertTrue(actualLDA == expectedLDA, "Expected LDA =  " + expectedLDA + " Actual LDA = " + actualLDA);
    }


    ///////////////////////
    /**
     * TESTING SCENARIO 3
     */
    //////////////////////

    @Test
    public void scenario3Runway09RTakeOffAway(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(15);

        // distance from threshold
        double distanceFromThreshold = 150;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09L
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway09r, obstacle, obstaclePosition, FlightMethod.TAKEOFF_AWAY);

        // expected values
        double expectedTORA = 2903;
        double expectedASDA = 2903;
        double expectedTODA = 2903;

        // actual values
        double actualTORA = revisedRunway.getParameters().getTora();
        double actualASDA = revisedRunway.getParameters().getAsda();
        double actualTODA = revisedRunway.getParameters().getToda();

        // Testing the TORA, ASDA and TODA
        assertTrue( actualTORA == expectedTORA, "Expected TORA =  " + expectedTORA + " Actual TORA = " + actualTORA);
        assertTrue( actualASDA == expectedASDA, "Expected ASDA =  " + expectedASDA + " Actual ASDA = " + actualASDA);
        assertTrue( actualTODA == expectedTODA, "Expected TODA =  " + expectedTODA + " Actual TODA = " + actualTODA);
    }

    @Test
    public void scenario3Runway09RLandingOver(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(15);

        // distance from threshold
        double distanceFromThreshold = 150;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09L
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway09r, obstacle, obstaclePosition, FlightMethod.LANDING_OVER);

        // expected value
        double expectedLDA = 2389;

        // actual value
        double actualLDA = Math.round(revisedRunway.getParameters().getLda());

        // Testing the LDA
        assertTrue(actualLDA == expectedLDA, "Expected LDA =  " + expectedLDA + " Actual LDA = " + actualLDA);
    }

    @Test
    public void scenario3Runway27LTakeOffTowards(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(15);

        // distance from threshold
        double distanceFromThreshold = 3203;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09R
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway27r, obstacle, obstaclePosition, FlightMethod.TAKEOFF_TOWARDS);

        // expected values
        double expectedTORA = 2389;
        double expectedASDA = 2389;
        double expectedTODA = 2389;

        // actual values
        double actualTORA = Math.round(revisedRunway.getParameters().getTora());
        double actualASDA = Math.round(revisedRunway.getParameters().getAsda());
        double actualTODA = Math.round(revisedRunway.getParameters().getToda());

        // Testing the TORA, ASDA and TODA
        assertTrue( actualTORA == expectedTORA, "Expected TORA =  " + expectedTORA + " Actual TORA = " + actualTORA);
        assertTrue( actualASDA == expectedASDA, "Expected ASDA =  " + expectedASDA + " Actual ASDA = " + actualASDA);
        assertTrue( actualTODA == expectedTODA, "Expected TODA =  " + expectedTODA + " Actual TODA = " + actualTODA);
    }

    @Test
    public void scenario3Runway27LLandingTowards(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(15);

        // distance from threshold
        double distanceFromThreshold = 3203;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09R
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway27l, obstacle, obstaclePosition, FlightMethod.LANDING_TOWARDS);

        // expected value
        double expectedLDA = 2903;

        // actual value
        double actualLDA = Math.round(revisedRunway.getParameters().getLda());

        // Testing the LDA
        assertTrue(actualLDA == expectedLDA, "Expected LDA =  " + expectedLDA + " Actual LDA = " + actualLDA);
    }

    ///////////////////////
    /**
     * TESTING SCENARIO 4
     */
    //////////////////////

    @Test
    public void scenario4Runway09LTakeOffTowards(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(20);

        // distance from threshold
        double distanceFromThreshold = 3546;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09R
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway09l, obstacle, obstaclePosition, FlightMethod.TAKEOFF_TOWARDS);

        // expected values
        double expectedTORA = 2788;
        double expectedASDA = 2788;
        double expectedTODA = 2788;

        // actual values
        double actualTORA = Math.round(revisedRunway.getParameters().getTora());
        double actualASDA = Math.round(revisedRunway.getParameters().getAsda());
        double actualTODA = Math.round(revisedRunway.getParameters().getToda());

        // Testing the TORA, ASDA and TODA
        assertTrue( actualTORA == expectedTORA, "Expected TORA =  " + expectedTORA + " Actual TORA = " + actualTORA);
        assertTrue( actualASDA == expectedASDA, "Expected ASDA =  " + expectedASDA + " Actual ASDA = " + actualASDA);
        assertTrue( actualTODA == expectedTODA, "Expected TODA =  " + expectedTODA + " Actual TODA = " + actualTODA);
    }

    @Test
    public void scenario4Runway09LLandingTowards(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(20);

        // distance from threshold
        double distanceFromThreshold = 3546;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09R
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway09l, obstacle, obstaclePosition, FlightMethod.LANDING_TOWARDS);

        // expected value
        double expectedLDA = 3246;

        // actual value
        double actualLDA = Math.round(revisedRunway.getParameters().getLda());

        // Testing the LDA
        assertTrue(actualLDA == expectedLDA, "Expected LDA =  " + expectedLDA + " Actual LDA = " + actualLDA);
    }

    @Test
    public void scenario4Runway27RTakeOffAway(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(20);

        // distance from threshold
        double distanceFromThreshold = 50;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09L
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway27r, obstacle, obstaclePosition, FlightMethod.TAKEOFF_AWAY);

        // expected values
        double expectedTORA = 3534;
        double expectedASDA = 3534;
        double expectedTODA = 3534;

        // actual values
        double actualTORA = revisedRunway.getParameters().getTora();
        double actualASDA = revisedRunway.getParameters().getAsda();
        double actualTODA = revisedRunway.getParameters().getToda();

        // Testing the TORA, ASDA and TODA
        assertTrue( actualTORA == expectedTORA, "Expected TORA =  " + expectedTORA + " Actual TORA = " + actualTORA);
        assertTrue( actualASDA == expectedASDA, "Expected ASDA =  " + expectedASDA + " Actual ASDA = " + actualASDA);
        assertTrue( actualTODA == expectedTODA, "Expected TODA =  " + expectedTODA + " Actual TODA = " + actualTODA);
    }

    @Test
    public void scenario4Runway27RLandingOver(){

        // Obstacle
        Obstacle obstacle = new Obstacle();
        obstacle.setHeight(20);

        // distance from threshold
        double distanceFromThreshold = 50;
        double distanceFromCentreline = 0;

        // Obstacle Position
        ObstaclePosition obstaclePosition = new ObstaclePosition(distanceFromThreshold, distanceFromCentreline);

        // Revised Logical Runway 09L
        RevisedLogicalRunway revisedRunway = RunwayReviser.getRevisedLogicalRunway(logicalRunway27r, obstacle, obstaclePosition, FlightMethod.LANDING_OVER);

        // expected value
        double expectedLDA = 2769;

        // actual value
        double actualLDA = Math.round(revisedRunway.getParameters().getLda());

        // Testing the LDA
        assertTrue(actualLDA == expectedLDA, "Expected LDA =  " + expectedLDA + " Actual LDA = " + actualLDA);
    }
}
