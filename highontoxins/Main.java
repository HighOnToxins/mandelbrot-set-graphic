
package highontoxins;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import highontoxins.gameObjects.tools.Vector;
import highontoxins.states.Run;

@SuppressWarnings("serial")
public class Main extends JPanel implements Runnable {

	//J-Frame stuff and icon image
	private static JFrame frame;

	//variables
	public static final String title = "Graphics Engine";
	public static final Vector screenSize = new Vector(1920, 1080);
	public static final Vector windowSize = screenSize.div(2);
	public static final float pxlScale = 7;
	public static float deltaTime;
	
	private static final int maxFps = 60;
	
	//graphics
	private BufferedImage screenImage;
	private Graphics2D g;
	
	//handling states
	private static Run run;
	
	//main method
	public static void main(String[] args){

		//adding j-frame and main, setting tile and setting exit on close
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Main());
		
		//set important j-frame variables
		frame.setResizable(false);
		
		//Running
		frame.setVisible(true);
		frame.pack();
	}

	//Creating a new thread
	private Thread thread;	
	public void addNotify(){
		super.addNotify();
		
		if(thread == null){
			//running thrugh all update
			thread = new Thread(this);
			thread.start();

			this.addKeyListener(run);
			this.addMouseListener(run);
			this.addMouseMotionListener(run);
			this.addMouseWheelListener(run);
			
		}
	}

	//Constructor
	public Main(){
		super();

		//setting states
		run = new Run();

		//add focus
		this.setFocusable(true);
		this.requestFocus();

		//setting window (position and size)
		this.setPreferredSize(new Dimension((int)windowSize.x(), (int)windowSize.y()));

		Vector location = Utill.getScreenCenter();
		frame.setLocation((int) location.x(), (int) location.y());
		
		//Setting full screen
//		setFullScreen(true);
		
	}
	
	//do run method
	public void run(){

		//Setting graphics
		screenImage = new BufferedImage((int) screenSize.x(), (int) screenSize.y(), BufferedImage.TYPE_INT_ARGB_PRE);
		g = (Graphics2D) screenImage.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 	 RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		//loop variables
		long exstraTime = 0;
		long whaitTime = 0;
		
		long beginTime = System.nanoTime();
		long firstWhaitTime = (long) (1000/maxFps);
		
		double lastTime = System.currentTimeMillis();

		//LOOP GAME
		while(true){
			
			//getting delta time
			double startTime = System.currentTimeMillis();
			deltaTime = (float) ((startTime - lastTime) / 1e3);
			lastTime = startTime;
			
			//update and draw
			g.clearRect(0, 0, (int)screenSize.x(), (int)screenSize.y());
			run.update();
			run.draw(g);			
			drawScreen();
			setScreen(isFullScreen);
			
			//getting sleep time
			exstraTime = (long) ((System.nanoTime() - beginTime) / 1e6);
			whaitTime = firstWhaitTime - exstraTime;
			
			//sleep
			try{
				Thread.sleep(whaitTime < 0 ? 0 : whaitTime);
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	//draw screen
	private void drawScreen(){
		
		Graphics g2 = this.getGraphics();
		if(frame.isDisplayable())
			g2.drawImage(screenImage, 0, 0, frame.getContentPane().getWidth(), frame.getContentPane().getHeight(), null);
		g2.dispose();
	}

	private static boolean isFullScreen = false;
	
	public static boolean isFullScreen() {return isFullScreen;}
	public static void setFullScreen(boolean setFull){isFullScreen = setFull;}
	
	//set isFull screen	
	private static void setScreen(boolean setFull){

		if(setFull == frame.isUndecorated()) return;
		
		//change
		if(setFull){
			//getting screen size
			Dimension scrrenSize = Toolkit.getDefaultToolkit().getScreenSize();
			
			//Removing decorations and setting screen-size
			frame.dispose();
			frame.setUndecorated(true);
			frame.setAlwaysOnTop(true);
			frame.setBounds(new Rectangle(0, 0, 
					(int)scrrenSize.getWidth(), 
					(int)scrrenSize.getHeight()));
			frame.setVisible(true);

		}else{
			
			//Adding decorations and setting screen-size
			frame.dispose();
			frame.setUndecorated(false);
			frame.setAlwaysOnTop(false);
			
			Vector screenCenter = Utill.getScreenCenter();
			frame.setBounds(new Rectangle((int)screenCenter.x(), (int)screenCenter.y(), 
					(int)windowSize.x() + (frame.getWidth()  - frame.getContentPane().getWidth()), 
					(int)windowSize.y() + (frame.getHeight() - frame.getContentPane().getHeight())));
			frame.setVisible(true);
		}
	}
	
	public static Vector getWindowSize() {return new Vector(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());}

	//exit game
	public static void exitGame(){System.exit(0);}
	
}
