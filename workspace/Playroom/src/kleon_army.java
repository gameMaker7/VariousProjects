
public class kleon_army {

	final static int ARMYCOUNT = 101;
	final static int FAKE = 3;
	
	public static void main(String[] args) {

		int kleonCount = 1;
		int nonKleon = 1;
		int bullets = 0;
			
		for(; kleonCount < ARMYCOUNT; kleonCount++){
			bullets += kleonCount;
			System.out.print("Kleon " + kleonCount + "..");
			if(nonKleon == kleonCount){
				bullets -= kleonCount;
				nonKleon += FAKE;
			}
			else{ System.out.println(kleonCount);
			
			}
			System.out.println("Total bullets " + bullets);
		}
		System.out.println(bullets);
	
	}

}
