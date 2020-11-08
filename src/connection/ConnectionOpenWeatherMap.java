package connection;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ConnectionOpenWeatherMap {
    public static final String DEFAULT_BASE_URL = "https://api.openweathermap.org";
    public static final String DEFAULT_ONECALL_CONTENT = "/data/2.5/onecall?";
    private static final String IMAGE_URL = "https://tile.openweathermap.org/map/temp_new/0/0/0.png?appid=8f262f416d14e54162be32e01df4a202";
    private static final String API_KEY = "8f262f416d14e54162be32e01df4a202";
    private static final String IMAGE_FILE_PATH = System.getProperty("user.dir") + "/mapImage.png";
    private static final String JSON_FILE_PATH = System.getProperty("user.dir") + "/jsonWeatherData";

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

    public File buildConnection(String type, String... args) {
        //Building the connection
        if (type.toLowerCase().equals("3")) {
            File imageFile = weatherMapAPIRequest(args);
            return imageFile;
        } else if ("1245".contains(type)) {
            String jsonData = onecallAPIRequest(type, args);
            return jsonToFile(jsonData);
        } else {
            return null;
        }
    }

    private File weatherMapAPIRequest(String[] args) {
        File image = null;
        String imgURL = ConnectionOpenWeatherMap.IMAGE_URL;
        try {
            BufferedImage inputStreamImage = ImageIO.read(new URL(imgURL));
            image = new File(IMAGE_FILE_PATH);
            ImageIO.write(inputStreamImage, "png", image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private File jsonToFile(String jsonData) {
        File file = new File(JSON_FILE_PATH);
        FileOutputStream fos= null;  // true for append mode
        try {
            file.createNewFile();
            fos = new FileOutputStream(JSON_FILE_PATH);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            byte[] b= jsonData.getBytes();       //converts string into bytes
            fos.write(b);
            fos.write(String.valueOf(timestamp).getBytes());//writes bytes into file
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public String onecallAPIRequest(String type, String... args) {
        StringBuilder content = new StringBuilder();
        for (String arg : args) {
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
                data += ("&exclude=" + getExcludes(type));
                data += ("&appid=" + API_KEY);
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
            } catch (IOException e) {
                System.err.println("System gave the error:\n" + e.getMessage());
            }
        }
        return "";
    }

    private String getExcludes(String type) {
        String excludes = "";

        return excludes;
    }

//1. Current Weather forecast
//2. Daily forecast for 7 days
//3. Basic Weather maps
//4. Minute forecast for 1 hour
//5. Historical Weather for 5 days

    public String getEndpoints() {
        return fields.toString();
    }

    private InputStream readWithAccess(URL url) {

        try {
            finalConnection = (HttpURLConnection) url.openConnection();
            finalConnection.setRequestMethod(METHOD);
            finalConnection.addRequestProperty("User-Agent", USER_AGENT);
            finalConnection.addRequestProperty("Content-Type", "application/json");
            finalConnection.connect();

            return finalConnection.getInputStream();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}



