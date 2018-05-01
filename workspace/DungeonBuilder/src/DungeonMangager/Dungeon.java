package DungeonMangager;


import java.awt.Color;
import java.util.Random;

import DungeonMangager.Traps.BladeSpawn;
import DungeonMangager.Traps.Blades;
import DungeonMangager.Traps.Content;
import DungeonMangager.Traps.Flame;
import DungeonMangager.Traps.FlameSpawn;
import DungeonMangager.Traps.Light;
import DungeonMangager.Traps.LightSpawn;
import DungeonMangager.Traps.MagicSpawn;
import DungeonMangager.Traps.MagicTower;
import DungeonMangager.Traps.Poison;
import DungeonMangager.Traps.PoisonSpawn;
import DungeonMangager.Traps.QuitButton;
import DungeonMangager.Traps.SellButton;
import DungeonMangager.Traps.SpawnSpike;
import DungeonMangager.Traps.Spike;
import Monsters.MonsterSpawner;
import Monsters.Monsters;
import Monsters.RotationPoint;
import Monsters.Wave;
import greenfoot.Greenfoot;
import greenfoot.World;


public class Dungeon extends World {


	final static int WIDTH = 1500;
	final static int HWIDTH = 750;

	final static int HIEGHT = 1000;
	final static int HHIEGHT = 500;

	final static int CELL = 1;

	Random gen = new Random();
	public static int waveCount = 0;
	public static Content state = Content.NONE;
	SpawnSpike ss = new SpawnSpike();
	public static int spikecost = 5;
	MagicSpawn ms = new MagicSpawn();
	public static int magiccost = 50;
	LightSpawn ls = new LightSpawn();
	public static int lightcost = 15;
	FlameSpawn fs = new FlameSpawn();
	public static int flamecost = 20;
	BladeSpawn bs = new BladeSpawn();
	public static int bladecost = 10;
	PoisonSpawn ps = new PoisonSpawn();
	public static int poisoncost = 25;
	public static GoldDisplay gd = new GoldDisplay();
	QuitButton qb = new QuitButton();
	SellButton sb = new SellButton();
	public static KillDisplay kd = new KillDisplay();
	public static WaveCount wb = new WaveCount();
	public static LivesDisplay ld = new LivesDisplay();
	Current cb = new Current();
	Instructions intro = new Instructions();

	static Core core = new Core();
	static Room floor = new Room();
	Wave waves = new Wave();
	boolean wave1 = true;
	int monsterN = 0;
	int delay = 30;
	static MonsterSpawner spawner = new MonsterSpawner();
	static MonsterSpawner spawner2 = new MonsterSpawner();
	MonsterSpawner spawner3 = new MonsterSpawner();
	public static RotationPoint[] waypoints = new RotationPoint[floor.wayTurn.length];

	public Dungeon(){
		super(WIDTH, HIEGHT, CELL);	
		setup();
		getBackground().setColor(Color.black);
		getBackground().fill();
		spawnCore(floor.coreX,floor.coreY);
		createPath();
		rotationPoints();
		spawnDen();
		spawnTraps();
		roomBuilder();
	}
	public void setup(){
		Monsters.healthincrease = 0;
		Core.health = 50;
		kd.kills = 0;
		gd.gold = 0;
		gd.addMoney(100);
		kd.pointlimit = 250;
		spikecost = 5;
		magiccost = 50;
		lightcost = 15;
		flamecost = 20;
		bladecost = 10;
		poisoncost = 25;
		waveCount = 0;
	}
	public void spawnTraps(){
		addObject(ss,100,920);
		addObject(ms,130+ss.getX(),920);
		addObject(ls,130+ms.getX(),920);
		addObject(fs,100,960);
		addObject(bs,130+fs.getX(),960);
		addObject(ps,130+bs.getX(),960);
		addObject(sb,130+ls.getX(),920);
		kd.getImage().scale(256, 32);
		addObject(cb,130+ls.getX(),960);
		addObject(intro,0,0);
		addObject(qb,130+cb.getX(),960);
		addObject(wb,130+qb.getX(),960);
		addObject(kd,195+sb.getX(),920);
		addObject(gd,130+wb.getX(),960);
		addObject(ld,195+kd.getX(),920);

	}

