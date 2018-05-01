package play;

import greenfoot.Actor;
import greenfoot.Greenfoot;

public class Light extends Actor {

	public static int health = 3;
	private int count = 10;
	private boolean weapon = true;
	public Light(){
		setImage("Images/trafficLightGreen.png");
	}
	public void gameOver(){

		Main_Menu restart = new Main_Menu();
		Greenfoot.setWorld(restart);
	}
	public void act(){
		count--;
		if(health == 3)setImage("Images/trafficLightGreen.png");
		if(health == 2)setImage("Images/trafficLightYellow.png");
		if(health == 1)setImage("Images/trafficLightRed.png");
		if(health == 0)gameOver();


		if(Greenfoot.isKeyDown("left")){
			turn(-5);
		}
		else if(Greenfoot.isKeyDown("right")){
			turn(5);
		}
		else if(Greenfoot.isKeyDown("down")){
			weapon = true;
			}
		else if(Greenfoot.isKeyDown("up")){
			weapon = false;
			}
		
		if(Greenfoot.isKeyDown("space") && weapon){
			if(count <= 0){
				getWorld().addObject(new Blade(this.getRotation()), getX(), getY());
				count += 15;
			}
			
		}
		if(Greenfoot.isKeyDown("space") && !weapon){
			if(count <= 0){
				getWorld().addObject(new Explosive(this.getRotation()), getX(), getY());
				count += 20;
			}
			
		}
	}
}
