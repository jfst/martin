package Lab5;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;


public class webFrame extends JFrame{
	public webFrame(){
		viewingWindow myViewingWindow = new viewingWindow();
		textField myTextField = new textField(myViewingWindow);

		//Skapa scroll
		JScrollPane pane = new JScrollPane(myViewingWindow);
//		getContentPane().add(pane, BorderLayout.CENTER);
		add(pane, BorderLayout.CENTER);
		add(myTextField, BorderLayout.NORTH);
//		add(myViewingWindow, BorderLayout.CENTER);
		
		setSize(new Dimension(700, 700));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
