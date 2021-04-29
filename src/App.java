import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import windows.MainWindow;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class App extends Application{
    private int pageNum = 0;
    private Pane root;
    private double xOffset;
    private double yOffset;

    public void start(Stage mainStage) {

        root = MainWindow.getPane();
        addWindowInteraction(mainStage);

        Scene scene = new Scene(root,1000,600);
        scene.setFill(Color.TRANSPARENT);
        
        mainStage.initStyle(StageStyle.TRANSPARENT);
        mainStage.setTitle("PS-Dispatcher");
        mainStage.setScene(scene);
        mainStage.getScene().getStylesheets().setAll(App.class.getResource("css/mainWindow.css").toString());
        mainStage.show();
    }

    private void addWindowInteraction(Stage stage) {
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

        root.getChildren().add(minimizeButton);
        root.getChildren().add(closeButton);

        closeButton.setOnAction(e -> {
            ((Stage)((Button)e.getSource()).getScene().getWindow()).close();
        });

        minimizeButton.setOnAction(e -> {
            ((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true);
        });

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        switch(pageNum){
            case 0:
            break;
        }

    }

    public static void main(String[] args) {
        launch(args);
    } 
}
