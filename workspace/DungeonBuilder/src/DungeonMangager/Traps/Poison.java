package DungeonMangager.Traps;

import greenfoot.Actor;
import greenfoot.Greenfoot;

import java.awt.Color;
import java.util.Random;

import DungeonMangager.Dungeon;
import Monsters.BossGoblin;
import Monsters.CursedSwords;
import Monsters.Goblin;
import Monsters.Rats;
import Monsters.Wraith;

public class Poison extends Actor {
	int height = 10;
	int width = 10;
	int tempx = -1;
	int tempy = -1;
	int damage = 15;
	int startx;
	int starty;
	Random rand = new Random();
	boolean cooldown = false;
	int timer = 10;
	int etime = 0;
	int ctimer = 0;
	int cooldowns = 0;
	boolean extratime = false;
	boolean dead = false;

	public Poison(int x,int y){
		this.setImage("Images/Clear.png");
		this.getImage().scale(width, height);
		startx = x;
		starty = y;
	}
	public void act(){
		this.setLocation(startx-width, starty-height);
		if(!dead){
			if(!cooldown)
			{
				if(!extratime)
				{
					if(tempx == -1 && tempy == -1)
					{
						tempx = rand.nextInt(width) + startx;
						tempy = rand.nextInt(height)+ starty;
					}
					//this.getImage().clear();
					this.getImage().scale(ctimer+1,ctimer+1);
					this.setLocation(tempx, tempy);
					this.getImage().setColor(Color.GREEN);
					this.getImage().fillOval(this.getImage().getWidth()/2 - ctimer/2, this.getImage().getHeight()/2 - ctimer/2, ctimer, ctimer);
					ctimer++;
				}
				else
				{
					etime++;
					if(etime>timer*2)
					{
						etime = 0;
						extratime = false;
						cooldown = true;
					}
				}
				for(Object obj: getObjectsInRange(this.getImage().getHeight()/2, Goblin.class))
				{
					Goblin gob = (Goblin) obj;
					if(!gob.poison)
					{
						gob.health -= damage;
						gob.poison = true;
					}
				}
				for(Object obj: getObjectsInRange(this.getImage().getHeight()/2, Wraith.class))
				{

					Wraith wraith = (Wraith) obj;
					if(!wraith.poison)
					{
						wraith.health -= damage;
						wraith.poison = true;

					}
				}	
				for(Object obj: getObjectsInRange(this.getImage().getHeight()/2, Rats.class))
				{

					Rats rat = (Rats) obj;
					if(!rat.poison)
					{
						rat.health -= damage;
						rat.poison = true;
					}
				}
				for(Object obj: getObjectsInRange(this.getImage().getHeight()/2, BossGoblin.class))
				{
					BossGoblin gob = (BossGoblin) obj;
					if(!gob.poison)
					{
						gob.health -= damage;
						gob.poison = true;
					}
				}
				for(Object obj: getObjectsInRange(this.getImage().getHeight()/2,CursedSwords.class))
				{
					CursedSwords gob = (CursedSwords) obj;
					if(!gob.poison)
					{
						gob.health -= damage;
						gob.poison = true;
					}
				}
				if(ctimer>timer)
				{
					extratime = true;
					ctimer = 0;
				}


			}
			else
			{


				this.getImage().clear();
				this.getImage().scale(width,height);
				this.setLocation(startx, starty);
				cooldowns++;
				tempx = -1;
				tempy = -1;
				if(cooldowns%40 == 0)
				{
					cooldown = false;
				}
			}

			if(Greenfoot.mouseClicked(this)&& Dungeon.state == Content.SELL){
				this.getWorld().removeObject(this);
				dead = true;
				Dungeon.gd.addMoney(Dungeon.poisoncost);
			}
		}

	}
}