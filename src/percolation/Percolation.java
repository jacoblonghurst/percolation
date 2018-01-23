package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;

    private int lenSize;

    private WeightedQuickUnionUF topUf;
    private WeightedQuickUnionUF botUf;

    private int topIndex, bottomIndex;

    public Percolation(int N) {

        if(N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        grid = new boolean[N][N];

        lenSize = N;

        topUf = new WeightedQuickUnionUF(lenSize * lenSize + 1);
        botUf = new WeightedQuickUnionUF(lenSize * lenSize + 1);

        topIndex = 0;

        bottomIndex = 0;
        
        int temp = 0;
    }
}
