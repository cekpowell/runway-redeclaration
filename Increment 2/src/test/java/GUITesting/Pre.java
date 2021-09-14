package GUITesting;

import IO.IOHandler;
import View.Main;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Pre extends ApplicationTest {

    @Start
    public void start(Stage stage) {
        Scene scene = new Scene(new Main.dashboardWithToolbar(),1350,850);
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
    public void predefinedObstacles(){
        //
        ComboBox box = lookup("#obstacleselect").query();
        assertTrue(true);

    }
}
