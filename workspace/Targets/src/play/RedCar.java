package play;

import Interfaces.Crash;
import Interfaces.Explode;
import Interfaces.Hit;
import Interfaces.Pierce;
import greenfoot.Actor;

public class RedCar extends Actor implements Pierce, Hit, Crash, Explode{
	private int speed = Game.gen.nextInt(5) + 1;
	private int offset = 5;
	private int size = Game.SIZE - offset;
	private int chance = Game.gen.nextInt(250);
	private State state = State.moving;
	private int turn = 45;
	private int multiplier = 3;


	public RedCar(Spawner spawner){
		this.setImage("images/topCarRed.png");
		this.setRotation(spawner.rotation);
	}


	public void act(){
		if(getWorld() != null){
			move(speed);

			if(this.getOneIntersectingObject(Light.class) != null){
				hit();
			}
			else if(this.getOneIntersectingObject(Hit.class) != null){
				crash();
			}
			
			if(state != State.dead){
				atEdge();
				if(chance == 0)turn(Game.gen.nextInt(15));
				if(chance == -100)turn(Game.gen.nextInt(15) * -1);
				chance = Game.gen.nextInt(250);
			}
		}
	}

	public void atEdge(){
		if(getX() < offset){getWorld().removeObject(this);
		return;}
		if(getX() >= size){getWorld().removeObject(this);
		return;}
		if(getY() < offset)this.setLocation(getX(), size);
		if(getY() > size)this.setLocation(getX(), offset);
	}
	@Override
	public void explode() {
		getWorld().addObject(new Explosion(), getX(), getY());
		state = State.dead;
		Game.score+= multiplier;
		getWorld().removeObject(this);
		return;
	}


	@Override
	public void crash() {
		if(state == State.crashed){
			state = State.dead;
			getWorld().removeObject(this);
			return;
		}
		turn(Game.gen.nextInt(turn) * -1);
		move(4);
		Game.score++;
		speed = 0;	
		state  = State.crashed;
	}


	@Override
	public void pierce() {
		Game.score += speed * 3;
		crash();
	}


	@Override
	public void hit() {
		if(this.getOneIntersectingObject(Light.class) != null){
			Light.health --;

			state = State.dead;
			getWorld().removeObject(this);
			return;
		}
	}
}
