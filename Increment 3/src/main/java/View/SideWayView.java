package View;

import Model.FlightMethod;
import Model.LogicalRunway;
import Model.LogicalRunwayParameters;
import Model.RevisedLogicalRunway;
import javafx.geometry.Side;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.w3c.dom.css.Rect;

import java.text.DecimalFormat;
import java.util.Optional;

public class SideWayView extends BorderPane {

    public static SideWayView instance;

    private Line line;
    private Arrow landingdistance;
    private Arrow heightairp;
    private Arrow RESA;
    private Arrow landingthreshold;
    FlightMethod method;
    private Arrow h50;
    private Arrow LDA;
    private Arrow takeoffrun;
    private Arrow engineblast;
    private Rectangle obstacle;

    public double getStartx() {
        return startx;
    }

    public void setStartx(double startx) {
        this.startx = startx;
    }

    public double getStarty() {
        return starty;
    }

    public void setStarty(double starty) {
        this.starty = starty;
    }

    private double startx;
    private double starty;
    private double runwaylength;
    private RevisedLogicalRunway runway;

    private boolean title;

    public SideWayView (LogicalRunway original, RevisedLogicalRunway runway, double width, double height,boolean title){

        instance = this;

        this.runway = runway;
        this.method = runway.getFlightMethod();
        this.runwaylength = original.getParameters().getToda();
        // configuring the view
        this.setWidth(width);
        this.setHeight(height);
        this.title = title;
        // setting up the view
        this.init();
    }

