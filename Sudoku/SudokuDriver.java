//Bullets only move once
//HAS SOMETHING TO DO WITH RECENT CHANGES
   import javax.swing.*;
   import java.awt.*;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.*;
   import java.awt.image.BufferStrategy.*;
   import java.applet.*;
   import java.io.*; 
   import java.util.*;
   import  sun.audio.*;    //import the sun.audio package
   import  java.io.*;
   //import javax.media.j3d.*; 
   //import com.sun.j3d.utils.timer.J3DTimer.*;
	
	
    public class SudokuDriver extends JFrame
   {
      private static final int DEFAULT_FPS=5;
      private static final int WIDTH=1000;
      private static final int HEIGHT=1000;
   
      private  SudokuPanel mySudokuPanel;//Game Windows

   	//buttons for gameMenu
      private JPanel menu; //The menu panel
      private JButton quit; //Tells Game to quit
      private JButton restart; //Tells Game to restart
      private JButton pause;//Pauses game
      private JButton resume;//Resumes game
      private JButton help; //Gives Game Instructions
   	
      private JPanel scorePanel;//gives game score
      private JLabel PlayerOneScore;
   	
   
      private javax.swing.Timer ScoringTimer; //Gets Player One's score
      
   
   
       public static void main(String[] args)
      {
      
         long period=(long)1000/DEFAULT_FPS;
         new SudokuDriver(period*1000000L);
      }
       public SudokuDriver(long period)
      {
         
      
         super("The Great Space War");
      	
         makeGUI();
         pack();
         setResizable(true);
      	
         Container c=getContentPane();
      	
      	
      	
         mySudokuPanel=new SudokuPanel(this,period,WIDTH,HEIGHT);
         c.add(mySudokuPanel,"Center");
         pack();
      	
         mySudokuPanel.startGame();
      	
         setVisible(true);
      
      
      }//end of init()
   
       private  void makeGUI()
      {	
         Container c= getContentPane();
         c.setLayout(new BorderLayout());
      
         
      
      
         menu=new JPanel();
         menu.setLayout(new GridLayout(5,1));
        
      
         quit=new JButton("quit");
         menu.add(quit);
         quit.addActionListener(new QuitListener());
      

      
         restart=new JButton("reset");
         menu.add(restart);
         restart.addActionListener(new RestartListener());
      
   
      
      
      
         c.add(menu,"East");
      	
      	
         scorePanel=new JPanel();
         scorePanel.setLayout(new GridLayout(1,1));
      	

      	

         c.add(scorePanel, "South");
      	
       
      
      }  // end of makeGUI()
   	
      
       private class QuitListener implements ActionListener//Listener for quit button
      {
          public void actionPerformed(ActionEvent e)
         {
            // myGameMenu=new GameMenu();
         	System.exit(1);
         	
         }
      }
   
       private class RestartListener implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
       
           
         }
      }
   
  
   
   
   
   
   // -------------------- applet life cycle methods --------------

   
   
   } // end of WormChaseApplet class
