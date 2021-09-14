package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents the title for the application's main page (purely aesthetic and no functionality).
 */
public class Title extends HBox {

    // defining the controls for the title page
    Label titleLabel;
    Label authorLabel;
    VBox container;

    public Title(String title, String author) {
        // setting the labels based on the parameters
        this.titleLabel = new Label(title);
        this.authorLabel = new Label("by " + author);

        // formatting the labels
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        authorLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

        // adding the labels to the container
        this.container = new VBox();
        this.container.getChildren().addAll(this.titleLabel, this.authorLabel);
        this.container.setAlignment(Pos.CENTER_RIGHT); // setting the alignment of the container

        // adding the container to the title
        this.getChildren().add(container);

        // formatting the title page
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));
    }
}