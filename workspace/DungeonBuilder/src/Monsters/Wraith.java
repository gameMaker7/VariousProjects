package Monsters;

import DungeonMangager.Dungeon;

public class Wraith extends Monsters{

	
	public Wraith(){
		this.setImage("Images/Wraith.png");
		this.getImage().scale(20, 20);
	}
	public int health = 5 + healthincrease;
	int speed = 2;
	int poisonCounter = 4;
	public boolean stalled = true;
	int pathpoint = 0;
	int delay = 5;
	public MonsterState state = MonsterState.Moving;
	public boolean poison;
	public void act(){
		if(poison){
			poisonCounter -= health;
			poisonCounter--;
		}
		if(health <=0){
			state = MonsterState.Dead;
			this.getWorld().removeObject(this);
			Dungeon.kd.currentkills += 5;

		}
		
		if(state != MonsterState.Dead && getOneIntersectingObject(RotationPoint.class) != null){
			if(delay <= 0){
				this.setRotation(Dungeon.waypoints[pathpoint].rotate);
				pathpoint ++;
				delay = 20;
			}
			else{
				delay--;
			}
		}
		else{
		}
		movement(speed, stalled, state);
	}
}
