package DieFight;

public class Slayer extends DieFight {

	public Slayer(){
		if(player[6] >= FINALTIER){
			System.out.println("No longer allowed to play this challenge");
			return;
		}
		System.out.println("Choose a difficulty.");

		for(difficulty x: difficulty.values()){
			System.out.println(x);
		}
		action2 = input.next();
		/* materials, lock, enchanmnet,  */

		locations = gen.nextInt(5);  
		String[] location = {"Wilderness", "Arena", "Battlefield", "Dungeon", "Plane"};


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

		int monsters = gen.nextInt(4);
		String[] monster = {"Goblin", "Troll", "Elemental", "Dragonkin"};


		if(monsters ==1){ // 1
			AI[0] ++;
		}
		if(monsters ==2){ // 2
			AI[1] ++;
		}
		if(monsters ==3){ // 3
			AI[0] ++;
			AI[2] ++;
		}

		if(action2.equalsIgnoreCase("commoner") || action2.equalsIgnoreCase("adventurer")){
			if(player[5] >= TIER5){
				System.out.println("No longer allowed to play this difficulty.");
				return;
			}
			if(action2.equalsIgnoreCase("adventurer")){
				AI[2] ++;
			}

			System.out.println("Your Monster Hunt is about to begin information regarding the challenge.");
			System.out.println("Location: " + location[locations] + "\nMonster: " + monster[monsters]);
			battle();

			cleanup();
			return;
		}
		if(action2.equalsIgnoreCase("soldier") || action2.equalsIgnoreCase("hero") || action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
			AI[0] += 2; 
			int amount = gen.nextInt(5) + 1;
			AI[0] += amount;

			if(action2.equalsIgnoreCase("hero") || action2.equalsIgnoreCase("soldier")){
				if(action2.equalsIgnoreCase("hero")){
					AI[2] ++;
					AI[0] ++;
				}
				System.out.println("Your Monster Hunt is about to begin information regarding the challenge.");
				System.out.println("Location: " + location[locations] + "\nMonster: " + monster[monsters] + "\nNumber of Monsters: " + amount);
				battle();
			}
			if(action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
				AI[0] += 2;

				int abilities = gen.nextInt(3);
				String[] ability  = {"Might", "Spellcraft", "Invisibility"};
				if(abilities == 0){
					AI[0] ++;
				}
				if(abilities == 1){
					AI[2] ++;
					if(monsters == 3){
						AI[0] ++;
					}
				}
				if(abilities == 2){
					AI[1] ++;
					AI[2] ++;
					if(locations >= 2){
						AI[1] ++;
					}
				}

				if(action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
					if(action2.equalsIgnoreCase("legend")){
						if(player[6] >= TIER5){
							AI[0] += 5;
						}
					}
					if(action2.equalsIgnoreCase("deity")){
						AI[2] ++;
						AI[0] += 3;
						if(player[6] >= TIER5){
							AI[0] *= 2;
						}
					}

					System.out.println("Your Monster Hunt is about to begin information regarding the challenge.");
					System.out.println("Location: " + location[locations] + "\nMonster: " + monster[monsters] + "\nNumber of Monsters: " + amount + "\nAbility: " + ability[abilities]);
					battle();
				}
			}

			cleanup();
			return;
		}
		else{
			System.out.println("Invalid Entry");
			valid = false;
		}
	}
}
