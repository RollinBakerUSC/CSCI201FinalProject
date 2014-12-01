package finalProj;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GridBagLayout manage;
	private Deck cardDeck;
	GUIBoard game;
	//you're going to have an array of cards in card deck (visual representation)
	//the playerCards is going to be your personal cards
	//availCards is the communal cards

	//each card is going to be connected to its visual representation (JLabel)
	HashMap<Card, JLabel>visualCards;

	/*
	JLabel [] visualDeck;
	JLabel [] playerCards;
	JLabel [] availCards;
	 */
	public GamePanel (GridBagLayout layout, GUIBoard window){
		super (layout);
		manage=layout;
		
		game=window;
		visualCards=new HashMap<Card, JLabel>();
		cardDeck=new Deck();
		visualCardsSetUp();
		//you want to assign all the cards then repopulate the deck
		cardDeck=new Deck();
		cardDeck.shuffle();
		
		setYourCards();
		//setCommonCards();
	}

	//what needs to happen?
	//figure out a way to show cards
	//figure out a way to hide cards
	//figure out a way to get the winners

	public Deck getDeck(){
		return cardDeck;
	}

	//this will take away your cards 
	void foldCards (){
		game.pokerPlayer.setHand(null, null);
	}

	//this is going to set your cards (cards in the bottom center)
	void setYourCards (){
		Card firstCard= cardDeck.deal();
		Card secondCard= cardDeck.deal();
		game.pokerPlayer.setHand (firstCard, secondCard);
		
		add (visualCards.get(firstCard));
		add (visualCards.get(secondCard));
		
		JLabel first=visualCards.get(firstCard);
		JLabel second= visualCards.get(secondCard);		
	}

	//this is going to set the cards for the common game 
		//initially all face down
	void setCommonCards (){
		ImageIcon hidden= new ImageIcon ("cardBack.jpg");
		JLabel hiddenRep= new JLabel (hidden);
		
		add (hiddenRep);
	}
	
	//everything face down at various positions
	void setOtherPlayerCards(){
		ImageIcon hidden= new ImageIcon ("cardBack.jpg");
		JLabel hiddenRep= new JLabel (hidden);
		
		add (hiddenRep);
	}
	
	//as a turn is finished, this will flip the common cards
	void updateCommonCards(){
		
	}

	Card findCard (Card toFind){

		System.out.println ("Finding card :"+toFind.getSuitAsString()+" "+toFind.getValueAsString());
		System.out.println ("Deck has: "+cardDeck.numCardsRemaining()+" cards left.");

		while (cardDeck.numCardsRemaining()!=0){
			Card check=cardDeck.deal();
			if (check.getSuitAsString().contains(toFind.getSuitAsString())){
				if (check.getValueAsString().contains(toFind.getValueAsString())){
					System.out.println ("Card found!");

					//add (visualCards.get(check));
					return check;
				}
			}
		}
		System.out.println ("Not found.");
		return null;
	}

	//this is going to setup the hashmap of Card to JLabel

	//clubs, spades, hearts, diamonds
	//ace onward=1
	//jack=11
	//queen=12
	//king=13
	//deck [0]=ace of clubs
	//deck [1]=ace of spades
	//deck [2]==ace of hearts
	//deck [3]=ace of diamonds
	//you have to call this before you shuffle the deck
	void visualCardsSetUp(){
		
		visualCardsSetUpAce();
		visualCardsSetUp2();
		visualCardsSetUp3();
		visualCardsSetUp4();
		visualCardsSetUp5();
		visualCardsSetUp6();
		visualCardsSetUp7();
		visualCardsSetUp8();
		visualCardsSetUp9();
		visualCardsSetUp10();
		visualCardsSetUpJack();
		visualCardsSetUpQueen();
		visualCardsSetUpKing();
	}
	
	void visualCardsSetUpAce(){

		Card aceClubs= cardDeck.deal();
		Card aceSpades= cardDeck.deal();
		Card aceHearts= cardDeck.deal();
		Card aceDiamonds= cardDeck.deal();

		ImageIcon aC=new ImageIcon ("AClubs.png");
		ImageIcon aS= new ImageIcon ("ASpades.png");
		ImageIcon aH= new ImageIcon ("AHearts.png");
		ImageIcon aD= new ImageIcon ("ADiamonds.png");

		JLabel aceClub= new JLabel (aC);
		JLabel aceSpade=new JLabel (aS);
		JLabel aceHeart=new JLabel (aH);
		JLabel aceDiamond=new JLabel (aD);

		visualCards.put (aceClubs, aceClub);
		visualCards.put (aceSpades, aceSpade);
		visualCards.put (aceHearts, aceHeart);
		visualCards.put (aceDiamonds, aceDiamond);
		
		//add (visualCards.get(aceClubs));
	}

	void visualCardsSetUp2(){
		Card twoClubs= cardDeck.deal();
		Card twoSpades= cardDeck.deal();
		Card twoHearts= cardDeck.deal();
		Card twoDiamonds= cardDeck.deal();

		ImageIcon twoC=new ImageIcon ("2Clubs.png");
		ImageIcon twoS= new ImageIcon ("2Spades.png");
		ImageIcon twoH= new ImageIcon ("2Hearts.png");
		ImageIcon twoD= new ImageIcon ("2Diamonds.png");

		JLabel twoClub= new JLabel (twoC);
		JLabel twoSpade=new JLabel (twoS);
		JLabel twoHeart=new JLabel (twoH);
		JLabel twoDiamond=new JLabel (twoD);

		visualCards.put (twoClubs, twoClub);
		visualCards.put (twoSpades, twoSpade);
		visualCards.put (twoHearts, twoHeart);
		visualCards.put (twoDiamonds, twoDiamond);
	}

	void visualCardsSetUp3(){
		Card threeClubs= cardDeck.deal();
		Card threeSpades= cardDeck.deal();
		Card threeHearts= cardDeck.deal();
		Card threeDiamonds= cardDeck.deal();

		ImageIcon threeC=new ImageIcon ("3Clubs.png");
		ImageIcon threeS= new ImageIcon ("3Spades.png");
		ImageIcon threeH= new ImageIcon ("3Hearts.png");
		ImageIcon threeD= new ImageIcon ("3Diamonds.png");

		JLabel threeClub= new JLabel (threeC);
		JLabel threeSpade=new JLabel (threeS);
		JLabel threeHeart=new JLabel (threeH);
		JLabel threeDiamond=new JLabel (threeD);

		visualCards.put (threeClubs, threeClub);
		visualCards.put (threeSpades, threeSpade);
		visualCards.put (threeHearts, threeHeart);
		visualCards.put (threeDiamonds, threeDiamond);
	}
	
	void visualCardsSetUp4(){
		Card fourClubs= cardDeck.deal();
		Card fourSpades= cardDeck.deal();
		Card fourHearts= cardDeck.deal();
		Card fourDiamonds= cardDeck.deal();

		ImageIcon fourC=new ImageIcon ("4Clubs.png");
		ImageIcon fourS= new ImageIcon ("4Spades.png");
		ImageIcon fourH= new ImageIcon ("4Hearts.png");
		ImageIcon fourD= new ImageIcon ("4Diamonds.png");

		JLabel fourClub= new JLabel (fourC);
		JLabel fourSpade=new JLabel (fourS);
		JLabel fourHeart=new JLabel (fourH);
		JLabel fourDiamond=new JLabel (fourD);

		visualCards.put (fourClubs, fourClub);
		visualCards.put (fourSpades, fourSpade);
		visualCards.put (fourHearts, fourHeart);
		visualCards.put (fourDiamonds, fourDiamond);
	}
	
	void visualCardsSetUp5(){
		Card fiveClubs= cardDeck.deal();
		Card fiveSpades= cardDeck.deal();
		Card fiveHearts= cardDeck.deal();
		Card fiveDiamonds= cardDeck.deal();

		ImageIcon twoC=new ImageIcon ("5Clubs.png");
		ImageIcon twoS= new ImageIcon ("5Spades.png");
		ImageIcon twoH= new ImageIcon ("5Hearts.png");
		ImageIcon twoD= new ImageIcon ("5Diamonds.png");

		JLabel fiveClub= new JLabel (twoC);
		JLabel fiveSpade=new JLabel (twoS);
		JLabel fiveHeart=new JLabel (twoH);
		JLabel fiveDiamond=new JLabel (twoD);

		visualCards.put (fiveClubs, fiveClub);
		visualCards.put (fiveSpades, fiveSpade);
		visualCards.put (fiveHearts, fiveHeart);
		visualCards.put (fiveDiamonds, fiveDiamond);
	}
	
	void visualCardsSetUp6(){
		Card sixClubs= cardDeck.deal();
		Card sixSpades= cardDeck.deal();
		Card sixHearts= cardDeck.deal();
		Card sixDiamonds= cardDeck.deal();

		ImageIcon twoC=new ImageIcon ("6Clubs.png");
		ImageIcon twoS= new ImageIcon ("6Spades.png");
		ImageIcon twoH= new ImageIcon ("6Hearts.png");
		ImageIcon twoD= new ImageIcon ("6Diamonds.png");

		JLabel sixClub= new JLabel (twoC);
		JLabel sixSpade=new JLabel (twoS);
		JLabel sixHeart=new JLabel (twoH);
		JLabel sixDiamond=new JLabel (twoD);

		visualCards.put (sixClubs, sixClub);
		visualCards.put (sixSpades, sixSpade);
		visualCards.put (sixHearts, sixHeart);
		visualCards.put (sixDiamonds, sixDiamond);
	}
	
	void visualCardsSetUp7(){
		Card sevenClubs= cardDeck.deal();
		Card sevenSpades= cardDeck.deal();
		Card sevenHearts= cardDeck.deal();
		Card sevenDiamonds= cardDeck.deal();

		ImageIcon twoC=new ImageIcon ("7Clubs.png");
		ImageIcon twoS= new ImageIcon ("7Spades.png");
		ImageIcon twoH= new ImageIcon ("7Hearts.png");
		ImageIcon twoD= new ImageIcon ("7Diamonds.png");

		JLabel fiveClub= new JLabel (twoC);
		JLabel fiveSpade=new JLabel (twoS);
		JLabel fiveHeart=new JLabel (twoH);
		JLabel fiveDiamond=new JLabel (twoD);

		visualCards.put (sevenClubs, fiveClub);
		visualCards.put (sevenSpades, fiveSpade);
		visualCards.put (sevenHearts, fiveHeart);
		visualCards.put (sevenDiamonds, fiveDiamond);
	}
	
	void visualCardsSetUp8(){
		
		Card fiveClubs= cardDeck.deal();
		Card fiveSpades= cardDeck.deal();
		Card fiveHearts= cardDeck.deal();
		Card fiveDiamonds= cardDeck.deal();

		ImageIcon twoC=new ImageIcon ("8Clubs.png");
		ImageIcon twoS= new ImageIcon ("8Spades.png");
		ImageIcon twoH= new ImageIcon ("8Hearts.png");
		ImageIcon twoD= new ImageIcon ("8Diamonds.png");

		JLabel fiveClub= new JLabel (twoC);
		JLabel fiveSpade=new JLabel (twoS);
		JLabel fiveHeart=new JLabel (twoH);
		JLabel fiveDiamond=new JLabel (twoD);

		visualCards.put (fiveClubs, fiveClub);
		visualCards.put (fiveSpades, fiveSpade);
		visualCards.put (fiveHearts, fiveHeart);
		visualCards.put (fiveDiamonds, fiveDiamond);
	}
	
	void visualCardsSetUp9(){
		Card fiveClubs= cardDeck.deal();
		Card fiveSpades= cardDeck.deal();
		Card fiveHearts= cardDeck.deal();
		Card fiveDiamonds= cardDeck.deal();

		ImageIcon twoC=new ImageIcon ("9Clubs.png");
		ImageIcon twoS= new ImageIcon ("9Spades.png");
		ImageIcon twoH= new ImageIcon ("9Hearts.png");
		ImageIcon twoD= new ImageIcon ("9Diamonds.png");

		JLabel fiveClub= new JLabel (twoC);
		JLabel fiveSpade=new JLabel (twoS);
		JLabel fiveHeart=new JLabel (twoH);
		JLabel fiveDiamond=new JLabel (twoD);

		visualCards.put (fiveClubs, fiveClub);
		visualCards.put (fiveSpades, fiveSpade);
		visualCards.put (fiveHearts, fiveHeart);
		visualCards.put (fiveDiamonds, fiveDiamond);
	}
	
	void visualCardsSetUp10(){
		Card fiveClubs= cardDeck.deal();
		Card fiveSpades= cardDeck.deal();
		Card fiveHearts= cardDeck.deal();
		Card fiveDiamonds= cardDeck.deal();

		ImageIcon twoC=new ImageIcon ("10Clubs.png");
		ImageIcon twoS= new ImageIcon ("10Spades.png");
		ImageIcon twoH= new ImageIcon ("10Hearts.png");
		ImageIcon twoD= new ImageIcon ("10Diamonds.png");

		JLabel fiveClub= new JLabel (twoC);
		JLabel fiveSpade=new JLabel (twoS);
		JLabel fiveHeart=new JLabel (twoH);
		JLabel fiveDiamond=new JLabel (twoD);

		visualCards.put (fiveClubs, fiveClub);
		visualCards.put (fiveSpades, fiveSpade);
		visualCards.put (fiveHearts, fiveHeart);
		visualCards.put (fiveDiamonds, fiveDiamond);
	}
	
	void visualCardsSetUpJack(){
		Card fiveClubs= cardDeck.deal();
		Card fiveSpades= cardDeck.deal();
		Card fiveHearts= cardDeck.deal();
		Card fiveDiamonds= cardDeck.deal();

		ImageIcon twoC=new ImageIcon ("JClubs.png");
		ImageIcon twoS= new ImageIcon ("JSpades.png");
		ImageIcon twoH= new ImageIcon ("JHearts.png");
		ImageIcon twoD= new ImageIcon ("JDiamonds.png");

		JLabel fiveClub= new JLabel (twoC);
		JLabel fiveSpade=new JLabel (twoS);
		JLabel fiveHeart=new JLabel (twoH);
		JLabel fiveDiamond=new JLabel (twoD);

		visualCards.put (fiveClubs, fiveClub);
		visualCards.put (fiveSpades, fiveSpade);
		visualCards.put (fiveHearts, fiveHeart);
		visualCards.put (fiveDiamonds, fiveDiamond);
	}
	
	void visualCardsSetUpQueen(){
		Card fiveClubs= cardDeck.deal();
		Card fiveSpades= cardDeck.deal();
		Card fiveHearts= cardDeck.deal();
		Card fiveDiamonds= cardDeck.deal();

		ImageIcon twoC=new ImageIcon ("QClubs.png");
		ImageIcon twoS= new ImageIcon ("QSpades.png");
		ImageIcon twoH= new ImageIcon ("QHearts.png");
		ImageIcon twoD= new ImageIcon ("QDiamonds.png");

		JLabel fiveClub= new JLabel (twoC);
		JLabel fiveSpade=new JLabel (twoS);
		JLabel fiveHeart=new JLabel (twoH);
		JLabel fiveDiamond=new JLabel (twoD);

		visualCards.put (fiveClubs, fiveClub);
		visualCards.put (fiveSpades, fiveSpade);
		visualCards.put (fiveHearts, fiveHeart);
		visualCards.put (fiveDiamonds, fiveDiamond);
	}	
	
	void visualCardsSetUpKing(){
		Card kingClubs= cardDeck.deal();
		Card kingSpades= cardDeck.deal();
		Card kingHearts= cardDeck.deal();
		Card kingDiamonds= cardDeck.deal();

		ImageIcon twoC=new ImageIcon ("KClubs.png");
		ImageIcon twoS= new ImageIcon ("KSpades.png");
		ImageIcon twoH= new ImageIcon ("KHearts.png");
		ImageIcon twoD= new ImageIcon ("KDiamonds.png");

		JLabel fiveClub= new JLabel (twoC);
		JLabel fiveSpade=new JLabel (twoS);
		JLabel fiveHeart=new JLabel (twoH);
		JLabel fiveDiamond=new JLabel (twoD);

		visualCards.put (kingClubs, fiveClub);
		visualCards.put (kingSpades, fiveSpade);
		visualCards.put (kingHearts, fiveHeart);
		visualCards.put (kingDiamonds, fiveDiamond);
	}


}


