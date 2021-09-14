package View;

import Controller.Validator;
import Model.LogicalRunway;
import Model.LogicalRunwayParameters;
import Model.ValidationException;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * Container that houses the controls for the user input the data for a logical runway.
 */
public class AddLogicalRunwayContainer extends VBox{

    // member variables
    private Label title;
    private TextField heading;
    private ToggleGroup position;
    private RadioButton leftPosition;
    private RadioButton centerPosition;
    private RadioButton rightPosition;
    private RadioButton noPosition;
    private TextField tora;
    private TextField toda;
    private TextField asda;
    private TextField lda;
    private TextField displacedThreshold;
    private TextField minimumAngleOfAscentDescent;

    /**
     * Constructor for the class.
     * @param titleString the title of the container.
     */
    public AddLogicalRunwayContainer(String titleString,String formname){

        // initialising member variables
        this.title = new Label(titleString);
        this.heading = new TextField();
        this.heading.setId(formname +"heading");
        this.position = new ToggleGroup();
        this.leftPosition = new RadioButton();
        this.leftPosition.setId(formname+"L");
        this.leftPosition.setUserData('L');
        this.leftPosition.setToggleGroup(this.position);
        this.centerPosition = new RadioButton();
        this.centerPosition.setId(formname+"C");
        this.centerPosition.setUserData('C');
        this.centerPosition.setToggleGroup(this.position);
        this.rightPosition = new RadioButton();
        this.rightPosition.setId(formname+"R");
        this.rightPosition.setUserData('R');
        this.rightPosition.setToggleGroup(this.position);
        this.noPosition = new RadioButton();
        this.noPosition.setUserData('N');
        this.noPosition.setId(formname+"N");
        this.noPosition.setToggleGroup(this.position);
        this.position.selectToggle(this.noPosition);
        this.tora = new TextField();
        this.tora.setId(formname+"tora");
        this.toda = new TextField();
        this.toda.setId(formname+"toda");
        this.asda = new TextField();
        this.asda.setId(formname+"asda");
        this.lda = new TextField();
        this.lda.setId(formname+"lda");
        this.displacedThreshold = new TextField();
        this.displacedThreshold.setId(formname+"displaced");
        this.minimumAngleOfAscentDescent = new TextField();
        this.minimumAngleOfAscentDescent.setId(formname+"minangle");

        // creating form labels
        Label headingLabel = new Label("Runway Heading : ");
        Label positionLabel = new Label("Runway Position : ");
        Label leftPositionLabel = new Label("L");
        Label centerPositionLabel = new Label("C");
        Label rightPositionLabel = new Label("R");
        Label noPositionLabel = new Label("NONE");
        Label toraLabel = new Label("TORA (m) : ");
        Label todaLabel = new Label("TODA (m) : ");
        Label asdaLabel = new Label("ASDA (m) : ");
        Label ldaLabel = new Label("LDA (m) : ");
        Label displacedThresholdLabel = new Label("Displaced\nThreshold (m)");
        displacedThresholdLabel.setTextAlignment(TextAlignment.CENTER);
        Label minimumAngleOfAscentLabel = new Label("Minimum Angle\nOf\nAscent/Descent");
        minimumAngleOfAscentLabel.setTextAlignment(TextAlignment.CENTER);

        // supplementary container for heading
        HBox headingContainer = new HBox();
        headingContainer.getChildren().addAll(headingLabel, this.heading);
        headingContainer.setSpacing(20);
        headingContainer.setAlignment(Pos.CENTER);

        // supplementary container for position
        HBox positionContainer = new HBox();
        HBox leftPositonContainer = new HBox();
        leftPositonContainer.getChildren().addAll(leftPositionLabel, this.leftPosition);
        leftPositonContainer.setSpacing(10);
        leftPositonContainer.setAlignment(Pos.CENTER);

        HBox centerPositonContainer = new HBox();
        centerPositonContainer.getChildren().addAll(centerPositionLabel, this.centerPosition);
        centerPositonContainer.setSpacing(10);
        centerPositonContainer.setAlignment(Pos.CENTER);

        HBox rightPositonContainer = new HBox();
        rightPositonContainer.getChildren().addAll(rightPositionLabel, this.rightPosition);
        rightPositonContainer.setSpacing(10);
        rightPositonContainer.setAlignment(Pos.CENTER);

        HBox noPositonContainer = new HBox();
        noPositonContainer.getChildren().addAll(noPositionLabel, this.noPosition);
        noPositonContainer.setSpacing(10);
        noPositonContainer.setAlignment(Pos.CENTER);

        VBox positionButtonsContainer = new VBox();
        positionButtonsContainer.getChildren().addAll(leftPositonContainer,
                                                      centerPositonContainer,
                                                      rightPositonContainer,
                                                      noPositonContainer);
        positionButtonsContainer.setSpacing(10);
        positionButtonsContainer.setAlignment(Pos.CENTER);
        positionContainer.getChildren().addAll(positionLabel, positionButtonsContainer);
        positionContainer.setSpacing(20);
        positionContainer.setAlignment(Pos.CENTER);

        // supplementary container for TORA
        HBox toraContainer = new HBox();
        toraContainer.getChildren().addAll(toraLabel, this.tora);
        toraContainer.setSpacing(20);
        toraContainer.setAlignment(Pos.CENTER);

        // supplementary container for TODA
        HBox todaContainer = new HBox();
        todaContainer.getChildren().addAll(todaLabel, this.toda);
        todaContainer.setSpacing(20);
        todaContainer.setAlignment(Pos.CENTER);

        // supplementary container for ASDA
        HBox asdaContainer = new HBox();
        asdaContainer.getChildren().addAll(asdaLabel, this.asda);
        asdaContainer.setSpacing(20);
        asdaContainer.setAlignment(Pos.CENTER);

        // supplementary container for LDA
        HBox ldaContainer = new HBox();
        ldaContainer.getChildren().addAll(ldaLabel, this.lda);
        ldaContainer.setSpacing(20);
        ldaContainer.setAlignment(Pos.CENTER);

        // supplementary container for displaced threshold
        HBox displacedThresholdContainer = new HBox();
        displacedThresholdContainer.getChildren().addAll(displacedThresholdLabel, this.displacedThreshold);
        displacedThresholdContainer.setSpacing(20);
        displacedThresholdContainer.setAlignment(Pos.CENTER);

        // supplementary container for minimum angle of ascent
        HBox minimumAngleOfAscentContainer = new HBox();
        minimumAngleOfAscentContainer.getChildren().addAll(minimumAngleOfAscentLabel, this.minimumAngleOfAscentDescent);
        minimumAngleOfAscentContainer.setSpacing(20);
        minimumAngleOfAscentContainer.setAlignment(Pos.CENTER);

        // adding items
        this.getChildren().addAll(this.title,
                                  headingContainer,
                                  positionContainer,
                                  toraContainer,
                                  todaContainer,
                                  asdaContainer,
                                  ldaContainer,
                                  displacedThresholdContainer,
                                  minimumAngleOfAscentContainer);

        // formatting
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
    }

