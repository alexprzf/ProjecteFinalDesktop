

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DispatchersWindows {
    WindowActions windowActions;
    public DispatchersWindows(WindowActions windowActions){
        this.windowActions = windowActions;
    }
    public void getPane(){
        Pane root = new Pane();
        DispatcherConfigurations dispatcherConfigurations = new DispatcherConfigurations();
        dispatcherConfigurations.init();

        ScrollPane sp = new ScrollPane();
        sp.pannableProperty().set(true);
        sp.fitToWidthProperty().set(true);
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setLayoutX(76);
        sp.setLayoutY(365);
        sp.setPrefSize(849, 211);
        sp.setId("Scroll");

        Button addDispatchers = new Button();
        addDispatchers.setLayoutX(636);
        addDispatchers.setLayoutY(288);
        addDispatchers.setPrefSize(213,50);
        addDispatchers.setId("dispatchersConfigurationButton");

        addDispatchers.setOnAction(e -> {
            if(dispatcherConfigurations.getStage().isShowing()){
                dispatcherConfigurations.getStage().close();
            }else{
                dispatcherConfigurations.getStage().show();
            }
                
        });

        HBox interiorScroll = new HBox();

        Pane cuadrat1 = new Pane();
        cuadrat1.setPrefSize(240,184);
        cuadrat1.setMinSize(240,184);
        cuadrat1.getStyleClass().add("dispatcherItem");

        Pane cuadrat2 = new Pane();
        cuadrat2.setPrefSize(240,184);
        cuadrat2.setMinSize(240,184);
        cuadrat2.getStyleClass().add("dispatcherItem");

        Pane cuadrat3 = new Pane();
        cuadrat3.setPrefSize(240,184);
        cuadrat3.setMinSize(240,184);
        cuadrat3.getStyleClass().add("dispatcherItem");

        Pane cuadrat4 = new Pane();
        cuadrat4.setPrefSize(240,184);
        cuadrat4.setMinSize(240,184);
        cuadrat4.getStyleClass().add("dispatcherItem");

        Pane cuadrat5 = new Pane();
        cuadrat5.setPrefSize(240,184);
        cuadrat5.setMinSize(240,184);
        cuadrat5.getStyleClass().add("dispatcherItem");

        Pane cuadrat6 = new Pane();
        cuadrat6.setPrefSize(240,184);
        cuadrat6.setMinSize(240,184);
        cuadrat6.getStyleClass().add("dispatcherItem");

        interiorScroll.setAlignment(Pos.CENTER);
        interiorScroll.setSpacing(30);
        interiorScroll.getChildren().add(cuadrat1);
        interiorScroll.getChildren().add(cuadrat2);
        interiorScroll.getChildren().add(cuadrat3);
        interiorScroll.getChildren().add(cuadrat4);
        interiorScroll.getChildren().add(cuadrat5);
        interiorScroll.getChildren().add(cuadrat6);
        interiorScroll.setId("InnerScroll");
        

        sp.setContent(interiorScroll);
        sp.setId("Scroll");


        root.getChildren().add(sp);
        root.getChildren().add(addDispatchers);
        System.out.println("Hola");
        root.setId("DispatchersWindow");
        windowActions.windowBarActionsIni(root);       
    }
}
