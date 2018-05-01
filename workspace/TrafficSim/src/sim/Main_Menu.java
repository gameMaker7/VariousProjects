package sim;



import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;

import greenfoot.Greenfoot;
import greenfoot.World;

public class Main_Menu extends World {

	String key = "";
	final static int WIDTH = TrafficWorld.WORLDWIDTH;;
	final static int HIEGHT = TrafficWorld.WORLDHEIGHT;
	final static int CELL = 1;
	int redCrashes = RedCar.getCrashes();
	int blueCrashes = BlueCar.getCrashes();
	int yelllowCrashes = YellowCar.getCrashes();
	int purpleCrashes = PurpleCar.getCrashes();
	int[] rturns = RedCar.getTurns();
	int[] bturns = BlueCar.getTurns();
	int[] yturns = YellowCar.getTurns();
	int[] pturns = PurpleCar.getTurns();
	int blocksTraveledRed = RedCar.getBlocks();
	int blocksTraveledBlue = BlueCar.getBlocks();
	int blocksTraveledPurple = PurpleCar.getBlocks();
	int blocksTraveledYellow = YellowCar.getBlocks();
	int seconds = 5000/Intersection.getCar();
	private final static int NOFINTERSECTIONS = TrafficWorld.NOFINTERSECTIONS;
	int cars = Intersection.getCar()/NOFINTERSECTIONS;
	TrafficWorld world;
	private Intersection[] intersections;

	final static int POSITION1 = HIEGHT/6;
	final static int POSITION2 = POSITION1 + POSITION1;
	final static int POSITION3 = POSITION2 + POSITION1;
	final static int POSITION4 = POSITION2 + POSITION2;
	final static int POSITION5 = POSITION4 + POSITION1;
	public Main_Menu(TrafficWorld traffic, Intersection[] intersection){
		super(WIDTH,HIEGHT,CELL);
		world = traffic;
		this.intersections = intersection;
		options();

	}
	public void intersectionStats(){
		try {
			FileWriter stats = new FileWriter("Stats.txt");
			for(int i = 0; i < intersections.length; i ++){
				stats.write("Intersection " + (i+1) + ": # of Cars " + intersections[i].getCars() + "\n");
			}
			stats.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void options(){
		String time = "Average Time Between Car Arrivals: " + seconds;
		String car = "Average cars per Intersection: " + cars;
		String blocks = "Blocks Traveled. Red: " + blocksTraveledRed + " Yellow: " + blocksTraveledYellow + " Blue: " + blocksTraveledBlue + " Purple: " + blocksTraveledPurple; 
		String crashes = "Crashes by Car: Red: " + redCrashes + " Yellow: " + yelllowCrashes + " Blue: " + blueCrashes + " Purple: " + purpleCrashes;
		String turns = "Average # of Turns Left / Right:  Red: " + rturns[0] + " / " + rturns[1] + " Yellow: " + yturns[0] + " / " + yturns[1] + " Blue: " + bturns[0] + " / " + bturns[1] + " Purple: " + pturns[0] + " / " + pturns[1];
		this.getBackground().setColor(Color.BLACK);
		this.getBackground().fill();
		this.getBackground().setColor(Color.WHITE);
		this.getBackground().setColor(Color.RED);
		this.getBackground().drawString(blocks, WIDTH/3, POSITION1);
		this.getBackground().drawString(crashes, WIDTH/3, POSITION2);
		this.getBackground().drawString(turns, WIDTH/3,POSITION3);
		this.getBackground().drawString(time, WIDTH/3,POSITION4);
		this.getBackground().drawString(car, WIDTH/3,POSITION5);



	}
	public void act(){
		key = Greenfoot.getKey();
		if(key != null && key.equals("space")){
			intersectionStats();
			System.exit(0);
		}
	}

}
