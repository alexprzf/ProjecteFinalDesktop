import javafx.application.Application;
import javafx.stage.Stage;
import windows.FinalFolderWindow;
import windows.LoginWindow;
import windows.MainFoldersWindow;
import windows.MainWindow;
import windows.SubFoldersWindow;
import windows.extra.GUIInteraction;

public class App extends Application{

    public void start(Stage mainStage) {
        GUIInteraction gui = new GUIInteraction(mainStage);
        MainFoldersWindow mainFoldersWindow = new MainFoldersWindow(gui);
        SubFoldersWindow subFoldersWindow = new SubFoldersWindow(gui);
        FinalFolderWindow finalFolderWindow = new FinalFolderWindow(gui);
        LoginWindow loginWindow = new LoginWindow(gui);
        MainWindow mainWindow = new MainWindow(gui);
        gui.setMainWindow(mainWindow);
        gui.setMainFoldersWindow(mainFoldersWindow);
        gui.setLoginWindow(loginWindow);
        gui.setSubFoldersWindow(subFoldersWindow);
        gui.setFinalFolderWindow(finalFolderWindow);
        
        gui.swapWindow(gui.getMainWindow().getPane());
    }

    public static void main(String[] args) {
        launch(args);
    } 
}
