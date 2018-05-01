package DungeonMangager.Traps;
import DungeonMangager.Dungeon;
import Monsters.BossGoblin;
import Monsters.CursedSwords;
import Monsters.Goblin;
import Monsters.Rats;
import Monsters.Wraith;
import greenfoot.*;

public class MagicTower extends Actor {
	boolean magic = true;
	boolean cooldown = false;
	int timer = 50;
	int time= 0;
	int range = 3000;
	boolean dead = false;
	//boolean shot = false;
	boolean expDone = false;
	Explosion exp;
	int damage = 2;

	public MagicTower(){
		this.setImage("Images/Magic Tower.png");
	}
	public void act(){
		if(!dead){
			if(Greenfoot.mouseClicked(this) && magic && Dungeon.state == Content.NONE )
			{
				exp = new Explosion();
				this.getWorld().addObject(exp, getX()+this.getImage().getWidth(), getY()+getImage().getHeight());
				magic = false;
				this.setImage("Images/Blank Magic Tower.png");
			}
			else
			{
				if(exp != null){
					expDone = exp.exp;
					if(!cooldown && !dead &&!magic&&expDone)
					{
						for(Object obj: this.getObjectsInRange(range,Goblin.class))
						{
							//if(!shot){
							Goblin gob = (Goblin) obj;
							gob.health -= damage;
							//	shot = true;
							cooldown = true;
							//}
						}for(Object obj: this.getObjectsInRange(range,Rats.class))
						{
							//if(!shot){
							Rats gob = (Rats) obj;
							gob.health -= damage;
							//	shot = true;
							cooldown = true;
							//}
						}
						for(Object obj: this.getObjectsInRange(range,Wraith.class))
						{
							//if(!shot){
							Wraith gob = (Wraith) obj;
							gob.health -= damage;
							//	shot = true;
							cooldown = true;
							//}
						}
						for(Object obj: this.getObjectsInRange(range,BossGoblin.class))
						{
							//if(!shot){
							BossGoblin gob = (BossGoblin) obj;
							gob.health -= damage;
							//	shot = true;
							cooldown = true;
							//}
						}
						for(Object obj: this.getObjectsInRange(range,CursedSwords.class))
						{
							//if(!shot){
							CursedSwords gob = (CursedSwords) obj;
							gob.health -= damage;
							//	shot = true;
							cooldown = true;
							//}
						}
						//	shot = false;
					}
					else
					{
						time++;
						if(time>timer)
						{
							cooldown = false;
							time = 0;
						}
					}

					if(Greenfoot.mouseClicked(this)&& Dungeon.state == Content.SELL){
						this.getWorld().removeObject(this);
						dead = true;
						Dungeon.gd.addMoney(Dungeon.magiccost);
					}
				}
			}
		}
	}
}
