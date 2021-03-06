package finalproject;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;

import finalproject.HandRank.HandType;


public class Player extends Thread{

	private Card[] pocketCards;
	ArrayList<Card> commonCards;
	private String name;
	private int money;
	int amount;
	private int moneyBetThisRound;
	private HandRank bestHand = null;
	public BufferedReader br;
	public PrintWriter pw;
	private List<String> serverCommands;
	private ReentrantLock bufferAccess;
	private Condition messageReceived;
	private GUIBoard board;
	String historyText;
	
	public Player(String name, String hostname, int port, GUIBoard board){
		this.board = board;
		bufferAccess = new ReentrantLock();
		messageReceived = bufferAccess.newCondition();
		serverCommands = Collections.synchronizedList(new ArrayList<String>());
		
		Socket s;
		try {
			s = new Socket(hostname, port);
			this.pw = new PrintWriter(s.getOutputStream());
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		this.name = name;
		this.money = 0;
		this.commonCards = new ArrayList<Card>();
		this.start();
	}
	
	public Player(String name, int money, String hostname, int port, GUIBoard board){
		this.board = board;
		bufferAccess = new ReentrantLock();
		messageReceived = bufferAccess.newCondition();
		serverCommands = Collections.synchronizedList(new ArrayList<String>());
		this.board.gameBoard.setMoney(Integer.toString(money));
		this.board.gameBoard.setUpCall(amount);
		Socket s;
		try {
			s = new Socket(hostname, port);
			this.pw = new PrintWriter(s.getOutputStream());
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		this.name = name;
		this.money = money;
		Integer tempMoney=new Integer (money);
		board.gameBoard.setMoney (tempMoney.toString());
		board.gameBoard.setUpCall (0);
		this.commonCards = new ArrayList<Card>();
		this.start();
	}
	
	public void run(){
		String messageFromServer;
		try {
			while(true){
				//System.out.println("Before readline");
				messageFromServer = br.readLine();
				//System.out.println("After readline");
				if(messageFromServer != null){
					System.out.println("trying to print this line " + messageFromServer);
					//synchronized(serverCommands){
						System.out.println("In " + name + "'s run function in synch trying to print " + messageFromServer);
						//serverCommands.add(messageFromServer);
//						CHAT**
						
						if(messageFromServer.contains("CHAT: ")){
							messageFromServer = messageFromServer.substring(6);
							board.showMessage(messageFromServer);
						}
//						if(messageFromServer.contains("StartRound")){
//						**CHAT
						else if(messageFromServer.contains("StartRound")){
							System.out.println("Round has started");
						}
						else if(messageFromServer.contains("Hand")){
							try{
								sleep(2000);
							}
							catch(InterruptedException ie)
							{
								System.out.println("interrupted during deal");
							}
							String cards = messageFromServer.substring(messageFromServer.indexOf(':')+1);
							Scanner cardParser = new Scanner(cards);
							String card1ValString = cardParser.next();
							String card1SuitString = cardParser.next();
							Card c1 = getCardFromString(card1ValString, card1SuitString);
							
							String card2ValString = cardParser.next();
							String card2SuitString = cardParser.next();
							Card c2 = getCardFromString(card2ValString, card2SuitString);
							
							setHand(c1, c2);//setHand(c1, c2);
							System.out.println("Received hand in response to HAND command from server");
							printHand();
						}
						else if(messageFromServer.contains("Done")){
							board.gameBoard.clearCommonCards();
							getBest5CardHand();
							HandRank hr = getHandRank();
							int handType;
							switch(hr.handType){
							case HIGHCARD:
								handType = 0;
								break;
							case ONEPAIR:
								handType = 1;
								break;
							case TWOPAIR:
								handType = 2;
								break;
							case THREEOFAKIND:
								handType = 3;
								break;
							case STRAIGHT:
								handType = 4;
								break;
							case FLUSH:
								handType = 5;
								break;
							case FULLHOUSE:
								handType = 6;
								break;
							case FOUROFAKIND:
								handType = 7;
								break;
							case STRAIGHTFLUSH:
								handType = 8;
								break;
							case ROYALFLUSH:
								handType = 9;
								break;
							default:
								handType = 10;
								break;
							}
							if(handType == 10){
								System.err.println("Bad handrank conversion in respone to DONE message");
							}
							String handRankString ="HandRank:" + handType + " " + hr.highCardRank 
									+ " " + hr.secondHighCard + " " + hr.thirdHighCard + " " + hr.fourthHighCard
									+ " " + hr.fifthHighCard;
							pw.println(handRankString);
							pw.flush();
							
						}
						//serverCommands.remove(0);
						else if(messageFromServer.contains("Turn:")){
							
							//TODO: In GUI, activate buttons
							String amountString = messageFromServer.substring(messageFromServer.indexOf(':')+1);
							amount = Integer.parseInt(amountString);
							this.board.gameBoard.setUpCall (amount);
							this.board.gameBoard.enableButtons();
							//Scanner userInput = new Scanner(System.in);
							//System.out.println("What do you want to do? Amount to call is " + amount);
							//String playerResponse = userInput.nextLine();
							//Player should input in form we want to send
							try {
								synchronized(board.gameBoard){
									board.gameBoard.wait();
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
//							if(playerResponse.contains("Call:")){
//								pw.println(playerResponse);
//								//pw.println("This is a response");
//								money-= (amount - moneyBetThisRound);
//								System.out.println(playerResponse);
//								pw.flush();
//								System.out.println("foo");
//							}
//							else if(playerResponse.contains("Raise:")){
//								pw.println(playerResponse);
//								String raiseString = messageFromServer.substring(messageFromServer.indexOf(':')+1);
//								int raiseAmount = Integer.parseInt(raiseString);
//								money -= ((amount + raiseAmount) - moneyBetThisRound);
//								pw.flush();
//							}
//							else if(playerResponse.contains("Fold")){
//								this.commonCards.clear();
//								this.pocketCards = null;
//								this.moneyBetThisRound = 0;
//								pw.println(playerResponse);
//								pw.flush();
//							}
//							
						}
						else if(messageFromServer.contains("Winner")){
							String amountString = messageFromServer.substring(messageFromServer.indexOf(':')+1, messageFromServer.indexOf('@'));
							int amount = Integer.parseInt(amountString);
							money += amount;
							String intermediate = messageFromServer.substring(messageFromServer.indexOf('@')+1);
							historyText = intermediate.replaceAll("\\*\\*\\*", "\n");
//							StringTokenizer tokenizer = new StringTokenizer(intermediate, "@@@###@@@");
//							historyText = "";
//							while(tokenizer.hasMoreTokens()){
//								String tokStr = tokenizer.nextToken();
//								if(tokStr.length()!=0)
//									historyText+=tokStr+"\n";
//							}
							System.out.println(historyText);
							Integer temp= new Integer (money);
							this.board.gameBoard.setMoney (temp.toString());
							this.commonCards.clear();
							this.pocketCards = null;
							this.moneyBetThisRound = 0;
							JOptionPane.showMessageDialog (this.board, "Winner Winner, Chicken Dinner!");
							
						}
						else if(messageFromServer.contains("Loser")){
							String intermediate = messageFromServer.substring(messageFromServer.indexOf('@')+1);
							historyText = intermediate.replaceAll("\\*\\*\\*", "\n");
							System.out.println(historyText);
							this.commonCards.clear();
							this.pocketCards = null;
							this.moneyBetThisRound = 0;
							JOptionPane.showMessageDialog (this.board, "Ha, You Lost.");
							
						}
						else if (messageFromServer.contains("Players"))
						{
							System.out.println("im at the players statement");
							board.gameBoard.clearPlayerCount();
							String temp=messageFromServer;
							int numPlayers=Integer.parseInt(temp.substring(8,temp.length()));
							for(int i= 0; i<numPlayers;i++ )
							{
								board.gameBoard.setOtherPlayerCards();
							}
						}
						else if(messageFromServer.contains("Deal")){
							
							String temp = new String(messageFromServer);
							int numCards = getFirstIntFromString(temp);
							String cards = messageFromServer.substring(messageFromServer.indexOf(':')+1);
							Scanner cardParser = new Scanner(cards);
							
							if(numCards == 1){
								String card1ValString = cardParser.next();
								String card1SuitString = cardParser.next();
								Card c1 = getCardFromString(card1ValString, card1SuitString);
								board.gameBoard.updateCommonCards(c1);
								commonCards.add(c1);
							}
							else if(numCards == 3){
								String card1ValString = cardParser.next();
								String card1SuitString = cardParser.next();
								Card c1 = getCardFromString(card1ValString, card1SuitString);
								board.gameBoard.updateCommonCards(c1);
								commonCards.add(c1);
								
								String card2ValString = cardParser.next();
								String card2SuitString = cardParser.next();
								Card c2 = getCardFromString(card2ValString, card2SuitString);
								board.gameBoard.updateCommonCards(c2);
								commonCards.add(c2);
								
								String card3ValString = cardParser.next();
								String card3SuitString = cardParser.next();
								Card c3 = getCardFromString(card3ValString, card3SuitString);
								board.gameBoard.updateCommonCards(c3);
								commonCards.add(c2);
							}
						}
						System.out.println(messageFromServer);
						//serverCommands.notify();
					}
					
					//Thread.yield();
				//}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setHand(Card c1, Card c2){
		if(c1 == null || c2 == null){
			System.out.println("One of the cards dealt to " + this.name + " is null");
			return;
		}
		pocketCards = new Card[2];
		pocketCards[0] = c1;
		pocketCards[1] = c2;
		board.gameBoard.setYourCards(pocketCards[0],pocketCards[1]);
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
		this.bestHand.name = this.name;
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
	
	public String getPlayerName(){
		return this.name;
	}
	
	/*public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.println("Player name?");
		String name = scan.nextLine();
		System.out.println("Server name?");
		String hostname = scan.nextLine();
		System.out.print("What is the port? ");
		int port = scan.nextInt();

		Player rollin = new Player( name, 500, hostname, port);
		
		
		
	}*/
	public Card getCardFromString(String cardValString, String cardSuitString){
		int cardVal;
		//System.out.println("In get card from string: ca")
		
		if(cardValString.equals("Ace")){
			cardVal = 1;
		}
		else if(cardValString.equals("King")){
			cardVal = 13;
		}
		else if(cardValString.equals("Queen")){
			cardVal = 12;
		}
		else if(cardValString.equals("Jack")){
			cardVal = 11;
		}
		else{
			cardVal = Integer.parseInt(cardValString);
		}
		
		Card card = new Card(cardVal, cardSuitString);
		
		return card;
	}
	
	Integer getMoney(){
		return this.money;
	}
	
	void setBet(Integer betAmount){
		if(betAmount == amount){
			pw.println("Call:" + betAmount);
		}
		else{
			pw.println("Raise:" + betAmount);
		}
		pw.flush();
		this.money=this.money-(betAmount);
		moneyBetThisRound = betAmount + moneyBetThisRound;
		Integer tempMoney= new Integer (this.money);
		board.gameBoard.setMoney (tempMoney.toString());
	}

	void foldCards(){
		this.commonCards.clear();
		this.pocketCards = null;
		this.moneyBetThisRound = 0;
		pw.println("Fold");
		pw.flush();
	}
	
	public int getFirstIntFromString(String string){
		String firstNumber = string.replaceFirst(".*?(\\d+).*", "$1");
		int firstNum = Integer.parseInt(firstNumber);
		
		return firstNum;
	}
}
