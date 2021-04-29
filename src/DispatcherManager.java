import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DispatcherManager {
    public final String dispatcherPath = "C:/Users/Alex Curro/Desktop/ps-dispatcher-1.8.181/";
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

    public void modifiyProperties(String property, String newValue)
    {
        File fileToBeModified = new File(this.dispatcherPath+"uploader.properties");
         
        String newContent = "";
         
        BufferedReader reader = null;
         
        FileWriter writer = null;
         
        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));
             
            //Reading all the lines of input text file into oldContent
             
            String line = reader.readLine();
             
            while (line != null) 
            {
                if(line.contains(property) && !line.contains("."+property)){
                  newContent = newContent + property + "=" + newValue + System.lineSeparator();
                }else{
                  newContent = newContent + line + System.lineSeparator();
                }
                 
                line = reader.readLine();
            }
             
            //Rewriting the input text file with newContent
             
            writer = new FileWriter(fileToBeModified);
             
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources
                 
                reader.close();
                 
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
