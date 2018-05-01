package Arena_Battles.Armies;

public enum WarriorArmy {

	Page, Recruit, Knight, Guardian, BattleMaster, Champion, Hero;



	public int[] getStats(){

//		int speed = 0; // 10 point system
//		int power = 0; // 10 point system
//		int defense = 0; // 5 point system
//		int health = 0; // 10 point system
		int[] Stats = {0,0,0,0};
		switch(this){
		case Page:
			Stats[1] = 2;
			Stats[0] = 8;
			Stats[2] = 1;
			Stats[3] = 4;
			// 15
			break;
		case Recruit: 
			Stats[2] = 2;
			Stats[1] = 3;
			Stats[0] = 1;
			Stats[3] = 4;
			// 10
			break;
		case Knight:
			Stats[1] = 3;
			Stats[2] = 3;
			Stats[0] = 3;
			Stats[3] = 6;
			// 15
			break;
		case Guardian:
			Stats[1] = 4;
			Stats[0] = 4;
			Stats[2] = 4;
			Stats[3] = 7;
			// 19
			break;
		case BattleMaster:
			Stats[0] = 5;
			Stats[2] = 2;
			Stats[1] = 6;
			Stats[3] = 8;
			// 21
			break;
		case Champion:
			Stats[0] = 6;
			Stats[2] = 3;
			Stats[1] = 8;
			Stats[3] = 9;
			// 21
			break;
		case Hero:
			Stats[0] = 10;
			Stats[2] = 5;
			Stats[1] = 10;
			Stats[3] = 10;
		}
		return Stats;
	}
}
