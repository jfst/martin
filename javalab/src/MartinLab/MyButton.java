package MartinLab;

//package paket;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class MyButton extends JButton implements ActionListener{
	//private JButton myButton;
	private static boolean state = false;
	private Color col1;
	private Color col2;
	private String text1;
	private String text2;
	
	
	public MyButton(Color col1, Color col2, String text1, String text2){
		//myButton = new JButton();
		this.col1 = col1;
		this.col2 = col2;
		this.text1 = text1;
		this.text2 = text2;
		if (state == false){
			this.setText(text1);
			this.setBackground(col1);

		} else {
			this.setText(text2);
			this.setBackground(col2);
		}

		this.addActionListener(this);
		
	}
	public MyButton(){
//		col1 = Color.blue;
//		col2 = Color.black;
//		text1 = "Blå";
//		text2 = "Svart";
		this(Color.blue,Color.yellow,"Blå","Gul");
		//		MyButton myButton = new MyButton(col1,col2,text1,text2);	
	}
	public void toggleState(){
		if (state == true){
			state = false;
			this.setText(text1);
			this.setBackground(col1);
		}
		else{
			state = true;
			this.setText(text2);
			this.setBackground(col2);
		}
	}
	public void actionPerformed(ActionEvent e) {
		this.toggleState();
	}
}
