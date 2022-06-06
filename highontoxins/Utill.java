package highontoxins;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.zip.ZipEntry;

import javax.swing.JFrame;

import highontoxins.gameObjects.tools.Vector;
import highontoxins.graphics.Graphic.GraphicFunc;

public class Utill { 

	/**Returns the correct mouse position even if the screen is full screen*/
	public static Vector getMousePosition(Vector vec) {
		Vector scaleVec = Main.screenSize.div(Main.getWindowSize());
		float scale = (float) Math.min(scaleVec.x(), scaleVec.y());
		return vec.multi(scale);
	}
	
	/***
	 * This method sets the icon image to the given input icon.
	 * @param icon The given image, which the icon will be set.
	 * @param frame The JFrame of the window, which icon should be changed.
	 */
	public static void setIconImage(BufferedImage icon, JFrame frame){
		if(icon != null){
			BufferedImage iconImage = new BufferedImage(icon.getWidth(), icon.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics ico = (Graphics2D)iconImage.getGraphics();
			
			ico.drawImage(icon, 0, 0, icon.getWidth(), icon.getHeight(), null);
			
			frame.setIconImage(iconImage);
		}
	}
	
	/***@return Returns the center of the computer screen.*/
	public static Vector getScreenCenter() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		return new Vector(screen.getWidth(), screen.getHeight()).sub(Main.windowSize).div(2);
	}
	
	/***
	 * A sigmoid function that goes from min (at negative infinity) to max (at posititve infinity).
	 * @param x The input given to the sigmoid function.
	 * @param min The value approximated by going towards negative infinity. 
	 * @param max The value approximated by going towards positive infinity.
	 * @param curve The curvature of the sigmoid function.
	 * @return The output with x as the given input.
	 */
	public static double sigmoid(double x, double min, double max, double curve) {
		/**
		 *     x*(max - min)      max + min
		 *  ------------------- + ---------
		 *  2*sqrt(x^2 + curve)       2
		 */
		return x*(max - min) / (2*Math.sqrt(Math.pow(x, 2) + curve)) + (max + min) / 2;
	}
	
	/***
	 * A cos function that goes from min to max, with a curvature.
	 * @param x The input given to the cos function.
	 * @param min The smallest possible output value. 
	 * @param max The biggest possible output value. 
	 * @param curve The curvature of the cos function.
	 * @return The output with x as the given input.
	 */
	public static double cos(double x, double min, double max, double curve) {
		/**        x
		 *  cos( ----- ) * (max - min) + max + min
		 *       curve
		 *  ---------------------------------------
		 *                    2
		 */
		return (Math.cos(x / curve)*(max - min) + max + min) / 2;
	}
	

	/***Returns true of the completely inside of object-1 is inside of object 2*/
	public static boolean isInside(Vector p1, Vector s1, Vector p2, Vector s2) {
		return  !(p1.x() + s1.x() < p2.x() || // x < 
				  p1.y() + s1.y() < p2.y() || // y <
				  p1.x() > p2.x() + s2.x() || // x >
				  p1.y() > p2.y() + s2.y());  // y >
	}
	
	
	private static final int c = 255;

	/**Returns the rgb value of a give h (based on HSV)*/
	public static Color H2RGB(float h) {
		
		int x = (int) ((1 - Math.abs((h * 6) % 2 - 1)) * 255);
		
		switch((int)(h * 6)) {
		case 0: return new Color(c, x, 0);
		case 1: return new Color(x, c, 0);
		case 2: return new Color(0, c, x);
		case 3: return new Color(0, x, c);
		case 4: return new Color(x, 0, c);
		case 5: return new Color(c, 0, x);
		}
		
		return null;
	}

	

	private static final int MAX_ITER = 2000;

	/**Visualization of the Mandelbrot set*/
	public static final GraphicFunc mandleBrotSet = (Vector c) -> {

		Vector z = new Vector();
		
		int i;
		for (i = 0; i < MAX_ITER && z.getLength() < 2; i++) {
			z = z.complexMulti(z).add(c);
		}
		
		if(i == MAX_ITER) return Color.BLACK;
		
		return Utill.H2RGB((float)i / MAX_ITER);
	};

	public static final GraphicFunc cosinus = (Vector pxlPos) -> {
		float dist = pxlPos.getLength();
		int brig = (int) cos(dist, 0, 255, 1);
		return new Color(brig, brig, brig);
	};
	
}
