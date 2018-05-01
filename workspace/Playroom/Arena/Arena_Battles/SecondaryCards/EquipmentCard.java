package Arena_Battles.SecondaryCards;

import Arena_Battles.PrimaryCards.Cards;


public class EquipmentCard extends Cards {
	private Equipment rank;
	
	public EquipmentCard( Equipment rank){
		this.rank = rank;		
	}

	public Equipment getRank() {
		return rank;
	}

	@Override
	public String toString(){
		String str = "" + rank;
		return str;
	}


}
