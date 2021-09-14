package IO;


import Controller.SystemController;
import Controller.XMLHandler;
import Model.Airport;
import Model.Notification;
import Model.Obstacle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class IOHandler{
    public static String resourceDir = "resources";
    public static String currentpath =System.getProperty("user.dir");;
    public static String seperator = System.getProperty("file.separator");
    public static String resourceAbs = currentpath + seperator + resourceDir;
    public static String javaResources = currentpath+seperator+"src" + seperator + "main" + seperator + resourceDir;
    public static boolean isXML(Path path, LinkOption... options){
        File file = new File(path.toUri());
        String filename = file.getName();
        String[] ext = filename.split("\\.");
        if(ext[ext.length -1].equals("xml")){
            return  true;
        }
        return false;

    }

    public static void loadFiles(ArrayList<Path> paths) throws IOException {
        for(Path p: paths){
            Wrapper wrap = (Wrapper) XMLHandler.loadObject(p.toString());
            Class type = wrap.getWrappedClass();
            if(type == Airport.class){
                ArrayList<Airport> airports = new ArrayList<Airport>();
                Airport[] airp = (Airport[]) wrap.getWrappedObj();
                Collections.addAll(airports,airp);
                ObservableList<Airport> ap = FXCollections.observableList(airports);
                SystemController.setAirports(ap);
            } else if (type == Obstacle.class) {
                ArrayList<Obstacle> obs = new ArrayList<Obstacle>();
                Obstacle[] obst = (Obstacle[]) wrap.getWrappedObj();
                Collections.addAll(obs,obst);
                ObservableList<Obstacle> obser = FXCollections.observableList(obs);
                SystemController.addObstacles(obser);
            } else if (type == Notification.class){
                Notification[] notis = (Notification[]) wrap.getWrappedObj();
                SystemController.existingNotifications = true;
                SystemController.existingnoti = notis;
            }

            else {
                //ERROR
                System.out.println("error");
            }
        }
    }
    public static void loadResources() throws IOException {
        loadPredefined();
        if(new File(resourceDir).mkdirs() == false){
            String resfolder = currentpath + seperator + "resources";
            ArrayList<Path> paths = new ArrayList<Path>();
            Files.walk(Paths.get(resfolder),1).filter(IOHandler::isXML).forEach(paths::add);
            loadFiles(paths);

        }
        else{
            System.out.println("creating dir");
            return;
        }
    }

    public static void destroyResources() {
        String resfolder = currentpath + seperator + "resources";
        File file2 = new File(resfolder);
        System.out.println(file2.listFiles().length);
        for(File file: file2.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    public static void loadPredefined() throws IOException {
        InputStream in = IOHandler.class.getClassLoader().getResourceAsStream("predefinedList.xml");
        ArrayList<Obstacle> obs = (ArrayList<Obstacle>) XMLHandler.loadObject(in);
        SystemController.addObstacles(FXCollections.observableList(obs));
    }



}