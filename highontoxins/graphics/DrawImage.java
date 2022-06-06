package highontoxins.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import highontoxins.gameObjects.tools.Vector;
import highontoxins.graphics.Graphic.GraphicFunc;

public class DrawImage implements Runnable{

	//fields and variables
	private BufferedImage image;
	private Vector pxlPos;
	private GraphicFunc func;
	
	private float scale;
	private int min, max;
	
	public DrawImage(BufferedImage image, Vector pxlPos, float scale, int min, int max, GraphicFunc func) {
		
		//setting variables
		this.image = image;
		this.pxlPos = pxlPos;
		this.func = func;
		
		this.scale = scale;
		this.min = min;
		this.max = max;
		
		//getting image
		if(this.max > image.getHeight()) max = image.getHeight();
	}

	@Override
	public void run() {
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		for(int x = min; x < max; x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				g.setColor(func.getPixelColor(new Vector(pxlPos.x() + (float)x * scale, 
														 pxlPos.y() + (float)y * scale)));
				g.fillRect(x, y, 1, 1);
			}
		}

		g.dispose();
		
	}

}
