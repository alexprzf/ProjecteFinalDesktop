import javafx.scene.layout.Pane;

public class WindowManager {
    public Pane getWindow(String window){
        switch(window){
            case "Login":
                return new LoginWindow().getPane(); 
        }
        return null;
    }
}
