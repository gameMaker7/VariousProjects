package Arena_Battles.Decks;

import java.util.Random;

import Arena_Battles.PrimaryCards.Cards;
import Arena_Battles.SecondaryCards.Equipment;
import Arena_Battles.SecondaryCards.EquipmentCard;
import Arena_Battles.SecondaryCards.SpellCard;
import Arena_Battles.SecondaryCards.Spells;
import Arena_Battles.SecondaryCards.Traits;
import Arena_Battles.SecondaryCards.TraitsCard;

public class EquipmentDeck {
	Random gen = new Random();
	private Equipment name;
	protected Cards[] eqipmentDeck = new Cards[21];
	static EquipmentDeck equip = new EquipmentDeck();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void equip(){ 
		int i = 0;
		for(int j = 0; j<3; j ++)
			for(Equipment rank: Equipment.values()){
				EquipmentCard card = new EquipmentCard(rank);
				eqipmentDeck[i] = card;
				i ++;
			}	
		for(int a = 0; a<eqipmentDeck.length; a++){

			int grab = gen.nextInt(eqipmentDeck.length);
			int place = gen.nextInt(eqipmentDeck.length);
			Cards temp = eqipmentDeck[grab];

			eqipmentDeck[grab] = eqipmentDeck[place];
			eqipmentDeck[place] = temp;

		}
	}

	public Cards equipdraw(){
		Cards[] array = new Cards[eqipmentDeck.length-1];
		Cards top = eqipmentDeck[0];
		for(int i = 0 ; i<array.length; i ++){
			array[i] = eqipmentDeck[i+1];
		}
		eqipmentDeck = array;
		return top;
	}
}
