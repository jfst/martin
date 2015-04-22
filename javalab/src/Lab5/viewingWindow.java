package Lab5;

import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


public class viewingWindow extends JEditorPane implements HyperlinkListener{
	
	public viewingWindow(){
		//super();
		setEditable(false);
		setBackground(Color.LIGHT_GRAY);
		setContentType("text/html");
		addHyperlinkListener(this);
		
	}

	public void hyperlinkUpdate(HyperlinkEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
