package Controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.scene.control.Toggle;

import java.util.ArrayList;

/**
 * Class for input  error detection. Detects input errors in objects ot class Airport and Obstacle.
 * Throws ValidationException if there is an input error detected.
 * <p>
 * Usage:
 * <p>
 * try {
 * <p>
 * Obstacle obstacle = new Obstacle();
 * <p>
 * // ... setting obstacle instance properties
 * <p>
 * Validator.getValidator().validate(obstacle)
 * <p>
 * // ... obstacle has no input error
 * <p>
 * // other code follows...
 * <p>
 * }
 * <p>
 * catch (ValidationException e) {
 * <p>
 * // Validation error process code here...
 * <p>
 * }
 */
public class Validator {

    private ValidationErrors errors;

    /**
     * Constructs Validator instance
     *
     * @return Validator instance
     */
    public static Validator getValidator() {
        return new Validator();
    }

    /**
     * Default parameter-less constructor
     */
    public Validator() {
        errors = new ValidationErrors();
    }

    /**
     * Validates the input of 'Airport' information.
     * @param pList instance ot Airport class
     * @throws ValidationException object
     */
    public void validateAirportInput(String name, String iata, ArrayList<PhysicalRunway> pList) throws ValidationException {
        ArrayList<String> physicalRunwayNames = new ArrayList<String>();

        // validating name
        if (name.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Name' provided");
        }
        for (Airport airportName : SystemController.getAirports()) {
            if (name.equals(airportName.getName())) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : An Airport with this name already exists");
            }
        }

