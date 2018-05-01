package DungeonMangager.Traps;


import java.awt.Color;

import DungeonMangager.Dungeon;
import Monsters.BossGoblin;
import Monsters.Goblin;
import Monsters.Rats;
import Monsters.Wraith;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class Light extends Actor {
	int timer = 0;
	int size = 50;
	int csize = 0;
	int damage = 7;
	boolean dead = false;

	public Light(){
		this.setImage("Images/Clear.png");
	}
	public void act(){
		if(!dead)
			this.getImage().clear();
			this.getImage().scale(csize + 1, csize + 1);
			if(timer%100 == 0)
			{
				this.getImage().setColor(Color.BLUE);
				this.getImage().drawOval(this.getImage().getWidth()/2 - csize/2, this.getImage().getHeight()/2 - csize/2, csize, csize);
				csize++;
				if(csize == size)
				{
					csize = 0;
					timer++;
				}
				for(Object obj: getIntersectingObjects(Goblin.class))
				{
					Goblin gob = (Goblin) obj;
					gob.health -= damage;
					gob.stalled = true;
				}
				for(Object obj: getObjectsInRange(this.getImage().getHeight()/2, Wraith.class))
				{
					Wraith wraith = (Wraith) obj;
					wraith.health -= damage;
					wraith.stalled = true;
				}
				for(Object obj: getObjectsInRange(this.getImage().getHeight()/2, Rats.class))
				{
					Rats rat = (Rats) obj;
					rat.health -= damage;
					rat.stalled = true;

				}

							for(Object obj: getObjectsInRange(this.getImage().getHeight()/2, BossGoblin.class))
							{
								BossGoblin gob = (BossGoblin) obj;
								gob.health -= damage;
								gob.stalled = true;
							}
				//			for(Object obj: getObjectsInRange(this.getImage().getHeight()/2, BossRat.class))
				//			{
				//				BossRat rat = (BossRat) obj;
				//				rat.health -= damage;
				//			}
				//			
			}
			else
			{
				timer++;
			}
			{
				if(Greenfoot.mouseClicked(this)&& Dungeon.state == Content.SELL){
					this.getWorld().removeObject(this);
					dead = true;
					Dungeon.gd.addMoney(Dungeon.lightcost);
				}
		}
	}
}
