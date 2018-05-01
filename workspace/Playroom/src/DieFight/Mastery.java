package DieFight;

public class Mastery extends DieFight {

	public Mastery() {
		System.out.println("This is a mastery level challenge do you wish to attempt this?");
		if(action2.equals("no")){
			return;
		}
		else{
			int types = gen.nextInt(5);
			if(types == 0){
				System.out.println("Create your Research Paper.");
				Research();
			}
			if(types == 1){
				System.out.println("This is an Epic Encounter.");
				Combat();
			}
			if(types == 2){
				System.out.println("This is a Crafting Challenge.");
				Crafting();
			}
			if(types == 3){
				System.out.println("This is a Mage's Duel.");
				Magic();
			}
			if(types == 4){
				System.out.println("A Raid is about to begin.");
				Raid();
			}
		}
	}

	public static void Research(){
		AI[0] = player[0] / player[6];
		System.out.println("This challenge becomes harder the better your score to chllenge ratio is " + player[0]/player[6] + ".\nHow many dice do you wish to use?");
		player[4] = input.nextInt();
		if(player[4]>player[2]){
			System.out.println("You attepted to use more dice than you have or tried to use all of your dice. Please try again.");
			return;
		}
		System.out.println("You roll.");
		for(int i=0;i<player[4];i++){
			rolledValue = gen.nextInt(player[3]) + 1 + player[5];
			System.out.print(rolledValue + "..");
			rolledValueTotal += rolledValue ;
		}
		for(int i=0;i<AI[0];i++){
			rolledValue = gen.nextInt(AI[1]) + 1 + AI[2];
			AIValue += rolledValue;
		}
		System.out.println("Your roll equals: " + rolledValueTotal + "\nChallenge Value is: " + AIValue);
		if(AIValue >= rolledValueTotal){
			System.out.println("Challenge failed you lose all dice used in this challenge.");
			player[2] -= player[4];
		}else{
			player[0] += (AI[0] * SCOREMULTIPLIER) + TIER2 + (player[4] * SCOREINCREASE);
			System.out.println("Challenge complete your score is now " + player[0]);
			player[2] -= player[4];
			player[2]+= (AI[0]);
			System.out.println("You now have " + player[2] + " dice.");
			player[6] ++;
		}	
		cleanup();
		return;
	}

	public static void Combat(){
		AI[0] = player[1] * player[6];
		System.out.println("This challenge bocomes harder based on your lives to challenge ratio." + player[1]/player[6]);
		player[4] = input.nextInt();
		if(player[4]>player[2]){
			System.out.println("You attepted to use more dice than you have or tried to use all of your dice. Please try again.");
			return;
		}
		System.out.println("You roll.");
		for(int i=0;i<player[4];i++){
			rolledValue = gen.nextInt(player[3]) + 1 + player[5];
			System.out.print(rolledValue + "..");
			rolledValueTotal += rolledValue ;
		}
		for(int i=0;i<AI[0];i++){
			rolledValue = gen.nextInt(AI[1]) + 1 + AI[2];
			AIValue += rolledValue;
		}
		System.out.println("\nEnemies. " + AI[0]);
		System.out.println("Your roll equals: " + rolledValueTotal + "\nChallenge Value is: " + AIValue);
		if(AIValue >= rolledValueTotal){
			System.out.println("Challenge failed you lose 2 lives and half the dice used in this challenge.");
			player[2] = player[4]/2;
			player[1] -= 2;
		}else{
			player[0] += (AI[0] * SCOREMULTIPLIER) + TIER2 + (player[4] * SCOREINCREASE);
			System.out.println("Challenge complete your score is now " + player[0]);
			player[2]+= AI[0];
			System.out.println("You now have " + player[2] + " dice.");
			player[6] += 2;
		}	
		cleanup();
		return;
	}
	public static void Crafting(){
		AI[0] = player[6] * player[5] + 10;
		System.out.println("This challenge gets harder the better your rolls are. " + player[2] +" dice.");
		player[4] = input.nextInt();
		if(player[4]>player[2]){
			System.out.println("You attepted to use more dice than you have or tried to use all of your dice. Please try again.");
			return;
		}
		System.out.println("You roll.");
		for(int i=0;i<player[4];i++){
			rolledValue = gen.nextInt(player[3]) + 1 + player[5];
			System.out.print(rolledValue + "..");
			rolledValueTotal += rolledValue ;
		}
		for(int i=0;i<AI[0];i++){
			rolledValue = gen.nextInt(AI[1]) + 1 + AI[2];
			AIValue += rolledValue;
		}
		System.out.println("\nMaterials Cost ." + AI[0]);
		System.out.println("Your roll equals: " + rolledValueTotal + "\nChallenge Value is: " + AIValue);
		if(AIValue >= rolledValueTotal){
			System.out.println("Challenge failed you lose all dice used in this challenge.");
			player[2] -= player[4];
		}else{
			player[0] += (AI[0] * SCOREMULTIPLIER) + TIER2 + (player[4] * SCOREINCREASE);
			System.out.println("Challenge complete your score is now " + player[0]);
			player[2] -= player[4];
			player[2] += AI[0];
			player[3] ++;
			System.out.println("You now have " + player[2] + " dice.");
			player[6] += 2;
		}	

		cleanup();
		return;
	}

