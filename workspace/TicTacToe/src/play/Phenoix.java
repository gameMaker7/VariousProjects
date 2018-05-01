package play;

import greenfoot.Actor;
import greenfoot.Greenfoot;

public class Phenoix extends Actor {
	State state = State.active;
	Nuke nuke = new Nuke();
	int offset = 100;
	public Phenoix(){
		this.setImage("images/phenoix.jpg");
		this.getImage().scale(75, 75);

	}
	public void vertical(){
		if(this.getOneObjectAtOffset(0,-offset, Phenoix.class) != null){
		if(this.getOneObjectAtOffset(0,offset, Phenoix.class) != null){
				Main_Menu.winner = "PHENOIX";
				Main_Menu.phenoixWins++;
				Main_Menu menu = new Main_Menu();
				Greenfoot.setWorld(menu);
			}
		}
	}
	public void horizontal(){
		if(this.getOneObjectAtOffset(-offset,0, Phenoix.class) != null){
			if(this.getOneObjectAtOffset(offset,0, Phenoix.class) != null){
					Main_Menu.winner = "PHENOIX";	
					Main_Menu.phenoixWins++;
					Main_Menu menu = new Main_Menu();
					Greenfoot.setWorld(menu);
				}
			}
		}
	public void diagonal1(){
			if(this.getOneObjectAtOffset(-offset,-offset, Phenoix.class) != null){
				if(this.getOneObjectAtOffset(offset,offset, Phenoix.class) != null){
						Main_Menu.winner = "PHENOIX";
						Main_Menu.phenoixWins++;
						Main_Menu menu = new Main_Menu();
						Greenfoot.setWorld(menu);
					}
				}
			}
	public void diagonal2(){
		if(this.getOneObjectAtOffset(-offset,offset, Phenoix.class) != null){
			if(this.getOneObjectAtOffset(offset,-offset, Phenoix.class) != null){
					Main_Menu.winner = "PHENOIX";
					Main_Menu.phenoixWins++;
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
		if(Greenfoot.mouseClicked(this) && Board.player2){
			getWorld().addObject(nuke, this.getX(), this.getY());
			Board.player2 = false;
			Board.player1 = true;
			state = State.dead;
			Board.nukeLimit ++;
			getWorld().removeObject(this);

		}
	}
}