package windows.extra;
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
    private static Map<String,String> uploaderProperties;

    public static void loadProperties(String dispatcherPath){
        uploaderProperties = new HashMap<>();
        try {
            File myObj = new File(dispatcherPath+"uploader.properties");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              try {
                data.substring(0,data.indexOf("="));
                uploaderProperties.put(data.substring(0,data.indexOf("=")), data.substring(data.indexOf("=")+1,data.length()).trim());
                
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
    public static String getValue(String key){
        return uploaderProperties.get(key);
    }

    public static void modifiyProperties(String property, String newValue,String dispatcherPath)
    {
        File fileToBeModified = new File(dispatcherPath+"uploader.properties");
         
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