    public void init(){
        LogicalRunwayParameters param = runway.getParameters();
        double displacedthresh = ((this.runway.getParameters().getDisplacedThreshold()/runwaylength) * this.getWidth());
        if(method.toString() == FlightMethod.LANDING_OVER.toString()){
            setObstacle(true);
            setLine();
            double resa = runway.getResaDistance();
            double lda = param.getLda();
            double obstaclescaled = (runway.getObstaclePosition().getDistanceFromThreshold() / runwaylength) * this.getWidth();
            setStartx(this.getWidth() - obstaclescaled - displacedthresh);
            setStarty(this.getHeight()/2);
            double lsa = runway.getAlstocs();
            this.setRESA(runway.getResaDistance(),this.startx - ((resa/runwaylength)*this.getWidth()),this.startx,starty +10,starty+10);
            this.setH50(runway.getAlstocs(),"ALS",this.startx - ((lsa/runwaylength)*this.getWidth()),this.startx,starty+45,starty+45);
            if(this.h50.getStartx() < this.RESA.getStartx()){
                this.setLandingThreshold(60,this.h50.getStartx() - ((60/runwaylength)*this.getWidth()),this.h50.getStartx(),this.starty+10,this.starty+10);
            }
            else{
                this.setLandingThreshold(60,this.RESA.getStartx() - ((60/runwaylength)*this.getWidth()),this.RESA.getStartx(),this.starty+10,this.starty+10);
            }
            this.setLDA(lda,this.landingthreshold.getStartx() - ((lda/runwaylength)*this.getWidth()),this.landingthreshold.getStartx(),this.starty +10 ,this.starty +10);
            double middlelda = (this.LDA.getEndX() - this.LDA.getStartx())/2;
            double quarterheight = this.getHeight() / 4;
            this.setTakeOffLandingDirection(false,"Landing Direction",middlelda,this.LDA.getEndX(),quarterheight,quarterheight);
        }
        else if(method.toString() == FlightMethod.LANDING_TOWARDS.toString()){
            setObstacle(false);
            setLine();
            double resa = runway.getResaDistance();
            double lda = param.getLda();
            setStartx(displacedthresh+((runway.getObstaclePosition().getDistanceFromThreshold()/runwaylength)*this.getWidth()));
            setStarty(this.getHeight()/2);
            this.setRESA(runway.getResaDistance(),this.startx - ((resa/runwaylength)*this.getWidth()),this.startx,starty +10,starty+10);
            this.setLandingThreshold(60,this.RESA.getStartx() - ((60/runwaylength)*this.getWidth()),this.RESA.getStartx(),this.starty+10,this.starty+10);
            this.setLDA(lda,this.landingthreshold.getStartx() - ((lda/runwaylength)*this.getWidth()),this.landingthreshold.getStartx(),this.starty +10 ,this.starty +10);
            double middlelda = (this.LDA.getEndX() - this.LDA.getStartx())/2;
            double quarterheight = this.getHeight() / 4;
            this.setTakeOffLandingDirection(true,"Landing Direction",middlelda,this.LDA.getEndX(),quarterheight,quarterheight);
        }
        else if(method.toString() == FlightMethod.TAKEOFF_TOWARDS.toString()){
            setObstacle(false);
            setLine();
            setStartx(displacedthresh+((runway.getObstaclePosition().getDistanceFromThreshold()/runwaylength)*this.getWidth()));
            setStarty(this.getHeight()/2);
            double resa = runway.getResaDistance();
            double lsa = runway.getAlstocs();
            this.setRESA(resa,this.startx - ((resa/runwaylength)*this.getWidth()),this.startx,starty +10,starty+10);
            this.setH50(lsa,"ALS",this.startx - ((lsa/runwaylength)*this.getWidth()),this.startx,starty+45,starty+45);
            if(this.h50.getStartx() < this.RESA.getStartx()){
                this.setLandingThreshold(60,this.h50.getStartx() - ((60/runwaylength)*this.getWidth()),this.h50.getStartx(),this.starty+10,this.starty+10);
            }
            else{
                this.setLandingThreshold(60,this.RESA.getStartx() - ((60/runwaylength)*this.getWidth()),this.RESA.getStartx(),this.starty+10,this.starty+10);
            }
            double asda = param.getAsda();
            this.setTakeOffRun(asda,this.landingthreshold.getStartx() - ((asda/runwaylength)*this.getWidth()),this.landingthreshold.getStartx(),this.starty +10 ,this.starty +10);
            double middlelda = (this.takeoffrun.getEndX() - this.takeoffrun.getStartx())/2;
            double quarterheight = this.getHeight() / 4;
            this.setTakeOffLandingDirection(true,"Take off Direction",middlelda,this.takeoffrun.getEndX(),quarterheight,quarterheight);


        }
        else {
            setObstacle(true);
            setLine();
            setStartx(this.getWidth() - ((runway.getObstaclePosition().getDistanceFromThreshold()/runwaylength)*this.getWidth())-displacedthresh);
            setStarty(this.getHeight()/2);
            double engblast = runway.getEngineBlastAllowance();
            setEngineBlastAllowance(engblast,startx-((engblast/runwaylength)*this.getWidth()),startx,starty+10,starty+10);
            double tora = runway.getParameters().getTora();
            this.setTakeOffRun(param.getAsda(),0,engineblast.getStartx(),starty+10,starty+10);
            double middlelda = (this.takeoffrun.getEndX() - this.takeoffrun.getStartx())/2;
            double quarterheight = this.getHeight() / 4;
            this.setTakeOffLandingDirection(false,"Take-Off Direction",middlelda,this.takeoffrun.getEndX(),quarterheight,quarterheight);
        }
        if(this.title){
            setTitle();
        }
    }


    public void setObstacle(boolean threshleft){
        double displacedthresh = ((this.runway.getParameters().getDisplacedThreshold()/runwaylength) * this.getWidth());
        this.obstacle = new Rectangle();
        if(threshleft){
            this.obstacle.setX((this.getWidth() - displacedthresh-((runway.getObstaclePosition().getDistanceFromThreshold()/runwaylength)*this.getWidth())));
        }
        else{
            this.obstacle.setX(displacedthresh+((runway.getObstaclePosition().getDistanceFromThreshold()/runwaylength)*this.getWidth()));
        }
        this.obstacle.setFill(Color.BLACK);
        this.obstacle.setY((this.getHeight()/2)-30);
        this.obstacle.setWidth(30);
        this.obstacle.setHeight(30);
        this.getChildren().add(this.obstacle);

    }
    public void setLine(){
        this.line = new Line();
        this.line.setStrokeWidth(5);
        line.setStartX(0);
        line.setStartY(this.getHeight() / 2);
        line.setEndX(this.getWidth());
        line.setEndY(this.getHeight() /2);
        this.getChildren().add(this.line);
    }

