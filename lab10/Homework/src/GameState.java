import java.util.*;

public class GameState {
    private List<String> moves = new ArrayList<>();

    public boolean validateMove(String move, String player) {
        return true;
    }

    public void applyMove(String move) {
        moves.add(move);
    }
}
