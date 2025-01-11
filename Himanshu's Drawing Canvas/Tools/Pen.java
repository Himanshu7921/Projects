package Tools;
import UICanvas.*;

import java.awt.*;

public class Pen implements Tools {
    CanvasPanel panel;
    int penWidth;
    public Pen(CanvasPanel panel){
        this.panel = panel;
        this.penWidth = 6;
    }

    // For Future Reference
    // Method to change the pen width
    public void setPenWidth(int width) {
        this.penWidth = width;
    }

    // Getter for pen width if needed elsewhere
    public int getPenWidth() {
        return penWidth;
    }

    @Override
    public void draw() {}

    public void draw(int x1, int y1, int x2, int y2) {
        if(x1 != -1 && y1 != -1){
            panel.g2d.setColor(panel.currentColor);
            panel.g2d.setStroke(new BasicStroke(penWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            panel.g2d.drawLine(x1, y1, x2, y2);
            panel.myMouseListener.lastX = x2;
            panel.myMouseListener.lastY = y2;
            panel.repaint();
        }
    }
}
