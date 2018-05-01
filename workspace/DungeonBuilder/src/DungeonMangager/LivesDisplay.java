package DungeonMangager;

import java.awt.Color;

import greenfoot.Actor;

public class LivesDisplay extends Actor{
	int lives = Core.health;
	int currentlives = 0;

	public LivesDisplay(){
		this.setImage("Images/Lives Display.png");
		this.getImage().setColor(Color.PINK);
		String lifes = Integer.toString(lives);
		this.getImage().drawString(lifes, 95, 20);
	}

	public void act(){
		lives = Core.health;
		this.getImage().clear();
		this.setImage("images/Lives Display.png");
		this.getImage().setColor(Color.PINK);
		String lifes = Integer.toString(lives);
		this.getImage().drawString(lifes, 95, 20);
	}

}
