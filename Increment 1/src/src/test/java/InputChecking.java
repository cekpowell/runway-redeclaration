import Controller.Validator;
import Model.PhysicalRunway;
import Model.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class InputChecking {
    Validator val;
    @BeforeEach
    public void setup(){
        val = Validator.getValidator();
    }

    @Test
    public void invalidObstacleID(){
        String name = "Rock";
        String id = "1i";
        String measurement = "1.0";

        Assertions.assertThrows(ValidationException.class,
                () -> {
            val.validateObstacleInput(name,id,measurement,measurement,measurement);
        });
    }
    @Test
    public void invalidLength(){
        String name = "Rock";
        String id = "1";
        String measurement = "1.0";
        String wrongmeasurement = "1.0a";

        Assertions.assertThrows(ValidationException.class,
                () -> {
                    val.validateObstacleInput(name,id,measurement,measurement,wrongmeasurement);
                });
    }

    @Test
    public void invalidWidth(){
        String name = "Rock";
        String id = "1";
        String measurement = "1.0";
        String wrongmeasurement = "a1.0";

        Assertions.assertThrows(ValidationException.class,
                () -> {
                    val.validateObstacleInput(name,id,wrongmeasurement,measurement,measurement);
                });
    }

    @Test
    public void invalidHeight(){
        String name = "Rock";
        String id = "1";
        String measurement = "1.0";
        String wrongmeasurement = "1.a0";
        Assertions.assertThrows(ValidationException.class,
                () -> {
                    val.validateObstacleInput(name,id,measurement,wrongmeasurement,measurement);
                });
    }

    @Test
    public void invalidIATA(){
        PhysicalRunway test1 = new PhysicalRunway();
        PhysicalRunway test2 = new PhysicalRunway();
        ArrayList<PhysicalRunway> run = new ArrayList<PhysicalRunway>();
        run.add(test1);
        run.add(test2);
        Assertions.assertThrows(ValidationException.class,
                () -> {
                    val.validateAirportInput("Lond","INVALID",run);
                });
    }


}
