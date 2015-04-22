package MartinLab;

import javax.swing.*;

public class MainApplet extends JApplet{
		
	private static final long serialVersionUID = 1L;

	MyButton myButton;
		
	public void init(){
		myButton = new MyButton();
		add(myButton);
		setVisible(true);
	}
}
