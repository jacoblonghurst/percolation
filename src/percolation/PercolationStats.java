package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Scanner;


public class PercolationStats {
    /**
     * Declare the variables being use
     */

    private Percolation perc; // Declare the percolation variable
    private double[] stats; // Keep track of the statistics we are finding out
    private int expCount; // Keep track of the number of times you wanted to run the program.

    // This is just the main
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        StdOut.print("Please input the grid size with a number: ");
        int N = input.nextInt();
        int T = 0;
        StdOut.print("Next input the number of times you want to run the experiment: ");
        // All the above was to handle user input
        do {
            // All the below is to make sure they don't pick 1 for run times or else it will throw a NaN
            // aka. divide by zero.
            try {
                T = input.nextInt();
                input.nextLine();
                if (T <= 1) {
                    System.out.print("Please choose a number greater than 1: ");
                }
                if (T > 1) {
                    valid = true;
                }
            } catch (Exception e) {
                System.err.println(e.getStackTrace());
            }
        } while (!valid);
        StdOut.printf("%n%n%s%n", "Great hold on while we crunch the numbers.");
        // Now we create our perc stats and run the numbers.
        PercolationStats percStats = new PercolationStats(N, T);
        StdOut.printf("The mean is: %f%nThe standard deviation is: %f%nThe confidence low is: %f%nThe confidence high is: %f",
                percStats.mean(), percStats.stddev(), percStats.confidenceLow(), percStats.confidenceHigh());
        input.close();
    }

    /**
     * @param N The GRID!
     * @param T How many times we gonna run this bad boy??
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Grid and/or number of experiments was <= 0");
        }
        expCount = T;
        stats = new double[T];
        for (int i = 0; i < T; i++) {
            int opened = 0;
            perc = new Percolation(N);
            while (!perc.percolates()) {
                int j = StdRandom.uniform(1, 1 + N);
                int k = StdRandom.uniform(1, 1 + N);
                if (!perc.isOpen(j, k)) {
                    perc.open(j, k);
                    opened++;
                }
            }
            double tempStat = (double) opened / (N * N); // this will get the ratio of opened sites to the whole grid
            stats[i] = tempStat;
        }

    }

    /**
     * @return The MEAN!
     */
    public double mean() {
        return StdStats.mean(stats);
    }

    /**
     * @return The Standard Deviation!!
     */
    public double stddev() {
        return StdStats.stddev(stats);
    }

    /**
     *
     * @return How confident are we??
     */
    public double confidenceLow() {
        return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(expCount));
    }

    /**
     *
     * @return Clearly, we are very confident..
     */
    public double confidenceHigh() {
        return this.mean() + ((1.96 * this.stddev()) / Math.sqrt(expCount));
    }
}
