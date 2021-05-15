package windows;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import windows.extra.DispatcherConfig;
import windows.extra.DispatcherListBuilder;

public class MainWindow {
 
    public static Pane getPane(){
        Pane root = new Pane();
        DispatcherConfig dispatcherConfigurations = new DispatcherConfig();
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

        DispatcherListBuilder.buildList(interiorScroll);

        interiorScroll.setAlignment(Pos.CENTER);
        interiorScroll.setSpacing(30);
        interiorScroll.setId("InnerScroll");
        

        sp.setContent(interiorScroll);
        sp.setId("Scroll");


        root.getChildren().add(sp);
        root.getChildren().add(addDispatchers);
        System.out.println("Hola");
        root.setId("DispatchersWindow");    
        
        return root;
    }
}
