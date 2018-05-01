package DungeonMangager.Traps;
import greenfoot.Actor;
import greenfoot.Greenfoot;


public class QuitButton extends Actor{

	public QuitButton(){
		this.setImage("Images/Quit Button.png");
	}
	
	public void act(){
		if(Greenfoot.mouseClicked(this)){
			System.exit(0);
		}
	}
}