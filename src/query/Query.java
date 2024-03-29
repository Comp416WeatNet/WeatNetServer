package query;

import connection.ConnectionOpenWeatherMap;
import model.DataType;
import model.Result;
import stransfer.FileServer;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

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
    private String token;

    public Query(BufferedReader cis, PrintWriter cos, Socket cs, BufferedReader dis, PrintWriter dos, Socket ds, ConnectionOpenWeatherMap connection, String token) {
        this.cis = cis;
        this.cos = cos;
        this.cs = cs;
        this.dis = dis;
        this.dos = dos;
        this.ds = ds;
        this.connection = connection;
        this.token = token;
    }

    public void startQuery() {
        try {
            String resp = cis.readLine();
            DataType dt = new DataType(resp);
            String cityName = dt.getPayload();
            String token = dt.getToken();
            if(!this.token.equals(token)){
                disconnect("Token mismatch", false);
                return;
            }
            String[] args = cityName.split(":");
            String queryType = args[0];
            String[] cityParams = getCityParams(args[1]);
            File conn = connection.buildConnection(queryType, cityParams);
            FileServer.sendFiles(cos, ds , conn.getPath());
            if ((resp = cis.readLine()) != null){
                dt = new DataType(resp);
                if (dt.getType() == DataType.QUERYING_SUCCESS){
                    System.out.println("File is successfully sent to : " + ds.getRemoteSocketAddress());
                    disconnect("File transmission successful", true);
                } else if(dt.getType() == DataType.QUERYING_FAIL) {
                    System.out.println("File transmission to : "+ ds.getRemoteSocketAddress() + " failed:");
                    disconnect("File transmission failed", false);
                }

            }
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

    private void disconnect(String message, boolean res) {
        Result result = new Result(message, res);
        DataType fail = result.convertToQueryDataType();
        try {
            cos.println(fail.getData());
            if (cis != null) {
                cis.close();
                System.err.println("The input stream of socket with the address: " + cs.getRemoteSocketAddress() + " is closed");
            }

            if (cos != null) {
                cos.close();
                System.err.println("The output stream of socket with the address: " + cs.getRemoteSocketAddress() + " is closed");
            }

            if (ds != null) {
                System.err.println("The data socket with the address: " + ds.getRemoteSocketAddress() + " is closed");
                ds.close();
            }
            if (cs != null) {
                System.err.println("The data socket with the address: " + cs.getRemoteSocketAddress() + " is closed");
                cs.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
