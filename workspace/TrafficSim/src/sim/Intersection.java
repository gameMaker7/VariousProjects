package sim;

import java.util.ArrayList;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Intersection extends Actor {

	final static int SIZE = 50;
	final static int RADIUS = 75;
	final static int RADIUS2 = 45;

	ArrayList<TrafficControl> close = new ArrayList<TrafficControl>();
	ArrayList<TrafficControl> in = new ArrayList<TrafficControl>();
	ArrayList<TrafficControl> approaching = new ArrayList<TrafficControl>();
	GreenfootImage image = new GreenfootImage(SIZE, SIZE);
	Light hState = Light.GREEN;
	Light vState = Light.RED;
	private int count = 0;
	private int change = 100;
	private int change2 = 200;
	private int total = 300;
	private Lights up;
	private Lights down;
	private Lights right;
	private Lights left;
	private int wait = 400;
	private static int car = 0;
	private int cars = 0;
	private int cars2 = 0;
	public boolean des = false;
	private int i;
	public int getI() {
		return i;
	}
	public int getA() {
		return a;
	}

	private int a;


	public  Intersection(){
		this.setImage(image);
	}
	public void act(){
		lightControl();
		commands();
		carCounter();
	}
	private void carCounter() {
		if(cars != cars2){
			getImage().clear();
			getImage().drawString("" + cars, 0, 0);
			cars2 = cars;
		}
	}
	public void turnCars(Car car, Direction turn){
		if(turn == car.state){
			return;
		}
		switch(turn){
		case NORTH: 
			car.setRotation(90);
			switch(car.state){
			case EAST:
				car.state = Direction.NORTH;
				car.setLocation(getX() + 10, getY() - car.H);
				break;
			case WEST:
				car.state = Direction.NORTH;
				car.setLocation(getX() + 10, getY() - car.H);
				break;
			default:
				break;
			}
			break;
		case EAST:
			car.setRotation(180);
			switch(car.state){
			case SOUTH:
				car.state = Direction.EAST;
				car.setLocation(getX() + car.W, getY() + 15);
				break;
			case NORTH:
				car.state = Direction.EAST;
				car.setLocation(getX() + car.W, getY() + 15);
				break;
			default:
				break;
			}
			break;
		case WEST:
			car.setRotation(0);
			switch(car.state){
			case NORTH:
				car.state = Direction.WEST;
				car.setLocation(getX() - car.W, getY() - 15);
				break;
			case SOUTH:
				car.state = Direction.WEST;
				car.setLocation(getX() - car.W, getY() - 15);
				break;
			default:
				break;
			}
			break;
		case SOUTH:
			car.setRotation(270);
			switch(car.state){
			case EAST: 
				car.state = Direction.SOUTH;
				car.setLocation(getX() - 10, getY() + car.H);
				break;
			case WEST:
				car.state = Direction.SOUTH;
				car.setLocation(getX() - 10, getY() + car.H);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}

	private void lightControl() {
		if( count == 0){
			hState = Light.GREEN;
			left.setImage("Images/trafficLightGreen.png");
			right.setImage("Images/trafficLightGreen.png");

			vState = Light.RED;
			down.setImage("Images/trafficLightRed.png");
			up.setImage("Images/trafficLightRed.png");
		}
		count ++;
		if(count == change){
			hState = Light.YELLOW;
			left.setImage("Images/trafficLightYellow.png");
			right.setImage("Images/trafficLightYellow.png");

			vState = Light.RED;
			up.setImage("Images/trafficLightRed.png");
			down.setImage("Images/trafficLightRed.png");

		}
		else if(count == change2){
			hState = Light.RED;
			left.setImage("Images/trafficLightRed.png");
			right.setImage("Images/trafficLightRed.png");

			vState = Light.GREEN;
			down.setImage("Images/trafficLightGreen.png");
			up.setImage("Images/trafficLightGreen.png");
		}

		else if(count == total){
			hState = Light.RED;
			left.setImage("Images/trafficLightRed.png");
			right.setImage("Images/trafficLightRed.png");

			vState = Light.YELLOW;
			down.setImage("Images/trafficLightYellow.png");
			up.setImage("Images/trafficLightYellow.png");

		}

		else if(count == wait){
			count = 0;
		}
	}

	private void commands(){
		ArrayList<TrafficControl> a1 = (ArrayList<TrafficControl>) this.getObjectsInRange(RADIUS, TrafficControl.class);
		ArrayList<TrafficControl> a2 = (ArrayList<TrafficControl>) this.getObjectsInRange(RADIUS2, TrafficControl.class);

		ArrayList<TrafficControl> a3 = (ArrayList<TrafficControl>) close.clone();
		for(TrafficControl a: a3){
			if(!a1.contains(a)){
				a.leaving();
				in.remove(a);
				close.remove(a);
			}
		}
		for(TrafficControl a: a1){
			if(!approaching.contains(a) && !in.contains(a)){
				close.add(a);
				approaching.add(a);
				a.approaching(this);
			}

		}
		for(TrafficControl a: a2){
			if(approaching.contains(a) && !in.contains(a)){
				in.add(a);
				approaching.remove(a);
				a.enter(this);	
				car++;
				setCars(getCars() + 1);
			}
		}

	}

	public static int getCar() {
		return car;
	}
	public void lights(){
		up = new Lights();
		getWorld().addObject(up, getX(),getY() - SIZE/2);
		up.turn(180);
		left = new Lights();
		getWorld().addObject(left, getX() - SIZE/2 , getY());
		left.turn(90);
		down = new Lights();
		getWorld().addObject(down, getX(), getY() + SIZE/2);
		right = new Lights();
		getWorld().addObject(right, getX() + SIZE/2, getY());
		right.turn(-90);

	}
	public int getCars() {
		return cars;
	}
	private void setCars(int cars) {
		this.cars = cars;
	}
	public void setCord(int i, int a) {
		this.i = i;//vertical
		this.a = a;//horizontal
	}
	private double getDistance(Intersection dest) {
		return Math.sqrt(  ((this.getX() - dest.getX())*(this.getX() - dest.getX())) + ((this.getY() - dest.getY())*(this.getY() - dest.getY())) );		
	}
	public double north(Intersection dest) {
		double out = -1;

		if(this.getI() == 0){
			out = Double.MAX_VALUE;
		}else{
			out = TrafficWorld.intersection[i-1][a].getDistance(dest);
		}
		//		double x = getNorth(this).getDistance(dest);
		return out;
	}

	public double south(Intersection dest) {
		double out = -1;

		if(this.getI() == TrafficWorld.HROADS - 1){
			out = Double.MAX_VALUE;
		}else{
			out = TrafficWorld.intersection[i+1][a].getDistance(dest);
		}
		return out;
	}

	public double west(Intersection dest) {
		double out;

		if(this.getA() == 0){
			out = Double.MAX_VALUE;
		}else{
			out = TrafficWorld.intersection[i][a-1].getDistance(dest);
		}
		return out;
	}

	public double east(Intersection dest) {
		double out = -1;

		if(this.getA() == TrafficWorld.VROADS - 1){
			out = Double.MAX_VALUE;
		}else{
			out = TrafficWorld.intersection[i][a+1].getDistance(dest);
		}
		return out;
	}	
}
