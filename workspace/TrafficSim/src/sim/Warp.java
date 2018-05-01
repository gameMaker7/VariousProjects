package sim;



public abstract class Warp extends Car implements TrafficControl{	
	protected int maxSpeed = 10;
	private int offset = 5;
	protected int length = TrafficWorld.WORLDWIDTH - offset;
	protected int width = TrafficWorld.WORLDHEIGHT - offset;
	private int count = 0;


	public Warp(Direction direction, int i){
		this.state = direction;
		this.setRotation(i);
	}
	@Override
	protected void move() {
		if(getX() < offset)this.setLocation(length, getY());
		if(getX() > length)this.setLocation(offset, getY());
		if(getY() < offset)this.setLocation(getX(), width);
		if(getY() > width)this.setLocation(getX(), offset);

		if(count  == 20){
			if(getSpeed() < maxSpeed  && getSpeed() != 0){
				setSpeed(getSpeed() + 1);
				count = 0;
			}else if(getSpeed() > 1){
				setSpeed(getSpeed() - 1);
				count = 0;
			}
			count ++;
		}
		move(getSpeed());		
	}	
	
}
