package Arena_Battles.SecondaryCards;

import Arena_Battles.PrimaryCards.Cards;


public class TraitsCard extends Cards {
	private Traits rank;
	
	public TraitsCard( Traits rank){
		this.rank = rank;		
	}

	public Traits getRank() {
		return rank;
	}

	@Override
	public String toString(){
		String str = "" + rank;
		return str;
	}


}
