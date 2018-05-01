package Arena_Battles.SecondaryCards;

public enum Spells {

	Fireball, Familiar, Teleport, SoulStone, ManaCharge, FrostStorm, NaturalState; 

	public int[] addon(){
		int [] Stats = {0,0,0,0};

		switch(this){
		case Fireball:
			Stats[1] = 3;
			Stats[0] = 0;
			Stats[2] = 0;
			Stats[3] = 0;
			// 15
			break;
		case Familiar: 
			Stats[2] = 0;
			Stats[1] = 1;
			Stats[0] = 0;
			Stats[3] = 4;
			// 10
			break;
		case Teleport:
			Stats[1] = 0;
			Stats[2] = 1;
			Stats[0] = 2;
			Stats[3] = 0;
			// 15
			break;
		case SoulStone:
			Stats[1] = 1;
			Stats[0] = 0;
			Stats[2] = 0;
			Stats[3] = 4;
			// 19
			break;
		case ManaCharge:
			Stats[0] = 1;
			Stats[2] = 1;
			Stats[1] = 1;
			Stats[3] = 0;
			// 17
			break;
		case FrostStorm:
			Stats[0] = 2;
			Stats[2] = 1;
			Stats[1] = 3;
			Stats[3] = -1;
			// 21
			break;
		case NaturalState:
			Stats[0] = 1;
			Stats[2] = 1;
			Stats[1] = 1;
			Stats[3] = 1;
		}
		return Stats;
	}
}
