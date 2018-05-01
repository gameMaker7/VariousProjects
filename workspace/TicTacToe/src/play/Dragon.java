package play;

import greenfoot.Actor;
import greenfoot.Greenfoot;

public class Dragon extends Actor {
	State state = State.active;
	int offset = 100;
	Nuke nuke = new Nuke();
	public Dragon(){
		this.setImage("images/dragon.jpg");
		this.getImage().scale(75, 75);

	}
	public void vertical(){
		if(this.getOneObjectAtOffset(0,-offset, Dragon.class) != null){
		if(this.getOneObjectAtOffset(0,offset, Dragon.class) != null){
				Main_Menu.winner = "DRAGON";
				Main_Menu.dragonWins++;
				Main_Menu menu = new Main_Menu();
				Greenfoot.setWorld(menu);
			}
		}
	}
	public void horizontal(){
		if(this.getOneObjectAtOffset(-offset,0, Dragon.class) != null){
			if(this.getOneObjectAtOffset(offset,0, Dragon.class) != null){
					Main_Menu.winner = "DRAGON";
					Main_Menu.dragonWins++;
					Main_Menu menu = new Main_Menu();
					Greenfoot.setWorld(menu);
				}
			}
		}
	public void diagonal1(){
			if(this.getOneObjectAtOffset(-offset,-offset, Dragon.class) != null){
				if(this.getOneObjectAtOffset(offset,offset, Dragon.class) != null){
						Main_Menu.winner = "DRAGON";
						Main_Menu.dragonWins++;
						Main_Menu menu = new Main_Menu();
						Greenfoot.setWorld(menu);
					}
				}
			}
	public void diagonal2(){
		if(this.getOneObjectAtOffset(-offset,offset, Dragon.class) != null){
			if(this.getOneObjectAtOffset(offset,-offset, Dragon.class) != null){
					Main_Menu.winner = "DRAGON";
					Main_Menu.dragonWins++;
					Main_Menu menu = new Main_Menu();
					Greenfoot.setWorld(menu);
				}
			}
		}
	public void act(){
		vertical();
		horizontal();
		diagonal1();
		diagonal2();
		if(Greenfoot.mouseClicked(this) && Board.player1){
			getWorld().addObject(nuke, this.getX(), this.getY());
			Board.player1 = false;
			Board.player2 = true;
			state = State.dead;
			Board.nukeLimit ++;
			getWorld().removeObject(this);

		}	
	}
}
