import javax.swing.*;
import java.awt.*;

public class BlackjackGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // Login Panel
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel loginStatusLabel;

    // Game Panel
    private GamePanel gamePanel = new GamePanel(this);

    // Database manager
    private DatabaseManager dbManager = new DatabaseManager();

    // User info
    private String currentUser = "";

    public BlackjackGUI() {
        setTitle("Blackjack Game");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createLoginPanel();
        setupPanels();

        cardLayout.show(mainPanel, "login");
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(0, 100, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("BLACKJACK LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        loginPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        loginPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(0, 100, 0));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        loginPanel.add(buttonPanel, gbc);

        gbc.gridy++;
        loginStatusLabel = new JLabel(" ");
        loginStatusLabel.setForeground(Color.RED);
        loginStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(loginStatusLabel, gbc);

        usernameField.addActionListener(e -> login());
        passwordField.addActionListener(e -> login());
    }

    private void setupPanels() {
        mainPanel.add(loginPanel, "login");
        mainPanel.add(gamePanel, "game");
        add(mainPanel);
    }

    private void login() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            loginStatusLabel.setText("Complete all fields!");
            return;
        }

        if (dbManager.login(user, pass)) {
            currentUser = user;
            int money = dbManager.getUserMoney(user);
            gamePanel.setPlayerMoney(money);

            clearLoginFields();
            cardLayout.show(mainPanel, "game");
        } else {
            loginStatusLabel.setText("Invalid username or password!");
        }
    }

    private void register() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            loginStatusLabel.setText("Complete all fields!");
            return;
        }

        if (!dbManager.register(user, pass)) {
            loginStatusLabel.setText("User already exists!");
            return;
        }

        loginStatusLabel.setText("Registration successful! You can now login.");
        clearLoginFields();
    }

    private void clearLoginFields() {
        usernameField.setText("");
        passwordField.setText("");
        loginStatusLabel.setText(" ");
    }

    // Apelată când banii jucătorului se modifică în GamePanel
    public void updateMoneyInDB(int newMoney) {
        if (!currentUser.isEmpty()) {
            dbManager.updateUserMoney(currentUser, newMoney);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BlackjackGUI().setVisible(true);
        });
    }
}
