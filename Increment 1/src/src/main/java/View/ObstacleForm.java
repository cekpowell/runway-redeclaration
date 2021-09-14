package View;

import Controller.SystemController;
import Controller.Validator;
import IO.IOHandler;
import Model.Obstacle;
import Model.ValidationException;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ObstacleForm extends VBox {
    public ObstacleForm(){
        rows = new ArrayList<HBox>();
        obstaclelist = SystemController.getObstacles();
        fields = new ArrayList<TextField>();
        setObstacleBox();
    }
    ArrayList<HBox> rows;
    ComboBox obstacles;
    Button editrow2;
    Button editrow3;
    Button editrow4;
    Button editrow5;
    ObservableList<Obstacle> obstaclelist;
    TextField Tname;
    TextField Twidth;
    TextField Tlength;
    TextField Theight;
    TextField byIDD;
    ArrayList<TextField> fields;

    Obstacle selected;

    public void setObstacleBox(){
        this.setSpacing(50);
        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox row3 = new HBox();
        HBox row4 = new HBox();
        HBox row5 = new HBox();
        HBox row6 = new HBox();
        this.rows.add(row1);
        this.rows.add(row2);
        this.rows.add(row3);
        this.rows.add(row4);
        this.rows.add(row5);
        this.rows.add(row6);
        //layout
        row1.setAlignment(Pos.CENTER);
        row2.setAlignment(Pos.CENTER);
        row3.setAlignment(Pos.CENTER);
        row4.setAlignment(Pos.CENTER);
        row5.setAlignment(Pos.CENTER);
        row6.setAlignment(Pos.CENTER);
        //Spacing
        row1.setSpacing(50);
        row2.setSpacing(50);
        row3.setSpacing(50);
        row4.setSpacing(50);
        row5.setSpacing(50);
        row6.setSpacing(50);


        //Labels
        Label selectobstacle = new Label("Select Obstacle");
        Label name = new Label("Name");
        Label width = new Label("width");
        Label length = new Label("length");
        Label height = new Label("height");
        Label byID = new Label("Search");

        //Textfields
        Tname = new TextField();
        Twidth = new TextField();
        Tlength = new TextField();
        Theight = new TextField();
        byIDD = new TextField();
        this.fields.add(Tname);
        this.fields.add(Twidth);
        this.fields.add(Tlength);
        this.fields.add(Theight);
        Theight.setDisable(true);
        Tlength.setDisable(true);
        Twidth.setDisable(true);
        Tname.setDisable(true);
        //Other
        //Icons
        Image img = new Image("edit.png");
        editrow2 = new Button("",new ImageView(img));
        editrow3 = new Button("",new ImageView(img));
        editrow4 = new Button("",new ImageView(img));
        editrow5 = new Button("",new ImageView(img));

        Image delete = new Image("del.png");
        Button del = new Button("",new ImageView(delete));

        Button prop = new Button("Properties");

        obstacles = new ComboBox();
        obstacles.setItems(SystemController.getObstacles());
        obstacles.setOnAction((e) -> {
            if(obstacles.getValue() != null && (Obstacle) obstacles.getValue() != selected){
                updateCurrent();
                setDisableFields(false);
                selected= (Obstacle) obstacles.getValue();
                Tname.setText(selected.getName());
                Twidth.setText(String.valueOf(selected.getWidth()));
                Tlength.setText(String.valueOf(selected.getLength()));
                Theight.setText(String.valueOf(selected.getHeight()));
            }


        });
        obstacles.setPrefWidth(150);

        editrow2.setOnAction((e) -> {
            if(obstacles.getValue() != null){
                Tname.setDisable(false);
            }
        });
        editrow3.setOnAction((e) -> {
            if(obstacles.getValue() != null){
                Twidth.setDisable(false);
            }
        });
        editrow4.setOnAction((e) -> {
            if(obstacles.getValue() != null){
                Tlength.setDisable(false);
            }
        });
        editrow5.setOnAction((e) -> {
            if(obstacles.getValue() != null){
                Theight.setDisable(false);
            }
        });

        del.setOnAction((e) -> {
            if(obstacles.getValue() != null){
                this.obstaclelist.remove((Obstacle) obstacles.getValue());
            }
            selected = null;
            clearFields();
            setDisableFields(true);
        });

        setDisableFields(true);
        //Construct tree
        row1.getChildren().addAll(selectobstacle,obstacles,del,byIDD);
        row2.getChildren().addAll(name,Tname,editrow2);
        row3.getChildren().addAll(width,Twidth,editrow3);
        row4.getChildren().addAll(length,Tlength,editrow4);
        row5.getChildren().addAll(height,Theight,editrow5);
        row6.getChildren().add(prop);
        this.getChildren().addAll(row1,row2,row3,row4,row5,row6);
    }

    public Obstacle getSelected() {
        return selected;
    }

    public void setSelected(Obstacle selected) {
        this.selected = selected;
    }

    public ObservableList<Obstacle> getObstaclelist(){
        return this.obstaclelist;
    }

    public void updateCurrent() {
        if (selected != null) {
            Validator val = Validator.getValidator();
            try {
                val.validateObstacleEdit(selected,Tname.getText(), String.valueOf(selected.getId()), Twidth.getText(), Theight.getText(), Tlength.getText());
                selected.setName(Tname.getText());
                selected.setId(selected.getId());
                selected.setWidth(Double.valueOf(Twidth.getText()));
                selected.setHeight(Double.valueOf(Theight.getText()));
                selected.setLength(Double.valueOf(Tlength.getText()));
            } catch (ValidationException exception) {
                ErrorAlert errorAlert = new ErrorAlert(exception);
                errorAlert.showAndWait();
            }
        }
    }

    public void clearFields(){
        for(TextField e:this.fields){
            e.clear();
        }

    }

    public void setDisableFields(boolean a){
        this.editrow2.setDisable(a);
        this.editrow3.setDisable(a);
        this.editrow4.setDisable(a);
        this.editrow5.setDisable(a);

    }

}