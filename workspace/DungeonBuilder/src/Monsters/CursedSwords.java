package Monsters;

import DungeonMangager.Dungeon;

public class CursedSwords extends Monsters{

	public CursedSwords(){
		this.setImage("Images/Cursed Sword.png");
	}
	public int health = 5 + healthincrease;
	int speed = 2;
	boolean stalled = true;
	int pathpoint = 0;
	int delay = 5;
	public MonsterState state = MonsterState.Moving;
	public boolean poison = false;
	public void act(){
		if(health <=0){
			state = MonsterState.Dead;
			this.getWorld().removeObject(this);
			Dungeon.kd.kills += 15;

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
this.movement(speed, stalled, state);
	}
}


