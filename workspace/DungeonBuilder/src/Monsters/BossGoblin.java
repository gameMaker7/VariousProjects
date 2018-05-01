package Monsters;

import DungeonMangager.Dungeon;

public class BossGoblin extends Monsters{

	public BossGoblin(){
		this.setImage("Images/Goblin Boss.png");
	}
	public int health = 10 + healthincrease;
	int speed = 1;
	int pathpoint = 0;
	public boolean poison = false;
	int delay = 60;
	int speedDelay = 1;
	int poisonCounter = 4;
	public boolean stalled = true;
	public MonsterState state = MonsterState.Moving;
	public void act(){
		if(poison){
			poisonCounter -= health;
			poisonCounter--;
		}
		if(health <=0){
			state = MonsterState.Dead;
			this.getWorld().removeObject(this);
			Dungeon.kd.currentkills += 30;

		}
		
		if(state != MonsterState.Dead && getOneIntersectingObject(RotationPoint.class) != null){
			if(delay <= 0){
				this.setRotation(Dungeon.waypoints[pathpoint].rotate);
				pathpoint ++;
				delay = 80;
			}
			else{
				delay--;
			}
		}
		this.movement(speed, stalled, state);
	}
}
