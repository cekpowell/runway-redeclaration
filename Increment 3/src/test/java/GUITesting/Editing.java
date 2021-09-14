package GUITesting;

import Controller.SystemController;
import IO.IOHandler;
import Model.Airport;
import Model.LogicalRunway;
import Model.Obstacle;
import Model.PhysicalRunway;
import View.Dashboard;
import View.Main;
import View.Toolbar;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static GUITesting.GUITestUtils.correctNotification;
import static GUITesting.GUITestUtils.destroyIO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.matcher.control.ComboBoxMatchers.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Editing extends ApplicationTest {

    @Start
    public void start(Stage stage) {
        Toolbar toolbar = new Toolbar();
        Dashboard dashboard = new Dashboard("title", "author");
        SystemController.setDashboard(dashboard);
        SystemController.setToolbar(toolbar);

        VBox container = new VBox();
        container.getChildren().addAll(toolbar,dashboard);
        VBox.setVgrow(dashboard, Priority.ALWAYS);

        Scene scene = new Scene(container,1350,850);
        stage.setScene(scene);
        stage.setTitle("Testin");
        stage.show();
    }


    @BeforeEach
    public void destroy(){
        destroyIO();
    }
    //TEST NR 7
    @Test
    public void removingAirport(){
        Adding ex = new Adding();
        ex.addingAirport();
        //Now removing
        clickOn("#edit");
        clickOn("#editairport");
        ComboBox box = lookup("#EditAirportselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.exampleAirport1);
        });
        clickOn("#EditAirportremoveairport");
        clickOn("#EditAirportconfirm");
        clickOn("#EditAirportsubmit");
        clickOn("#EditAirportconfirm");
        FxAssert.verifyThat("#airportselect",hasItems(0));
        Label notification = lookup("#noti1").query();
        assertTrue(correctNotification(notification, SystemController.airportRemoval));


    }

    //TEST NR 8
    @Test
    public void editAirportValidProperties(){
        Adding ex = new Adding();
        Airport changed = new Airport();
        changed.setName("NEWNAME");
        changed.setIATA("LXA");
        ex.addingAirport();
        clickOn("#edit");
        clickOn("#editairport");
        ComboBox box = lookup("#EditAirportselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.exampleAirport1);
        });
        TextField name = lookup("#EditAirportname").query();
        name.clear();
        TextField iata = lookup("#EditAirportiata").query();
        iata.clear();
        clickOn("#EditAirportnameenable");
        clickOn("#EditAirportiataenable");
        clickOn("#EditAirportname").write("NEWNAME");
        clickOn("#EditAirportiata").write("LXA");
        clickOn("#EditAirportsubmit");
        clickOn("#EditAirportconfirm");
        clickOn("#airportselect");
        ComboBox airports = lookup("#airportselect").query();
        Airport a = (Airport) airports.getItems().get(0);
        assertTrue(a.getName().equals("NEWNAME") && a.getIATA().equals("LXA"));

        Label notification = lookup("#noti1").query();
        assertTrue(correctNotification(notification, SystemController.airpnameedit));
        Label notification2 = lookup("#noti2").query();
        assertTrue(correctNotification(notification2, SystemController.airpiataedit));
    }

    //TEST NR 9
    @Test
    public void editAirportInvalidProperties(){
        Adding ex = new Adding();
        Airport changed = new Airport();
        ex.addingAirport();
        clickOn("#edit");
        clickOn("#editairport");
        ComboBox box = lookup("#EditAirportselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.exampleAirport1);
        });
        clickOn("#EditAirportiataenable");
        TextField iata = lookup("#EditAirportiata").query();
        iata.clear();
        clickOn("#EditAirportiata").write("INV1");
        clickOn("#EditAirportsubmit");
        clickOn("#EditAirportconfirm");
        clickOn("#EditAirporterror");
        clickOn("#EditAirportcancel");
        clickOn("#EditAirportconfirm");
    }

    //TEST NR 10
    @Test
    public void editAirportAddRunway(){
        Adding ex = new Adding();
        Airport changed = new Airport();
        ex.addingAirport();
        clickOn("#edit");
        clickOn("#editairport");
        ComboBox box = lookup("#EditAirportselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.exampleAirport1);
        });
        Adding add = new Adding();
        add.addRunwaysValid(ExampleData.getExampleValidRunways(),"EditAirport");
        clickOn("#EditAirportsubmit");
        clickOn("#EditAirportconfirm");
        for (int i = 0; i < ExampleData.getExampleValidRunways().length;i++){
            Label notification = lookup("#noti"+String.valueOf(i+1)).query();
            assertTrue(correctNotification(notification, SystemController.addrunway));
        }
        //some checking now edit successful
    }

    //TEST NR 11
    @Test
    public void editAirportAddInvalidRunway(){
        Adding ex = new Adding();
        Airport changed = new Airport();
        ex.addingAirport();
        clickOn("#edit");
        clickOn("#editairport");
        ComboBox box = lookup("#EditAirportselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.exampleAirport1);
        });
        Adding add = new Adding();
        add.addRunwaysInvalid(ExampleData.getExampleInvalidRunways(),"EditAirport");
        clickOn("#EditAirportcancel");
        clickOn("#EditAirportconfirm");

    }

    //TEST 12
    //come back here later..
    //@Test
    public void editAirportEditingRunway(){
        Adding add = new Adding();
        PhysicalRunway[] runways = new PhysicalRunway[1];
        runways[0] = ExampleData.firstExample();
        add.addingRunwayToAirport(runways);
        clickOn("#AddAirportsubmit");
        clickOn("#AddAirportconfirm");
        clickOn("#edit");
        clickOn("#editairport");
        ComboBox box = lookup("#EditAirportselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.exampleAirport1);
        });
        ComboBox box2 = lookup("#EditAirportrunselect").query();
        interact(() -> {
            box2.getSelectionModel().select(ExampleData.firstExample());
        });
        clickOn("#EditAirporteditrunway");
        /*
        TextField toda1 = lookup("#EditPhysicalRunwaylogicalform1toda").query();
        toda1.clear();

         */
        clickOn("#EditPhysicalRunwaylogicalform1toda").write("3902.0");
        clickOn("#EditPhysicalRunwaysubmit");
        clickOn("#EditPhysicalRunwayconfirm");
        clickOn("#EditAirportsubmit");
        clickOn("#EditAirportconfirm");



    }

    //TEST NR 13
    @Test
    public void editAirportEditingInvalidRunway(){
        Adding add = new Adding();
        PhysicalRunway[] runways = new PhysicalRunway[1];
        runways[0] = ExampleData.firstExample();
        add.addingRunwayToAirport(runways);
        clickOn("#AddAirportsubmit");
        clickOn("#AddAirportconfirm");
        clickOn("#edit");
        clickOn("#editairport");
        ComboBox box = lookup("#EditAirportselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.exampleAirport1);
        });
        ComboBox box2 = lookup("#EditAirportrunselect").query();
        interact(() -> {
            box2.getSelectionModel().select(ExampleData.firstExample());
        });
        clickOn("#EditAirporteditrunway");
        TextField toda1 = lookup("#EditPhysicalRunwaylogicalform1toda").query();
        toda1.clear();
        clickOn("#EditPhysicalRunwaylogicalform1toda").write("a3900");
        clickOn("#EditPhysicalRunwaysubmit");
        clickOn("#EditPhysicalRunwayconfirm");
        clickOn("#EditPhysicalRunwayerror");
        clickOn("#EditPhysicalRunwaycancel");
        clickOn("#EditPhysicalRunwayconfirm");
    }

    //TEST NUMBER 14
    //come back

    public void editAirportRemoveRunway(){
        editAirportEditingRunway();
        clickOn("#edit");
        clickOn("#editairport");
        ComboBox box = lookup("#EditAirportselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.exampleAirport1);
        });
        ComboBox box2 = lookup("#EditAirportrunselect").query();
        interact(() -> {
            box2.getSelectionModel().select(ExampleData.firstExample());
        });
        clickOn("#EditAirportremoverun").clickOn("#EditAirportconfirm").clickOn("#EditAirportsubmit").clickOn("#EditAirportconfirm");

    }



    //TEST 15
    @Test
    public void removeObstacle(){
        Adding add = new Adding();
        add.fillObstacle(ExampleData.firstobstacle);
        clickOn("#Obstaclesubmit");
        clickOn("#Obstacleconfirm");
        clickOn("#edit");
        clickOn("#editobstacle");
        ComboBox box = lookup("#EditObstacleselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.firstobstacle);
        });
        clickOn("#EditObstacleremove");
        clickOn("#EditObstaclesubmit");
        clickOn("#EditObstacleconfirm");
        assertFalse(box.getItems().contains(ExampleData.firstobstacle));
        Label notification = lookup("#noti0").query();
        assertTrue(correctNotification(notification, SystemController.obstacleadd));
        Label notification2 = lookup("#noti1").query();
        assertTrue(correctNotification(notification2, SystemController.obstacleremove));


    }

    //TEST 16
    @Test
    public void editObstacle(){
        Adding add = new Adding();
        add.fillObstacle(ExampleData.firstobstacle);
        clickOn("#Obstaclesubmit");
        clickOn("#Obstacleconfirm");
        clickOn("#edit");
        clickOn("#editobstacle");
        ComboBox box = lookup("#EditObstacleselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.firstobstacle);
        });
        TextField field = lookup("#EditObstaclename").query();
        TextField field2 = lookup("#EditObstaclewidth").query();
        field.clear();
        field2.clear();
        clickOn("#EditObstaclenameenable").clickOn("#EditObstaclewidthenable");
        clickOn("#EditObstaclename").write("Changed").clickOn("#EditObstaclewidth").write("5.0");
        Obstacle changed = ExampleData.firstobstacle;
        changed.setWidth(5.0);
        changed.setName("Changed");
        clickOn("#EditObstaclesubmit");
        clickOn("#EditObstacleconfirm");
        assertTrue(box.getItems().contains(changed));
        Iterator it = (box.getItems().iterator());
        while(it.hasNext()){
            Obstacle o = (Obstacle) it.next();
            if(o.equals(changed)){
                assertTrue(o.getName().equals("Changed"));
            }
        }
        Label notification = lookup("#noti1").query();
        assertTrue(correctNotification(notification, SystemController.obsnameedit));
        Label notification2 = lookup("#noti2").query();
        assertTrue(correctNotification(notification2, SystemController.obswidthedit));

    }

    //TEST 17
    @Test
    public void editObstacleInvalid(){
        Adding add = new Adding();
        add.fillObstacle(ExampleData.firstobstacle);
        clickOn("#Obstaclesubmit");
        clickOn("#Obstacleconfirm");
        clickOn("#edit");
        clickOn("#editobstacle");
        ComboBox box = lookup("#EditObstacleselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.firstobstacle);
        });
        TextField field = lookup("#EditObstaclename").query();
        TextField field2 = lookup("#EditObstaclewidth").query();
        field.clear();
        field2.clear();
        clickOn("#EditObstaclenameenable").clickOn("#EditObstaclewidthenable");
        clickOn("#EditObstaclename").write("Changed").clickOn("#EditObstaclewidth").write("5.0a");
        clickOn("#EditObstaclesubmit");
        clickOn("#EditObstacleconfirm");
        clickOn("#EditObstacleerror");
        clickOn("#EditObstaclecancel");
        clickOn("#EditObstacleconfirm");


    }


    //TEST 19
    @Test
    public void invalidRunwayRevisionInputs(){
        String name = "LOND";
        String IATA = "LHR";
        Adding add = new Adding();
        add.fillObstacle(ExampleData.firstobstacle);
        clickOn("#Obstaclesubmit");
        clickOn("#Obstacleconfirm");


        PhysicalRunway[] run = new PhysicalRunway[1];
        clickOn("#file");
        clickOn("#addairport");
        clickOn("#AddAirportname").write(name);
        clickOn("#AddAirportIATA").write(IATA);
        run[0] = ExampleData.firstExample();
        add.addRunwaysValid(run,"AddAirport");
        clickOn("#AddAirportsubmit").clickOn("#AddAirportconfirm");
        ComboBox box = lookup("#airportselect").query();
        interact(() -> {
            box.getSelectionModel().select(ExampleData.exampleAirport1);
        });
        ComboBox box2 = lookup("#runwayselection").query();
        interact(() -> {
            box2.getSelectionModel().select(ExampleData.firstExample());
        });
        ComboBox box3 = lookup("#logicalselection").query();
        interact(() -> {
            box3.getSelectionModel().select(ExampleData.firstExample().getLogicalRunways().get(0));
        });
        clickOn("#l").clickOn("#threshold").write("3aaa");
        clickOn("#center").write("22");
        ComboBox box4 = lookup("#flightmethod").query();
        interact(() -> {
            box4.getSelectionModel().select(box4.getItems().get(0));
        });
        ComboBox box5 = lookup("#obstacleselect").query();
        interact(() -> {
            box5.getSelectionModel().select(box5.getItems().get(0));
        });
        clickOn("#performrevision");
        clickOn("#calculationpanelerror");

    }



}


