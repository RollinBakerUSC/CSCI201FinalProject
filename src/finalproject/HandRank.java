package finalproject;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


//This class takes in a card array and produces an object with a hand type and high card value
public class HandRank implements Comparable {
	public enum HandType {HIGHCARD, ONEPAIR, TWOPAIR, THREEOFAKIND, STRAIGHT, FLUSH, FULLHOUSE,
					FOUROFAKIND, STRAIGHTFLUSH, ROYALFLUSH}
	
	private HandType handType;
	
	//14 indicates Ace-high
	private int highCardRank = 0;
	private int secondHighCard = 0;
	private int thirdHighCard = 0;
	private int fourthHighCard = 0;
	private int fifthHighCard = 0;
	private Card[] hand;
	private int handSize;
	private ArrayList<Integer> cardValues;
	private ArrayList<Card.Suit> cardSuits;
	
	//For intermediate hand processing, will be used once best possible hand has been narrowed
	private ArrayList<Integer> targetCardValues;
	
	//ArrayList consisting of players hand plus all common cards will be passed in
	//All values needed for hand comparison will be generated in constructor
	public HandRank(Card [] hand){	
		this.hand = hand;
		this.handSize = hand.length;
		this.highCardRank = 0;
		cardValues = new ArrayList<Integer>();
		cardSuits = new ArrayList<Card.Suit>();
		for(int i = 0; i < handSize; i++){
			cardValues.add(new Integer(hand[i].getValue()));
			cardSuits.add(hand[i].getSuit());
		}
		//Check for hand possibility in order of best to worst
		//Check for Royal Flush
		if (!checkForRoyalFlush()){
			if(!checkForStraightFlush()){
				if(!checkForFourOfaKind()){
					if(!checkForFullHouse()){
						if(!checkForFlush()){
							if(!checkForStraight()){
								if(!checkForThreeOfaKind()){
									if(!checkForTwoPair()){
										if(!checkForOnePair()){
											checkForHighCard();
										}
									}
								}
							}
						}
					}
				}
			}
		}	
	}
	
	/*
	 * Every checkFor...() function has a corresponding evaluate...() function.
	 * checkFor() will verify the minimum conditions required for that hand are met
	 * evaluate() will actually check the cards and determine if it contains the hand
	 * 		If so, evaluate() updates the local variables to reflect the best hand
	 * 			note: evaluate() may take a subset of the hand as an argument to make for simpler comparison
	 * 		
	 */
	
	
	//Returns true if hand contains a royal flush, false otherwise
	private boolean checkForRoyalFlush(){
		
		//Make sure there are enough cards for a royal flush (min of 5)
		if(handSize < 5){
			return false;
		}
		
		//Makes sure at least five cards are the same suit
		if(fiveCardsAreSameSuit() == null){
			return false;
		}
		
		//Now checks for necessary cards
		return evaluateRoyalFlush(targetCardValues);
	}
	
	//Assumes you have already checked for proper hand size and all cards as same suit
	//If true, sets values of local variables
	private boolean evaluateRoyalFlush(ArrayList<Integer> sameSuitValues){
		if(sameSuitValues.size() < 5){
			System.err.println("Error in evaluating royal flush, target hand is of improper size");
			return false;
		}
		if(sameSuitValues.contains(Card.ACE) && sameSuitValues.contains(Card.KING) && sameSuitValues.contains(Card.QUEEN)
				&& sameSuitValues.contains(Card.JACK) && sameSuitValues.contains(10)){
			this.handType = HandType.ROYALFLUSH;
			this.highCardRank = 14;
			return true;
		}
		else{
			return false;
		}
	}
	
	//Returns true if hand contains a straight flush and updates local variables if so (within evaluateRoyalFlush())
	//At this point will have already determined if hand has 5 cards (from checkforRoyalFlush)
	private boolean checkForStraightFlush(){
		//checks if there are enough cards for a straight flush
		if(handSize < 5){
			return false;
		}
		
		//Makes sure all cards are same suit
		Card.Suit targetSuit = fiveCardsAreSameSuit();
		if(targetSuit == null){
			return false;
		}
		
		//Now checks for necessary cards
		return evaluateStraightFlush(targetCardValues);
	}
	
