package DungeonMangager.Traps;

import DungeonMangager.Dungeon;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class MagicSpawn extends Actor {

	public MagicSpawn(){
		this.setImage("Images/Rune Button.png");
	}
	public void act(){
		if(Greenfoot.mouseClicked(this))
		{
			Dungeon.state = Content.MAGIC;
		}
	}

}
