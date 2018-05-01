package Arena_Battles.SecondaryCards;

import Arena_Battles.PrimaryCards.Cards;


public class SpellCard extends Cards {
	private Spells rank;
	
	public SpellCard( Spells rank){
		this.rank = rank;		
	}

	public Spells getRank() {
		return rank;
	}

	@Override
	public String toString(){
		String str = "" + rank;
		return str;
	}


}