	//Assume it has already checked for proper number of same suit cards
	//Returns true if hand subset contains a straight flush
	private boolean evaluateStraightFlush(ArrayList<Integer> sameSuitValues){
		//Check by high card of straight
		
		//Check for ace high straight
		if(sameSuitValues.contains(Card.ACE)){
			if(sameSuitValues.contains(Card.KING) && sameSuitValues.contains(Card.QUEEN)
					&& sameSuitValues.contains(Card.JACK) && sameSuitValues.contains(10)){
				
				this.handType = HandType.STRAIGHTFLUSH;
				this.highCardRank = 14;
				return true;
			}
		}
		
		//Check for king high straight
		if(sameSuitValues.contains(Card.KING)){
			if(sameSuitValues.contains(Card.QUEEN) && sameSuitValues.contains(Card.JACK) 
					&& sameSuitValues.contains(10) && sameSuitValues.contains(9)){
				
				this.handType = HandType.STRAIGHTFLUSH;
				this.highCardRank = Card.KING;
				return true;
			}
		}
		
		//Check for queen high straight
		if(sameSuitValues.contains(Card.QUEEN)){
			if(sameSuitValues.contains(Card.JACK) && sameSuitValues.contains(10) 
					&& sameSuitValues.contains(9) && sameSuitValues.contains(8)){
				
				this.handType = HandType.STRAIGHTFLUSH;
				this.highCardRank = Card.QUEEN;
				return true;
			}
		}
		
		//Check for jack high straight flush
		if(sameSuitValues.contains(Card.JACK)){
			if(sameSuitValues.contains(10) && sameSuitValues.contains(9) 
					&& sameSuitValues.contains(8) && sameSuitValues.contains(7)){
				
				this.handType = HandType.STRAIGHTFLUSH;
				this.highCardRank = Card.JACK;
				return true;
			}
		}
		
		//Check for 10 high straight flush
		if(sameSuitValues.contains(10)){
			if(sameSuitValues.contains(9) && sameSuitValues.contains(8) 
					&& sameSuitValues.contains(7) && sameSuitValues.contains(6)){
				
				this.handType = HandType.STRAIGHTFLUSH;
				this.highCardRank = 10;
				return true;
			}
		}
		//Check for 9 high straight flush
		if(sameSuitValues.contains(9)){
			if(sameSuitValues.contains(8) && sameSuitValues.contains(7) 
					&& sameSuitValues.contains(6) && sameSuitValues.contains(5)){
				
				this.handType = HandType.STRAIGHTFLUSH;
				this.highCardRank = 9;
				return true;
			}
		}
		
		//Check for 8 high straight flush
		if(sameSuitValues.contains(8)){
			if(sameSuitValues.contains(7) && sameSuitValues.contains(6) 
					&& sameSuitValues.contains(5) && sameSuitValues.contains(4)){
				
				this.handType = HandType.STRAIGHTFLUSH;
				this.highCardRank = 8;
				return true;
			}
		}
		
		//Check for 7 high straight flush
		if(sameSuitValues.contains(7)){
			if(sameSuitValues.contains(6) && sameSuitValues.contains(5) 
					&& sameSuitValues.contains(4) && sameSuitValues.contains(3)){
				
				this.handType = HandType.STRAIGHTFLUSH;
				this.highCardRank = 7;
				return true;
			}
		}	
		
		//Check for 6 high straight flush
		if(sameSuitValues.contains(6)){
			if(sameSuitValues.contains(5) && sameSuitValues.contains(4) 
					&& sameSuitValues.contains(3) && sameSuitValues.contains(2)){
				
				this.handType = HandType.STRAIGHTFLUSH;
				this.highCardRank = 6;
				return true;
			}
		}	
		
		//Check for 5 high straight flush
		if(sameSuitValues.contains(5)){
			if(sameSuitValues.contains(4) && sameSuitValues.contains(3) 
					&& sameSuitValues.contains(2) && sameSuitValues.contains(Card.ACE)){
				
				this.handType = HandType.STRAIGHTFLUSH;
				this.highCardRank = 5;
				return true;
			}
		}	
		
		//if you made it to here, there is no straight flush
		return false;
	}
	
