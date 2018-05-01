package DungeonMangager;

import DungeonMangager.Traps.Content;
import greenfoot.Actor;

public class Current extends Actor {
	public static Content content = Content.NONE;
	
	public Current(){
		setImage("Images/Blank Current.png");
	}
	public static void setCurrent(Content news){
		Current.content = news;
	}
	public void act(){
		switch(content){
		case NONE:
			this.setImage("Images/Blank Current.png");
			break;
		case POISON:
			this.setImage("Images/Poison Current.png");
			break;
		case BLADE:
			this.setImage("Images/Blades Current.png");
			break;
		case FLAME:
			this.setImage("Images/Fire Current.png");
			break;
		case SPIKE:
			this.setImage("Images/Spike Current.png");
			break;
		case LIGHTNING:
			this.setImage("Images/Thunder Current.png");
			break;
		case MAGIC:
			this.setImage("Images/Magic Current.png");
			break;
		default:
			break;
		}
	}
}