package Controller;

import Model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.lang.Math;

/**
 * Machine that updates the parameters of a runway when an obstacle is present.
 */
public class RunwayReviser {

    private static final double resaDistance = 240; // default distance for RESA used in calculations
    private static final double stripEndDistance = 60; // default distance for Strip End used in calculations
    private static final double engineBlastAllowance = 400; // default distance allowed for engine blast during take-off

    /**
     * Updator method for a logical runway based on an obstacle placement and a flight method.
     * @param logicalRunway The logical runway to be revised.
     * @param obstacle The obstacle placed on the runway.
     * @param obstaclePosition The position of the obstacle on the runway.
     * @param flightMethod The flight method of the plane relative to the obstacle
     * @return The revised logical runway.
     */
    public static RevisedLogicalRunway getRevisedLogicalRunway (LogicalRunway logicalRunway, Obstacle obstacle, ObstaclePosition obstaclePosition, FlightMethod flightMethod){
        LogicalRunwayParameters revisedParameters = new LogicalRunwayParameters();

        // performing the runway revision based on the flight method
        if(flightMethod == FlightMethod.LANDING_OVER){
            // perform calculation with landing over method
            revisedParameters = landingOverObstacleRevision(logicalRunway.getParameters(), obstacle, obstaclePosition);
        }
        else if(flightMethod == FlightMethod.LANDING_TOWARDS){
            // perform calculation with landing towards method
            revisedParameters = landingTowardsObstacleRevision(logicalRunway.getParameters(), obstacle, obstaclePosition);
        }
        else if(flightMethod == FlightMethod.TAKEOFF_TOWARDS){
            // perform calculation with taking-off towards method
            revisedParameters = takeOffTowardsObstacleRevision(logicalRunway.getParameters(), obstacle, obstaclePosition);
        }
        else if(flightMethod == FlightMethod.TAKEOFF_AWAY){
            // perform calculation with taking-off away method
            revisedParameters = takeOffAwayFromObstacleRevision(logicalRunway.getParameters(), obstacle, obstaclePosition);
        }

        RevisedLogicalRunway revisedLogicalRunway = new RevisedLogicalRunway(logicalRunway.getDesignation(),
                                                                             logicalRunway.getHeading(),
                                                                             logicalRunway.getPosition(),
                                                                             revisedParameters,
                                                                             obstacle,
                                                                             obstaclePosition);
        // returning the revised logical runway
        return revisedLogicalRunway;
    }

    /**
     * Updates a logical runway when landing over an obstacle.
     * @param parameters Logical runway parameters to be updated
     * @param obstacle Obstacle placed on the logical runway.
     * @param obstaclePosition position of obstacle on the runway.
     * @return The revised logical runway parameters.
     */
    private static LogicalRunwayParameters landingOverObstacleRevision(LogicalRunwayParameters parameters, Obstacle obstacle, ObstaclePosition obstaclePosition){
        /**
         * Only the LDA must be updated in this case.
         */

        // calculating distances lost due to obstacle position
        double distanceLostFromPosition = obstaclePosition.getDistanceFromThreshold() + (obstacle.getLength() / 2);

        // new threshold placed based on if alsTocs, blastAllowance or RESA is larger
        double alsTocs = getAlsTocsLanding(parameters.getMinimumAngleOfAscentDescent(), obstacle);
        double[] possibleValues = {alsTocs, engineBlastAllowance, resaDistance};
        double largestValue = largest(possibleValues);

        // calculating new LDA based on parameters
        double newLda = parameters.getLda() - distanceLostFromPosition - largestValue - stripEndDistance;

        // returning the revised object
        LogicalRunwayParameters newParameters = new LogicalRunwayParameters(parameters);
        newParameters.setLda(newLda);
        return newParameters;
    }

