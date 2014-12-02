package finalproject;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//GridBagLayout manage;
	private Deck cardDeck;
	GUIBoard game;
	//you're going to have an array of cards in card deck (visual representation)
	//the playerCards is going to be your personal cards
	//availCards is the communal cards

	//each card is going to be connected to its visual representation (JLabel)

	//why do you need a hashmap?
	//you need a hashmap because when the player gets dealt his cards, 
	//you have to use a look up table to get the corresponding JLabel
	//why not a hashmap, it's fastest
	HashMap<String, JLabel>visualCards;

	/*
	JLabel [] visualDeck;
	JLabel [] playerCards;
	JLabel [] availCards;
	 */
	JLabel playerCard1;
	JLabel playerCard2;
	JLabel cmnCard1;
	JLabel cmnCard2;
	JLabel cmnCard3;
	JLabel cmnCard4;
	JLabel cmnCard5;
	JLabel money;
	JLabel callPot;
	
	JButton bet;
	JButton fold;
	static int updateCardCount=0;
	static int playerCount=0;
	public GamePanel (LayoutManager layout, GUIBoard window){
		
		super (layout);
		//manage=layout;
		
		game=window;
		visualCards=new HashMap<String, JLabel>();
		cardDeck=new Deck();
		playerCard1=new JLabel();
		playerCard2=new JLabel();
		cmnCard1=new JLabel();
		cmnCard2=new JLabel();
		cmnCard3=new JLabel();
		cmnCard4=new JLabel();
		cmnCard5=new JLabel();
		bet= new JButton ();
		fold= new JButton ();
		
		
		visualCardsSetUp();

		System.out.println (visualCards.keySet().size());

		//you want to assign all the cards then repopulate the deck
		cardDeck=new Deck();
		cardDeck.shuffle();
		
		setButtons();
		setMoney();
		setUpCall();
		setOtherPlayerCards();
		setYourCards();
		setCommonCards();
		setTable();
		
		System.out.println ("Updating card....turn 1");
		//updateCommonCards();
	}

	//what needs to happen?
	//figure out a way to get the winners

	public Deck getDeck(){
		return cardDeck;
	}
	
	void setUpCall(){
		callPot= new JLabel ("$"+" to call.");
		Point callPt= new Point (725, 475);
		callPot.setBounds(callPt.x,callPt.y, 50, 50);
		add(callPot);
	}
	
	//this will take away your cards 
	void foldCards (){
		game.pokerPlayer.setHand(null, null);
		System.out.println ("Folding cards.....");
		playerCard1.setVisible(false);
		playerCard1.removeAll();
		playerCard2.setVisible(false);
		playerCard2.removeAll();
	}
	
	void setMoney(){
		String labelTemp= game.pokerPlayer.getMoney().toString();
		money= new JLabel ("$"+labelTemp);
		
		Point moneyPt= new Point (725, 500);
		money.setBounds(moneyPt.x, moneyPt.y, 50, 50);
		
		add (money);
	}
	
	void setButtons(){
		bet= new JButton ("Bet");
		bet.addActionListener (new betListen(game));
		
		Point betState= new Point (610, 490);
		bet.setBounds (betState.x, betState.y, 100, 25);
		add (bet);
		
		Point foldState= new Point (610, 520);
		fold= new JButton ("Fold");
		fold.addActionListener(new foldListen(game));
		fold.setBounds(foldState.x, foldState.y, 100, 25);
		add (fold);
	}

	void setTable(){
	
		ImageIcon tablePic= new ImageIcon ("pokerTable.jpg");
		JLabel pokerTable=new JLabel (tablePic);
		Point tableState= new Point (-10, 0);
		pokerTable.setBounds(tableState.x, tableState.y, 805, 600);
		
		add (pokerTable);
	}
	
	//this is going to set your cards (cards in the bottom center)
	void setYourCards (){
		Card firstCard= cardDeck.deal();
		Card secondCard= cardDeck.deal();
		game.pokerPlayer.setHand (firstCard, secondCard);
		String firstCardKey=firstCard.getSuitAsString()+firstCard.getValueAsString();
		String secondCardKey=secondCard.getSuitAsString()+secondCard.getValueAsString();
		
		if (visualCards.containsKey(firstCardKey)){
			System.out.println ("Card one exists!");	
			playerCard1=visualCards.get(firstCardKey);
			
			Point player1= new Point (400, 475);
			playerCard1.setBounds(player1.x, player1.y, 65, 65);
			
			add (playerCard1);
		}
		else {
			return;
		}

		if (visualCards.containsKey(secondCardKey)){
			System.out.println ("Card two exists!");	
			playerCard2= visualCards.get(secondCardKey);
			
			Point player2=new Point (335, 475);
			playerCard2.setBounds (player2.x, player2.y, 65, 65);
			add (playerCard2);
		}

		else {
			return;
		}

	}

	//this is going to set the cards for the common game 
	//initially all face down
	//what happens when you start a new game
	void setCommonCards (){
		
		ImageIcon hidden= new ImageIcon ("cardBack.jpg");
		cmnCard1= new JLabel (hidden);
		Point ctrCard= new Point (250, 200);
		cmnCard1.setBounds (ctrCard.x, ctrCard.y, 50, 50);
		add (cmnCard1);
		
		cmnCard2= new JLabel (hidden);
		cmnCard2.setBounds (ctrCard.x+55, ctrCard.y, 50, 50);
		cmnCard2.setVisible (false);
		add (cmnCard2);
		
		cmnCard3=new JLabel (hidden);
		cmnCard3.setBounds (ctrCard.x+110, ctrCard.y, 50, 50);
		cmnCard3.setVisible (false);
		add (cmnCard3);
		
		cmnCard4= new JLabel (hidden);
		cmnCard4.setBounds (ctrCard.x+165, ctrCard.y, 50, 50);
		
		cmnCard5=new JLabel (hidden);
		cmnCard5.setBounds (ctrCard.x+220, ctrCard.y, 50, 50);
		
		cmnCard4.setVisible (false);
		cmnCard5.setVisible (false);
		
		add (cmnCard4);
		add (cmnCard5);
	}

	//everything face down at various positions
	//call this whenever someone new joins
	void setOtherPlayerCards(){
		playerCount++;
		ImageIcon hidden= new ImageIcon ("cardBack.jpg");
		
		JLabel hiddenRep1= new JLabel (hidden);
		JLabel hiddenRep2= new JLabel (hidden);
		JLabel hiddenRep3= new JLabel (hidden);
		JLabel hiddenRep4= new JLabel (hidden);
		JLabel hiddenRep5= new JLabel (hidden);
		JLabel hiddenRep6= new JLabel (hidden);
		JLabel hiddenRep7= new JLabel (hidden);
		JLabel hiddenRep8= new JLabel (hidden);
		JLabel hiddenRep9= new JLabel (hidden);
		JLabel hiddenRep10= new JLabel (hidden);
		JLabel hiddenRep11= new JLabel (hidden);
		JLabel hiddenRep12= new JLabel (hidden);
		JLabel hiddenRep13= new JLabel (hidden);
		JLabel hiddenRep14= new JLabel (hidden);
		
		Point card1= new Point (30, 170);
		hiddenRep1.setBounds (card1.x, card1.y, 50, 50);
		add (hiddenRep1);
		
		hiddenRep2.setBounds(card1.x, card1.y+55, 50, 50);
		add (hiddenRep2);
		
		hiddenRep3.setBounds (hiddenRep2.getX()+35, hiddenRep2.getY()+60, 50, 50);
		add (hiddenRep3);
		
		hiddenRep4.setBounds (hiddenRep3.getX()+55, hiddenRep3.getY()+30, 50, 50);
		add (hiddenRep4);
		
		hiddenRep5.setBounds (hiddenRep4.getX()+70, hiddenRep4.getY()+35, 50, 50);
		add (hiddenRep5);
		
		hiddenRep6.setBounds (hiddenRep5.getX()+55, hiddenRep5.getY()+15, 50, 50);
		add (hiddenRep6);
		
		hiddenRep7.setBounds (hiddenRep6.getX()+245, hiddenRep6.getY()+5, 50, 50);
		add (hiddenRep7);
		
		hiddenRep8.setBounds(hiddenRep7.getX()+55, hiddenRep7.getY()+5, 50, 50);
		add (hiddenRep8);
		
		hiddenRep9.setBounds (hiddenRep8.getX()+95, hiddenRep8.getY()-45, 50, 50);
		add (hiddenRep9);
		
		hiddenRep10.setBounds (hiddenRep9.getX()+55, hiddenRep9.getY()-30, 50, 50);
		add (hiddenRep10);
		
		hiddenRep11.setBounds(hiddenRep10.getX()+15, hiddenRep10.getY()-85, 50, 50);
		add (hiddenRep11);
		
		hiddenRep12.setBounds (hiddenRep11.getX(), hiddenRep11.getY()-5, 50, 50);
		add (hiddenRep12);
		
		hiddenRep13.setBounds (hiddenRep12.getX(), hiddenRep12.getY()-55, 50, 50);
		add (hiddenRep13);
		
		hiddenRep14.setBounds(hiddenRep10.getX(), hiddenRep13.getY()-75, 50, 50);
		add (hiddenRep14);
		
		hiddenRep1.setVisible (false);
		hiddenRep2.setVisible (false);
		hiddenRep3.setVisible (false);
		hiddenRep4.setVisible (false);
		hiddenRep5.setVisible (false);
		hiddenRep6.setVisible (false);
		hiddenRep7.setVisible (false);
		hiddenRep8.setVisible (false);
		hiddenRep9.setVisible (false);
		hiddenRep10.setVisible (false);
		hiddenRep11.setVisible (false);
		hiddenRep12.setVisible (false);
		hiddenRep13.setVisible (false);
		hiddenRep14.setVisible (false);
		
		if (playerCount==1){
			hiddenRep1.setVisible(true);
			hiddenRep2.setVisible(true);
		}
		
		else if (playerCount==2){
			hiddenRep3.setVisible(true);
			hiddenRep4.setVisible(true);
		}
		
		else if (playerCount==3){
			hiddenRep5.setVisible(true);
			hiddenRep6.setVisible(true);
		}
		
		else if (playerCount==4){
			hiddenRep7.setVisible(true);
			hiddenRep8.setVisible(true);
		}
		
		else if (playerCount==5){
			hiddenRep9.setVisible(true);
			hiddenRep10.setVisible(true);
		}
		
		else if (playerCount==6){
			hiddenRep11.setVisible(true);
			hiddenRep12.setVisible(true);
		}
		
		else if (playerCount==7){
			hiddenRep13.setVisible(true);
			hiddenRep14.setVisible(true);
		}
	}

	//as a turn is finished, this will flip the common cards
	//call this anytime a turn finishes
	void updateCommonCards(Card nextCommonCard){
		updateCardCount++;
				
		if (updateCardCount==1){
			//Card nextCommonCard= cardDeck.deal();
			String cmnCardKey= nextCommonCard.getSuitAsString()+nextCommonCard.getValueAsString();
			System.out.println (cmnCardKey);
			cmnCard1.setIcon(visualCards.get(cmnCardKey).getIcon());
			cmnCard1.setVisible(true);
			game.pokerPlayer.commonCards.add(nextCommonCard);
		}
		
		if (updateCardCount==2){
			//Card nextCommonCard= cardDeck.deal();
			String cmnCardKey= nextCommonCard.getSuitAsString()+nextCommonCard.getValueAsString();
			System.out.println (cmnCardKey);
			cmnCard2.setIcon(visualCards.get(cmnCardKey).getIcon());
			cmnCard2.setVisible(true);
			game.pokerPlayer.commonCards.add(nextCommonCard);
		}
		
		if (updateCardCount==3){
			//Card nextCommonCard= cardDeck.deal();
			String cmnCardKey= nextCommonCard.getSuitAsString()+nextCommonCard.getValueAsString();
			System.out.println (cmnCardKey);
			cmnCard3.setIcon(visualCards.get(cmnCardKey).getIcon());
			cmnCard3.setVisible(true);
			game.pokerPlayer.commonCards.add(nextCommonCard);
		}
		
		if (updateCardCount==4){
			//Card nextCommonCard= cardDeck.deal();
			String cmnCardKey= nextCommonCard.getSuitAsString()+nextCommonCard.getValueAsString();
			System.out.println (cmnCardKey);
			cmnCard4.setIcon(visualCards.get(cmnCardKey).getIcon());
			cmnCard4.setVisible(true);
			game.pokerPlayer.commonCards.add(nextCommonCard);	
		}
		
		if (updateCardCount==5){
			//Card nextCommonCard= cardDeck.deal();
			String cmnCardKey= nextCommonCard.getSuitAsString()+nextCommonCard.getValueAsString();
			System.out.println (cmnCardKey);
			cmnCard5.setIcon(visualCards.get(cmnCardKey).getIcon());
			cmnCard5.setVisible(true);
			game.pokerPlayer.commonCards.add(nextCommonCard);
			updateCardCount=0;
		}
		
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
		setUpClubs();
		setUpSpades();
		setUpHearts();
		setUpDiamonds();
	}

	//so your hash map will be a string and a JLabel
	//JLabel will contain the actual card
	//string is going to be the card's suit and value 
	//cant use the actual card cause no copy constructor
	//or equals to method exists for type card
	//the order at which you get cards matters. a lot.
	//ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, j, k, q

	ArrayList<String> makeMapKey(){
		ArrayList<String>keys=new ArrayList<String>();

		//you know the suits are in this order
		Card ace= cardDeck.deal();
		Card two= cardDeck.deal();
		Card three= cardDeck.deal();
		Card four= cardDeck.deal();
		Card five= cardDeck.deal();
		Card six= cardDeck.deal();
		Card seven= cardDeck.deal();
		Card eight= cardDeck.deal();
		Card nine= cardDeck.deal();
		Card ten= cardDeck.deal();
		Card jack= cardDeck.deal();
		Card queen= cardDeck.deal();
		Card king= cardDeck.deal();


		//you've got all the keys for the map, now for the actual JLabels/values

		String aceKey=ace.getSuitAsString()+ace.getValueAsString();
		String twoKey=two.getSuitAsString()+two.getValueAsString();
		String threeKey=three.getSuitAsString()+three.getValueAsString();
		String fourKey=four.getSuitAsString()+four.getValueAsString();
		String fiveKey= five.getSuitAsString()+five.getValueAsString();
		String sixKey= six.getSuitAsString()+six.getValueAsString();
		String sevenKey= seven.getSuitAsString()+seven.getValueAsString();
		String eightKey=eight.getSuitAsString()+eight.getValueAsString();
		String nineKey=nine.getSuitAsString()+nine.getValueAsString();
		String tenKey= ten.getSuitAsString()+ten.getValueAsString();
		String jackKey= jack.getSuitAsString()+jack.getValueAsString();
		String queenKey= queen.getSuitAsString()+queen.getValueAsString();
		String kingKey= king.getSuitAsString()+king.getValueAsString();

		System.out.println (aceKey);
		System.out.println (twoKey);
		System.out.println (threeKey);
		System.out.println (fourKey);
		System.out.println (fiveKey);
		System.out.println (sixKey);
		System.out.println (sevenKey);
		System.out.println (eightKey);
		System.out.println (nineKey);
		System.out.println (tenKey);
		System.out.println (jackKey);
		System.out.println (queenKey);
		System.out.println (kingKey);

		keys.add (aceKey);
		keys.add (twoKey);
		keys.add (threeKey);
		keys.add (fourKey);
		keys.add (fiveKey);
		keys.add (sixKey);
		keys.add (sevenKey);
		keys.add (eightKey);
		keys.add (nineKey);
		keys.add (tenKey);
		keys.add (jackKey);
		keys.add (queenKey);
		keys.add (kingKey);

		return keys;
	}

	void setUpClubs(){
		ArrayList<String>keys= makeMapKey();

		for (int i=0; i<keys.size();i++){
			String key=keys.get(i);
			if (key.contains("Ace")){
				ImageIcon aD= new ImageIcon ("AClubs.png");
				JLabel aceDiamond=new JLabel (aD);
				visualCards.put(keys.get(i), aceDiamond);
			}

			else if (key.contains ("2")){
				ImageIcon twoD= new ImageIcon ("2Clubs.png");
				JLabel twoDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i),twoDiamond);
			}

			else if (key.contains("3")){
				ImageIcon threeD= new ImageIcon ("3Clubs.png");
				JLabel threeDiamond=new JLabel (threeD);
				visualCards.put(keys.get(i), threeDiamond);

			}

			else if (key.contains ("4")){
				ImageIcon fourD= new ImageIcon ("4Clubs.png");
				JLabel fourDiamond=new JLabel (fourD);
				visualCards.put (keys.get(i),fourDiamond);
			}

			else if (key.contains("5")){
				ImageIcon fiveD= new ImageIcon ("5Clubs.png");
				JLabel fiveDiamond=new JLabel (fiveD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("6")){
				ImageIcon twoD= new ImageIcon ("6Clubs.png");
				JLabel sixDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), sixDiamond);
			}

			else if (key.contains ("7")){
				ImageIcon twoD= new ImageIcon ("7Clubs.png");
				JLabel sevenDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), sevenDiamond);
			}

			else if (key.contains ("8")){
				ImageIcon twoD= new ImageIcon ("8Clubs.png");
				JLabel eightDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), eightDiamond);
			}

			else if (key.contains ("9")){
				ImageIcon twoD= new ImageIcon ("9Clubs.png");
				JLabel nineDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), nineDiamond);
			}

			else if (key.contains ("10")){
				ImageIcon twoD= new ImageIcon ("10Clubs.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("Jack")){
				ImageIcon twoD= new ImageIcon ("JClubs.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("Queen")){
				ImageIcon twoD= new ImageIcon ("QClubs.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains("King")){
				ImageIcon twoD= new ImageIcon ("KClubs.png");
				JLabel kingDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), kingDiamond);
			}
		}
	}

	void setUpSpades(){

		ArrayList<String>keys= makeMapKey();

		for (int i=0; i<keys.size();i++){
			String key=keys.get(i);
			if (key.contains("Ace")){
				ImageIcon aD= new ImageIcon ("ASpades.png");
				JLabel aceDiamond=new JLabel (aD);
				visualCards.put(keys.get(i), aceDiamond);
			}

			else if (key.contains ("2")){
				ImageIcon twoD= new ImageIcon ("2Spades.png");
				JLabel twoDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i),twoDiamond);
			}

			else if (key.contains("3")){
				ImageIcon threeD= new ImageIcon ("3Spades.png");
				JLabel threeDiamond=new JLabel (threeD);
				visualCards.put(keys.get(i), threeDiamond);

			}

			else if (key.contains ("4")){
				ImageIcon fourD= new ImageIcon ("4Spades.png");
				JLabel fourDiamond=new JLabel (fourD);
				visualCards.put (keys.get(i),fourDiamond);
			}

			else if (key.contains("5")){
				ImageIcon fiveD= new ImageIcon ("5Spades.png");
				JLabel fiveDiamond=new JLabel (fiveD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("6")){
				ImageIcon twoD= new ImageIcon ("6Spades.png");
				JLabel sixDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), sixDiamond);
			}

			else if (key.contains ("7")){
				ImageIcon twoD= new ImageIcon ("7Spades.png");
				JLabel sevenDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), sevenDiamond);
			}

			else if (key.contains ("8")){
				ImageIcon twoD= new ImageIcon ("8Spades.png");
				JLabel eightDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), eightDiamond);
			}

			else if (key.contains ("9")){
				ImageIcon twoD= new ImageIcon ("9Spades.png");
				JLabel nineDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), nineDiamond);
			}

			else if (key.contains ("10")){
				ImageIcon twoD= new ImageIcon ("10Spades.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("Jack")){
				ImageIcon twoD= new ImageIcon ("JSpades.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("Queen")){
				ImageIcon twoD= new ImageIcon ("QSpades.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains("King")){
				ImageIcon twoD= new ImageIcon ("KSpades.png");
				JLabel kingDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), kingDiamond);
			}
		}
	}

	void setUpHearts(){
		ArrayList<String>keys= makeMapKey();

		for (int i=0; i<keys.size();i++){
			String key=keys.get(i);
			if (key.contains("Ace")){
				ImageIcon aD= new ImageIcon ("AHearts.png");
				JLabel aceDiamond=new JLabel (aD);
				visualCards.put(keys.get(i), aceDiamond);
			}

			else if (key.contains ("2")){
				ImageIcon twoD= new ImageIcon ("2Hearts.png");
				JLabel twoDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i),twoDiamond);
			}

			else if (key.contains("3")){
				ImageIcon threeD= new ImageIcon ("3Hearts.png");
				JLabel threeDiamond=new JLabel (threeD);
				visualCards.put(keys.get(i), threeDiamond);

			}

			else if (key.contains ("4")){
				ImageIcon fourD= new ImageIcon ("4Hearts.png");
				JLabel fourDiamond=new JLabel (fourD);
				visualCards.put (keys.get(i),fourDiamond);
			}

			else if (key.contains("5")){
				ImageIcon fiveD= new ImageIcon ("5Hearts.png");
				JLabel fiveDiamond=new JLabel (fiveD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("6")){
				ImageIcon twoD= new ImageIcon ("6Hearts.png");
				JLabel sixDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), sixDiamond);
			}

			else if (key.contains ("7")){
				ImageIcon twoD= new ImageIcon ("7Hearts.png");
				JLabel sevenDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), sevenDiamond);
			}

			else if (key.contains ("8")){
				ImageIcon twoD= new ImageIcon ("8Hearts.png");
				JLabel eightDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), eightDiamond);
			}

			else if (key.contains ("9")){
				ImageIcon twoD= new ImageIcon ("9Hearts.png");
				JLabel nineDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), nineDiamond);
			}

			else if (key.contains ("10")){
				ImageIcon twoD= new ImageIcon ("10Hearts.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("Jack")){
				ImageIcon twoD= new ImageIcon ("JHearts.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("Queen")){
				ImageIcon twoD= new ImageIcon ("QHearts.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains("King")){
				ImageIcon twoD= new ImageIcon ("KHearts.png");
				JLabel kingDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), kingDiamond);
			}
		}
	}

	void setUpDiamonds(){
		ArrayList<String>keys= makeMapKey();

		for (int i=0; i<keys.size();i++){
			String key=keys.get(i);
			if (key.contains("Ace")){
				ImageIcon aD= new ImageIcon ("ADiamonds.png");
				JLabel aceDiamond=new JLabel (aD);
				visualCards.put(keys.get(i), aceDiamond);
			}

			else if (key.contains ("2")){
				ImageIcon twoD= new ImageIcon ("2Diamonds.png");
				JLabel twoDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i),twoDiamond);
			}

			else if (key.contains("3")){
				ImageIcon threeD= new ImageIcon ("3Diamonds.png");
				JLabel threeDiamond=new JLabel (threeD);
				visualCards.put(keys.get(i), threeDiamond);

			}

			else if (key.contains ("4")){
				ImageIcon fourD= new ImageIcon ("4Diamonds.png");
				JLabel fourDiamond=new JLabel (fourD);
				visualCards.put (keys.get(i),fourDiamond);
			}

			else if (key.contains("5")){
				ImageIcon fiveD= new ImageIcon ("5Diamonds.png");
				JLabel fiveDiamond=new JLabel (fiveD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("6")){
				ImageIcon twoD= new ImageIcon ("6Diamonds.png");
				JLabel sixDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), sixDiamond);
			}

			else if (key.contains ("7")){
				ImageIcon twoD= new ImageIcon ("7Diamonds.png");
				JLabel sevenDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), sevenDiamond);
			}

			else if (key.contains ("8")){
				ImageIcon twoD= new ImageIcon ("8Diamonds.png");
				JLabel eightDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), eightDiamond);
			}

			else if (key.contains ("9")){
				ImageIcon twoD= new ImageIcon ("9Diamonds.png");
				JLabel nineDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), nineDiamond);
			}

			else if (key.contains ("10")){
				ImageIcon twoD= new ImageIcon ("10Diamonds.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("Jack")){
				ImageIcon twoD= new ImageIcon ("JDiamonds.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("Queen")){
				ImageIcon twoD= new ImageIcon ("QDiamonds.png");
				JLabel fiveDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains("King")){
				ImageIcon twoD= new ImageIcon ("KDiamonds.png");
				JLabel kingDiamond=new JLabel (twoD);
				visualCards.put(keys.get(i), kingDiamond);
			}
		}
	}
	
	class betListen implements ActionListener {
		//send out some message this is happening
		GUIBoard gui;
		public betListen (GUIBoard game){
			gui=game;
		}

		public void actionPerformed (ActionEvent e){
			//you want the user to specify how much they're betting

			String amount= (String)JOptionPane.showInputDialog (gui,"Enter betting amount (to nearest dollar): ", 
					"Betting", JOptionPane.PLAIN_MESSAGE);
			//Update player money here...
			try {
				Integer bet= Integer.parseInt(amount);

				//you'll set the player's money when you make the money panel
				if (bet>gui.pokerPlayer.getMoney()){
					JOptionPane.showMessageDialog(gui, "Insufficient Funds.");
				}
				
				if (bet<0){
					JOptionPane.showMessageDialog (gui, " Invalid Bet Amount.");
				}

				else{
					gui.pokerPlayer.setBet(bet);
					
					money.setText("$"+gui.pokerPlayer.getMoney().toString());
					synchronized(this){
						this.notify();
					}
				}
			}

			catch (Exception ef){
				JOptionPane.showMessageDialog (gui,"Invalid Bet Amount.");
			}
		}
	}

	class foldListen implements ActionListener {
		GUIBoard gui;
		public foldListen (GUIBoard game){
			gui=game;
		}
		//what'll this do? you want to get rid of the cards
		public void actionPerformed (ActionEvent e){
		
			foldCards();
			this.gui.pokerPlayer.foldCards();
			synchronized(this.gui.gameBoard){
				this.gui.gameBoard.notify();
			}
			//send out some message
		}
	}
	
}


