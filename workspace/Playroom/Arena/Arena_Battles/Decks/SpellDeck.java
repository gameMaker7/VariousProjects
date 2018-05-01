package Arena_Battles.Decks;

import java.util.Random;

import Arena_Battles.PrimaryCards.Cards;
import Arena_Battles.SecondaryCards.SpellCard;
import Arena_Battles.SecondaryCards.Spells;

public class SpellDeck {
	Random gen = new Random();
	private Spells name;
	protected Cards[] SpellDeck = new Cards[21];
	static SpellDeck spells = new SpellDeck();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void spells(){ 
		int i = 0;
		for(int j = 0; j<3; j ++)
			for(Spells rank: Spells.values()){
				SpellCard card = new SpellCard(rank);
				SpellDeck[i] = card;
				i ++;
			}	
		for(int a = 0; a<SpellDeck.length; a++){

			int grab = gen.nextInt(SpellDeck.length);
			int place = gen.nextInt(SpellDeck.length);
			Cards temp = SpellDeck[grab];

			SpellDeck[grab] = SpellDeck[place];
			SpellDeck[place] = temp;

		}
	}

	public Cards spelldraw(){
		Cards[] array = new Cards[SpellDeck.length-1];
		Cards top = SpellDeck[0];
		for(int i = 0 ; i<array.length; i ++){
			array[i] = SpellDeck[i+1];
		}
		SpellDeck = array;
		return top;
	}
}
