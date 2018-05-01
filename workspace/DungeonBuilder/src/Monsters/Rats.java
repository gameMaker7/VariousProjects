package Monsters;

import DungeonMangager.Dungeon;

public class Rats extends Monsters{

	public Rats(){
		this.setImage("Images/Rat.png");
	}
	public int health = 5 + healthincrease;
	int speed = 3;
	public boolean stalled = true;
	int pathpoint = 0;
	int delay = 5;
	int poisonCounter = 4;
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
			Dungeon.kd.kills += 3;

		}
		if(state != MonsterState.Dead && getOneIntersectingObject(RotationPoint.class) != null){
			if(delay <= 0){
				this.setRotation(Dungeon.waypoints[pathpoint].rotate);
				pathpoint ++;
				delay = 8;
			}
			else{
				delay--;
			}
		}
		movement(speed, stalled, state);

	}
}


