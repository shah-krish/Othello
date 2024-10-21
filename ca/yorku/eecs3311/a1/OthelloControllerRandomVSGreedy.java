package ca.yorku.eecs3311.a1;

/**
 * The goal here is to print out the probability that Random wins and Greedy
 * wins as a result of playing 10000 games against each other with P1=Random and
 * P2=Greedy. What is your conclusion, which is the better strategy?
 * @author ilir
 *
 */

public class OthelloControllerRandomVSGreedy {

	protected Othello othello;
	PlayerRandom player1;
	PlayerGreedy player2;

	public OthelloControllerRandomVSGreedy() {
		this.othello = new Othello();
		this.player1 = new PlayerRandom(this.othello, OthelloBoard.P1);
		this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
	}

	/**
	 * Method to execute the gameplay between the two players.
	 */
	public void play() {
		while (!othello.isGameOver()) {
			char whosTurn = othello.getWhosTurn();
			Move move = findMove(whosTurn);

			if (move == null || move.equals(new Move(-1, -1))) {
				othello.setWhosTurn(othello.otherplayer(whosTurn));

				move = findMove(whosTurn);
				if (move == null || move.equals(new Move(-1, -1)))
					break;
			}
			othello.move(move.getRow(), move.getCol());
		}
	}

	/**
	 * Determine the move for the current player based on the strategy.
	 *
	 * @param whosTurn the player whose turn it is.
	 * @return the Move object representing the selected move.
	 */
	private Move findMove(char whosTurn) {
		Move move = null;

		if (whosTurn == OthelloBoard.P1) {
			move = player1.getMove();
		} else if (whosTurn == OthelloBoard.P2) {
			move = player2.getMove();
		}
		return move;
	}

	/**
	 * Main method to run 10000 simulations of Random vs. Greedy.
	 *
	 * @param args command-line arguments (not used).
	 */
	public static void main(String[] args) {
		int p1wins = 0, p2wins = 0, numGames = 10000;

		// Simulate 10,000 games between Random (P1) and Greedy (P2)
		for (int i = 0; i < numGames; i++) {
			OthelloControllerRandomVSGreedy oc = new OthelloControllerRandomVSGreedy();
			oc.play();

			char winner = oc.othello.getWinner();
			if (winner == OthelloBoard.P1) {
				p1wins++;
			} else if (winner == OthelloBoard.P2) {
				p2wins++;
			}
		}

		// Output the probability of each player winning
		String p1winProbability = String.format("%.2f", (float) p1wins / numGames);
		String p2winProbability = String.format("%.2f", (float) p2wins / numGames);

		System.out.println("Probability P1 wins=" + p1winProbability);
		System.out.println("Probability P2 wins=" + p2winProbability);
	}
}
