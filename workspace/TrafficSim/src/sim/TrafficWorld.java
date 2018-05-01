package sim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import greenfoot.Greenfoot;
import greenfoot.World;

public class TrafficWorld extends World{

	final static int WORLDWIDTH = 1000;
	final static int WORLDHEIGHT = 750;
	private final static int CELL = 1;
	private static int counter;
	private final static int ROADWIDTH = 50;
	private final static int DISTANCE = 25;
	static int HROADS,  VROADS;
	private static int HOFFSET , VOFFSET;
	public static int NOFINTERSECTIONS; 
	int YroadStart = 0;
	int XroadStart = 0;
	private static int nCars;
	static Random gen = new Random();
	Horizontal[] hroad;
	Vertical[] vroad;
	static Car[] car;
	private int a = 1;
	private int Xposition = 0;
	int Yposition = 0;
	static Intersection[][] intersection;
	private int carSpawn = counter;
	private int nroads;
	private Intersection[] intersectionstats;
	public static Intersection destination;

	public TrafficWorld(){
		super(WORLDWIDTH, WORLDHEIGHT, CELL);
		Controls a = new Controls();
	}
	public TrafficWorld(String a){
		super(WORLDWIDTH, WORLDHEIGHT, CELL);
		HROADS = TrafficInfo.getROADS();
		VROADS = TrafficInfo.getROADS();
		counter = TrafficInfo.getCounter();
		HOFFSET = (WORLDWIDTH - ROADWIDTH) / (VROADS - 1); 
		VOFFSET = (WORLDHEIGHT - ROADWIDTH) / (HROADS - 1);
		NOFINTERSECTIONS = VROADS * HROADS;
		nCars = (HROADS + VROADS) * 2;
		hroad = new Horizontal[HROADS];
		vroad = new Vertical[VROADS];
		car = new Car[nCars];
		nroads = VROADS + HROADS;
		intersection = new Intersection[HROADS][VROADS];
		background();
		spawnRoads();
		spawnIntersect();
	}

	public void spawnRoads(){
		int rotate = 0;
		this.getBackground().setColor(Color.gray);
		for(int i = 0; i <HROADS; i ++){
			rotate = 0;
			hroad[i] = new Horizontal();
			addObject(hroad[i],XroadStart ,YroadStart);
			hroad[i].draw(XroadStart, YroadStart, hroad[i].width, hroad[i].length);
			rotate = 180;
			hroad[i].spawnCars(WORLDWIDTH, Xposition, Yposition, rotate, YroadStart, a);
			YroadStart += VOFFSET;
			a ++;
		}
		YroadStart = 0;
		for(int i = 0; i<VROADS; i ++){
			rotate = 270;
			vroad[i] = new Vertical();
			addObject(vroad[i],XroadStart ,YroadStart);
			vroad[i].draw(XroadStart, YroadStart, vroad[i].width, vroad[i].length);
			vroad[i].spawnCars(WORLDHEIGHT, Yposition, Xposition, rotate, XroadStart, a);
			XroadStart += HOFFSET;
			a ++;
		}

	}
	public void spawnIntersect(){
		int i = 0;
		int a = 0;
		int x = 0;
		intersectionstats = new Intersection[NOFINTERSECTIONS];
		for(i = 0; i<VROADS ; i ++){
			for( a = 0; a < HROADS ; a ++){
				intersection[i][a] = new Intersection();
				this.addObject(intersection[i][a],vroad[i].getX() + DISTANCE, hroad[a].getY() + DISTANCE);
				intersection[i][a].lights();
				intersectionstats[x] = intersection[i][a];
				x ++;
				intersection[i][a].setCord(i, a);
			}
		}
		selectDes();
	}

