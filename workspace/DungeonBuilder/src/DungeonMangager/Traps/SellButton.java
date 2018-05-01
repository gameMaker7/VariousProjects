package DungeonMangager.Traps;

import DungeonMangager.Dungeon;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class SellButton extends Actor {
	public SellButton(){
		this.setImage("Images/Sell Button.png");
	}
	
	public void act(){
		if(Greenfoot.mouseClicked(this)){
			Dungeon.state = Content.SELL;
		}
	}

}
