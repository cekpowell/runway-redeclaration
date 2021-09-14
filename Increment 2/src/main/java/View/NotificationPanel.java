package View;

import Model.Notification;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import java.util.ArrayList;

/**
 * Handles the display of notifications to the user.
 */
public class NotificationPanel extends VBox {

    public int count;
    // member variables
    private Dashboard dashboard;
    private ScrollPane notificationScrollPanel;
    private ArrayList<Label> notifications;
    VBox notificationContainer;

    /**
     * Constructor for the class.
     * @param dashboard The dashboard that the notification panel is being displayed on.
     */
   public  NotificationPanel(Dashboard dashboard){

       this.count = 0;
       // initialising member vairables
       this.dashboard = dashboard;
       this.notificationScrollPanel = new ScrollPane();
       this.notificationScrollPanel.setFitToWidth(true);
       this.notificationScrollPanel.setPrefHeight(100);
       this.notifications = new ArrayList<Label>();
       this.notificationContainer = new VBox();
       notificationContainer.setMaxHeight(notificationScrollPanel.getMaxHeight());

       // configuring title label
       Label titleLabel = new Label("NOTIFICATIONS");
       titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

       // adding main container to view
       this.getChildren().addAll(titleLabel, this.notificationScrollPanel);

       // formatting
       this.setAlignment(Pos.TOP_LEFT);
       this.setPadding(new Insets(20,20,20,20));
   }

    /**
     * Displays the given notification within the notification panel.
     * @param notificiation The notification to be displayed within the panel.
     */
   public void displayNotification(Notification notificiation){
       //Label notificationMessage = new Label(notificiation.toString(notificiation.getMessage(), notificiation.getTime()));
       Label notificationMessage = new Label(notificiation.toString(notificiation.getMessage()));
       notificationMessage.setId("noti"+String.valueOf(count));
       notificationMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));
       notificationMessage.setTextFill(Color.RED);
       notificationContainer.getChildren().add(notificationMessage);
       this.notificationScrollPanel.setContent(notificationContainer);
       notificationScrollPanel.applyCss();
       notificationScrollPanel.layout();
       notificationScrollPanel.setVvalue(1d);
       count++;
   }
}