    /**
     * Updates a logical runway when landing towards an obstacle.
     * @param parameters Logical runway parameters to be updated
     * @param obstacle Obstacle placed on the logical runway.
     * @param obstaclePosition position of obstacle on the runway.
     * @return The revised logical runway parameters.
     */
    private static LogicalRunwayParameters landingTowardsObstacleRevision(LogicalRunwayParameters parameters, Obstacle obstacle, ObstaclePosition obstaclePosition){
        /**
         * Only the LDA must be updated in this case.
         */

        // calculating diistances lost due to obstacle position
        double distanceLostFromPosition = (parameters.getLda() - obstaclePosition.getDistanceFromThreshold()) + (obstacle.getLength() / 2);

        // calculating new LDA based on parameters
        double newLda = parameters.getLda() - distanceLostFromPosition - resaDistance - stripEndDistance;

        // returning the revised object
        LogicalRunwayParameters newParameters = new LogicalRunwayParameters(parameters);
        newParameters.setLda(newLda);
        return newParameters;
    }

    /**
     * Updates a logical runway when taking-off towards an obstacle.
     * @param parameters Logical runway parameters to be updated
     * @param obstacle Obstacle placed on the logical runway.
     * @param obstaclePosition position of obstacle on the runway.
     * @return The revised logical runway parameters.
     */
    private static LogicalRunwayParameters takeOffTowardsObstacleRevision(LogicalRunwayParameters parameters, Obstacle obstacle, ObstaclePosition obstaclePosition){
        /**
         * TORA, TODA and ASDA must be recalculated.
         */

        // new maximum based on if alsTocs or RESA is larger
        double alsTocs = getAlsTocsTakeOff(parameters.getMinimumAngleOfAscentDescent(), obstacle);
        double[] possibleValues = {alsTocs, resaDistance};
        double largestValue = largest(possibleValues);

        // calculating new TORA
        double toraLostFromPosition = (parameters.getTora() - obstaclePosition.getDistanceFromThreshold()) + (obstacle.getLength()/2) - parameters.getDisplacedThreshold();
        double newTora = parameters.getTora() - toraLostFromPosition - largestValue - stripEndDistance;

        // calculating new ASDA
        double newAsda = newTora;

        // calculating new TODA
        double newToda = newTora;

        // returning the revised parameters
        LogicalRunwayParameters newParameters = new LogicalRunwayParameters(parameters);
        newParameters.setTora(newTora);
        newParameters.setAsda(newAsda);
        newParameters.setToda(newToda);
        return newParameters;
    }

    /**
     * Updates a logical runway when taking-off away from an obstacle.
     * @param parameters Logical runway parameters to be updated
     * @param obstacle Obstacle placed on the logical runway.
     * @param obstaclePosition position of obstacle on the runway.
     * @return The revised logical runway parameters.
     */
    private static LogicalRunwayParameters takeOffAwayFromObstacleRevision(LogicalRunwayParameters parameters, Obstacle obstacle, ObstaclePosition obstaclePosition){
        /**
         * TORA, TODA and ASDA must be recalculated.
         */

        // calculating total distance lost
        double distanceLost = obstaclePosition.getDistanceFromThreshold() - parameters.getDisplacedThreshold() + (obstacle.getLength()/2) + engineBlastAllowance;

        // calculating length of clearway + stopway
        double clearWayAndStopWay = parameters.getClearway() + parameters.getStopway();

        // calculating new TORA
        double newTora = parameters.getTora() - distanceLost + clearWayAndStopWay;

        // calculating new ASDA
        double newAsda = newTora;

        // calculating new TODA
        double newToda = newTora;

        // returning the revised parameters
        LogicalRunwayParameters newParameters = new LogicalRunwayParameters(parameters);
        newParameters.setTora(newTora);
        newParameters.setAsda(newAsda);
        newParameters.setToda(newToda);
        return newParameters;
    }

    /**
     * Calculates and returns the ALS/TOCS value of the revised runway when landing.
     * @param minimumAngleOfAscentDescent Minimum angle of ascent/descent for the runway.
     * @param obstacle Obstacle placed on the runway.
     * @return The ALS/TOCS value for the revised runway.
     */
    private static double getAlsTocsLanding(double minimumAngleOfAscentDescent, Obstacle obstacle){
        // distance = h / tan(x)
        double alsTocs =  obstacle.getHeight() * Math.tan(Math.toRadians(minimumAngleOfAscentDescent));

        return alsTocs;
    }

