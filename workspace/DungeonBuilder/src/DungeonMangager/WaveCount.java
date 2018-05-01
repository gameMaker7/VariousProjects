package DungeonMangager;
import java.awt.Color;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WaveCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaveCount extends Actor
{

	

	public WaveCount(){
		this.setImage("images/Wave Display.png");
	}

	public void act(){
		
			this.getImage().clear();
			this.setImage("images/Wave Display.png");
			this.getImage().setColor(Color.GREEN);
			String lives = Integer.toString(Dungeon.waveCount);
			this.getImage().drawString(lives, 80, 20);
			
		}
	}
