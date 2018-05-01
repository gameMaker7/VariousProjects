
package DungeonMangager.Traps;

import DungeonMangager.Dungeon;
import Monsters.BossGoblin;
import Monsters.CursedSwords;
import Monsters.Goblin;
import Monsters.Rats;
import Monsters.Wraith;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class Spike extends Actor {
	boolean cooldown = false;
	int timer = 25;
	int cd = 0;
	int damage = 7;
	boolean dead = false;

	public Spike(){
		this.setImage("Images/Spike.png");
	}
	public void act(){
		if(!dead){
			
			if(!cooldown)
			{
				for(Object obj: getIntersectingObjects(Goblin.class))
				{
					Goblin gob = (Goblin) obj;
					gob.health -= damage;
					cooldown = true;
				}
				//				for(Object obj: getIntersectingObjects(Wraith.class))
				//				{
				//					Wraith wraith = (Wraith) obj;
				//					wraith.health -= damage;
				//					cooldown = true;
				//				}
				for(Object obj: getIntersectingObjects(Rats.class))
				{
					Rats rat = (Rats) obj;
					rat.health -= damage;
					cooldown = true;
				}
				for(Object obj: getIntersectingObjects( BossGoblin.class))
				{
					BossGoblin gob = (BossGoblin) obj;
					gob.health -= damage;
					cooldown = true;
				}
				for(Object obj: getIntersectingObjects( CursedSwords.class))
				{
					CursedSwords wraith = (CursedSwords) obj;
					wraith.health -= damage;
					cooldown = true;
				}
				//			for(Object obj: getObjectsInRange(0, BossRat.class))
				//			{
				//				BossRat rat = (BossRat) obj;
				//				rat.health -= damage;
				//				cooldown = true;
				//			}
			}
			else
			{
				cd++;
				if(cd == timer)
				{
					cooldown = false;
					cd = 0;
				}
			}
			if(Greenfoot.mouseClicked(this)&& Dungeon.state == Content.SELL){
				dead = true;
				this.getWorld().removeObject(this);
				Dungeon.gd.addMoney(Dungeon.spikecost);
			}
		}
	}
}