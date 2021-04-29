import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WindowActions {
    private double xOffset;
    private double yOffset;
    private Stage stage;
    private Pane root = new Pane();
    Scene scene = new Scene(root, 1000, 600);;

    public WindowActions(Stage stage){
        this.xOffset = 0;
        this.yOffset = 0;
        this.stage = stage;
    }

    public void windowBarActionsIni(Pane pane){
        Button closeButton = new Button();
        closeButton.setLayoutX(944);
        closeButton.setLayoutY(28);
        closeButton.setPrefSize(22,22);
        closeButton.setId("closeBtn");

        Button minimizeButton = new Button();
        minimizeButton.setLayoutX(901);
        minimizeButton.setLayoutY(28);
        minimizeButton.setPrefSize(22,22);
        minimizeButton.setId("minimizeBtn");

        pane.getChildren().add(minimizeButton);
        pane.getChildren().add(closeButton);

        closeButton.setOnAction(e -> {
            ((Stage)((Button)e.getSource()).getScene().getWindow()).close();
        });

        minimizeButton.setOnAction(e -> {
            ((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true);
        });

        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        this.root = pane;
        this.scene.setRoot(pane);
    }

    public void start(){
        scene.setFill(Color.TRANSPARENT);
        
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("PS-Dispatcher");
        stage.setScene(scene);
        stage.getScene().getStylesheets().setAll(App.class.getResource("css/mainWindow.css").toString());
        stage.show();
    }
}
