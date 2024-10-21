package ca.yorku.eecs3311.a1;

/**
 * Represents a move on the Othello board.
 * This class shows the row and column coordinates of a move.
 *
 * @author ilir
 */
public class Move {
	private int row, col;

	/**
	 * Constructs a Move with the specified row and column.
	 *
	 * @param row the row of the move
	 * @param col the column of the move
	 */
	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Returns the row of this move.
	 *
	 * @return the row of the move
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column of this move.
	 *
	 * @return the column of the move
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Indicates whether some other move is equal to this Move.
	 *
	 * @param obj the reference object with which to compare
	 * @return true if this Move is the same as the move argument; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;

		Move move = (Move) obj;
		return this.row == move.row && this.col == move.col;
	}

	/**
	 * Returns a string representation of this Move.
	 * The string representation consists of the row and column in the format (row,col).
	 *
	 * @return a string representation of this Move
	 */
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
}
