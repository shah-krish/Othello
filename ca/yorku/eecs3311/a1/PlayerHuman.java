package ca.yorku.eecs3311.a1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Represents a human player in the Othello game.
 * The player provides moves through standard input, and the input is validated.
 *
 * The player's move is specified by row and column indices, which must be within
 * the range of valid positions on the board (1-8).
 *
 * @author ilir
 */
public class PlayerHuman {

	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8"; // Message for invalid input
	private static final String IO_ERROR_MESSAGE = "I/O Error"; // Message for I/O errors
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); // Standard input reader

	private Othello othello; // The Othello game instance
	private char player;      // The character representing the player ('X' or 'O')

	/**
	 * Constructs a PlayerHuman instance with the specified Othello game instance and player character.
	 *
	 * @param othello the Othello game instance
	 * @param player  the character representing the player ('X' or 'O')
	 */
	public PlayerHuman(Othello othello, char player) {
		this.othello = othello;
		this.player = player;
	}

	/**
	 * Prompts the human player to enter their move.
	 * The method retrieves the row and column for the move and returns it as a Move object.
	 *
	 * @return a Move object representing the player's chosen move
	 */
	public Move getMove() {
		int row = getMove("row: ");
		int col = getMove("col: ");
		return new Move(row, col);
	}

	/**
	 * Prompts the player to enter a move (row or column) and validates the input.
	 * The input must be an integer within the range of 0 to 7 (inclusive).
	 *
	 * @param message the prompt message to display to the player
	 * @return a valid move (integer) entered by the player
	 */
	private int getMove(String message) {
		int move, lower = 0, upper = 7; // Valid move range
		while (true) {
			try {
				System.out.print(message);
				String line = PlayerHuman.stdin.readLine(); // Read input
				move = Integer.parseInt(line); // Parse input to integer
				if (lower <= move && move <= upper) { // Validate move
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (IOException e) {
				System.out.println(IO_ERROR_MESSAGE); // Handle I/O errors
				break;
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_MESSAGE); // Handle invalid number format
			}
		}
		return -1; // Return -1 if an error occurs
	}
}
