package GUITesting;

import Controller.SystemController;
import IO.IOHandler;
import Model.Airport;
import Model.Obstacle;
import Model.PhysicalRunway;
import View.Main;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static GUITesting.ExampleData.exampleAirport1;
import static GUITesting.GUITestUtils.correctNotification;
import static GUITesting.GUITestUtils.destroyIO;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.matcher.control.ComboBoxMatchers.*;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class Adding extends ApplicationTest {

    @Start
    public void start(Stage stage) {
        Scene scene = new Scene(new Main.dashboardWithToolbar(),1350,850);
        stage.setScene(scene);
        stage.setTitle("Testin");
        stage.show();
    }


    //First test to see if adding an airport works
    //TEST NR 1
    @Test
    public void addingAirport(){
        destroyIO();
        Airport a = exampleAirport1;
        clickOn("#file");
        clickOn("#addairport");
        clickOn("#AddAirportname");
        write(a.getName());
        clickOn("#AddAirportIATA");
        write(a.getIATA());
        clickOn("#AddAirportsubmit");
        clickOn("#AddAirportconfirm");
        Label notification = lookup("#noti0").query();
        assertTrue(correctNotification(notification,SystemController.airportDecleration));

    }

    //TEST NR 1
    @Test
    public void addingAirportandAdded(){
        destroyIO();
        Airport a = new Airport();
        a.setIATA("LHR");
        a.setName("LOND");
        String name = "LOND";
        String IATA = "LHR";
        clickOn("#file");
        clickOn("#addairport");
        clickOn("#AddAirportname");
        write(name);
        clickOn("#AddAirportIATA");
        write(IATA);
        clickOn("#AddAirportsubmit");
        clickOn("#AddAirportconfirm");
        FxAssert.verifyThat("#airportselect",containsItems(a));
        Label notification = lookup("#noti0").query();
        assertTrue(correctNotification(notification,SystemController.airportDecleration));

    }

    //TEST NR 2
    @Test
    public void addingAirportInvalidInput(){
        String name = "LOND";
        String IATA = "23";
        String IATA2 = "ab";
        String IATA3 = "abc3";
        String[] iatas = {IATA,IATA2,IATA3};
        clickOn("#file");
        clickOn("#addairport");
        clickOn("#AddAirportname").write(name);
        for(String iata:iatas){
            for(int i = 0; i < 4;i++){
                clickOn("#AddAirportIATA").press(KeyCode.DELETE);
            }
            clickOn("#AddAirportIATA").write(iata);
            clickOn("#AddAirportsubmit");
            clickOn("#AddAirportconfirm");
            clickOn("#AddAirporterror");
        }

    }

    //TEST NR 3
    @Test
    public void addingRunwayToAirport(){
        destroyIO();
        String name = "LOND";
        String IATA = "LHR";
        //PhysicalRunway[] runways = ExampleData.getExampleValidRunways();
        PhysicalRunway[] runways = new PhysicalRunway[1];
        runways[0] = ExampleData.firstExample();
        clickOn("#file");
        clickOn("#addairport");
        clickOn("#AddAirportname").write(name);
        clickOn("#AddAirportIATA").write(IATA);
        addRunwaysValid(runways,"AddAirport");
        clickOn("#AddAirportsubmit");
        clickOn("#AddAirportconfirm");
        Label notification = lookup("#noti0").query();
        assertTrue(correctNotification(notification,SystemController.airportDecleration));

    }





    //TEST NR 4
    @Test
    public void addingInvalidRunway(){
        String name = "LOND";
        String IATA = "LHR";
        String formname = "AddPhysicalRunway";
        String baseform = "AddAirport";
        PhysicalRunway[] runways = ExampleData.getExampleInvalidRunways();
        clickOn("#file");
        clickOn("#addairport");
        clickOn("#AddAirportname").write(name);
        clickOn("#AddAirportIATA").write(IATA);
        addRunwaysInvalid(runways,baseform);

    }

    //TEST NR 5
    @Test
    public void addingObstacles(){
        Obstacle[] obs = ExampleData.getExampleObstacles();
        int counter = 0;
        for(Obstacle o:obs){
            fillObstacle(o);
            clickOn("#Obstaclesubmit");
            clickOn("#Obstacleconfirm");
            //notification check
            FxAssert.verifyThat("#noti"+String.valueOf(counter),org.testfx.matcher.base.NodeMatchers.isNotNull());
            FxAssert.verifyThat("#obstacleselect",containsItems(o));
            //correct notification

            Label notification = lookup("#noti"+String.valueOf(counter)).query();
            assertTrue(correctNotification(notification,SystemController.obstacleadd));
            counter++;
        }
    }


    //TEST NR 6
    @Test
    public void addingObstacleInvalidInput(){
        MockObstacle[] obs = ExampleData.getExampleInvalidObstacles();
        int counter = 0;
        for(MockObstacle o:obs){
            fillObstacle(o);
            clickOn("#Obstaclesubmit");
            clickOn("#Obstacleconfirm");
            clickOn("#Obstacleerror");
            clickOn("#Obstaclecancel");
            clickOn("#Obstacleconfirm");
            //notification check
        }
    }





    public void fillObstacle(Obstacle o) {
        clickOn("#file");
        clickOn("#addobstacle");
        clickOn("#Obstaclename").write(o.getName());
        clickOn("#Obstacleid").write(String.valueOf(o.getId()));
        clickOn("#Obstaclewidth").write(String.valueOf(o.getWidth()));
        clickOn("#Obstaclelength").write(String.valueOf(o.getLength()));
        clickOn("#Obstacleheight").write(String.valueOf(o.getHeight()));

    }

    public void fillObstacle(MockObstacle o) {
        clickOn("#file");
        clickOn("#addobstacle");
        clickOn("#Obstaclename").write(o.getName());
        clickOn("#Obstacleid").write(o.getId());
        clickOn("#Obstaclewidth").write(o.getWidth());
        clickOn("#Obstaclelength").write(o.getLength());
        clickOn("#Obstacleheight").write(o.getHeight());

    }

    public void addRunwaysInvalid(PhysicalRunway[] runways,String base){
        String baseform = "AddPhysicalRunway";
        String other = baseform + "logicalRunwayForm";
        for(PhysicalRunway run : runways) {
            clickOn("#"+base+"addRunway");
            clickOn("#" + baseform + "name").write(run.getName());
            if(run.getLogicalRunways().size() ==2){
                fillOutFormRunway("1",run,other,baseform);
                fillOutFormRunway("2",run,other,baseform);
            }
            else{
                fillOutFormRunway("1",run,other,baseform);
            }
            clickOn("#AddPhysicalRunwaysubmit");
            //SOME STUFF HERE
            clickOn("#AddPhysicalRunwayconfirm");
            clickOn("#AddPhysicalRunwayerror");
            clickOn("#AddPhysicalRunwaycancel");
            clickOn("#AddPhysicalRunwayconfirm");
        }


    }
    //CALLED in another class not a test
    public void addingRunwayToAirport(PhysicalRunway[] runways){
        String name = "LOND";
        String IATA = "LHR";
        clickOn("#file");
        clickOn("#addairport");
        clickOn("#AddAirportname").write(name);
        clickOn("#AddAirportIATA").write(IATA);
        addRunwaysValid(runways,"AddAirport");

    }
    public void addRunwaysValid(PhysicalRunway[] runways,String base){
        String baseform = "AddPhysicalRunway";
        String other = baseform + "logicalRunwayForm";
        for(PhysicalRunway run : runways) {

            clickOn("#"+base +"addRunway");
            clickOn("#AddPhysicalRunwayname").write(run.getName());
            if(run.getLogicalRunways().size() ==2){
                fillOutFormRunway("1",run,other,baseform);
                fillOutFormRunway("2",run,other,baseform);
            }
            else{
                fillOutFormRunway("1",run,other,baseform);
            }
            clickOn("#AddPhysicalRunwaysubmit");
            clickOn("#AddPhysicalRunwayconfirm");
        }
    }

    public void fillOutFormRunway(String formnr,PhysicalRunway run,String form,String baseform){
        if(formnr.equals("1")){
            clickOn("#"+baseform+"oneLogicalRunway");
        }
        else{
            clickOn("#"+baseform+"twoLogicalRunway");
        }
        int pos = Integer.valueOf(formnr)-1;
        clickOn("#"+form + formnr +run.getLogicalRunways().get(pos).getPosition());
        clickOn("#"+form + formnr + "heading").write(String.valueOf(run.getLogicalRunways().get(pos).getHeading()));
        clickOn("#"+form + formnr + "tora").write(String.valueOf(run.getLogicalRunways().get(pos).getParameters().getTora()));
        clickOn("#"+form + formnr + "toda").write(String.valueOf(run.getLogicalRunways().get(pos).getParameters().getToda()));
        clickOn("#"+form + formnr + "asda").write(String.valueOf(run.getLogicalRunways().get(pos).getParameters().getAsda()));
        clickOn("#"+form + formnr + "lda").write(String.valueOf(run.getLogicalRunways().get(pos).getParameters().getLda()));
        clickOn("#"+form + formnr + "displaced").write(String.valueOf(run.getLogicalRunways().get(pos).getParameters().getDisplacedThreshold()));
        clickOn("#"+form + formnr + "minangle").write(String.valueOf(run.getLogicalRunways().get(pos).getParameters().getMinimumAngleOfAscentDescent()));
    }




}
