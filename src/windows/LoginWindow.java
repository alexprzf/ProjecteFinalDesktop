package windows;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import windows.extra.ApiConnect;
import windows.extra.DispatcherManager;
import windows.extra.GUIInteraction;

public class LoginWindow {
    GUIInteraction gui;
    String path;
    public LoginWindow(GUIInteraction gui) {
        this.gui=gui;
    }
    public void setPath(String path){
        this.path=path;
    }

    public Pane getPane(){
        Pane root = new Pane();
        TextField username = new TextField();
        username.getStyleClass().add("loginTextfields");
        username.setPrefSize(283, 32);
        username.setLayoutX(87);
        username.setLayoutY(384);
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.getStyleClass().add("loginTextfields");
        password.setPrefSize(283, 32);
        password.setLayoutX(87);
        password.setLayoutY(434);
        password.setPromptText("Password");

        Text warningText = new Text(110, 370, "Credencials erronis");
        warningText.setFill(Color.TRANSPARENT); 

        root.setId("LoginWindow");
        root.getChildren().add(username);
        root.getChildren().add(password);
        root.getChildren().add(warningText);

        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    System.out.println("Enter username");
                    password.requestFocus();
                }
            }
        });

        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    System.out.println("Enter password");
                    if(ApiConnect.getLoginAuth(username.getText(), password.getText())){
                        System.out.println("Credencials correctes");
                        DispatcherManager.modifiyProperties(path,"user", username.getText());
                        DispatcherManager.modifiyProperties(path,"password", password.getText());
                        gui.getMainFoldersWindow().setPath(path);
                        gui.swapWindow(gui.getMainFoldersWindow().getPane());
                    }else{
                        warningText.setFill(Color.RED);
                        System.out.println("Credencials erronis");
                    }
                }
            }
        });

        return root;
    }

}
