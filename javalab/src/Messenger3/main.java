package Messenger3;

public class main {
	public static void main(String args[]){
		ChatFrame chat = new ChatFrame();
	//	ChatFrame chat2 = new ChatFrame();
		while(true){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(chat.state == chat.CONNECTED){
				chat.checkMsg();
		//		chat2.checkMsg();
			}
		}
		
	}
}
