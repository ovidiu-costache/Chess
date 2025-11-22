package src;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(Colors colors, Position position) {
        super(colors, position);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> move = new ArrayList<>();
        Position current = getPosition();
        int currentRow = current.getRow();
        char currentCol = current.getCol();

        int row;
        for (row = currentRow + 1; row <= 8; row++) {
            Position pos = new Position(currentCol, row);
            Piece target = board.getPieceAt(pos);

            if (target == null)
                move.add(pos);
            else {
                if (target.getColor() != this.getColor())
                    move.add(pos);
                break;
            }
        }

        for (row = currentRow - 1; row >= 1; row--) {
            Position pos = new Position(currentCol, row);
            Piece target = board.getPieceAt(pos);

            if (target == null)
                move.add(pos);
            else {
                if (target.getColor() != this.getColor())
                    move.add(pos);
                break;
            }
        }

        char col;
        for (col = (char) (currentCol + 1); col <= 'H'; col++) {
            Position pos = new Position(col, currentRow);
            Piece target = board.getPieceAt(pos);

            if (target == null)
                move.add(pos);
            else {
                if (target.getColor() != this.getColor())
                    move.add(pos);
                break;
            }
        }

        for (col = (char) (currentCol - 1); col >= 'A'; col--) {
            Position pos = new Position(col, currentRow);
            Piece target = board.getPieceAt(pos);

            if (target == null)
                move.add(pos);
            else {
                if (target.getColor() != this.getColor())
                    move.add(pos);
                break;
            }
        }

        return move;
    }

    @Override
    public boolean checkForCheck(Board board, Position kingPosition) {
        // JUST FOR NOW !!!!!
        return false;
    }

    @Override
    public char type() {
        return 'R';
    }
}
