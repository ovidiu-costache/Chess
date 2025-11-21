package src;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int id;
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private int currentPlayerIndex;

    public Game() {
        this.board = new Board();
        this.players = new ArrayList<>();
        this.moves = new ArrayList<>();
    }

    public void start() {
        // board.initiaize(); !!!!!
        this.moves.clear(); // moves is and ArrayList, not a Move Object

        // The players using WHITE starts
        int i;
        for (i = 0; i < players.size(); i++)
            if (players.get(i).getColor() == Colors.WHITE) {
                this.currentPlayerIndex = i;
                break;
            }
    }

    public void resume() {
        // ???
    }

    public void switchPlayer() {
        if (this.currentPlayerIndex == 0)
            this.currentPlayerIndex = 1;
        else
            this.currentPlayerIndex = 0;
    }

    public boolean checkForCheckMate() {
        // Ramane de implementat logica SAH MAT
        return false;
    }

    public void addMove(Player p, Position from, Position to) {
        Piece captured = null;
        // Piece captured = board.getPieceAt(to);

        Move move = new Move(p.getColor(), from, to, captured);
        this.moves.add(move);
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getCurrentPlayer() {
        if (players.isEmpty())
            return null;
        else
            return players.get(currentPlayerIndex);
    }

    // Setters
    public void setId(long id) {
        this.id = (int) id;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setCurrentPlayerColor(String colorString) {
        Colors color = Colors.valueOf(colorString);

        int i;
        for (i = 0; i < players.size(); i++)
            if (players.get(i).getColor() == color) {
                this.currentPlayerIndex = i;
                break;
            }
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public void setBoard(List<Piece> pieces) {
        // !!! Cand e gata Board !!!
    }
}
