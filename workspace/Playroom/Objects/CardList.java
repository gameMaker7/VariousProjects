

import java.util.ArrayList;
import java.util.Random;

public class CardList {

	public static Random gen = new Random();
	protected ArrayList<Object> list  = new ArrayList<Object>();
	public CardList(){
		
	}
	public void add(Object obj){
		list.add(obj);
	}
	public Object draw(int i){
		return list.remove(i-1);
	}
	public Object scry(int i){
		return list.get(i-1);
	}
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		for(Object c: list){
			result.append(c + "\n");
		}
		return result.toString();
	}
	public void shuffle(){
		for(int i = 0; i<list.size(); i++){

			int grab = gen.nextInt(list.size());
			int place = gen.nextInt(list.size());
			Object temp = list.get(grab);

			list.set(grab, list.get(place));
			list.set(place, temp);
		}
	}
	public Object randomDraw(){
		return draw(gen.nextInt(list.size()));
	}
}
