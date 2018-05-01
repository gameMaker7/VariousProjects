package Decks;

public enum Rank {

	ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;

	public boolean isFaceCard(){
		return (this == JACK || this == QUEEN || this == KING);
	}
}
