package finalproject;

import java.util.ArrayList;

public class CompareHands {

	static ArrayList<HandRank> getWinningPlayer(ArrayList<HandRank> players){
		//Assume you have not called get best five card hand
		ArrayList<HandRank> winners = new ArrayList<HandRank>();
		HandRank winner = players.get(0);
		HandRank secondWinner = null;
		HandRank thirdWinner = null;
		HandRank fourthWinner = null;
		HandRank fifthWinner = null;
		HandRank sixthWinner = null;
		HandRank seventhWinner = null;
		HandRank eighthWinner = null;
//		for(int i = 0; i < players.size(); i++){
//			players.get(i).getBest5CardHand();
//		}
		for(int i = 1; i < players.size(); i++){
			if(players.get(i).compareTo(winner) > 0){
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
			else if(players.get(i).compareTo(winner) == 0){
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
