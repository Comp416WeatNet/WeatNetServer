package query;

import connection.ConnectionOpenWeatherMap;
import model.DataType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Query {
    private BufferedReader is;
    private PrintWriter os;
    private Socket s;
    private ConnectionOpenWeatherMap connection;

    public Query(BufferedReader is, PrintWriter os, Socket s, ConnectionOpenWeatherMap connection) {
        this.is = is;
        this.os = os;
        this.s = s;
        this.connection = connection;
    }

    public void startQuery() {
    }
}
