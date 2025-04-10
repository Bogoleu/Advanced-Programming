import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ControlPanel extends JPanel {
    private final JButton saveButton, loadButton, exitButton, exportButton;

    public ControlPanel(MainFrame frame) {
        setLayout(new FlowLayout());

        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        exitButton = new JButton("Exit");
        exportButton = new JButton("Export Image");

        add(saveButton);
        add(loadButton);
        add(exitButton);
        add(exportButton);

        saveButton.addActionListener((ActionEvent e) -> saveGame(frame.getDrawingPanel()));
        loadButton.addActionListener((ActionEvent e) -> loadGame(frame.getDrawingPanel()));
        exitButton.addActionListener((ActionEvent e) -> System.exit(0));
        exportButton.addActionListener((ActionEvent e) -> exportImage(frame.getDrawingPanel()));
    }

    private void saveGame(DrawingPanel drawingPanel) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game.dat"))) {
            out.writeObject(drawingPanel);
            JOptionPane.showMessageDialog(this, "Game saved successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadGame(DrawingPanel drawingPanel) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("game.dat"))) {
            DrawingPanel loadedPanel = (DrawingPanel) in.readObject();
            // Actualizează panelul curent cu cel încărcat
            drawingPanel.repaint();
            JOptionPane.showMessageDialog(this, "Game loaded successfully!");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void exportImage(DrawingPanel drawingPanel) {
        try {
            BufferedImage image = new BufferedImage(drawingPanel.getWidth(), drawingPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            drawingPanel.paint(g2d);
            ImageIO.write(image, "PNG", new File("game_board.png"));
            JOptionPane.showMessageDialog(this, "Image exported successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
