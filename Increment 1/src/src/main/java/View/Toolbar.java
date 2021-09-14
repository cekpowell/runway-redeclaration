package View;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Represents the toolbar within the application.
 */
public class Toolbar extends MenuBar {

    // FILE
    private Menu file;
    private MenuItem addAirport;
    private MenuItem addObstacle;

    // EDIT
    private Menu edit;
    private MenuItem editAirport;
    private MenuItem editObstacle;

    /**
     * Constructor for the class. Initialises a new toolbar.
     */
    public Toolbar(){
        // initialising member variables
        this.file = new Menu("File");
        this.file.setId("file");
        this.addAirport = new MenuItem("Add Airport");
        this.addAirport.setId("addairport");
        this.addObstacle = new MenuItem("Add Obstacle");
        this.addObstacle.setId("#addobstacle");
        this.file.getItems().addAll(addAirport, addObstacle);

        this.edit = new Menu("Edit");
        this.edit.setId("edit");
        this.editAirport = new MenuItem("Edit Airport");
        this.editObstacle = new MenuItem("Edit Obstacle");
        this.edit.getItems().addAll(editAirport, editObstacle);

        // configurnig actions of menu items
        addAirport.setOnAction((e) -> {
            AddNewAirportForm addNewAirportForm = new AddNewAirportForm();
            addNewAirportForm.showWindow();
        });

        addObstacle.setOnAction((e) -> {
            AddNewObstacleForm addNewObstacleForm = new AddNewObstacleForm();
            addNewObstacleForm.showWindow();
        });

        editAirport.setOnAction((e) -> {
            EditAirportAlert editAirportForm = new EditAirportAlert();
            editAirportForm.showWindow();
        });

        editObstacle.setOnAction((e) -> {
            EditObstacleAlert editObstacleAlert = new EditObstacleAlert();
            editObstacleAlert.showWindow();
        });

        // adding items to the toolbar
        this.getMenus().addAll(this.file, this.edit);
    }
}