	//Returns true if hand contains four of a kind and updates local variables 
	//within evaluateFourOfaKind() if so
	private boolean checkForFourOfaKind(){
		if(handSize < 5){
			return false;
		}
		int numOccurrences = 0; 
		for(int i = 1; i < 14; i++){
			numOccurrences = Collections.frequency(cardValues, i);
			if(numOccurrences >= 4){
				if(i == 1){ //4 Aces
					return evaluateFourOfaKind(14);
				}
				else{
					return evaluateFourOfaKind(i);
				}
			}
		}
		//If here, there is no four of a kind 
		return false;
	}
	
	//Have already verified four of a kind for given cardVal
	//Updates local variables and returns true (always)
	private boolean evaluateFourOfaKind(int cardVal){
		this.handType = HandType.FOUROFAKIND;
		this.highCardRank = cardVal;
		return true;
	}

	//Returns true if hand contains a full house and false otherwise
	//Updates local variables if it contains a full house
	private boolean checkForFullHouse(){
		
		if(handSize < 5){
			return false;
		}
		//First checks for three of a kind
		//then checks for a pair other than the three of a kind
		int numHouseOccurrences = 0;
		int numInHouseOccurrences = 0;
		ArrayList<Integer> houseList = new ArrayList<Integer>();
		ArrayList<Integer> fullOfList = new ArrayList<Integer>();
		
		for(int cardVal = 1; cardVal < 14; cardVal++ ){
			numHouseOccurrences = Collections.frequency(cardValues, cardVal);
			if(numHouseOccurrences >=3){
				if(cardVal == 1){
					houseList.add(14);
				}
				else{
					houseList.add(cardVal);
				}
			}
		}
		
		if(houseList.size() == 0){
			//There were no three of a kinds
			return false;
		}
		
		//Takes max of list to make sure we get best 3 of a kind
		int maxHouse = Collections.max(houseList);
		
		for(int cardVal = 1; cardVal < 14; cardVal++){
			numInHouseOccurrences = Collections.frequency(cardValues, cardVal);
			if(numInHouseOccurrences >= 2 && cardVal != maxHouse && !(cardVal == 1 && maxHouse == 14)){
				if(cardVal == 1){
					fullOfList.add(14);
				}
				else{
					fullOfList.add(cardVal);
				}		
			}
		}
		
		if(fullOfList.size() == 0){
			//There was no additional two of a kind
			return false;
		}
		
		int maxInHouse = Collections.max(fullOfList);
		
		//If you got here then you have a full house
		return evaluateFullHouse(maxHouse, maxInHouse);
		
		
	}
	
	//Have already verified full house for given values
	//Updates local variables and returns true
	private boolean evaluateFullHouse(int house, int fullOf){
		this.handType = HandType.FULLHOUSE;
		this.highCardRank = house;
		this.secondHighCard = fullOf;
		return true;
	}
	
	//Returns true if hand contains a flush and false otherwise
	//Updates local variable if it contains a flush
	private boolean checkForFlush(){
		if(handSize < 5){
			return false;
		}
		Card.Suit targetSuit = fiveCardsAreSameSuit();
		if(targetSuit == null){
			return false;
		}
		
		return evaluateFlush(targetCardValues);
	}
	
	//Have already verified flush exists
	//Updates local variables based on best flush and returns true
	private boolean evaluateFlush(ArrayList<Integer> sameSuitValues){
		
		this.handType = HandType.FLUSH;
		
		//First sorts the list to get best hand
		Collections.sort(sameSuitValues);
		//Need to account for highest value as ace high
		if(sameSuitValues.contains(Card.ACE)){
			//Sets high card rankings equal to top 4 values in list
			this.highCardRank = 14;
			this.secondHighCard = sameSuitValues.get(sameSuitValues.size() - 1);
			this.thirdHighCard = sameSuitValues.get(sameSuitValues.size() - 2);
			this.fourthHighCard = sameSuitValues.get(sameSuitValues.size() - 3);
			this.fifthHighCard = sameSuitValues.get(sameSuitValues.size() - 4);
		}
		else{
		//Then sets high card rankings equal to top five values in list
			this.highCardRank = sameSuitValues.get(sameSuitValues.size() - 1);
			this.secondHighCard = sameSuitValues.get(sameSuitValues.size() - 2);
			this.thirdHighCard = sameSuitValues.get(sameSuitValues.size() - 3);
			this.fourthHighCard = sameSuitValues.get(sameSuitValues.size() - 4);
			this.fifthHighCard = sameSuitValues.get(sameSuitValues.size() - 5);
		}
		
		return true;
	}
	
