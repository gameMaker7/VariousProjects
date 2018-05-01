package DungeonMangager.Traps;

import DungeonMangager.Dungeon;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class FlameSpawn extends Actor {

	public FlameSpawn(){
		this.setImage("Images/Fire Button.png");
	}
	public void act(){
		if(Greenfoot.mouseClicked(this))
		{
			Dungeon.state = Content.FLAME;
		}
	}

}
