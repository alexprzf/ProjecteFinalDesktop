package windows.extra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class DispatcherListBuilder {
    GUIInteraction gui;
    List<Map<String, String>> dispatchersValues;
    public DispatcherListBuilder(GUIInteraction gui){
        this.gui=gui;
        this.dispatchersValues=new ArrayList<>();
    }

    public void buildList(HBox generalPanel){
        getPathsAndName();
        DispatcherManager dispatcherManager = new DispatcherManager();
        for (int i = 0; i < dispatchersValues.size(); i++) {
            dispatcherManager.loadProperties(dispatchersValues.get(i).get("path")+"/");
            if(dispatcherManager.getValue("inbox")!=null){
                dispatchersValues.get(i).put("inbox",String.valueOf(getFileNumber(dispatcherManager.getValue("inbox"))));
                dispatchersValues.get(i).put("ok",String.valueOf(getFileNumber(dispatcherManager.getValue("ok"))));
                dispatchersValues.get(i).put("reject",String.valueOf(getFileNumber(dispatcherManager.getValue("reject"))));
                dispatchersValues.get(i).put("signed",String.valueOf(getFileNumber(dispatcherManager.getValue("signed"))));
            }
        }
        for (int i = 0; i < dispatchersValues.size(); i++) {
            generateBlock(i,generalPanel);
        }
        
    }

    private void generateBlock(int i, HBox generalPanel) {
        Pane block = new Pane();
        block.setOnMouseClicked(event -> {
            DispatcherManager dispatcherManager = new DispatcherManager();
            boolean isValid = dispatcherManager.loadProperties(dispatchersValues.get(i).get("path"));
            if(isValid){
                if(ApiConnect.getLoginAuth(dispatcherManager.getValue("user"), dispatcherManager.getValue("password"))){
                    System.out.println("Credencials correctes");
                    gui.getMainFoldersWindow().setPath(dispatchersValues.get(i).get("path"));
                    gui.swapWindow(gui.getMainFoldersWindow().getPane());
                }else{
                    gui.getLoginWindow().setPath(dispatchersValues.get(i).get("path"));
                    gui.swapWindow(gui.getLoginWindow().getPane());
                }
            }
        });
        block.setPrefSize(240,184);
        block.setMinSize(240,184);
        block.getStyleClass().add("dispatcherItem");

        if(dispatchersValues.get(i).get("inbox")==null){
            Text wrongDir = new Text("This path is not a dispatcher");
            wrongDir.setFont(Font.font("Roboto",FontWeight.BOLD,16));
            wrongDir.setFill(Color.RED);
            wrongDir.setWrappingWidth(240);
            wrongDir.setLayoutY(50);
            wrongDir.setTextAlignment(TextAlignment.CENTER);
            block.getChildren().add(wrongDir);
        }else{
            Text inbox = new Text(TextCat.inboxText+dispatchersValues.get(i).get("inbox"));
            inbox.setFont(Font.font("Roboto",FontWeight.BOLD,12));
            inbox.setFill(Color.web("FFFFFF"));
            inbox.setLayoutX(15);
            inbox.setLayoutY(129);
    
            Text ok = new Text(TextCat.okText+dispatchersValues.get(i).get("ok"));
            ok.setFont(Font.font("Roboto",FontWeight.BOLD,12));
            ok.setFill(Color.web("FFFFFF"));
            ok.setLayoutX(15);
            ok.setLayoutY(141);
    
            Text reject = new Text(TextCat.rejectText+dispatchersValues.get(i).get("reject"));
            reject.setFont(Font.font("Roboto",FontWeight.BOLD,12));
            reject.setFill(Color.web("FFFFFF"));
            reject.setLayoutX(15);
            reject.setLayoutY(153);
    
            Text signed = new Text(TextCat.signedText+dispatchersValues.get(i).get("signed"));
            signed.setFont(Font.font("Roboto",FontWeight.BOLD,12));
            signed.setFill(Color.web("FFFFFF"));
            signed.setLayoutX(15);
            signed.setLayoutY(165);
    
            block.getChildren().add(inbox);
            block.getChildren().add(ok);
            block.getChildren().add(reject);
            block.getChildren().add(signed);
        }

        Text title = new Text(dispatchersValues.get(i).get("name"));
        title.setFont(Font.font("Roboto",FontWeight.BOLD,24));
        title.setFill(Color.web("FFFFFF"));
        title.setLayoutY(34);
        title.setWrappingWidth(240);
        title.setTextAlignment(TextAlignment.CENTER);
        block.getChildren().add(title);
        
        

        generalPanel.getChildren().addAll(block);
    }

    private static int getFileNumber(String dirPath) {
        int count = 0;
        File f = new File(dirPath);
        File[] files = f.listFiles();
    
        if (files != null)
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if(file.getName().endsWith(".pdf")){
                count++;
            }    
            if (file.isDirectory()) { 
                count+=getFileNumber(file.getAbsolutePath()); 
            }
        }
        return count;
    }

    private void getPathsAndName() {
        try {
            File myObj = new File("src/files/dispatchers.txt");
            Scanner myReader = new Scanner(myObj);
            int index = 0;
            while (myReader.hasNextLine()) {
                dispatchersValues.add(new HashMap<>());
                dispatchersValues.get(index).put("name",myReader.nextLine());
                dispatchersValues.get(index).put("path",myReader.nextLine());
                index++;
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    public DispatcherListBuilder() {
    }
    
}
