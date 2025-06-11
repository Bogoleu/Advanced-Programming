import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private int playerMoney = 1000;
    private int bet = 50;
    private Deck deck = new Deck();
    private Hand playerHand = new Hand();
    private Hand dealerHand = new Hand();
    private boolean gameInProgress = false;
    private boolean dealerHidden = true;

    private JLabel moneyLabel, playerScoreLabel, dealerScoreLabel, messageLabel;
    private JPanel playerCardsPanel, dealerCardsPanel;
    private JTextField betField;
    private JButton dealButton, hitButton, standButton;

    private BlackjackGUI blackjackGUI;

    public GamePanel(BlackjackGUI blackjackGUI) {
        this.blackjackGUI = blackjackGUI;

        setLayout(new BorderLayout());
        setBackground(new Color(0, 100, 0));

        // Top panel - Money only
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 100, 0));
        moneyLabel = new JLabel("Money: $" + playerMoney);
        moneyLabel.setForeground(Color.WHITE);
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(moneyLabel);

        // Center panel - Cards with labels
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        centerPanel.setBackground(new Color(0, 100, 0));

        dealerScoreLabel = new JLabel("Dealer: ", SwingConstants.CENTER);
        dealerScoreLabel.setForeground(Color.WHITE);
        dealerScoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dealerCardsPanel = new JPanel(new FlowLayout());
        dealerCardsPanel.setBackground(new Color(0, 100, 0));

        playerScoreLabel = new JLabel("Player: ", SwingConstants.CENTER);
        playerScoreLabel.setForeground(Color.WHITE);
        playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        playerCardsPanel = new JPanel(new FlowLayout());
        playerCardsPanel.setBackground(new Color(0, 100, 0));

        centerPanel.add(dealerScoreLabel);
        centerPanel.add(dealerCardsPanel);
        centerPanel.add(playerScoreLabel);
        centerPanel.add(playerCardsPanel);

        // Bottom panel - Buttons and Bet
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0, 100, 0));

        dealButton = new JButton("Deal");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");

        JLabel betLabel = new JLabel("Bet: $");
        betLabel.setForeground(Color.WHITE);
        betField = new JTextField("50", 5);

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 12));

        dealButton.addActionListener(e -> deal());
        hitButton.addActionListener(e -> hit());
        standButton.addActionListener(e -> stand());

        bottomPanel.add(dealButton);
        bottomPanel.add(hitButton);
        bottomPanel.add(standButton);
        bottomPanel.add(betLabel);
        bottomPanel.add(betField);
        bottomPanel.add(messageLabel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        updateButtons();
    }

    private void deal() {
        try {
            bet = Integer.parseInt(betField.getText());
            if (bet > playerMoney || bet <= 0) {
                messageLabel.setText("Invalid bet!");
                return;
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid bet!");
            return;
        }

        playerMoney -= bet;
        blackjackGUI.updateMoneyInDB(playerMoney);  // Actualizare bani in DB

        playerHand.clear();
        dealerHand.clear();
        dealerHidden = true;
        gameInProgress = true;
        messageLabel.setText("Dealing...");
        updateButtons();

        // Deal cards with animation
        Timer dealTimer = new Timer(500, null);
        int[] cardCount = {0};
        dealTimer.addActionListener(e -> {
            if (cardCount[0] == 0) playerHand.addCard(deck.drawCard());
            else if (cardCount[0] == 1) dealerHand.addCard(deck.drawCard());
            else if (cardCount[0] == 2) playerHand.addCard(deck.drawCard());
            else if (cardCount[0] == 3) {
                dealerHand.addCard(deck.drawCard());
                dealTimer.stop();
                messageLabel.setText(" ");
                if (playerHand.getScore() == 21) {
                    Timer timer = new Timer(800, ev -> stand());
                    timer.setRepeats(false);
                    timer.start();
                }
            }
            updateDisplay();
            cardCount[0]++;
        });
        dealTimer.start();
    }

    private void hit() {
        if (!gameInProgress) return;

        messageLabel.setText("Hitting...");
        updateButtons();

        Timer hitTimer = new Timer(300, e -> {
            playerHand.addCard(deck.drawCard());
            updateDisplay();
            messageLabel.setText(" ");

            if (playerHand.getScore() > 21) {
                messageLabel.setText("Bust! You lose $" + bet);
                gameInProgress = false;
            }
            updateButtons();
        });
        hitTimer.setRepeats(false);
        hitTimer.start();
    }

    private void stand() {
        if (!gameInProgress) return;

        dealerHidden = false;
        messageLabel.setText("Dealer's turn...");
        updateButtons();

        Timer dealerTimer = new Timer(800, null);
        dealerTimer.addActionListener(e -> {
            if (dealerHand.getScore() < 17) {
                dealerHand.addCard(deck.drawCard());
                updateDisplay();
            } else {
                dealerTimer.stop();
                finishGame();
            }
        });
        dealerTimer.start();
        updateDisplay();
    }

    private void finishGame() {
        int playerScore = playerHand.getScore();
        int dealerScore = dealerHand.getScore();

        if (dealerScore > 21) {
            messageLabel.setText("Dealer busts! You win $" + bet);
            playerMoney += bet * 2;
        } else if (playerScore > dealerScore) {
            messageLabel.setText("You win $" + bet);
            playerMoney += bet * 2;
        } else if (playerScore == dealerScore) {
            messageLabel.setText("Push! Bet returned");
            playerMoney += bet;
        } else {
            messageLabel.setText("Dealer wins! You lose $" + bet);
        }

        blackjackGUI.updateMoneyInDB(playerMoney);  // Actualizare bani in DB

        gameInProgress = false;
        updateDisplay();
        updateButtons();
    }

    private void updateDisplay() {
        moneyLabel.setText("Money: $" + playerMoney);

        // Update scores
        playerScoreLabel.setText("Player: " + playerHand.getScore());
        if (dealerHidden && !dealerHand.getCards().isEmpty()) {
            dealerScoreLabel.setText("Dealer: ?");
        } else {
            dealerScoreLabel.setText("Dealer: " + dealerHand.getScore());
        }

        // Update card displays
        updateCardPanel(playerCardsPanel, playerHand, false);
        updateCardPanel(dealerCardsPanel, dealerHand, dealerHidden);
    }

    private void updateCardPanel(JPanel panel, Hand hand, boolean hideFirst) {
        panel.removeAll();
        for (int i = 0; i < hand.getCards().size(); i++) {
            Card card = hand.getCards().get(i);
            JLabel cardLabel;

            if (hideFirst && i == 0) {
                try {
                    ImageIcon backIcon = new ImageIcon(getClass().getResource("/cards/back.png"));
                    Image backImage = backIcon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
                    cardLabel = new JLabel(new ImageIcon(backImage));
                } catch (Exception e) {
                    cardLabel = new JLabel();
                    cardLabel.setPreferredSize(new Dimension(80, 120));
                    cardLabel.setOpaque(true);
                    cardLabel.setBackground(Color.BLUE);
                    cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    cardLabel.setText("?");
                    cardLabel.setForeground(Color.WHITE);
                }
            } else {
                Image cardImage = card.getImage();
                if (cardImage != null) {
                    Image scaledImage = cardImage.getScaledInstance(80, 120, Image.SCALE_SMOOTH);
                    cardLabel = new JLabel(new ImageIcon(scaledImage));
                } else {
                    cardLabel = new JLabel(new ImageIcon(card.getImage()));
                }
            }
            panel.add(cardLabel);
        }
        panel.revalidate();
        panel.repaint();
    }

    private void updateButtons() {
        dealButton.setEnabled(!gameInProgress);
        hitButton.setEnabled(gameInProgress);
        standButton.setEnabled(gameInProgress);
    }

    public void setPlayerMoney(int money) {
        this.playerMoney = money;
        updateDisplay();
    }

    public int getPlayerMoney() {
        return playerMoney;
    }
}
