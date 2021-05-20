package windows.extra;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import windows.FinalFolderWindow;
import windows.LoginWindow;
import windows.MainFoldersWindow;
import windows.MainWindow;
import windows.SubFoldersWindow;
import windows.extra.GUIInteraction;

public class GUIInteraction {
    Stage stage;
    private double xOffset;
    private double yOffset;
    LoginWindow loginWindow;
    MainWindow mainWindow;
    MainFoldersWindow mainFoldersWindow;
    SubFoldersWindow subFoldersWindow;
    FinalFolderWindow finalFolderWindow;
    public GUIInteraction(Stage stage){
        this.stage = stage;
    }

    public void swapWindow(Pane root){
        addWindowInteraction(this.stage,root);
        Scene scene = new Scene(root,1000,600);
        scene.setFill(Color.TRANSPARENT);
        
        
        this.stage.setTitle("PS-Dispatcher");
        this.stage.setScene(scene);
        if(!this.stage.isShowing()){
            this.stage.initStyle(StageStyle.TRANSPARENT);
        }
        this.stage.getScene().getStylesheets().add("css/mainWindow.css");
        this.stage.show();
    }

    private void addWindowInteraction(Stage stage,Pane root) {
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
    }

    public void setLoginWindow(LoginWindow loginWindow){
        this.loginWindow=loginWindow;
    }
    public LoginWindow getLoginWindow(){
        return this.loginWindow;
    }
    public void setMainWindow(MainWindow mainWindow){
        this.mainWindow=mainWindow;
    }
    public MainWindow getMainWindow(){
        return this.mainWindow;
    }
    public void setMainFoldersWindow(MainFoldersWindow mainFoldersWindow){
        this.mainFoldersWindow=mainFoldersWindow;
    }
    public MainFoldersWindow getMainFoldersWindow(){
        return this.mainFoldersWindow;
    }
    public void setSubFoldersWindow(SubFoldersWindow subFoldersWindow){
        this.subFoldersWindow=subFoldersWindow;
    }
    public SubFoldersWindow getsubFoldersWindow(){
        return this.subFoldersWindow;
    }
    public void setFinalFolderWindow(FinalFolderWindow finalFolderWindow){
        this.finalFolderWindow=finalFolderWindow;
    }
    public FinalFolderWindow getFinalFolderWindow(){
        return this.finalFolderWindow;
    }

}
