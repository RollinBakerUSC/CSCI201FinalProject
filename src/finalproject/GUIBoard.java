package finalProj;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	//left:	
		//get actual card images
		//get table image
	
	//everything is stored here, so this has the grid bag layout
	GamePanel gameBoard;

	//this is being updated so you want to have it be independent of the GUI
	//you start off with 1000
	JLabel money; 

	//you have a player cause the GUIBoard is from the perspective of a player
	Player pokerPlayer;
	public GUIBoard (){
		super ("Can't Read My Poker Face");
		setSize (800, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		String name= (String)JOptionPane.showInputDialog (this,"What's your name?", 
				"Hello", JOptionPane.PLAIN_MESSAGE);
		pokerPlayer=new Player (name, 1000.0);

		gameBoard=new GamePanel(new GridBagLayout(), this);

		//pretty self explanatory stuff here, make a menubar then menus
		//add to the menus
		String labelTemp= pokerPlayer.getMoney().toString();
		money= new JLabel (labelTemp);

		JMenuBar menu= new JMenuBar();
		JMenu game= new JMenu("Game");
		JMenu acct= new JMenu("Account");

		JMenuItem newGame= new JMenuItem ("New Game");
		newGame.addActionListener(new nextGameListen(this));

		JMenuItem quitGame= new JMenuItem ("Quit Game");
		quitGame.addActionListener (new quitGameListen(this));

		JMenuItem export= new JMenuItem ("Export Data");
		export.addActionListener (new exportListen());

		JMenuItem stats= new JMenuItem ("Statistics");
		stats.addActionListener (new statsListen());

		acct.add (export);
		acct.add (stats);
		game.add (newGame);
		game.add (quitGame);

		menu.add (game);
		menu.add (acct);

		//buttons for betting and folding
		JButton bet= new JButton ("Bet");
		bet.addActionListener (new betListen (this));

		JButton fold= new JButton ("Fold");

		//gameBoard.money= new JLabel ("1000");

		gameBoard.add (bet);
		gameBoard.add (fold);

		//this is just a placeholder string
		//you want to start off with the player's money
		gameBoard.add (money);

		add (gameBoard);
		setJMenuBar (menu);
		setVisible(true);
	}

	class betListen implements ActionListener {
		GUIBoard gui;
		public betListen (GUIBoard game){
			gui=game;
		}

		public void actionPerformed (ActionEvent e){
			//you want the user to specify how much they're betting

			String amount= (String)JOptionPane.showInputDialog (gui,"Enter betting amount: ", 
					"Betting", JOptionPane.PLAIN_MESSAGE);
			//Update player money here...
			try {
				Double bet= Double.parseDouble(amount);

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
		}
	}

	class nextGameListen implements ActionListener {
		private GUIBoard gui;
		public nextGameListen (GUIBoard window){
			gui=window;
		}

		public void actionPerformed (ActionEvent e){
			gui.setVisible(false);
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

	class exportListen implements ActionListener{
		public void actionPerformed (ActionEvent e){

		}
	}

	class statsListen implements ActionListener{
		public void actionPerformed (ActionEvent e){

		}
	}

	public static void main (String [] args){
		GUIBoard holdem= new GUIBoard();
	}

}
