package windows;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import windows.extra.DispatcherConfig;
import windows.extra.DispatcherListBuilder;
import windows.extra.GUIInteraction;

public class MainWindow {
    public GUIInteraction gui;
    public MainWindow(GUIInteraction gui){
        this.gui=gui;
    }

    public GUIInteraction getGui(){
        return this.gui;
    }
 
    public Pane getPane(){
        Pane root = new Pane();
        root.getChildren().clear();
        DispatcherConfig dispatcherConfigurations = new DispatcherConfig(gui);
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
        interiorScroll.getChildren().clear();
        DispatcherListBuilder dispatcherListBuilder = new DispatcherListBuilder(gui);
        dispatcherListBuilder.buildList(interiorScroll);

        interiorScroll.setAlignment(Pos.CENTER);
        interiorScroll.setSpacing(30);
        interiorScroll.setId("InnerScroll");
        

        sp.setContent(interiorScroll);
        sp.setId("Scroll");


        root.getChildren().add(sp);
        root.getChildren().add(addDispatchers);
        root.setId("DispatchersWindow");    
        
        return root;
    }
}
