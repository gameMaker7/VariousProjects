package Arena_Battles.Decks;

import java.util.Random;

import Arena_Battles.Armies.ConstructArmy;
import Arena_Battles.Armies.DragonArmy;
import Arena_Battles.Armies.MageArmy;
import Arena_Battles.Armies.WarriorArmy;
import Arena_Battles.PrimaryCards.Cards;
import Arena_Battles.PrimaryCards.Constructs;
import Arena_Battles.PrimaryCards.Dragons;
import Arena_Battles.PrimaryCards.Mages;
import Arena_Battles.PrimaryCards.Warriors;
import Arena_Battles.SecondaryCards.ArmsCard;
import Arena_Battles.SecondaryCards.EquipmentCard;
import Arena_Battles.SecondaryCards.SpellCard;
import Arena_Battles.SecondaryCards.Spells;
import Arena_Battles.SecondaryCards.TraitsCard;
import Arena_Battles.Types.SpellsCompleteFire;
import Arena_Battles.Types.DragonTypes;
import Arena_Battles.Types.MageTypes;
import Arena_Battles.Types.WarriorTypes;



public class ArenaDeck {
	Random gen = new Random(); 
	final int N_CARDS = 98;
	int turns;
	boolean playerturn = false;
	protected Cards[] ArenaCards = new Cards[N_CARDS];
	static ArenaDeck Deck = new ArenaDeck();
	int[] stats1 = {0,0,0,0};
	int[] stats2 = {0,0,0,0};
	int[] sStats1 = {0,0,0,0};
	int[] sStats2 = {0,0,0,0};
	public int winsUser = 0;
	public int winsAI = 0;
	public int winner;

	public ArenaDeck(){
		int n = 0;
		for(DragonTypes type: DragonTypes.values()){
			for(DragonArmy rank: DragonArmy.values()){

				Dragons card = new Dragons(type, rank);
				ArenaCards[n] = card;
				n++;
			}	
		}

		for(SpellsCompleteFire type: SpellsCompleteFire.values()){
			for(ConstructArmy rank: ConstructArmy.values()){

				Constructs card = new Constructs(type, rank);
				ArenaCards[n] = card;
				n++;
			}	
		}
		for(MageTypes type: MageTypes.values()){
			for(MageArmy rank: MageArmy.values()){

				Mages card = new Mages(type, rank);
				ArenaCards[n] = card;
				n++;
			}	
		}
		for(WarriorTypes type: WarriorTypes.values()){
			for(WarriorArmy rank: WarriorArmy.values()){

				Warriors card = new Warriors(type, rank);
				ArenaCards[n] = card;
				n++;
			}	
		}
	}

	public void setup(){
		ArmamentDeck.arms.arms();
		SpellDeck.spells.spells();
		TraitsDeck.traits.traits();
		EquipmentDeck.equip.equip();

		Deck.shuffle();
	}
	public void shuffle(){
		for(int i = 0; i<ArenaCards.length; i++){

			int grab = gen.nextInt(ArenaCards.length);
			int place = gen.nextInt(ArenaCards.length);
			Cards temp = ArenaCards[grab];

			ArenaCards[grab] = ArenaCards[place];
			ArenaCards[place] = temp;

		}

	}


	public Cards draw(){
		Cards[] array = new Cards[ArenaCards.length-1];
		Cards top = ArenaCards[0];
		for(int i = 0 ; i<array.length; i ++){
			array[i] = ArenaCards[i+1];
		}
		ArenaCards = array;
		return top;
	}

