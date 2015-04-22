package Messenger3;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;


public class ChatFrame {
	//Create the GUI components
	public static JFrame mainFrame = null;
//	public static JTextArea chatText = null;
	public static JTextPane chatText = null;		//where the text is shown
	public static JTextArea chatField = null;
	//public static JTextField chatField = null;		//where you write the message you want to send
	public static JTextField ipField = null;		//where you put the ip adress you want to connect to
	public static JTextField portField = null;		//where you put the port to connect to
	public static JTextField nameField = null;		//where you choose the name you want to use
	public static JRadioButton serverOption = null;	//choose if you want to run as server or client
	public static JRadioButton clientOption = null;
	public static JButton connectButton = null;		//for connecting and disconnecting 
	public static JButton disconnectButton = null;
	public static JComboBox colorList = null;
	//TCP Components
	public Server srv = null;
	public Client cl = null;

	//Connection Status
	public final static int NULL = 0;
	public final static int DISCONNECTED = 1;
	public final static int CONNECTED = 2;
	public final static int SERVER_STARTED = 3;
	
	//Connection Info
	public static int state = DISCONNECTED;
	public static String serverIP = null;
	public static int port = 4321;
	public static boolean isServer = true;
	public static String name = "You"; 			//Default name
//	public static Color color = Color.BLACK;
	public static String color = "000000";

	public static StyledDocument doc = null;
	public static Style style = null;
	
	public static XMLHandler xml = new XMLHandler();

	public ChatFrame(){
		JPanel pane = null;
		ActionListener buttonListener = null;
		JPanel connectionPane = new JPanel(new GridLayout(5,1));			//Connection pane
		//Create color list
		pane = new JPanel(new BorderLayout());
		String[] colorString = {"Black","Red","Blue","Green"};
		colorList = new JComboBox(colorString);
		colorList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox cb = (JComboBox)e.getSource();
				String colorName = (String)cb.getSelectedItem();
				if(colorName == "Black"){
					color = "#000000";
//					color = Color.BLACK;
				}else if(colorName == "Red"){
					color = "#FF0000";
//					color = Color.RED;
				}else if(colorName == "Blue"){
					color = "#0000FF";
//					color = Color.BLUE;
				}else if(colorName == "Green"){
					color = "#00FF00";
//					color = Color.GREEN;
				}
			}
		});
		pane.add(colorList);
		connectionPane.add(pane);
		
		//Create ip field
		pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pane.add(new JLabel("IP Adress: "));
		ipField = new JTextField(10);
		ipField.setText("localhost");
		ipField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				serverIP = ipField.getText();
			}
		});
		pane.add(ipField);
		
		//Create port field
		pane.add(new JLabel("Port: "));
		portField = new JTextField(4);
		//portField.setText("5432");
		portField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				port = Integer.parseInt(portField.getText());
			}
		});
		pane.add(portField);
		connectionPane.add(pane);
		
		//Create name field
		pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pane.add(new JLabel("Name: "));
		nameField = new JTextField(10);
		nameField.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent e) {
				name = nameField.getText();
			}
		});
		pane.add(nameField);
		connectionPane.add(pane);
		
		//Create the option for server/client
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isServer = e.getActionCommand().equals("server");
				if (isServer){
					ipField.setEnabled(false);				//Since we can't change ip adress if we run as host
//					ipField.setText("localhost");			//If we run as server then we are the host
					serverIP = "localhost";					
				}
				else {
					ipField.setEditable(true);				//If we run as client we need to be able to choose ip
					ipField.setEnabled(true);
				}
			}
		};
		ButtonGroup bg = new ButtonGroup();					//Creates group of buttons
		serverOption = new JRadioButton("Server");			//Creates the option for server
		serverOption.setActionCommand("server");
		serverOption.addActionListener(buttonListener);
		clientOption = new JRadioButton("Client");
		clientOption.setActionCommand("client");
		clientOption.addActionListener(buttonListener);
		bg.add(serverOption);
		bg.add(clientOption);
		pane = new JPanel(new GridLayout(1,2));
		pane.add(serverOption);
		pane.add(clientOption);
		connectionPane.add(pane);
		
		// Connect/disconnect buttons
		pane = new JPanel(new GridLayout(1,2));
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Connect
				if(e.getActionCommand().equals("connect")) {
					connectButton.setEnabled(false);
					disconnectButton.setEnabled(true);
					start();
				}
				//Disconnect
				else {
					close();
				}
			}
		};
		connectButton = new JButton("Connect");
		connectButton.setActionCommand("connect");
		connectButton.addActionListener(buttonListener);
		connectButton.setEnabled(true);
		disconnectButton = new JButton("Disconnect");
		disconnectButton.setActionCommand("disconnect");
		disconnectButton.addActionListener(buttonListener);
		disconnectButton.setEnabled(false);
		pane.add(connectButton);
		pane.add(disconnectButton);
		connectionPane.add(pane);
		
		//Set up the chat pane
		JPanel chatPane = new JPanel(new BorderLayout());
