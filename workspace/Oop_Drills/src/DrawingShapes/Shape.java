package DrawingShapes;

import java.awt.Color;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public abstract class Shape extends Actor {

	protected int length = 5;
	private Color color = Color.BLACK;
	
	
	
	public int getLength() {
		return length;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public abstract void draw(Color color);
}