	//Returns true if hand contains a straight
	//Updates local variables if it contains a straight
	private boolean checkForStraight(){
		if(handSize < 5){
			return false;
		}
		return evaluateStraight();
	}
	
	//Returns true if hand contains a straight, false otherwise
	//Updates local variables if straight is found
	private boolean evaluateStraight(){
		//Check by high card of straight
		
		//Check for ace high straight
		if(cardValues.contains(Card.ACE)){
			if(cardValues.contains(Card.KING) && cardValues.contains(Card.QUEEN)
					&& cardValues.contains(Card.JACK) && cardValues.contains(10)){
				
				this.handType = HandType.STRAIGHT;
				this.highCardRank = 14;
				return true;
			}
		}
		
		//Check for king high straight
		if(cardValues.contains(Card.KING)){
			if(cardValues.contains(Card.QUEEN) && cardValues.contains(Card.JACK) 
					&& cardValues.contains(10) && cardValues.contains(9)){
				
				this.handType = HandType.STRAIGHT;
				this.highCardRank = Card.KING;
				return true;
			}
		}
		
		//Check for queen high straight
		if(cardValues.contains(Card.QUEEN)){
			if(cardValues.contains(Card.JACK) && cardValues.contains(10) 
					&& cardValues.contains(9) && cardValues.contains(8)){
				
				this.handType = HandType.FLUSH;
				this.highCardRank = Card.QUEEN;
				return true;
			}
		}
		
		//Check for jack high straight
		if(cardValues.contains(Card.JACK)){
			if(cardValues.contains(10) && cardValues.contains(9) 
					&& cardValues.contains(8) && cardValues.contains(7)){
				
				this.handType = HandType.STRAIGHT;
				this.highCardRank = Card.JACK;
				return true;
			}
		}
		
		//Check for 10 high straight
		if(cardValues.contains(10)){
			if(cardValues.contains(9) && cardValues.contains(8) 
					&& cardValues.contains(7) && cardValues.contains(6)){
				
				this.handType = HandType.STRAIGHT;
				this.highCardRank = 10;
				return true;
			}
		}
		//Check for 9 high straight
		if(cardValues.contains(9)){
			if(cardValues.contains(8) && cardValues.contains(7) 
					&& cardValues.contains(6) && cardValues.contains(5)){
				
				this.handType = HandType.STRAIGHT;
				this.highCardRank = 9;
				return true;
			}
		}
		
		//Check for 8 high straight
		if(cardValues.contains(8)){
			if(cardValues.contains(7) && cardValues.contains(6) 
					&& cardValues.contains(5) && cardValues.contains(4)){
				
				this.handType = HandType.STRAIGHT;
				this.highCardRank = 8;
				return true;
			}
		}
		
		//Check for 7 high straight
		if(cardValues.contains(7)){
			if(cardValues.contains(6) && cardValues.contains(5) 
					&& cardValues.contains(4) && cardValues.contains(3)){
				
				this.handType = HandType.STRAIGHT;
				this.highCardRank = 7;
				return true;
			}
		}	
		
		//Check for 6 high straight flush
		if(cardValues.contains(6)){
			if(cardValues.contains(5) && cardValues.contains(4) 
					&& cardValues.contains(3) && cardValues.contains(2)){
				
				this.handType = HandType.STRAIGHT;
				this.highCardRank = 6;
				return true;
			}
		}	
		
		//Check for 5 high straight
		if(cardValues.contains(5)){
			if(cardValues.contains(4) && cardValues.contains(3) 
					&& cardValues.contains(2) && cardValues.contains(Card.ACE)){
				
				this.handType = HandType.STRAIGHT;
				this.highCardRank = 5;
				return true;
			}
		}	
		
		//if you made it to here, there is no straight
		return false;
	}
	
