package windows.extra;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class checkDporUpdates {
    private static final String USER_AGENT = "Mozilla/5.0";
    static List<Map<String, String>> dispatchersValues = new ArrayList<>();;
    public static void check(String user, int i) throws IOException{
        
        URL obj = new URL("http://127.0.0.1:8000/api/getDpor/"+user);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
            if(response.toString().length()<2){
                downloadNewDpors(response.toString(),i,user);
            }
		} else {
			System.out.println("GET request not worked");
		}
    }
    private static void downloadNewDpors(String string, int i, String user) throws IOException {
        string = string.replace("[", "");
        string = string.replace("]", "");
        string = string.replace("\"", "");
        string = string.replace(" ", "");
        String[] list = string.split(",");
        String url = "http://127.0.0.1:8000/api/getFile/"+user+"/";
        String url2 = "http://127.0.0.1:8000/api/swapStatus/"+user+"/";
        String aux;
        for (String string2 : list) {
            System.out.println(string2);
            aux=dispatchersValues.get(i).get("inbox")+"/"+string2+"/config.dpor";
            downloadUsingStream(url+string2, aux);
            swapStatus(url2+string2);
            System.out.println(dispatchersValues.get(i).get("inbox")+"/"+string2+"/config.dpor");
        }
    }
    private static void swapStatus(String string) throws IOException {
        URL obj = new URL(string);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}
    }
    public static void getDispatchersNames() throws IOException{
        DispatcherManager dispatcherManager = new DispatcherManager();
        getPathsAndName();
        for (int i = 0; i < dispatchersValues.size(); i++) {
            dispatcherManager.loadProperties(dispatchersValues.get(i).get("path")+"/");
            if(dispatcherManager.getValue("inbox")!=null){
                dispatchersValues.get(i).put("inbox",dispatcherManager.getValue("inbox").replace("\\\\", "/"));
                check(dispatcherManager.getValue("user"),i);
            }
        }
    }
    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    private static void getPathsAndName() {
        try {
            File myObj = new File("src/files/dispatchers.txt");
            Scanner myReader = new Scanner(myObj);
            int index = 0;
            while (myReader.hasNextLine()) {
                dispatchersValues.add(new HashMap<>());
                dispatchersValues.get(index).put("name",myReader.nextLine());
                dispatchersValues.get(index).put("path",myReader.nextLine());
                index++;
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}
