package src;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Player {
    private String playerName;
    private Colors colors;
    private List<Piece> captured;
    private TreeSet<ChessPair<Position, Piece>> owned;
    private int points;

    // From JSON (color->string)
    public Player(String playerName, String colorString) {
        this.playerName = playerName;
        this.colors = Colors.valueOf(colorString);
        this.captured = new ArrayList<>();
        this.owned = new TreeSet<>();
        this.points = 0;
    }

    public Player(String playerName, Colors colors) {
        this.playerName = playerName;
        this.colors = colors;
        this.captured = new ArrayList<>();
        this.owned =  new TreeSet<>();
        this.points = 0;
    }

    public void makeMove(Position from, Position to, Board board) {
        // FOR LATER
    }

    public List<Piece> getCapturedPieces() {
        return this.captured;
    }

    public List<ChessPair<Position, Piece>> getOwnedPieces() {
        return new ArrayList<>(this.owned);
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return this.playerName;
    }

    public Colors getColor() {
        return this.colors;
    }
}
