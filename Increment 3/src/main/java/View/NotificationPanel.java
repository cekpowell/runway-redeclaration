package View;

import Controller.SystemController;
import Model.Notification;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Handles the display of notifications to the user.
 */
public class NotificationPanel extends VBox {

    public int count;
    // member variables
    private Dashboard dashboard;
    private ScrollPane notificationScrollPanel;
    private ArrayList<Notification> notifications;
    private VBox notificationContainer;
    private ConfirmationButton clearNotifications;

    /**
     * Constructor for the class.
     * @param dashboard The dashboard that the notification panel is being displayed on.
     */
   public NotificationPanel(Dashboard dashboard){

       this.count = 0;
       // initialising member vairables
       this.dashboard = dashboard;
       this.notificationScrollPanel = new ScrollPane();
       this.notificationScrollPanel.setFitToWidth(true);
       this.notificationScrollPanel.setPrefHeight(100);
       this.notifications = new ArrayList<Notification>();
       this.notificationContainer = new VBox();
       notificationContainer.setMaxHeight(notificationScrollPanel.getMaxHeight());
       this.clearNotifications = new ConfirmationButton("Clear", "Clear Notifications", "Are you sure you want to permanently remove the notifications?");
       this.clearNotifications.setDisable(true);

       // action for clear notifications button
       this.clearNotifications.setOnAction(e -> {
           // displaying confirmation window
           boolean clearConfirmed = this.clearNotifications.showConfirmationWindow("", this.getScene().getWindow());

           // clearing the notifications if the action is confirmed
           if(clearConfirmed){
               // clearing the notificatoins
               SystemController.clearNotifications();
               this.clearNotifications.setDisable(true);
           }
       });

       // configuring title label
       Label titleLabel = new Label("NOTIFICATIONS");
       titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

       // container for title
       HBox titleContainer = new HBox(titleLabel);
       HBox.setHgrow(titleContainer, Priority.ALWAYS);

       // container for clear button
       HBox buttonContainer = new HBox(this.clearNotifications);
       buttonContainer.setAlignment(Pos.TOP_RIGHT);

       // title for container and header
       HBox headerContainer = new HBox();
       headerContainer.getChildren().addAll(titleContainer, buttonContainer);

       // adding main container to view
       this.getChildren().addAll(headerContainer, this.notificationScrollPanel);

       // formatting
       this.setAlignment(Pos.TOP_LEFT);
       this.setPadding(new Insets(20,20,20,20));
   }

    /**
     * Displays the given notification within the notification panel.
     * @param notificiation The notification to be displayed within the panel.
     */
   public void displayNotification(Notification notificiation){
       this.notifications.add(notificiation);
       //Label notificationMessage = new Label(notificiation.toString(notificiation.getMessage(), notificiation.getTime()));
       Label notificationMessage = new Label(notificiation.toString());
       notificationMessage.setId("noti"+String.valueOf(count));
       notificationMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));
       if(notificiation.getType().equals(Notification.Type.ADD)) notificationMessage.setTextFill(Color.valueOf(Notification.Type.ADD.getColor()));
       else if(notificiation.getType().equals(Notification.Type.REMOVE)) notificationMessage.setTextFill(Color.valueOf(Notification.Type.REMOVE.getColor()));
       else if(notificiation.getType().equals(Notification.Type.EDIT)) notificationMessage.setTextFill(Color.valueOf(Notification.Type.EDIT.getColor()));
       else if(notificiation.getType().equals(Notification.Type.REVISION)) notificationMessage.setTextFill(Color.valueOf(Notification.Type.REVISION.getColor()));

       notificationContainer.getChildren().add(notificationMessage);
       this.notificationScrollPanel.setContent(notificationContainer);
       notificationScrollPanel.applyCss();
       notificationScrollPanel.layout();
       notificationScrollPanel.setVvalue(1d);
       count++;

       this.clearNotifications.setDisable(false);
   }


    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void clearPanel(){
       this.notificationContainer.getChildren().clear();
       this.notifications.clear();

    }
}
