import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawingPanel extends JPanel {
    private final java.util.List<Point> dots = new ArrayList<>();

    public void createDots(int count) {
        dots.clear();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(getWidth() - 40) + 20;
            int y = rand.nextInt(getHeight() - 40) + 20;
            dots.add(new Point(x, y));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        g.setColor(Color.BLUE);
        for (Point dot : dots) {
            g.fillOval(dot.x - 5, dot.y - 5, 10, 10);
        }
    }
}
