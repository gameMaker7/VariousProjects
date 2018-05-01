package Arena_Battles.Decks;

import java.util.Random;

import Arena_Battles.PrimaryCards.Cards;
import Arena_Battles.SecondaryCards.Arms;
import Arena_Battles.SecondaryCards.ArmsCard;

public class ArmamentDeck {
	Random gen = new Random();
	private Arms name;
	protected Cards[] aramentDeck = new Cards[21];
	static ArmamentDeck arms = new ArmamentDeck();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void arms(){ 
		int i = 0;
		for(int j = 0; j<3; j ++)
			for(Arms rank: Arms.values()){
				ArmsCard card = new ArmsCard(rank);
				aramentDeck[i] = card;
				i ++;
			}	
		for(int a = 0; a<aramentDeck.length; a++){

			int grab = gen.nextInt(aramentDeck.length);
			int place = gen.nextInt(aramentDeck.length);
			Cards temp = aramentDeck[grab];

			aramentDeck[grab] = aramentDeck[place];
			aramentDeck[place] = temp;

		}
	}

	public Cards armsdraw(){
		Cards[] array = new Cards[aramentDeck.length-1];
		Cards top = aramentDeck[0];
		for(int i = 0 ; i<array.length; i ++){
			array[i] = aramentDeck[i+1];
		}
		aramentDeck = array;
		return top;
	}
}
