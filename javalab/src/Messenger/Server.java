package Messenger;

import java.io.*;
import java.net.*;

public class Server {
	BufferedReader input;
	PrintWriter output;
	ServerSocket srv;
	Socket skt;
	
	public Server(int port){
		try {
			srv = new ServerSocket(port);	//set up the server
		}
		catch(Exception e) {
			System.out.println("Connection failed!");
		}
	}
	public String waiting(){
		try {
			skt = srv.accept();			//set up a socket that waits for the client to connect
			input = new BufferedReader(					//set up input reader
					new InputStreamReader(skt.getInputStream()));
			return input.readLine();

		} catch (IOException e) {
			e.printStackTrace();
			
		} 					
		return "";
	}
	public void close(){
		try {
			skt.close();
			srv.close();
			output.close();								//close streams
			input.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void write(String msg){
		try {
			output = new PrintWriter(skt.getOutputStream(), true);	//creates printwriter for output stream
			output.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} 	

	}
}
