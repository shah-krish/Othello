package ca.yorku.eecs3311.a1;

/**
 * This controller uses the Model classes to allow the Human player P1 to play
 * against the computer P2. The computer, P2, uses a greedy strategy.
 *
 * @author ilir
 *
 */
public class OthelloControllerHumanVSGreedy {

	/**
	 * Run main to play a Human (P1) against the computer P2.
	 * The computer uses a greedy strategy, which picks the first
	 * move that maximizes its number of tokens on the board.
	 * The output should be almost identical to that of OthelloControllerHumanVSHuman.
	 *
	 * @param args Command line arguments
	 */
	protected Othello othello;
	PlayerHuman player1;
	PlayerGreedy player2;

	/**
	 * Constructs a new OthelloController for a game between a human player and a greedy computer.
	 */
	public OthelloControllerHumanVSGreedy() {
		this.othello = new Othello(); // Initialize the Othello game
		this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1); // Create the human player
		this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2); // Create the greedy computer player
	}

	/**
	 * Manages the main loop of the game, allowing the human player to take turns
	 * against the greedy computer until the game is over.
	 */
	public void play() {
		while (!othello.isGameOver()) {
			this.report();

			Move move = null;
			char whosTurn = othello.getWhosTurn(); // Get the current player's turn

			if (whosTurn == OthelloBoard.P1)
				move = player1.getMove(); // Get move from the human player
			if (whosTurn == OthelloBoard.P2)
				move = player2.getMove(); // Get move from the computer player

			this.reportMove(whosTurn, move); // Report the move made
			othello.move(move.getRow(), move.getCol()); // Update the game with the move
		}
		this.reportFinal(); // Report the final game state
	}

	/**
	 * Reports the move made by the current player.
	 *
	 * @param whosTurn The character representing the player making the move
	 * @param move     The move that was made
	 */
	private void reportMove(char whosTurn, Move move) {
		System.out.println(whosTurn + " makes move " + move + "\n");
	}

	/**
	 * Reports the current state of the game, including the board and player scores.
	 */
	private void report() {
		String s = othello.getBoardString() + OthelloBoard.P1 + ":"
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
				+ othello.getWhosTurn() + " moves next";
		System.out.println(s); // Print the current game status
	}

	/**
	 * Reports the final state of the game, including the winner and final scores.
	 */
	private void reportFinal() {
		String s = othello.getBoardString() + OthelloBoard.P1 + ":"
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2)
				+ "  " + othello.getWinner() + " won\n";
		System.out.println(s); // Print the final game status
	}

	/**
	 * Run main to play a Human (P1) against the computer P2.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		OthelloControllerHumanVSGreedy oc = new OthelloControllerHumanVSGreedy();
		oc.play(); // Start the game
	}
}
