package DieFight;

public class Craft extends DieFight {
	public Craft(){
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
		String[] location = {"Forge", "Castle", "Battlefield", "Wilderness", "Plane"};


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

		int objects = gen.nextInt(4);
		String[] object = {"Ring", "Shield", "Sword", "Armor"};


		if(objects ==1){ // 1
			AI[0] ++;
		}
		if(objects ==2){ // 2
			AI[1] ++;
		}
		if(objects ==3){ // 3
			AI[0] ++;
			AI[2] ++;
		}

		if(action2.equalsIgnoreCase("commoner") || action2.equalsIgnoreCase("adventurer")){
			if(player[6] >= TIER5){
				System.out.println("No longer allowed to play this difficulty");
				return;}
			if(action2.equalsIgnoreCase("adventurer")){
				AI[2] ++;
			}

			System.out.println("Your work is about to begin information regarding the challenge.");
			System.out.println("Location: " + location[locations] + "\nObject: " + object[objects]);
			battle();

			cleanup();
			return;
		}

		if(action2.equalsIgnoreCase("soldier") || action2.equalsIgnoreCase("hero") || action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
			AI[0] += 2; 
			int types = gen.nextInt(4);
			String[] type = {"Basic", "Improved", "Expert", "Masterwork"};

			if(types == 1){ // 1
				AI[0] ++;
			}
			if(types == 2){ // 2
				AI[1] ++;
			}
			if(types == 3){ // 3
				AI[2] ++;
			}

			if(action2.equalsIgnoreCase("hero") || action2.equalsIgnoreCase("soldier")){
				if(action2.equalsIgnoreCase("hero")){
					AI[2] ++;
					AI[0] ++;
				}
				System.out.println("Your work is about to begin information regarding the challenge.");
				System.out.println("Location: " + location[locations] + "\nObject: " + object[objects] + "\nType: " + type[types]);
				battle();

			}
			if(action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
				AI[0] += 2;

				int magics = gen.nextInt(3);
				String[] magic = {"Strengthen", "Inscribed", "Enchanted"};
				if(magics == 0){
					AI[0] ++;
				}
				if(magics == 1){
					AI[2] ++;
					if(objects == 0){
						AI[0] ++;
					}
				}
				if(magics == 2){
					AI[1] ++;
					AI[2] ++;
					if(types == 3){
						AI[1] ++;
					}
				}

				if(action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
					if(player[6] >= TIER5){
						AI[0] += 8;
					}
					if(action2.equalsIgnoreCase("legend")){
						AI[1] ++;
					}
					if(action2.equalsIgnoreCase("deity")){
						AI[2] ++;
						AI[0] += 3;
					}

					System.out.println("Your work is about to begin information regarding the challenge.");
					System.out.println("Location: " + location[locations] + "\nObject: " + object[objects] + "\nType: " + type[types] + "\nMagic: " + magic[magics]);
					battle();
				}
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
