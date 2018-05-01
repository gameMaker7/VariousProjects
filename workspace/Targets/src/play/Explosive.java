package play;

import java.util.ArrayList;

import Interfaces.Explode;
import Interfaces.Pierce;
import greenfoot.Actor;

public class Explosive extends Actor {
	int offset = 5;
	int size = Game.SIZE - offset;
	private int speed = 3;
	
	public Explosive(int rotate){
		setImage("Images/Fire.png");
		setRotation(rotate);

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
			if(this.getOneIntersectingObject(Explode.class) != null){
				ArrayList<Explode> explode = new ArrayList<Explode>();
				explode.add((Explode) this.getOneIntersectingObject(Explode.class));
				for(Explode i: explode){
					i.explode();
				}
				this.getWorld().removeObject(this);
				return;
			}
			atEdge();
		}
	}
}
