package stransfer;

import java.io.*;
import java.net.*;

public class FileServer {

	public static void main(String[] args) throws Exception{
		
		ServerSocket ss=new ServerSocket (4333);
		Socket s= ss.accept();
		FileInputStream fr= new FileInputStream("F://SecurityAnswers.txt");
	byte b[]=new byte[2002];
	fr.read(b, 0, b.length);
	OutputStream os= s.getOutputStream();
		
	}
}
