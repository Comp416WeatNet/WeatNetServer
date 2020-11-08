package connection;

import netscape.javascript.JSObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ConnectionOpenWeatherMap {
    public static final String DEFAULT_BASE_URL = "https://api.openweathermap.org";
    public static final String DEFAULT_ONECALL_CONTENT = "/data/2.5/onecall?";
    private static final String API_KEY = "8f262f416d14e54162be32e01df4a202";

    private String METHOD = "GET";
    private String USER_AGENT = "Mozilla/5.0";

    private String baseURL;
    private String content;
    private String data;
    private URL connection;
    private HttpURLConnection finalConnection;

    private HashMap<String, String> fields = new HashMap<>();

    public ConnectionOpenWeatherMap(String url, String content) {
        this.baseURL = url;
        this.content = content;
        data = this.baseURL + this.content;
    }

    public String buildConnection(String type, String... args) {
        //Building the connection
        if(type.toLowerCase().equals("basic weather maps")){
            return "";
        } else {
            return onecallAPIRequest(args);
        }
    }
    public String onecallAPIRequest(String... args){
        StringBuilder content = new StringBuilder();
        for (String arg : args){
            String[] params = arg.split("-");
            fields.put(params[0], params[1]);
        }
        if (!this.getEndpoints().equalsIgnoreCase("") && !this.getEndpoints().isEmpty()) {
            String vars = "";
            String vals = "";
            try {
                for (Map.Entry<String, String> entry : fields.entrySet()) {
                    vars = entry.getKey();
                    vals = entry.getValue();
                    data += ("&" + vars + "=" + vals);
                }
                data+= ("&appid=" + API_KEY);
                connection = new URL(data);

                //Reading the content
                BufferedReader reader = new BufferedReader(new InputStreamReader(readWithAccess(connection)));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line + "\n");
                }
                reader.close();
                return content.toString();
            } catch (MalformedURLException e) {
                System.err.println("System gave the error:\n" + e.getMessage());
            } catch (IOException e){
                System.err.println("System gave the error:\n" + e.getMessage());
            }
        }
        return "";
    }
    public String getEndpoints() {
        return fields.toString();
    }
    private String buildURL() {

        return "";
    }
    private InputStream readWithAccess (URL url) {

        try {
            finalConnection = (HttpURLConnection) url.openConnection();
            finalConnection.setRequestMethod(METHOD);
            finalConnection.addRequestProperty("User-Agent",USER_AGENT);
            finalConnection.addRequestProperty("Content-Type", "application/json");
            finalConnection.connect();

            return finalConnection.getInputStream();

        } catch(Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }
}



