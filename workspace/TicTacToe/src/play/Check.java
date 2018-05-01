package play;

import greenfoot.Actor;
import greenfoot.Greenfoot;

public class Check extends Actor {

	State state = State.active;
	Phenoix phenoix = new Phenoix();
	Dragon dragon = new Dragon();
	int size = 75;

	public Check (){
		this.getImage().scale(size,size);
		this.getImage().clear();

	}
	public void act(){
		if(state != State.dead){
			if(Greenfoot.mouseClicked(this) && Board.player1){
				getWorld().addObject(phenoix, this.getX(), this.getY());
				Board.player1 = false;
				Board.player2 = true;
				state = State.dead;
				getWorld().removeObject(this);

			}
			else if(Greenfoot.mouseClicked(this) && Board.player2){
				getWorld().addObject(dragon, this.getX(), this.getY());
				Board.player2 = false;
				Board.player1 = true;
				state = State.dead;
				getWorld().removeObject(this);

			}
		}

	}

}
