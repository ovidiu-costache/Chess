package src;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private List<Account> users;
    // LONG because of JsonreaderUtil
    private Map<Long, Game> games;
    private Account currentUser;
    private Scanner scanner;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.currentUser = null;
    }

    public void read() {
        // Using try for exceptions
        try {
            this.users = JsonReaderUtil.readAccounts(Paths.get("input","accounts.json"));
            this.games = JsonReaderUtil.readGamesAsMap(Paths.get("input","games.json"));

            System.out.println("DEBUG: S-au citit " + users.size() + " conturi");
            System.out.println("DEBUG: S-au citit " + games.size() + " jocuri");
        } catch (IOException | ParseException e) {
            System.out.println("Nu s-au putut citi fisierele");
        }
    }

    public void write() {
        JSONArray userList = new JSONArray();

        for (Account acc : this.users) {
            JSONObject userObj = new JSONObject();

            userObj.put("email", acc.getEmail());
            userObj.put("password", acc.getPassword());
            userObj.put("points", acc.getPoints());

            JSONArray gamesList = new JSONArray();
            for (Game game : acc.getGames())
                gamesList.add(game.getId());
            userObj.put("games", gamesList);
            userList.add(userObj);
        }

        try (java.io.FileWriter file = new java.io.FileWriter("input/accounts.json")) {
            file.write(userList.toJSONString());
            file.flush();
            System.out.println("Datele au fost salvate cu succes!");
        } catch (IOException e) {
            System.out.println("Eroare la salvarea datelor: " + e.getMessage());
        }
    }

    public Account login(String email, String password) {
        for (Account user : this.users)
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                this.currentUser = user;
                System.out.println("Autentificare reusita pentru " + email);
                return user;
            }
        System.out.println("Email sau parola gresita");
        return null;
    }

    public Account newAccount(String email, String password) {
        Account newUser = new Account();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPoints(0);

        this.users.add(newUser);
        this.currentUser = newUser;

        write();

        System.out.println("Cont creat pentru " + email);
        return newUser;
    }

    public void run() {
        boolean isRunning = true;

        while(isRunning) {
            // No current user
            if (this.currentUser == null) {
                System.out.println("-------------------------------------------------");
                System.out.println("                 MENIUL PRINCIPAL                ");
                System.out.println("-------------------------------------------------");
                System.out.println("Alege o optiune: 1. Login; 2. Cont nou; 3. Iesire");

                if (scanner.hasNextInt()) {
                    int cmd = scanner.nextInt();
                    scanner.nextLine();

                    switch (cmd) {
                        case 1:
                            System.out.print("Email: ");
                            String email = scanner.nextLine();
                            System.out.print("Parola: ");
                            String pass = scanner.nextLine();
                            login(email, pass);
                            break;
                        case 2:
                            System.out.print("Email nou: ");
                            String newEmail = scanner.nextLine();
                            System.out.print("Parola noua: ");
                            String newPass = scanner.nextLine();
                            newAccount(newEmail, newPass);
                            break;
                        case 3:
                            System.out.println("La revedere!");
                            isRunning = false;
                            break;
                        default:
                            System.out.println("Optiune invalida!");
                    }
                } else {
                    System.out.println("Introdu un numar!");
                    scanner.next();
                }
            } else {
                // The user is logged in
                System.out.println("----------------------------------------------------------");
                System.out.println("                    MENIUl PRINCIPAL                      ");
                System.out.println("----------------------------------------------------------");
                System.out.println("Bine ai venit " + currentUser.getEmail() + "!");
                System.out.println("Alege o optiune: 1. Joc Nou; 2. Jocurile Mele; 3. Delogare");

                if (scanner.hasNextInt()) {
                    int cmd = scanner.nextInt();
                    scanner.nextLine();

                    switch(cmd) {
                        case 1:
                            System.out.println("Incepe un joc nou ...");
                            Game newGame = new Game();
                            newGame.getBoard().initialize();

                            boolean gameRunning = true;
                            while (gameRunning) {
                                System.out.println(newGame.getBoard().toString());
                                System.out.println("Mutarea viitoare: (ex. A2-A3) sau alege EXIT");
                                String input = scanner.nextLine();

                                if (input.equalsIgnoreCase("EXIT")) {
                                    gameRunning = false;
                                    break;
                                }

                                if (input.length() == 5 && input.charAt(2) == '-') {
                                    // Start postion
                                    char col1 = input.charAt(0);
                                    int row1 = Integer.parseInt(String.valueOf(input.charAt(1)));
                                    Position from = new Position(col1, row1);

                                    // Final position
                                    char col2 = input.charAt(3);
                                    int row2 = Integer.parseInt(String.valueOf(input.charAt(4)));
                                    Position to = new Position(col2, row2);

                                    if (newGame.getBoard().isValidMove(from, to)) {
                                        newGame.getBoard().movePiece(from, to);
                                        System.out.println("Mutarea a fost efectuata!");
                                    } else {
                                        System.out.println("Formatul este gresit! EXEMPLU: A2-A3 (pozitie actuala - pozitie finala)");
                                    }
                                }
                            }

                            break;
                        case 2:
                            System.out.println("Jocurile tale: " + currentUser.getGames().size());
                            // Will print the games
                            break;
                        case 3:
                            System.out.println("Te-ai delogat cu succes!");
                            this.currentUser = null;
                            break;
                        default:
                            System.out.println("Optiune invalida!");
                    }
                } else {
                    System.out.println("Introdu un numar!");
                    scanner.next();
                }
            }
        }
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.read();
        app.run();
    }
}