	private boolean checkForThreeOfaKind(){
		if(handSize < 5){
			return false;
		}
		//First checks for three of a kind
		//then checks for a pair other than the three of a kind
		int numOccurrences = 0;
		ArrayList<Integer> threeOfaKindList = new ArrayList<Integer>();
		ArrayList<Integer> otherCards = new ArrayList<Integer>();
		
		for(int cardVal = 1; cardVal < 14; cardVal++ ){
			numOccurrences = Collections.frequency(cardValues, cardVal);
			if(numOccurrences >=3){
				if(cardVal == 1){
					threeOfaKindList.add(14);
				}
				else{
					threeOfaKindList.add(cardVal);
				}
			}
		}
		
		if(threeOfaKindList.size() == 0){
			//There were no three of a kinds
			return false;
		}
		
		//If you are here, there was a three of a kind
		//Takes max of three of a kind list to make sure you get best triplet
		int maxThreeOfaKind = Collections.max(threeOfaKindList);
		
		return evaluateThreeOfaKind(maxThreeOfaKind);
	}
	
	//Have already determined three of a kind (of cardVal) exists in hand
	//Update local variables after determining best cards remaining
	//Returns true
	private boolean evaluateThreeOfaKind(int cardVal){
		this.handType = HandType.THREEOFAKIND;
		ArrayList<Integer> restOfHand = new ArrayList<Integer>();
		for(int i = 0; i < cardValues.size(); i++){
			//Makes sure you only count cards not part of three of a kind
			//Also account for cardVal being an ace
			if(cardValues.get(i) != cardVal && !(cardVal == 14 && cardValues.get(i) == 1)){
				restOfHand.add(cardValues.get(i));
			}
		}
		
		//Sorts rest of hand in ascending order
		Collections.sort(restOfHand);
		
		//Sets necessary hand values, accounting for aces as high card
		this.highCardRank = cardVal;
		if(restOfHand.contains(Card.ACE)){
			this.secondHighCard = 14;
			this.thirdHighCard = restOfHand.get((restOfHand.size() - 1));
		}
		else{
			this.secondHighCard = restOfHand.get(restOfHand.size() - 1);
			this.thirdHighCard = restOfHand.get(restOfHand.size() - 2);
		}
		
		return true;
	}
	
	private boolean checkForTwoPair(){
		if(handSize < 5){
			return false;
		}
		int numOccurrences = 0;
		ArrayList<Integer> twoPairList = new ArrayList<Integer>();
		for(int cardVal = 1; cardVal < 14; cardVal++){
			numOccurrences = Collections.frequency(cardValues, cardVal);
			if(numOccurrences >= 2){
				if(cardVal == 1){
					twoPairList.add(14);
				}
				else{
					twoPairList.add(cardVal);
				}
			}
		}
		
		if(twoPairList.size() < 2){
			//Not two pair
			return false;
		}
		
		Collections.sort(twoPairList);
		return evaluateTwoPair(twoPairList.get(twoPairList.size() - 1), twoPairList.get(twoPairList.size() - 2));
	}
	
	private boolean evaluateTwoPair(int bestPair, int secondPair){
		this.handType = HandType.TWOPAIR;
		this.highCardRank = bestPair;
		this.secondHighCard = secondPair;
		
		ArrayList<Integer> restOfHand = new ArrayList<Integer>();
		
		for(int i = 0; i < cardValues.size(); i++){
			if(cardValues.get(i) != bestPair && cardValues.get(i) != secondPair
					&& !(bestPair == 14 && cardValues.get(i) == 1) && !(secondPair == 14 && cardValues.get(i) == 1)){
				restOfHand.add(cardValues.get(i));
			}
		}
		
		Collections.sort(restOfHand);
		
		if(restOfHand.contains(Card.ACE)){
			this.thirdHighCard = 14;
		}
		else{
			this.thirdHighCard = restOfHand.get(restOfHand.size() - 1);
		}
		return true;
		
	}
	
