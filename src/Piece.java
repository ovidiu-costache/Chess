package src;

public abstract class Piece implements ChessPiece{
    private final Colors colors;
    private Position position;

    public Piece(Colors colors, Position position) {
        this.colors = colors;
        this.position = position;
    }

    public Colors getColor() {
        return this.colors;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
