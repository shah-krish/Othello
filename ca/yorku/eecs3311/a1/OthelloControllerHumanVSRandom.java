package ca.yorku.eecs3311.a1;

public class OthelloControllerHumanVSRandom {
    protected Othello othello;
    PlayerHuman player1;
    PlayerRandom player2;

    public OthelloControllerHumanVSRandom() {
        this.othello = new Othello(); // Initialize the Othello game
        this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
        this.player2 = new PlayerRandom(this.othello, OthelloBoard.P2);
    }
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
     * Run main to play Humans against Random.
     *
     * @param args Command line arguments
     */

    public static void main(String[] args) {
        OthelloControllerHumanVSRandom oc = new OthelloControllerHumanVSRandom();
        oc.play();
    }
}
