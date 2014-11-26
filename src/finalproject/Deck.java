package finalproject;


//Represents a standard 52 card deck of playing cards 
//There are 4 suits, and 13 card types (Ace through King), with no jokers

public class Deck {

	//Will be initialized to an array of 52 cards
	private Card[] deck;
	
	//Tracks the number of cards dealt from the deck
	private int cardsDealt;
	
	
	//Creates a deck a deck of cards in sorted order
	public Deck(){
		deck = new Card[52];
		int numCards = 0;
		
		 for (int suitNum = 0; suitNum < 4; suitNum++ ) {
	            for (int cardVal = 1; cardVal < 14; cardVal++) {
	            	switch(suitNum){
	            	case 0:
	            		deck[numCards] = new Card(cardVal, Card.Suit.CLUBS);
	            		break;
	            	case 1:
	            		deck[numCards] = new Card(cardVal, Card.Suit.SPADES);
	            		break;
	            	case 2:
	            		deck[numCards] = new Card(cardVal, Card.Suit.HEARTS);
	            		break;
	            	case 3:
	            		deck[numCards] = new Card(cardVal, Card.Suit.DIAMONDS);
	            		break;
	            	}
	                
	                numCards++;
	            }
	        }
		 this.cardsDealt = 0;
	}
	
	
	//Shuffles the deck. Since the deck will only be shuffled at the end of a hand,
	//this function returns all cards to the deck (by setting cardsDealt to 0)
	public void shuffle() {
        for ( int i = deck.length-1; i > 0; i-- ) {
            int randVal = (int)(Math.random()*(i+1));
            Card tempCard = deck[i];
            deck[i] = deck[randVal];
            deck[randVal] = tempCard;
        }
        cardsDealt = 0;
    }
	
	public Card deal(){
		if (cardsDealt == 52){
			System.out.println("Deck is empty");
			return null;
		}
		
        return deck[cardsDealt++]; 
	}
	
	public int numCardsRemaining() {
        return 52 - cardsDealt;
    }
}
