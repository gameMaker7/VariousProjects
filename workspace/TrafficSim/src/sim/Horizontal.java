package sim;

public class Horizontal extends Road {

	int width = TrafficWorld.WORLDWIDTH;
	int length = 50;

	public Horizontal(){
		this.getImage().scale(width, length);
		this.getImage().clear();
	}
	public void spawnCars(int space,int x, int y, int rotate, int road, int a){
//		x = offset; 
//		y = road + offset;
//		Car.createCar(x, y, getWorld(), Direction.EAST, 0);
//		y += 10;
//		x = space - width;
//		a ++;
//		Car.createCar(x, y, getWorld(), Direction.WEST, 180);
	}
}
