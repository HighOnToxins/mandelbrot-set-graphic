package highontoxins.gameObjects.tools;

public class Vector {
	//FIELDS
	private double x, y;
	
	//CONSTRUCTORS
	public Vector() {x = y = 0;}
	public Vector(float f) {x = y = f;}
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Vector vec) {
		x = vec.x;
		y = vec.y;
	}
	
//	public Vector(float length, double angle) {
//		x = (float)Math.cos(angle) * length;
//		y = (float)Math.sin(angle) * length;
//	}
	
	//getting vector
	public double x() {return x;} 
	public double y() {return y;}
	
	public float getLength() {return (float) Math.sqrt(x*x + y*y);} 
	public double getAngle() {return Math.atan2(y, x);} 
	
	//setting vector
	public Vector setX(double f) {return new Vector(f, y);}
	public Vector setY(double f) {return new Vector(x, f);}

	public Vector setLength(double f) {return div(getLength()).multi(f);} 
	public Vector setAngle(double d) {return new Vector(getLength(), d);} 
	
	public Vector normalize() {return setLength(1);}
	
	//MATHEMATICAL OPERATIONS
		//Vector2D and Vector2D
	public Vector add(Vector vec) 	{return new Vector(x + vec.x, y + vec.y);} 
	public Vector sub(Vector vec)	{return new Vector(x - vec.x, y - vec.y);}
	public Vector multi(Vector vec)	{return new Vector(x * vec.x, y * vec.y);}
	public Vector div(Vector vec) 	{return new Vector(x / (vec.x == 0 ? 0 : vec.x), y / (vec.y == 0 ? 0 : vec.y));} 

		//Vector2D and float
	public Vector add(double f) 		{return new Vector(x + f, y + f);} 
	public Vector sub(double f) 		{return new Vector(x - f, y - f);} 
	public Vector multi(double f) 		{return new Vector(x * f, y * f);} 
	public Vector div(double f) 		{return new Vector(x / f, y / f);}
	
		//multiplication with complex numbers
	public Vector complexMulti(Vector vec) {
		return new Vector(x*vec.x - y*vec.y, 
						  x*vec.y + y*vec.x);
	}
	
	//absolute value
	public Vector setAbs()					{return new Vector(Math.abs(x), Math.abs(y));}
	
	//equals
	public boolean equals(Vector vec) 		{return x == vec.x && y == vec.y;}
	
}
