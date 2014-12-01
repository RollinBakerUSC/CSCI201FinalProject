package finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;


public class Player extends Thread{

	private Card[] pocketCards;
	ArrayList<Card> commonCards;
	private String name;
	private int money;
	private HandRank bestHand = null;
	private BufferedReader br;
	private PrintWriter pw;
	
	public Player(String name, String hostname, int port){
		
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
	
	public Player(String name, int money, String hostname, int port){
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
		this.commonCards = new ArrayList<Card>();
		this.start();
	}
	
	public void run(){
		String messageFromServer;
		try {
			while(true){
				messageFromServer = br.readLine();
				System.out.println(messageFromServer);
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
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.println("Player name?");
		String name = scan.nextLine();
		System.out.println("Server name?");
		String hostname = scan.nextLine();
		System.out.print("What is the port? ");
		int port = scan.nextInt();

		Player rollin = new Player( name, 500, hostname, port);

		
		
		
	}
}
