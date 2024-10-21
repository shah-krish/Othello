package ca.yorku.eecs3311.a1;

/**
 * Determine whether the first player or second player has the advantage when
 * both are playing a Random Strategy.
 * 
 * Do this by creating two players which use a random strategy and have them
 * play each other for 10000 games. What is your conclusion, does the first or
 * second player have some advantage, at least for a random strategy? 
 * State the null hypothesis H0, the alternate hypothesis Ha and 
 * about which your experimental results support. Place your short report in
 * randomVsRandomReport.txt.
 * 
 * @author ilir
 *
 */

public class OthelloControllerRandomVSRandom {

	protected Othello othello; // The Othello game instance
	PlayerRandom player1; // First player using random strategy
	PlayerRandom player2; // Second player using random strategy

	/**
	 * Constructs an OthelloControllerRandomVSRandom instance and initializes
	 * the Othello game and the two random players.
	 */
	public OthelloControllerRandomVSRandom() {
		this.othello = new Othello(); // Initialize the Othello game
		this.player1 = new PlayerRandom(this.othello, OthelloBoard.P1); // Create the first player
		this.player2 = new PlayerRandom(this.othello, OthelloBoard.P2); // Create the second player
	}

	/**
	 * Finds the move for the current player based on whose turn it is.
	 *
	 * @param whosTurn The character representing the player whose turn it is (P1 or P2).
	 * @return The move chosen by the current player.
	 */
	public Move findMove(char whosTurn) {
		Move move = null;

		// Get the move for the player whose turn it is
		if (whosTurn == OthelloBoard.P1)
			move = player1.getMove();
		if (whosTurn == OthelloBoard.P2)
			move = player2.getMove();

		return move; // Return the move
	}

	/**
	 * Plays the game until it is over, alternating between players
	 * and making moves until no valid moves remain.
	 */
	public void play() {
		while (!othello.isGameOver()) {

			char whosTurn = othello.getWhosTurn(); // Get the current player
			Move move = findMove(whosTurn); // Find the move for the player

			// If the player has no valid moves
			if (move.equals(new Move(-1, -1))) {
				othello.setWhosTurn(othello.otherplayer(whosTurn)); // Switch turn to the other player

				move = findMove(whosTurn); // Try to find a move again
				if (move.equals(new Move(-1, -1)))
					break; // If still no move, break the loop
			} else if (move.equals(new Move(-1, -1))) {
				othello.setWhosTurn(othello.otherplayer(whosTurn)); // Switch turn to the other player

				move = findMove(whosTurn); // Find the move again
			}
			othello.move(move.getRow(), move.getCol()); // Make the move
		}
	}

	/**
	 * Main method to execute the simulation of 10,000 games of Othello
	 * and print out the probabilities of each player winning.
	 *
	 * @param args Command-line arguments (not used).
	 */
	public static void main(String[] args) {

		int p1wins = 0, p2wins = 0, numGames = 10000; // Initialize counters for wins and total games

		// Simulate the specified number of games
		for (int i = 0; i < numGames; i++) {
			OthelloControllerRandomVSRandom oc = new OthelloControllerRandomVSRandom(); // Create a new game controller
			oc.play(); // Play the game

			char winner = oc.othello.getWinner(); // Get the winner of the game
			if (winner == OthelloBoard.P1) {
				p1wins++; // Increment P1 win counter
			} else if (winner == OthelloBoard.P2) {
				p2wins++; // Increment P2 win counter
			}
		}

		// Calculate the win probabilities
		String p1winProbability = String.format("%.2f", (float) p1wins / numGames);
		String p2winProbability = String.format("%.2f", (float) p2wins / numGames);

		// Print the probabilities
		System.out.println("Probability P1 wins=" + p1winProbability);
		System.out.println("Probability P2 wins=" + p2winProbability);
	}
}


