package Messenger3;

import java.awt.Color;
import java.io.*;
import java.net.*;

public class Server{
	BufferedReader input;
	PrintWriter output;
	ServerSocket srv;
	Socket skt;
	String txt;
	Thread thread;
	Color color = Color.BLACK;
	public Server(int port) throws IOException{
		start(port);
/*		try {
			srv = new ServerSocket(port);	//set up the server
			skt = srv.accept();				//set up a socket that waits for the client to connect
			System.out.println("Client has connected!");
			input = new BufferedReader(					//set up input reader
					new InputStreamReader(skt.getInputStream()));
			output = new PrintWriter(skt.getOutputStream(), true);	//creates printwriter for output stream with auto flush
		}catch(Exception e) {
			System.out.println("Connection failed!");
		}*/
	}
	public void waitForClient(int port){
//		try {
			close();
			start(port);
//			srv = new ServerSocket(port);
//			skt = srv.accept();      //Wait for the client to connect
//		} catch (IOException e) {
//			e.printStackTrace();
//		}				
	}
	public boolean isConnected() {
		try {
			output.print("H\n");
		} catch (Exception e){
			return false;
		}
		//boolean b = output.checkError();
		return true;
		//return skt.isConnected();
	}
	public String read(){
		try{
			if(input.ready()){
				txt = input.readLine();
				return txt;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return "";
	}
//	public void write(File f){
	public void write(String f){
		output.print(f + "\n");
		output.flush();
	}
	public void start(int port) {
		try {
			srv = new ServerSocket(port);	//set up the server
			skt = srv.accept();				//set up a socket that waits for the client to connect
			System.out.println("Client has connected!");
			input = new BufferedReader(					//set up input reader
					new InputStreamReader(skt.getInputStream()));
			output = new PrintWriter(skt.getOutputStream(), true);	//creates printwriter for output stream with auto flush
		}catch(Exception e) {
			System.out.println("Connection failed!");
		}
	}
	public void close() {
		try {
			output.close();
			input.close();
			skt.close();
			srv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
