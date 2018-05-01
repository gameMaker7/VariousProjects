package Arena_Battles.PrimaryCards;

import Arena_Battles.Armies.DragonArmy;
import Arena_Battles.Types.DragonTypes;


public class Dragons extends Cards {
	private DragonTypes type;
	private DragonArmy rank;
	
	public Dragons(DragonTypes type, DragonArmy rank){
		this.type = type;
		this.rank = rank;		
	}

	public DragonTypes getType(){
		return type;	
	}

	public DragonArmy getRank() {
		return rank;
	}

	@Override
	public String toString(){
		String str = type + " " + rank;
		return str;
	}
}
