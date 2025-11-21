package src;

public class Position implements Comparable<Position>{
    private char col;
    private int row;

    public Position(char col, int row) {
        this.col = col;
        this.row = row;
    }

    // Getter and Setter for row and col
    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public char getCol() {
        return this.col;
    }

    public void setCol(char col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        // The object could be of a different class
        if (o == null || getClass() != o.getClass())
            return false;

        Position pos = (Position) o;

        if (this.col != pos.col || this.row != pos.row)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "" + this.col + this.row;
    }

    @Override
    public int compareTo(Position position) {
        // First the rows
        if (this.row != position.row)
            return Integer.compare(this.row, position.row);
        else
            return Character.compare(this.col, position.col);
    }
}
