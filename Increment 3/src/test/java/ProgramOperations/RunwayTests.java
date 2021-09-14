package ProgramOperations;

import Model.LogicalRunway;
import Model.PhysicalRunway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class RunwayTests {

    @Test
    public void addMoreThanTwoLogical(){
        PhysicalRunway run = new PhysicalRunway();
        run.setName("STRIP");
        run.addLogicalRunway(new LogicalRunway());
        run.addLogicalRunway(new LogicalRunway());
        assertFalse(run.addLogicalRunway(new LogicalRunway()));
    }

    @Test
    public void setMoreThanTwoLogical(){
        PhysicalRunway run = new PhysicalRunway();
        run.setName("STRIP");
        ObservableList<LogicalRunway> logical = FXCollections.observableArrayList();
        for(int i = 0; i < 3;i++){
            logical.add(new LogicalRunway());
        }
        run.setLogicalRunways(logical);
        assertFalse(run.getLogicalRunways().size() > 2);
    }
}

