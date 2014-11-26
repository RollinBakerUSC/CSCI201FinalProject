package finalproject;

import java.util.ArrayList;

public class CompareHands {

	static ArrayList<Player> getWinningPlayer(ArrayList<Player> players){
		//Assume you have not called get best five card hand
		ArrayList<Player> winners = new ArrayList<Player>();
		Player winner = players.get(0);
		Player secondWinner = null;
		Player thirdWinner = null;
		Player fourthWinner = null;
		Player fifthWinner = null;
		Player sixthWinner = null;
		Player seventhWinner = null;
		Player eighthWinner = null;
		for(int i = 0; i < players.size(); i++){
			players.get(i).getBest5CardHand();
		}
		for(int i = 1; i < players.size(); i++){
			if(players.get(i).getHandRank().compareTo(winner.getHandRank()) > 0){
				if(secondWinner != null){
					secondWinner = null;
					thirdWinner = null;
					fourthWinner = null;
					fifthWinner = null;
					sixthWinner = null;
					seventhWinner = null;
					eighthWinner = null;
				}
				winner = players.get(i);
			}
			else if(players.get(i).getHandRank().compareTo(winner.getHandRank()) == 0){
				if(secondWinner == null){
					secondWinner = players.get(i);
				}
				else if(thirdWinner == null){
					thirdWinner = players.get(i);
				}
				else if(fourthWinner == null){
					fourthWinner = players.get(i);
				}
				else if(fifthWinner == null){
					fifthWinner = players.get(i);
				}
				else if(sixthWinner == null){
					sixthWinner = players.get(i);
				}
				else if(seventhWinner == null){
					seventhWinner = players.get(i);
				}
				else if(eighthWinner == null){
					eighthWinner = players.get(i);
				}
			}
		}
		
		winners.add(winner);
		if(secondWinner != null){
			winners.add(secondWinner);
		}
		
		if(thirdWinner != null){
			winners.add(thirdWinner);
		}
		
		if(fourthWinner != null){
			winners.add(fourthWinner);
		}
		if(fifthWinner != null){
			winners.add(fifthWinner);
		}
		if(sixthWinner != null){
			winners.add(sixthWinner);
		}
		if(seventhWinner != null){
			winners.add(seventhWinner);
		}
		if(eighthWinner != null){
			winners.add(eighthWinner);
		}
		
		return winners;
		
	}
}
