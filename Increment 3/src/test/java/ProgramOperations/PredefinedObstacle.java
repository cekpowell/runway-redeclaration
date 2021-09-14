package ProgramOperations;

import Controller.SystemController;
import IO.IOHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PredefinedObstacle {

    @Test
    public void predefinedObstaclesExist(){
        assertDoesNotThrow(IOHandler::loadPredefined);
        assertEquals(SystemController.getObstacles().size(),12);
    }
}
