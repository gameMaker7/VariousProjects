package DungeonMangager;

import java.awt.Color;

import greenfoot.Actor;


public class KillDisplay extends Actor{
	public int currentkills = 0;
	public int kills = 0;
	public int pointlimit = 250;
	
	public KillDisplay(){
		this.setImage("Images/Kill Display.png");
	}
	
	public void act(){
		if(kills != currentkills){
			this.getImage().clear();
			this.setImage("images/Kill Display.png");
			this.getImage().scale(256, 32);
			this.getImage().setColor(Color.CYAN);
			String deaths = Integer.toString(kills);
			this.getImage().drawString(deaths, 175, 20);
			currentkills = kills;
		}
	}
}