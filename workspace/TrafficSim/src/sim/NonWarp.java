package sim;



public abstract class NonWarp extends Car implements TrafficControl{	
	protected int maxSpeed = 2;
	private int offset = 5;
	protected int length = TrafficWorld.WORLDWIDTH - offset;
	protected int width = TrafficWorld.WORLDHEIGHT - offset;


	public NonWarp(Direction direction, int i){
		this.state = direction;
		this.setRotation(i);
	}

	@Override
	protected void move() {	
		move(getSpeed());		
		if(getX() < offset){
			getWorld().removeObject(this);
			return;
		}
		if(getX() > length){
			getWorld().removeObject(this);
			return;
		}
		if(getY() < offset){
			getWorld().removeObject(this);
			return;
		}
		if(getY() > width){
			getWorld().removeObject(this);
			return;
		}
		if(getSpeed() < maxSpeed  && getSpeed() != 0){
			setSpeed(getSpeed() + 1);
		}
	}	
}
