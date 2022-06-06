package highontoxins.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import highontoxins.Main;
import highontoxins.Utill;
import highontoxins.gameObjects.tools.Camera;
import highontoxins.gameObjects.tools.Vector;
import highontoxins.graphics.Graphic;
import highontoxins.graphics.Graphic.GraphicFunc;

public class Run extends State{
	//variables
	private Camera cam;
	private Graphic image;
	
	private static final int MAX_ITER = 2000;
	
	//Constructor
	public Run() {
		
		//setting variables
		cam = new Camera(new Vector(), 2.5f/1080);
		
		/**Visualization of the Mandelbrot set*/
		GraphicFunc scaledMandleBrotSet = (Vector c) -> {

			Vector z = new Vector();
			
			int i;
			for (i = 0; i < MAX_ITER && z.getLength() < 2 / cam.getScale(); i++) {
				z = z.complexMulti(z).multi(cam.getScale()).add(c);
			}
			
			if(i == MAX_ITER) return Color.BLACK;
			
			return Utill.H2RGB((float)i / MAX_ITER);
		};
		
		image = new Graphic(scaledMandleBrotSet);
		
	}
	
	//Updating play state
	@Override
	public void update(){}
	
	//drawing play state
	@Override
	public void draw(Graphics2D g) {
		
		//setting background color
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, (int)Main.screenSize.x(), (int)Main.screenSize.y());
		
		//drawing visualization
		image.draw(g, cam); 
		
	}
	
	//If the mouse has been released
	@Override
	public void mouseReleased(MouseEvent e) {
		
		//Setting the position of the mouse
		cam.setPosition(cam.cam2WorldPos(Utill.getMousePosition(new Vector(e.getX(), e.getY()))));
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		cam.changeScale(e.getWheelRotation() * .1f + 1f);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();

		switch(keyCode) {
		case KeyEvent.VK_UP: 	 cam.changeScale(1/1.1f);  break;
		case KeyEvent.VK_DOWN: 	 cam.changeScale(1.1f);break;
		case KeyEvent.VK_SPACE:  image.threadedRender(cam); break; //load current screen
		case KeyEvent.VK_ENTER:	 image.saveImage("C:\\Users\\Markus Brun Olsen\\Downloads\\mandlebrotset\\"); break;
		case KeyEvent.VK_F:		 Main.setFullScreen(!Main.isFullScreen()); break;
		case KeyEvent.VK_ESCAPE: Main.exitGame(); break;
		}
		
	}
}
