package windows;

import java.io.File;
import java.io.IOException;
import java.awt.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import windows.extra.GUIInteraction;

public class FinalFolderWindow {
    GUIInteraction gui;
    String path;
    String origin;
    public FinalFolderWindow(GUIInteraction gui) {
        this.gui=gui;
    }
    public void setPath(String path){
        this.path=path;
    }
    public void setOrigin(String origin){
        this.origin=origin;
    }

    public Pane getPane(){
        ScrollPane sp = new ScrollPane();
        GridPane interiorScroll = new GridPane();
        System.out.println(path);
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

        Button backButton = new Button();
        backButton.setLayoutX(858);
        backButton.setLayoutY(30);
        backButton.setPrefSize(22,22);
        backButton.setId("backBtn");

        backButton.setOnAction(e -> {
            if(origin=="Main"){
                gui.swapWindow(gui.getMainFoldersWindow().getPane());
            }else{
                gui.swapWindow(gui.getsubFoldersWindow().getPane());
            }
        });

        root.setId("MainFoldersWindow");
        root.getChildren().add(sp);
        root.getChildren().add(backButton);
        return root;
    }

    private void buildList(String dirPath,GridPane root){
        File f = new File(dirPath);
        File[] files = f.listFiles();
        int x = 0;
        int y = 0;

        if (files != null)
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if(file.getName().endsWith(".pdf")){
                addItem(x, y, file.getName(),root);
                x++;
                if(x==5){
                    x=0;
                    y++;
                }
            }
        }
    }

    private void addItem(int x,int y,String name,GridPane interiorScroll){
        Pane pane = new Pane();
        pane.setPrefSize(140,140);
        pane.setMinSize(140,140);
        pane.getStyleClass().add("pdfItem");
        GridPane.setRowIndex(pane, y);
        GridPane.setColumnIndex(pane, x);
        Text text = new Text(name);
        text.setFont(Font.font("Roboto",10));
        text.setFill(Color.web("FFFFFF"));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setLayoutY(140);
        text.setWrappingWidth(140);
        pane.getChildren().add(text);
        interiorScroll.getChildren().add(pane);

        pane.setOnMouseClicked(event -> {
            try {
                File myFile = new File(path+"\\"+name);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        });
    }

}
