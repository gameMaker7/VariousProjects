package Decks;


public class Deck extends CardList{


	public Deck(){
		for(Suit s: Suit.values()){
			for(Rank r: Rank.values()){
				Card card = new Card(s, r);
				add(card);
			}
		}
	}




	public static void main(String[] args){
		Deck deck = new Deck();
		System.out.println(deck);
		System.out.println(deck.scry(10));
		System.out.println("random draw " + deck.randomDraw());
		deck.shuffle();
		System.out.println(deck);
	}
}
