package server;

import controllers.AuthController;
import controllers.QueryingController;
import model.DataType;
import model.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
    protected BufferedReader is;
    protected PrintWriter os;
    protected Socket s;
    private String line = new String();
    private String lines = new String();
    private AuthController authController;
    private QueryingController queryingController;

    /**
     * Creates a server thread on the input socket
     *
     * @param s input socket to create a thread on
     */
    public ServerThread(Socket s)
    {
        this.s = s;
        try
        {
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream());
            authController = new AuthController(is,os,this.s);
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
       if(check) {
           String token = authController.createToken();
           Result result = new Result(token, check);
           DataType dataType = result.convertToDatatype();
           os.println(dataType.getData());
           os.flush();
           // TODO Create a datasocket and send in the datasocket things to Querying controller
           queryingController = new QueryingController(authController, is, os, this.s);
           queryingController.startQueryingPhase();
       }
    }
}
