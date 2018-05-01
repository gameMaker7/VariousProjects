package Monsters;

import java.util.Random;

import DungeonMangager.Dungeon;
import greenfoot.Actor;


public class MonsterSpawner extends Actor{
	public boolean reversed = false;
	public boolean down = false;

	public MonsterSpawner(){
		this.setImage("Images/Clear.png");
	}
	int delay = 30;
	Wave waves = new Wave();
	boolean wave1 = true;
	int monsterN = 0;
	Random gen = new Random();

	public void spawn(){
		if(wave1){
			this.getWorld().addObject(waves.wave[monsterN],getX(),getY());
			if(reversed){
				waves.wave[monsterN].setRotation(180);
				waves.wave[monsterN].pathpoint = 0;					
			}
			if(down){
				waves.wave[monsterN].setRotation(90);
				waves.wave[monsterN].pathpoint = 1;					
			}
		}

	}
	public void act(){
		if(wave1 && delay == 0){
			spawn();
			monsterN ++;
			delay = 30;
			if(monsterN >= waves.wave.length){
				Monsters[] array = new Monsters[waves.wave.length + gen.nextInt(30)];
				Dungeon.waveCount ++;
				waves.wave = array;
				waves.create(waves.wave);
				monsterN = 0;
				Monsters.healthincrease += 5;
			}
		}
		//
		delay --;
	}
}
