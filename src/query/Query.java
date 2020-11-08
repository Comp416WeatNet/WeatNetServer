package query;

import connection.ConnectionOpenWeatherMap;
import model.DataType;
import stransfer.FileServer;

import java.io.*;
import java.net.Socket;

public class Query {
    private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "/CityList";
    // COMMAND SOCKET INFO
    private final BufferedReader cis;
    private final PrintWriter cos;
    private final Socket cs;
    // DATA SOCKET INFO
    private final BufferedReader dis;
    private final PrintWriter dos;
    private final Socket ds;
    private final ConnectionOpenWeatherMap connection;

    public Query(BufferedReader cis, PrintWriter cos, Socket cs, BufferedReader dis, PrintWriter dos, Socket ds, ConnectionOpenWeatherMap connection) {
        this.cis = cis;
        this.cos = cos;
        this.cs = cs;
        this.dis = dis;
        this.dos = dos;
        this.ds = ds;
        this.connection = connection;
    }

    public void startQuery() {
        try {
            String resp = cis.readLine();
            DataType dt = new DataType(resp);
            String cityName = dt.getPayload();
            String[] args = cityName.split(":");
            String queryType = args[0];
            String[] cityParams = getCityParams(args[1]);
//            File conn = connection.buildConnection(queryType, cityParams);
            FileServer.sendFiles(ds , System.getProperty("user.dir") + "/jsonWeatherData");
//            System.out.println(conn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getCityParams(String cityName) {
        String[] params = new String[2];
        try {
            File cities = new File(DEFAULT_FILE_PATH);
            BufferedReader fin = new BufferedReader(new FileReader(cities));
            String line;
            while ((line = fin.readLine()) != null) {
                line = line.toLowerCase();
                if (line.contains(cityName)) {
                    int index = line.indexOf("lat");
                    String latVal = line.substring(index + 5, line.indexOf('}', index + 5));
                    params[0] = "lat" + "-" + latVal;
                    index = line.indexOf("lon");
                    String lonVal = line.substring(index + 5, line.indexOf(',', index + 5));
                    params[1] = "lon" + "-" + lonVal;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return params;
    }
}
