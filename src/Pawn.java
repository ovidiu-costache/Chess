package src;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(Colors colors, Position position) {
        super(colors, position);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        Position current = getPosition();
        int currentRow = current.getRow();
        char currentCol = current.getCol();

        // WHITE + 1, BLACK - 1
        int direction;
        if (this.getColor() == Colors.WHITE)
            direction = 1;
        else
            direction = -1;

        // A step forward
        int nextRow = currentRow + direction;
        if (nextRow >= 1 && nextRow <= 8) {
            Position forward1 = new Position(currentCol, nextRow);

            if (board.getPieceAt(forward1) == null) {
                moves.add(forward1);

                // 2 steps forward if it's first move
                boolean isFirst = false;
                if (this.getColor() == Colors.WHITE && currentRow == 2)
                    isFirst = true;
                if (this.getColor() == Colors.BLACK && currentRow == 7)
                    isFirst = true;

                if (isFirst) {
                    int nextRow2 = currentRow + (2 * direction);
                    Position forward2 = new Position(currentCol, nextRow2);

                    if (board.getPieceAt(forward2) == null)
                        moves.add(forward2);
                }
            }
        }

        // Diagonal movement
        int[] cols = {-1, 1};

        for (int offset : cols) {
            char attackCol = (char) (currentCol + offset);
            int attackRow = currentRow + direction;

            if (attackCol >= 'A' && attackCol <= 'H' && attackRow >= 1 && attackRow <= 8) {
                Position attackPos = new Position(attackCol, attackRow);
                Piece target = board.getPieceAt(attackPos);

                if (target != null)
                    if (target.getColor() != this.getColor())
                        moves.add(attackPos);
            }
        }

        return moves;
    }

    @Override
    public boolean checkForCheck(Board board, Position kingPosition) {
        // JUST FOR NOW !!!!!
        return false;
    }

    @Override
    public char type() {
        return 'P';
    }
}