    /**
     * Calculates and returns the ALS/TOCS value of the revised runway when taking off.
     * @param minimumAngleOfAscentDescent Minimum angle of ascent/descent for the runway.
     * @param obstacle Obstacle placed on the runway.
     * @return The ALS/TOCS value for the revised runway.
     */
    private static double getAlsTocsTakeOff(double minimumAngleOfAscentDescent, Obstacle obstacle){
        // distance = h / tan(x)
        double alsTocs =  obstacle.getHeight() / Math.tan(Math.toRadians(minimumAngleOfAscentDescent));

        return alsTocs;
    }


    /**
     * Returns largest element in an array of doubles.
     * @param arr Array to be searched.
     * @return Largest element in the array.
     */
    private static double largest(double[] arr)
    {
        int i;

        // Initialize maximum element
        double max = arr[0];

        // Traverse array elements from second and
        // compare every element with current max
        for (i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];

        return max;
    }

    /**
     * Gets the calculation breakdown for a runway revision.
     * @param logicalRunway
     * @param obstacle
     * @param obstaclePosition
     * @param flightMethod
     * @return
     */
    public static Node getCalculationBreakdown(LogicalRunway logicalRunway, Obstacle obstacle, ObstaclePosition obstaclePosition, FlightMethod flightMethod){
        // gettinng the revised logical runway from the calculation
        RevisedLogicalRunway revisedLogicalRunway = getRevisedLogicalRunway(logicalRunway, obstacle, obstaclePosition, flightMethod);

        // RUNWAY DETAILS

        // Designation
        HBox designationContainer = new HBox();
        Label designationTitleLabel = new Label("Designation : ");
        Label designationLabel = new Label(revisedLogicalRunway.getDesignation());
        designationContainer.getChildren().addAll(designationTitleLabel, designationLabel);
        designationContainer.setAlignment(Pos.CENTER);
        designationContainer.setSpacing(20);

        // heading
        HBox headingContainer = new HBox();
        Label headingTitleLabel = new Label("Heading : ");
        Label headingLabel = new Label(String.valueOf(revisedLogicalRunway.getHeading()));
        headingContainer.getChildren().addAll(headingTitleLabel, headingLabel);
        headingContainer.setAlignment(Pos.CENTER);
        headingContainer.setSpacing(20);

        // position
        HBox positionContainer = new HBox();
        Label positionTitleLabel = new Label("Position : ");
        Label positionLabel = new Label();
        if(revisedLogicalRunway.getPosition() == 'N'){
            positionLabel.setText("N/A");
        }
        else{
            positionLabel.setText(String.valueOf(revisedLogicalRunway.getPosition()));
        }
        positionContainer.getChildren().addAll(positionTitleLabel, positionLabel);
        positionContainer.setAlignment(Pos.CENTER);
        positionContainer.setSpacing(20);

        // minimum angle of ascent/descent
        HBox minimumAngleContainer = new HBox();
        Label minimumAngleTitleLabel = new Label("Minimum\nAngle : ");
        minimumAngleTitleLabel.setTextAlignment(TextAlignment.CENTER);
        Label minimumAngleLabel = new Label(String.valueOf(logicalRunway.getParameters().getMinimumAngleOfAscentDescent()));
        minimumAngleContainer.getChildren().addAll(minimumAngleTitleLabel, minimumAngleLabel);
        minimumAngleContainer.setAlignment(Pos.CENTER);
        minimumAngleContainer.setSpacing(20);

        // Runway Details Container
        VBox runwayDetailsContainer = new VBox();
        Label runwayDetailsLabel = new Label("Runway Details");
        runwayDetailsLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        runwayDetailsContainer.getChildren().addAll(runwayDetailsLabel, designationContainer, headingContainer, positionContainer, minimumAngleContainer);
        runwayDetailsContainer.setAlignment(Pos.CENTER);
        runwayDetailsContainer.setSpacing(20);

        // OBSTACLE INFORMATION

        // obstacle name
        HBox obstacleNameContainer = new HBox();
        Label obstacleNameTitleLabel = new Label("Name : ");
        Label obstacleNameLabel = new Label(revisedLogicalRunway.getObstacle().getName());
        obstacleNameContainer.getChildren().addAll(obstacleNameTitleLabel, obstacleNameLabel);
        obstacleNameContainer.setAlignment(Pos.CENTER);
        obstacleNameContainer.setSpacing(20);

        // obstacle width
        HBox obstacleWidthContainer = new HBox();
        Label obstacleWidthTitleLabel = new Label("Width : ");
        Label obstacleWidthLabel = new Label(String.valueOf(revisedLogicalRunway.getObstacle().getWidth()) + "(m)");
        obstacleWidthContainer.getChildren().addAll(obstacleWidthTitleLabel, obstacleWidthLabel);
        obstacleWidthContainer.setAlignment(Pos.CENTER);
        obstacleWidthContainer.setSpacing(20);

        // obstacle height
        HBox obstacleHeightContainer = new HBox();
        Label obstacleHeightTitleLabel = new Label("Height : ");
        Label obstacleHeightLabel = new Label(String.valueOf(revisedLogicalRunway.getObstacle().getHeight()) + "(m)");
        obstacleHeightContainer.getChildren().addAll(obstacleHeightTitleLabel, obstacleHeightLabel);
        obstacleHeightContainer.setAlignment(Pos.CENTER);
        obstacleHeightContainer.setSpacing(20);

        // obstacle length
        HBox obstacleLengthContainer = new HBox();
        Label obstacleLengthTitleLabel = new Label("Length : ");
        Label obstacleLengthLabel = new Label(String.valueOf(revisedLogicalRunway.getObstacle().getLength()) + "(m)");
        obstacleLengthContainer.getChildren().addAll(obstacleLengthTitleLabel, obstacleLengthLabel);
        obstacleLengthContainer.setAlignment(Pos.CENTER);
        obstacleLengthContainer.setSpacing(20);

        // obstacle distance from threshold
        HBox distanceFromThresholdContainer = new HBox();
        Label distanceFromThresholdTitleLabel = new Label("Distance\nFrom\nThreshold : ");
        distanceFromThresholdTitleLabel.setTextAlignment(TextAlignment.CENTER);
        Label distanceFromThresholdLabel = new Label(String.valueOf(revisedLogicalRunway.getPlacement().getDistanceFromThreshold()) + "(m)");
        distanceFromThresholdContainer.getChildren().addAll(distanceFromThresholdTitleLabel, distanceFromThresholdLabel);
        distanceFromThresholdContainer.setAlignment(Pos.CENTER);
        distanceFromThresholdContainer.setSpacing(20);

        // obstacle distance from centre line
        HBox distanceFromCentreLineContainer= new HBox();
        Label distanceFromCentreLineTitleLabel = new Label("Distance\nFrom\nCentre line : ");
        distanceFromCentreLineTitleLabel.setTextAlignment(TextAlignment.CENTER);
        Label distanceFromCentreLineLabel = new Label();
        double distanceFromCentreLine = revisedLogicalRunway.getPlacement().getDistanceFromCentreLine();
        if(distanceFromCentreLine < 0){
            distanceFromCentreLineLabel.setText(String.valueOf(-1 * distanceFromCentreLine) + "(m) (Left)");
        }
        else if(distanceFromCentreLine > 0){
            distanceFromCentreLineLabel.setText(String.valueOf(distanceFromCentreLine) + "(m) (Right)");
        }
        else{
            distanceFromCentreLineLabel.setText("0 (m)");
        }
        distanceFromCentreLineContainer.getChildren().addAll(distanceFromCentreLineTitleLabel, distanceFromCentreLineLabel);
        distanceFromCentreLineContainer.setAlignment(Pos.CENTER);
        distanceFromCentreLineContainer.setSpacing(20);

        // obstacle information container
        VBox obstacleInformationContainer = new VBox();
        Label obstacleInformationLabel = new Label("Obstacle Information");
        obstacleInformationLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        obstacleInformationContainer.getChildren().addAll(obstacleInformationLabel,
                obstacleNameContainer,
                obstacleWidthContainer,
                obstacleHeightContainer,
                obstacleLengthContainer,
                distanceFromThresholdContainer,
                distanceFromCentreLineContainer);
        obstacleInformationContainer.setAlignment(Pos.CENTER);
        obstacleInformationContainer.setSpacing(20);

        // FLIGHT DETAILS

        // flight method
        HBox flightMethodContainer = new HBox();
        Label flightMethodTitleLabel = new Label("Flight\nMethod:");
        distanceFromCentreLineTitleLabel.setTextAlignment(TextAlignment.CENTER);
        Label flightMethodLabel = new Label(String.valueOf(flightMethod.toString()));
        flightMethodContainer.getChildren().addAll(flightMethodTitleLabel, flightMethodLabel);
        flightMethodContainer.setAlignment(Pos.CENTER);
        flightMethodContainer.setSpacing(20);

        // flight details container
        VBox flightDetailsContainer = new VBox();
        Label flightDetailsLabel = new Label("Flight Details ");
        flightDetailsLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        flightDetailsContainer.getChildren().addAll(flightDetailsLabel, flightMethodContainer);
        flightDetailsContainer.setAlignment(Pos.CENTER);
        flightDetailsContainer.setSpacing(20);


        //  CALCULATION BREAKDOWN

        // getting the final part of the calculation breakdown
        String calculationBreakdown = "";

        // getting the calculation breakdown based on the flight method
        if(flightMethod == FlightMethod.LANDING_OVER){
            // perform calculation with landing over method
            calculationBreakdown += landingOverObstacleBreakdown(logicalRunway.getParameters(), obstacle, obstaclePosition);
        }
        else if(flightMethod == FlightMethod.LANDING_TOWARDS){
            // perform calculation with landing towards method
            calculationBreakdown += landingTowardsObstacleBreakdown(logicalRunway.getParameters(), obstacle, obstaclePosition);
        }
        else if(flightMethod == FlightMethod.TAKEOFF_TOWARDS){
            // perform calculation with taking-off towards method
            calculationBreakdown += takeOffTowardsObstacleBreakdown(logicalRunway.getParameters(), obstacle, obstaclePosition);
        }
        else if(flightMethod == FlightMethod.TAKEOFF_AWAY){
            // perform calculation with taking-off away method
            calculationBreakdown += takeOffAwayObstacleBreakdown(logicalRunway.getParameters(), obstacle, obstaclePosition);
        }

        // label to store the calculation breakdown string
        Label calculationBreakdownLabel = new Label(calculationBreakdown);

        // calculation breakdown container
        VBox calculationBreakdownContainer = new VBox();
        Label calculationBreakdownTitleLabel = new Label("Calculation Breakdown");
        calculationBreakdownTitleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        calculationBreakdownContainer.getChildren().addAll(calculationBreakdownLabel);
        calculationBreakdownContainer.setAlignment(Pos.TOP_LEFT);
        calculationBreakdownContainer.setPadding(new Insets(5));

        // main container for the display
        VBox container = new VBox();
        container.getChildren().addAll(runwayDetailsContainer,
                                       obstacleInformationContainer,
                                       flightDetailsContainer,
                                       calculationBreakdownTitleLabel,
                                       calculationBreakdownContainer);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        return container;
    }

