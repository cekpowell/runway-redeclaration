import Model.Airport;
import Model.PhysicalRunway;
import Model.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AirportDecleration {

    //BACKEND TESTS
    @Test
    public void basicDecleration(){
        Airport a1 = new Airport();
        String name = "LOND";
        String IATA = "LHR";
        a1.setName(name);
        a1.setIATA(IATA);
        a1.setPos(new Position(0,0,0));
        Assertions.assertEquals(a1.getIATA(),IATA);
        Assertions.assertEquals(a1.getName(),name);
    }

    @Test
    public void addingRunways(){
        Airport a1 = new Airport();
        String name = "LOND";
        String IATA = "LHR";
        a1.setName(name);
        a1.setIATA(IATA);
        PhysicalRunway test1 = new PhysicalRunway();
        PhysicalRunway test2 = new PhysicalRunway();
        test1.setName("STRIP");
        test2.setName("STRIP2");
        assertTrue(a1.addNewRunway(test1));
        assertTrue(a1.addNewRunway(test2));
        ObservableList<PhysicalRunway> run = FXCollections.observableArrayList();
        run.add(test1);
        run.add(test2);
        Assertions.assertEquals(a1.getRunways(),run);

    }
    @Test
    public void addingDuplicateRunway(){
        Airport a1 = new Airport();
        String name = "LOND";
        String IATA = "LHR";
        a1.setName(name);
        a1.setIATA(IATA);
        PhysicalRunway test1 = new PhysicalRunway();
        PhysicalRunway test2 = new PhysicalRunway();
        test1.setName("STRIP");
        test2.setName("STRIP");
        a1.addNewRunway(test1);
        Assertions.assertEquals(a1.addNewRunway(test2),false,"Physical runways of the same name should not be allowed");

    }

    @Test
    public void getRunwayByName(){
        Airport a1 = new Airport();
        String name = "LOND";
        String IATA = "LHR";
        a1.setName(name);
        a1.setIATA(IATA);
        PhysicalRunway test1 = new PhysicalRunway();
        PhysicalRunway test2 = new PhysicalRunway();
        test1.setName("STRIP");
        test2.setName("STRIP2");
        assertTrue(a1.addNewRunway(test1));
        assertTrue(a1.addNewRunway(test2));
        assertEquals(a1.getRunwayByName("STRIP"),test1);
        assertEquals(a1.getRunwayByName("STRIP2"),test2);
    }

    @Test
    public void removeRunwayByName(){
        Airport a1 = new Airport();
        PhysicalRunway test1 = new PhysicalRunway();
        PhysicalRunway test2 = new PhysicalRunway();
        test1.setName("STRIP");
        test2.setName("STRIP2");
        ObservableList<PhysicalRunway> removed = FXCollections.observableArrayList(test1);
        a1.addNewRunway(test1);
        a1.addNewRunway(test2);
        Assertions.assertThrows(Exception.class,() -> {a1.removeByName("NONEXISTENT");});
        assertDoesNotThrow(() -> {
            a1.removeByName("STRIP2");
        });
        assertEquals(removed,a1.getRunways());

    }

    //GUI TESTIN
}
