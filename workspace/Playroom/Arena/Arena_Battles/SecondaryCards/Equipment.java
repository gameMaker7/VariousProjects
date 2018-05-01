package Arena_Battles.SecondaryCards;

public enum Equipment {

	Legion, Calvary, Supply, Intel, Logistics, Strategist,  Blacksmith;

	public int[] addon(){
		int [] Stats = {0,0,0,0};

		switch(this){
		case Legion:
			Stats[1] = 2;
			Stats[0] = 0;
			Stats[2] = 1;
			Stats[3] = 0;
			// 15
			break;
		case Calvary: 
			Stats[2] = 0;
			Stats[1] = 1;
			Stats[0] = 2;
			Stats[3] = 0;
			// 10
			break;
		case Supply:
			Stats[1] = 1;
			Stats[2] = 1;
			Stats[0] = 0;
			Stats[3] = 2;
			// 15
			break;
		case Intel:
			Stats[1] = 1;
			Stats[0] = 1;
			Stats[2] = 1;
			Stats[3] = 1;
			// 19
			break;
		case Logistics:
			Stats[0] = 2;
			Stats[2] = 0;
			Stats[1] = 0;
			Stats[3] = 1;
			// 17
			break;
		case Strategist:
			Stats[0] = 1;
			Stats[2] = 1;
			Stats[1] = 2;
			Stats[3] = 2;
			// 21
			break;
		case Blacksmith:
			Stats[0] = 0;
			Stats[2] = 1;
			Stats[1] = 1;
			Stats[3] = 0;
		}
		return Stats;
	}
}
