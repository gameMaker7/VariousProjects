package DieFight;

public class Dungeon extends DieFight {

	public Dungeon(){
		if(player[6] < TIER1){
			System.out.println("This challenge is not yet avaible.\n ");

			return;
		}
		System.out.println("Choose a difficulty.");

		for(difficulty x: difficulty.values()){
			System.out.println(x);
		}
		action2 = input.next();
		/* materials, lock, enchanmnet,  */

		locations = gen.nextInt(5);  
		String[] location = {"Cavern", "Forest", "Ruins", "Crypt", "Plane"};


		if(locations == 1){ // 2
			AI[2] ++;
		}
		if(locations == 2){ // 1
			AI[0] ++;
		}
		if(locations == 3){ // 3
			AI[1] ++;
			AI[0] ++;
		}
		if(locations == 4){ // 4
			AI[0] += 2;
			AI[2] ++;
		}

		int Monsters = gen.nextInt(4);
		String[] Monster = {"Wolves", "Bandits", "Elementals", "Undead", "Dragonkin"};


		if(Monsters ==1){ // 1
			AI[0] ++;
		}
		if(Monsters ==2){ // 2
			AI[1] ++;
		}
		if(Monsters ==3){ // 3
			AI[0] ++;
			AI[2] ++;
		}
		if(Monsters == 4){
			AI[0 ]++;
			AI[1] ++;
		}
		if(action2.equalsIgnoreCase("commoner") || action2.equalsIgnoreCase("adventurer")){
			if(player[6] >= TIER5){
				System.out.println("No longer allowed to play this difficulty");
				return;
			}

			if(action2.equalsIgnoreCase("adventurer")){
				AI[2] ++;
				AI[0] ++;
			}
			AI[0] += 2;
			System.out.println("Your clearing expedition is about to begin information regarding the challenge.");
			System.out.println("Location: " + location[locations] + "\nMonsters: " + Monster);
			battle();

			cleanup();
			return;
		}

		if(action2.equalsIgnoreCase("soldier") || action2.equalsIgnoreCase("hero") || action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
			AI[0] += 2; 
			int amount = gen.nextInt(10) + 1;
			AI[0] += amount;

			if(action2.equalsIgnoreCase("hero") || action2.equalsIgnoreCase("soldier")){
				if(player[6] >= TIER5){
					AI[0] += 5;
				}
				if(player[6] >= FINALTIER){
					System.out.println("No longer allowed to play this difficulty");
					return;
				}
				if(action2.equalsIgnoreCase("hero")){
					AI[2] ++;
					AI[0] ++;
				}
				System.out.println("Your clearing expedition is about to begin information regarding the challenge.");
				System.out.println("Location: " + location[locations] + "\nMonster: " + Monster[Monsters] + "\nNumber of Levels: " + amount);
				battle();	


			}
			if(action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
				AI[0] += 3;

				int bosses = gen.nextInt(5);
				String[] boss  = {"Wizard", "Giant", "Golem", "Anchient Dragon", "Spirit Guardian"};
				if(bosses == 0){
					AI[0] ++;
				}
				if(bosses == 1){
					AI[2] ++;
					if(Monsters == 3){
						AI[0] ++;
					}
				}
				if(bosses == 2){
					AI[0] ++;
					AI[2] ++;
					if(locations == 4){
						AI[1] ++;
					}
					if(Monsters == 2){
						AI[1] ++;
					}
				}
				if(bosses == 3){
					AI[0] ++;
					if(Monsters == 4){
						AI[0] ++;
					}
					if(locations == 0){
						AI[1] ++;
					}
				}
				if(bosses == 4){
					AI[2] ++;
					if(locations == 3){
						AI[1] ++;
					}
					if(Monsters == 3){
						AI[0] ++;
					}
				}


				if(action2.equalsIgnoreCase("legend")){
					AI[1] ++;
					if(player[6] >= TIER5){
						AI[0] *= 2;
					}

				}

				if(action2.equalsIgnoreCase("deity")){
					AI[2] += 2;
					AI[0] += 4;
					if(player[6] >= TIER5){
						AI[0] *= 3;
					}

					System.out.println("Your clearing expedition is about to begin information regarding the challenge.");
					System.out.println("Location: " + location[locations] + "\nMonsters: " + Monster[Monsters] + "\nNumber of Levels: " + amount + "\nBoss: " + boss[bosses]);
					battle();	
				}
			}
			else{
				System.out.println("Invalid Entry");
				valid = false;
			}
			cleanup();
			return;
		}
	}

}
