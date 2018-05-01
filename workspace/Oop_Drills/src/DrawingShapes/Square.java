package DrawingShapes;

import java.awt.Color;

import greenfoot.GreenfootImage;

public class Square extends Shape{

	private Color color = Color.BLACK;
	private Point topLeft;
	public Square(int length, Point p){
		topLeft = p;
		draw(color);
	}
	@Override
	public void draw(Color color){
		getWorld().getBackground().setColor(color);
		getWorld().getBackground().drawRect(topLeft.getX(), topLeft.getY(), length, length);
	
	}


}
