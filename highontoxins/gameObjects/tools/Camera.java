package highontoxins.gameObjects.tools;

import java.awt.Color;
import java.awt.Graphics2D;

import highontoxins.Main;

public class Camera{
	
	//Fields
	protected Vector position;
	private float scale;
	
	//constructor
	public Camera(Vector position, float scale) {
		
		//setting values for fields
		this.position = position;
		this.scale = scale;
		
	}
	
	//drawing this camera
	public void draw(Graphics2D g, Camera camera) {
		
		//if the given camera is not null
		if(camera != null) {
			camera.worldObj2Cam(position, Main.screenSize, (camPos, camSize) -> {
				
				//Setting the color for graphics
				g.setColor(Color.RED);
				
				//drawing an outline of the this camera
				g.drawRect(
						(int)camPos.x(), 
						(int)camPos.y(), 
						(int)camSize.x(), 
						(int)camSize.y());
				
			});
		}
		
	}
	
	//setting the position of the camera
	public void setPosition(Vector v) {position = new Vector(v);}
	public Vector getPosition() {return position;}
	
	public void changeScale(float mult) {scale *= mult;}
	public float getScale() {return scale;}
	
	//drawing stuff using the camera
	public void worldObj2Cam(Vector position, Vector size, Func func) {
		func.function(world2CamPos(position), world2CamSize(size));
	}

	public Vector world2CamPos(Vector v) {return world2CamSize(v.sub(position)).add(Main.screenSize.div(2));}
	public Vector world2CamSize(Vector v) {return v.div(scale);}

	public Vector cam2WorldPos(Vector v) {return cam2WorldSize(v.sub(Main.screenSize.div(2))).add(position);}
	public Vector cam2WorldSize(Vector v) {return v.multi(scale);}
	
	public interface Func{
		void function(Vector position, Vector size); 
	}
}
