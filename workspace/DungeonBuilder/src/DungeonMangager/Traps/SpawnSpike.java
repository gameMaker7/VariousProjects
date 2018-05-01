package DungeonMangager.Traps;

import DungeonMangager.Dungeon;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class SpawnSpike extends Actor {

	public SpawnSpike()
	{
		this.setImage("Images/Spike Button.png");
	}
	public void act(){
		if(Greenfoot.mouseClicked(this))
		{
			Dungeon.state = Content.SPIKE;
		}
	}

}