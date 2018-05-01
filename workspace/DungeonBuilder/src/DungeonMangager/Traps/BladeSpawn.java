package DungeonMangager.Traps;

import DungeonMangager.Dungeon;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class BladeSpawn extends Actor {

	public BladeSpawn(){
		this.setImage("Images/Blades Button.png");
	}
	public void act(){
		if(Greenfoot.mouseClicked(this))
		{
			Dungeon.state = Content.BLADE;
		}
	}

}
