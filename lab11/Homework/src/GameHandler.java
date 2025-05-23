import java.io.*;
import java.net.*;
import java.util.Map;

public class GameHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Map<String, Game> games;

    public GameHandler(Socket socket, Map<String, Game> games) {
        this.socket = socket;
        this.games = games;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Welcome to the Game Server!");

            String input;
            while ((input = in.readLine()) != null) {
                String[] parts = input.split(" ");
                String cmd = parts[0];

                switch (cmd) {
                    case "create":
                        String gameId = "game" + System.currentTimeMillis();
                        Player creator = new Player(parts[1]);
                        Game game = new Game(gameId, creator, 300_000); // 5 minutes
                        games.put(gameId, game);
                        out.println("Game created with ID: " + gameId);
                        break;
                    case "join":
                        Game g = games.get(parts[1]);
                        if (g != null) {
                            out.println(g.join(new Player(parts[2])));
                        } else {
                            out.println("Game not found.");
                        }
                        break;
                    case "move":
                        Game gm = games.get(parts[1]);
                        if (gm != null) {
                            out.println(gm.submitMove(parts[2], parts[3]));
                        } else {
                            out.println("Game not found.");
                        }
                        break;
                    default:
                        out.println("Unknown command.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
