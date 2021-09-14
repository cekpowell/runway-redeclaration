package Controller;

import Model.LogicalRunway;
import Model.RevisedLogicalRunway;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Handles the display of runways (i.e., converts runways into objects that can be displayed within a JavaFX view).
 */
public class RunwayDisplayer {

    /**
     * Returns a Node representation of the given logical runway.
     * @param logicalRunway The 'LogicalRunway' that is to be displayed.
     * @return A 'Node' representation of the logical runway.
     */
    public static Node getDisplayOfLogicalRunway(LogicalRunway logicalRunway){

        // RUNWAY PARAMETERS

        // LDA
        HBox ldaContainer = new HBox();
        Label ldaTitleLabel = new Label("LDA : ");
        Label ldaLabel = new Label(String.valueOf(logicalRunway.getParameters().getLda()) + "(m)");
        ldaContainer.getChildren().addAll(ldaTitleLabel, ldaLabel);
        ldaContainer.setAlignment(Pos.CENTER);
        ldaContainer.setSpacing(20);

        // TORA
        HBox toraContainer = new HBox();
        Label toraTitleLabel = new Label("TORA : ");
        Label toraLabel = new Label(String.valueOf(logicalRunway.getParameters().getTora()) + "(m)");
        toraContainer.getChildren().addAll(toraTitleLabel, toraLabel);
        toraContainer.setAlignment(Pos.CENTER);
        toraContainer.setSpacing(20);

        // ASDA
        HBox asdaContainer = new HBox();
        Label asdaTitleLabel = new Label("ASDA : ");
        Label asdaLabel = new Label(String.valueOf(logicalRunway.getParameters().getAsda()) + "(m)");
        asdaContainer.getChildren().addAll(asdaTitleLabel, asdaLabel);
        asdaContainer.setAlignment(Pos.CENTER);
        asdaContainer.setSpacing(20);

        // TODA
        HBox todaContainer = new HBox();
        Label todaTitleLabel = new Label("TODA : ");
        Label todaLabel = new Label(String.valueOf(logicalRunway.getParameters().getToda()) + "(m)");
        todaContainer.getChildren().addAll(todaTitleLabel, todaLabel);
        todaContainer.setAlignment(Pos.CENTER);
        todaContainer.setSpacing(20);

        // Displaced Threshold
        HBox displacedThresholdContainer = new HBox();
        Label displacedThresholdTitleLabel = new Label("Displaced\nThreshold : ");
        Label displacedThresholdLabel = new Label(String.valueOf(logicalRunway.getParameters().getDisplacedThreshold()) + "(m)");
        displacedThresholdContainer.getChildren().addAll(displacedThresholdTitleLabel, displacedThresholdLabel);
        displacedThresholdContainer.setAlignment(Pos.CENTER);
        displacedThresholdContainer.setSpacing(20);

        // Runway Parameters Container
        VBox runwayParametersContainer = new VBox();
        Label runwayParametersLabel = new Label("Parameters");
        runwayParametersLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        runwayParametersContainer.getChildren().addAll(runwayParametersLabel, ldaContainer, toraContainer, asdaContainer, todaContainer, displacedThresholdContainer);
        runwayParametersContainer.setAlignment(Pos.CENTER);
        runwayParametersContainer.setSpacing(20);


        // man container for the display
        VBox container = new VBox();
        container.getChildren().addAll(runwayParametersContainer);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        return container;
    }

    /**
     * Returns a Node representation of the given revised logical runway.
     * @param revisedLogicalRunway The 'RevisedLogicalRunway' that is to be displayed.
     * @return A 'Node' representatiion of the revised logical runway.
     */
    public static Node getDisplayOfRevisedLogicalRunway(LogicalRunway logicalRunway, RevisedLogicalRunway revisedLogicalRunway){

        // RUNWAY PARAMETERS

        // LDA
        HBox ldaContainer = new HBox();
        Label ldaTitleLabel = new Label("LDA : ");
        Label ldaLabel = new Label(String.valueOf(revisedLogicalRunway.getParameters().getLda()) + "(m)");
        ldaContainer.getChildren().addAll(ldaTitleLabel, ldaLabel);
        ldaContainer.setAlignment(Pos.CENTER);
        ldaContainer.setSpacing(20);

        // TORA
        HBox toraContainer = new HBox();
        Label toraTitleLabel = new Label("TORA : ");
        Label toraLabel = new Label(String.valueOf(revisedLogicalRunway.getParameters().getTora()) + "(m)");
        toraContainer.getChildren().addAll(toraTitleLabel, toraLabel);
        toraContainer.setAlignment(Pos.CENTER);
        toraContainer.setSpacing(20);

        // ASDA
        HBox asdaContainer = new HBox();
        Label asdaTitleLabel = new Label("ASDA : ");
        Label asdaLabel = new Label(String.valueOf(revisedLogicalRunway.getParameters().getAsda()) + "(m)");
        asdaContainer.getChildren().addAll(asdaTitleLabel, asdaLabel);
        asdaContainer.setAlignment(Pos.CENTER);
        asdaContainer.setSpacing(20);

        // TODA
        HBox todaContainer = new HBox();
        Label todaTitleLabel = new Label("TODA : ");
        Label todaLabel = new Label(String.valueOf(revisedLogicalRunway.getParameters().getToda()) + "(m)");
        todaContainer.getChildren().addAll(todaTitleLabel, todaLabel);
        todaContainer.setAlignment(Pos.CENTER);
        todaContainer.setSpacing(20);

        // Runway Parameters Container
        VBox runwayParametersContainer = new VBox();
        Label runwayParametersLabel = new Label("Revised Parameters");
        runwayParametersLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        runwayParametersContainer.getChildren().addAll(runwayParametersLabel);
        runwayParametersContainer.setAlignment(Pos.CENTER);
        runwayParametersContainer.setSpacing(20);

        // adding only needed parameters to the container (i.e., adding if revised is different to original).
        if(logicalRunway.getParameters().getLda() != revisedLogicalRunway.getParameters().getLda()){
            runwayParametersContainer.getChildren().add(ldaContainer);
        }
        if(logicalRunway.getParameters().getTora() != revisedLogicalRunway.getParameters().getTora()){
            runwayParametersContainer.getChildren().add(toraContainer);
        }
        if(logicalRunway.getParameters().getAsda() != revisedLogicalRunway.getParameters().getAsda()){
            runwayParametersContainer.getChildren().add(asdaContainer);
        }
        if(logicalRunway.getParameters().getToda() != revisedLogicalRunway.getParameters().getToda()){
            runwayParametersContainer.getChildren().add(todaContainer);
        }

        // main container for the display
        VBox container = new VBox();
        container.getChildren().addAll(runwayParametersContainer);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        return container;
    }
}