	public void spawnDen(){
		addObject(spawner,floor.spawnX[0],floor.spawnY[0]);
		spawner.reversed = true;
		addObject(spawner3,floor.spawnX[1],floor.spawnY[1]);
		if(floor.chance == 1){
			addObject(spawner2,floor.spawnX[2],floor.spawnY[2]);
			spawner2.down = true;

		}
	}
	public void spawnCore(int x, int y){
		addObject(core,x, y);
	}
	public void rotationPoints(){

		for(int i = 0; i<waypoints.length; i++){
			waypoints[i].rotate = floor.wayTurn[i];
		}

	}
	public void roomBuilder(){
		getBackground().drawImage(floor.getImage(), 0, 0);
	}

	public void createPath(){
		for(int i = 0; i<waypoints.length; i ++){
			waypoints[i] = new RotationPoint();
			addObject(waypoints[i], floor.wayX[i], floor.wayY[i]);
		}
	}
	public void act(){
		Current.setCurrent(state);
		if(state != Content.NONE){
			Greenfoot.setSpeed(20);
		}
		else
		{
			Greenfoot.setSpeed(50);
		}

		if(Core.health < 0){
			Greenfoot.stop();
		}

		if(wave1 && delay == 0){
			monsterN ++;
			delay = 30;
			if(monsterN <= 10){
				wave1 = false;
				Monsters.healthincrease ++;
				Monsters[] array = new Monsters[waves.wave.length + gen.nextInt(50)];
				waves.wave = array;
			}
		}
		delay --;

		if(kd.kills >= kd.pointlimit){
			kd.pointlimit += 100;
			gd.addMoney(15);
		}

		if(Greenfoot.isKeyDown("space")){
			state = Content.PAUSE;
		}
		if(Greenfoot.mouseClicked(this) && state == Content.MAGIC){
			if(gd.canSpend(magiccost)){
				gd.spendMoney(magiccost);
				magiccost += 50;
				MagicTower obj = new MagicTower();
				addObject(obj,Greenfoot.getMouseInfo().getX(),Greenfoot.getMouseInfo().getY());
				state = Content.NONE;
			}
			else{
				state = Content.NONE;				
			}
		}
		if(Greenfoot.mouseClicked(this) && state == Content.SPIKE){
			if(gd.canSpend(spikecost)){
				gd.spendMoney(spikecost);
				spikecost += 5;
				Spike obj = new Spike();
				addObject(obj,Greenfoot.getMouseInfo().getX(),Greenfoot.getMouseInfo().getY());
				state = Content.NONE;
			}
			else{
				state = Content.NONE;				
			}
		}
		if(Greenfoot.mouseClicked(this) && state == Content.LIGHTNING){
			if(gd.canSpend(lightcost)){
				gd.spendMoney(lightcost);
				lightcost += 5;
				Light obj = new Light();
				addObject(obj,Greenfoot.getMouseInfo().getX(),Greenfoot.getMouseInfo().getY());
				state = Content.NONE;
			}
			else{
				state = Content.NONE;				
			}
		}
		if(Greenfoot.mouseClicked(this) && state == Content.FLAME){
			if(gd.canSpend(flamecost)){
				gd.spendMoney(flamecost);
				flamecost += 5;
				Flame obj = new Flame();
				addObject(obj,Greenfoot.getMouseInfo().getX(),Greenfoot.getMouseInfo().getY());
				state = Content.NONE;
			}
			else{
				state = Content.NONE;				
			}
		}
		if(Greenfoot.mouseClicked(this) && state == Content.BLADE){
			if(gd.canSpend(bladecost)){
				gd.spendMoney(bladecost);
				bladecost += 5;
				Blades obj = new Blades();
				addObject(obj,Greenfoot.getMouseInfo().getX(),Greenfoot.getMouseInfo().getY());
				state = Content.NONE;
			}
			else{
				state = Content.NONE;				
			}
		}
		if(Greenfoot.mouseClicked(this) && state == Content.POISON){
			if(gd.canSpend(poisoncost)){
				gd.spendMoney(poisoncost);
				poisoncost += 5;
				Poison obj = new Poison(Greenfoot.getMouseInfo().getX(),Greenfoot.getMouseInfo().getY());
				addObject(obj,Greenfoot.getMouseInfo().getX(),Greenfoot.getMouseInfo().getY());
				state = Content.NONE;
			}
			else{
				state = Content.NONE;				
			}
		}
	}
}
