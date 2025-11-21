package src;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading JSON documents using JSON.simple ("simple-json").
 *
 * ###### IMPORTANT: This is just an example of how to read JSON documents using the library.
 * Your classes might differ slightly, so don’t hesitate to update this class as needed.
 *
 * Expected structures:
 * - accounts.json: an array of objects with fields: email (String), password (String), points (Number), games (array of numbers)
 * - games.json: an array of objects with fields matching the JSON provided:
 * id (Number), players (array of {email, color}), currentPlayerColor (String),
 * board (array of {type, color, position}), moves (array of {playerColor, from, to})
 */
public final class JsonReaderUtil {

    private JsonReaderUtil() {
    }

    /**
     * Reads the accounts from the given JSON file path.
     *
     * @param path path to accounts.json
     * @return list of Account objects (empty list if file not found or array empty)
     * @throws IOException    if I/O fails
     * @throws ParseException if JSON is invalid
     */
    public static List<Account> readAccounts(Path path) throws IOException, ParseException {
        if (path == null || !Files.exists(path)) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JSONParser parser = new JSONParser();
            Object root = parser.parse(reader);
            JSONArray arr = asArray(root);
            List<Account> result = new ArrayList<>();

            if (arr == null) {
                return result;
            }

            for (Object item : arr) {
                JSONObject obj = asObject(item);
                if (obj == null) {
                    continue;
                }

                Account acc = new Account();
                acc.setEmail(asString(obj.get("email")));
                acc.setPassword(asString(obj.get("password")));
                acc.setPoints(asInt(obj.get("points"), 0));
                List<Integer> gameIds = new ArrayList<>();
                JSONArray games = asArray(obj.get("games"));
                if (games != null) {
                    for (Object gid : games) {
                        gameIds.add(asInt(gid, 0));
                    }
                }

                // AI MODIFICATION START: Commented out this line.
                // Reason: 'acc.setGames' expects List<Game>, but 'gameIds' is List<Integer>.
                // We will implement the logic to link IDs to Game objects in Part 2.
                // acc.setGames(gameIds);
                // AI MODIFICATION END

                result.add(acc);
            }
            return result;
        }
    }

    /**
     * Reads the games from the given JSON file path and returns them as a map by id.
     * The structure strictly follows games.json as provided (no title/genre).
     *
     * @param path path to games.json
     * @return map id -> Game (empty if file missing or array empty)
     * @throws IOException    if I/O fails
     * @throws ParseException if JSON is invalid
     */
    public static Map<Long, Game> readGamesAsMap(Path path) throws IOException, ParseException {
        Map<Long, Game> map = new HashMap<>();
        if (path == null || !Files.exists(path)) {
            return map;
        }
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JSONParser parser = new JSONParser();
            Object root = parser.parse(reader);
            JSONArray arr = asArray(root);
            if (arr == null) return map;
            for (Object item : arr) {
                JSONObject obj = asObject(item);
                if (obj == null) {
                    continue;
                }
                long id = asLong(obj.get("id"), -1);
                if (id < 0) {
                    continue;
                }// skip invalid
                Game g = new Game();
                g.setId(id);

                // players array
                JSONArray playersArr = asArray(obj.get("players"));
                if (playersArr != null) {
                    List<Player> players = new ArrayList<>();
                    for (Object pItem : playersArr) {
                        JSONObject pObj = asObject(pItem);
                        if (pObj == null) {
                            continue;
                        }
                        String email = asString(pObj.get("email"));
                        String color = asString(pObj.get("color"));
                        players.add(new Player(email, color));
                    }
                    g.setPlayers(players);
                }

                // currentPlayerColor
                g.setCurrentPlayerColor(asString(obj.get("currentPlayerColor")));

                // board array
                JSONArray boardArr = asArray(obj.get("board"));
                // AI MODIFICATION: Created a local list to hold pieces before sending them to Game
                List<Piece> boardPieces = new ArrayList<>();

                if (boardArr != null) {
                    for (Object bItem : boardArr) {
                        JSONObject bObj = asObject(bItem);
                        if (bObj == null) {
                            continue;
                        }

                        String type = asString(bObj.get("type"));
                        String colorString = asString(bObj.get("color"));
                        String positionString = asString(bObj.get("position"));

                        // AI MODIFICATION START: Replaced 'new Piece(...)' with concrete classes.
                        // Reason: Piece is abstract and cannot be instantiated.

                        // 1. Convert String to Enum and Position
                        Colors colorEnum = Colors.valueOf(colorString);
                        char col = positionString.charAt(0);
                        int row = Integer.parseInt(positionString.substring(1));
                        Position pos = new Position(col, row);

                        // 2. Factory logic (Switch) to create the correct Piece
                        Piece piece = null;
                        switch (type) {
                            case "R": piece = new Rook(colorEnum, pos); break;
                            case "N": piece = new Knight(colorEnum, pos); break;
                            case "B": piece = new Bishop(colorEnum, pos); break;
                            case "Q": piece = new Queen(colorEnum, pos); break;
                            case "K": piece = new King(colorEnum, pos); break;
                            case "P": piece = new Pawn(colorEnum, pos); break;
                        }

                        if (piece != null) {
                            boardPieces.add(piece);
                        }
                        // AI MODIFICATION END
                    }
                    // AI MODIFICATION: Pass the list of pieces to the Game object
                    g.setBoard(boardPieces);
                }

                // Parse optional moves array
                JSONArray movesArr = asArray(obj.get("moves"));
                if (movesArr != null) {
                    List<Move> moves = new ArrayList<>();
                    for (Object mItem : movesArr) {
                        JSONObject mObj = asObject(mItem);
                        if (mObj == null) {
                            continue;
                        }

                        String playerColor = asString(mObj.get("playerColor"));
                        String fromStr = asString(mObj.get("from"));
                        String toStr = asString(mObj.get("to"));

                        // AI MODIFICATION START: Parse positions and fix Move constructor
                        Position from = new Position(fromStr.charAt(0), Integer.parseInt(fromStr.substring(1)));
                        Position to = new Position(toStr.charAt(0), Integer.parseInt(toStr.substring(1)));

                        // Added 'null' for captured piece because Move constructor requires 4 arguments
                        moves.add(new Move(Colors.valueOf(playerColor), from, to, null));
                        // AI MODIFICATION END
                    }
                    g.setMoves(moves);
                }
                map.put(id, g);
            }
        }
        return map;
    }

    // -------- helper converters --------

    private static JSONArray asArray(Object o) {
        return (o instanceof JSONArray) ? (JSONArray) o : null;
    }

    private static JSONObject asObject(Object o) {
        return (o instanceof JSONObject) ? (JSONObject) o : null;
    }

    private static String asString(Object o) {
        return o == null ? null : String.valueOf(o);
    }

    private static int asInt(Object o, int def) {
        if (o instanceof Number) return ((Number) o).intValue();
        try {
            return o != null ? Integer.parseInt(String.valueOf(o)) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static long asLong(Object o, long def) {
        if (o instanceof Number) return ((Number) o).longValue();
        try {
            return o != null ? Long.parseLong(String.valueOf(o)) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }
}