//		chatText = new JTextArea(10,20);
//		chatText.setLineWrap(true);
		chatText = new JTextPane();
		chatText.setEditable(false);
		doc = chatText.getStyledDocument();
		style = chatText.addStyle("Style", null);
	
		JScrollPane chatTextPane = new JScrollPane(chatText);
		chatTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		chatTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

//		chatField = new JTextField();
		chatField = new JTextArea(3,20);
		chatField.setLineWrap(true);
		chatField.setEnabled(false);
		chatField.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					String txt = chatField.getText();
					if (!txt.equals("")) {
						//Send the message
						try {
							sendMsg(txt);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		
		chatPane.add(chatTextPane);
		chatPane.add(chatField, BorderLayout.SOUTH);
		
		//Set up main frame
		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.add(connectionPane, BorderLayout.NORTH);
		mainPane.add(chatPane, BorderLayout.CENTER);
		
		mainFrame = new JFrame();
		mainFrame.setContentPane(mainPane);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);	
	}
	
	public void start(){
		try{
			if (isServer){
				state = SERVER_STARTED;
				srv = new Server(port);
				state = CONNECTED;
			} else {
				cl = new Client(serverIP, port);
				state = CONNECTED;
				}
		}catch(IOException e){
			e.printStackTrace();
		}
		setState();

	}
	
	public void close(){
		state = DISCONNECTED;
		setState();
//		try{
			if(isServer){
				String txt = xml.createXML("", "", "", "disconnecting");
				srv.write(txt);
				srv.close();
			} else{
				// Send system message to server to signal disconnection
				String txt = xml.createXML("", "", "", "disconnecting");
				cl.write(txt);
				cl.close();
			}
//		} catch(IOException e){
//			e.printStackTrace();
//		}
	}
	
	public void sendMsg(String msg) throws InterruptedException{
		//String color = "#FF0145";
		String txt = xml.createXML(msg, color, name, "");
		if(isServer){
			srv.write(txt);
		}else{
			cl.write(txt);
		}
		
		try{
			Color c = Color.decode(color);
			StyleConstants.setForeground(style, c);
			doc.insertString(doc.getLength(), name + ": " + msg, style);
			chatField.setText("");
		} catch(BadLocationException e){
			e.printStackTrace();
		}

	}
	public void checkMsg(){
		if (state == DISCONNECTED) return;
		String msg = "";
		if(isServer){
			msg = srv.read();
		}else{
			msg = cl.read();
		}
		if(msg.length() != 0){
			String txt = xml.readXML(msg);
			if (xml.getSystemMsg().equals("disconnecting")) {
				if(isServer){
					state = SERVER_STARTED;
					srv.waitForClient(port);
					state = CONNECTED;
				} else {
					state = DISCONNECTED;
					cl.close();
					setState();
				}
			} else {
				Color c = Color.decode(xml.getColor());
				StyleConstants.setForeground(style, c);
				try {
					doc.insertString(doc.getLength(), xml.getName() + ": " + txt + "\n", style);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void setState(){
		switch (state) {
		case DISCONNECTED:
			connectButton.setEnabled(true);
			disconnectButton.setEnabled(false);
			ipField.setEnabled(true);
			portField.setEnabled(true);
			serverOption.setEnabled(true);
			clientOption.setEnabled(true);
			chatField.setEnabled(false);
			break;
		case CONNECTED:
			connectButton.setEnabled(false);
			disconnectButton.setEnabled(true);
			ipField.setEnabled(false);
			portField.setEnabled(false);
			serverOption.setEnabled(false);
			clientOption.setEnabled(false);
			chatField.setEnabled(true);
			break;
		}
		ipField.setText(serverIP);
		portField.setText((new Integer(port)).toString());
		serverOption.setSelected(isServer);
		if (serverOption.isSelected()){
			ipField.setEnabled(false);
		}
		clientOption.setSelected(!isServer);
		
		mainFrame.repaint();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Main Frame repainted!");
		
	}
}
