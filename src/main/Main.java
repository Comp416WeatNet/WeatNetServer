package main;

import connection.ConnectionOpenWeatherMap;
import server.Server;

public class Main {
    public static void main(String[] args) {
//        Server server = new Server(Server.DEFAULT_SERVER_PORT);
//        API DENEMEK İÇİN SERVER KURULUMU COMMENTLEDIM
        ConnectionOpenWeatherMap connection = new ConnectionOpenWeatherMap(ConnectionOpenWeatherMap.DEFAULT_BASE_URL, ConnectionOpenWeatherMap.DEFAULT_CONTENT);
        udp_server.udpServer();
        System.out.println(connection.buildConnection("london"));
    }
}
