package View;

import Controller.SystemController;
import Controller.XMLHandler;
import IO.Wrapper;
import Model.Airport;
import Model.Obstacle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ExportForm extends Stage {
    private BorderPane pane;
    private ListView selectedmode;
    private ToggleGroup toggle;
    public ExportForm(String title){
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle(title);
        pane = new BorderPane();


        selectedmode = new ListView();
        selectedmode.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        HBox options = new HBox();
        options.setSpacing(10);
        Button export = new Button("Export selected");
        export.setOnAction((e) -> export());
        Label opts = new Label("Export options");
        RadioButton airports = new RadioButton("Airports");
        airports.setUserData(Airport.class);
        RadioButton obstacles = new RadioButton("Obstacles");
        obstacles.setUserData(Obstacle.class);
        toggle = new ToggleGroup();
        ObservableList<Object> list = FXCollections.observableArrayList();
        selectedmode.setItems(list);
        airports.setOnAction((e) -> {
            selectedmode.getItems().clear();
            selectedmode.getItems().addAll(SystemController.getAirports());
        });
        obstacles.setOnAction((e) -> {
            selectedmode.getItems().clear();
            selectedmode.getItems().addAll(SystemController.getObstacles());
        });

        airports.setToggleGroup(toggle);
        obstacles.setToggleGroup(toggle);


        options.getChildren().addAll(opts,airports,obstacles,export);
        pane.setCenter(selectedmode);
        pane.setBottom(options);
        Scene scene = new Scene(pane, 500, 500);
        // setting scene to stage
        this.setScene(scene);


    }

    public void export(){
        ObservableList<Object> list= this.selectedmode.getSelectionModel().getSelectedItems();
        FileChooser fileChooser = new FileChooser();
        Wrapper wrap = null;
        if(toggle.getSelectedToggle().getUserData() == Airport.class){
            wrap = new Wrapper<Airport>(Airport.class);
            Airport[] air = new Airport[list.size()];
            for(int i = 0; i < list.toArray().length;i++){
                air[i] = (Airport) list.toArray()[i];
            }
            wrap.setWrappedObj(air);
        }
        else if(toggle.getSelectedToggle().getUserData() == Obstacle.class){
            wrap = new Wrapper<Obstacle>(Obstacle.class);
            Obstacle[] obs = new Obstacle[list.size()];
            for(int i = 0; i < list.toArray().length;i++){
                obs[i] = (Obstacle) list.toArray()[i];
            }
            wrap.setWrappedObj(obs);

        }
        fileChooser.setInitialFileName("objects.xml");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files","*.xml"));
        File file = fileChooser.showSaveDialog(this);
        if(file != null){
            try {
                XMLHandler.saveObjectAsXML(wrap,file);
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Export complete",ButtonType.CLOSE);
                alert.showAndWait();
            } catch (IOException e) {
                ErrorAlert errorAlert = new ErrorAlert("Export fail try again");
                errorAlert.showWindow(this);
            }
        }
    }


}
