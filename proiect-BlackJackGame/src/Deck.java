import java.util.*;


public class Deck {
    private List<Card> cards;
    private int currentIndex;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                String cardName = rank + "_of_" + suit;
                cards.add(new Card(cardName));
            }
        }

        shuffle();
        currentIndex = 0;
    }

    public void shuffle() {
        Collections.shuffle(cards);
        currentIndex = 0;
    }

    public Card drawCard() {
        if (currentIndex >= cards.size()) {
            shuffle();
        }
        return cards.get(currentIndex++);
    }
}
