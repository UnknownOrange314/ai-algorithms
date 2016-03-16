
// WormPanel.java
// Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th

/* The game's drawing surface. It shows:
     - the moving worm
     - the obstacles (blue boxes)
     - the current average FPS and UPS
*/

   import javax.swing.*;
   import java.awt.event.*;
   import java.awt.*;
   import java.text.DecimalFormat;
	
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

   //import com.sun.j3d.utils.timer.J3DTimer;



    public class	MapPanel extends JPanel implements Runnable
   {
   	//Menu and title objects
   	
      JLabel title; //Shows title of game
   	
   	
      JLabel PlayerOneScore;
		   	
      //public GameMenu myMenu;    //Buttons for menu
      JPanel top,menuPanel, scorePanel; //Top is for title of game
   												//menuPanel is for the menu
   												//ScorePanel is for the score
   	
   	//game variables	

   	

   	
   	//Graphics variables
      public BufferedImage myImage;
      public Graphics myBuffer1,myBuffer2;
      private static final int N=400;//size of panel
      private static final Color BACKGROUND = new Color(204, 204, 204);

      public Graphics g;
     

      


   	
     
   	

   	
  
   	
   
       
   
      private static long MAX_STATS_INTERVAL = 1000000000L;
   // private static long MAX_STATS_INTERVAL = 1000L;
    // record stats every 1 second (roughly)
   
      private static final int NO_DELAYS_PER_YIELD = 16;
   /* Number of frames with a delay of 0 ms before the animation thread yields
     to other running threads. */
   
      private static int MAX_FRAME_SKIPS = 5;   // was 2;
    // no. of frames that can be skipped in any one animation loop
    // i.e the games state is updated but not rendered
   
      private static int NUM_FPS = 10;
     // number of FPS values stored to get an average
   
   
   // used for gathering statistics
      private long statsInterval = 0L;    // in ns
      private long prevStatsTime;   
      private long totalElapsedTime = 0L;
      private long gameStartTime;
      private int timeSpentInGame = 0;    // in seconds
   
      private long frameCount = 0;
      private double fpsStore[];
      private long statsCount = 0;
      private double averageFPS = 0.0;
   
      private long framesSkipped = 0L;
      private long totalFramesSkipped = 0L;
      private double upsStore[];
      private double averageUPS = 0.0;
   
   
      private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
      private DecimalFormat timedf = new DecimalFormat("0.####");  // 4 dp
   
   
      private Thread animator;           // the thread that performs the animation
      private volatile boolean running = false;   // used to stop the animation thread
      private volatile boolean isPaused = false;
   
      private long period;                // period between drawing in _nanosecs_
   
      private MapDriver spTop;
   
   
   
   // used at game termination
      private volatile boolean gameOver = false;
      private int score = 0;
      private Font font;
      private FontMetrics metrics;
      private boolean finishedOff = false;
   
   // off screen rendering
      private Graphics dbg; 
      private Image dbImage = null;
		
	//size of panel
		private int PWIDTH;
		private int PHEIGHT;
		
	//these variables represent the bounds of the playing area
		private static final int MIN_X_POS=50;
		private static final int MAX_X_POS=450;
		private static final int MIN_Y_POS=50;
		private static final int MAX_Y_POS=450;
		private HashSet<State> myStates;
   
		ArrayList<Color> myColors;
		
		//Genetic Algorithm Variables


       public	MapPanel(MapDriver mySpaceshipPanel, long period,int w,int h)
      {
    
    	   
    	   
  
			myColors= new ArrayList<Color>();
			myColors.add(Color.RED);
			myColors.add(Color.BLUE);
			myColors.add(Color.GREEN);
			myColors.add(Color.YELLOW);
			PWIDTH=w;
			PHEIGHT=h;
         spTop = mySpaceshipPanel;
         this.period = period;
      	
         setBackground(Color.white);
         setPreferredSize( new Dimension(PWIDTH,PHEIGHT));
			
			setLayout(new BorderLayout());
      
      // create game components
         top=new JPanel();
         top.setLayout(new GridLayout(1,1));
         add(top, BorderLayout.NORTH);
      	
         title=new JLabel("The ultimate battle");//Game Title
         top.add(title);
      	
         scorePanel=new JPanel();
         scorePanel.setLayout(new GridLayout(1,1));
			PlayerOneScore=new JLabel("");//Score for playerOne
         scorePanel.add(PlayerOneScore);
			
			
         add(scorePanel, BorderLayout.SOUTH);
      	
      try {
		readFile();
		
		readBorders();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	boolean suceed=false;
	while(suceed==false)
	{
	suceed=colorStates();
	}
         //ImageIcon Instructions=new ImageIcon("Instructions.JPG");
			//g.drawImage(Instructions.getImage(),0,0,1000,300,null);
        
      
      	
      	
      

         

      
        // addKeyListener(new Player2Key());
         setFocusable(true);
      	
      }  // end of WormPanel()
       public void readBorders() throws FileNotFoundException
       {

  		 Scanner scanner = null;
	        try {
	            scanner = new Scanner(new BufferedReader(new FileReader("/home/situ/workspace/Map Coloring/src/BorderData.txt")));
	            
	            
	    
	           
	      
	   
	           
	            while (scanner.hasNext()) 
	            {
	                String border=scanner.nextLine();
	            	String n1=border.substring(0,2);
	            	String n2=border.substring(3,5);
	            	State s1=null;
	            	State s2=null;
	          
	            	for(State s: myStates)
	            	{
	          
	            		
	            		if(s.getName().equals(n1))
	            		{
	            			s1=s;
	            			
	            		}
	            		if(s.getName().equals(n2))
	            		{
	            			s2=s;
	            		}
	            	}
	      
	            	s1.addBorder(s2);
	            	s2.addBorder(s1);
	            
	                
	                
	            }
	         
	        } finally {
	            if (scanner != null) {
	            	scanner.close();
	            }
	        }
	        for(State s: myStates)
	        {
	        	System.out.println(s.getBorders());
	        }
	  
    	   
    	   
       }
       public void readFile() throws FileNotFoundException
       {
    	   myStates=new HashSet<State>();
    		 Scanner scanner = null;
 	        try {
 	            scanner = new Scanner(new BufferedReader(new FileReader("/home/situ/workspace/Map Coloring/src/MapData.txt")));
 	            
 	            
 	            ArrayList<Integer> myXPoints=new ArrayList<Integer>();
 	            ArrayList<Integer> myYPoints=new ArrayList<Integer>();
 	           
 	           
 	           String b=scanner.nextLine();
 	   
 	           
 	            while (scanner.hasNext()) 
 	            {
 	     
 	            	if(b.contains(" ")==false)
 	            	{
 	            		//System.out.println(b);
 	            		String myName=b;
 	            		b=scanner.nextLine();
 	            		State s=new State(myName);
 	            		while(b.equals("next")==false)
 	            		{
 	            			
 	            			int index1=b.indexOf(" ");
 	            			//System.out.println("reading:"+b);
 	 	            		String xPos=b.substring(0,index1);
 	 	            		String yPos=b.substring(index1);
 	 	            		
 	 	            	
 	 	            		int x=800-(int)(Double.parseDouble(xPos)+120)*15;
 	 	            		int y=800-(int)(Double.parseDouble(yPos)*15);
 	 	            		myXPoints.add(x);
 	 	            		myYPoints.add(y); 
 	 	            		b=scanner.nextLine();
 	 	            		if(b.equals("stop"))
 	 	            		{
 	 	            			s.addIsland(myXPoints,myYPoints);
 	 	            			myXPoints.clear();
 	 	            			myYPoints.clear();
 	 	            			b=scanner.nextLine();
 	 	            		}
 	            		}
 	            		myStates.add(s);
 	            		
 	            		
 	            	}
 	            	if(scanner.hasNextLine()==false)
 	            	{
 	            		break;
 	            	}
 	            	System.out.println(b);
 	            	b=scanner.nextLine();
 	            	System.out.println(b);
 	            	
 	           
 	                
 	            
 	                
 	                
 	            }
 	         
 	        } finally {
 	            if (scanner != null) {
 	            	scanner.close();
 	            }
 	        }

 	        
 	   
 	     
       }
   
   
   // ------------- game life cycle methods ------------
   // called from the applet's life cycle methods
   
       public void startGame()
      // initialise and start the thread 
      { 
         if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
         }
      } // end of init()
    
   
       public void resumeGame()
      // start game /resume a paused game
      {  isPaused = false;  
				
		} 
   
   
       public  void pauseGame()
      {
		  isPaused = true;	
		 
			 
		 }
   
   
       public void stopGame() 
      // stop the thread by flag setting
      { running = false;  
         finishOff();
      }
   
   // ----------------------------------------------
   
   
   
   
       public void run()
      /* The frames of the animation are drawn inside the while loop. */
      {
         long beforeTime, afterTime, timeDiff, sleepTime;
         long overSleepTime = 0L;
         int noDelays = 0;
         long excess = 0L;
      
         gameStartTime =System.nanoTime();
         prevStatsTime = gameStartTime;
         beforeTime = gameStartTime;
      
         running = true;
      
         while(running) {
			
				
				
				if(!isPaused)
				{
            gameUpdate(); 
            gameRender();   // render the game to a buffer
            paintScreen();  // draw the buffer on-screen
         }
            afterTime =System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;  
         
            if (sleepTime > 0) {   // some time left in this cycle
               try {
                  Thread.sleep(sleepTime/1000000L);  // nano -> ms
               }
                   catch(InterruptedException ex){}
               overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
            }
            else {    // sleepTime <= 0; the frame took longer than the period
               excess -= sleepTime;  // store excess time value
               overSleepTime = 0L;
            
               if (++noDelays >= NO_DELAYS_PER_YIELD) {
                  Thread.yield();   // give another thread a chance to run
                  noDelays = 0;
               }
            }
         
            beforeTime =System.nanoTime();
         
         /* If frame animation is taking too long, update the game state
         without rendering it, to get the updates/sec nearer to
         the required FPS. */
            int skips = 0;
            while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
               excess -= period;
               gameUpdate();    // update state but don't render
               skips++;
            }
            framesSkipped += skips;
         
         }
         finishOff();
      } // end of run()
   
   
   
       private void gameUpdate() 
      { 
    	 
         if (!isPaused && !gameOver)
			{
        	
    			
    			//System.out.println(fitness);
    			//System.exit(1);
			}
      	//fred.move()
      }  // end of gameUpdate()
       
   



  
       private void gameRender()
      {
         if (dbImage == null){
            dbImage = createImage(PWIDTH, PHEIGHT);
            if (dbImage == null) {
               System.out.println("dbImage is null");
               return;
            }
            else
               dbg = dbImage.getGraphics();
         }
      
      // clear the background
         dbg.setColor(Color.white);
         dbg.fillRect (0, 0, PWIDTH, PHEIGHT);
         dbg.setColor(Color.RED);
        
         //dbg.fillRect(0,0,800,800);
   
         
         dbg.setFont(font);
      
      // report frame count & average FPS and UPS at top left
      // dbg.drawString("Frame Count " + frameCount, 10, 25);
         dbg.drawString("Average FPS/UPS: " + df.format(averageFPS) + ", " +
                                df.format(averageUPS), 20, 25);  // was (10,55)
      
         dbg.setColor(Color.green);
 
       drawStates(dbg);
      // draw game elements: the obstacles and the worm

      	

 

   
         //Bullets.setText("Bullets"+myBullets.size());
      	


      	
      
      
         if (gameOver)
            gameOverMessage(dbg);
      }  // end of gameRender()
       private void drawStates(Graphics g)
       {
    	    for(State s: myStates)
    	    {
    	        System.out.println("hi");
    	    	s.draw(g);
    	    }
    	    System.out.println("blah");
    	    
       }
    
   
   
       private void gameOverMessage(Graphics g)
      // center the game-over message in the panel
      {
         String msg = "Game Over. Your Score: " + score;
         int x = (PWIDTH - metrics.stringWidth(msg))/2; 
         int y = (PHEIGHT - metrics.getHeight())/2;
         g.setColor(Color.red);
         g.setFont(font);
         g.drawString(msg, x, y);
      }  // end of gameOverMessage()
   
   
       private void paintScreen()
      // use active rendering to put the buffered image on-screen
      { 
         Graphics g;
         try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null))
               g.drawImage(dbImage, 0, 0, null);
            Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
            g.dispose();
         }
             catch (Exception e)   // quite commonly seen at applet destruction
            { System.out.println("Graphics Context error: " + e);  }
      } // end of paintScreen()
   
   	
   
   
       private void finishOff()
      /* Tasks to do before terminating. Called at end of run()
      and via applet's destroy() calling stopGame().
      
      The call at the end of run() is not really necessary, but
      included for safety. The flag stops the code being called
      twice.
      */
      { 
         if (!finishedOff) {
            finishedOff = true;
         
         }
      } // end of finishedOff()
   
   
   
   	
        	
   	           
   	
   	
   
		public static int getLeftBorder()
		{
			return MIN_X_POS;
		}
		
		public static int getRightBorder()
		{
			return MAX_X_POS;
		}
		
		public static int getTopBorder()
		{
			return MIN_Y_POS;
		}
		
		public static int getBottomBorder()	
		{
			return MAX_Y_POS;
		}
		public boolean colorStates()
		{
			State startState=null;
			for(State s: myStates)
			{
				s.setLegalColors(myColors);
				if(s.getName().equals("WY"))
				{
					startState=s;
				}
			}

			
			int count=0;
			
			colorState(startState);
			for(int x=0;x<myStates.size()-1;x++)
			{
				State s=pickState();
				if(s==null)
				{
					return false;
				}
				if(s.getLegalColors()==null)
				{
					return false;
				}
				colorState(s);
				System.out.println(x);
			}
			return true;
			
		
			
			
			
			/*
			• Forward checking (FC), don’t wait until a color has been assigned to every state to
			  check for a solution; rather, as assignments are made immediately remove those colors
			  from every neighboring state’s possibility list. Then, if every state gets a color it must
			  be a solution and if any state runs out of colors you can give up early (backtrack).
			• Minimum remaining values (MRV), at each step we assign a color to whichever state
			  has the fewest possible colors available to it; on the first step every state has four
			  possible colors so we need a tie-breaker: choose the state with the most neighbors.
			• If there were only three colors instead of four then which state would be the first to
			  run out of colors? (Just use your four-color code but with only three colors and report
			  the first failure.)
			  */

		}
		public State pickState()//picks a state to color
		{
			State min=null;
			int minColors=5;
			for(State s: myStates)
			{
				int pCol=s.getLegalColors().size();
				if(pCol<minColors&&pCol>1)
				{
					min=s;
					minColors=pCol;
				}
				if(pCol==minColors&&minColors>1)
				{
					int s1=min.getBorders().size();
					int s2=s.getBorders().size();
					if(s2>s1)
					{
						min=s;
					}
				}
			}
			return min;
		}
		public void colorState(State s)
		{
			s.setLegalColor(s.pickLegalColor());
			Color c=s.getLegalColor();
			for(State border: s.getBorders())
			{
				border.removeLegalColor(c);
				
			}
			
		}
	
	

   
   }  