	private void selectDes() {
//		int x = gen.nextInt(NOFINTERSECTIONS);
//		intersection[x].des = true;
//		destination = intersection[Greenfoot.getRandomNumber(HROADS)][Greenfoot.getRandomNumber(VROADS)];
		destination = intersection[0][VROADS/2];
		System.out.println(destination.getI()+"," + destination.getA());
		
		for(SmartCar c: ((ArrayList<SmartCar>) this.getObjects(SmartCar.class))){
			if(c.getGo() == null){
				c.startGo();
			}
			
			c.desSearch(c.getGo(), destination);
//			for(Direction i: c.desDir){
//				System.out.println(i.toString());
//			}
//			System.out.println("");
		}
		
		
	}
	public void background(){
		this.getBackground().setColor(Color.GREEN);
		this.getBackground().fill();


	}
	public void act(){
		if(counter == 0){
			Greenfoot.setWorld(new Main_Menu(this, intersectionstats));
		}
		counter--;
	}
	
	class Controls extends JFrame implements ActionListener{

		private FileOutputStream save;
		private ObjectOutputStream output;

		private FileInputStream loader;
		private ObjectInputStream input;

		private JTextField hroads;
		private JTextField timer;
		private JTextField rcars;
		private JTextField pcars;
		private JTextField ycars;
		private JTextField bcars;

		private JButton start;
		private JButton load;
		private TrafficInfo config;

		private JLabel hort = new JLabel("Number of Roads horizontal and vertical");
		private JLabel count = new JLabel("World TimeSpan");
		private JLabel red = new JLabel("Red Car Max Speed");
		private JLabel blue = new JLabel("Blue Car Max Speed");
		private JLabel yellow = new JLabel("Yellow Car Max Speed");
		private JLabel purple = new JLabel("Purple Car Max Speed");

		int width = 250;
		int hieght = 400;
		private int horts;
		private int verts;
		private int counters;
		private int rcar;
		private int bcar;
		private int ycar;
		private int pcar;

		public Controls(){
			super("TrafficSim Menu");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setAlwaysOnTop(true);
			Dimension d = new Dimension(width,hieght);
			this.setPreferredSize(d);

			JPanel cpanel = new JPanel();


			start = new JButton("Start");
			start.addActionListener(this);
			cpanel.add(start);
			load = new JButton("Load");
			load.addActionListener(this);
			cpanel.add(load);

			cpanel.add(hort);
			hroads = new JTextField("", 5);
			cpanel.add(hroads);
			cpanel.add(count);
			timer = new JTextField("", 5);
			cpanel.add(timer);
			cpanel.add(red);
			rcars = new JTextField("", 5);
			cpanel.add(rcars);
			cpanel.add(blue);
			bcars = new JTextField("", 5);
			cpanel.add(bcars);
			cpanel.add(yellow);
			ycars = new JTextField("", 5);
			cpanel.add(ycars);
			cpanel.add(purple);
			pcars = new JTextField("", 5);
			cpanel.add(pcars);
			this.add(cpanel);

			this.pack();
			this.setLocation((1920/2)-(this.getWidth()/2), (1080/2)-(this.getHeight()/2));
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == load){

				try{
					loader = new FileInputStream("TrafficConfig.txt");
					input = new ObjectInputStream(loader);
					config = (TrafficInfo)input.readObject();
					output.close();



				} catch (FileNotFoundException e1) {
				} catch (IOException e2) {
				} catch (ClassNotFoundException e3) {
				}
				Greenfoot.setWorld(new TrafficWorld(""));
				Greenfoot.start();
				this.dispose();
			}
			if(e.getSource() == start){
				try{
					horts = Integer.parseInt(hroads.getText());
					counters = Integer.parseInt(timer.getText());

					rcar = Integer.parseInt(rcars.getText());
					bcar = Integer.parseInt(bcars.getText());
					ycar = Integer.parseInt(ycars.getText());
					pcar = Integer.parseInt(pcars.getText());

				}catch(NumberFormatException eq){
					System.exit(0);
				}
				config = new TrafficInfo(rcar, bcar, ycar, pcar, horts, counters);


				try{
					save = new FileOutputStream("TrafficConfig.txt");	
					output = new ObjectOutputStream(save);
					output.writeObject(config);
					output.close();

				}
				catch(IOException ex){
				}

				Greenfoot.setWorld(new TrafficWorld(""));
				Greenfoot.start();
				this.dispose();
			}
		}
	}
}                