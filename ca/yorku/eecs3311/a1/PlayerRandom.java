package ca.yorku.eecs3311.a1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 * 
 * @author ilir
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a player in the Othello game that makes random moves.
 * The player selects valid moves at random from the available options.
 */
public class PlayerRandom {

	protected Othello othello; // The Othello game instance
	private char player;        // The character representing the player ('X' or 'O')

	/**
	 * Constructs a PlayerRandom instance with the specified Othello game
	 * instance and player character.
	 *
	 * @param othello the Othello game instance
	 * @param player  the character representing the player ('X' or 'O')
	 */
	public PlayerRandom(Othello othello, char player) {
		this.othello = othello;
		this.player = player;
	}

	private Random rand = new Random(); // Random number generator

	/**
	 * Gets a random valid move for the player.
	 * The method checks all possible moves on the board and returns one of the valid moves at random.
	 * If no valid moves are available, it returns a move indicating no valid move (-1, -1).
	 *
	 * @return a Move object representing the chosen move, or (-1, -1) if no valid moves are available
	 */
	public Move getMove() {
		List<Move> listMove = new ArrayList<>(); // List to store valid moves
		for (int i = 0; i < othello.getDimension(); i++) {
			for (int j = 0; j < othello.getDimension(); j++) {
				outerLoop:
				for (int drow = -1; drow <= 1; drow++) {
					for (int dcol = -1; dcol <= 1; dcol++) {
						// Check if the current position is empty and has a valid move for the player
						if (othello.get(i, j) == ' ' && othello.hasMove(i, j, drow, dcol) == player) {
							// If the move is valid and not already added to the list, add it
							if (!listMove.contains(new Move(i, j))) {
								listMove.add(new Move(i, j));
								break outerLoop;
							}
						}
					}
				}
			}
		}

		// If there are no valid moves, return (-1, -1)
		if (listMove.size() == 0) {
			return new Move(-1, -1);
		} else {
			// Return a random move from the list of valid moves
			int a = rand.nextInt(listMove.size());
			return listMove.get(a);
		}
	}
}

