package Messenger;

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
	//public static ServerSocket serverSocket = null;
	//public static Socket socket = null;
	//public static BufferedReader in = null;
	//public static PrintWriter out = null;
	
	public Server srv;
	public Client cl;
	//Connection Status
	public final static int NULL = 0;
	public final static int DISCONNECTED = 1;
	public final static int CONNECTED = 2;
	
	//Connection Info
	public static int state = DISCONNECTED;
	public static String serverIP = null;
	public static int port = 2345;
	public static boolean isServer = true;
	
	public ChatFrame(){
		JPanel pane = null;
		ActionListener buttonListener = null;
		JPanel connectionPane = new JPanel(new GridLayout(3,1));			//Connection pane
		
		//Create ip field
		pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pane.add(new JLabel("IP Adress: "));
		ipField = new JTextField(10);
		ipField.setText("IP Adress");
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
		
		// Connect/disconnect buttons
		pane = new JPanel(new GridLayout(1,2));
		buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Connect
				if(e.getActionCommand().equals("connect")) {
					state = CONNECTED;
					run();
				}
				//Disconnect
				else {
					state = DISCONNECTED;
					run();
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
		
		//chatPane.add(chatText, BorderLayout.NORTH);
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
		
		chatText.setText("hej");
		//chatText.setText("då!");
		chatText.append("\ndå!");
		
	}
	//Renew the main frame
	public void run() {
		setState();
		
		if (isServer){
			srv = new Server(port);
			String msg = srv.waiting();
			chatText.append(msg);
		} else {
			cl = new Client(serverIP, port);
			
		}
	}
	public void sendMsg(String msg) throws InterruptedException{
		if(isServer){
			srv.write(msg);
			String msg_in = srv.waiting();
			chatText.append(msg_in);
		} else{
			cl.write(msg);
			cl.waiting();
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
		
	}
}
