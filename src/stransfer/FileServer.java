package stransfer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;

public class FileServer {

    public static void sendFiles(Socket clientSocket, String DEFAULT_FILE_PATH) throws IOException {

        //Send file
        File myFile = new File(DEFAULT_FILE_PATH);
        System.out.println(myFile.getPath());
        byte[] mybytearray = new byte[(int) myFile.length()];

        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);

        DataInputStream dis = new DataInputStream(bis);
        dis.readFully(mybytearray, 0, mybytearray.length);

        OutputStream os = clientSocket.getOutputStream();

        //Sending file name and file size to the server
        DataOutputStream dos = new DataOutputStream(os);

        dos.writeUTF(myFile.getName());
        dos.writeLong(mybytearray.length);
        dos.write(mybytearray, 0, mybytearray.length);
        dos.flush();

        //Sending file data to the server
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();

        //Closing socket
        os.close();
        dos.close();
    }
}