	public void fightStart(){
		Cards card1 = Deck.draw();
		Cards card2 = Deck.draw();
		System.out.println(card1 + " VS " + card2);
		Cards special1 = null;
		Cards special2 = null;

		// Card1
		if(card1 instanceof Mages){
			System.out.print("Type: Spellcaster");
			stats1 = ((Mages) card1).getRank().getStats();
			special1 = SpellDeck.spells.spelldraw();
			sStats1 = ((SpellCard) special1).getRank().addon();
		}
		if(card1 instanceof Warriors){
			System.out.print("Type: Warrior");
			stats1 = ((Warriors) card1).getRank().getStats();
			special1 = EquipmentDeck.equip.equipdraw();
			sStats1 = ((EquipmentCard) special1).getRank().addon();

		}
		if(card1 instanceof Dragons){
			System.out.print("Type: Dragon");
			stats1 = ((Dragons) card1).getRank().getStats();
			special1 = TraitsDeck.traits.traitdraw();
			sStats1 = ((TraitsCard) special1).getRank().addon();

		}
		if(card1 instanceof Constructs){
			System.out.print("Type: Construct");
			stats1 = ((Constructs) card1).getRank().getStats();
			special1 = ArmamentDeck.arms.armsdraw();
			sStats1 = ((ArmsCard) special1).getRank().addon();
		}
		stats1[0] += sStats1[0];
		if(stats1[0] <=0){
			stats1[0] = 1;
		}
		stats1[1] += sStats1[1];
		stats1[2] += sStats1[2];
		stats1[3] += (sStats1[3] * gen.nextInt(5) + 1);


		// Card2
		if(card2 instanceof Mages){
			System.out.println("\tType: Spellcaster");
			stats2 = ((Mages) card2).getRank().getStats();
			special2 = SpellDeck.spells.spelldraw();
			sStats2 = ((SpellCard) special2).getRank().addon();

		}
		if(card2 instanceof Warriors){
			System.out.println("\tType: Warrior");
			stats2 = ((Warriors) card2).getRank().getStats();
			special2 = EquipmentDeck.equip.equipdraw();
			sStats2 = ((EquipmentCard) special2).getRank().addon();

		}
		if(card2 instanceof Dragons){
			System.out.println("\tType: Dragon");
			stats2 = ((Dragons) card2).getRank().getStats();
			special2 = TraitsDeck.traits.traitdraw();
			sStats2 = ((TraitsCard) special2).getRank().addon();

		}
		if(card2 instanceof Constructs){
			System.out.println("\tType: Construct");
			stats2 = ((Constructs) card2).getRank().getStats();
			special2 = ArmamentDeck.arms.armsdraw();
			sStats2 = ((ArmsCard) special2).getRank().addon();

		}
		stats2[0] += sStats2[0];
		if(stats2[0] <=0){
			stats2[0] = 1;
		}
		stats2[1] += sStats2[1];
		stats2[2] += sStats2[2];
		stats2[3] += (sStats2[3] * gen.nextInt(5) + 1);
		// stats

		System.out.print("Speed: " + stats1[0]);
		System.out.println("\tSpeed: " + stats2[0]);
		if(stats1[0] > stats2[0]){
			turns = stats1[0]/stats2[0];
			System.out.println("Player 1");
			playerturn = true;
		}
		else {
			System.out.println("Player 2");
			turns = stats2[0]/stats1[0];
		}

		System.out.print("Power: " + stats1[1]);
		System.out.println("\tPower: " + stats2[1]);

		System.out.print("Defense: " + stats1[2]);
		System.out.println("\tDefense: " + stats2[2]);

		System.out.print("Life: " + stats1[3]);
		System.out.println(".	Life: " + stats2[3]);

		System.out.print(special1);
		System.out.println("\t" + special2);


	}

	public void battle(){
		winner = 0;
		int attack = 0;
		int defense = 0;
		while(winner == 0){

			if(playerturn){
				for(int i = 0; i<turns; i ++){
					System.out.println("turn " + (i + 1) + " of " + turns + " turn(s)");
					for(int a = 0; a<stats1[1]; a ++){
						attack += gen.nextInt(6) + 1;
					}
					for(int d = 0; d<stats2[2]; d ++){
						defense += gen.nextInt(8) + 2;
					}
					if(stats1[1] > stats2[2]){

						stats2[3] -= attack - defense;
						System.out.println("Player two takes " + attack + " Damage.\nDefense blocks " + defense + " damage");
						System.out.println("Player 2 has " + stats2[3] + " life left");
						System.out.println();
						attack = 0;
						defense = 0;
						if(stats2[3] <= 0 || stats1[3] >= 100){
							winner = 1;
							winsUser ++;
							continue;
						}
					}
					else {
						System.out.println("Player two takes no damage.");
					}
				}
				for(int a = 0; a<stats2[1]; a ++){
					attack += gen.nextInt(6) + 1;
				}
				for(int d = 0; d<stats1[2]; d ++){
					defense += gen.nextInt(8) + 2;
				}
				if(stats2[1] > stats1[2]){
					stats1[3] -= attack - defense;
					System.out.println("Player one takes " + attack + " Damage.\nDefense blocks " + defense + " damage");
					System.out.println("Player one has " + stats1[3] + " life left");
					System.out.println();
					attack = 0;
					defense = 0;
					if(stats1[3] <= 0 || stats2[3] >= 100){
						winner = 2;
						winsAI ++;
					}
				}
				else {
					System.out.println("Player one takes no damage.");
				}			
			}
			if(!playerturn){
				for(int i = 0; i<turns; i ++){
					System.out.println("turn " + (i + 1) + " of " + turns + " turn(s)");
						for(int a = 0; a<stats2[1]; a ++){
							attack += gen.nextInt(6) + 1;
						}
						for(int d = 0; d<stats1[2]; d ++){
							defense += gen.nextInt(8) + 2;
						}
						if(stats2[1] > stats1[2]){
						stats1[3] -= attack - defense;
						System.out.println("Player one takes " + attack + " Damage.\nDefense blocks " + defense + " damage");
						System.out.println("Player one has " + stats1[3] + " life left");
						System.out.println();
						attack = 0;
						defense = 0;
						if(stats1[3] <= 0 || stats2[3] >= 100){
							winner = 2;
							winsAI ++;
							continue;
						}

					}
					else {
						System.out.println("Player one takes no damage.");
					}
						for(int a = 0; a<stats1[1]; a ++){
							attack += gen.nextInt(6) + 1;
						}
						for(int d = 0; d<stats2[2]; d ++){
							defense += gen.nextInt(8) + 2;
						}
						if(stats1[1] > stats2[2]){
						stats2[3] -= attack - defense;
						System.out.println("Player two takes " + attack + " Damage.\nDefense blocks " + defense + " damage");
						System.out.println("Player 2 has " + stats2[3] + " life left");
						System.out.println();
						if(stats2[3] <= 0 || stats1[3] >= 100){
							winner = 1;
							winsUser ++;	
						}
					}

					else {
						System.out.println("Player two takes no damage.");
					}
				}
			}

		}


		System.out.println("Player " + winner + " wins");
	}
}


