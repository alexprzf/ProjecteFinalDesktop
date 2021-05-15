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

public class DispatcherListBuilder {
    static List<Map<String, String>> dispatchersValues = new ArrayList<>();
    public static void buildList(HBox generalPanel){
        getPathsAndName();
        for (int i = 0; i < dispatchersValues.size(); i++) {
            DispatcherManager.loadProperties(dispatchersValues.get(i).get("path")+"/");
            if(DispatcherManager.getValue("inbox")!=null){
                dispatchersValues.get(i).put("inbox",String.valueOf(getFileNumber(DispatcherManager.getValue("inbox"))));
                dispatchersValues.get(i).put("ok",String.valueOf(getFileNumber(DispatcherManager.getValue("ok"))));
                dispatchersValues.get(i).put("reject",String.valueOf(getFileNumber(DispatcherManager.getValue("reject"))));
                dispatchersValues.get(i).put("signed",String.valueOf(getFileNumber(DispatcherManager.getValue("signed"))));
            }
        }
        for (int i = 0; i < dispatchersValues.size(); i++) {
            generateBlock(i,generalPanel);
        }
        
    }

    private static void generateBlock(int i, HBox generalPanel) {
        Pane block = new Pane();
        block.setPrefSize(240,184);
        block.setMinSize(240,184);
        block.getStyleClass().add("dispatcherItem");

        if(dispatchersValues.get(i).get("inbox")==null){
            Text wrongDir = new Text("This path is not a dispatcher");
            wrongDir.setFont(Font.font("Roboto",FontWeight.BOLD,16));
            wrongDir.setFill(Color.RED);
            wrongDir.setLayoutX(54);
            wrongDir.setLayoutY(50);
            block.getChildren().add(wrongDir);
        }else{
            Text inbox = new Text(dispatchersValues.get(i).get("inbox"));
            inbox.setFont(Font.font("Roboto",FontWeight.BOLD,12));
            inbox.setFill(Color.web("FFFFFF"));
            inbox.setLayoutX(15);
            inbox.setLayoutY(119);
    
            Text ok = new Text(dispatchersValues.get(i).get("ok"));
            ok.setFont(Font.font("Roboto",FontWeight.BOLD,12));
            ok.setFill(Color.web("FFFFFF"));
            ok.setLayoutX(15);
            ok.setLayoutY(131);
    
            Text reject = new Text(dispatchersValues.get(i).get("reject"));
            reject.setFont(Font.font("Roboto",FontWeight.BOLD,12));
            reject.setFill(Color.web("FFFFFF"));
            reject.setLayoutX(15);
            reject.setLayoutY(143);
    
            Text signed = new Text(dispatchersValues.get(i).get("signed"));
            signed.setFont(Font.font("Roboto",FontWeight.BOLD,12));
            signed.setFill(Color.web("FFFFFF"));
            signed.setLayoutX(15);
            signed.setLayoutY(155);
    
            block.getChildren().add(inbox);
            block.getChildren().add(ok);
            block.getChildren().add(reject);
            block.getChildren().add(signed);
        }

        Text title = new Text(dispatchersValues.get(i).get("name"));
        title.setFont(Font.font("Roboto",FontWeight.BOLD,24));
        title.setFill(Color.web("FFFFFF"));
        title.setLayoutX(54);
        title.setLayoutY(34);
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

    private static void getPathsAndName() {
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
}
