package MartinLab;

import javax.swing.*;
import java.awt.event.*;
//import paket.*;

public abstract class MainWindow implements ActionListener{
	
	public static void main(String[] args){
//		MyButton button = new MyButton();
		JFrame myFrame = new JFrame();
		MyButton button = new MyButton();

		myFrame.add(button);		//L�gger till knappen i ramen
		myFrame.pack();				//G�r s� att ramen blir tillr�ckligt stor f�r att knappen ska f� plats
		myFrame.setVisible(true);		//G�r s� att ramen blir synlig
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //G�r s� att programmet slutar k�ra n�r man st�nger f�nstret
//		button.addActionListener(button);
		
	}

}
