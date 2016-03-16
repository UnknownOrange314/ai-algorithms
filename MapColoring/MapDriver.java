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
	
	
    public class MapDriver extends JFrame
   {
      private static final int DEFAULT_FPS=5;
      private static final int WIDTH=1000;
      private static final int HEIGHT=1000;
   
      private  MapPanel myMapPanel;//Game Windows

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
         new MapDriver(period*1000000L);
      }
       public MapDriver(long period)
      {
         
      
         super("The Great Space War");
     
;
         makeGUI();
         pack();
         setResizable(true);
      	
         Container c=getContentPane();
      	
      	
      	
         myMapPanel=new MapPanel(this,period,WIDTH,HEIGHT);
         c.add(myMapPanel,"Center");
         pack();
      	
         myMapPanel.startGame();
      	
         setVisible(true);
      
      
      }//end of init()
   
       private  void makeGUI()
      {	
         Container c= getContentPane();
         c.setLayout(new BorderLayout());
      
         
      
      
         menu=new JPanel();
         menu.setLayout(new GridLayout(5,1));
        
      
  
      
      
      
         c.add(menu,"East");
      	
      	
         scorePanel=new JPanel();
         scorePanel.setLayout(new GridLayout(1,1));
      	

      	

         c.add(scorePanel, "South");
      	
       
      
      }  // end of makeGUI()
   	
      



   
   
   // -------------------- applet life cycle methods --------------
   
       public void start()
      {  myMapPanel.resumeGame();  }
   
       public void stop()
      {  myMapPanel.pauseGame();  }
   
       public void destroy()
      {  myMapPanel.stopGame();  }
   
   
   } // end of WormChaseApplet class