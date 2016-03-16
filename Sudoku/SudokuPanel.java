
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



    public class	SudokuPanel extends JPanel implements Runnable
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
   
      private SudokuDriver spTop;
   
   
   
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
   
   
      ArrayList<Color> myColors;
   	
   	//Genetic Algorithm Variables
      ArrayList<Board> sBoards;
   
   
       public	SudokuPanel(SudokuDriver mySudokuPanel, long period,int w,int h)
      {
      
         
         
      
         myColors= new ArrayList<Color>();
         myColors.add(Color.RED);
         myColors.add(Color.BLUE);
         myColors.add(Color.GREEN);
      	//myColors.add(Color.YELLOW);
         PWIDTH=w;
         PHEIGHT=h;
         spTop = mySudokuPanel;
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
      
         sBoards=new ArrayList<Board>(50);
         for(int x=0;x<99;x++)
         {
            sBoards.add(new Board());
         }
      
         try {
            readPuzzles();
         } 
             catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
               e.printStackTrace();
            }
         for(Board s: sBoards)
         {
			
        
    
           
         	s.rowScan();
				s.rowScan();
				s.rowScan();
				s.print();
         //	System.out.println("starting to solve");
           //System.exit(1);
          s=solve(s,0);
         	
				
				
            if(s !=null)
            {
               s.print();
            }
            else
            {
               System.out.println("fail");
            }
         
         
         }
      
         //ImageIcon Instructions=new ImageIcon("Instructions.JPG");
      	//g.drawImage(Instructions.getImage(),0,0,1000,300,null);
        
      
      	
      
      
      
         
      
      
        // addKeyListener(new Player2Key());
         setFocusable(true);
      	
      }  
       public Board solve(Board s,int index)
      {
         s.rowScan();
         s.squareScan();
         int x=index%9;
         int y=index/9;
      	
         if(x==9||y==9)
         {
				System.out.println("hi");
            return s;
         
         }
         if(s.getLegalNums(x,y).size()==0)
         {
				System.out.println(x+":"+y);
            return null;
          
         }
         ArrayList<Integer> c=s.getLegalNums(x,y);
         ArrayList<Integer> d=new ArrayList<Integer>();
         for(int n: c)
         {
            d.add(n);
          
         }
         for(int i: d)
         {
            s.replaceData(x,y,i);//there may be a problem here
            Board b=solve(s,index+1);
            if(b !=null)
            {
               if(b.isSolved())
               {
                  return b;
               }
            }
         
          
         }
         return null;
      
      
      
      }
   	
   	
   	
   	// end of WormPanel()
       public void readPuzzles() throws FileNotFoundException
      {
         
         
         int count=0;
         Scanner scanner = null;
         try {
            scanner = new Scanner(new BufferedReader(new FileReader("P1.txt")));
               
          
              
         
              
            while (scanner.hasNext()) 
            {
               System.out.println(count);
               Board board=sBoards.get(count);
               String b=scanner.nextLine();
               board.addData(b);
                   //System.out.println(b);
               count++;
            }
         		
         		
            
         } 
         finally {
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
      
      // draw game elements: the obstacles and the worm
      
      	
      
      
      
      
         //Bullets.setText("Bullets"+myBullets.size());
      	
      
      
      	
      
      
         if (gameOver)
            gameOverMessage(dbg);
      }  // end of gameRender()
   
   
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
   
   
   
   
   
   }  