    /**
     * Gets the calculation breakdown for a revision landing over an obstacle.
     * @param parameters
     * @param obstacle
     * @param obstaclePosition
     * @return
     */
    private static String landingOverObstacleBreakdown(LogicalRunwayParameters parameters, Obstacle obstacle, ObstaclePosition obstaclePosition){
        /**
         * Only the LDA must be updated in this case.
         */

        String calculationBreakdown = "";

        // PARAMETERS

        calculationBreakdown += "Parameters : \n";
        calculationBreakdown += "\tRESA = " + String.valueOf(resaDistance) + "(m)\n";
        calculationBreakdown += "\tStrip End = " + String.valueOf(stripEndDistance) + "(m)\n";
        calculationBreakdown += "\tEngine Blast = " + String.valueOf(engineBlastAllowance) + "(m)\n";

        // DISTANCE LOST FROM POSITION

        // calculating distances lost due to obstacle position
        double distanceLostFromPosition = obstaclePosition.getDistanceFromThreshold() + (obstacle.getLength() / 2);
        calculationBreakdown += "\nDistance Lost From Position:\n";
        calculationBreakdown += "\tObstacle distance = " + obstaclePosition.getDistanceFromThreshold() + "(m)\n";
        calculationBreakdown += "\tObstacle length = " + obstacle.getLength() + "(m)\n";
        calculationBreakdown += "\t= " + obstaclePosition.getDistanceFromThreshold() + " + (" +  obstacle.getLength() + "/2)\n";
        calculationBreakdown += "\t= " + distanceLostFromPosition + "(m)\n";

        // ALS/TOCS or BLAST ALLOWANCE or RESA

        // getting values
        double alsTocs = getAlsTocsLanding(parameters.getMinimumAngleOfAscentDescent(), obstacle);
        double[] possibleValues = {alsTocs, engineBlastAllowance, resaDistance};
        double largestValue = largest(possibleValues);

        // calculatoin of ALS/TOCS
        calculationBreakdown += "\nALS/TOCS:\n";
        calculationBreakdown += "\t= height * tan(min angle)\n";
        calculationBreakdown += "\t= " + obstacle.getHeight() + " * tan(" + parameters.getMinimumAngleOfAscentDescent() + ")\n";
        calculationBreakdown += "\t=" + Math.round(alsTocs) + "(m)\n";

        // calculation of which value is largest
        calculationBreakdown += "\nLargest Value:\n";
        if(largestValue == alsTocs){
            calculationBreakdown += "\t= ALS/TOCS";
        }
        else if(largestValue == engineBlastAllowance){
            calculationBreakdown += "\t= Engine Blast Allowance\n";
        }
        else if(largestValue == resaDistance){
            calculationBreakdown += "\t= RESA\n";
        }

        // NEW LDA

        // getting the new value
        double newLda = parameters.getLda() - distanceLostFromPosition - largestValue - stripEndDistance;

        // string for the calculation
        calculationBreakdown += "\nNew LDA:\n";
        calculationBreakdown += "\t= " + parameters.getLda() + "-" +  distanceLostFromPosition + "-" + Math.round(largestValue) + "-" + stripEndDistance + "\n";
        calculationBreakdown += "\t= " + Math.round(newLda) + "(m)\n";

        // FINAL RESULTS
        calculationBreakdown += "\nUpdated Parameters:\n";
        calculationBreakdown += "\tLDA = " + Math.round(newLda) + "(m)\n";

        // returning the final calculation breakdown
        return calculationBreakdown;
    }

