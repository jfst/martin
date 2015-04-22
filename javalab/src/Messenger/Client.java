package Messenger;

import java.io.*;
import java.net.*;

public class Client {
	BufferedReader input;
	PrintWriter output;
	Socket skt;
	
	public Client(String IP, int port){
		try {
			skt = new Socket(IP, port);		//create socket that connects to server
			input = new BufferedReader(new
				InputStreamReader(skt.getInputStream()));
		}
		catch(Exception e){
			System.out.println("Connection Failed!");
		}
	}
	public String waiting() throws InterruptedException{
		try {
			while(true){
				Thread.sleep(2000);
				String txt = input.readLine();
				if(txt != "")
					return txt;
			}

		} catch (IOException e) {
			e.printStackTrace();
			
		} 					
		return "";
	}

	public void close(){
		try {
			skt.close();
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
