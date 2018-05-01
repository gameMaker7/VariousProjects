package around;

import java.util.ArrayList;
import java.util.List;


public class Main {
	private static int NFISH = 10;
	private static int NROCKS = 5;


	public static void main(String[] args) {
		makeFish();

	}

	private static void makeFish() {
		ArrayList<Submercible> obj = new ArrayList<Submercible>();
		for(int i = 0; i<NFISH; i ++){
			Fish fish = new Fish();
			obj.add(fish);
		}
		for(int i = 0; i<NROCKS; i ++){
			Rocks rock = new Rocks();
			obj.add(rock);
		}
		submerge(obj);
	}
	public static void submerge(ArrayList<Submercible> obj){

		for(Submercible f: obj){
			f.submerge();
			f.reveal();
		}
	}
}
