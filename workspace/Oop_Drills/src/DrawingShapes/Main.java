package DrawingShapes;

import java.awt.Color;

import greenfoot.World;

public class Main extends World {

	private static final int SIZE = 400;
	private static final int cellSize = 1;

	public Main() {
		super(SIZE, SIZE, cellSize);
		this.getBackground().setColor(Color.BLACK);
	}

	public static void main(String[] args) {
		
		Point p = new Point(100,100);
		Square s = new Square( 100, p);
	}
}


