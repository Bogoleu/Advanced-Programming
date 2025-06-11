import java.util.*;

public class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }


    public int getScore() {
        int score = 0;
        int aces = 0;
        for (Card c : cards) {
            String name = c.getName();
            String rank = name.split("_of_")[0];
            if ("jack".equals(rank) || "queen".equals(rank) || "king".equals(rank)) {
                score += 10;
            } else if ("ace".equals(rank)) {
                aces++;
                score += 11;
            } else {
                score += Integer.parseInt(rank);
            }
        }
        // Ajustează asii dacă scorul > 21
        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }
        return score;
    }

    public void clear() {
        cards.clear();
    }
}
