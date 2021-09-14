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
        this.addObstacle.setId("addobstacle");
        this.file.getItems().addAll(addAirport, addObstacle);

        this.edit = new Menu("Edit");
        this.edit.setId("edit");
        this.editAirport = new MenuItem("Edit Airport");
        this.editAirport.setId("editairport");
        this.editObstacle = new MenuItem("Edit Obstacle");
        this.editObstacle.setId("editobstacle");
        this.edit.getItems().addAll(editAirport, editObstacle);

        // CONFIGURING MENU ITEM ACTIONS //

        // FILE //

        addAirport.setOnAction((e) -> {
            // showing the form to add an airport
            AddAirportForm addAirportForm = new AddAirportForm();
            addAirportForm.initOwner(this.getScene().getWindow());
            addAirportForm.show();
        });

        addObstacle.setOnAction((e) -> {
            // showing the form to add an obstacle
            AddObstacleForm addObstacleForm = new AddObstacleForm();
            addObstacleForm.initOwner(this.getScene().getWindow());
            addObstacleForm.show();
        });

        // EDIT //

        editAirport.setOnAction((e) -> {
            // showing the form to edit an airport
            EditAirportForm editAirportForm = new EditAirportForm();
            editAirportForm.initOwner(this.getScene().getWindow());
            editAirportForm.show();
        });

        editObstacle.setOnAction((e) -> {
            //EditObstacleAlert editObstacleAlert = new EditObstacleAlert();
            //editObstacleAlert.showWindow();

            // showing the form to edit an airport
            EditObstacleForm editObstacleForm = new EditObstacleForm();
            editObstacleForm.initOwner(this.getScene().getWindow());
            editObstacleForm.show();
        });

        // adding items to the toolbar
        this.getMenus().addAll(this.file, this.edit);
    }
}
