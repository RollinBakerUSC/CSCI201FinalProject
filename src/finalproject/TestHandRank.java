package finalproject;

public class TestHandRank {

	public static void main(String[] args){
		Card [] hand = new Card[7];
		hand[0] = new Card(Card.QUEEN, Card.Suit.CLUBS);
		hand[1] = new Card(Card.ACE, Card.Suit.CLUBS);
		hand[2] = new Card(Card.ACE, Card.Suit.DIAMONDS);
		hand[3] = new Card(6, Card.Suit.HEARTS);
		hand[4] = new Card(Card.QUEEN, Card.Suit.HEARTS);
		hand[5] = new Card(8, Card.Suit.HEARTS);
		hand[6] = new Card(3, Card.Suit.DIAMONDS);
		
		HandRank hr = new HandRank(hand);
		System.out.println("Best hand is: " + hr );
	}
}
