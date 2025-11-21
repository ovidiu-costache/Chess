package src;

import java.util.TreeSet;
import java.util.Iterator;

public class Board {
    private TreeSet<ChessPair<Position, Piece>> pieces;

    public Board() {
        this.pieces = new TreeSet<>();
    }

    public void initialize() {
        this.pieces.clear();

        // WHITE Team
        char c;
        // WHITE Pawns are on 2nd row
        for (c = 'A'; c <= 'H'; c++) {
            Position posP = new Position(c, 2);
            this.pieces.add(new ChessPair<>(posP, new Pawn(Colors.WHITE, posP)));
        }

        // WHITE Rooks
        Position posR1 = new Position('A', 1);
        this.pieces.add(new ChessPair<>(posR1, new Rook(Colors.WHITE, posR1)));
        Position posR2 = new Position('H', 1);
        this.pieces.add(new ChessPair<>(posR2, new Rook(Colors.WHITE, posR2)));

        // WHITE Knights
        Position posN1 = new Position('B', 1);
        this.pieces.add(new ChessPair<>(posN1, new Knight(Colors.WHITE, posN1)));
        Position posN2 = new Position('G', 1);
        this.pieces.add(new ChessPair<>(posN2, new Knight(Colors.WHITE, posN2)));

        // WHITE Bishops
        Position posB1 = new Position('C', 1);
        this.pieces.add(new ChessPair<>(posB1, new Bishop(Colors.WHITE, posB1)));
        Position posB2 = new Position('F', 1);
        this.pieces.add(new ChessPair<>(posB2, new Bishop(Colors.WHITE, posB2)));

        // WHITE Queen
        Position posQ = new Position('D', 1);
        this.pieces.add(new ChessPair<>(posQ, new Queen(Colors.WHITE, posQ)));

        // WHITE King
        Position posK = new Position('E', 1);
        this.pieces.add(new ChessPair<>(posK, new King(Colors.WHITE, posK)));

        // BLACK Team
        // BLACK Pawns are on 7th row
        for (c = 'A'; c <= 'H'; c++) {
            Position posP = new Position(c, 7);
            this.pieces.add(new ChessPair<>(posP, new Pawn(Colors.BLACK, posP)));
        }

        // BLACK Rooks
        posR1 = new Position('A', 8);
        this.pieces.add(new ChessPair<>(posR1, new Rook(Colors.BLACK, posR1)));
        posR2 = new Position('H', 8);
        this.pieces.add(new ChessPair<>(posR2, new Rook(Colors.BLACK, posR2)));

        // BLACK Knights
        posN1 = new Position('B', 8);
        this.pieces.add(new ChessPair<>(posN1, new Knight(Colors.BLACK, posN1)));
        posN2 = new Position('G', 8);
        this.pieces.add(new ChessPair<>(posN2, new Knight(Colors.BLACK, posN2)));

        // BLACK Bishops
        posB1 = new Position('C', 8);
        this.pieces.add(new ChessPair<>(posB1, new Bishop(Colors.BLACK, posB1)));
        posB2 = new Position('F', 8);
        this.pieces.add(new ChessPair<>(posB2, new Bishop(Colors.BLACK, posB2)));

        // BLACK Queen
        posQ = new Position('D', 8);
        this.pieces.add(new ChessPair<>(posQ, new Queen(Colors.BLACK, posQ)));

        // BLACK King
        posK = new Position('E', 8);
        this.pieces.add(new ChessPair<>(posK, new King(Colors.BLACK, posK)));
    }

    public void movePiece(Position from, Position to) {
        // Will be implemented
    }

    public Piece getPieceAt(Position position) {
        for (ChessPair<Position, Piece> pair : pieces)
            if (pair.getKey().equals(position))
                return pair.getValue();
        return null;
    }

    public boolean isValidMove(Position from, Position to) {
        // Will be implemented
        return true;
    }

    public void add(Piece piece) {
        this.pieces.add(new ChessPair<>(piece.getPosition(), piece));
    }

    public void clear() {
        this.pieces.clear();
    }

    public void removePieceAt(Position position) {
        Iterator<ChessPair<Position, Piece>> iterator = pieces.iterator();

        while (iterator.hasNext()) {
            ChessPair<Position, Piece> pair = iterator.next();
            if (pair.getKey().equals(position)) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public String toString() {
        String table = "";

        // Header
        table = table + "   -----------------------------------------\n";
        int row;
        char col;

        for (row = 8; row >= 1; row--) {
            table = table + row + " | ";

            for (col = 'A'; col <= 'H'; col++) {
                Position pos = new Position(col, row);
                Piece piece = getPieceAt(pos);

                if (piece == null) {
                    table = table + "...  ";
                }
                else {
                    char type = piece.type();
                    char colorCode;

                    if (piece.getColor() == Colors.WHITE)
                        colorCode = 'W';
                    else
                        colorCode = 'B';

                    table = table + type + "-" + colorCode + "  ";
                }
            }
            table = table + "|\n";
        }
        table = table + "   -----------------------------------------\n";
        table = table + "    A    B    C    D    E    F    G    H\n";

        return table;
    }
}
