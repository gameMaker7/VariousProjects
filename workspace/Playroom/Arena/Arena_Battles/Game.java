package Arena_Battles;

import java.util.Scanner;

import Arena_Battles.Decks.ArenaDeck;

public class Game {

	public static void main(String[] args) {

		int counter = 0;
		boolean play = true;
		Scanner input = new Scanner(System.in);
		String action;
		int actionInt;
		int wins = 0;
		int  games = 0;
		ArenaDeck game = new ArenaDeck();

		game.setup();
		//		Deck.print();
		while(play & counter < 20){
			game.fightStart();
			System.out.println("\nWho do you believe will win? Player one (1) or Player two (2)");
			actionInt = input.nextInt();		
			game.battle();
			if(actionInt == game.winner){
				wins ++;
				games ++;
			}
			else{
				games ++;
			}
			System.out.println("Continue or Quit");
			action = input.next();
			if(action.equals("Quit")){
				play = false;
				System.out.println("Player one has " + game.winsUser + " wins.");
				System.out.println("Player two has " + game.winsAI +" wins.");
				System.out.println("Your accuracy is " + (wins / games) * 100 + "%");
			}
			counter ++;
		}
		input.close();
	}
}
