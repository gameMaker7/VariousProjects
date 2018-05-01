package Arena_Battles.SecondaryCards;

import Arena_Battles.PrimaryCards.Cards;


public class ArmsCard extends Cards {
	private Arms rank;
	
	public ArmsCard( Arms rank){
		this.rank = rank;		
	}

	public Arms getRank() {
		return rank;
	}

	@Override
	public String toString(){
		String str = "" + rank;
		return str;
	}


}
