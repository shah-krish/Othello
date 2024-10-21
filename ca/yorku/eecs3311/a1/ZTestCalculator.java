package ca.yorku.eecs3311.a1;

/**
 * This class performs statistical calculations for a two-proportion Z-test,
 * which is used to determine whether there is a significant difference
 * between the win probabilities of two players in othello.
 * It calculates the Z-value and P-value based on the given win probabilities
 * and the number of games played.
 *
 * The null hypothesis is that there is no significant difference in the
 * win probabilities of the two players.
 *
 * Author: [Your Name]
 */
public class ZTestCalculator {

    static int numGames = 10000;  // Total number of games played
    static double p1 = 0.24;       // Player 1 win probability
    static double p2 = 0.39;       // Player 2 win probability

    /**
     * Calculates the Z-value based on the win probabilities of two players
     * and the total number of games played.
     *
     * @param p1 the win probability of player 1
     * @param p2 the win probability of player 2
     * @param n  the total number of games played
     * @return the calculated Z-value
     */
    public static double calculateZValue(double p1, double p2, int n) {
        double p_hat = (p1 + p2) / 2;
        double standardError = Math.sqrt(p_hat * (1 - p_hat) * (2.0 / n));
        return (p1 - p2) / standardError;
    }

    /**
     * Calculates the P-value based on the given Z-value.
     *
     * @param z the Z-value calculated from the win probabilities
     * @return the calculated P-value
     */
    public static double calculatePValue(double z) {
        return 1 - cdf(z);
    }

    /**
     * Approximates the cumulative distribution function (CDF) for a standard
     * normal distribution using the hyperbolic tangent function.
     *
     * @param z the Z-value for which to calculate the CDF
     * @return the approximate CDF value
     */
    public static double cdf(double z) {
        return 0.5 * (1 + Math.tanh(Math.sqrt(2 / Math.PI) * (z / Math.sqrt(2))));
    }

    /**
     * Main method to execute the Z-test calculations and print the results.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        double zValue = calculateZValue(p1, p2, numGames);
        System.out.println("Z-value: " + zValue);

        double pValue = calculatePValue(Math.abs(zValue));
        System.out.println("P-value: " + pValue);

        double alpha = 0.05;  // significance level (5%)
        if (pValue < alpha) {
            System.out.println("Reject the null hypothesis. Significant difference between players.");
        } else {
            System.out.println("Fail to reject the null hypothesis. No significant difference.");
        }
    }
}