	private boolean checkForOnePair(){
		int numOccurrences = 0;
		ArrayList<Integer> onePairList = new ArrayList<Integer>();
		for(int cardVal = 1; cardVal < 14; cardVal++){
			numOccurrences = Collections.frequency(cardValues, cardVal);
			if(numOccurrences >= 2){
				if(cardVal == 1){
					onePairList.add(14);
				}
				else{
					onePairList.add(cardVal);
				}
			}
		}
		
		if(onePairList.size() < 1){
			//Not one pair
			return false;
		}
		
		int pairFound = Collections.max(onePairList);
		return evaluateOnePair(pairFound);
	
	}
	
	private boolean evaluateOnePair(int pairVal){
		this.handType = HandType.ONEPAIR;
		
		ArrayList<Integer> restOfHand = new ArrayList<Integer>();
		
		for(int i = 0; i < cardValues.size(); i++){
			if(cardValues.get(i) != pairVal && !(pairVal == 14 && cardValues.get(i) == 1)){
				restOfHand.add(cardValues.get(i));
			}
		}
		
		Collections.sort(restOfHand);
		
		this.highCardRank = pairVal;
		if(cardValues.size() == 2){ //Determining best hand without any common cards
			return true;
		}
		
		if(restOfHand.contains(Card.ACE)){
			this.secondHighCard = 14;
			this.thirdHighCard = restOfHand.get(restOfHand.size() - 1);
			this.fourthHighCard = restOfHand.get(restOfHand.size() - 2);
		}
		else{
			this.secondHighCard = restOfHand.get(restOfHand.size() - 1);
			this.thirdHighCard = restOfHand.get(restOfHand.size() - 2);
			this.fourthHighCard = restOfHand.get(restOfHand.size() - 3);
		}
		
		return true;
	}
	
	private boolean checkForHighCard(){
		return evaluateHighCard();
	}
	
	private boolean evaluateHighCard(){
		this.handType = HandType.HIGHCARD;
		
		ArrayList<Integer> allCards = new ArrayList<Integer>();
		for(int i = 0; i < cardValues.size(); i++){
			allCards.add(cardValues.get(i));
		}
		
		//First sorts the list to get best hand
		Collections.sort(allCards);
		//Need to account for highest value as ace high
		if(allCards.contains(Card.ACE)){
			//Sets high card rankings equal to top 4 values in list
			this.highCardRank = 14;
			this.secondHighCard = allCards.get(allCards.size() - 1);
			if(allCards.size() == 2){ //Determining best hand without any common cards
				return true;
			}
			this.thirdHighCard = allCards.get(allCards.size() - 2);
			this.fourthHighCard = allCards.get(allCards.size() - 3);
			this.fifthHighCard = allCards.get(allCards.size() - 4);
		}
		else{
		//Then sets high card rankings equal to top five values in list
			this.highCardRank = allCards.get(allCards.size() - 1);
			this.secondHighCard = allCards.get(allCards.size() - 2);
			if(allCards.size() == 2){ //Determining best hand without any common cards
				return true;
			}
			this.thirdHighCard = allCards.get(allCards.size() - 3);
			this.fourthHighCard = allCards.get(allCards.size() - 4);
			this.fifthHighCard = allCards.get(allCards.size() - 5);
		}
		
		return true;
	}
	
	//Checks if any five cards in a hand are of the same suit
	//Within called functions, will also set the member variable targetCardValues
	//equal to an array containing the values of cards that match the suit
	private Card.Suit fiveCardsAreSameSuit(){
		
		int numClubs;
		int numSpades;
		int numHearts;
		int numDiamonds;
		
		if((numClubs = this.getNumCardsofSameSuit(Card.Suit.CLUBS)) >= 5){
			return Card.Suit.CLUBS;
		}
		if((numSpades = this.getNumCardsofSameSuit(Card.Suit.SPADES)) >= 5){
			return Card.Suit.SPADES;
		}
		if((numHearts = this.getNumCardsofSameSuit(Card.Suit.HEARTS))>= 5){
			return Card.Suit.HEARTS;
		}
		if((numDiamonds = this.getNumCardsofSameSuit(Card.Suit.DIAMONDS)) >= 5){
			return Card.Suit.DIAMONDS;
		}
	
		//Otherwise return null
		return null;	
	}
	
