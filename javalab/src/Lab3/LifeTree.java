package Lab3;


import javax.swing.*;
import javax.swing.tree.*;

import java.io.*;        
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;

public class LifeTree extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public LifeTree() throws IOException {
      Container c = getContentPane();
      //*** Build the tree and a mouse listener to handle clicks
      root = new MyNode("Life");
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
      //*** build the tree by adding the nodes
      buildTree();
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

   private void buildTree() throws IOException {
//      File f=new File(katalog);
//      String[] list = f.list(); 
//      for (int i=0; i<list.length; i++ )
//         buildTree(new File(f,list[ i ]), root); 
	   Scanner s = null;
       try {
    	   s = new Scanner(new BufferedReader(new FileReader("D:/Användare/jonas/Eclipse/workspace/Test/data/Liv.xml")));
    	   root = readNode(s, root);
       } finally {
    	   s.close();
       }
      
   }

   private MyNode readNode(Scanner s, MyNode parent) throws IOException {
	   
       String line;

       while (s.hasNext()) {
    	   line = s.nextLine();
    	   System.out.println(line);
    	   
    	   if (line.startsWith("</")) {
    		   return parent;
    	   } else if (line.startsWith("<")) {
    		   MyNode retNode = new MyNode(line.substring(1, 4));
    		   parent.add(retNode);
    		   if (line.endsWith("</Klass>")) {
    			   //return retNode;
    		   } else {
    			   return retNode = readNode(s, retNode);
    		   }
    	   }
       } 
	   return parent;
   }
   
//   private void buildTree( File f, DefaultMutableTreeNode parent) {  
//      DefaultMutableTreeNode child = 
//         new DefaultMutableTreeNode( f.toString() );
//      parent.add(child);
//      if ( f.isDirectory() ) {
//        String list[] = f.list();
//        for ( int i = 0; i < list.length; i++ )
//           buildTree( new File(f,list[i]), child);            
//      }        
//   }    

   private void showDetails( TreePath p ) {
      if ( p == null )
        return;
      File f = new File( p.getLastPathComponent().toString() );
      JOptionPane.showMessageDialog( this, f.getPath() + 
                                     "\n   " + 
                                     getAttributes( f ) );
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

   public static void main( String[ ] args ) throws IOException {
       if(args.length>0) katalog=args[0];
       new LifeTree();
   }

   private JCheckBox box;
   private JTree tree;
   private MyNode root;
   private DefaultTreeModel treeModel;
   private JPanel controls;
   private static String katalog=".";
   private static final String closeString = " Close ";
   private static final String showString = " Show Details ";
}

	
	
