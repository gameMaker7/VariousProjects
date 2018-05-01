package DungeonMangager.Traps;

import DungeonMangager.Dungeon;
import Monsters.BossGoblin;
import Monsters.CursedSwords;
import Monsters.Goblin;
import Monsters.Rats;
import greenfoot.Actor;
import greenfoot.Greenfoot;
public class Blades extends Actor{
	boolean cooldown = false;
	int time = 0;
	int timer = 10;
	int damage = 4;
	boolean dead = false;

	public Blades(){
		this.setImage("Images/Blades.png");
	}

	public void act(){
		if(!dead){
			if(!cooldown)
			{
				for(Object obj:  getIntersectingObjects( Goblin.class))
				{
					Goblin gob = (Goblin) obj;
					gob.health -= damage;
					cooldown = true;
				}
//				for(Object obj: getObjectsInRange(this.getImage().getHeight()/2, Wraith.class))
//				{
//					Wraith wraith = (Wraith) obj;
//					wraith.health -= damage;
//					cooldown = true;
//				}
				for(Object obj:  getIntersectingObjects( Rats.class))
				{
					Rats rat = (Rats) obj;
					rat.health -= damage;
					cooldown = true;
				}

							for(Object obj:  getIntersectingObjects( BossGoblin.class))
							{
								BossGoblin gob = (BossGoblin) obj;
								gob.health -= damage;
								cooldown = true;
							}
							for(Object obj:  getIntersectingObjects( CursedSwords.class))
							{
								CursedSwords wraith = (CursedSwords) obj;
								wraith.health -= damage;
								cooldown = true;
							}
				//			for(Object obj: getObjectsInRange(this.getImage().getHeight()/2, BossRat.class))
				//			{
				//				BossRat rat = (BossRat) obj;
				//				rat.health -= damage;
				//				cooldown = true;
				//			}
			}
			else
			{
				time++;
				if(time > timer)
				{
					cooldown = false;
					time = 0;
				}
			}
//			if(Greenfoot.mouseClicked(this) && Dungeon.state==Content.SELL)
//			{
//				this.getWorld().removeObject(this);
//			}

			if(Greenfoot.mouseClicked(this)&& Dungeon.state == Content.SELL){
				this.getWorld().removeObject(this);
				dead = true;
				Dungeon.gd.addMoney(Dungeon.bladecost);
			}
		}
	}
}