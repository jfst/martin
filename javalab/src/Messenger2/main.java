package Messenger2;

public class main {
	public static void main(String args[]){
		
		ChatFrame chat = new ChatFrame();
		
		while(true) {
			try {
				Thread.sleep(10);
			}
			catch (InterruptedException e) {}
			
			if(ChatFrame.state == ChatFrame.CONNECTED)
				chat.checkMsg();
		}
	}
}
