import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class LoginWindow {

    public Pane getPane(){
        Pane  root = new Pane();
        
        TextField username = new TextField();
        username.getStyleClass().add("loginTextfields");
        username.setPrefSize(283, 32);
        username.setLayoutX(87);
        username.setLayoutY(384);
        PasswordField password = new PasswordField();
        password.getStyleClass().add("loginTextfields");
        password.setPrefSize(283, 32);
        password.setLayoutX(87);
        password.setLayoutY(434);

        root.setId("mainWindow");
        root.getChildren().add(username);
        root.getChildren().add(password);

        return root;
    }
}
