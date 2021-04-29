package windows.extra;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.event.EventHandler;
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
        sp.setContent(interiorScroll);

        readCongif();
        dispatcherConfigPane.getChildren().add(sp);
        dispatcherConfigPane.getChildren().add(addDispatcher);
        dispatcherConfigPane.getChildren().add(addDispatcherText);
        dispatcherConfigPane.getChildren().add(minimizeButton);
        dispatcherConfigPane.getChildren().add(closeButton);

        closeButton.setOnAction(e -> {
            secondStage.close();
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
    public void readCongif(){
        try {
            File myObj = new File("src/files/dispatchers.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String dispatcherName = myReader.nextLine();
              String dispatcherPath = myReader.nextLine();
              createAndAddData(new Pane(),new Text(dispatcherName),new Text(dispatcherPath));
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    public void createAndAddData(Pane panel,Text dispatcherName,Text dispatcherPath){
        panel.getStyleClass().add("dispatcherItem");
        panel.setPrefHeight(60);

        dispatcherName.setFont(Font.font("Roboto",FontWeight.BOLD,18));
        dispatcherName.setFill(Color.web("FFFFFF"));
        dispatcherName.setLayoutX(10);
        dispatcherName.setLayoutY(25);

        dispatcherPath.setFont(Font.font("Roboto",FontWeight.BOLD,14));
        dispatcherPath.setFill(Color.web("FFFFFF"));
        dispatcherPath.setLayoutX(10);
        dispatcherPath.setLayoutY(50);

        panel.getChildren().add(dispatcherName);
        panel.getChildren().add(dispatcherPath);
        interiorScroll.getChildren().addAll(panel);
    }
    public Stage getStage(){
        return secondStage;
    }
}

