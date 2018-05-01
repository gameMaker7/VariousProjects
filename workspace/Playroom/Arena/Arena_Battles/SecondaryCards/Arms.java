package Arena_Battles.SecondaryCards;

public enum Arms {

	Balista, Thrusters, FortressArmor, IonCore, Magnetics, AirStrike,  Upgrades;


	public int[] addon(){
		int [] Stats = {0,0,0,0};

		switch(this){
		case Balista:
			Stats[1] = 2;
			Stats[0] = -1;
			Stats[2] = 0;
			Stats[3] = 0;
			// 15
			break;
		case Thrusters: 
			Stats[2] = 0;
			Stats[1] = 0;
			Stats[0] = 1;
			Stats[3] = 0;
			// 10
			break;
		case FortressArmor:
			Stats[1] = 1;
			Stats[2] = 3;
			Stats[0] = -4;
			Stats[3] = 2;
			// 15
			break;
		case IonCore:
			Stats[1] = 1;
			Stats[0] = 1;
			Stats[2] = 1;
			Stats[3] = 1;
			// 19
			break;
		case Magnetics:
			Stats[0] = 1;
			Stats[2] = 1;
			Stats[1] = 0;
			Stats[3] = 2;
			// 17
			break;
		case AirStrike:
			Stats[0] = 1;
			Stats[2] = 0;
			Stats[1] = 2;
			Stats[3] = 0;
			// 21
			break;
		case Upgrades:
			Stats[0] = 1;
			Stats[2] = 1;
			Stats[1] = 1;
			Stats[3] = 1;
		}
		return Stats;
	}
}
