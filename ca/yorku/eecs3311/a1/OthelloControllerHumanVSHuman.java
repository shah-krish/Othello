package ca.yorku.eecs3311.a1;

/**
 * Run the main from this class to play two humans against eachother. Only
 * minimal modifications to this class are permitted.
 * 
 * @author arnold
 *
 */
public class OthelloControllerHumanVSHuman {

	protected Othello othello;
	PlayerHuman player1, player2;

	/**
	 * Constructs a new OthelloController with a new Othello game, ready to play
	 * with two users at the console.
	 */
	public OthelloControllerHumanVSHuman() {
		this.othello = new Othello(); // Initialize the Othello game
		this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
		this.player2 = new PlayerHuman(this.othello, OthelloBoard.P2);
	}

	/**
	 * Manages the main loop of the game, allowing players to take turns until
	 * the game is over.
	 */
	public void play() {
		while (!othello.isGameOver()) { // Loop until the game is over
			this.report();

			Move move = null;
			char whosTurn = othello.getWhosTurn(); // Get the current player's turn

			if (whosTurn == OthelloBoard.P1)
				move = player1.getMove(); // Get move from player 1
			if (whosTurn == OthelloBoard.P2)
				move = player2.getMove(); // Get move from player 2

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
	 * Run main to play two Humans against each other at the console.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		OthelloControllerHumanVSHuman oc = new OthelloControllerHumanVSHuman();
		oc.play();
	}
}
