package View;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import javax.swing.text.html.Option;
import java.util.Optional;

public class Arrow extends Group {
    boolean doublesided;
    double width;
    private Line line;
    private double startx;

    public double getStartx() {
        return startx;
    }

    public void setStartx(double startx) {
        this.startx = startx;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    private double endX;
    private double startY;
    private double endY;
    Polygon arrowhead1;
    Polygon arrowhead2;
    Optional<Boolean> rl;
    //true is right
    //false is left
    public Arrow (double startX,double endX, double startY, double endY, boolean doublesided, Optional<Boolean> rl) {
        super();
        this.rl = rl;
        this.startx = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.doublesided = doublesided;
        this.width = width;
        this.line = new Line();
        init();
    }

    public void init(){
        if(this.doublesided){
            this.line.setStartX(startx +5);
            this.line.setEndX(endX -5);
            this.line.setStartY(startY);
            this.line.setEndY(endY);
            Polygon arrowhead1 = arrowHead(true);
            Polygon arrowhead2 = arrowHead(false);
            getChildren().addAll(line,arrowhead1,arrowhead2);
        }
        else if(rl.isPresent() && rl.get() == true){
            this.line.setStartX(startx);
            this.line.setEndX(endX -5);
            this.line.setStartY(startY);
            this.line.setEndY(endY);
            Polygon arrowhead1 = arrowHead(true);
            getChildren().addAll(line,arrowhead1);
        }
        else if(rl.isPresent()  && rl.get() ==false){
            this.line.setStartX(startx +5);
            this.line.setEndX(endX);
            this.line.setStartY(startY);
            this.line.setEndY(endY);
            Polygon arrowhead1 = arrowHead(false);
            getChildren().addAll(line,arrowhead1);
        }
    }

    public Polygon arrowHead(boolean right){
        Polygon arrowhead = new Polygon();
        if(right){
            arrowhead.getPoints().addAll(new Double[] {
                    this.line.getEndX(),this.line.getEndY() - 7,this.line.getEndX(), this.line.getEndY() +7,this.getEndX(),this.line.getEndY(),
            });
        }
        else{
            arrowhead.getPoints().addAll(new Double[] {
                    this.line.getStartX(),this.line.getStartY() - 7,this.line.getStartX(), this.line.getStartY() +7,this.startx,this.line.getStartY()
            });
        }
        return arrowhead;
    }
}
