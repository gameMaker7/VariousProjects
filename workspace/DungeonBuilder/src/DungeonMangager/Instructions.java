package DungeonMangager;

import java.awt.Color;

import greenfoot.*;

public class Instructions extends Actor {
	boolean dead = false;
	GreenfootSound musics;
	int timer = 0;
	int die = 0;
	public Instructions(){
		this.setImage("Images/Clear.png");
		musics = new GreenfootSound("Images/Music.mp3");
		this.getImage().scale(15000, 2000);
		this.getImage().setColor(Color.BLACK);
		//this.getImage().fill();
		this.getImage().setColor(Color.RED);
		String instruct = "The Instructions for the game";// + "/n" + "This is a tower defense game, the buttons at the bottem of the screen put traps into your hand, and "
				//+ "then you can place them on the map to defend the core." + "/n" + "- There are 6 traps that range in power and price.  They raise in price " +
//				"as you play, so be careful in placement!" + "n/" + "Wraith enemies are immune to physical traps. Use some fire or magic or something!" + "n/" +
//				"- Boss Goblins will occasionally appear, and they are very tough to kill, but they yield a lot of gold!" + "n/" + "- Traps raise in cost as you play " + 
//				"and that means you can sell previously built traps for profit. But remember to keep a good defense up!" + "n/" + "- The Magic trap builds a tower" + 
//				"that shoots in a massive range, and also has a one-shot blast! Use it wisely!!";
		this.getImage().setColor(Color.WHITE);
		this.getImage().drawString(instruct, 100,100);
	}
	public void act(){
		timer++;
		if(timer > die){
			if(!dead)
			{
				this.getImage().clear();
				this.setLocation(1000, 0);
				this.getImage().scale(1, 1);
				if(!musics.isPlaying())
				{
					musics.playLoop();
				}
			}
		}

	}
}
