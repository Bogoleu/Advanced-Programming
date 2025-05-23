import java.util.*;

public class TimerManager {
    private Map<String, Long> lastMoveTime = new HashMap<>();

    public void startTurn(String username) {
        lastMoveTime.put(username, System.currentTimeMillis());
    }

    public long endTurn(String username, long currentRemaining) {
        long now = System.currentTimeMillis();
        long used = now - lastMoveTime.getOrDefault(username, now);
        return currentRemaining - used;
    }
}
