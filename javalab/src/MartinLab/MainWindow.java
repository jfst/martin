package MartinLab;

import javax.swing.*;
import java.awt.event.*;
//import paket.*;

public abstract class MainWindow implements ActionListener{
	
	public static void main(String[] args){
//		MyButton button = new MyButton();
		JFrame myFrame = new JFrame();
		MyButton button = new MyButton();

		myFrame.add(button);		//Lägger till knappen i ramen
		myFrame.pack();				//Gör så att ramen blir tillräckligt stor för att knappen ska få plats
		myFrame.setVisible(true);		//Gör så att ramen blir synlig
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Gör så att programmet slutar köra när man stänger fönstret
//		button.addActionListener(button);
		
	}

}
