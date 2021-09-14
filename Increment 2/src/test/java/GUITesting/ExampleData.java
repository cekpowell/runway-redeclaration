package GUITesting;

import Controller.XMLHandler;
import Model.*;
import RunwayRevisions.RunwayRevision;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class ExampleData {

    public static Airport exampleAirport1 = new Airport("LHR","LOND");




    public static PhysicalRunway[] getExampleValidRunways(){
        PhysicalRunway[] runways = new PhysicalRunway[4];
        runways[0] = firstExample();
        runways[1] = secondExample();
        runways[2] = thirdExample();
        runways[3] = fourthExample();
        return runways;
    }

    public static Obstacle[] getExampleObstacles(){
        Obstacle[] obstacles = new Obstacle[2];
        obstacles[0] = firstobstacle;
        obstacles[1] = secondobstacle;
        return obstacles;
    }

    //TODO
    public static MockObstacle[] getExampleInvalidObstacles(){
        MockObstacle[] errors = new MockObstacle[4];
        errors[0] = firsterror;
        errors[1] = seconderror;
        errors[2] = thirderror;
        errors[3] = fourtherror;
        return errors;
    }

    public static PhysicalRunway[] getExampleInvalidRunways(){
        PhysicalRunway[] runways = new PhysicalRunway[1];
        runways[0] = firstError();
        return runways;
    }

    public static PhysicalRunway firstExample(){
        PhysicalRunway run =  new PhysicalRunway();
        run.setName("ex1");
        run.addLogicalRunway(logicalRunway09l);
        run.addLogicalRunway(logicalRunway27r);
        return run;
    }

    public static PhysicalRunway firstError(){
        PhysicalRunway run =  new PhysicalRunway();
        run.setName("ex1");
        run.addLogicalRunway(logicalRunway09rERROR);
        run.addLogicalRunway(logicalRunway27r);
        return run;
    }

    public static PhysicalRunway secondExample(){
        PhysicalRunway run =  new PhysicalRunway();
        run.setName("ex2");
        run.addLogicalRunway(logicalRunway09r);
        run.addLogicalRunway(logicalRunway27r);
        return run;
    }

    public static PhysicalRunway thirdExample(){
        PhysicalRunway run =  new PhysicalRunway();
        run.setName("ex3");
        run.addLogicalRunway(logicalRunway09r);
        run.addLogicalRunway(logicalRunway27l);
        return run;
    }

    public static PhysicalRunway fourthExample(){
        PhysicalRunway run =  new PhysicalRunway();
        run.setName("ex4");
        run.addLogicalRunway(logicalRunway09l);
        run.addLogicalRunway(logicalRunway27r);
        return run;
    }

    private static final double minAngleAscentDescent = 1.14;

    private static final MockObstacle firsterror = new MockObstacle("debris","22","1.0a","2.0","3.0");
    private static final MockObstacle seconderror = new MockObstacle("debris","22","1.0","a2.0b","3.0");
    private static final MockObstacle thirderror = new MockObstacle("debris","22","1.0","2.0","ca.30");
    private static final MockObstacle fourtherror = new MockObstacle("debris","2a2","1.0","2.0","ca.30");
    public static final Obstacle firstobstacle = new Obstacle("rock",1, 3.0, 5.0,7.0, Optional.empty());
    public static final Obstacle secondobstacle = new Obstacle("cone",2, 1.0, 1.0,1.0, Optional.empty());

    // Runway 09R
    private static final LogicalRunwayParameters parameters09r = new LogicalRunwayParameters(3660,
            3660,
            3660,
            3353,
            307,
            minAngleAscentDescent);

    private static final LogicalRunwayParameters parameters09rERROR = new LogicalRunwayParameters(3660,
            3660,
            3660,
            3353,
            306,
            minAngleAscentDescent);
    private static final LogicalRunway logicalRunway09r = new LogicalRunway("09R",
            9,
            'R',
            parameters09r);

    private static final LogicalRunway logicalRunway09rERROR = new LogicalRunway("09R",
            9,
            'R',
            parameters09rERROR);
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
}