    /**
     * Gets the calculation breakdown for a revision landing towards an obstacle.
     * @param parameters
     * @param obstacle
     * @param obstaclePosition
     * @return
     */
    private static String landingTowardsObstacleBreakdown(LogicalRunwayParameters parameters, Obstacle obstacle, ObstaclePosition obstaclePosition){

        /**
         * Only the LDA must be updated in this case.
         */

        String calculationBreakdown = "";

        // PARAMETERS

        calculationBreakdown += "Parameters : \n";
        calculationBreakdown += "\tRESA = " + String.valueOf(resaDistance) + "(m)\n";
        calculationBreakdown += "\tStrip End = " + String.valueOf(stripEndDistance) + "(m)\n";

        // DISTANCE LOST FROM POSITION

        // calculating distances lost due to obstacle position
        double distanceLostFromPosition = (parameters.getLda() - obstaclePosition.getDistanceFromThreshold()) + (obstacle.getLength() / 2);
        calculationBreakdown += "\nDistance Lost From Position:\n";
        calculationBreakdown += "\tObstacle distance = " + obstaclePosition.getDistanceFromThreshold() + "(m)\n";
        calculationBreakdown += "\tObstacle length = " + obstacle.getLength() + "(m)\n";
        calculationBreakdown += "\t= (" + parameters.getLda() + "-" + obstaclePosition.getDistanceFromThreshold() + ") + (" +  obstacle.getLength() + "/2)\n";
        calculationBreakdown += "\t= " + distanceLostFromPosition + "(m)\n";

        // NEW LDA

        // getting the new value
        double newLda = newLda = parameters.getLda() - distanceLostFromPosition - resaDistance - stripEndDistance;

        // string for the calculation
        calculationBreakdown += "\nNew LDA:\n";
        calculationBreakdown += "\t= " + parameters.getLda() + "-" +  distanceLostFromPosition + "-" + resaDistance +  "-" + stripEndDistance + "\n";
        calculationBreakdown += "\t= " + Math.round(newLda) + "(m)\n";

        // FINAL RESULTS
        calculationBreakdown += "\nUpdated Parameters:\n";
        calculationBreakdown += "\tLDA = " + Math.round(newLda) + "(m)\n";

        return calculationBreakdown;
    }

