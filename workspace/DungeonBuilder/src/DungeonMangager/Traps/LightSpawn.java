package DungeonMangager.Traps;
import DungeonMangager.Dungeon;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class LightSpawn extends Actor {

	public LightSpawn(){
		this.setImage("Images/Thunder Button.png");
	}
	public void act(){
		if(Greenfoot.mouseClicked(this))
		{
			Dungeon.state = Content.LIGHTNING;
		}
	}

}
