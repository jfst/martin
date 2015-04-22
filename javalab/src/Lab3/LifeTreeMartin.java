package Lab3;

import javax.swing.*;
import javax.swing.tree.*;

import java.io.*;        
import java.util.Scanner;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.*;

public class LifeTreeMartin extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public LifeTreeMartin() {
      Container c = getContentPane();

      //*** Build the tree and a mouse listener to handle clicks
      root = readNode();
      treeModel = new DefaultTreeModel( root );
      tree = new JTree( treeModel );
      MouseListener ml = 
        new MouseAdapter() {
          public void mouseClicked( MouseEvent e ) {
            if ( box.isSelected() )
              showDetails( tree.getPathForLocation( e.getX(), 
                                                    e.getY() ) );
          }
        };
      tree.addMouseListener( ml );

      //*** panel the JFrame to hold controls and the tree
      controls = new JPanel();
      box = new JCheckBox( showString );
      init(); //** set colors, fonts, etc. and add buttons
      c.add( controls, BorderLayout.NORTH );
      c.add( tree, BorderLayout.CENTER );   
      setVisible( true ); //** display the framed window
   } 

   public void actionPerformed( ActionEvent e ) {
      String cmd = e.getActionCommand();
      if ( cmd.equals( closeString ) )
        dispose();
   }

   private void init() {
      tree.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
      controls.add( box );
      addButton( closeString );
      controls.setBackground( Color.lightGray );
      controls.setLayout( new FlowLayout() );    
      setSize( 400, 400 );
   }

   private void addButton( String n ) {
      JButton b = new JButton( n );
      b.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
      b.addActionListener( this );
      controls.add( b );
   }

   private MyNode readNode() {
	   try {
		   sc = new Scanner(new File("D:/Användare/jonas/Eclipse/workspace/Test/data/Liv.xml"), "UTF-8");
	   } catch (FileNotFoundException e) {
		   e.printStackTrace();
	   }

	   // Skapa rot
	   String line = sc.nextLine();
	   String name = line.substring(line.indexOf("=")+2, line.indexOf(">")-1);
	   String level = line.substring(1, line.indexOf(" "));
	   String text = line.substring(line.indexOf(">")+2, line.length());
	   MyNode root = new MyNode(name, level, text);
	   readNode(root);
//	   readNode2(root);
	   
//	   String rootNode = sc.next();
//	   String rootName = sc.next();
//	   rootName = rootName.replace("namn=\"", "");
//	   rootName = rootName.replace("\">", "");
//	   String rootText = sc.nextLine();
//	   MyNode root = new MyNode(rootNode, rootName, rootText);
	   return root;   
   }
   
   private void readNode(MyNode parent) {
	   while(sc.hasNext()){
		   String line = sc.nextLine();
		   if(line.startsWith("</")){
			   parent = (MyNode)parent.getParent();
			   continue;
		   }
		   String name = line.substring(line.indexOf("=")+2, line.indexOf(">")-1);
		   String level = line.substring(1, line.indexOf(" "));
		   String text = line.substring(line.indexOf(">")+2, line.length());
		   MyNode child = new MyNode(name, level, text);
		   parent.add(child);
		   if (line.indexOf("</") < 0) {
			   readNode(child);
		   }
	   }
		   
//		   if(Line.startsWith("</")){
//		   		break;
//		   }
//		   else if(Line.contains("<")){
//			   String level = Line.substring(1, Line.indexOf(" "));
//			   int nodeNameStart = Line.indexOf("\"");
//			   int nodeNameEnd = Line.indexOf("\"", nodeNameStart);
//			   String name = Line.substring(nodeNameStart,nodeNameEnd);
//			   int nodeTextStart = Line.indexOf(">");
//			   int nodeTextEnd = Line.indexOf(" ",nodeNameEnd);
//			   String text = Line.substring(nodeTextStart, nodeTextEnd);
//			   if(text == ""){
//				   text = sc.nextLine();
//			   }
//			   MyNode child = new MyNode(name, level, text);
//			   parent.add(child);
//			   readNode(child);
//		   }
//	   }
   }

   private void readNode2(MyNode parent) {

	   String tok = "";;
	   String level = "";
	   String name = "";
	   String text = "";
	   
	   while(sc.hasNext()) {
		   String line = sc.nextLine();
		   Scanner lsc = new Scanner(line);
		   
		   if(lsc.hasNext()) {
			   tok = lsc.next();
			   if(tok.startsWith("</")){
				   parent = (MyNode)parent.getParent();
				   continue;
			   }
			   level = tok.substring(1);
		   }

		   if(lsc.hasNext()) {
			   lsc.useDelimiter(">");
			   tok = lsc.next();
			   name = tok.substring(tok.indexOf("=")+2, tok.length()-1);
		   }
			   
		   text = "";
		   while(lsc.hasNext()) {
			   tok = lsc.next();
			   text = text + " " + tok;
		   }
		   if (text.indexOf("</") >= 0) {
			   text = text.substring(0, tok.indexOf("</")+1);
		   }
		   
		   MyNode child = new MyNode(name, level, text);
		   parent.add(child);
		   if (line.indexOf("</") < 0) {
			   readNode2(child);
		   }
	   }
   }
	   
   private void showDetails( TreePath p ) {
      if ( p == null )
        return;
//      MyNode node = (MyNode) tree.getLastSelectedPathComponent();   // Funkar också
      MyNode node = (MyNode) p.getLastPathComponent();
      JOptionPane.showMessageDialog(this, node.getLevel2() + ": " + node.getText());
      //      File f = new File( p.getLastPathComponent().toString() );
//      JOptionPane.showMessageDialog( this, f.getPath() + 
//                                     "\n   " + 
//                                     getAttributes( f ) );
   }

   private String getAttributes( File f ) {
      String t = "";
      if ( f.isDirectory() )
        t += "Directory";
      else
        t += "Nondirectory file";
      t += "\n   ";
      if ( !f.canRead() )
        t += "not ";
      t += "Readable\n   ";
      if ( !f.canWrite() )
        t += "not ";
      t += "Writeable\n  ";
      if ( !f.isDirectory() )
        t += "Size in bytes: " + f.length() + "\n   ";
      else {
        t += "Contains files: \n     ";
        String[ ] contents = f.list();
        for ( int i = 0; i < contents.length; i++ )
           t += contents[ i ] + ", ";
        t += "\n";
      } 
      return t;
   }

   public static void main( String[ ] args ) {

	  if(args.length>0) katalog=args[0];
       	new LifeTreeMartin();
   }
   
   private Scanner sc;
   private JCheckBox box;
   private JTree tree;
   private MyNode root;
   private DefaultTreeModel treeModel;
   private JPanel controls;
   private static String katalog="Liv";
   private static final String closeString = " Close ";
   private static final String showString = " Show Details ";
}
