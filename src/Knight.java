package src;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(Colors colors, Position position) {
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
        return 'N';
    }
}
