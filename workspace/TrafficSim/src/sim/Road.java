package sim;

import java.util.Random;

import greenfoot.Actor;

public class Road extends Actor{

	Random gen = new Random();
	int road = 50;
	final static int DISTANCE = 25;
	int offset = 10;

	public void draw(int x, int y, int width, int length){
		getWorld().getBackground().fillRect(x, y, width, length);
	}
	
}
