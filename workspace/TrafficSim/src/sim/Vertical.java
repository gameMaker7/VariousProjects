package sim;

public class Vertical extends Road{

	int width = 50;
	int length = TrafficWorld.WORLDHEIGHT;

	public Vertical(){
		this.getImage().clear();
		this.getImage().scale(width, length);

	}
	public void spawnCars(int space, int x, int y, int rotate, int road, int a){
		y = offset;
		x = road + offset;
		Car.createCar(x, y, getWorld(), Direction.SOUTH, 90);
		x += 20;
		y = length;
		a ++;
		Car.createCar(x, y, getWorld(), Direction.NORTH, 270);
	}	
	
}
