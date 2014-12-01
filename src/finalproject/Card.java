package finalproject;

public class Card {
	
	public enum Suit {CLUBS, SPADES, HEARTS, DIAMONDS}
	private final Suit suit;
	private final int value;
	
	//Values for non-numeric cards
	//All other cards have their values defined by their card type
	public final static int ACE = 1;      
	public final static int JACK = 11;   
	public final static int QUEEN = 12;   
	public final static int KING = 13;
	
	
	//Creates a card with specified suit and value
	public Card(int val, Suit suit){
		if(val > 13 || val < 1){
			System.out.println("Illegal card value ERROR");
		}
		this.suit = suit;
		this.value = val;
	}
	
	public Card(int val, String suit){
		if(val > 13 || val < 1){
			System.out.println("Illegal card value ERROR");
		}
		this.value = val;
		if(suit.equals("Hearts")){
			this.suit = Suit.HEARTS;
		}
		else if(suit.equals("Clubs")){
			this.suit = Suit.CLUBS;
		}
		else if(suit.equals("Spades")){
			this.suit = Suit.SPADES;
		}
		else if(suit.equals("Diamonds")){
			this.suit = Suit.DIAMONDS;
		}
		else{
			System.err.println("Illegal suit passed into card constructor");
			System.out.println(suit);
			this.suit = null;
		}
	}
	
	//Returns suit in form of enum
	public Suit getSuit(){
		return this.suit;
	}
	
	//Returns value in form of an int
	public int getValue(){
		return this.value;
	}
	
	
	//Returns string representation of the suit
	public String getSuitAsString(){
		switch(suit){
		case CLUBS:
			return "Clubs";
		case SPADES:
			return "Spades";
		case HEARTS:
			return "Hearts";
		default:
			return "Diamonds";
		}
	}
	
	//Returns string representation of value
	public String getValueAsString(){
		switch(value){
		case 1:   
			return "Ace";
        case 2:   
        	return "2";
        case 3:   
        	return "3";
        case 4:   
        	return "4";
        case 5:   
        	return "5";
        case 6:   
        	return "6";
        case 7:   
        	return "7";
        case 8:   
        	return "8";
        case 9:   
        	return "9";
        case 10:  
        	return "10";
        case 11:  
        	return "Jack";
        case 12:  
        	return "Queen";
        default:  
        	return "King";
        
		}
	}
	
	public String toString(){
		return getValueAsString() + " of " + getSuitAsString();
	}
}
