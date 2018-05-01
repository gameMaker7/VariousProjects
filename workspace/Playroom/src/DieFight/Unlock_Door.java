package DieFight;

public class Unlock_Door extends DieFight{
	public Unlock_Door() {
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
		if(player[6] >= 50){
			AI[0] += 10;
		}
		locations = gen.nextInt(5);  
		String[] location = {"House", "Castle", "Mansion", "Vualt", "Plane"};
		AI[0] = 3;

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

		int materials = gen.nextInt(4);
		String[] material = {"wood", "iron", "steel", "magic"};


		if(materials==1){ // 1
			AI[0] ++;
		}
		if(materials==2){ // 2
			AI[0] ++;
		}
		if(materials==3){ // 3
			AI[0] ++;
			AI[2] ++;
		}

		if(action2.equalsIgnoreCase("commoner") || action2.equalsIgnoreCase("adventurer")){
			if(action2.equalsIgnoreCase("adventurer")){
				AI[2] ++;
			}

			System.out.println("Locksmith is about to begin information regarding the challenge.");
			System.out.println("Location: " + location[locations] + "\nMaterial: " + material[materials]);
			battle();
		}
		if(action2.equalsIgnoreCase("soldier") || action2.equalsIgnoreCase("hero") || action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
			AI[0] += 2;
			int locks = gen.nextInt(4);
			String[] lock = {"simple", "complex", "advanced", "masterwork"};

			if(locks == 1){ // 1
				AI[0] ++;
			}
			if(locks == 2){ // 2
				AI[1] ++;
			}
			if(locks == 3){ // 3
				AI[2] ++;
			}

			if(action2.equalsIgnoreCase("hero") || action2.equalsIgnoreCase("soldier")){
				if(action2.equalsIgnoreCase("hero")){
					AI[2] ++;
				}
				System.out.println("Locksmith is about to begin information regarding the challenge.");
				System.out.println("Location: " + location[locations] + "\nMaterial: " + material[materials] + "\nLock: " + lock[locks]);
				battle();

			}
			if(action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
				AI[0] += 3; 
				int magics = gen.nextInt(3);
				String[] magic = {"slow", "blind", "detect"};
				if(magics == 0){
					AI[0] ++;
				}
				if(magics == 1){
					AI[2] ++;
					player[2] --;
				}
				if(magics == 2){
					AI[1] ++;
					AI[2] ++;
				}

				if(action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
					if(action2.equalsIgnoreCase("legend")){
						AI[1] ++;
					}
					if(action2.equalsIgnoreCase("deity")){
						AI[2] ++;
					}

					System.out.println("Locksmith is about to begin information regarding the challenge.");
					System.out.println("Location: " + location[locations] + "\nMaterial: " + material[materials] + "\nLock: " + lock[locks] + "\nMagic: " + magic[magics]);
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
