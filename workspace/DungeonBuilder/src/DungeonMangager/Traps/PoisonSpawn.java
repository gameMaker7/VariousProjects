package DungeonMangager.Traps;

import DungeonMangager.Dungeon;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class PoisonSpawn extends Actor {

	public PoisonSpawn(){
		this.setImage("Images/Poison Button.png");
	}
	public void act(){
		if(Greenfoot.mouseClicked(this))
		{
			Dungeon.state = Content.POISON;
		}
	}

}
