package Messenger2;

import java.io.*;
import java.net.*;

public class Server {
	BufferedReader input;
	PrintWriter output;
	ServerSocket srv;
	Socket skt;
	String txt;
	Thread thread;
	public Server(int port) throws IOException{
		try {
			srv = new ServerSocket(port);	            //set up the server
			skt = srv.accept();			                //set up a socket that waits for the client to connect
			input = new BufferedReader(					//set up input reader
					new InputStreamReader(skt.getInputStream()));
			output = new PrintWriter(skt.getOutputStream(), true);	//creates printwriter for output stream with auto flush

			System.out.println("Client has connected!");
		}
		catch(Exception e) {
			System.out.println("Connection failed!");
		}
	}
	public String read() {
		try {
			if(input.ready()){
				txt = input.readLine();
				return txt;
			}
		}
		catch(IOException e) {
				e.printStackTrace();
		}
		return "";
		
	}
	public void write(String s){
		output.print(s);
		output.flush();
	}
	public void close() throws IOException{
		output.close();
		input.close();
		skt.close();
		srv.close();
	}
}
