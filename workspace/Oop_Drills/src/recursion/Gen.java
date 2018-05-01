package recursion;

import java.util.Random;


public class Gen {

	public static Random gen = new Random();

	public int dice(int nDice, int sides, int bonus){
		int x = 0;
		for(int i = 0; i<nDice; i ++){
			x = gen.nextInt(sides) + bonus + 1;
		}
		return x;

	}
}