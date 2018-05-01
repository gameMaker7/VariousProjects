package DrawingShapes;

import java.awt.Color;

import greenfoot.GreenfootImage;

public class Circle extends Shape{

	private Color color = Color.BLACK;
	private Point topLeft;
	public Circle(int length, int x, int y){
		topLeft = new Point(x, y);
		draw(color);
	}
	@Override
	public void draw(Color color){
		getWorld().getBackground().setColor(color);
		getWorld().getBackground().drawOval(topLeft.getX(), topLeft.getY(), length, length);
	
	}


}
