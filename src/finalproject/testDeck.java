package finalproject;

public class testDeck {

	public static void main(String [] args){
		Deck deck = new Deck();
		deck.shuffle();
		for(int i = 0; i < 52; i++){
			System.out.println(i + ": " + deck.deal());
		}
	}
}
