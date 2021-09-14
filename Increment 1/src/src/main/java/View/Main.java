package View;

import IO.IOHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main class for the project - Entry point into the system.
 */
public class Main extends Application {

    private Toolbar toolbar;
    private Dashboard dashboard;

    private static final String titleName = "Runway Re-Decleration";
    private static final String authorName = "Group 23";

    @Override
    public void start(Stage stage) throws Exception {


        // configuring the stage
        Scene scene = new Scene(new dashboardWithToolbar(),1100,700);
        stage.setScene(scene);
        stage.setTitle(titleName + " by " + authorName);
        stage.show();
    }


    public static class dashboardWithToolbar extends VBox{

        public dashboardWithToolbar(){
            super();
            Toolbar toolbar = new Toolbar();
            Dashboard dashboard = new Dashboard(titleName, authorName);
            this.setId("ent");
            toolbar.setId("toolbar");
            dashboard.setId("dashboard");
            this.getChildren().addAll(toolbar,dashboard);
            VBox.setVgrow(dashboard,Priority.ALWAYS);

        }

    }

    /**
     * Loading the system on startup.
     * I will finish this(amir)
     */
    @Override
    public void init() throws IOException {
        //DO SOME XML LOADING
        IOHandler.loadResources();
        //FOR EXAMPLE
    }

    /**
     * Function run when program execution stops.
     */
    //Will do this (amir)
    @Override
    public void stop(){
        //SAVE ALL XML stuff before actually "stopping"
    }

    /**
     * Main method - entry point for the program
     * @param args System arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}