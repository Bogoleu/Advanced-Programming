import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {

    public ControlPanel(MainFrame frame) {
        setLayout(new FlowLayout());

        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton exitButton = new JButton("Exit");

        add(saveButton);
        add(loadButton);
        add(exitButton);

        exitButton.addActionListener((ActionEvent e) -> System.exit(0));

    }
}
