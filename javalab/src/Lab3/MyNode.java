package Lab3;

import javax.swing.tree.*;


public class MyNode extends DefaultMutableTreeNode {
		private String level;
		private String text;
	
		public MyNode(String nodeName) {
			super(nodeName);
		}

		public MyNode(String nodeName, String Level, String Text){
			//DefaultMutableTreeNode myNode = new DefaultMutableTreeNode(Level);
			super(nodeName);
			level = Level;
			text = Text;
		}
		
		public void addLevel(String levelIn){
			level = levelIn;
		}
		public void addText(String textIn){
			text = textIn;
		}
		public String getLevel2(){
			 return level;
		}
		public String getText(){
			return text;
		}
}
