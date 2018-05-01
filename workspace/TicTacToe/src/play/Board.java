package play;

import java.awt.Color;

import greenfoot.Greenfoot;
import greenfoot.World;

public class Board extends World {

	public static boolean player2 = true;
	public static boolean player1 = false;
	int max = 16;
	int xposition = 50;
	int yposition = 50;
	int offset = 100;
	int line = 100;
	int nukeMax = 4;
	final static int SIZE = Main_Menu.SIZE;
	final static int CELL = 1;
	public static int nukeLimit = 0;
	private static int[][] array = new int[4][4];
	public Board(){
		super(SIZE,SIZE,CELL);
		background();
		mouseZones();
		nukeLimit = 0;		
	}
	public void background(){
		this.getBackground().setColor(Color.BLACK);
		this.getBackground().drawLine(line, 0, line, SIZE);
		line += offset;
		this.getBackground().drawLine(line, 0, line, SIZE);
		line += offset;
		this.getBackground().drawLine(line, 0, line, SIZE);
		this.getBackground().drawLine(0, line, SIZE, line);
		line -= offset;
		this.getBackground().drawLine(0, line, SIZE, line);
		line -= offset;
		this.getBackground().drawLine(0, line, SIZE, line);

	}
	public void mouseZones(){
		for(int i = 0; i < array.length; i ++ ){
			for(a = 0; a<array.length; a++){
			Check obj = new Check();
			this.addObject(obj, xposition, yposition);
			array[i][a] = [xposition += offset][yposition];
			if(xposition >= SIZE){
				yposition += offset;
				xposition = offset/2;
			}
			}
		}
	}
	public void act(){
		
		if(nukeLimit == nukeMax){
			Main_Menu.winner = "NUKES";
			Main_Menu menu = new Main_Menu();
			Greenfoot.setWorld(menu);
		}
	}
}
