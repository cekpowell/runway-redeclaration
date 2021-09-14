package GUITesting;

import Controller.SystemController;
import IO.IOHandler;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GUITestUtils {
    public static boolean correctNotification(Label notification, String expected){
        String actual = getStringFromNotification(notification.getText());
        System.out.println(actual.stripLeading());
        return (actual.stripLeading().equals(expected));
    }

    public static String getStringFromNotification(String noti){
        String[] split = Arrays.copyOfRange(noti.split(" "),2,noti.split(" ").length);
        List<String> list = IntStream.range(0,split.length).
                filter(j-> (j != split.length -2) || (split[j]) != " ").
                mapToObj(j -> split[j]).collect(Collectors.toList());
        return String.join(" ",list);
    }

    public static void destroyIO(){
        IOHandler.destroyResources();
        SystemController.setAirports(FXCollections.observableArrayList());
        SystemController.setObstacles(FXCollections.observableArrayList());
    }
}
