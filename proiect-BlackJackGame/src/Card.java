import javax.swing.*;
import java.awt.*;


public class Card {
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    public enum Rank {
        ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10);

        private final int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            switch(this) {
                case ACE: return "ace";
                case TWO: return "2";
                case THREE: return "3";
                case FOUR: return "4";
                case FIVE: return "5";
                case SIX: return "6";
                case SEVEN: return "7";
                case EIGHT: return "8";
                case NINE: return "9";
                case TEN: return "10";
                case JACK: return "jack";
                case QUEEN: return "queen";
                case KING: return "king";
                default: return "";
            }
        }
    }

    private String name;       // ex: "2_of_clubs"
    private ImageIcon imageIcon;
    private Suit suit;
    private Rank rank;

    public Card(String name) {
        this.name = name;
        parseNameToSuitAndRank();
        loadImage();
    }

    private void parseNameToSuitAndRank() {
        try {
            String[] parts = name.split("_of_");
            if (parts.length == 2) {
                String rankStr = parts[0].toLowerCase();
                String suitStr = parts[1].toLowerCase();

                // Parse rank
                switch(rankStr) {
                    case "ace": this.rank = Rank.ACE; break;
                    case "2": this.rank = Rank.TWO; break;
                    case "3": this.rank = Rank.THREE; break;
                    case "4": this.rank = Rank.FOUR; break;
                    case "5": this.rank = Rank.FIVE; break;
                    case "6": this.rank = Rank.SIX; break;
                    case "7": this.rank = Rank.SEVEN; break;
                    case "8": this.rank = Rank.EIGHT; break;
                    case "9": this.rank = Rank.NINE; break;
                    case "10": this.rank = Rank.TEN; break;
                    case "jack": this.rank = Rank.JACK; break;
                    case "queen": this.rank = Rank.QUEEN; break;
                    case "king": this.rank = Rank.KING; break;
                    default: this.rank = Rank.ACE;
                }

                // Parse suit
                switch(suitStr) {
                    case "hearts": this.suit = Suit.HEARTS; break;
                    case "diamonds": this.suit = Suit.DIAMONDS; break;
                    case "clubs": this.suit = Suit.CLUBS; break;
                    case "spades": this.suit = Suit.SPADES; break;
                    default: this.suit = Suit.HEARTS;
                }
            }
        } catch (Exception e) {

            this.suit = Suit.HEARTS;
            this.rank = Rank.ACE;
        }
    }

    private void loadImage() {
        String path = "/cards/" + name + ".png";
        try {
            imageIcon = new ImageIcon(getClass().getResource(path));
            if (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Image not loaded properly: " + path);
                imageIcon = null;
            }
        } catch (Exception e) {
            System.err.println("Cannot load image: " + path);
            imageIcon = null;
        }
    }

    public Image getImage() {
        return imageIcon.getImage();
    }

    private String getRankDisplay() {
        switch(rank) {
            case ACE: return "A";
            case JACK: return "J";
            case QUEEN: return "Q";
            case KING: return "K";
            default: return String.valueOf(rank.getValue());
        }
    }

    private String getSuitSymbol() {
        switch(suit) {
            case HEARTS: return "♥";
            case DIAMONDS: return "♦";
            case CLUBS: return "♣";
            case SPADES: return "♠";
            default: return "?";
        }
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return getRankDisplay() + " of " + suit.toString().toLowerCase();
    }
}