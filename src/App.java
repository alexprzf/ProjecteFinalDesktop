import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application{

    public void start(Stage primaryStage) {

        WindowManager windowManager = new WindowManager();
        DispatcherManager dispatcherManager = new DispatcherManager();
        Pane  root = new Pane();

        //Check if user is logged
        if(!dispatcherManager.getValue("user").isEmpty() && !dispatcherManager.getValue("password").isEmpty()){
            root = windowManager.getWindow("Login");
        }else{
            root = windowManager.getWindow("Login");
        }

        WindowActions windowBarActions = new WindowActions(root,primaryStage);
        windowBarActions.windowBarActionsIni();
        windowBarActions.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
