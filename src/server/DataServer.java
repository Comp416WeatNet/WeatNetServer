package server;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class DataServer {
    public static final int DEFAULT_DATA_SOCKET_PORT = 7001;
    private static DataServer dataServer;
    private ServerSocket serverSocket;

    private DataServer(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Opened up a data server socket on " + Inet4Address.getLocalHost());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Server class.Constructor exception on oppening a server socket");
        }
    }

    public static DataServer getDataServer() {
        if (dataServer == null)
            dataServer = new DataServer(DEFAULT_DATA_SOCKET_PORT);
        return dataServer;
    }

    public void setupThread(ServerThread st){
        Socket ds;
        try {
            ds = serverSocket.accept();
            System.out.println("A data connection was established with a client on the address of " + ds.getRemoteSocketAddress());
            BufferedReader dis = new BufferedReader(new InputStreamReader(ds.getInputStream()));
            PrintWriter dos = new PrintWriter(ds.getOutputStream(),true);
            st.createDataConnection(dis, dos, ds);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Server Class.Connection establishment error inside listen and accept function");
        }
    }
}
