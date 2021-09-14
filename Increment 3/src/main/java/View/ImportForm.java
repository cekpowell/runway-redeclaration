package View;

import Controller.SystemController;
import Controller.XMLHandler;
import IO.Wrapper;
import Model.Airport;
import Model.Notification;
import Model.Obstacle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static View.Mode.*;

public class ImportForm extends Stage {

    private BorderPane form;
    private ConfirmationButton selectimport;

    private Object[] loadedObjects;

    private TextField selected;
    private File imported;
    private String importedtext;
    private Scene scene;

    public ImportForm(String title,String buttontext){
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle(title);
        Label titleform = new Label("Import by file or paste manually via text below");
        titleform.setAlignment(Pos.CENTER);
        titleform.setStyle("-fx-font-weight: bold");
        this.form = new BorderPane();
        this.form.setTop(titleform);
        this.selectimport = new ConfirmationButton(buttontext,"Confirm import","Are you sure? this may alter the system state");
        TextArea manual = new TextArea();
        form.setCenter(manual);

        Button loadcontent = new Button("Load");

        RadioButton formatfile = new RadioButton("File");
        formatfile.setUserData(FILE);
        RadioButton formattext = new RadioButton("Text");
        formattext.setUserData(TEXT);
        RadioButton replace = new RadioButton("Replace");
        replace.setUserData(REPLACE);
        RadioButton append = new RadioButton("Append");
        append.setUserData(APPEND);
        ToggleGroup group1 = new ToggleGroup();
        ToggleGroup group2 = new ToggleGroup();
        formatfile.setToggleGroup(group1);
        formattext.setToggleGroup(group1);
        replace.setToggleGroup(group2);
        append.setToggleGroup(group2);
        Label desc = new Label("Options:");

        this.scene = new Scene(form, 500, 500);

        this.selectimport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean removeConfirmed = selectimport.showConfirmationWindow("ImportForm", scene.getWindow());
                if(removeConfirmed) {
                    if (group1.getSelectedToggle() == null || group2.getSelectedToggle() == null ) {
                        ErrorAlert errorAlert = new ErrorAlert("You need to select a file or text mode and a replace or append mode");
                        errorAlert.showWindow(scene.getWindow());
                    } else {
                        Toggle toggle = group1.getSelectedToggle();
                        Toggle toggle1 = group2.getSelectedToggle();
                        Mode mode = (Mode) toggle.getUserData();
                        Mode mode2 = (Mode) toggle1.getUserData();
                        if (mode == FILE) {
                            importByFile(ImportForm.this.loadedObjects, mode2);
                        } else {
                            importByText(ImportForm.this.loadedObjects, mode2);
                        }
                    }
                }


            }
        });

        loadcontent.setOnAction((e) -> {
            try{
                Toggle toggle = group1.getSelectedToggle();
                if(toggle == null){
                    throw new Exception("You must choose file or text mode before loading");
                }
                Mode mode = (Mode) toggle.getUserData();
                if (mode == FILE) {
                    File file = selectFile();
                    ImportForm.this.imported = file;
                    Object obj = XMLHandler.loadObject(new FileInputStream(file));
                    setObject(obj);

                }
                else if (mode == TEXT) {
                    String text = manual.getText();
                    ImportForm.this.importedtext = text;
                    InputStream stream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
                    Object obj = XMLHandler.loadObject(stream);
                    setObject(obj);

                }
            } catch(Exception a){
                ErrorAlert errorAlert = new ErrorAlert(a.getMessage());
                errorAlert.showWindow(scene.getWindow());
            }
        });


        Label selectedfile = new Label("Status:");
        selected = new TextField();
        selected.setDisable(true);
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        HBox options = new HBox();
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        options.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        options.setSpacing(10);
        options.getChildren().addAll(desc,formatfile,formattext,replace,append);
        box.getChildren().addAll(selectedfile,selected,loadcontent,selectimport);
        vbox.getChildren().addAll(options,box);
        this.form.setBottom(vbox);

        // setting scene to stage
        this.setScene(scene);
    }

    public void setObject(Object obj) throws Exception {
        if(!(obj instanceof Wrapper)){
            throw new Exception("XML file is not correct");
        }
        else{
            Wrapper wrap = (Wrapper) obj;
            this.loadedObjects = wrap.getWrappedObj();
            if(wrap.getWrappedClass() == Airport.class){
                this.selected.setText("Airports loaded");
            }
            if(wrap.getWrappedClass() == Obstacle.class){
                this.selected.setText("Obstacles loaded");
            }
            if(wrap.getWrappedClass() == Notification.class){
                this.selected.setText("Notifications restored");
            }
            this.selected.setStyle("-fx-text-fill: red;-fx-font-weight: bold");
        }
    }

    public void importByFile(Object[] obj,Mode mode){
        try {
            if(obj == null){
                throw new Exception("You need to press load first");
            }
            performImport(mode,obj);
            this.close();
        } catch (Exception e){
            ErrorAlert errorAlert = new ErrorAlert(e.getMessage());
            errorAlert.showWindow(this.scene.getWindow());
        }
    }

    public void importByText(Object[] obj,Mode mode){

        try {
            if(obj == null){
                throw new Exception("You need to press load first");
            }
            performImport(mode,obj);
            this.close();
        } catch (Exception e){
            ErrorAlert errorAlert = new ErrorAlert(e.getMessage());
            errorAlert.showWindow(this.scene.getWindow());
        }
    }


    public void performImport(Mode mode,Object obj) throws Exception {
           boolean isairport = obj instanceof Airport[];
           boolean isobstacle = obj instanceof Obstacle[];
           boolean isnotification = obj instanceof Notification[];
           if(mode == REPLACE && isairport){
               SystemController.clearAirports();
           }
           else if(mode == REPLACE && isobstacle){
            SystemController.clearObstacles();
           }
            //maybe allow notis
           if(isairport){
                for(Airport a:(Airport[]) obj){
                    SystemController.addAirport(a);
                }
           }
           else if(isobstacle){
                for(Obstacle o:(Obstacle[]) obj){
                    SystemController.addObstacle(o);
                }
           }
           else if(isnotification && mode == APPEND){
               throw new Exception("You cannot use append for notification import");
           }
           else if(isnotification && mode == REPLACE){
               SystemController.clearNotifications();
               for(Notification n:(Notification[]) obj){
                   SystemController.getDashboard().getNotificationPanel().displayNotification(n);
               }
               SystemController.autoSave();
           }

    }

    public File selectFile(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(this);
        return selectedFile;
    }

    public File getImported() {
        return imported;
    }

    public void setImported(File imported) {
        this.imported = imported;
    }

    public String getImportedtext() {
        return importedtext;
    }

    public void setImportedtext(String importedtext) {
        this.importedtext = importedtext;
    }

}
enum Mode{
    FILE,
    TEXT,
    REPLACE,
    APPEND

        }
