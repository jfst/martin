package Messenger2;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ChatFrame {
	//Create the GUI components
	public static JFrame mainFrame = null;
	public static JTextArea chatText = null;		//where the text is shown
	public static JTextField chatField = null;		//where you write the message you want to send
	public static JTextField ipField = null;		//where you put the ip adress you want to connect to
	public static JTextField portField = null;		//where you put the port to connect to
	public static JRadioButton serverOption = null;	//choose if you want to run as server or client
	public static JRadioButton clientOption = null;
	public static JButton connectButton = null;		//for connecting and disconnecting 
	public static JButton disconnectButton = null;
	
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
	public static int port = 2345;
	public static boolean isServer = true;
	
	Thread thread;
	
	public ChatFrame(){
		JPanel pane = null;
		ActionListener buttonListener = null;
		JPanel connectionPane = new JPanel(new GridLayout(3,1));			//Connection pane
		
		//Create ip field
		pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pane.add(new JLabel("IP Adress: "));
		ipField = new JTextField(10);
		ipField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				serverIP = ipField.getText();
			}
		});
		pane.add(ipField);
		
		//Create port field
		pane.add(new JLabel("Port: "));
		portField = new JTextField(4);
		portField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				port = Integer.parseInt(portField.getText());
			}
		});
		pane.add(portField);
		connectionPane.add(pane);
		//Create the option for server/client
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isServer = e.getActionCommand().equals("server");
				if (isServer){
					ipField.setEnabled(false);				//Since we can't change ip adress if we run as host
					ipField.setText("localhost");			//If we run as server then we are the host
					serverIP = "localhost";					
				}
				else {
					ipField.setEditable(true);				//If we run as client we need to be able to choose ip
					ipField.setEnabled(true);
				}
			}
		};
		ButtonGroup bg = new ButtonGroup();					//Creates group of buttons
		serverOption = new JRadioButton("Server");	//Creates the option for server
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
		
		// Connect/Disconnect
		pane = new JPanel(new GridLayout(1,2));
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("connect")) {    
					start();									// Connect
				} else {
					close();                                    // Disconnect
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
		chatText = new JTextArea(10,20);
		chatText.setLineWrap(true);
		chatText.setEditable(false);
		JScrollPane chatTextPane = new JScrollPane(chatText);
		chatTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		chatTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		chatField = new JTextField();
		chatField.setEnabled(false);
		chatField.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					String txt = chatField.getText();
					if (!txt.equals("")) {
						//Send the message
						try {
							sendMsg(txt);
							chatField.setText("");
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
	public void start() {
		try {
			if (isServer){
				state = SERVER_STARTED;
				setState();

				srv = new Server(port);

				state = CONNECTED;
				setState();
			} else {
				state = CONNECTED;
				setState();
				cl = new Client(serverIP, port);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void close() {
		state = DISCONNECTED;
		setState();
		try {
			if(srv != null){
				srv.close();
			} else{
				cl.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void sendMsg(String msg) throws InterruptedException{
		if(isServer){
			srv.write(msg);
		} else{
			cl.write(msg);
		}
		chatText.append("You: " + msg + "\n");
	}
	public void checkMsg(){
		String msg;
		if(isServer)
			msg = srv.read();
		else
			msg = cl.read();
		if (msg.length() != 0) {
			chatText.append("Other: " + msg + "\n");
		}
	}

	//Renew the main frame
	public void setState(){
		switch (state) {
		case SERVER_STARTED:
			connectButton.setEnabled(false);
			disconnectButton.setEnabled(true);
			break;
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
		System.out.println("Main Frame repainted!");
		
	}
}
