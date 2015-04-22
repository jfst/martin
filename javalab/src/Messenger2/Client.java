package Messenger2;

import java.io.*;
import java.net.*;

public class Client {
	BufferedReader input;
	PrintWriter output;
	Socket skt;
	String txt;
	
	public Client(String IP, int port) throws IOException{
		try {
			skt = new Socket(IP, port);		                        //create socket that connects to server
			input = new BufferedReader(new
				InputStreamReader(skt.getInputStream()));
			output = new PrintWriter(skt.getOutputStream(), true);	//creates printwriter for output stream

			System.out.println("Connection Successful!");
		}
		catch(Exception e){
			System.out.println("Connection Failed!");
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
	}
	

}
