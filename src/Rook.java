package src;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(Colors colors, Position position) {
        super(colors, position);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        // WILL BE IMPLEMENTED LATER
        return new ArrayList<>();
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
