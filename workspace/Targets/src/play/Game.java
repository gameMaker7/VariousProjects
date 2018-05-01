package play;


import java.awt.Color;
import java.util.Random;

import greenfoot.Greenfoot;
import greenfoot.World;

public class Game extends World {

	
	final static int SIZE = Main_Menu.SIZE;
	final static int CELL = 1;
	static int score = 0;
	static int time = 0;
	int offset = SIZE/4;
	int position = offset * 2;
	int place = 25;
	int rotate = 90;
	int side = 0;
	int count = 20;
	String Score = "SCORE: " + score;
	String Time = "TIME: " + time;
	private int waves;
	private int a = 4;
	public static Random gen = new Random();
	public Game(){
		super(SIZE,SIZE,CELL);
		this.getBackground().setColor(Color.gray);
		this.getBackground().fill();	
		setup();
		Light.health = 3;
		score = 0;
		time = 0;
	}
	public void setup(){
		
		this.getBackground().setColor(Color.WHITE);
		addObject(new Light(), SIZE/2, SIZE/2);
		getBackground().setColor(Color.WHITE);
		int x = SIZE/8;
		int y = SIZE/3;
		Horizontal line = new Horizontal();
		addObject(line, 0, 0);
		line.draw(x,y);
		for(int i = 0; i<a; i ++){
			Spawner spawn = new Spawner();
			
			spawn.rotation = rotate;
			rotate += 90;
			spawn.side = side;
			side ++;
			addObject(spawn,0,0);
		}
	}
	
	public void act(){
		//showText(Score, offset, place)
		//showText(Time, offset + position, place)
		//showText(Waves, position, place)
		count--;
		if(count == 0){
			time ++;
			Time = "TIME: " + time;
			count = 20;
		}
				
	}
}
