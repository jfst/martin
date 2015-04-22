package Lab5;

import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

public class textField extends JTextField implements KeyListener{
	private static final long serialVersionUID = 1L;
	private viewingWindow myViewingWindow;
	public textField(viewingWindow ViewingWindow){
		myViewingWindow = ViewingWindow;
		addKeyListener(this);
			
	}
	

	private void actionGo() throws IOException {
		URL pageURL = null;
		try {
			pageURL = new URL(this.getText());
			myViewingWindow.setPage(pageURL);
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(this, "Otillåten URL",
		               "Error", JOptionPane.ERROR_MESSAGE);

			//e.printStackTrace();
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			try {
				actionGo();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
	public void keyPressed(KeyEvent e){
		
	}


}
