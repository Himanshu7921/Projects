package Tools;
import UICanvas.CanvasPanel;
import java.awt.*;

public class Eraser implements Tools {
    private final CanvasPanel panel;
    public final int ERASER_WIDTH = 20;
    public final int ERASER_HEIGHT = 20;
    public final int ERASER_STROKE = 12;

    public Eraser(CanvasPanel panel) {
        this.panel = panel;
    }

    @Override
    public void draw() {}

    public void draw(int x1, int y1, int x2, int y2) {
        if(x1 != -1 && y1 != -1){
            panel.g2d.setColor(panel.getBackgroundColor());
            panel.g2d.setStroke(new BasicStroke(ERASER_STROKE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            panel.g2d.drawLine(x1, y1, x2, y2);
            panel.myMouseListener.lastX = x2;
            panel.myMouseListener.lastY = y2;
            panel.repaint();
        }
    }
}
