//
//
//
//import java.awt.Color;
//
//import greenfoot.Greenfoot;
//import greenfoot.World;
//
//public class Main_Menu extends World {
//
//	public static String winner = "NONE";
//	String key = "";
//	public static int dragonWins = 0;
//	public static int phenoixWins = 0;
//	final static int SIZE = 600;
//	final static int CELL = 1;
//	int offset = SIZE/4;
//	int position = SIZE/2;
//	int place = offset/2;
//	int alter = 75;
//	public Main_Menu(){
//		super(SIZE,SIZE,CELL);
//		Score();
//		options();
//
//	}
//	public void Score(){
//		if(Game.score>dragonWins){
//			dragonWins = Game.score;
//		}
//		if(Game.time > phenoixWins){
//			phenoixWins = Game.time;
//		}
//	}
//	public void options(){
//		String instructions = "FOR INSTRUCTIONS PRESS SPACEBAR";
//		String play = "TO PLAY GAME PRESS ENTER";
//		String title = "Parking Lot Sim"; 
//		String dragons = "Best Score: ";
//		String phenoixs = "Highest Wave: ";
//		this.getBackground().setColor(Color.BLACK);
//		this.getBackground().fill();
//		this.getBackground().setColor(Color.WHITE);
//		this.getBackground().drawString(instructions, offset, offset);
//		this.getBackground().drawString(play, offset, offset + position);
//		this.getBackground().setColor(Color.RED);
//		this.getBackground().drawString(title, offset + alter, place);
//		this.getBackground().drawString(dragons + dragonWins, place, SIZE-place);
//		this.getBackground().drawString(phenoixs + phenoixWins, position + alter, SIZE-place);
//
//		
//		
//	}
//	public void act(){
//		key = Greenfoot.getKey();
//		if(key != null && key.equals("space")){
//			Instructions menu = new Instructions();
//			Greenfoot.setWorld(menu);	
//		}
//		if(key != null && key.equals("enter")){
//			Game game = new Game();
//			Greenfoot.setWorld(game);
//		}
//	}
//}
