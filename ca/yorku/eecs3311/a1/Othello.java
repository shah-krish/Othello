package ca.yorku.eecs3311.a1;

import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 *
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 *
 *
 */
public class Othello {
	public static final int DIMENSION = 8; // This is an 8x8 game
	private char whosTurn = OthelloBoard.P1; // P1 moves first!
	private int numMoves = 0;
	private OthelloBoard board;

	public Othello() {
		board = new OthelloBoard(DIMENSION);
	}

	/**
	 * return dimension of the board.
	 *
	 * @return DIMENSION
	 */

	public int getDimension(){
		return DIMENSION;
	}

	/**
	 *
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
		return board.get(row,col);
	}
	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 *
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		return whosTurn;
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 *
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
		char playerToMove = getWhosTurn();
		boolean a = board.move(row, col, playerToMove);
		//System.out.println(a);
		if (a) {
			whosTurn = board.otherPlayer(playerToMove);
			numMoves++;
		}
		return a;
	}

	private int flipCount(int row, int col, int drow, int dcol, char player){
		return board.flipCount(row,col,drow,dcol,player);
	}

	/**
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return board.getCount(player);
	}

	public char hasMove(int row, int col, int drow, int dcol){
		return board.whoseMove(row,col,drow,dcol);
	}

	public int getCount(int row, int col, int drow, int dcol, char player){
		return board.flipCount(row,col,drow,dcol,player);
	}
	/**
	 * Returns the winner of the game.
	 *
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		int p1 = getCount(OthelloBoard.P1);
		int p2 = getCount(OthelloBoard.P2);
		if (p1 > p2) {
			return OthelloBoard.P1;
		} else {
			return OthelloBoard.P2;
		}
	}

	/**
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		return board.hasMove()==OthelloBoard.EMPTY;
	}

	/**
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString();
	}


	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Random rand = new Random();

		Othello o = new Othello();
		System.out.println(o.getBoardString());
//		int row = 2;
//		int col = 4;
//		if (o.move(row, col)) {
//			System.out.println("makes move (" + row + "," + col + ")");
//			System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
//		}

		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);
			//System.out.println(o.getWhosTurn()+" "+o.move(row, col)+" "+row+" "+col);
			if (o.move(row, col)) {
				System.out.println("makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}
	}
}