        // validating IATA
        if (iata.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'IATA' provided");
        }
        if (iata.length() != 3) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'IATA' must be 3 characters in length");
        }
        if (iata.matches(".*\\d.*")) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'IATA' cannot contain numbers");
        }
        for (Airport airportName : SystemController.getAirports()) {
            if (iata.equals(airportName.getIATA())) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : An Airport with this IATA already exists");
            }
        }

        // validating physical runways
        for (PhysicalRunway p : pList) {
            if (physicalRunwayNames.contains(p.getName())) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : Physical runways must have unique names");
                break;
            }
            physicalRunwayNames.add(p.getName());
        }

        if (errors.hasErrors()) throw new ValidationException(errors, "There are validation errors!");
    }


    /**
     * Validates the input of 'PhysicalRunway' information.
     * @param name
     * @param logRunways
     * @throws ValidationException
     */
    public void validatePhysicalRunwayInput(String name, ArrayList<LogicalRunway> logRunways) throws ValidationException {
        // validating physical runway name
        if (name.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) +" : No 'Name' provided");
        }

        // validating logical runway headings
        if (logRunways.size() == 2) {
            if (Math.abs(logRunways.get(1).getHeading() - logRunways.get(1).getHeading()) != 18) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) +" : The logical runways must have a 'Heading' difference of 18");
            }
        }

        if (errors.hasErrors()) throw new ValidationException(errors, "There are validation errors!");
    }


    /**
     * Validates the input of 'LogicalRunway' information.
     * @param headingS
     * @param ldaS
     * @param toraS
     * @param asdaS
     * @param todaS
     * @param displacedThresholdS
     * @param minimumAngleS
     */
    public void validateLogicalRunwayInput(String headingS, String ldaS, String toraS, String asdaS, String todaS, String displacedThresholdS, String minimumAngleS) throws ValidationException{
        // validating heading exists
        if(headingS.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Heading' provided");
        }

        // validating lda exists
        if(ldaS.length() == 0){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'LDA' provided");
        }

        // validating tora exists
        if(toraS.length() == 0){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'TORA' provided");
        }

        // validating asda exists
        if(asdaS.length() == 0){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'ASDA' provided");
        }

        // validating toda exists
        if(todaS.length() == 0){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'TODA' provided");
        }

        // validating displaced threshold exists
        if(displacedThresholdS.length() == 0){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Displaced Threshold' provided");
        }

        // validating minimum angle exists
        if(minimumAngleS.length() == 0){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Minimum Angle of Ascent/Descent' provided");
        }

        // validating iniput data types and constraints
        try {
            int heading = Integer.valueOf(headingS);
            double lda = Double.valueOf(ldaS);
            double tora = Double.valueOf(toraS);
            double asda = Double.valueOf(asdaS);
            double toda = Double.valueOf(todaS);
            double dispacedThreshold = Double.valueOf(displacedThresholdS);
            double minAngle = Double.valueOf(minimumAngleS);
            if (heading < 0 || heading > 36) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Heading' must be between 0 and 36 degrees");
            }
            if (lda < 0) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'LDA' cannot not be negative");
            }
            if (tora < lda || tora != lda + dispacedThreshold) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'TORA' must be greater than or equal to 'LDA' OR equal to 'LDA' plus 'Displaced Threshold'");
            }
            if (asda < tora) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'ASDA' must be greater than or equal to 'TORA'");
            }
            if (toda < asda) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'TODA' must be greater than or equal to 'ASDA'");
            }
            if (dispacedThreshold < 0) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Displaced Threshold' cannot be negative");
            }
            if (minAngle <= 0 || minAngle > 90) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Minimum Angle of Ascent/Descent' must be between 0 and 90 degrees");
            }
        } catch (Exception e) { errors.add("ERROR " + (errors.getErrors().size() + 1) + " : Runway details must be numbers.");}

        if(errors.hasErrors()){throw new ValidationException(errors, "There are validation errors!");}
    }

    /**
     * Validates the input of 'Obstacle' information.
     * @param name
     * @param idS
     * @param widthS
     * @param heightS
     * @param lengthS
     * @throws ValidationException
     */
    public void validateObstacleInput(String name, String idS, String widthS, String heightS, String lengthS) throws ValidationException {
        // validating name
        for (Obstacle obstacle : SystemController.getObstacles()) {
            if (name.equals(obstacle.getName())) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : An Obstacle with this name already exists");
                break;
            }
        }
        if (name.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Name' provided");
        }

        // validating id
        if(idS.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'ID' provided");
        }
        else{
            try{
                int id = Integer.valueOf(idS);

                if (id < 0) {
                    errors.add("ERROR  " + (errors.getErrors().size() + 1) + " : 'ID' cannot be negative");
                }
            }
            catch(Exception e){ errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'ID' must be an Integer");}
        }

        // validating width
        if(widthS.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Width' provided");
        }
        else{
            try{
                double width = Double.valueOf(widthS);

                if (width < 0) {
                    errors.add("ERROR  " + (errors.getErrors().size() + 1) + " : 'Width' cannot be negative");
                }
            }
            catch(Exception e){ errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Width' must be a number");}
        }

        // validating length
        if(lengthS.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Length' provided");
        }
        else{
            try{
                double length = Double.valueOf(lengthS);

                if (length < 0) {
                    errors.add("ERROR  " + (errors.getErrors().size() + 1) + " : 'Length' cannot be negative");
                }
            }
            catch(Exception e){ errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Length' must be a number");}
        }

        // validating height
        if(heightS.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Height' provided");
        }
        else{
            try{
                double height = Double.valueOf(heightS);

                if (height < 0) {
                    errors.add("ERROR  " + (errors.getErrors().size() + 1) + " : 'Height' cannot be negative");
                }
            }
            catch(Exception e){ errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Height' must be a number");}
        }

        if (errors.hasErrors()) throw new ValidationException(errors, "There are validation errors!");
    }


    /**
     * Validates the input of 'CalculationPanel' information.
     * @param disFromThreshold
     * @param disFromCenterLine
     * @param logicalRunway
     * @throws ValidationException
     */
    public void validateCalculationPanelInput(Airport airport, PhysicalRunway physicalRunway, LogicalRunway logicalRunway, Obstacle obstacle, String disFromThreshold, Toggle position, String disFromCenterLine, FlightMethod flightMethod ) throws ValidationException{
        // validating airport
        if(airport == null){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Airport' selected");
        }

        // validating physical runway
        if(physicalRunway == null){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Physical Runway' selected");
        }

        // validating logical runway
        if(logicalRunway == null){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Logical Runway' selected");
        }

        // validating obstacle
        if(obstacle == null){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Obstacle' selected");
        }

        // validating distance from threshold
        if(disFromThreshold.length() == 0){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Distance From Threshold' provided");
        }
        else{
            try {
                double disFromThresh = Double.valueOf(disFromThreshold);
                if (disFromThresh < 0) {
                    errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Distance From Threshold' can't be a negative number");
                }
                if(logicalRunway != null){
                    if (disFromThresh > logicalRunway.getParameters().getToda() + 60) {
                        errors.add("ERROR " + (errors.getErrors().size() + 1) + " : The obstacle must be within 60m of the runway 'TODA'");
                    }
                }
            }
            catch (Exception e) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Distance From Threshold' must be a number");
            }
        }

        // validating position
        if(position == null){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'L/R From Centre Line' selected");
        }

        // validating distance from centre line
        if(disFromCenterLine.length() == 0){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Distance From Centre Line' provided");
        }
        else{
            try {
                double disFromCenter = Double.valueOf(disFromCenterLine);
                if (disFromCenter < 0) {
                    errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Distance From Centre Line' can't be a negative number");
                }
                if (disFromCenter > 75) {
                    errors.add("ERROR " + (errors.getErrors().size() + 1) + " : The obstacle must be within 75m of the runway centre line");
                }
            } catch (Exception e) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Distance From Centre Line' must be a number");
            }
        }

        // validating flight method
        if(flightMethod == null){
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Flight Method' selected");
        }

        if (errors.hasErrors()) throw new ValidationException(errors, "There are validation errors!");
    }


    /**
     * Validates the editing of an 'Airport'.
     * @param editAirport
     * @param newName
     * @param newIata
     * @param newPList
     * @throws ValidationException
     */
    public void validateAirportEdit(Airport editAirport, String newName, String newIata, ArrayList<PhysicalRunway> newPList) throws ValidationException {
        ArrayList<String> physicalRunwayNames = new ArrayList<String>();

        // validating name
        if (newName.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Name' provided");
        }
        for (Airport airport : SystemController.getAirports()) {
            if (newName.equals(airport.getName()) && editAirport != airport) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : An Airport with this name already exists");
            }
        }

        // validating IATA
        if (newIata.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'IATA' provided");
        }
        if (newIata.length() != 3) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'IATA' must be 3 characters in length");
        }
        if (newIata.matches(".*\\d.*")) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'IATA' cannot contain numbers");
        }
        for (Airport airport : SystemController.getAirports()) {
            if (newIata.equals(airport.getIATA()) &&  editAirport != airport) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : An Airport with this IATA already exists");
            }
        }

        // validating physical runways
        for (PhysicalRunway p : newPList) {
            if (physicalRunwayNames.contains(p.getName())) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : Physical runways must have unique names");
                break;
            }
            physicalRunwayNames.add(p.getName());
        }

        if (errors.hasErrors()) throw new ValidationException(errors, "There are validation errors!");
    }

    /**
     * Validates the editing of an obstacle.
     * @param editObstacle
     * @param newName
     * @param newIdS
     * @param newWidthS
     * @param newHeightS
     * @param newLengthS
     * @throws ValidationException
     */
    public void validateObstacleEdit(Obstacle editObstacle, String newName, String newIdS, String newWidthS, String newHeightS, String newLengthS) throws ValidationException {
        // validating name
        for (Obstacle obstacle : SystemController.getObstacles()) {
            if (newName.equals(obstacle.getName()) && editObstacle != editObstacle) {
                errors.add("ERROR " + (errors.getErrors().size() + 1) + " : An Obstacle with this name already exists");
                break;
            }
        }
        if (newName.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Name' provided");
        }

        // validating id
        if(newIdS.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'ID' provided");
        }
        else{
            try{
                int id = Integer.valueOf(newIdS);

                if (id < 0) {
                    errors.add("ERROR  " + (errors.getErrors().size() + 1) + " : 'ID' cannot be negative");
                }
            }
            catch(Exception e){ errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'ID' must be an Integer");}
        }

        // validating width
        if(newWidthS.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Width' provided");
        }
        else{
            try{
                double width = Double.valueOf(newWidthS);

                if (width < 0) {
                    errors.add("ERROR  " + (errors.getErrors().size() + 1) + " : 'Width' cannot be negative");
                }
            }
            catch(Exception e){ errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Width' must be a number");}
        }

        // validating length
        if(newLengthS.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Length' provided");
        }
        else{
            try{
                double length = Double.valueOf(newLengthS);

                if (length < 0) {
                    errors.add("ERROR  " + (errors.getErrors().size() + 1) + " : 'Length' cannot be negative");
                }
            }
            catch(Exception e){ errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Length' must be a number");}
        }

        // validating height
        if(newHeightS.length() == 0) {
            errors.add("ERROR " + (errors.getErrors().size() + 1) + " : No 'Height' provided");
        }
        else{
            try{
                double height = Double.valueOf(newHeightS);

                if (height < 0) {
                    errors.add("ERROR  " + (errors.getErrors().size() + 1) + " : 'Height' cannot be negative");
                }
            }
            catch(Exception e){ errors.add("ERROR " + (errors.getErrors().size() + 1) + " : 'Height' must be a number");}
        }


        if (errors.hasErrors()) throw new ValidationException(errors, "There are validation errors!");
    }
}