package Utils;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import java.util.ArrayList;

/**
 * Class for adding details to teh top-down view of a runway
 */
public class TopDownViewDetails {

    /**
     * Uses the rectangle that acts as a body to the runway, an offset, a width and a height to place the stripe marking of the runway.
     * @param rectangle The rectangle
     * @param xOffset The offset to place the stripe.
     * @param width The width of the stripe.
     * @param height The height of the stripe.
     */
    static public Rectangle[] getLines(Rectangle rectangle, int xOffset, int width, int height) {
        ArrayList<Rectangle> recto = new ArrayList<>();
        for(int i = height; i < rectangle.getHeight(); i+=2*height) {
            Rectangle rectangleLocal = new Rectangle((int)rectangle.getX() + xOffset, (int)rectangle.getY() + i, width, height);
            rectangleLocal.setFill(Color.WHITE);
            recto.add(rectangleLocal);
        }
        return recto.toArray(new Rectangle[recto.size()]);
    }

    /**
     * Uses the rectangle that acts as a body to the runway and an offset to place the line along the runway.
     * @param r The rectangle
     * @param xOffset The offset to place the line.
     */
    static public Line getLine(Rectangle r, int xOffset) {
        Line lineLocal = new Line(r.getX() + xOffset, r.getY() + r.getHeight()/2, r.getWidth() - xOffset, r.getY() + r.getHeight()/2 );
        lineLocal.setStroke(Color.WHITE);
        lineLocal.getStrokeDashArray().addAll(20d, 5d);
        return lineLocal;
    }

    /**
     * Uses the rectangle that acts as a body to the runway, an offset, a heading and boolean a heading on the runway.
     * @param r The rectangle
     * @param xOffset The offset to place the heading.
     * @param heading The heading itself.
     * @param isStarting Whether this is a heading at the start (which would be the landing direction) or the end of the runway.
     */
    static public Text getHeading(Rectangle r, int heading, int xOffset, boolean isStarting) {
        int findSize = (int) r.getHeight() / 2;
        String fontStyle = "-fx-font: " + String.valueOf(findSize) + "px Tahoma";
        Text text;

        heading = heading == 0 ? 36 : heading;
        String string;
        if (isStarting) {
            string = String.format("%02d", heading);
        } else {
            heading = heading + 18 > 36 ? heading - 18 : heading + 18;
            string = String.format("%02d", heading);
        }
        if (isStarting) {
            text = new Text(r.getX() + xOffset, r.getY() + r.getHeight() * 2 / 3, string);
            text.setRotate(90);
        } else {
            text = new Text(r.getX() + r.getWidth() - xOffset, r.getY() + r.getHeight() * 2 / 3, string);
            text.setRotate(270);
        }

        text.setStyle(fontStyle);
        text.setFill(Color.WHITE);
        return text;
    }


    /**
     * Uses the rectangle that acts as a body to the runway to add a Rectangle representing a graded area.
     * @param r The rectangle
     */
    static public Rectangle getGradedArea(Rectangle r) {
        System.out.println("W: " + (r.getX() - r.getWidth() / 8));
        Rectangle graded = new Rectangle(r.getX() - r.getWidth() / 8, r.getY() - 3 * r.getHeight(), r.getWidth() + r.getWidth() * 2/8 , r.getHeight() * 7);

        Color color = Color.web("CC92C2");
        graded.setFill(color);
        return graded;
    }

    /**
     * Uses the rectangle that acts as a body to the runway to add a Polygon representing a cleared area.
     * @param r The rectangle
     */
    static public Polygon getClearedArea(Rectangle r) {
        Polygon clear = new Polygon();
        double displacement = r.getWidth() / 8;
        clear.getPoints().addAll(r.getX() - displacement, r.getY() - r.getHeight(),
                r.getX(), r.getY() - r.getHeight(),
                r.getX() + displacement, r.getY() - 2 * r.getHeight(),
                r.getX() + r.getWidth() - displacement, r.getY() - 2 * r.getHeight(),
                r.getX() + r.getWidth(), r.getY() - r.getHeight(),
                r.getX() + r.getWidth() + displacement, r.getY() - r.getHeight(),
                r.getX() + r.getWidth() + displacement, r.getY() + 2 * r.getHeight(),
                r.getX() + r.getWidth(), r.getY() + 2 * r.getHeight(),
                r.getX() + r.getWidth() - displacement, r.getY() + 3 * r.getHeight(),
                r.getX() + displacement, r.getY() + 3 * r.getHeight(),
                r.getX(), r.getY() + 2 * r.getHeight(),
                r.getX() - displacement, r.getY() + 2 * r.getHeight());

        Color color = Color.web("5B85AA");
        clear.setFill(color);
        return clear;
    }
    static public Group getLegend(Rectangle gradedArea) {
        Rectangle body = new Rectangle(gradedArea.getX(), gradedArea.getY() + gradedArea.getHeight() * 3/4, gradedArea.getWidth() *3/8, gradedArea.getHeight() * 1/4 );
        body.setFill(Color.WHITE);
        double height = body.getHeight() /5;
        int findSize = (int) height;
        String fontStyle = "-fx-font: " + String.valueOf(findSize) + "px Tahoma";

        Rectangle clearedArea = new Rectangle(body.getX() + body.getWidth() * 1/20, body.getY() +body.getHeight() * 1/20, body.getWidth() /10, height);
        Color color = Color.web("5B85AA");
        Text clearText = new Text(clearedArea.getX() + clearedArea.getWidth(),clearedArea.getY() + height, " Cleared and Graded Area");
        clearText.setFill(Color.BLACK);
        clearText.setStyle(fontStyle);
        clearedArea.setFill(color);

        Rectangle gArea = new Rectangle(body.getX() + body.getWidth() * 1/20, body.getY() +body.getHeight() * 2/5, body.getWidth() /10, height);
        Color color2 = Color.web("CC92C2");
        gArea.setFill(color2);
        Text gradedText = new Text(gArea.getX() + gArea.getWidth(),gArea.getY() + height, " Empty Area");
        gradedText.setFill(Color.BLACK);
        gradedText.setStyle(fontStyle);

        Rectangle freeArea = new Rectangle(body.getX() + body.getWidth() * 1/20, body.getY() +body.getHeight() * 3/4, body.getWidth() /10, height);
        freeArea.setFill(Color.DARKSALMON);
        Text freeText = new Text(freeArea.getX() + freeArea.getWidth(),freeArea.getY() + height, " Redeclared Runway");
        freeText.setFill(Color.BLACK);
        freeText.setStyle(fontStyle);

        Group group = new Group(body,clearedArea,gArea,freeArea,clearText,gradedText,freeText);
        return group;
    }
}
