package DieFight;

public class Explore extends DieFight{

	public Explore(){
		if(player[6] >= TIER5){
			System.out.println("No longer allowed to play this challenge");
			return;
		}

		System.out.println("Choose a difficulty.");

		for(difficulty x: difficulty.values()){
			System.out.println(x);
		}
		action2 = input.next();
		/* location, season, events */

		locations = gen.nextInt(5);  
		String[] location = {"Forest", "Mountains", "City", "Wasteland", "Plane"};


		int seasons = gen.nextInt(4);
		String[] season = {"spring", "summer", "fall", "winter"};


		int events = gen.nextInt(4);
		String event[] = {"war", "festival", "plague", "none"};


		if(action2.equalsIgnoreCase("commoner") || action2.equalsIgnoreCase("adventurer")){
			if(action2.equalsIgnoreCase("adventurer")){
				AI[2] ++;
			}

			AI[0] = 1;
			if(seasons == 3){
				AI[0] ++;
			}
			if(locations == 1 || locations == 3){
				AI[0] ++;
			}
			if(events == 0 ){
				AI[0] ++;
			}
			if(events == 1){
				AI[0] --;
			}
			if(events == 2){
				player[2] --;
			}

			System.out.println("Your exploration journey is about to begin information regarding the challenge.");
			System.out.println("Location: " + location[locations] + "\nSeason: " + season[seasons] + "\nEvents: " + event[events]);
			battle();
		}

		if(action2.equalsIgnoreCase("hero") || action2.equalsIgnoreCase("soldier")){
			if(action2.equalsIgnoreCase("hero")){
				AI[2] ++;
			}

			AI[0] = 3;
			if(seasons == 3 && locations != 4){
				AI[0] ++;
				if(locations == 1){
					AI[0] ++;
				}
			}
			if(locations == 4 || locations == 3){
				AI[0] ++;
			}
			if(events == 0 ){
				AI[0] ++;
				if(locations == 2){
					AI[0] ++;
				}
			}
			if(events == 1 && locations == 2){
				AI[0] --;
			}
			if(events == 2){
				player[2] --;
			}

			System.out.println("Your exploration journey is about to begin information regarding the challenge.");
			System.out.println("Location: " + location[locations] + "\nSeason: " + season[seasons] + "\nEvents: " + event[events]);
			battle();

		}
		if(action2.equalsIgnoreCase("legend") || action2.equalsIgnoreCase("deity")){
			if(action2.equalsIgnoreCase("legend")){
				AI[1] ++;
			}
			if(action2.equalsIgnoreCase("deity")){
				AI[2] ++;
			}

			AI[1] ++;
			AI[0] = 5; 
			if(seasons == 3){
				AI[0] ++;
				if(locations == 1 || locations == 4){
					AI[0] ++;
				}
			}
			if(locations == 4 || locations == 3){
				AI[0] ++;
			}
			if(events == 0 ){
				AI[0] ++;
				if(locations == 2 || locations == 4){
					AI[0] ++;
				}
			}
			if(events == 1 && locations == 2){
				AI[0] --;
			}
			if(events == 2){
				player[2] --;
				if(seasons == 3){
					AI[0] ++;
				}
				if(locations == 4){
					AI[0] ++;
				}
			}

			System.out.println("Your exploration journey is about to begin information regarding the challenge.");
			System.out.println("Location: " + location[locations] + "\nSeason: " + season[seasons] + "\nEvents: " + event[events]);
			battle();
		}
		else{
			System.out.println("Invalid Entry");
			valid = false;
		}
		cleanup();
		return;
	}
}
