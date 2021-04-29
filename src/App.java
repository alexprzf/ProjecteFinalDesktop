import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{

    public void start(Stage primaryStage) {
        DispatcherManager dispatcherManager = new DispatcherManager();
        WindowActions windowActions = new WindowActions(primaryStage);
        WindowManager windowManager = new WindowManager(windowActions);

        //Check if user is logged
        if(!dispatcherManager.getValue("user").isEmpty() && !dispatcherManager.getValue("password").isEmpty()){
            //Check if credentials are correct
            if(ApiConnect.getLoginAuth(dispatcherManager.getValue("user"),dispatcherManager.getValue("password"))){
                windowManager.swapWindow("Dispatchers");
            }else{
                windowManager.swapWindow("Login");
            }
        }else{
            windowManager.swapWindow("Login");
        }
        windowActions.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
