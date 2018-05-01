package play;

import java.awt.Color;

import greenfoot.Actor;

public class Horizontal extends Actor{

	int width = Game.SIZE - Game.SIZE/4;
	int length = 10;
	int line = 75;
	int reset = 0;
	int offset = 100;

public void draw(int x, int y){
	getWorld().getBackground().fillRect(x, y, width, length);
	reset = x;
	while(x<width+offset){
		getWorld().getBackground().fillRect(x, y, length, line);
		x += width/10;
	}
	x = reset;
	while(x<width+offset){
		getWorld().getBackground().fillRect(x, y-line, length, line);
		x += width/10;
	}
	x = reset;
	y =  Game.SIZE - y;
	getWorld().getBackground().fillRect(x,y, width, length);
	reset = x;
	while(x<width+offset){
		getWorld().getBackground().fillRect(x, y, length, line);
		x += width/10;
	}
	x = reset;
	while(x<width+offset){
		getWorld().getBackground().fillRect(x, y-line, length, line);
		x += width/10;
	}
}

}
