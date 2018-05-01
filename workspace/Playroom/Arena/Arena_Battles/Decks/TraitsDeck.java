package Arena_Battles.Decks;

import java.util.Random;

import Arena_Battles.PrimaryCards.Cards;
import Arena_Battles.SecondaryCards.SpellCard;
import Arena_Battles.SecondaryCards.Spells;
import Arena_Battles.SecondaryCards.Traits;
import Arena_Battles.SecondaryCards.TraitsCard;

public class TraitsDeck {
	Random gen = new Random();
	private Traits name;
	protected Cards[] traitDeck = new Cards[21];
	static TraitsDeck traits = new TraitsDeck();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void traits(){ 
		int i = 0;
		for(int j = 0; j<3; j ++)
			for(Traits rank: Traits.values()){
				TraitsCard card = new TraitsCard(rank);
				traitDeck[i] = card;
				i ++;
			}	
		for(int a = 0; a<traitDeck.length; a++){

			int grab = gen.nextInt(traitDeck.length);
			int place = gen.nextInt(traitDeck.length);
			Cards temp = traitDeck[grab];

			traitDeck[grab] = traitDeck[place];
			traitDeck[place] = temp;

		}
	}

	public Cards traitdraw(){
		Cards[] array = new Cards[traitDeck.length-1];
		Cards top = traitDeck[0];
		for(int i = 0 ; i<array.length; i ++){
			array[i] = traitDeck[i+1];
		}
		traitDeck = array;
		return top;
	}
}
