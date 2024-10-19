package ca.yorku.eecs3311.a1;


import java.util.*;

/**
 * PlayerGreedy makes a move by considering all possible moves that the player
 * can make. Each move leaves the player with a total number of tokens.
 * getMove() returns the first move which maximizes the number of
 * tokens owned by this player. In case of a tie, between two moves,
 * (row1,column1) and (row2,column2) the one with the smallest row wins. In case
 * both moves have the same row, then the smaller column wins.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * See the examples supplied in the assignment handout.
 * 
 * @author ilir
 *
 */

public class PlayerGreedy {
	protected Othello othello;
	private char player;

	public PlayerGreedy(Othello othello, char player) {
		this.othello = othello;
		this.player = player;
	}

	public Move getMove() {
		List<Move> listMove = new ArrayList<>();
		List<Integer> listCount = new ArrayList<>();
		for (int i = 0; i < othello.getDimension(); i++) {
			for (int j = 0; j < othello.getDimension(); j++) {
				for (int drow = -1; drow <= 1; drow++) {
					for (int dcol = -1; dcol <= 1; dcol++) {
						if (othello.hasMove(i, j, drow, dcol) == player) {
							listCount.add(othello.getCount(i, j, drow, dcol, player));
							listMove.add(new Move(i, j));
						}
					}
				}
			}
		}
		int a = 0;
		int index = 0;
		for(int i =0; i <listCount.size();i++){
			a = Math.max(a, listCount.get(i));
		}
		for(int i = 0; i<listCount.size();i++){
			if(listCount.get(i)==a){
				index = i;
				break;
			}
		}
		return listMove.get(index);
	}
}

