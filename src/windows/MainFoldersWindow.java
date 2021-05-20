package windows;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import windows.extra.DispatcherManager;
import windows.extra.GUIInteraction;

public class MainFoldersWindow {
    GUIInteraction gui;
    String path;
    public MainFoldersWindow(GUIInteraction gui) {
        this.gui=gui;
    }
    public void setPath(String path){
        this.path=path;
    }

    public Pane getPane(){
        System.out.println(path);
        Pane root = new Pane();

        Text inboxText = new Text("Inbox");
        inboxText.setFont(Font.font("Roboto",48));
        inboxText.setFill(Color.web("FFFFFF"));
        inboxText.setLayoutX(22);
        inboxText.setLayoutY(100);

        Text okText = new Text("Ok");
        okText.setFont(Font.font("Roboto",48));
        okText.setFill(Color.web("FFFFFF"));
        okText.setLayoutX(22);
        okText.setLayoutY(100);

        Text rejectText = new Text("Reject");
        rejectText.setFont(Font.font("Roboto",48));
        rejectText.setFill(Color.web("FFFFFF"));
        rejectText.setLayoutX(22);
        rejectText.setLayoutY(100);

        Text signedText = new Text("Signed");
        signedText.setFont(Font.font("Roboto",48));
        signedText.setFill(Color.web("FFFFFF"));
        signedText.setLayoutX(22);
        signedText.setLayoutY(100);

        Button backButton = new Button();
        backButton.setLayoutX(858);
        backButton.setLayoutY(30);
        backButton.setPrefSize(22,22);
        backButton.setId("backBtn");

        backButton.setOnAction(e -> {
            gui.swapWindow(gui.getMainWindow().getPane());
        });

        Pane inboxBox = new Pane();
        inboxBox.setLayoutX(153);
        inboxBox.setLayoutY(234);
        inboxBox.setPrefSize(286,131);
        inboxBox.setMinSize(286,131);
        inboxBox.getStyleClass().add("dispatcherItem");
        inboxBox.getChildren().add(inboxText);
        inboxBox.setOnMouseClicked(event -> {
            DispatcherManager dispatcherManager = new DispatcherManager();
            dispatcherManager.loadProperties(path);
            String path = dispatcherManager.getValue("inbox").replace("\\\\", "\\");
            gui.getsubFoldersWindow().setPath(path);
            gui.swapWindow(gui.getsubFoldersWindow().getPane());
        });

        Pane okBox = new Pane();
        okBox.setLayoutX(560);
        okBox.setLayoutY(234);
        okBox.setPrefSize(286,131);
        okBox.setMinSize(286,131);
        okBox.getStyleClass().add("dispatcherItem");
        okBox.getChildren().add(okText);
        okBox.setOnMouseClicked(event -> {
            DispatcherManager dispatcherManager = new DispatcherManager();
            dispatcherManager.loadProperties(path);
            String path = dispatcherManager.getValue("ok").replace("\\\\", "\\");
            gui.getsubFoldersWindow().setPath(path);
            gui.swapWindow(gui.getsubFoldersWindow().getPane());
        });

        Pane rejectBox = new Pane();
        rejectBox.setLayoutX(153);
        rejectBox.setLayoutY(407);
        rejectBox.setPrefSize(286,131);
        rejectBox.setMinSize(286,131);
        rejectBox.getStyleClass().add("dispatcherItem");
        rejectBox.getChildren().add(rejectText);
        rejectBox.setOnMouseClicked(event -> {
            DispatcherManager dispatcherManager = new DispatcherManager();
            dispatcherManager.loadProperties(path);
            String path = dispatcherManager.getValue("reject").replace("\\\\", "\\");
            gui.getsubFoldersWindow().setPath(path);
            gui.swapWindow(gui.getsubFoldersWindow().getPane());
        });


        Pane signedBox = new Pane();
        signedBox.setLayoutX(560);
        signedBox.setLayoutY(407);
        signedBox.setPrefSize(286,131);
        signedBox.setMinSize(286,131);
        signedBox.getStyleClass().add("dispatcherItem");
        signedBox.getChildren().add(signedText);
        signedBox.setOnMouseClicked(event -> {
            DispatcherManager dispatcherManager = new DispatcherManager();
            dispatcherManager.loadProperties(path);
            String path = dispatcherManager.getValue("signed").replace("\\\\", "\\");
            gui.getsubFoldersWindow().setPath(path);
            gui.swapWindow(gui.getsubFoldersWindow().getPane());
        });

        root.setId("MainFoldersWindow");
        root.getChildren().add(inboxBox);
        root.getChildren().add(okBox);
        root.getChildren().add(rejectBox);
        root.getChildren().add(signedBox);
        root.getChildren().add(backButton);
        return root;
    }

}