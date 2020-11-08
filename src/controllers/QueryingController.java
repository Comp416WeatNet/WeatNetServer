package controllers;

import connection.ConnectionOpenWeatherMap;
import query.Query;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class QueryingController {
    // COMMAND SOCKET INFO
    private BufferedReader cis;
    private PrintWriter cos;
    private Socket cs;
    // DATA SOCKET INFO
    private BufferedReader dis;
    private PrintWriter dos;
    private Socket ds;

    private AuthController authController;
    private Query query;
    private ConnectionOpenWeatherMap connection;

    public QueryingController(AuthController authController, BufferedReader cis, PrintWriter cos, Socket cs, BufferedReader dis, PrintWriter dos, Socket ds) {
        this.authController = authController;
        this.cis = cis;
        this.cos = cos;
        this.cs = cs;
        this.dis = dis;
        this.dos = dos;
        this.ds = ds;
        this.connection = new ConnectionOpenWeatherMap(ConnectionOpenWeatherMap.DEFAULT_BASE_URL, ConnectionOpenWeatherMap.DEFAULT_ONECALL_CONTENT);

        query = new Query(this.cis, this.cos, this.cs, this.dis, this.dos, this.ds, this.connection);
    }


    public void startQueryingPhase() {
        query.startQuery();
    }

}
