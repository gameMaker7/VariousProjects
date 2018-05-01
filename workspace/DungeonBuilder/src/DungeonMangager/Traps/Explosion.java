package DungeonMangager.Traps;

import java.awt.Color;

import DungeonMangager.Dungeon;
import Monsters.Goblin;
import Monsters.Rats;
import Monsters.Wraith;
import greenfoot.Actor;

public class Explosion extends Actor {
	int ctimer = 0;
	int maxsize = 200;
	boolean exp = false;

	public void act(){
		if(ctimer < maxsize/* && this.getObjectsInRange(maxsize, Goblin.class) != null || this.getObjectsInRange(maxsize, Wraith.class) != null || this.getObjectsInRange(maxsize, Rats.class) != null && ctimer < maxsize*/)
		{
			this.getImage().clear();
			this.getImage().setColor(Color.GREEN);
			this.getImage().scale(ctimer+1,ctimer+1);
			this.getImage().drawOval(this.getImage().getWidth()/2 - ctimer/2, this.getImage().getHeight()/2 - ctimer/2, ctimer, ctimer);

			ctimer++;
			for(Object obj: getObjectsInRange(this.getImage().getHeight(), Goblin.class))
			{
				Goblin gob = (Goblin) obj;
				gob.health = 0;
			}
			for(Object obj: getObjectsInRange(this.getImage().getHeight(), Wraith.class))
			{
				Wraith wraith = (Wraith) obj;
				wraith.health = 0;
			}
			for(Object obj: getObjectsInRange(this.getImage().getHeight(), Rats.class))
			{
				Rats rat = (Rats) obj;
				rat.health = 0;
			}
		}
		else
		{
			exp = true;
			this.getWorld().removeObject(this);
			Dungeon.state = Content.NONE;
		}
	}
}
