package src;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String email;
    private String password;
    private List<Game> games;
    private int points;

    public Account() {
        this.games = new ArrayList<>();
        this.points = 0;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public void removeGame(Game game) {
        this.games.remove(game);
    }

    // Getter and Setters
    public List<Game> getActiveGames() {
        return this.games;
    }

    public List<Game> getGames() {
        return this.games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
