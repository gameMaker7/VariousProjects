package Monsters;

import greenfoot.Greenfoot;
import DungeonMangager.Dungeon;

public class Goblin extends Monsters{

	public Goblin(){
		this.setImage("Images/Goblin.png");
	}
	public int health = 7 + healthincrease;
	int speed = 1;
	public boolean flamed = false;
	public boolean stalled = false;
	int pathpoint = 0;
	int delay = 15;
	public MonsterState state = MonsterState.Moving;
	public boolean poison;
	int poisonCounter = 4;
	int X = 0;
	int Y = 0; 
	public void act(){

		X = Greenfoot.getMouseInfo().getX();
		Y = Greenfoot.getMouseInfo().getY();
		setLocation(X,Y);

		if(poison){
			poisonCounter -= health;
			poisonCounter--;
		}
		if(health <=0){
			state = MonsterState.Dead;
			this.getWorld().removeObject(this);
			Dungeon.kd.kills += 2;

		}

		if(state != MonsterState.Dead && getOneIntersectingObject(RotationPoint.class) != null){
			if(delay <= 0){
				this.setRotation(Dungeon.waypoints[pathpoint].rotate);
				pathpoint ++;
				delay = 50;
			}
			else{
				delay--;
			}
		}
		//this.movement(speed, stalled, state);}
	}
}

