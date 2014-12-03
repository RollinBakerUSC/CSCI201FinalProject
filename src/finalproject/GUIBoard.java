package finalproject;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class GUIBoard extends JFrame {

	//to do:	
		//fix alignment
		//add functionality of export and stats 
		//dealer image
		//get table image
	
	//everything is stored here, so this has the grid bag layout
	GamePanel gameBoard;

	//this is being updated so you want to have it be independent of the GUI
	//you start off with 1000
	//JLabel money; 
	boolean isDealer=false;
	
	//you have a player cause the GUIBoard is from the perspective of a player
	Player pokerPlayer;
//	CHAT**
	public void showMessage(String s){
		JOptionPane.showMessageDialog(null, s, "New Message!", JOptionPane.INFORMATION_MESSAGE);
	}
	
//	**CHAT
	public GUIBoard (){
		super ("Can't Read My Poker Face");
		setSize (800, 600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		String name= (String)JOptionPane.showInputDialog (this,"What's your name?", 
				"Hello", JOptionPane.PLAIN_MESSAGE);
		String hostname= (String)JOptionPane.showInputDialog (this, "What hostname would you like to connect to?",
				"Connectivity", JOptionPane.PLAIN_MESSAGE);
		String port= (String)JOptionPane.showInputDialog (this, "What port would you like to connect to?",
				"Connectivity", JOptionPane.PLAIN_MESSAGE);
		
		Integer connectToPort;
		try{
			connectToPort= Integer.parseInt(port);
		}
		
		catch (Exception e){
			JOptionPane.showMessageDialog (this, "Using default port 1500.");
			connectToPort=1500;
		}
		
		
		
		//pretty self explanatory stuff here, make a menubar then menus
		//add to the menus
		JMenuBar menu= new JMenuBar();
		JMenu game= new JMenu("Game");
		JMenu acct= new JMenu("Account");
		

		JMenuItem newGame= new JMenuItem ("New Game");
		newGame.addActionListener(new nextGameListen(this));

		JMenuItem quitGame= new JMenuItem ("Quit Game");
		quitGame.addActionListener (new quitGameListen(this));

		/*JMenuItem export= new JMenuItem ("Export Data");
		export.addActionListener (new exportListen());
*/
		JMenuItem stats= new JMenuItem ("Statistics");
		stats.addActionListener (new statsListen(this));

		//acct.add (export);
		acct.add (stats);
		game.add (newGame);
		game.add (quitGame);

		menu.add (game);
		menu.add (acct);
//		CHAT**
			JMenu chat = new JMenu("Chat");
			JMenuItem newChat = new JMenuItem("New Chat");
			menu.add(chat);
			chat.add(newChat);
			newChat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String msg = JOptionPane.showInputDialog(null, "", "New Message!", 1);
					if(msg.compareTo("")!=0)
						msg = "CHAT: "+msg;
						pokerPlayer.pw.println(msg);
						pokerPlayer.pw.flush();
						System.out.println(msg);
				}
			});
		
//		**CHAT

		pokerPlayer=new Player (name, 500, hostname, connectToPort, this);
	
		gameBoard=new GamePanel(null, this);
		//gameBoard.add (money);
		add (gameBoard);
		setJMenuBar (menu);
		setVisible(true);

		
	}

	/*class betListen implements ActionListener {
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
				if (bet>pokerPlayer.getMoney()){
					JOptionPane.showMessageDialog(gui, "Insufficient Funds.");
				}

				else{
					pokerPlayer.setBet(bet);
					gui.money.setText(pokerPlayer.getMoney().toString());
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
			gui.gameBoard.foldCards();
			//send out some message
			
		}
	}
*/
	class nextGameListen implements ActionListener {
		private GUIBoard gui;
		public nextGameListen (GUIBoard window){
			gui=window;
		}

		public void actionPerformed (ActionEvent e){
			gui.setVisible(false);
			//gui.dispose();
			gui= new GUIBoard();
		}
	}

	class quitGameListen implements ActionListener {
		private GUIBoard gui;
		public quitGameListen (GUIBoard window){
			gui=window;
		}

		public void actionPerformed (ActionEvent e){
			/*	gui.setVisible (false);
			gui.removeAll();
			 */	gui.dispose();
		}
	}

	/*class exportListen implements ActionListener{
		public void actionPerformed (ActionEvent e){
			
		}
	}
*/
	class statsListen implements ActionListener{
		GUIBoard gui;
		public statsListen (GUIBoard game){
			gui=game;
		}
		
		public void actionPerformed (ActionEvent e){
			//PokerServer.showHistoryPopup();
		}
	}

	public static void main (String [] args){
		GUIBoard holdem= new GUIBoard();
	}

}
