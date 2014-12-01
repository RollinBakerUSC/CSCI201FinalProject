package finalproject;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;


public class PokerServer extends Thread{
	public static Vector<ServerThread> pokerPlayers= new Vector<ServerThread>();
	public static Vector<ServerThread> part_Players= new Vector<ServerThread>();
	public static ArrayList<HandRank> player_Hands=new ArrayList<HandRank>();
	public static int MoneyPot;
	public static int maxBet;
	public static int turnIndex;//keeps track of the order of players
	public static int dealer;
	public static int rounds;
	public static int count;//used to make sure dealer only gets assigned once
	public static Deck PokerDeck; //to be implemented when I receive the Deck class
	public PokerServer(int port){
		try{
			ServerSocket ss = new ServerSocket(port);
			MoneyPot=0;
			turnIndex=1;
			dealer=1;//not sure if i want to do this line yet
			rounds=1;
			count=0;
			//deck initialization
			
			 PokerDeck=new Deck();
			 PokerDeck.shuffle();
			 
			this.start();
			while(true){
				System.out.println("Waiting for connections...");
				Socket s=ss.accept();
				System.out.println("Connection from" + s.getInetAddress());
				ServerThread st = new ServerThread(s);
				ChatThread ct=new ChatThread(s,st);
				pokerPlayers.add(st);
				//System.out.println("hi");
				
				st.start();
				//st.pw.println("you connected!");
				ct.start();
			}
		}
		catch (IOException ioe){
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void run()
	{
		while(true)
		{
			if (PokerServer.pokerPlayers.size()>1)//handling starting rounds and "activating" players
			{
				boolean round=false;
				for(ServerThread st : PokerServer.pokerPlayers)
				{
					if(st.inRound==true)
					{
						round=true;
					}
				}
				if(round==false)//no one is in a round, meaning either round has ended or game hasn't started
				{
					for(ServerThread st : PokerServer.pokerPlayers)//starts round, hands out cards
					{
					st.pw.println("StartRound");//sends signal to a client to start a new round
					st.pw.flush();
					PokerServer.part_Players.add(st);//adds client to the vector of participating players for a round
					st.inRound=true;
					st.doneBet=false;
					Card card1=PokerDeck.deal();//hands out cards
					String card1String=card1.getValueAsString()+" "+card1.getSuitAsString();
					//st.pw.println(card1String);
					Card card2=PokerDeck.deal();
					String card2String=card2.getValueAsString()+ " " +card2.getSuitAsString();
					st.pw.println("Hand:"+card1String + " "+card2String);
					st.pw.flush();
					}
				}
				
			}
			if(PokerServer.pokerPlayers.size()>1)
			{	
			boolean doneRound=true;
			for(ServerThread st : PokerServer.part_Players)
			{
				if(st.doneBet==false)
				{
					doneRound=false;
				}
			}
			
			if(doneRound==true)
			{
				PokerServer.maxBet=0;
				count=0;
				System.out.println("do i get here");
				if(PokerServer.dealer<PokerServer.pokerPlayers.size())//updating the dealer
				{
					PokerServer.dealer++;
				}
				else
				{
					PokerServer.dealer=1;
				}
			
				if(PokerServer.rounds==4)//finished last round, reset all variables
				{
					int maxRank=0;
					PokerDeck.shuffle();
					System.out.println("end of round game");
					for(ServerThread st:PokerServer.part_Players)
					{
						st.inRound=false;
						st.inTurn=false;
						st.amountBet=0;
						st.doneBet=false;
						st.pw.println("Done");
						st.pw.flush();
						st.count=0;
						PokerServer.rounds=1;
						//insert code for determining winner
						try{
						String dummy=st.br.readLine();//or just access 
						dummy=dummy.substring(9,dummy.length());
						Scanner scan= new Scanner(dummy);
						int num1=scan.nextInt();
						int num2=scan.nextInt();
						int num3=scan.nextInt();
						int num4=scan.nextInt();
						int num5=scan.nextInt();
						int num6=scan.nextInt();
						st.Rank=new HandRank(num1,num2,num3,num4,num5,num6);
						PokerServer.player_Hands.add(st.Rank);
						}
						catch(IOException ioe)
						{
							System.out.println("ioe: " + ioe.getMessage());
						}
						
					}
					System.out.println(PokerServer.player_Hands.size());
					ArrayList<HandRank> Winners=CompareHands.getWinningPlayer(PokerServer.player_Hands);
					
					
					for(ServerThread st:PokerServer.part_Players)
					{
						if(st.Rank.equals(Winners.get(0)))
						{
							st.pw.println("Winner:"+(PokerServer.MoneyPot/Winners.size()));
							st.pw.flush();
						}
						else
						{
							st.pw.println("Loser");
							st.pw.flush();
						}
					
					}
					
					PokerServer.part_Players.removeAllElements();
					PokerServer.MoneyPot=0;
					PokerServer.player_Hands.clear();
				}
				else{//more rounds to go
					//pop card TO DO
					PokerServer.rounds++;
					String card1String=null;
					String card2String=null;
					String card3String=null;
					if(PokerServer.rounds==2)//3 cards
					{
						Card card1=PokerDeck.deal();//hands out cards
						 card1String=card1.getValueAsString()+ " " + card1.getSuitAsString();
						//st.pw.println(card1String);
						Card card2=PokerDeck.deal();
						 card2String=card2.getValueAsString()+ " " +card2.getSuitAsString();
						//st.pw.println(card2String);
						Card card3=PokerDeck.deal();
						card3String = card3.getValueAsString()+ " " +card3.getSuitAsString();
						//st.pw.println("Deal3:"+card1String+","+card2String+","+card3String);
					}
					else if (PokerServer.rounds==3||PokerServer.rounds==4)
					{
						Card card1=PokerDeck.deal();//hands out cards
						 card1String=card1.getValueAsString()+ " " +card1.getSuitAsString();
						//st.pw.println(card1String);

					}
					for(ServerThread st:PokerServer.part_Players)
					{
						//send each thread the cards TO DO
						st.amountBet=0;
						st.doneBet=false;
						st.inTurn=false;
						st.count=0;
					//UPDATE DEALER HERE???
						if(PokerServer.rounds==2)
						{
						st.pw.println("Deal3:"+card1String+" "+card2String+" "+card3String);
						st.pw.flush();
						}
						else if (PokerServer.rounds==3||PokerServer.rounds==4)
						{
							st.pw.println("Deal1:"+card1String);
							st.pw.flush();
						}
						
						
					}
				}
			}
		}
		}
	}
	
	
	
	public static void main (String [] args)
	{
		Scanner scan=new Scanner(System.in);
		System.out.print("What port? " );
		int port = scan.nextInt();
		new PokerServer(port);
	}
}


class ChatThread extends Thread
{
	private Socket s;
	public BufferedReader br;
	public PrintWriter pw;
	public ServerThread st;
	public ChatThread(Socket s, ServerThread st){
		this.s=s;
		this.st=st;
		try{
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter (s.getOutputStream());
		}
		catch(IOException ioe)
		{
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	public void run()
	{
		while(true)
		{
			try{
				String line=br.readLine();
				if(line != null && line.contains("Message:"))
				{
					String message=line.substring(8,line.length());
					for(ServerThread client : PokerServer.pokerPlayers)
					{
						if (client!=st)
						{
							client.pw.println("Message:"+message);
							st.pw.flush();
						}
					}
				}
			}
			catch(IOException ioe)
			{
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
	}
}


class ServerThread extends Thread
{
	private Socket s;
	public BufferedReader br;
	public PrintWriter pw;
	boolean started;//keeps track if a poker game has started or not
	boolean inRound;//keeps track if a player is currently participating in a round or not (either just connected in middle of a round or folded)
	public int amountBet;//keeps track of how much have bet during a round
	boolean inTurn;//keeps track of whose turn it is to bet
	public int turnOrder;//keeps track of the order of players
	HandRank Rank;
	int count;
	boolean doneBet; //keeps track of if a person has finished betting
	public ServerThread(Socket s){
		this.s=s;
		inRound=false;
		inTurn=false;
		doneBet=false;
		amountBet=0;
		//handRank=0;
		
		turnOrder=PokerServer.turnIndex;//sets your turn order based on order in which you join the server
		System.out.println("turnOrder is:" + turnOrder);
		PokerServer.turnIndex=PokerServer.turnIndex+1;//updates the turn index
		if(PokerServer.pokerPlayers.size()>1)//make sure the game doesn't start until at least 2 players connected
		{
			started=true;
			
		}
		else
			started=false;
		try{
			
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter (s.getOutputStream());
			System.out.println("hi");
			//pw.println("connected yay");
			//pw.flush();
		}
		catch(IOException ioe)
		{
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void run()
	{
		try{
			 PokerServer.count=0;
			 pw.println("in run of serverthread");
			 pw.flush();
			 int tester=0;
			 int test=0;
			while(true)
			{
				
				if(tester==0)
				{
				//System.out.println(inRound);
				}
				tester=tester+1;
				 if (inRound==true)
				{
					
					 if(test==0)
					 {
					pw.println(PokerServer.dealer);
					pw.flush();
					pw.println("turn is:" + turnOrder);
					pw.flush();
					test++;
					 }
					//first check if the round is going to end
					
					//determining order
					//System.out.println("inRound is true");
					if(PokerServer.dealer==turnOrder&&count==0)//this player will be the dealer for this round and start betting
					{
						pw.println("dealer!"+ " " + PokerServer.dealer);
						pw.flush();
						//inTurn=true;
						pw.println("Turn:0");
						pw.flush();
						count++;
					}
					if(inTurn==true)
					{
						if(amountBet<PokerServer.maxBet)//if have to call a raise
						{
						pw.println("Turn:"+ (PokerServer.maxBet-amountBet));
						pw.flush();
						}
						else if (amountBet==PokerServer.maxBet &&PokerServer.maxBet==0)//in case no one has bet yet
						{
							pw.println("Turn:0");
							pw.flush();
						}
						else//round is over
						{
							doneBet=true;
						}
						
					
					//once its a players turn
					if(doneBet==false)
					{
					String line = br.readLine();
					
						if(line.equals("Fold"))//user folded
						{
							PokerServer.part_Players.remove(this);
							inRound=false;
							amountBet=0;
							inTurn=false;
						
							boolean found=false;
							if(PokerServer.part_Players.size()==1)//if only one person left after fold
							{
								PokerServer.part_Players.get(0).inRound=false;
								PokerServer.part_Players.get(0).amountBet=0;
								PokerServer.part_Players.get(0).inTurn=false;
								PokerServer.maxBet=0;
								PokerServer.part_Players.get(0).doneBet=false;
								PokerServer.part_Players.get(0).pw.println("Done");
								PokerServer.part_Players.get(0).pw.flush();
								PokerServer.part_Players.get(0).pw.println("Winner:"+PokerServer.MoneyPot);
								PokerServer.part_Players.get(0).pw.flush();
								PokerServer.part_Players.get(0).count=0;
								PokerServer.rounds=1;
								PokerServer.part_Players.removeAllElements();
								PokerServer.MoneyPot=0;
								PokerServer.player_Hands.clear();
							}
							else{
							for(ServerThread st: PokerServer.pokerPlayers)//determining the next person betting
							{
								if(st.inRound==true&&st.turnOrder>turnOrder)
								{
									st.inTurn=true;
									found=true;
								}
							}
							if(found==false)
							{
								for(ServerThread st: PokerServer.pokerPlayers)
								{
									if(st.inRound==true)
									{
										st.inTurn=true;
										
									}
								}
							}
						}
						}
						else if (line.contains("Raise:"))//reads in a bet and updates the public pot 
						{
							doneBet=true;
							inTurn=false;
							int betAmount=Integer.parseInt(line.substring(6,line.length()));
							PokerServer.MoneyPot=PokerServer.MoneyPot+betAmount;
							amountBet=amountBet+betAmount;
							PokerServer.maxBet=amountBet;
							boolean found=false;
							for(ServerThread st: PokerServer.pokerPlayers)//update all players to have to continue matching the raise
							{
								if(st!=this)
								{
									st.doneBet=false;
								}
							}
							for(ServerThread st: PokerServer.pokerPlayers)//determining the next person betting
							{
								if(st.inRound==true&&st.turnOrder>turnOrder)
								{
									st.inTurn=true;
									found=true;
								}
							}
							if(found==false)
							{
								for(ServerThread st: PokerServer.pokerPlayers)
								{
									if(st.inRound==true)
									{
										st.inTurn=true;
										
									}
								}
							}
						}	
						else if (line.contains("Call:"))
						{
							doneBet=true;
							inTurn=false;
							int betAmount=Integer.parseInt(line.substring(4,line.length()));
							PokerServer.MoneyPot=PokerServer.MoneyPot + betAmount;
							amountBet=amountBet+betAmount;
							boolean found=false;
							for(ServerThread st: PokerServer.pokerPlayers)//determining the next person betting
							{
								if(st.inRound==true&&st.turnOrder>turnOrder)
								{
									st.inTurn=true;
									found=true;
								}
							}
							if(found==false)
							{
								for(ServerThread st: PokerServer.pokerPlayers)
								{
									if(st.inRound==true)
									{
										st.inTurn=true;
										
									}
								}
							}
						}
					}
					}
					
				}
				
				
				
			}
		}
		catch(IOException ioe){
			System.out.println("Client disconnected from " + s.getInetAddress());
		}
		
		
	}
}