    public LogicalRunway getLogicalRunway() throws ValidationException{
        // validating user input to the form
        Validator validator = new Validator();
        validator.validateLogicalRunwayInput(this.heading.getText(),
                                             this.lda.getText(),
                                             this.tora.getText(),
                                             this.asda.getText(),
                                             this.toda.getText(),
                                             this.displacedThreshold.getText(),
                                             this.minimumAngleOfAscentDescent.getText());

        // user input valid, so creating logical runway

        // creating new runway parameters
        LogicalRunwayParameters param = new LogicalRunwayParameters();
        param.setAsda(Double.valueOf(this.asda.getText()));
        param.setToda(Double.valueOf(this.toda.getText()));
        param.setTora(Double.valueOf(this.tora.getText()));
        param.setLda(Double.valueOf(this.lda.getText()));
        param.setDisplacedThreshold(Double.valueOf(this.displacedThreshold.getText()));
        param.setMinimumAngleOfAscentDescent(Double.valueOf(this.minimumAngleOfAscentDescent.getText()));

        // getting runway heading
        Integer heading = Integer.valueOf(this.heading.getText());

        // creating runway designation (need to account for fact it might not have a runway position)
        String designation = "";
        if(this.getRunwayPosition() == 'N'){
            designation = this.heading.getText();
        }
        else{
            designation = this.heading.getText() + this.getRunwayPosition();
        }

        // creating runway object
        LogicalRunway logicalRunway = new LogicalRunway(designation, heading, this.getRunwayPosition(), param);
        return logicalRunway;
    }

    /**
     * Helper method to return selected runway position.
     * @return Selected runway position.
     */
    public Character getRunwayPosition()
    {
        return (Character) this.position.getSelectedToggle().getUserData();
    }

    /**
     * GETTERS AND SETTERS
     */

    public TextField getHeading() {
        return heading;
    }

    public ToggleGroup getPosition() {
        return position;
    }

    public RadioButton getLeftPosition() {
        return leftPosition;
    }

    public RadioButton getCenterPosition() {
        return centerPosition;
    }

    public RadioButton getRightPosition() {
        return rightPosition;
    }

    public RadioButton getNoPosition() {
        return noPosition;
    }

    public TextField getTora() {
        return tora;
    }

    public TextField getToda() {
        return toda;
    }

    public TextField getAsda() {
        return asda;
    }

    public TextField getLda() {
        return lda;
    }

    public TextField getDisplacedThreshold() {
        return displacedThreshold;
    }

    public TextField getMinimumAngleOfAscentDescent() {
        return minimumAngleOfAscentDescent;
    }
}

