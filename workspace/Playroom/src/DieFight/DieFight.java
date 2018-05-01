package DieFight;


import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class DieFight {

	final static int CHALLENGES = 100;
	final static int TIER1 = 10;
	final static int TIER2 = 20;
	final static int TIER3 = 30;
	final static int TIER4 = 40;
	final static int TIER5 = 50;
	final static int FINALTIER = 90;

	final static int SCOREMULTIPLIER = 20;
	final static int SCOREINCREASE = 5;
	final static int UPGRADE = 500;
	final static int CHANCE = 1;

	static int[] player = {0, 10, 10, 6, 0, 0, 0};
	static int[] AI = {0, 6, 0};

	static int rolledValue = 0;
	static int rolledValueTotal = 0;
	static int AIValue = 0;
	static int upgrade = UPGRADE; 
	static int locations = 0;  
	static String action2 = "";
	static int action = 0;
	static Random gen = new Random();
	static Scanner input = new Scanner(System.in);
	public static boolean valid;


	public static void main(String[] args) {

		System.out.println("This is a dice game you begin the game with a random number of six sided dice.\nAs you progress dice will be awarded to you each time you use a die it will be removed from your bag.");
		System.out.println("Is this understood.");
		action2 = input.next();
		if(action2.equals("yes")){
			player[2] += gen.nextInt(10)+1;
			System.out.println("You have been given " + player[2] + " dice.");
			System.out.println("This game fetures five challenges with varying difficulty choose wisely higher difficulty grant greater rewards but are also much harder.");

			do{
				System.out.println("Current Lives: " + player[1] + "\nCurrent Score: " + player[0] + "\nCurrent Dice amount: " + player[2]);
				System.out.println("");
				if(player[6] > TIER1){
					int chance = gen.nextInt(CHANCE+1);
					if(chance == 0 || chance == CHANCE){
						new Mastery();
						continue;
					}
				}
				valid = true;
				System.out.println("Choose a challenge.");
				int a = 0;
				for(challenges x: challenges.values()){
					a++;
					System.out.println("[" + a +"] " + x);
				}

				try {
					action = input.nextInt();						
				}
				catch (InputMismatchException e) {
					System.out.print("I refuse to deal with you.\n");
					break;
				}

				try{
					switch(action){
					case 1: 
						new Explore();
						break;
					case 2:
						new Unlock_Door();
						break;
					case 3:
						new Craft();
						break;
					case 4:
						new Slayer();
						break;
					case 5:
						new Dungeon();
						break;
					case 6:
						player[1] = 0;
						continue;
					default:
						valid = false;
						continue;
					}
				}
				catch(Exception e){
					System.out.println("Stop wasting my time!");	
					continue;
				}

				if(valid){
					System.out.println("Due to the number of challenges complete you have been given extra dice.\nchallenges complete: " + player[6]);
					if(player[6] >= 0){
						player[2]++;
						if(player[6] >= TIER1){
							player[2]++;
							if(player[6] >= TIER2){
								player[2]+= 2;
								if(player[6] >= TIER3){
									player[2]+= 3;
									if(player[6] >= TIER4){
										player[2]+= 4;
										if(player[6] <= TIER5){
											player[2]+= 5;
											player[1] ++;
										}
									}
								}
							}
						}
					}
				}
				if(player[0] >= upgrade){
					System.out.println("Your score has rewarded you with upgrades.\nChoose extra life(life) or dice bonus increase(dice)");
					action2 = input.next();
					if(action2.equalsIgnoreCase("life")){
						player[1]++;
						upgrade +=UPGRADE;
					}
					if(action2.equalsIgnoreCase("dice")){
						player[5] ++;
						upgrade+= UPGRADE;
					}
				}

			}
			while(player[1] >0 && player[2] >0 && player[5] < CHALLENGES);
		}

		if(player[5] == CHALLENGES){
			System.out.println("You have completed 100 challenges game complete.");
			player[0] += (player[2] * SCOREMULTIPLIER) * player[5] * player[1];
			System.out.println("Final score: " + player[0]);
		}
		System.out.println("Game Over");
		input.close();
	}
	public static void battle(){
		System.out.println("With this information you may choose how many dice you wish to use for this challenge. You have " + player[2] + " dice.");
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
			System.out.println("Challenge failed you lose 1 life and all dice used in this challenge.");
			player[2] -= player[4];
			player[1]--;
		}else{
			player[0] += (AI[0] * SCOREMULTIPLIER) + player[6] + (player[4] * SCOREINCREASE);
			System.out.println("Challenge complete. Your score is now " + player[0]);
			player[2] -= player[4];
			player[2] += gen.nextInt(AI[0]) + 1;
			System.out.println("You now have " + player[2] + " dice.");
			player[6] ++;
		}
	}
	public static void cleanup(){
		AIValue = 0;
		AI[2] = 0;
		rolledValueTotal = 0;
		AI[1] = 6;
		AI[0] = 0;
	}
}
