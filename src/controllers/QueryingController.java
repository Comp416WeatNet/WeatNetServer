package controllers;

import auth.Authentication;
import connection.ConnectionOpenWeatherMap;
import query.Query;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class QueryingController {
    private BufferedReader is;
    private PrintWriter os;
    private Socket s;
    private AuthController authController;
    private Query query;
    private ConnectionOpenWeatherMap connection;

    public QueryingController(AuthController authController, BufferedReader is, PrintWriter os, Socket s) {
        this.authController = authController;
        this.is = is;
        this.os = os;
        this.s = s;
        this.connection = new ConnectionOpenWeatherMap(ConnectionOpenWeatherMap.DEFAULT_BASE_URL, ConnectionOpenWeatherMap.DEFAULT_CONTENT);

        query = new Query(this.is, this.os, this.s, this.connection);
    }


    public void startQueryingPhase() {
        query.startQuery();
    }

}
