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
import java.util.concurrent.BrokenBarrierException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;


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
	boolean doneRound;
	public static int winnerIndex;
	public int winnerEarnings;
	public static Vector<String> historyStrings = new Vector<String>();
//	CHAT**
	public void broadcast(String s, ServerThread sender){
//		System.out.println("BROADCAST: "+pokerPlayers.size());
		synchronized(PokerServer.pokerPlayers)
		{
		for(ServerThread st : PokerServer.pokerPlayers){
//			System.out.println("PS SENDING: "+s);
			if(st!=sender)
				st.send(s);
		}
		}
	}
//	**CHAT
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
				st.pokerServer = this;
				//ChatThread ct=new ChatThread(s,st,st.br);
				pokerPlayers.add(st);
				//System.out.println("hi");

				st.start();
				//st.pw.println("you connected!");
				//ct.start();
			}
		}
		catch (IOException ioe){
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	// NEWCODE

	public static void insertData(int player_id, int winnings) {
		Connection conn;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/final_project", "root", null);
			String query = "INSERT INTO winners (winner_player_id, winnings) VALUES (?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, ""+player_id);
			ps.setString(2, ""+winnings);
			ps.execute();
			System.out.println("SQL called");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// NEWCODE

	public static String getData() {
		Connection conn;
		String historyText = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/final_project", "root", null);
			String query = "SELECT * from winners";
			Statement stmt = conn.createStatement();	
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				//print out the right shit. so print out "#gameID: Player #winning_player_id won #winnings
				// vector of strings
				String hs = rs.getString("game_id")+":\t"+rs.getString("winner_player_id")+" won "+rs.getString("winnings");
				historyStrings.add(hs);
				System.out.println(hs);
			}

			for (String s : historyStrings) {
				historyText = historyText + s + "\n";
			}
			System.out.println(historyText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return historyText;

	}

	// THIS FUNCTION WILL BE CALLED IN THE ACTION LISTENER IN GUI CODE
	//	public static void showHistoryPopup(){
	//		
	//		String historyText = "";
	//		for (String s : historyStrings) {
	//			historyText = historyText + s + "\n";
	//		}
	//		
	//		
	//		JOptionPane.showMessageDialog(null, historyText, "Game History", JOptionPane.INFORMATION_MESSAGE);
	//	}

	public void run()
	{
		while(true)
		{
			if (PokerServer.pokerPlayers.size()>1)//handling starting rounds and "activating" players
			{
				boolean round=false;
				synchronized(PokerServer.pokerPlayers)
				{
				for(ServerThread st : PokerServer.pokerPlayers)
				{
					if(st.inRound==true)
					{
						round=true;
					}
				}
				}
				if(round==false)//no one is in a round, meaning either round has ended or game hasn't started
				{
					System.out.println("Starting a new game");
					synchronized(PokerServer.pokerPlayers)
					{
					for(ServerThread st : PokerServer.pokerPlayers)//starts round, hands out cards
					{



						
						try{
							sleep(1000);
						}
						catch(InterruptedException ie)
						{
							System.out.println("ie exception when sending Players:");
						}
						PokerServer.part_Players.add(st);//adds client to the vector of participating players for a round


						Card card1=PokerDeck.deal();//hands out cards
						String card1String=card1.getValueAsString()+ " " +card1.getSuitAsString();
						//st.pw.println(card1String);
						Card card2=PokerDeck.deal();
						String card2String=card2.getValueAsString()+" " +card2.getSuitAsString();
						st.pw.println("Players:"+(PokerServer.pokerPlayers.size()-1));
						st.pw.flush();
						st.pw.println("Hand:"+card1String + " "+card2String);
						st.pw.flush();
						st.pw.println("StartRound");//sends signal to a client to start a new round
						st.pw.flush();
						st.inRound=true;
						st.doneBet=false;
					}
					}
					/*	for(ServerThread st : PokerServer.part_Players)
					{
						st.pw.println("StartRound");//sends signal to a client to start a new round
						st.pw.flush();
						st.inRound=true;
						st.doneBet=false;
					}*/
				}

			}
			if(PokerServer.pokerPlayers.size()>1)
			{	

				int counter=0;
				//System.out.println("size of part_Players is : " + PokerServer.part_Players.size());
				synchronized(PokerServer.part_Players){
					for(ServerThread st : PokerServer.part_Players)
					{
						if(counter==0)
						{
							doneRound=true;
							counter++;
						}
						if(st.doneBet==false)
						{
							//System.out.println("A DONE BET IS FALSE");
							doneRound=false;
						}
						if(st.doneBet==true)
						{
							//System.out.println("I FOUDN A TRUE");

						}
					}
				}
				if(doneRound==true)
				{
					PokerServer.maxBet=0;
					count=0;
					System.out.println("do i get here");
					/*boolean foundDealer=false;
				while(foundDealer==false)
				{
				if(PokerServer.dealer<PokerServer.pokerPlayers.size())//updating the dealer
					{
						PokerServer.dealer++;
					}
					else
					{
						PokerServer.dealer=1;
					}

					for(ServerThread st : PokerServer.pokerPlayers)//checking that the correct dealer is also in the round
					{
						st.inTurn=false;
						if(st.turnOrder==PokerServer.dealer&&st.inRound==true)
						{
							System.out.println("found dealer, turn order is : " + st.turnOrder);
							foundDealer=true;
						}
					}
				}*/

					if(PokerServer.rounds==4||PokerServer.part_Players.size()==1)//finished last round, reset all variables
					{
						int maxRank=0;
						PokerDeck.shuffle();
						System.out.println("end of round game");
						if(PokerServer.dealer<PokerServer.pokerPlayers.size())
						{
							PokerServer.dealer++;
						}
						else
						{
							PokerServer.dealer=1;
						}
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

						synchronized(PokerServer.part_Players)
						{
							for(ServerThread st:PokerServer.part_Players)
							{
								if(st.Rank.equals(Winners.get(0)))
								{
									winnerIndex = st.turnOrder;
									winnerEarnings = PokerServer.MoneyPot/Winners.size();
									insertData(winnerIndex, winnerEarnings);
									//									getData();
									st.pw.println("Winner:"+(PokerServer.MoneyPot/Winners.size()));
									st.pw.flush();
								}
								else
								{
									st.pw.println("Loser");
									st.pw.flush();
								}

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
							System.out.println("sending 3 cards");
							Card card1=PokerDeck.deal();//hands out cards
							card1String=card1.getValueAsString()+" "+card1.getSuitAsString();
							//st.pw.println(card1String);
							Card card2=PokerDeck.deal();
							card2String=card2.getValueAsString()+" "+card2.getSuitAsString();
							//st.pw.println(card2String);
							Card card3=PokerDeck.deal();
							card3String = card3.getValueAsString()+" "+card3.getSuitAsString();
							//st.pw.println("Deal3:"+card1String+","+card2String+","+card3String);
						}
						else if (PokerServer.rounds==3||PokerServer.rounds==4)
						{
							Card card1=PokerDeck.deal();//hands out cards
							card1String=card1.getValueAsString()+" "+card1.getSuitAsString();
							//st.pw.println(card1String);

						}
						synchronized(PokerServer.part_Players)
						{
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
	public ChatThread(Socket s, ServerThread st, BufferedReader br){
		this.s=s;
		this.st=st;
		try{
			this.br=br;
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
				if(line.contains("Message:"))
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
	PokerServer pokerServer;
	private Socket s;
	public BufferedReader br;
	public PrintWriter pw;
	boolean started;//keeps track if a poker game has started or not
	public volatile boolean inRound;//keeps track if a player is currently participating in a round or not (either just connected in middle of a round or folded)
	public int amountBet;//keeps track of how much have bet during a round
	public volatile boolean inTurn;//keeps track of whose turn it is to bet
	public int turnOrder;//keeps track of the order of players
	HandRank Rank;
	int count;
	public volatile boolean doneBet; //keeps track of if a person has finished betting
//	CHAT**
	public void send(String s){
		System.out.println("SENDING: "+s);
		pw.println(s);
		pw.flush();
	}
//	**CHAT
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
			pw.println("connected yay");
			pw.flush();
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
						//pw.println(PokerServer.dealer);
						//pw.flush();
						//pw.println("turn is:" + turnOrder);
						//pw.flush();
						test++;
					}
					//first check if the round is going to end
					if(PokerServer.part_Players.size()==1)
					{
						doneBet=true;
						inTurn=false;
					}
					//determining order
					//System.out.println("inRound is true");
					if(PokerServer.dealer==turnOrder&&count==0)//this player will be the dealer for this round and start betting
					{
						pw.println("dealer!"+ " " + PokerServer.dealer+ " " + doneBet);
						pw.flush();
						//inTurn=true;
						//pw.println("Turn:0");
						//pw.flush();
						count++;
						inTurn=true;
					}
					if(inTurn==true&&doneBet==false)
					{
						//System.out.println("inTurn is true");
						if(amountBet<PokerServer.maxBet)//if have to call a raise
						{
							pw.println("Turn:"+ (PokerServer.maxBet-amountBet));
							pw.flush();
						}
						else if (amountBet==PokerServer.maxBet &&PokerServer.maxBet==0)//in case no one has bet yet
						{
							//System.out.println("i'm outputting turn:0");
							pw.println("Turn:0");
							pw.flush();
						}
						else//round is over
						{
							doneBet=true;
						}

						System.out.println("DONEBET: "+doneBet);
						//once its a players turn
						if(doneBet==false)
						{
							String line = "Blah";
							//							CHAT**
							//							while(!line.contains("Fold")&&!line.contains("Call:")&&!line.contains("Raise:")){
							while(!line.contains("Fold")&&!line.contains("Call:")&&!line.contains("Raise:")&&!line.contains("CHAT:")){
								//								**CHAT
								line=br.readLine();
								System.out.println(line);
								//								CHAT**
								if(line.contains("CHAT:")){
									this.pokerServer.broadcast(line, this);
								}
								//							if(line.equals("Fold"))//user folded
								//								**CHAT
								else if(line.equals("Fold"))//user folded
								{
									PokerServer.part_Players.remove(this);
									inRound=false;
									amountBet=0;
									inTurn=false;

									boolean found=false;/*
							if(PokerServer.part_Players.size()==1)//if only one person left after fold
							{

								if(PokerServer.dealer<PokerServer.pokerPlayers.size())//updating the dealer
								{
									PokerServer.dealer++;
								}
								else
								{
									PokerServer.dealer=1;
								}
								PokerServer.part_Players.get(0).inRound=false;

								PokerServer.part_Players.get(0).amountBet=0;
								//PokerServer.part_Players.get(0).inTurn=false;
								PokerServer.maxBet=0;
								//PokerServer.part_Players.get(0).doneBet=false;
								PokerServer.part_Players.get(0).pw.println("Done");
								PokerServer.part_Players.get(0).pw.flush();
								PokerServer.part_Players.get(0).pw.println("Winner:"+PokerServer.MoneyPot);
								PokerServer.part_Players.get(0).pw.flush();
								PokerServer.part_Players.get(0).count=0;
								PokerServer.rounds=1;
							//	PokerServer.part_Players.removeAllElements();
								PokerServer.MoneyPot=0;
								PokerServer.player_Hands.clear();
							}*/
									//if(PokerServer.part_Players.size()>1){
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
									//		}
								}
								else if (line.contains("Raise:"))//reads in a bet and updates the public pot 
								{

									inTurn=false;
									int betAmount=Integer.parseInt(line.substring(6,line.length()));
									PokerServer.MoneyPot=PokerServer.MoneyPot+betAmount;
									amountBet=amountBet+betAmount;
									PokerServer.maxBet=amountBet;

									boolean found=false;
									synchronized(PokerServer.part_Players)
									{
										for(ServerThread st: PokerServer.part_Players)//update all players to have to continue matching the raise
										{
											if(st!=this)
											{
												st.doneBet=false;
											}
										}
									}
									doneBet=true;
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
									System.out.println("in call");
									doneBet=true;
									inTurn=false;
									int betAmount=Integer.parseInt(line.substring(5,line.length()));
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
											if(st.inRound==true&&st!=this)
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
		}
		catch(IOException ioe){
			System.out.println("Client disconnected from " + s.getInetAddress());
		}


	}
}