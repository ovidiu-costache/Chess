package src;

public class Move {
    private Colors playerColor;
    private Position from;
    private Position to;
    private Piece captured;

    public Move(Colors playerColor, Position from, Position to, Piece captured) {
        this.playerColor = playerColor;
        this. from = from;
        this.to = to;
        this.captured = captured;
    }

    // Getters
    public Colors getPlayerColor() {
        return this.playerColor;
    }

    public Position getFrom() {
        return this.from;
    }

    public Position getTo() {
        return this.to;
    }

    public Piece getCaptured() {
        return this.captured;
    }
}
