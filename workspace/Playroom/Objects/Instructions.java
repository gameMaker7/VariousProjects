//
//
//
//import java.awt.Color;
//
//import greenfoot.Greenfoot;
//import greenfoot.World;
//
//public class Instructions extends World {
//
//	final static int SIZE = 600;
//	final static int CELL = 1;
//	int position = SIZE/4;
//	int offset = SIZE/4;
//	int place = offset/2;
//	String key = "";
//	public Instructions(){
//		super(SIZE,SIZE,CELL);
//		String objective = "Survive the Out of Control cars";
//		String controls = "Left ad Right Arrow Keys turn. \nSpace to shoot and down or up to switch weapons.";
//		String exit = "TO RETURN TO MENU PRESS SPACEBAR";
//		this.getBackground().setColor(Color.BLACK);
//		this.getBackground().fill();
//		this.getBackground().setColor(Color.WHITE);
//		this.getBackground().drawString(objective, place,position);
//		position += offset;
//		this.getBackground().drawString(controls, place,position);
//		position += offset;
//		this.getBackground().drawString(exit, place,position);
//	}
//	public void act(){
//		key = Greenfoot.getKey();
//		if(key != null && key.equals("space")){
//			Main_Menu menu = new Main_Menu();
//			Greenfoot.setWorld(menu);	
//		}
//	}
//}
//
//
