import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ConfigPanel extends JPanel {
    private final JTextField dotsField;
    private final JButton newGameButton;

    public ConfigPanel(MainFrame frame) {
        setLayout(new FlowLayout());

        add(new JLabel("Number of dots:"));
        dotsField = new JTextField(5);
        add(dotsField);

        newGameButton = new JButton("New Game");
        add(newGameButton);

        newGameButton.addActionListener((ActionEvent e) -> {
            int numberOfDots = Integer.parseInt(dotsField.getText());
            frame.getDrawingPanel().createDots(numberOfDots);
        });
    }
}
