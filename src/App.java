import java.io.IOException;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import windows.FinalFolderWindow;
import windows.LoginWindow;
import windows.MainFoldersWindow;
import windows.MainWindow;
import windows.SubFoldersWindow;
import windows.extra.GUIInteraction;
import windows.extra.checkDporUpdates;

public class App extends Application{

    public void start(Stage mainStage) throws IOException {
        mainStage.getIcons().add(new Image("img/appIcon.png"));
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
        checkDporUpdates.getDispatchersNames();
    }

    public static void main(String[] args) {
        launch(args);
    } 
}
