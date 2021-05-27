package windows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import windows.extra.GUIInteraction;

public class SubFoldersWindow {
    GUIInteraction gui;
    String path;
    public SubFoldersWindow(GUIInteraction gui) {
        this.gui=gui;
    }
    public void setPath(String path){
        this.path=path;
    }

    public Pane getPane(){
        ScrollPane sp = new ScrollPane();
        GridPane interiorScroll = new GridPane();
        System.out.println(path);
        checkIfFinalFolder(path);
        Pane root = new Pane();
        buildList(path,interiorScroll);

        sp.pannableProperty().set(true);
        sp.fitToWidthProperty().set(true);
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        sp.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setLayoutX(50);
        sp.setLayoutY(220);
        sp.setPrefSize(900, 350);
        sp.setId("Scroll");
        sp.setContent(interiorScroll);
        interiorScroll.setHgap(50);
        interiorScroll.setVgap(50);
        RowConstraints rc = new RowConstraints();
        rc.setFillHeight(false);
        interiorScroll.getRowConstraints().add(rc);

        Button addDirButton = new Button();
        addDirButton.setLayoutX(636);
        addDirButton.setLayoutY(125);
        addDirButton.setPrefSize(213,50);
        addDirButton.setId("dispatchersConfigurationButton");

        Button backButton = new Button();
        backButton.setLayoutX(858);
        backButton.setLayoutY(30);
        backButton.setPrefSize(22,22);
        backButton.setId("backBtn");

        backButton.setOnAction(e -> {
            gui.swapWindow(gui.getMainFoldersWindow().getPane());
        });

        root.setId("MainFoldersWindow");
        root.getChildren().add(sp);
        root.getChildren().add(backButton);
        root.getChildren().add(addDirButton);
        return root;
    }

    private void checkIfFinalFolder(String dirPath) {
        File f = new File(dirPath);
        File[] files = f.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];   
            if (!file.isDirectory()) { 
                gui.getFinalFolderWindow().setOrigin("Main");
                gui.getFinalFolderWindow().setPath(dirPath);
                gui.swapWindow(gui.getFinalFolderWindow().getPane() );
            }
        }
    }
    private void buildList(String dirPath,GridPane root){
        File f = new File(dirPath);
        File[] files = f.listFiles();
        int x = 0;
        int y = 0;

        if (files != null)
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            addItem(x, y, file.getName(),root);
            x++;
            if(x==3){
                RowConstraints rc = new RowConstraints();
                rc.setFillHeight(false);
                rc.setVgrow(Priority.NEVER) ;
                root.getRowConstraints().add(rc);
                x=0;
                y++;
            }
        }
    }

    private void addItem(int x,int y,String name,GridPane interiorScroll){
        Pane pane = new Pane();
        pane.setPrefSize(261,131);
        pane.setMinSize(261,131);
        pane.getStyleClass().add("dispatcherItem");
        InputStream stream;
        ImageView imageView = new ImageView();
        imageView.setVisible(false);
        try {
            stream = new FileInputStream("src/img/upload.png");
            Image image = new Image(stream);
            imageView.setImage(image);
        } catch (FileNotFoundException e1) {
            System.out.println("No la trobo lol");
            e1.printStackTrace();
        }
        imageView.setLayoutX(93);
        imageView.setLayoutY(39);
        GridPane.setRowIndex(pane, y);
        GridPane.setColumnIndex(pane, x);
        Text text = new Text(name);
        text.setFont(Font.font("Roboto",20));
        text.setFill(Color.web("FFFFFF"));
        text.setLayoutX(22);
        text.setLayoutY(100);
        pane.getChildren().add(imageView);
        pane.getChildren().add(text);
        interiorScroll.getChildren().add(pane);

        pane.setOnMouseClicked(event -> {
            gui.getFinalFolderWindow().setOrigin("Sub");
            gui.getFinalFolderWindow().setPath(path+"\\"+name);
            gui.swapWindow(gui.getFinalFolderWindow().getPane());
        });

        pane.setOnDragOver(new EventHandler <DragEvent>() {

            public void handle(DragEvent event) {
                imageView.setVisible(true);
                text.setVisible(false);
                event.acceptTransferModes(TransferMode.ANY);
                pane.setPrefSize(261,150);
                pane.setMinSize(261,150);
                event.consume();
            }
        });
        pane.setOnDragDropped(new EventHandler <DragEvent>() {

            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    // Only get the first file from the list
                    List<File> files = db.getFiles();
                    for(File file : files) {
                        try {
                            Files.copy(file.toPath(),
                                (new File(path +"\\"+name+"\\"+ file.getName())).toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        pane.setOnDragExited(event->{
            imageView.setVisible(false);
            text.setVisible(true);
            pane.setPrefSize(261,131);
            pane.setMinSize(261,131);
        });

    }

}
