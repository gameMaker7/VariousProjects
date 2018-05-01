package Arena_Battles.SecondaryCards;

public enum Traits {

	Trickery, Honor, Wisdom,  Courage, Patience, Decisive,  Aura;


	public int[] addon(){
		int [] Stats = {0,0,0,0};

		switch(this){
		case Trickery:
			Stats[1] = 2;
			Stats[0] = 2;
			Stats[2] = 0;
			Stats[3] = 0;
			// 15
			break;
		case Honor: 
			Stats[2] = 2;
			Stats[1] = 1;
			Stats[0] = 0;
			Stats[3] = 1;
			// 10
			break;
		case Wisdom:
			Stats[1] = 1;
			Stats[2] = 1;
			Stats[0] = 0;
			Stats[3] = 2;
			// 15
			break;
		case Courage:
			Stats[1] = 1;
			Stats[0] = 1;
			Stats[2] = 0;
			Stats[3] = 0;
			// 19
			break;
		case Patience:
			Stats[0] = -3;
			Stats[2] = 2;
			Stats[1] = 1;
			Stats[3] = 2;
			// 17
			break;
		case Decisive:
			Stats[0] = 4;
			Stats[2] = -1;
			Stats[1] = 3;
			Stats[3] = 0;
			// 21
			break;
		case Aura:
			Stats[0] = 1;
			Stats[2] = 1;
			Stats[1] = 1;
			Stats[3] = 1;
		}
		return Stats;
	}}
