package Monsters;

import java.util.Random;

import DungeonMangager.Dungeon;



public class Wave {
	public Wave(){
		create(wave);	

	}
	int a = 0;
	Random gen = new Random();
	public Monsters[] wave = new Monsters[10];

	public void waveEnemiesR(Monsters[] wave){
		for(int i = 0; i <wave.length; i ++){
			wave[i] = new Rats();
		}

	}	
	public void waveEnemiesW(Monsters[] wave){
		for(int i = 0; i <wave.length; i ++){
			wave[i] = new Wraith();
		}

	}
	public void waveEnemiesG(Monsters[] wave){
		for(int i = 0; i <wave.length; i ++){
			wave[i] = new Goblin();
		}
	}
	public void waveEnemiesC(Monsters[] wave){
		for(int i = 0; i <wave.length; i ++){
			wave[i] = new CursedSwords();
		}
	}	
	public void waveEnemiesBG(Monsters[] wave){
		int a = 0;
		if(Dungeon.waveCount > 15){
			for(int i = a; a < wave.length%50; i ++, a ++){
				wave[i] = new BossGoblin();
			}
		}
			for(int n = a; a < wave.length;a++, n++){
				wave[n] = new Goblin();
			}
		}
	
	public void create(Monsters[] wave){
		a = gen.nextInt(100);
//		if(a <= 10){
//			waveEnemiesBG(wave);
//		}
		 if(a >= 75 && Dungeon.waveCount < 5){
			waveEnemiesG(wave);
		}
		else if(a < 75 && a > 50 && Dungeon.waveCount > 10){
			waveEnemiesR(wave);
		}
		else if(a < 50 && a > 30 && Dungeon.waveCount > 20){
			waveEnemiesW(wave);
		}
		else{
			waveEnemiesC(wave);


		}
	}
}
