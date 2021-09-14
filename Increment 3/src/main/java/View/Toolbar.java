package View;

import Controller.SystemController;
import Model.Notification;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

/**
 * Represents the toolbar within the application.
 */
public class Toolbar extends MenuBar {

    // FILE
    private Menu file;
    private MenuItem addAirport;
    private MenuItem addObstacle;
    private MenuItem importform;

    // EDIT
    private Menu edit;
    private MenuItem editAirport;
    private MenuItem editObstacle;

    // IMPORT
    private Menu importMenu;


    // EXPORT
    private Menu export;
    private MenuItem exportObjects;
    private Menu exportRevision;
    private ExportMenuItem exportSideOnView;
    private ExportMenuItem exportTopDownView;
    private ExportMenuItem exportSimultaneousView;
    private ExportMenuItem exportCalculationBreakdown;
    private ExportMenuItem exportNotifications;

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
        this.importform = new MenuItem("Import Airport/Obstacle");
        this.importform.setId("import");
        this.file.getItems().addAll(addAirport, addObstacle);

        this.edit = new Menu("Edit");
        this.edit.setId("edit");
        this.editAirport = new MenuItem("Edit Airport");
        this.editAirport.setId("editairport");
        this.editObstacle = new MenuItem("Edit Obstacle");
        this.editObstacle.setId("editobstacle");
        this.edit.getItems().addAll(editAirport, editObstacle);

        this.importMenu = new Menu("Import");
        this.importMenu.getItems().addAll(this.importform);

        this.export = new Menu("Export");
        this.exportObjects = new MenuItem("Airport/Obstacle");
        this.exportNotifications = new ExportMenuItem("Notifications");
        this.exportRevision = new Menu("Revision");
        this.exportSideOnView  = new ExportMenuItem("Side-On View");
        this.exportTopDownView = new ExportMenuItem("Top-Down View");
        this.exportSimultaneousView = new ExportMenuItem("Simultaneous View");
        this.exportCalculationBreakdown = new ExportMenuItem("Calculation Breakdown");
        this.exportRevision.getItems().addAll(this.exportSideOnView,
                                              this.exportTopDownView,
                                              this.exportSimultaneousView,
                                              this.exportCalculationBreakdown);
        this.exportRevision.setDisable(true); // can't be exported until a revision is performed
        this.export.getItems().addAll(this.exportObjects, this.exportNotifications, this.exportRevision);

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

        importform.setOnAction((e) -> {
            ImportForm form = new ImportForm("Import","Import");
            form.initOwner(this.getScene().getWindow());
            form.show();
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

        // EXPORT //


        this.exportNotifications.setOnAction((e) -> {
            // todo...
            // filename for the export
            String filename = "Notifications";

            // creating extension filters
            FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TEXT", "*.txt");
            FileChooser.ExtensionFilter[] filters = {txtFilter};

            // gathering the file selected by the user
            File file = this.exportNotifications.getFile(this.getScene().getWindow(), filename, filters);

            // storing data to file if file was chosen
            if(file != null){

                String str = "";

                //for each notificationm I append to the string
                //I cannot directly use the SystemController ArrayList because of the
                //saveToFile() only accepting byte[]
                for(Notification notification : SystemController.getDashboard().getNotificationPanel().getNotifications()){
                    str = str + notification.toString() + "\n";
                }

                // saving the string to the file
                this.exportCalculationBreakdown.saveToFile(str.getBytes(), file);
            }
        });

        this.exportObjects.setOnAction(e -> {
            ExportForm exform = new ExportForm ("Export Objects");
            exform.initOwner(this.getScene().getWindow());
            exform.show();
        });

        this.exportSideOnView.setOnAction(e -> {
            FileChooser.ExtensionFilter[] extensions =
                    new FileChooser.ExtensionFilter[] {
                            new FileChooser.ExtensionFilter("JPEG *.jpeg", "*.jpeg"),
                            new FileChooser.ExtensionFilter("PNG *.png", "*.png"),
                            new FileChooser.ExtensionFilter("GIF *.gif", "*.gif")};
            File fl = exportSideOnView.getFile(this.getScene().getWindow(), "Side-On View", extensions);
            String[] split = fl.toString().split("\\.");
            if(split == null || split.length < 2) {
                System.out.println("Error!");
                return;
            }
            exportSideOnView.saveImageToFile(SideWayView.instance.focusedSnapshot(), split[split.length-1], fl);
        });

        this.exportTopDownView.setOnAction(e -> {
            FileChooser.ExtensionFilter[] extensions =
                    new FileChooser.ExtensionFilter[] {
                            new FileChooser.ExtensionFilter("JPEG *.jpeg", "*.jpeg"),
                            new FileChooser.ExtensionFilter("PNG *.png", "*.png"),
                            new FileChooser.ExtensionFilter("GIF *.gif", "*.gif")};
            File fl = exportTopDownView.getFile(this.getScene().getWindow(), "Top-Down View", extensions);
            String[] split = fl.toString().split("\\.");
            if(split == null || split.length < 2) {
                System.out.println("Error!");
                return;
            }
            exportTopDownView.saveImageToFile(TopDownMap.instance.focusedSnapshot(), split[split.length-1], fl);
        });

        this.exportSimultaneousView.setOnAction(e -> {
            FileChooser.ExtensionFilter[] extensions =
                    new FileChooser.ExtensionFilter[] {
                            new FileChooser.ExtensionFilter("JPEG *.jpeg", "*.jpeg"),
                            new FileChooser.ExtensionFilter("PNG *.png", "*.png"),
                            new FileChooser.ExtensionFilter("GIF *.gif", "*.gif")};
            File fl = exportSimultaneousView.getFile(this.getScene().getWindow(), "Simultaneous Runway View", extensions);
            String[] split = fl.toString().split("\\.");
            if(split == null || split.length < 2) {
                System.out.println("Error!");
                return;
            }
            exportSimultaneousView.saveImageToFile(SimultaneousRunwayView.instance.focusedSnapshot(), split[split.length-1], fl);
        });

        this.exportCalculationBreakdown.setOnAction(e -> {
            // filename for the export
            String filename = "Calculation Breakdown";

            // creating extension filters
            FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG", "*.png");
            FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JPEG", "*.jpeg");
            FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF", "*.gif");
            FileChooser.ExtensionFilter[] filters = {pngFilter, jpegFilter, gifFilter};

            // gathering the file selected by the user
            File file = this.exportCalculationBreakdown.getFile(this.getScene().getWindow(), filename, filters);

            // storing data to file if file was chosen
            if(file != null){

                // getting the file type
                String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());

                // getting snapshot of calculation breakdown
                SnapshotParameters param = new SnapshotParameters();
                param.setDepthBuffer(true);
                WritableImage snapshot =  SystemController.getDashboard().getCalculationBreakdownView().getCalculationBreakdown().getContent().snapshot(param, null);

                // saving snapshot to the file
                this.exportCalculationBreakdown.saveImageToFile(snapshot, fileType, file);
            }
        });

        // adding items to the toolbar
        this.getMenus().addAll(this.file, this.edit,this.importMenu, this.export);
    }

    /**
     * Enables the menu items that allow for a revision to be exported.
     */
    public void allowRevisionExport(){
        // enabling menu items
        this.exportRevision.setDisable(false);
    }
}