    /**
     * Gets the calculation breakdown for a revision taking off towards an obstacle.
     * @param parameters
     * @param obstacle
     * @param obstaclePosition
     * @return
     */
    private static String takeOffTowardsObstacleBreakdown(LogicalRunwayParameters parameters, Obstacle obstacle, ObstaclePosition obstaclePosition){
        /**
         * TORA, TODA and ASDA must be recalculated.
         */

        String calculationBreakdown = "";

        // PARAMETERS

        calculationBreakdown += "Parameters : \n";
        calculationBreakdown += "\tRESA = " + String.valueOf(resaDistance) + "(m)\n";
        calculationBreakdown += "\tStrip End = " + String.valueOf(stripEndDistance) + "(m)\n";

        // DISTANCE LOST FROM POSITION

        // calculating distances lost due to obstacle position
        double toraLostFromPosition = (parameters.getTora() - obstaclePosition.getDistanceFromThreshold()) + (obstacle.getLength()/2) - parameters.getDisplacedThreshold();
        calculationBreakdown += "\nDistance Lost From Position:\n";
        calculationBreakdown += "\tObstacle distance = " + obstaclePosition.getDistanceFromThreshold() + "(m)\n";
        calculationBreakdown += "\tObstacle length = " + obstacle.getLength() + "(m)\n";
        calculationBreakdown += "\tDisplaced Threshold = " + parameters.getDisplacedThreshold() + "(m)\n";
        calculationBreakdown += "\t=(" + parameters.getTora() + "-" + obstaclePosition.getDistanceFromThreshold() + ")+(" +  obstacle.getLength() + "/2)-" + parameters.getDisplacedThreshold() + "\n";
        calculationBreakdown += "\t= " + toraLostFromPosition + "(m)\n";

        // ALS/TOCS or BLAST ALLOWANCE or RESA

        // getting values
        double alsTocs = getAlsTocsTakeOff(parameters.getMinimumAngleOfAscentDescent(), obstacle);
        double[] possibleValues = {alsTocs, resaDistance};
        double largestValue = largest(possibleValues);

        // calculatoin of ALS/TOCS
        calculationBreakdown += "\nALS/TOCS:\n";
        calculationBreakdown += "\t= height / tan(min angle)\n";
        calculationBreakdown += "\t= " + obstacle.getHeight() + "/ tan(" + parameters.getMinimumAngleOfAscentDescent() + ")\n";
        calculationBreakdown += "\t=" + Math.round(alsTocs) + "(m)\n";

        // calculation of which value is largest
        calculationBreakdown += "\nLargest Value:\n";
        if(largestValue == alsTocs){
            calculationBreakdown += "\t= ALS/TOCS";
        }
        else if(largestValue == resaDistance){
            calculationBreakdown += "\t= RESA\n";
        }

        // NEW TORA

        // calculating new TORA
        double newTora = parameters.getTora() - toraLostFromPosition - largestValue - stripEndDistance;

        // string for the calculation
        calculationBreakdown += "\nNew TORA:\n";
        calculationBreakdown += "\t= " + parameters.getTora() + "-" +  toraLostFromPosition + "-" + Math.round(largestValue) + "-" + stripEndDistance + "\n";
        calculationBreakdown += "\t= " + Math.round(newTora) + "(m)\n";

        // NEW ASDA

        // calculating new ASDA
        double newAsda = newTora;

        // string for the calculation
        calculationBreakdown += "\nNew ASDA:\n";
        calculationBreakdown += "\t= TORA\n";
        calculationBreakdown += "\t= " + Math.round(newTora) + "(m)\n";


        // NEW TODA

        // calculating new TODA
        double newToda = newTora;

        // string for the calculation
        calculationBreakdown += "\nNew TODA:\n";
        calculationBreakdown += "\t= TORA\n";
        calculationBreakdown += "\t= " + Math.round(newTora) + "(m)\n";

        // FINAL RESULTS
        calculationBreakdown += "\nUpdated Parameters:\n";
        calculationBreakdown += "\tTORA = " + Math.round(newTora) + "(m)\n";
        calculationBreakdown += "\tASDA = " + Math.round(newAsda) + "(m)\n";
        calculationBreakdown += "\tTODA = " + Math.round(newToda) + "(m)\n";

        return calculationBreakdown;
    }

