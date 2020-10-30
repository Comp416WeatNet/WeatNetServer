package connection;

import java.net.URL;
import java.util.HashMap;

public class ConnectionOpenWeatherMap {

    private double API_VERSION = 0;
    private String API = "";

    private String METHOD  = "POST";
    private String TYPE = "api/openweathermap.org";
    private String USER_AGENT = "Mozilla/5.0";
    private String data ="";
    private URL connection;
    private HttpURLConnection finalConnection;

    private HashMap<String, String> fields = new HashMap<String, String>();

    public class ConnectionOpenWeatherMap(String[] endpoint, String url, double version){
        
        this.API_VERSION = version;
        this.API = url;
        fields.put("version", String.valueOf(version));
        for(int i=0; i<endpoint.length; i++){
            String[] points = endpoint[i].split(,);
            
        }
    }
}

public String buildConnection() {
    StringBuilding content = new StringBuilder();
    if (!this.getEndpoints().equalsIgnoreCase("") && !this.getEndpoints().isEmpty()){
        String vars = "";
        String vals = "";
        try {
            for(Map.Entry<String, String> entry : fields.entrySet()){
                vars = entry.getKey();
                vals = entry.getValue();
                data+= ("&"+vars+"="+vals);
            }
            connection = new URL(API);
            BufferedReader reader = new BufferedReader (new InputStreamReader (readWithAccess));
            String line;
            while ((line = reader.readLine()) ! = null){
                content.append(line + "/n");
            }
            reaader.close();
            return content.toString();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}

public String getApiVersion() {
    return String.valueOf(API_VERSION);
}

public String getEndpoints() {
    return fields.toString();
}

public String getEndpointValue(String key) {
    return fields.get(key);
}

public void setUserAgent (String userAgent){
    this.USER_AGENT = userAgent;
}

public void setSubmissionType (String type){
    this.TYPE = type;
}


private InputStream readWithAccess (URL url, String data) {

    try {
        byte[] out = data.toString().getBytes();
        finalConnection = (HttpURLConnection) url.openConnection();
        finalConnection.setRequestMethod(METHOD);
        finalConnection.setDoOutput(true);
        finalConnection.addRequestProperty("User-Agent",USER_AGENT);
        finalConnection.addRequestProperty("Content-Type", TYPE);
        finalConnection.connect();

        try{
            OutputStream os = finalConnection.getOutputStream();

        } catch(Exception e){
            System.err.println(e.getMessage());
        }
        return finalConnection.getInputStream();

     } catch(Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    

}



