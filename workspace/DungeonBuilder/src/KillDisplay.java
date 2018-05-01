import java.awt.Color;

import greenfoot.Actor;


public class KillDisplay extends Actor{
	int kills = 0;
	
	public KillDisplay(){
		this.setImage("images/Kill Display.png");
	}
	
	public void act(){
		int currentkills = 0;
		if(kills != currentkills){
			this.getImage().clear();
			this.setImage("images/Kill Display.png");
			this.getImage().setColor(Color.CYAN);
			String deaths = Integer.toString(kills);
			this.getImage().drawString(deaths, 95, 20);
			currentkills = kills;
		}
	}
}