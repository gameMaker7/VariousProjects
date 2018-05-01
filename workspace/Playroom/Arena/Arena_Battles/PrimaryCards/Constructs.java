package Arena_Battles.PrimaryCards;

import Arena_Battles.Armies.ConstructArmy;
import Arena_Battles.Types.SpellsCompleteFire;



public class Constructs extends Cards {

	private SpellsCompleteFire type;
	private ConstructArmy rank;
	
	public Constructs(SpellsCompleteFire type, ConstructArmy rank){
		this.type = type;
		this.rank = rank;		
	}

	public SpellsCompleteFire getType(){
		return type;	
	}

	public ConstructArmy getRank() {
		return rank;
	}

	@Override
	public String toString(){
		String str = type + " " + rank;
		return str;
	}
	
}
