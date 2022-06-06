package highontoxins.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import highontoxins.FileUtill;
import highontoxins.Main;
import highontoxins.gameObjects.tools.Camera;
import highontoxins.gameObjects.tools.Vector;

public class Graphic {
	
	//fields
	private GraphicFunc function;
	private BufferedImage image;
	
	private Vector position;
	private Vector imageSize;
	private float scale;
	
	private static final float BASE_SCALE = 1f;
	
	//constructor
	public Graphic(GraphicFunc func) { 
		
		//setting the lambda function to the given one
		function = func;

		//calculating image size
		/*int s = (int) (Main.screenSize.x() / BASE_SCALE); imageSize = new Vector(s, s);*/
		imageSize = Main.screenSize.div(BASE_SCALE);
		
	}

	//getting screen images
	public void render(Camera cam) {

		//setting variables to be the same as the input variables
		scale = cam.getScale() * BASE_SCALE;
		position = new Vector(cam.getPosition());
		
		//getting the starting position for the image
		Vector pixelPosition = position.sub(imageSize.multi(scale).div(2));
		
		//creating an image for the class to use
		image = new BufferedImage((int)imageSize.x(), (int)imageSize.y(), BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		//looping through the entire image
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				g.setColor(function.getPixelColor(new Vector(pixelPosition.x() + (float)x * scale, 
														     pixelPosition.y() + (float)y * scale)));
				g.fillRect(x, y, 1, 1);
			}
		}
		
		g.dispose();
	}

	private static final int THREAD_COUNT = 16;
	
	//getting screen images
	public void threadedRender(Camera cam) {

		//setting variables to be the same as the input variables
		scale = cam.getScale() * BASE_SCALE;
		position = new Vector(cam.getPosition());

		float scale = BASE_SCALE; //more detail
		
		//getting the starting position for the image
		Vector pixelPosition = position.div(cam.getScale()).sub(Main.screenSize.div(2));

		//creating an image for the class to use
		image = new BufferedImage((int)imageSize.x(), (int)imageSize.y(), BufferedImage.TYPE_INT_ARGB_PRE);
		
		//Setting up virtual cores
		Thread threads[] = new Thread[THREAD_COUNT];

		//running the threads
		int threadImageWidth = (int) (imageSize.x() / THREAD_COUNT);
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new DrawImage(
					image, 
					pixelPosition, 
					scale, 
					i*threadImageWidth, 
					(i+1)*threadImageWidth, 
					function));
			threads[i].start();
		}
		
		//waiting for the threads to be done
		for (int i = 0; i < threads.length; i++) {
			if(threads[i].isAlive()) i = -1;
		}


		name = "s" + cam.getScale() + "x" + cam.getPosition().x() + "y" + cam.getPosition().y();
		
		
	}
	
	private String name;
		
	public void saveImage(String path) {

		long start_time = System.currentTimeMillis();
		
		//Exporting the Mandelbrot set to image
		FileUtill.exportImage(image, path + name);

		long current_time = System.currentTimeMillis();
		System.out.printf("Exporting the image took %s second(s).\n", (current_time - start_time)/1000d);
		
	}
	
	//Drawing function
	public void draw(Graphics2D g, Camera cam) {
		
		//if there is an image to draw
		if(position != null && image != null) {

			//getting the image size in world
			Vector size = imageSize.multi(scale);
			
			//drawing the image for the camera
			cam.worldObj2Cam(position.sub(size.div(2)), size, (camPos, camSize) -> {
				g.drawImage(image, (int)camPos.x(), (int)camPos.y(), (int)camSize.x(), (int)camSize.y(), null);
			});
			
		}
	}

	//interface for lambda functions
	public interface GraphicFunc{
		Color getPixelColor(Vector pixelPosition); 
	}
	
}
