package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid; // The grid ( like in tron ). Use boolean, it's less memory ¯\_(ツ)_/¯
    private int lenSize; // Length of grid
    private WeightedQuickUnionUF topUf; // Weighted and 2 of them cause you know backwash
    private WeightedQuickUnionUF botUf;

    private int topInd, botInd; // Indexes... am i right?

    /**
     *
     * @param N - Our grid size of course.
     */
    public Percolation(int N) {
        if(N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.grid = new boolean[N][N];
        this.lenSize = N;
        this.topUf = new WeightedQuickUnionUF(this.lenSize * this.lenSize + 1); // initialize our wquf
        this.botUf = new WeightedQuickUnionUF(this.lenSize * this.lenSize + 1); // wquf = weightedquickunionfind

        this.topInd = 0;
        this.botInd = 0;
    }

    /**
     *
     * @param i - Our column
     * @param j - Our row
     */
    public void open(int i, int j) {
        if(i >= lenSize + 1 || j >= lenSize + 1 || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException("Illegal point of (" + i + ", " + j + ") given to open()");
        } else {

            grid[i - 1][j - 1] = true; // set that square to open!

            // just a lot of conditions..
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

    /**
     *
     * @param i - Once again our column
     * @param j - And.. our row
     * @return - I'm just returning a boolean since our grid is made of booleans
     */
    public boolean isOpen(int i, int j) {
        if(i >= lenSize + 1 || j >= lenSize + 1 || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException("Illegal point of (" + i + ", " + j + ") given to isOpen()");
        } else {

            // YOOO are you open??
            return grid[i - 1][j - 1];
        }
    }

    /**
     *
     * @param i - Column
     * @param j - Row
     * @return returns if theres a path from this point to the top virtual
     */
    public boolean isFull(int i, int j) {
        if(i >= lenSize + 1 || j >= lenSize + 1 || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException("Illegal point of (" + i + ", " + j + ") given to isFull()");
        } else {
            // Is there a path from this point to the virtual top one?
            return topUf.connected(topInd, getUF(i, j));
        }
    }

    /**
     *
     * @return - Wait.. does it percolate??
     */
    public boolean percolates() {
        // But does it percolate??
        // DOES IT??
        for(int i = 0; i < lenSize; i++) {
            if(topUf.connected(getUF(lenSize, i), topInd) && botUf.connected(getUF(lenSize, i), botInd)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param i - Column
     * @param j - Row
     * @return We change our 3d grid point to a 2d index
     */
    private int getUF(int i, int j) {
        return lenSize * (i - 1) + j;
    }
}
