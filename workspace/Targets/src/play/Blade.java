package play;

import java.util.ArrayList;

import Interfaces.Crash;
import Interfaces.Explode;
import Interfaces.Pierce;
import greenfoot.Actor;

public class Blade extends Actor {

	int offset = 5;
	int size = Game.SIZE - offset;
	private int speed = 3;

	public Blade(int rotate){
		setImage("Images/Cursed Sword.png");
		setRotation(rotate );

	}
	public void atEdge(){
		if(getX() < offset){
			getWorld().removeObject(this);
			return;
		}
		if(getX() > size){
			getWorld().removeObject(this);
			return;
		}
		if(getY() < offset){
			getWorld().removeObject(this);
			return;
		}
		if(getY() > size){
			getWorld().removeObject(this);
			return;
		}
	}
	public void act(){
		if(getWorld() != null){
			move(speed );
			if(this.getOneIntersectingObject(Pierce.class) != null){
				Actor x = getOneIntersectingObject(Pierce.class);
					((Pierce) x).pierce();
				this.getWorld().removeObject(this);
				return;
				}
			}
			atEdge();
		}
	}

