package server;

import controllers.AuthController;
import controllers.QueryingController;
import model.DataType;
import model.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    private BufferedReader cis;
    private PrintWriter cos;
    private Socket cs;

    private BufferedReader dis;
    private PrintWriter dos;
    private Socket ds;

    private String line = new String();
    private String lines = new String();
    private ServerSocket dataSocket;
    private AuthController authController;
    private QueryingController queryingController;

    /**
     * Creates a server thread on the input socket
     *
     * @param cs input socket to create a thread on
     */
    public ServerThread(Socket cs)
    {
        this.cs = cs;
        try
        {
            cis = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            cos = new PrintWriter(cs.getOutputStream(),true);
            authController = new AuthController(cis, cos,this.cs);
        }
        catch (IOException e)
        {
            System.err.println("Server Thread. Run. IO error in server thread");
        }
    }

    /**
     * The server thread, authenticates the client. If it recieves a false input then it breaks the connection.
     */
    public void run()
    {
       boolean check = authController.authenticate();
       if (check) {
           createDataSocket(check);
           getQuery();
       }
    }

    public void createDataConnection(BufferedReader dis, PrintWriter dos, Socket ds) {
        this.dis = dis;
        this.dos = dos;
        this.ds = ds;
    }
    private void createDataSocket(boolean check){
            String token = authController.createToken();
            Result result = new Result(token, check);
            DataType dataType = result.convertToDatatype();
            cos.println(dataType.getData());
            DataServer dataServer = DataServer.getDataServer();
            dataServer.setupThread(this);
    }
    private void getQuery(){
        queryingController = new QueryingController(authController, cis, cos , cs, dis, dos, ds);
        queryingController.startQueryingPhase();
    }

}
