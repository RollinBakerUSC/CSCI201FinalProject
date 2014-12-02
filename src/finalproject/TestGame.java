package finalproject;

import java.util.ArrayList;

public class TestGame {

	
	public static void main(String [] args){
		
		
	/*	ArrayList<HandRank>players = new ArrayList<HandRank>();
//		Player rollin = new Player("Rollin");
//		Player dan = new Player("Dan");
//		Player kash = new Player("Jordan");
		
		
		Deck deck = new Deck();
		deck.shuffle();
		
//		rollin.setHand(new Card(2, Card.Suit.CLUBS), new Card(2, Card.Suit.SPADES));
//		dan.setHand(new Card(2, Card.Suit.HEARTS), new Card(2, Card.Suit.DIAMONDS));
//		kash.setHand(new Card(5, Card.Suit.DIAMONDS), new Card(8, Card.Suit.SPADES));
		rollin.setHand(deck.deal(), deck.deal());
		dan.setHand(deck.deal(), deck.deal());
		kash.setHand(deck.deal(), deck.deal());
		rollin.printHand();
		dan.printHand();
		kash.printHand();
		
		//Deal flop
		ArrayList<Card> commons = new ArrayList<Card>();
		commons.add(deck.deal());
		commons.add(deck.deal());
		commons.add(deck.deal());
		commons.add(deck.deal());
		commons.add(deck.deal());
//		commons.add(new Card(Card.ACE, Card.Suit.CLUBS));
//		commons.add(new Card(Card.KING, Card.Suit.CLUBS));
//		commons.add(new Card(Card.QUEEN, Card.Suit.CLUBS));
//		commons.add(new Card(Card.JACK, Card.Suit.CLUBS));
//		commons.add(new Card(10, Card.Suit.CLUBS));
		
		for(int i = 0; i < commons.size(); i++){
			System.out.println(commons.get(i));
		}
		
		rollin.setCommonCards(commons);
		dan.setCommonCards(commons);
		kash.setCommonCards(commons);
		
		rollin.getBest5CardHand();
		dan.getBest5CardHand();
		kash.getBest5CardHand();
		
		players.add(rollin.getHandRank());
		players.add(dan.getHandRank());
		players.add(kash.getHandRank());
		ArrayList<HandRank> winners = CompareHands.getWinningPlayer(players);
		
		if(winners.size() == 1){
			System.out.println(winners.get(0).getPlayerName() + " wins with a " + winners.get(0));
		}
		else{
			System.out.println("Split Pot!");
			for(int i = 0; i < winners.size(); i++){
				
				System.out.print(winners.get(i).getPlayerName() + " ");
				if(i == winners.size() - 1){
					System.out.println(" with a " + winners.get(i));
				}
			}
		}*/
	}
}
