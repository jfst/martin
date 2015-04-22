package Messenger3;

import java.awt.Color;
import java.io.*;
import java.net.*;

public class Client {
	BufferedReader input;
	PrintWriter output;
	Socket skt;
	String txt;
	Color color;
	public Client(String IP, int port) throws IOException{
		try {
			skt = new Socket(IP, port);		//create socket that connects to server
			input = new BufferedReader(new
				InputStreamReader(skt.getInputStream()));
			output = new PrintWriter(skt.getOutputStream(), true);	//creates printwriter for output stream
			System.out.println("Connection Successful!");

		}
		catch(Exception e){
			System.out.println("Connection Failed!");
		}
	}
	
	public String read(){
		try {
			if(input.ready()){
				txt = input.readLine();
				return txt;		

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void write(String s){
		output.print(s + "\n");
		output.flush();
	}
	public Color getColor(){
		return color;
	}
	public void setColor(Color newColor){
		color = newColor;
	}
	public void close(){
		try{
			output.close();
			input.close();
			skt.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	

}
