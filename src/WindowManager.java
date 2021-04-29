
public class WindowManager {
    WindowActions windowActions;
    public WindowManager(WindowActions windowActions){
        this.windowActions = windowActions;
    }
    public void swapWindow(String window){
        switch(window){
            case "Login":
                new LoginWindow(this.windowActions).getPane(); 
                break;
            case "Dispatchers":
                new DispatchersWindows(this.windowActions).getPane();
                break;
        }
    }
}
