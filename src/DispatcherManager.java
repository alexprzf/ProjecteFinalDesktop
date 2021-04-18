import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DispatcherManager {
    public final String dispatcherPath = "C:/Users/Alex/Desktop/ps-dispatcher-1.8.181/";
    private Map<String,String> uploaderProperties;

    public DispatcherManager(){
        this.uploaderProperties = new HashMap<>();
        loadProperties();
    }
    public void loadProperties(){
        try {
            File myObj = new File(dispatcherPath+"uploader.properties");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              try {
                data.substring(0,data.indexOf("="));
                this.uploaderProperties.put(data.substring(0,data.indexOf("=")), data.substring(data.indexOf("=")+1,data.length()).trim());
                
              } catch (Exception e) {
                  System.out.println("Line empty");
              }
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    public String getValue(String key){
        return this.uploaderProperties.get(key);
    }
}
