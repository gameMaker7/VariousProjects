import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Room here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
 

import greenfoot.Actor;

import java.util.Random;

public class Room extends Actor{

	Random gen = new Random();
	int coreX = 0;
	int coreY = 0;
	int[] wayTurn = {0};
	int[] wayX = {0};
	int[] wayY = {0};
	int[] spawnX = new int[3];
	int[] spawnY = new int[3];
	int room = 1;//gen.nextInt(2);
	
		public void create(){
			
			switch(room){
			case 0:
				this.setImage("Images/DungeonTwo.png");
				coreX = 595;
				coreY =	315;
				int[] spawnerX = {850,300};
				spawnX = spawnerX;
				int[] spawnerY = {110,550};
				spawnY = spawnerY;

				int[] turn = {91,50,330,0,-90,230};
				wayTurn = turn;
				int[] x = {550,550,410,460,640,635};
				wayX = x;
				int[] y = {110,220,550,520,520,380};
				wayY = y;
				break;
			case 1:
				this.setImage("Images/Dungeon Room.png");
				this.getImage().scale(1400, 900);
				int[] spawnerX2 = {1250,170,150};
				spawnX = spawnerX2;
				int[] spawnerY2 = {100,820,100};
				spawnY = spawnerY2;
				coreX = 1200;
				coreY =	800;
				int[] turn2 = {91,0};
				wayTurn = turn2;
				int[] x2 = {150,150};
				wayX = x2;
				int[] y2 = {100,815};
				wayY = y2;
				break;
			}
		}
		public Room(){
			create();
		}
		
	}

