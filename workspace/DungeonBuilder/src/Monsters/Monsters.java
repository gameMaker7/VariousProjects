package Monsters;

import DungeonMangager.Core;
import greenfoot.Actor;

public class Monsters extends Actor{

	public int pathpoint = 1;
	public static int healthincrease = 0;

	public void movement(int speed, boolean stalled, MonsterState state){
		switch(state){
		case Moving:
			move(speed);
			if(getOneIntersectingObject(Core.class) != null){
				Core.health--;
				this.getWorld().removeObject(this);
			}
			break;
		case Stalled:

			if(!stalled){
				move(speed);			
			}
			stalled = false;
			break;
		case Dead:
			break;
		}
	}
}