	public static void Magic(){
		AI[0] = player[0]/player[2];
		System.out.println("A mage's duel is based on your score to dice ratio." + player[0]/player[2]);
		player[4] = input.nextInt();
		if(player[4]>player[2]){
			System.out.println("You attepted to use more dice than you have or tried to use all of your dice. Please try again.");
			return;
		}
		System.out.println("You roll.");
		for(int i=0;i<player[4];i++){
			rolledValue = gen.nextInt(player[3]) + 1 + player[5];
			System.out.print(rolledValue + "..");
			rolledValueTotal += rolledValue ;
		}
		for(int i=0;i<AI[0];i++){
			rolledValue = gen.nextInt(AI[1]) + 1 + AI[2];
			AIValue += rolledValue;
		}
		System.out.println("Your roll equals: " + rolledValueTotal + "\nChallenge Value is: " + AIValue);
		if(AIValue >= rolledValueTotal){
			System.out.println("Challenge failed you lose 3 lives.");
			player[1]-= 3;
		}else{
			player[0] += (AI[0] * SCOREMULTIPLIER) + FINALTIER + (player[4] * SCOREINCREASE);
			System.out.println("Challenge complete your score is now " + player[0]);
			player[2]+= AI[0];
			System.out.println("You now have " + player[2] + " dice.");
			player[6] += 3;
		}	

		cleanup();
		return;

	}

	public static void Raid(){
		System.out.println("For attempting a raid you gain better equipment +1 to die rolls and +2 if you survive.");
		player[5]++;
		AI[0] = player[0]/player[1] - gen.nextInt(player[0]/player[1]) * player[5];
		System.out.println("Raids are unknown dungeons no telling what they have. " + player[2] +" dice.");
		player[4] = input.nextInt();
		if(player[4]>player[2]){
			System.out.println("You attepted to use more dice than you have or tried to use all of your dice. Please try again.");
			return;
		}
		System.out.println("You roll.");
		for(int i=0;i<player[4];i++){
			rolledValue = gen.nextInt(player[3]) + 1 + player[5];
			System.out.print(rolledValue + "..");
			rolledValueTotal += rolledValue ;
		}
		for(int i=0;i<AI[0];i++){
			rolledValue = gen.nextInt(AI[1]) + 1 + AI[2];
			AIValue += rolledValue;
		}
		System.out.println("\nObstacles Faced." + AI[0]);
		System.out.println("Your roll equals: " + rolledValueTotal + "\nChallenge Value is: " + AIValue);
		if(AIValue >= rolledValueTotal){
			System.out.println("Challenge failed you lose all dice.");
			player[2] = 1;
		}else{
			player[0] += (AI[0] * SCOREMULTIPLIER) + TIER2 + (player[4] * SCOREINCREASE);
			System.out.println("Challenge complete your score is now " + player[0]);
			player[2]+= AI[0];
			player[5] += 2;
			System.out.println("You now have " + player[2] + " dice.");
			player[6] += 4;
		}	

		cleanup();
		return;
	}
}