    public void setTitle(){
        Text rtext = new Text(this.method.toString());
        rtext.setStyle("-fx-font-weight: bold");
        rtext.setX(this.getWidth()/40);
        rtext.setY(40);
        getChildren().add(rtext);
    }

    public void setEngineBlastAllowance(double value,double startx,double endx,double starty,double endy){
        Arrow engineblast = new Arrow(startx,endx,starty,endy,true,Optional.empty());
        String format = new DecimalFormat("#").format(value).toString() + "m";
        this.engineblast = engineblast;
        String rtext = "Engine blast allowance " + format;
        Text text = new Text(rtext);
        text.setY(starty+12);
        text.setX(startx+10);
        text.setStyle("-fx-font-size:10");
        getChildren().addAll(engineblast,text);
    }
    public void setTakeOffRun(double value,double startx,double endx,double starty, double endy){
        Arrow takeoffrun = new Arrow(startx,endx, starty,endy,true,Optional.empty());
        this.takeoffrun = takeoffrun;
        String format = new DecimalFormat("#").format(value).toString() + "m";
        Text rtext = new Text("TORA " + format + "\n" + "TODA "+ format + "\n" + "ASDA "+ format);
        rtext.setY(starty+12);
        rtext.setX(startx +10);
        rtext.setStyle("-fx-font-size:10");
        this.getChildren().addAll(takeoffrun,rtext);
    }
    public void setTakeOffLandingDirection(boolean right,String str,double startx,double endx,double starty, double endy){
        /*double startx = 40;
        double starty = (this.getHeight()/2)/2;

         */
        Arrow landdir = new Arrow(startx,endx ,starty,endy,false,Optional.of(right));
        Text rtext = new Text(str);
        rtext.setX(startx);
        rtext.setY(starty-10);
        rtext.setStyle("-fx-font-size:10");
        this.getChildren().addAll(landdir,rtext);
    }

    public void setLDA(double LDA,double startx,double endx,double starty, double endy){
        Arrow lda = new Arrow(0,endx, (this.getHeight() / 2)+10,(this.getHeight() / 2)+10,true,Optional.empty());
        this.LDA = lda;
        Text rtext = new Text("Landing Distance Available\n" + (new DecimalFormat("#").format(LDA).toString()) + "m");
        rtext.setY(starty+12);
        rtext.setX((startx+endx)/2);
        this.getChildren().addAll(lda,rtext);
    }

    public void setLandingThreshold(double value,double startx,double endx,double starty, double endy){
        Arrow landingthreshold = new Arrow(startx ,endx,starty,(this.getHeight() / 2)+10,true,Optional.empty());
        this.landingthreshold = landingthreshold;
        Text rtext = new Text((new DecimalFormat("#").format(value).toString()) + "m");
        rtext.setY(starty+17);
        rtext.setStyle("-fx-font-size:10");
        rtext.setX(startx);
        this.getChildren().addAll(landingthreshold,rtext);
    }

    public void setRESA(double resa,double startx,double endx,double starty, double endy){
        Arrow arrow = new Arrow(startx, endx,starty,endy,true,Optional.empty());
        this.RESA = arrow;
        Text rtext = new Text("RESA\n" + (new DecimalFormat("#").format(resa).toString()) + "m");
        rtext.setStyle("-fx-font-size:10");
        rtext.setY(starty+18);
        rtext.setX(startx);
        getChildren().addAll(arrow,rtext);
    }

    public void setH50 (double h50,String tls,double startx,double endx,double starty, double endy){
        Arrow arrow = new Arrow(startx, endx,starty,endy,true,Optional.empty());
        this.h50 = arrow;
        Text rtext = new Text(tls + "\n" + (new DecimalFormat("#").format(h50).toString()) + "m");
        rtext.setY(starty+15);
        rtext.setX(((endx-startx)/2)+startx);
        rtext.setStyle("-fx-font-size:10");
        getChildren().addAll(arrow,rtext);
    }

    public void setH (double h,double startx,double endx,double starty, double endy){
        runway.getObstacle().getHeight();
    }

    public WritableImage focusedSnapshot() {
        SnapshotParameters param = new SnapshotParameters();
        param.setDepthBuffer(true);
        return this.snapshot(param, null);
    }

    // n eed arrows
}
