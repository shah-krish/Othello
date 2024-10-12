package ca.yorku.eecs3311.a1;

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 * 
 * Othello makes use of the OthelloBoard.
 * 
 * @author Ilir
 *
 */
public class OthelloBoard {
	
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;
	private int p1Tokens=2;
	private int p2Tokens=2;
	public OthelloBoard(int dim) {
		this.dim = dim;
		board = new char[this.dim][this.dim];
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.board[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
		this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
	}

	public int getDimension() {
		return this.dim;
	}

	/**
	 * 
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
	public static char otherPlayer(char player) {
		if(player==P1){
			return P2;
		}
		else{
			return P1;
		}
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
		if (validCoordinate(row, col)) {
			return board[row][col];
		}
		return EMPTY;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether (row,col) is a position on the board. Example: (6,12) is not
	 *         a position on the board.
	 */
	private boolean validCoordinate(int row, int col) {
		return row >= 0 && row < dim && col >= 0 && col < dim;
	}

	/**
	 * Check if there is an alternation of P1 next to P2, starting at (row,col) in
	 * direction (drow,dcol). That is, starting at (row,col) and heading in
	 * direction (drow,dcol), you encounter a sequence of at least one P1 followed
	 * by a P2, or at least one P2 followed by a P1. The board is not modified by
	 * this method. Why is this method important? If
	 * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
	 * assuming that square is EMPTY, is a valid move, resulting in a collection of
	 * P2 being flipped.
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
	 *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
	 *         alternation
	 */
	private char alternation(int row, int col, int drow, int dcol) {
		if (!validCoordinate(row, col) || board[row][col] != EMPTY) {
			return EMPTY;
		}

		// Move one step in the specified direction
		row += drow;
		col += dcol;

		// If the next square is not the opponent, return EMPTY
		char opponent = otherPlayer(board[row - drow][col - dcol]);
		if (!validCoordinate(row, col) || board[row][col] != opponent) {
			return EMPTY;
		}

		// Continue moving in the specified direction, looking for the current player's token
		while (validCoordinate(row, col)) {
			if (board[row][col] == EMPTY) {
				return EMPTY;
			} else if (board[row][col] == otherPlayer(opponent)) {
				return board[row][col]; // Found the player's token, valid alternation
			}
			row += drow;
			col += dcol;
		}

		return EMPTY; // No valid alternation found
	}


		/*
			int firstRow = row, firstCol = col;
			while(true){
				//I am using a sliding window approach in a way I think
				//making a window of 3 elements
				int secondRow = firstRow+drow, secondCol = firstCol+dcol;
				int thirdRow = secondRow+drow, thirdCol = secondCol+dcol;

				//if the third(last) index is out of bounds we exit
				if (thirdRow < 0 || thirdRow >= board.length || thirdCol < 0 || thirdCol >= board[thirdRow].length) {
					return EMPTY;
				}

				//Extracting elements from the 3 indexes
				char first = board[firstRow][firstCol];
				char second = board[secondRow][secondCol];
				char third = board[thirdRow][thirdCol];

				//If any of the elements is empty i just return empty
				if(first==EMPTY||second==EMPTY||third==EMPTY){
					return EMPTY;
				}


				if ((first == P1 && second == P2 && third == P1) ||
						(first == P2 && second == P1 && third == P2)) {
					return first;
				}
				firstRow+=drow;
				firstCol+=dcol;
			}
			 */


	/**
	 * flip all other player tokens to player, starting at (row,col) in direction
	 * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
	 * result in a flip to OOOO
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   the row direction, in {-1,0,1}
	 * @param dcol   the col direction, in {-1,0,1}
	 * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               flip to.
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flip(int row, int col, int drow, int dcol, char player) {
		int counter = 1;
		int r = row, c = col;
		if(alternation(row,col,drow,col) == EMPTY||alternation(row,col,drow,dcol)==player){
			return -1;
		}
		else{
			while(board[r][c]!=player){
				board[r][c] = player;
				r+= drow;
				c+=dcol;
				counter++;
				incrementTokens(player);
			}
			return counter;
		}
	}
	private void incrementTokens(char player){
		if(player == P1){
			p1Tokens++;
			p2Tokens--;
		}
		else{
			p2Tokens++;
			p1Tokens--;
		}
	}

	/**
	 * Return which player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1,P2,EMPTY
	 */
	private char hasMove(int row, int col, int drow, int dcol) {
		if(!validCoordinate(row,col)||board[row][col]==EMPTY){
			return EMPTY;
		}
		return alternation(row,row,drow,dcol);
	}
	/*
	private char getOpponentPiece(int r, int c, int drow, int dcol) {
		char prev = board[r - drow][c - dcol];
		if (prev == P1) {
			return P2;
		} else if (prev == P2) {
			return P1;
		} else {
			return EMPTY;
		}
	}
	 */
	/**
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	public char hasMove() {
		return P1;
	}

	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {
		// HINT: Use some of the above helper methods to get this methods
		// job done!!
		char playerToMove = hasMove(row,col,0,0);
		if (playerToMove == player){
			board[row][col] = player;
			flip(row+0,col+1,0,1,player);
			flip(row+1,col+0,1,0,player);
			flip(row+1,col+1,1,1,player);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {
		int counter=0;
		for(int i = 0;i<board.length;i++){
			for(int j = 0; j<board.length;j++){
				if(board[i][j]==player){
					counter++;
				}
			}
		}
		return counter;
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		/**
		 * See assignment web page for sample output.
		 */
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += this.board[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard. Output is on assignment page.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloBoard ob = new OthelloBoard(8);
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				ob.board[row][col] = P1;
			}
		}
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));

		// Should all be blank
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}
		System.out.println("here");


		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (row == 0 || col == 0) {
					ob.board[row][col] = P2;
				}
			}
		}
		System.out.println(ob.toString());

		// Should all be P2 (O) except drow=0,dcol=0
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}
		// Can't move to (4,4) since the square is not empty
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));

		ob.board[4][4] = EMPTY;
		ob.board[2][4] = EMPTY;

		System.out.println(ob.toString());

		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
			}
		}
		System.out.println("who has a move=" + ob.hasMove());
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
		System.out.println(ob.toString());

	}
}