	//Gets number of cards in hand that match targetSuit and also creates temporary target
	//Array list for cards that match that suit
	private int getNumCardsofSameSuit(Card.Suit targetSuit){
		this.targetCardValues = new ArrayList<Integer>();
		for(int i = 0; i < handSize; i++){
			if(cardSuits.get(i) == targetSuit){
				targetCardValues.add(cardValues.get(i));
			}
		}
		
		return targetCardValues.size();
	}
	
	public int compareTo(Object anotherHand){
		if(!(anotherHand instanceof HandRank)){
			throw new ClassCastException("A HandRank object expected in comparator.");
		}
		
		if(this.handType != ((HandRank)anotherHand).handType){
			return this.handType.compareTo(((HandRank)anotherHand).handType);
		}
		else if(this.highCardRank != ((HandRank)anotherHand).highCardRank){
			return this.highCardRank - ((HandRank)anotherHand).highCardRank;
		}
		else if(this.secondHighCard != ((HandRank)anotherHand).secondHighCard){
			return this.secondHighCard - ((HandRank)anotherHand).secondHighCard;
		}
		else if(this.thirdHighCard != ((HandRank)anotherHand).thirdHighCard){
			return this.thirdHighCard - ((HandRank)anotherHand).thirdHighCard;
		}
		else if(this.fourthHighCard != ((HandRank)anotherHand).fourthHighCard){
			return this.fourthHighCard - ((HandRank)anotherHand).fourthHighCard;
		}
		else{
			return this.fifthHighCard - ((HandRank)anotherHand).fifthHighCard;
		}
	}
	
	public String toString(){
		String highCardString;
		if(highCardRank == 14){
			highCardString = "Ace";
		}
		else if(highCardRank == 13){
			highCardString = "King";
		}
		else if(highCardRank == 12){
			highCardString = "Queen";
		}
		else if(highCardRank == 11){
			highCardString = "Jack";
		}
		else{
			highCardString = "" + highCardRank;
		}
		
		if(secondHighCard == 14){
			highCardString += ", Ace";
		}
		else if(secondHighCard == 13){
			highCardString += ", King";
		}
		else if(secondHighCard == 12){
			highCardString += ", Queen";
		}
		else if(secondHighCard == 11){
			highCardString += ", Jack";
		}
		else{
			if(secondHighCard != 0){
				highCardString += ", " + secondHighCard;
			}
		}
		
		if(thirdHighCard == 14){
			highCardString += ", Ace";
		}
		else if(thirdHighCard == 13){
			highCardString += ", King";
		}
		else if(thirdHighCard == 12){
			highCardString += ", Queen";
		}
		else if(thirdHighCard == 11){
			highCardString += ", Jack";
		}
		else{
			if(thirdHighCard != 0){
				highCardString += ", " + thirdHighCard;
			}
		}
		
		if(fourthHighCard == 14){
			highCardString += ", Ace";
		}
		else if(fourthHighCard == 13){
			highCardString += ", King";
		}
		else if(fourthHighCard == 12){
			highCardString += ", Queen";
		}
		else if(fourthHighCard == 11){
			highCardString += ", Jack";
		}
		else{
			if(fourthHighCard != 0){
				highCardString += ", " + fourthHighCard;
			}
		}
		
		if(fifthHighCard == 14){
			highCardString += ", Ace";
		}
		else if(fifthHighCard == 13){
			highCardString += ", King";
		}
		else if(fifthHighCard == 12){
			highCardString += ", Queen";
		}
		else if(fifthHighCard == 11){
			highCardString += ", Jack";
		}
		else{
			if(fifthHighCard != 0){
				highCardString += ", " + fifthHighCard;
			}
		}
	
		return (this.handType + ", " + highCardString);
	}
}
