package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;

    private int lenSize;

    private WeightedQuickUnionUF topUf;
    private WeightedQuickUnionUF botUf;

    private int topInd, botInd;

    public Percolation(int N) {

        if(N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        grid = new boolean[N][N];

        lenSize = N;

        topUf = new WeightedQuickUnionUF(lenSize * lenSize + 1);
        botUf = new WeightedQuickUnionUF(lenSize * lenSize + 1);

        topInd = 0;

        botInd = 0;
    }

    public void open(int i, int j) {
        if(i >= lenSize + 1 || j >= lenSize + 1 || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException("Illegal point of (" + i + ", " + j + ") given to open()");
        } else {

            grid[i - 1][j - 1] = true;

            if(i > 1 && isOpen(i - 1, j)) {
                topUf.union(getUF(i, j), getUF(i - 1, j));
                botUf.union(getUF(i, j), getUF(i - 1, j));
            }

            if(i < lenSize - 1 && isOpen(i + 1, j)) {
                topUf.union(getUF(i, j), getUF(i + 1, j));
                botUf.union(getUF(i, j), getUF(i + 1, j));
            }

            if(j < lenSize && isOpen(i, j + 1)) {
                topUf.union(getUF(i, j), getUF(i, j + 1));
                botUf.union(getUF(i, j), getUF(i, j + 1));
            }

            if(j > 1 && isOpen(i, j - 1)) {
                topUf.union(getUF(i, j), getUF(i, j - 1));
                botUf.union(getUF(i, j), getUF(i, j - 1));
            }

            if (i == 1) {
                topUf.union(getUF(i, j), topInd);
            }

            if (i == lenSize) {
                botUf.union(getUF(i, j), botInd);
            }
        }
    }

    public boolean isOpen(int i, int j) {
        if(i >= lenSize + 1 || j >= lenSize + 1 || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException("Illegal point of (" + i + ", " + j + ") given to isOpen()");
        } else {

            // returns if it is open or not
            return grid[i - 1][j - 1];
        }
    }


    public boolean isFull(int i, int j) {
        if(i >= lenSize + 1 || j >= lenSize + 1 || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException("Illegal point of (" + i + ", " + j + ") given to isFull()");
        } else {
            // returns if there is a path from this point to the virtual top point
            return topUf.connected(topInd, getUF(i, j));
        }
    }

    public boolean percolates() {
        // sees if it percolates by seeing if there is a point in the bottom row that connects to both virtual spots

        for(int i = 0; i < lenSize; i++) {
            if(topUf.connected(getUF(lenSize, i), topInd) && botUf.connected(getUF(lenSize, i), botInd)) {
                return true;
            }
        }
        return false;
    }


    // private method that changes our 3d grid point into a 2d index used by UF
    private int getUF(int i, int j) {
        return lenSize * (i - 1) + j;
    }
}
