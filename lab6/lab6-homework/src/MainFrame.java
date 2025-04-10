import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    DrawingPanel drawingPanel;
    ControlPanel controlPanel;

    public MainFrame() {
        super("Dots and Lines Game");

        configPanel = new ConfigPanel(this);
        drawingPanel = new DrawingPanel();
        controlPanel = new ControlPanel(this);

        setLayout(new BorderLayout());
        add(configPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
