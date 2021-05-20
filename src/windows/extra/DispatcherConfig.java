package windows.extra;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;


public class DispatcherConfig {
    Stage secondStage = new Stage();
    private double xOffset = 0;
    private double yOffset = 0;
    ScrollPane sp = new ScrollPane();
    Button closeButton = new Button();
    Scene secondScene;
    Button minimizeButton = new Button();
    Button addDispatcher = new Button();
    Text addDispatcherText = new Text();
    VBox interiorScroll = new VBox();
    GUIInteraction gui;


    public DispatcherConfig(GUIInteraction gui) {
        this.gui=gui;
    }
    public void init(){
        Pane dispatcherConfigPane = new Pane();
        secondScene = new Scene(dispatcherConfigPane,576,505);
        secondScene.getStylesheets().add("css/mainWindow.css");
        dispatcherConfigPane.setId("dispatchersConfigurationFrame");
        secondScene.setFill(Color.TRANSPARENT);    
        secondStage.initStyle(StageStyle.TRANSPARENT);
        secondStage.setTitle("PS-Dispatcher");
        secondStage.setScene(secondScene);

        addDispatcher.setLayoutX(355);
        addDispatcher.setLayoutY(169);
        addDispatcher.setPrefSize(16,16);
        addDispatcher.setId("addDispatcherBtn");

        addDispatcherText.setText("Add dispatcher");
        addDispatcherText.setFont(Font.font("Roboto",FontWeight.BOLD,18));
        addDispatcherText.setFill(Color.web("EB690B"));
        addDispatcherText.setLayoutX(390);
        addDispatcherText.setLayoutY(182);
        addDispatcherText.setId("addDispatcherText");
        

        closeButton.setLayoutX(525);
        closeButton.setLayoutY(30);
        closeButton.setPrefSize(22,22);
        closeButton.setId("closeBtn");

        minimizeButton.setLayoutX(482);
        minimizeButton.setLayoutY(30);
        minimizeButton.setPrefSize(22,22);
        minimizeButton.setId("minimizeBtn");

        sp.pannableProperty().set(true);
        sp.fitToWidthProperty().set(true);
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        sp.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setLayoutX(25);
        sp.setLayoutY(252);
        sp.setPrefSize(526, 209);
        sp.setId("Scroll");
        interiorScroll.setAlignment(Pos.CENTER);
        interiorScroll.setSpacing(30);
        sp.setContent(interiorScroll);

        readCongif();
        dispatcherConfigPane.getChildren().add(sp);
        dispatcherConfigPane.getChildren().add(addDispatcher);
        dispatcherConfigPane.getChildren().add(addDispatcherText);
        dispatcherConfigPane.getChildren().add(minimizeButton);
        dispatcherConfigPane.getChildren().add(closeButton);

        closeButton.setOnAction(e -> {
            gui.swapWindow(gui.getMainWindow().getPane());
            secondStage.close();
        });

        addDispatcher.setOnAction(e -> {
            try {
                addDispatcher();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        minimizeButton.setOnAction(e -> {
            secondStage.setIconified(true);
        });

        dispatcherConfigPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        dispatcherConfigPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                secondStage.setX(event.getScreenX() - xOffset);
                secondStage.setY(event.getScreenY() - yOffset);
            }
        });
    }
    private void addDispatcher() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
        File selectedDirectory = directoryChooser.showDialog(secondStage);
        String dispatcherPath = selectedDirectory.getAbsolutePath();
        TextInputDialog td = new TextInputDialog("Enter dispatcher name");
        td.showAndWait();
        String dispatcherName = td.getEditor().getText();
        addDispatcherToFile(dispatcherPath,dispatcherName);
    }
    private void addDispatcherToFile(String dispatcherPath, String dispatcherName) throws IOException {
        Writer output;
        output = new BufferedWriter(new FileWriter("src/files/dispatchers.txt",true));
        output.append(dispatcherName+"\n");
        output.append(dispatcherPath+"\n");
        output.close();
        readCongif();
    }
    public void readCongif(){
        interiorScroll.getChildren().clear();
        try {
            File myObj = new File("src/files/dispatchers.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String dispatcherName = myReader.nextLine();
              String dispatcherPath = myReader.nextLine();
              createAndAddData(new Pane(),new Text(dispatcherName),new Text(dispatcherPath),new Button());
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    public void deleteDispatcher(String dispatcher) throws IOException{
        try {
            File inputFile = new File("src/files/dispatchers.txt");
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader("src/files/dispatchers.txt"));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;
            Boolean skip = false;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals(dispatcher) && !skip) {
                    pw.println(line);
                    pw.flush();
                }else if(skip){
                    skip=false;
                }else{
                    skip=true;
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile)){
                System.out.println("Could not rename file");
            }
            readCongif();
            }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    public void createAndAddData(Pane panel,Text dispatcherName,Text dispatcherPath,Button deleteButton){
        panel.getStyleClass().add("dispatcherItemList");
        panel.setPrefHeight(60);

        deleteButton.setPrefSize(35,35);
        deleteButton.setLayoutX(460);
        deleteButton.setLayoutY(13);
        deleteButton.setId("deleteButton");

        deleteButton.setOnAction(e -> {
            try {
                deleteDispatcher(dispatcherName.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });


        dispatcherName.setFont(Font.font("Roboto",FontWeight.BOLD,18));
        dispatcherName.setFill(Color.web("FFFFFF"));
        dispatcherName.setLayoutX(10);
        dispatcherName.setLayoutY(25);

        dispatcherPath.setFont(Font.font("Roboto",FontWeight.BOLD,14));
        dispatcherPath.setFill(Color.web("FFFFFF"));
        dispatcherPath.setLayoutX(10);
        dispatcherPath.setLayoutY(50);

        panel.getChildren().add(deleteButton);
        panel.getChildren().add(dispatcherName);
        panel.getChildren().add(dispatcherPath);
        interiorScroll.getChildren().addAll(panel);
    }
    public Stage getStage(){
        return secondStage;
    }
}

