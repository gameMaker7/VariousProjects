package play;



import Interfaces.Crash;
import Interfaces.Hit;
import greenfoot.Actor;

public class PurpleCar extends Actor implements Hit, Crash{
	int speed = Game.gen.nextInt(5) + 1;
	int offset = 5;
	int size = Game.SIZE - offset;
	int chance = Game.gen.nextInt(300);
	private State state = State.moving;
	private int turn = 45;
	private int multiplier = 3;

	public PurpleCar(Spawner spawner){
		this.setImage("images/topCarPurple.png");
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
	public void crash() {
		if(state == State.crashed){
			state = State.dead;
			Game.score+= multiplier;
			getWorld().removeObject(this);
			return;
		}
		turn(Game.gen.nextInt(turn) * -1);
		move(speed);
		speed = 0;	
		state  = State.crashed;
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
