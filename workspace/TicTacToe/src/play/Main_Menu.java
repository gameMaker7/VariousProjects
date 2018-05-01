package play;

import java.awt.Color;

import greenfoot.Greenfoot;
import greenfoot.World;

public class Main_Menu extends World {

	public static String winner = "NONE";
	String key = "";
	public static int dragonWins = 0;
	public static int phenoixWins = 0;
	final static int SIZE = 400;
	final static int CELL = 1;
	int offset = 100;
	int position = 200;
	int place = 50;
	int alter = 75;
	public Main_Menu(){
		super(SIZE,SIZE,CELL);
		options();
	}
	public void options(){
		String instructions = "FOR INSTRUCTIONS PRESS SPACEBAR";
		String play = "TO PLAY GAME PRESS ENTER";
		String title = "TicTacToe + 2"; 
		String dragons = "DRAGON WINS: ";
		String phenoixs = "PHENOIX WINS: ";
		this.getBackground().setColor(Color.BLACK);
		this.getBackground().fill();
		this.getBackground().setColor(Color.WHITE);
		this.getBackground().drawString(instructions, offset, offset);
		this.getBackground().drawString(play, offset, offset + position);
		this.getBackground().setColor(Color.RED);
		this.getBackground().drawString(title, offset + alter, place);
		this.getBackground().drawString("PRIEVIOUS GAME WINNER: " + winner, offset, position);
		this.getBackground().drawString(dragons + dragonWins, place, SIZE-place);
		this.getBackground().drawString(phenoixs + phenoixWins, position + alter, SIZE-place);

		
		
	}
	public void act(){
		key = Greenfoot.getKey();
		if(key != null && key.equals("space")){
			Instructions menu = new Instructions();
			Greenfoot.setWorld(menu);	
		}
		if(key != null && key.equals("enter")){
			Board game = new Board();
			Greenfoot.setWorld(game);
		}
	}
}
