
import java.util.Random;
import java.util.Scanner;

public class arraysProject {

	final static int DRAWS = 100;

	public static void main(String[] args) {

		Random gen = new Random();


		Scanner input = new Scanner(System.in);
		System.out.println("Enter - suit ");
		String type = input.next();
		System.out.println("Enter - card ");
		String value = input.next();
		String finished = value + " of " + type;
		int number = 0;
		String[] suits = {"Hearts", "Spades", "Diamonds", "Clubs"};
		String[] rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
		String[] deck = new String[suits.length * rank.length];

		int cardname = 0;
		for(String suit: suits){
			for(String card: rank){
				deck[cardname] = card + " of " + suit;
				cardname ++;
			}

		}




		for(int i = 0; i<100; i ++){
//			System.out.println(deck[0]);
			if(deck[0].equals(finished)){
				number ++;
			}

			for(int s = 0; s<deck.length; s++){
				int firstSlot = gen.nextInt(deck.length);
				int secondSlot = gen.nextInt(deck.length);
				
				String savedCard = deck[firstSlot];
				deck[firstSlot] = deck[secondSlot];
				deck[secondSlot] = savedCard;
			}

		}		

		System.out.println("Your card was drawed " + number + " times.");

	}
}
