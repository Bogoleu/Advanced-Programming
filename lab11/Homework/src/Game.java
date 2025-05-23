import java.util.*;

public class Game {
    private String id;
    private Player player1;
    private Player player2;
    private GameState gameState = new GameState();
    private long timePerPlayer;
    private Map<String, Long> remainingTime = new HashMap<>();
    private TimerManager timerManager = new TimerManager();
    private String currentTurn;

    public Game(String id, Player creator, long timePerPlayer) {
        this.id = id;
        this.player1 = creator;
        this.timePerPlayer = timePerPlayer;
        this.remainingTime.put(creator.getUsername(), timePerPlayer);
        this.currentTurn = creator.getUsername();
    }

    public synchronized String join(Player p) {
        if (player2 != null) return "Game already full.";
        this.player2 = p;
        remainingTime.put(p.getUsername(), timePerPlayer);
        return "Player " + p.getUsername() + " joined game " + id;
    }

    public synchronized String submitMove(String username, String move) {
        if (!username.equals(currentTurn)) return "Not your turn.";

        long newTime = timerManager.endTurn(username, remainingTime.get(username));
        if (newTime <= 0) return "Time's up. You lost.";
        remainingTime.put(username, newTime);

        if (!gameState.validateMove(move, username)) return "Invalid move.";
        gameState.applyMove(move);


        currentTurn = (currentTurn.equals(player1.getUsername()) ? player2.getUsername() : player1.getUsername());
        timerManager.startTurn(currentTurn);

        return "Move accepted. " + currentTurn + "'s turn.";
    }
}
