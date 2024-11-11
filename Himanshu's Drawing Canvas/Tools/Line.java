package Tools;
import UICanvas.*;
import java.awt.*;

public class Line implements Tools {

    public CanvasPanel panel;
    int lineWidth;

    public Line(CanvasPanel panel){
        this.panel = panel;
        this.lineWidth = 6;
    }

    // For Future Reference
    // Method to change the pen width
    public void setLineWidth(int width) {
        this.lineWidth = width;
    }

    // Getter for pen width if needed elsewhere
    public int getLineWidth() {
        return lineWidth;
    }

    public void drawPermanentLine(int x1, int y1, int x2, int y2){
        panel.g2d.setColor(panel.currentColor);
        panel.g2d.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        panel.g2d.drawLine(x1, y1, x2, y2);
        panel.repaint();
        panel.g2d.setStroke(new BasicStroke(1));
    }
    public void drawDummyLine(Graphics g, int x1, int y1, int x2, int y2){
        Graphics2D g2 = panel.cast(g);
        g2.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(x1, y1, x2, y2);
    }
    @Override
    public void draw() {}
    @Override
    public void draw(int x1, int y1, int x2, int y2) {}
}
