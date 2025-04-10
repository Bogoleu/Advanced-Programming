import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class DrawingPanel extends JPanel {
    private final List<Point> dots = new ArrayList<>();
    private final List<Line> lines = new ArrayList<>();
    private Point startPoint = null;

    public DrawingPanel() {
        setBackground(Color.BLACK);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickedPoint = getClosestPoint(e.getPoint());
                if (startPoint == null) {
                    startPoint = clickedPoint;
                } else {
                    if (!startPoint.equals(clickedPoint)) {
                        lines.add(new Line(startPoint, clickedPoint));
                    }
                    startPoint = null;
                }
                repaint();
            }
        });
    }

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

    private Point getClosestPoint(Point p) {
        Point closest = null;
        double minDist = Double.MAX_VALUE;
        for (Point dot : dots) {
            double dist = p.distance(dot);
            if (dist < minDist) {
                minDist = dist;
                closest = dot;
            }
        }
        return closest;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        for (Point dot : dots) {
            g.fillOval(dot.x - 5, dot.y - 5, 10, 10);
        }

        g.setColor(Color.WHITE);
        for (Line line : lines) {
            g.drawLine(line.start.x, line.start.y, line.end.x, line.end.y);
        }
    }

    private static class Line {
        Point start, end;

        Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }
    }
}