    /**
     * Gets the calculation breakdown for a revision taking off away from an obstacle.
     * @param parameters
     * @param obstacle
     * @param obstaclePosition
     * @return
     */
    private static String takeOffAwayObstacleBreakdown(LogicalRunwayParameters parameters, Obstacle obstacle, ObstaclePosition obstaclePosition){

        /**
         * TORA, TODA and ASDA must be recalculated.
         */

        String calculationBreakdown = "";

        // PARAMETERS

        calculationBreakdown += "Parameters : \n";
        calculationBreakdown += "\tEngine Blast Allowance  = " + String.valueOf(engineBlastAllowance) + "(m)\n";

        // DISTANCE LOST FROM POSITION

        // calculating distances lost due to obstacle position
        double distanceLost = obstaclePosition.getDistanceFromThreshold() - parameters.getDisplacedThreshold() + (obstacle.getLength()/2) + engineBlastAllowance;
        calculationBreakdown += "\nDistance Lost From Position:\n";
        calculationBreakdown += "\tObstacle distance = " + obstaclePosition.getDistanceFromThreshold() + "(m)\n";
        calculationBreakdown += "\tObstacle length = " + obstacle.getLength() + "(m)\n";
        calculationBreakdown += "\tDisplaced Threshold = " + parameters.getDisplacedThreshold() + "(m)\n";
        calculationBreakdown += "\t=" + obstaclePosition.getDistanceFromThreshold() + "+ (" +  obstacle.getLength() + "/2)+" + engineBlastAllowance + "-" + parameters.getDisplacedThreshold() + "\n";
        calculationBreakdown += "\t= " + distanceLost + "(m)\n";

        // DISTANCE GAINED FROM CLEARWAY AND STOPWAY

        // calculating length of clearway + stopway
        double stopway = parameters.getStopway();
        double clearway = parameters.getClearway();
        double clearWayAndStopWay = parameters.getClearway() + parameters.getStopway();

        calculationBreakdown += "\nDistance Gained From Stop/Clearway:\n";
        calculationBreakdown += "\tStopway =" + stopway + "(m)\n";
        calculationBreakdown += "\tClearway =" + clearway + "(m)\n";
        calculationBreakdown += "\t= Stopway + Clearway\n";
        calculationBreakdown += "\t=" + stopway + " + " +  clearway + "\n";
        calculationBreakdown += "\t=" + clearWayAndStopWay + "\n";

        // NEW TORA

        // calculating new TORA
        double newTora = parameters.getTora() - distanceLost + clearWayAndStopWay;

        // string for the calculation
        calculationBreakdown += "\nNew TORA:\n";
        calculationBreakdown += "\t= " + parameters.getTora() + "-" +  distanceLost + "+" + clearWayAndStopWay + "\n";
        calculationBreakdown += "\t= " + Math.round(newTora) + "(m)\n";

        // NEW ASDA

        // calculating new ASDA
        double newAsda = newTora;

        // string for the calculation
        calculationBreakdown += "\nNew ASDA:\n";
        calculationBreakdown += "\t= TORA\n";
        calculationBreakdown += "\t= " + Math.round(newTora) + "(m)\n";


        // NEW TODA

        // calculating new TODA
        double newToda = newTora;

        // string for the calculation
        calculationBreakdown += "\nNew TODA:\n";
        calculationBreakdown += "\t= TORA\n";
        calculationBreakdown += "\t= " + Math.round(newTora) + "(m)\n";

        // FINAL RESULTS
        calculationBreakdown += "\nUpdated Parameters:\n";
        calculationBreakdown += "\tTORA = " + Math.round(newTora) + "(m)\n";
        calculationBreakdown += "\tASDA = " + Math.round(newAsda) + "(m)\n";
        calculationBreakdown += "\tTODA = " + Math.round(newToda) + "(m)\n";

        return calculationBreakdown;
    }
}
