package finalproject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
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
	//HashMap<String, JLabel>visualCards;
	HashMap<String, File>visualCards;

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
	JLabel hiddenRep1;
	JLabel hiddenRep2;
	JLabel hiddenRep3;
	JLabel hiddenRep4;
	JLabel hiddenRep5;
	JLabel hiddenRep6;
	JLabel hiddenRep7;
	JLabel hiddenRep8;
	JLabel hiddenRep9;
	JLabel hiddenRep10;
	JLabel hiddenRep11;
	JLabel hiddenRep12;
	JLabel hiddenRep13;
	JLabel hiddenRep14;
	/*File playerCard1;
	File playerCard2;
	File cmnCard1;
	File cmnCard2;
	File cmnCard3;
	File cmnCard4;
	File cmnCard5;*/

	JLabel money;
	JLabel callPot;

	JButton bet;
	JButton fold;
	int updateCardCount=0;
	static int playerCount=0;
	public GamePanel (LayoutManager layout, GUIBoard window){

		super (layout);
		//manage=layout;

		game=window;
		visualCards=new HashMap<String, File>();
		cardDeck=new Deck();
		playerCard1=new JLabel();
		playerCard2=new JLabel();
		/*	cmnCard1=new JLabel();
		cmnCard2=new JLabel();
		cmnCard3=new JLabel();
		cmnCard4=new JLabel();
		cmnCard5=new JLabel();*/
		Point player1= new Point (400, 475);
		Point player2=new Point (335, 475);
		playerCard1.setBounds(player1.x, player1.y, 65, 65);
		playerCard2.setBounds (player2.x, player2.y, 65, 65);
		add (playerCard2);
		add (playerCard1);
		bet= new JButton ();
		fold= new JButton ();
		
		callPot= new JLabel();
		Point callPt= new Point (715, 475);
		callPot.setBounds(callPt.x,callPt.y, 75, 50);
		add(callPot);
		
		money=new JLabel();
		Point moneyPt= new Point (735, 500);
		money.setBounds(moneyPt.x, moneyPt.y, 50, 50);
		add (money);
		
		ImageIcon hidden= new ImageIcon ("cardBack.jpg");

		 hiddenRep1= new JLabel (hidden);
		 hiddenRep2= new JLabel (hidden);
		 hiddenRep3= new JLabel (hidden);
		 hiddenRep4= new JLabel (hidden);
		 hiddenRep5= new JLabel (hidden);
		 hiddenRep6= new JLabel (hidden);
		 hiddenRep7= new JLabel (hidden);
		 hiddenRep8= new JLabel (hidden);
		 hiddenRep9= new JLabel (hidden);
		 hiddenRep10= new JLabel (hidden);
		 hiddenRep11= new JLabel (hidden);
		 hiddenRep12= new JLabel (hidden);
		 hiddenRep13= new JLabel (hidden);
		 hiddenRep14= new JLabel (hidden);
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
		
		
		visualCardsSetUp();

		System.out.println (visualCards.keySet().size());

		//you want to assign all the cards then repopulate the deck
		cardDeck=new Deck();
		cardDeck.shuffle();

		setButtons();
	//	setOtherPlayerCards();
		//setYourCards();
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
	
	void disableButtons(){
		bet.setEnabled(false);
		fold.setEnabled(false);
	}
	
	void enableButtons(){
		bet.setEnabled (true);
		fold.setEnabled(true);
	}

	void setUpCall(int amount){
		//callPot= new JLabel ("$"+" to call.");
		Integer holder= amount;
		callPot.setText ("Call: " + "$ "+holder.toString());
		callPot.setVisible (true);
		callPot.updateUI();
	}
	
	void disappearCall (){
		callPot.setVisible(false);
		callPot.updateUI();
	}

	//this will take away your cards 
	void foldCards (){
		//game.pokerPlayer.setHand(null, null);
		System.out.println ("Folding cards.....");
		synchronized(playerCard1)
		{

			synchronized(playerCard2)
			{
				disappearCall();
				playerCard1.setVisible(false);

				//playerCard1.removeAll();

				playerCard2.setVisible(false);
			}
		}
		//playerCard2.removeAll();
	}

	void setMoney(String money1){
		//	String labelTemp= game.pokerPlayer.getMoney().toString();
		String labelTemp=money1;
		System.out.println ("Label temp is: "+money1);
		//money= new JLabel ("$"+labelTemp);
		money.setText ("$"+labelTemp);
		money.updateUI();
		System.out.println (money.getText());
		
		money.setVisible(true);
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
	void setYourCards (Card firstCard, Card secondCard){
		//firstCard= cardDeck.deal();
		//secondCard= cardDeck.deal();
		//game.pokerPlayer.setHand (firstCard, secondCard);
		String firstCardKey=firstCard.getSuitAsString()+firstCard.getValueAsString();
		String secondCardKey=secondCard.getSuitAsString()+secondCard.getValueAsString();
		System.out.println(firstCardKey + " " + secondCardKey);
		if (visualCards.containsKey(firstCardKey)){
			System.out.println ("Card one exists!");	
			File pc1=visualCards.get(firstCardKey);
			//System.out.println(playerCard1);

			//try {
			ImageIcon cardImage = new ImageIcon(pc1.getAbsolutePath());
			playerCard1.setIcon(cardImage);
			playerCard1.setVisible(true);
			playerCard1.updateUI();
			System.out.println(playerCard1.isVisible());
			//BufferedImage cImage = toBufferedImage(cardImage);

			//Graphics2D g2d = (Graphics2D)this.getGraphics();
			//			//g2d.drawImage(cardImage, player1.x, player1.y, 65, 65, null);
			//		} catch (IOException e) {
			// TODO Auto-generated catch block
			//		e.printStackTrace();
			//}



		}
		else {
			return;
		}

		if (visualCards.containsKey(secondCardKey)){
			System.out.println ("Card two exists!");	
			File pc2= visualCards.get(secondCardKey);


			//try {
			ImageIcon cardImage = new ImageIcon(pc2.getAbsolutePath());
			playerCard2.setIcon(cardImage);
			playerCard2.setVisible(true);
			playerCard2.updateUI();
			//BufferedImage cImage = toBufferedImage(cardImage);
			//Graphics2D g2d = (Graphics2D)this.getGraphics();
			//g2d.drawImage(cardImage, player2.x, player2.y, 65, 65, null);
			//this.repaint();
			//} catch (IOException e) {
			/// TODO Auto-generated catch block
			//e.printStackTrace();
			//}

		}

		else {
			return;
		}

	}

void clearCommonCards(){
	updateCardCount=0;
	cmnCard1.setVisible(false);
	cmnCard1.updateUI();
	cmnCard2.setVisible(false);
	cmnCard2.updateUI();
	cmnCard3.setVisible(false);
	cmnCard3.updateUI();
	cmnCard4.setVisible(false);
	cmnCard4.updateUI();
	cmnCard5.setVisible(false);
	cmnCard5.updateUI();
}
	//this is going to set the cards for the common game 
	//initially all face down
	//what happens when you start a new game
	void setCommonCards (){

		//ImageIcon hidden= new ImageIcon ("cardBack.jpg");
		cmnCard1= new JLabel ();
		//	cmnCard1= new File ("cardBack.jpg");
		Point ctrCard= new Point (250, 200);
		cmnCard1.setBounds (ctrCard.x, ctrCard.y, 50, 50);
		cmnCard1.setVisible(false);
		add (cmnCard1);

		cmnCard2= new JLabel ();
		cmnCard2.setBounds (ctrCard.x+55, ctrCard.y, 50, 50);
		cmnCard2.setVisible (false);
		add (cmnCard2);

		//cmnCard2= new File ("cardBack.jpg");
		//cmnCard3=new File ("cardBack.jpg");
		//cmnCard4=new File ("cardBack.jpg");
		//cmnCard5= new File ("cardBack.jpg");

		cmnCard3=new JLabel ();
		cmnCard3.setBounds (ctrCard.x+110, ctrCard.y, 50, 50);
		cmnCard3.setVisible (false);
		add (cmnCard3);

		cmnCard4= new JLabel ();
		cmnCard4.setBounds (ctrCard.x+165, ctrCard.y, 50, 50);

		cmnCard5=new JLabel ();
		cmnCard5.setBounds (ctrCard.x+220, ctrCard.y, 50, 50);

		cmnCard4.setVisible (false);
		cmnCard5.setVisible (false);

		add (cmnCard4);
		add (cmnCard5);
	}

	//everything face down at various positions
	//call this whenever someone new joins
	void clearPlayerCount()
	{
		playerCount=0;
	}
	void setOtherPlayerCards(){
		playerCount++;
		System.out.println("in setOtherPlayerCards()");
		ImageIcon hidden= new ImageIcon ("cardBack.jpg");
		if (playerCount==1){
			hiddenRep1.setIcon (hidden);
			hiddenRep2.setIcon (hidden);
			hiddenRep1.setVisible(true);
			
			hiddenRep1.updateUI();
			hiddenRep2.setVisible(true);
			hiddenRep2.updateUI();
			System.out.println("in setOtherPlayerCards()");
		}

		else if (playerCount==2){
			hiddenRep3.setVisible(true);
			hiddenRep3.updateUI();
			hiddenRep4.setVisible(true);
			hiddenRep4.updateUI();
		}

		else if (playerCount==3){
			hiddenRep5.setVisible(true);
			hiddenRep5.updateUI();
			hiddenRep6.setVisible(true);
			hiddenRep6.updateUI();
		}

		else if (playerCount==4){
			hiddenRep7.setVisible(true);
			hiddenRep7.updateUI();
			hiddenRep8.setVisible(true);
			hiddenRep8.updateUI();
		}

		else if (playerCount==5){
			hiddenRep9.setVisible(true);
			hiddenRep9.updateUI();
			hiddenRep10.setVisible(true);
			hiddenRep10.updateUI();
		}

		else if (playerCount==6){
			hiddenRep11.setVisible(true);
			hiddenRep11.updateUI();
			hiddenRep12.setVisible(true);
			hiddenRep12.updateUI();
		}

		else if (playerCount==7){
			hiddenRep13.setVisible(true);
			hiddenRep13.updateUI();
			hiddenRep14.setVisible(true);
			hiddenRep14.updateUI();
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
			ImageIcon cmn1Icon=new ImageIcon(visualCards.get(cmnCardKey).getAbsolutePath());
			cmnCard1.setIcon(cmn1Icon);
			cmnCard1.setVisible(true);
			cmnCard1.updateUI();
			//game.pokerPlayer.commonCards.add(nextCommonCard);
		}

		if (updateCardCount==2){
			//Card nextCommonCard= cardDeck.deal();
			String cmnCardKey= nextCommonCard.getSuitAsString()+nextCommonCard.getValueAsString();
			System.out.println (cmnCardKey);
			ImageIcon cmn2Icon=new ImageIcon(visualCards.get(cmnCardKey).getAbsolutePath());
			cmnCard2.setIcon(cmn2Icon);
			cmnCard2.setVisible(true);
			cmnCard2.updateUI();
		}

		if (updateCardCount==3){
			//Card nextCommonCard= cardDeck.deal();
			String cmnCardKey= nextCommonCard.getSuitAsString()+nextCommonCard.getValueAsString();
			System.out.println (cmnCardKey);
			ImageIcon cmn1Icon=new ImageIcon(visualCards.get(cmnCardKey).getAbsolutePath());
			cmnCard3.setIcon(cmn1Icon);
			cmnCard3.setVisible(true);
			cmnCard3.updateUI();
		}

		if (updateCardCount==4){
			//Card nextCommonCard= cardDeck.deal();
			String cmnCardKey= nextCommonCard.getSuitAsString()+nextCommonCard.getValueAsString();
			System.out.println (cmnCardKey);
			ImageIcon cmn1Icon=new ImageIcon(visualCards.get(cmnCardKey).getAbsolutePath());
			cmnCard4.setIcon(cmn1Icon);
			cmnCard4.setVisible(true);
			cmnCard4.updateUI();
		}

		if (updateCardCount==5){
			//Card nextCommonCard= cardDeck.deal();
			String cmnCardKey= nextCommonCard.getSuitAsString()+nextCommonCard.getValueAsString();
			System.out.println (cmnCardKey);
			ImageIcon cmn1Icon=new ImageIcon(visualCards.get(cmnCardKey).getAbsolutePath());
			cmnCard5.setIcon(cmn1Icon);
			cmnCard5.setVisible(true);
			cmnCard5.updateUI();
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
				//visualCards.put(keys.get(i), aceDiamond);
				File aclubs= new File ("AClubs.png");
				visualCards.put (keys.get(i),aclubs);
			}

			else if (key.contains ("2")){
				ImageIcon twoD= new ImageIcon ("2Clubs.png");
				JLabel twoDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i),twoDiamond);
				File twoClubs= new File ("2Clubs.png");
				visualCards.put (keys.get(i),twoClubs);
			}

			else if (key.contains("3")){
				ImageIcon threeD= new ImageIcon ("3Clubs.png");
				JLabel threeDiamond=new JLabel (threeD);
				//visualCards.put(keys.get(i), threeDiamond);
				File threeClubs= new File ("3Clubs.png");
				visualCards.put (keys.get(i), threeClubs);
			}

			else if (key.contains ("4")){
				ImageIcon fourD= new ImageIcon ("4Clubs.png");
				JLabel fourDiamond=new JLabel (fourD);
				//visualCards.put (keys.get(i),fourDiamond);
				File fourClubs= new File ("4Clubs.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains("5")){
				ImageIcon fiveD= new ImageIcon ("5Clubs.png");
				JLabel fiveDiamond=new JLabel (fiveD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("5Clubs.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("6")){
				ImageIcon twoD= new ImageIcon ("6Clubs.png");
				JLabel sixDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), sixDiamond);
				File fourClubs= new File ("6Clubs.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("7")){
				ImageIcon twoD= new ImageIcon ("7Clubs.png");
				JLabel sevenDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), sevenDiamond);
				File fourClubs= new File ("7Clubs.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("8")){
				ImageIcon twoD= new ImageIcon ("8Clubs.png");
				JLabel eightDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), eightDiamond);
				File fourClubs= new File ("8Clubs.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("9")){
				ImageIcon twoD= new ImageIcon ("9Clubs.png");
				JLabel nineDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), nineDiamond);
				File fourClubs= new File ("9Clubs.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("10")){
				ImageIcon twoD= new ImageIcon ("10Clubs.png");
				JLabel fiveDiamond=new JLabel (twoD);
				File fourClubs= new File ("10Clubs.png");
				visualCards.put (keys.get(i), fourClubs);
				//visualCards.put(keys.get(i), fiveDiamond);
			}

			else if (key.contains ("Jack")){
				ImageIcon twoD= new ImageIcon ("JClubs.png");
				JLabel fiveDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("JClubs.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains ("Queen")){
				ImageIcon twoD= new ImageIcon ("QClubs.png");
				JLabel fiveDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("QClubs.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains("King")){
				ImageIcon twoD= new ImageIcon ("KClubs.png");
				JLabel kingDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), kingDiamond);
				File fourClubs= new File ("KClubs.png");
				visualCards.put (keys.get(i), fourClubs);
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
				//visualCards.put(keys.get(i), aceDiamond);
				File fourClubs= new File ("ASpades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("2")){
				ImageIcon twoD= new ImageIcon ("2Spades.png");
				JLabel twoDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i),twoDiamond);
				File fourClubs= new File ("2Spades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains("3")){
				ImageIcon threeD= new ImageIcon ("3Spades.png");
				JLabel threeDiamond=new JLabel (threeD);
				//visualCards.put(keys.get(i), threeDiamond);
				File fourClubs= new File ("3Spades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("4")){
				ImageIcon fourD= new ImageIcon ("4Spades.png");
				JLabel fourDiamond=new JLabel (fourD);
				//visualCards.put (keys.get(i),fourDiamond);
				File fourClubs= new File ("4Spades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains("5")){
				ImageIcon fiveD= new ImageIcon ("5Spades.png");
				JLabel fiveDiamond=new JLabel (fiveD);
				//	visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("5Spades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("6")){
				ImageIcon twoD= new ImageIcon ("6Spades.png");
				JLabel sixDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), sixDiamond);
				File fourClubs= new File ("6Spades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("7")){
				ImageIcon twoD= new ImageIcon ("7Spades.png");
				JLabel sevenDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), sevenDiamond);
				File fourClubs= new File ("7Spades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("8")){
				ImageIcon twoD= new ImageIcon ("8Spades.png");
				JLabel eightDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), eightDiamond);
				File fourClubs= new File ("8Spades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("9")){
				ImageIcon twoD= new ImageIcon ("9Spades.png");
				JLabel nineDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), nineDiamond);
				File fourClubs= new File ("9Spades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("10")){
				ImageIcon twoD= new ImageIcon ("10Spades.png");
				JLabel fiveDiamond=new JLabel (twoD);
				//	visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("10Spades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("Jack")){
				ImageIcon twoD= new ImageIcon ("JSpades.png");
				JLabel fiveDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("JSpades.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("Queen")){
				ImageIcon twoD= new ImageIcon ("QSpades.png");
				JLabel fiveDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("QSpades.png");
				visualCards.put (keys.get(i), fourClubs);
			}


			else if (key.contains("King")){
				ImageIcon twoD= new ImageIcon ("KSpades.png");
				JLabel kingDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), kingDiamond);
				File fourClubs= new File ("KSpades.png");
				visualCards.put (keys.get(i), fourClubs);
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
				//visualCards.put(keys.get(i), aceDiamond);
				File fourClubs= new File ("AHearts.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("2")){
				ImageIcon twoD= new ImageIcon ("2Hearts.png");
				JLabel twoDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i),twoDiamond);
				File fourClubs= new File ("2Hearts.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains("3")){
				ImageIcon threeD= new ImageIcon ("3Hearts.png");
				JLabel threeDiamond=new JLabel (threeD);
				//visualCards.put(keys.get(i), threeDiamond);	
				File fourClubs= new File ("3Hearts.png");
				visualCards.put (keys.get(i), fourClubs);


			}

			else if (key.contains ("4")){
				ImageIcon fourD= new ImageIcon ("4Hearts.png");
				JLabel fourDiamond=new JLabel (fourD);
				//visualCards.put (keys.get(i),fourDiamond);
				File fourClubs= new File ("4Hearts.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains("5")){
				ImageIcon fiveD= new ImageIcon ("5Hearts.png");
				JLabel fiveDiamond=new JLabel (fiveD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("5Hearts.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains ("6")){
				ImageIcon twoD= new ImageIcon ("6Hearts.png");
				JLabel sixDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), sixDiamond);
				File fourClubs= new File ("6Hearts.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains ("7")){
				ImageIcon twoD= new ImageIcon ("7Hearts.png");
				JLabel sevenDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), sevenDiamond);
				File fourClubs= new File ("7Hearts.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains ("8")){
				ImageIcon twoD= new ImageIcon ("8Hearts.png");
				JLabel eightDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), eightDiamond);
				File fourClubs= new File ("8Hearts.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains ("9")){
				ImageIcon twoD= new ImageIcon ("9Hearts.png");
				JLabel nineDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), nineDiamond);
				File fourClubs= new File ("9Hearts.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains ("10")){
				ImageIcon twoD= new ImageIcon ("10Hearts.png");
				JLabel fiveDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("10Hearts.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains ("Jack")){
				ImageIcon twoD= new ImageIcon ("JHearts.png");
				JLabel fiveDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("JHearts.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains ("Queen")){
				ImageIcon twoD= new ImageIcon ("QHearts.png");
				JLabel fiveDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("QHearts.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains("King")){
				ImageIcon twoD= new ImageIcon ("KHearts.png");
				JLabel kingDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), kingDiamond);
				File fourClubs= new File ("KHearts.png");
				visualCards.put (keys.get(i), fourClubs);

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
				//visualCards.put(keys.get(i), aceDiamond);
				File fourClubs= new File ("ADiamonds.png");
				visualCards.put (keys.get(i), fourClubs);

			}

			else if (key.contains ("2")){
				ImageIcon twoD= new ImageIcon ("2Diamonds.png");
				JLabel twoDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i),twoDiamond);
				File fourClubs= new File ("2Diamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains("3")){
				ImageIcon threeD= new ImageIcon ("3Diamonds.png");
				JLabel threeDiamond=new JLabel (threeD);
				//visualCards.put(keys.get(i), threeDiamond);
				File fourClubs= new File ("3Diamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("4")){
				ImageIcon fourD= new ImageIcon ("4Diamonds.png");
				JLabel fourDiamond=new JLabel (fourD);
				//visualCards.put (keys.get(i),fourDiamond);
				File fourClubs= new File ("4Diamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains("5")){
				ImageIcon fiveD= new ImageIcon ("5Diamonds.png");
				JLabel fiveDiamond=new JLabel (fiveD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("5Diamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("6")){
				ImageIcon twoD= new ImageIcon ("6Diamonds.png");
				JLabel sixDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), sixDiamond);
				File fourClubs= new File ("6Diamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("7")){
				ImageIcon twoD= new ImageIcon ("7Diamonds.png");
				JLabel sevenDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), sevenDiamond);
				File fourClubs= new File ("7Diamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("8")){
				ImageIcon twoD= new ImageIcon ("8Diamonds.png");
				JLabel eightDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), eightDiamond);
				File fourClubs= new File ("8Diamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("9")){
				ImageIcon twoD= new ImageIcon ("9Diamonds.png");
				JLabel nineDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), nineDiamond);
				File fourClubs= new File ("9Diamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("10")){
				ImageIcon twoD= new ImageIcon ("10Diamonds.png");
				JLabel fiveDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("10Diamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("Jack")){
				//File Jack= new File( "JDiamonds.png");
				//ImageIcon twoD= new ImageIcon ("JDiamonds.png");
				//JLabel fiveDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), fiveDiamond);

				File fourClubs= new File ("JDiamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains ("Queen")){
				ImageIcon twoD= new ImageIcon ("QDiamonds.png");
				JLabel fiveDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), fiveDiamond);
				File fourClubs= new File ("QDiamonds.png");
				visualCards.put (keys.get(i), fourClubs);
			}

			else if (key.contains("King")){
				ImageIcon twoD= new ImageIcon ("KDiamonds.png");
				JLabel kingDiamond=new JLabel (twoD);
				//visualCards.put(keys.get(i), kingDiamond);
				File fourClubs= new File ("KDiamonds.png");
				visualCards.put (keys.get(i), fourClubs);
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
					disableButtons();
					gui.pokerPlayer.setBet(bet);

					money.setText("$"+gui.pokerPlayer.getMoney().toString());
					disappearCall();
					//changed from this.notify to this.gui.gameBoard.notify
					synchronized(this.gui.gameBoard){
						this.gui.gameBoard.notify();
					}
				}
			}

			catch (Exception ef){
				JOptionPane.showMessageDialog (gui,"Number Only");
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
			disableButtons();
			clearCommonCards();
			this.gui.pokerPlayer.foldCards();
			try {
				synchronized(this.gui.gameBoard){
					this.gui.gameBoard.notify();
				}
			}

			catch (IllegalMonitorStateException iem){
				System.out.println ("FUUUUUU");
				JOptionPane.showMessageDialog (gui,"Thread Exception>");
			}
			//send out some message
		}
	}
	public static BufferedImage toBufferedImage(Image img)
	{
		if (img instanceof BufferedImage)
		{
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

}


