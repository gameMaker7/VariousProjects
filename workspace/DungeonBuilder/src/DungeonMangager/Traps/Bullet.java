package DungeonMangager.Traps;

import Monsters.*;
import greenfoot.*;

public class Bullet extends Actor {
	Goblin gob;
	Rats rat;
	Wraith wraith;
	BossGoblin bg;
	CursedSwords cs;
	public Bullet(Goblin obj)
	{
		this.setImage("Images/Bullet.png");
		this.gob = obj;
	}
	public Bullet(Rats obj)
	{
		this.setImage("Images/Bullet.png");
		this.rat = obj;
	}
	public Bullet(BossGoblin obj)
	{
		this.setImage("Images/Bullet.png");
		this.bg = obj;
	}
	public Bullet(CursedSwords obj)
	{
		this.setImage("Images/Bullet.png");
		this.cs = obj;
	}
	public void Act(){
		if(gob != null)
		{
			if(gob.state != MonsterState.Dead)
			{
				this.setLocation(gob.getX()-gob.getImage().getWidth(),gob.getY()-gob.getImage().getHeight());
				this.setRotation(0);
				move(1);
			}
			if(this.intersects(gob))
			{
				gob.health = 0;
			}
		}
		if(rat != null)
		{
			if(rat.state != MonsterState.Dead)
			{
				this.setLocation(rat.getX()-rat.getImage().getWidth(),rat.getY()-rat.getImage().getHeight());
				this.setRotation(0);
				move(1);
			}
			if(this.intersects(rat))
			{
				rat.health = 0;
			}
		}
		if(wraith != null)
		{
			if(wraith.state != MonsterState.Dead)
			{
				this.setLocation(wraith.getX()-wraith.getImage().getWidth(),wraith.getY()-wraith.getImage().getHeight());
				this.setRotation(0);
				move(1);
			}
			if(this.intersects(wraith))
			{
				wraith.health = 0;
			}
		}
		if(bg != null)
		{
			if(bg.state != MonsterState.Dead)
			{
				this.setLocation(bg.getX()-bg.getImage().getWidth(),bg.getY()-bg.getImage().getHeight());
				this.setRotation(0);
				move(1);
			}
			if(this.intersects(bg))
			{
				gob.health = 0;
			}
		}
		if(cs != null)
		{
			if(cs.state != MonsterState.Dead)
			{
				this.setLocation(cs.getX()-cs.getImage().getWidth(),cs.getY()-cs.getImage().getHeight());
				this.setRotation(0);
				move(1);
			}
			if(this.intersects(cs))
			{
				cs.health = 0;
			}
		}
	}
}