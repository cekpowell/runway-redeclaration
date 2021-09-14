package View;

import Controller.SystemController;
import IO.IOHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main class for the project - Entry point into the system.
 */
public class Main extends Application {

    // member variables
    private Toolbar toolbar;
    private Dashboard dashboard;

    // static variables
    private static final String titleName = "Runway Re-Decleration";
    private static final String authorName = "Group 23";

    /**
     * Main method - entry point for the program
     * @param args System arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * STarting point for application
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Toolbar toolbar = new Toolbar();
        Dashboard dashboard = new Dashboard(titleName, authorName);
        SystemController.setDashboard(dashboard);
        SystemController.setToolbar(toolbar);
        toolbar.setId("toolbar");
        dashboard.setId("dashboard");

        VBox container = new VBox();
        container.setId("ent");
        container.getChildren().addAll(toolbar,dashboard);
        VBox.setVgrow(dashboard,Priority.ALWAYS);

        // configuring the stage
        Scene scene = new Scene(container,1350,850);
        stage.setScene(scene);
        stage.setTitle(titleName + " by " + authorName);
        stage.show();
    }

    //////////////////////////
    /**
     * SYSTEM MAINTAINANCE
     */
    //////////////////////////

    /**
     * Loading the system on startup.
     * I will finish this(amir)
     */
    @Override
    public void init() throws IOException {
        //TODO... DO SOME XML LOADING
        IOHandler.loadResources();
    }

    /**
     * Closing the system on close.
     */
    @Override
    public void stop(){
        //TODO... SAVE ALL XML stuff before actually "stopping"
    }
}