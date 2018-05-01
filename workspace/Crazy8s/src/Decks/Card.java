package Decks;

import java.util.Hashtable;

public class Card {

	private Suit suit;
	private Rank rank;

	public Card(Suit suit, Rank rank){
		this.suit = suit;
		this.rank = rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}
	

	@Override
	public String toString() {
		return rank + " of " + suit;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
	public static void main(String[] args){
		Card one = new Card(Suit.SPADES, Rank.JACK);
		Card two = new Card(Suit.SPADES, Rank.JACK);
		Card three = new Card(Suit.DIAMONDS, Rank.JACK);
		boolean compare = one.equals(two);
		System.out.println("Card one compared to Card two " + compare);
		compare = one.equals(three);
		System.out.println("Card one compared to Card three " + compare);
		
		Hashtable<String, Card> tab = new Hashtable<String, Card>();
		tab.put("fred", one);
		tab.put("george", two);
		tab.put("steve", three);
		Card found = tab.get("bill");
		System.out.println("Fred got " + found);
		found = tab.get("steve");
		System.out.println("Steve got " + found);
		
		
		
		
		
	}
}
