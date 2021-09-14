package GUITesting;

import Controller.SystemController;
import IO.IOHandler;
import View.Dashboard;
import View.Main;
import View.Toolbar;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static GUITesting.GUITestUtils.destroyIO;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.matcher.control.ComboBoxMatchers.*;

public class StateTests extends ApplicationTest {

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
    @Override
    public void init() throws IOException {
        //TODO... DO SOME XML LOADING
        IOHandler.loadResources();
    }

    @Test
    public void addingChanges(){
        Adding add = new Adding();
        add.addingAirport();
    }

    //Test 21, will test further stuff here
    @Test
    public void changesSaved(){
        ComboBox airports = lookup("#airportselect").query();
        FxAssert.verifyThat(airports,containsItems(ExampleData.exampleAirport1));
    }

    //Test 21

}
