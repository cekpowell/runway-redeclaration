package View;

import Controller.SystemController;
import Controller.Validator;
import IO.IOHandler;
import Model.*;
import javafx.collections.FXCollections;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AirportForm extends VBox{

    ArrayList<HBox> rows;
    ObservableList<PhysicalRunway> addedrunways;
    ObservableList<Airport> airports;
    TextField Tname;
    TextField TIata;
    ComboBox runway;
    ComboBox airp;
    Button removeRun;
    Airport selected;
    public AirportForm(){
        super();
        rows = new ArrayList<HBox>();
        addedrunways = FXCollections.observableArrayList();
        airports = SystemController.getAirports();
        setAirportBox();
    }


    public void setAirportBox(){
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


        //Spacing
        row1.setSpacing(50);
        row2.setSpacing(50);
        row3.setSpacing(50);
        row4.setSpacing(50);
        row5.setSpacing(50);
        row6.setSpacing(50);
        runway = new ComboBox();
        runway.setDisable(true);
        airp = new ComboBox();
        airp.setItems(SystemController.getAirports());

        Label select = new Label("Select Airport");
        Label name = new Label("Name");
        Label IATA = new Label("IATA");
        Label addRun = new Label("Select Runway");
        removeRun = new Button("Remove Runway");
        removeRun.setDisable(true);
        Button addRunN = new Button("Add New Runway");
        addRunN.setDisable(true);
        Image img = new Image("edit.png");
        Image del = new Image("del.png");
        Button editrow2 = new Button("",new ImageView(img));
        editrow2.setDisable(true);
        Button editrow3 = new Button("",new ImageView(img));
        editrow3.setDisable(true);

        Button removeairport = new Button("",new ImageView(del));
        removeairport.setDisable(true);
        Tname = new TextField();
        Tname.setDisable(true);
        TIata = new TextField();
        TIata.setDisable(true);
        row1.getChildren().addAll(select,airp,removeairport);
        row2.getChildren().addAll(name,Tname,editrow2);
        row3.getChildren().addAll(IATA,TIata,editrow3);
        row4.getChildren().addAll(addRun,runway,removeRun,addRunN);
        this.getChildren().clear();
        this.getChildren().addAll(row1,row2,row3,row4);
        addRunN.setOnAction(((e) -> {
            // opening the add new runway form
            AddNewPhysicalRunwayForm addNewRunwayForm = new AddNewPhysicalRunwayForm();
            PhysicalRunway newRunway = addNewRunwayForm.showWindow();

            // testing if a runway was returned and adding it to the list if it was
            if(!(newRunway == null)){
                // adding the new runway to the list, and to the forms
                this.addedrunways.add(newRunway); // adding new runway to the list
            }
        }));

        airp.setOnAction((e) -> {
            if(airp.getValue() != null && (Airport) airp.getValue() != selected) {
                updateAirport();
                removeairport.setDisable(false);
                selected = (Airport) airp.getValue();
                Tname.setText(selected.getName());
                TIata.setText(selected.getIATA());
                this.addedrunways.clear();
                this.addedrunways.addAll(selected.getRunways());
            }
            editrow2.setDisable(false);
            editrow3.setDisable(false);
            runway.setDisable(false);
            addRunN.setDisable(false);
            Tname.setText(selected.getName());
            TIata.setText(selected.getIATA());
            runway.setItems(addedrunways);
        });
        editrow2.setOnAction((e) -> {
            if(selected != null){
                Tname.setDisable(false);
            }
        });
        editrow3.setOnAction((e) -> {
            if(selected!= null){
                TIata.setDisable(false);
            }
        });

        runway.setOnAction((e) -> {
            removeRun.setDisable(false);
        });

        removeRun.setOnAction((e) -> {
            this.addedrunways.remove((PhysicalRunway)runway.getValue());
        });

        removeairport.setOnAction((e) ->{
            this.airports.remove(selected);
            this.addedrunways.clear();
            removeairport.setDisable(true);
            Tname.setDisable(true);
            TIata.setDisable(true);
            editrow2.setDisable(true);
            editrow3.setDisable(true);
        });

    }

    public ObservableList<Airport> getAirport(){
        return airports;
    }

    public Airport getSelected() {
        return selected;
    }

    public void setSelected(Airport selected) {
        this.selected = selected;
    }

    public void updateAirport(){
        if (selected != null) {
            Validator val = Validator.getValidator();
            try {
                ArrayList<PhysicalRunway> runw = new ArrayList<PhysicalRunway>();
                for(PhysicalRunway e: this.addedrunways){
                    runw.add(e);
                }
                val.validateAirportEdit(selected,Tname.getText(),TIata.getText(),runw);
                selected.setName(Tname.getText());
                selected.setIATA(TIata.getText());
                System.out.println("updating current runways" + this.addedrunways.size());
                System.out.println("Setting " + selected.getIATA() + " airports to" + runw.size());
                selected.setRunways(FXCollections.observableArrayList(runw));
            } catch (ValidationException exception) {
                ErrorAlert errorAlert = new ErrorAlert(exception);
                errorAlert.showAndWait();
            }
        }
    }


}
