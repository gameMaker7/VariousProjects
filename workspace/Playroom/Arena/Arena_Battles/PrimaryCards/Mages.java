package Arena_Battles.PrimaryCards;

import Arena_Battles.Armies.MageArmy;
import Arena_Battles.Types.MageTypes;


public class Mages extends Cards{
	private MageTypes type;
	private MageArmy rank;
	
	public Mages(MageTypes type, MageArmy rank){
		this.type = type;
		this.rank = rank;		
	}

	public MageTypes getType(){
		return type;	 
	}

	public MageArmy getRank() {
		return rank;
	}

	@Override
	public String toString(){
		String str = type + " " + rank;
		return str;
	}
}
