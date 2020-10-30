package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    public static final int DEFAULT_SERVER_PORT = 9999;

    public Server(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Opened up a server socket on " + Inet4Address.getLocalHost());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Server class.Constructor exception on oppening a server socket");
        }
        while (true)
        {
            listenAndAccept();
        }
    }

    private void listenAndAccept()
    {
        Socket s;
        try {
                s = serverSocket.accept();
//                s.setSoTimeout(10 * 1000);
                System.out.println("A connection was established with a client on the address of " + s.getRemoteSocketAddress());
                ServerThread st = new ServerThread(s);
                st.start();
        }

        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Server Class.Connection establishment error inside listen and accept function");
        }
    }
}
