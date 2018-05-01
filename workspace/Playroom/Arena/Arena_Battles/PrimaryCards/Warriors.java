package Arena_Battles.PrimaryCards;

import Arena_Battles.Armies.WarriorArmy;
import Arena_Battles.Types.WarriorTypes;


public class Warriors extends Cards{
	private WarriorTypes type;
	private WarriorArmy rank;
	
	public Warriors(WarriorTypes type, WarriorArmy rank){
		this.type = type;
		this.rank = rank;		
	}

	public WarriorTypes getType(){
		return type;	
	}

	public WarriorArmy getRank() {
		return rank;
	}

	@Override
	public String toString(){
		String str = type + " " + rank;
		return str;
	}
}
