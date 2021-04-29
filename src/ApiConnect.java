
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ApiConnect {
   public static Boolean getLoginAuth(String user,String password){
      try {
         URL url = new URL ("https://api.portasigma.com/v1/users/"+user+"/");
         String encoding = Base64.getEncoder().encodeToString((user+":"+password).getBytes(StandardCharsets.UTF_8));

         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("GET");
         connection.setDoOutput(true);
         connection.setRequestProperty  ("Authorization", "Basic " + encoding);
         connection.getInputStream();
         return true;
     } catch(Exception e) {
         return false;
     }
   }
}
