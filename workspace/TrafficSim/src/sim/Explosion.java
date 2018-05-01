package sim;

import greenfoot.Actor;

public class Explosion extends Actor {
		private int timer = 50;

	public Explosion(){
		setImage("Images/explosion1.png");
	}
	public void act(){
		if(timer == 0){
			getWorld().removeObject(this);
		}
		else{
			timer --;
		}
	}
}
