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
    private int expCount;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        StdOut.print("Please input the grid size with a number: ");
        int N = input.nextInt();
        StdOut.print("Next input the number of times you want to run the experiment: ");
        int T = input.nextInt();
        StdOut.printf("%n%n%s%n", "Great hold on while we crunch the numbers.");
        PercolationStats percStats = new PercolationStats(N, T);
        StdOut.printf("The mean is: %f%nThe standard deviation is: %f%nThe confidence low is: %f%nThe confidence high is: %f",
                percStats.mean(), percStats.stddev(), percStats.confidenceLow(), percStats.confidenceHigh());
        input.close();
    }

    /**
     * @param N defines the grid size N * N
     * @param T defines the number of times to run the statistics
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
     * @return the mean of the percolation
     */
    public double mean() {
        return StdStats.mean(stats);
    }

    /**
     * @return the standard deviation
     */
    public double stddev() {
        return StdStats.stddev(stats);
    }

    public double confidenceLow() {
        return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(expCount));
    }

    public double confidenceHigh() {
        return this.mean() + ((1.96 * this.stddev()) / Math.sqrt(expCount));
    }
}
