package finalproject;

import java.util.ArrayList;

public class Player {

	private Card[] pocketCards;
	ArrayList<Card> commonCards;
	private String name;
	private int money;
	private HandRank bestHand = null;
	
	public Player(String name){
		this.name = name;
		this.money = 0;
		this.commonCards = new ArrayList<Card>();
	}
	
	public Player(String name, int money){
		this.name = name;
		this.money = money;
		this.commonCards = new ArrayList<Card>();
	}
	
	public void setHand(Card c1, Card c2){
		if(c1 == null || c2 == null){
			System.out.println("One of the cards dealt to " + this.name + " is null");
			return;
		}
		pocketCards = new Card[2];
		pocketCards[0] = c1;
		pocketCards[1] = c2;
	}
	
	public void printHand(){
		System.out.println(name +"'s hand: " + pocketCards[0] + " and  " + pocketCards[1]);
	}
	
	public void getBest5CardHand(){
		int numTotalCards = 2 + commonCards.size();
		Card[] totalCards = new Card[numTotalCards];
		for(int i = 0; i < 2; i++){
			totalCards[i] = pocketCards[i];
		}
		for(int i = 0; i < commonCards.size(); i++){
			totalCards[i + 2] = commonCards.get(i);
		}
		
		this.bestHand = new HandRank(totalCards);
	}
	
	public void setCommonCards(ArrayList<Card> commons){
		this.commonCards = commons;
	}
	
	public void printBestHand(){
		System.out.println(bestHand);
	}
	
	public HandRank getHandRank(){
		if(bestHand == null){
			System.err.println("Best hand not yet determined");
			return null;
		}
		return this.bestHand;
	}
	
	public String getName(){
		return this.name;
	}
	
	
}
