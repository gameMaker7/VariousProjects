package play;

import java.awt.Color;

import greenfoot.Greenfoot;
import greenfoot.World;

public class Instructions extends World {

	final static int SIZE = 400;
	final static int CELL = 1;
	int position = 100;
	int offset = 100;
	int place = 50;
	String key = "";
	public Instructions(){
		super(SIZE,SIZE,CELL);
		String objective = "TO WIN ONE MUST CONNECT THREE SQUARES TOGETHER";
		String controls = "ALL CONTROLS ARE DONE VIA MOUSE CLICK";
		String exit = "TO RETURN TO MENU PRESS SPACEBAR";
		this.getBackground().setColor(Color.BLACK);
		this.getBackground().fill();
		this.getBackground().setColor(Color.WHITE);
		this.getBackground().drawString(objective, place,position);
		position += offset;
		this.getBackground().drawString(controls, place,position);
		position += offset;
		this.getBackground().drawString(exit, place,position);
	}
	public void act(){
		key = Greenfoot.getKey();
		if(key != null && key.equals("space")){
			Main_Menu menu = new Main_Menu();
			Greenfoot.setWorld(menu);	
		}
	}
}


