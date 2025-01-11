package Shapes;
import Calculations.*;
import UICanvas.CanvasPanel;

import java.awt.*;

public class Circle implements Shapes {

    public CanvasPanel panel;
    // default Values
    private int CIRCLE_RADIUS ;
    private int CIRCLE_DIAMETER;

    public int getCircleCenterX() {
        return circleCenterX;
    }

    public void setCircleCenterX(int circleCenterX) {
        this.circleCenterX = circleCenterX;
    }

    public int getCircleCenterY() {
        return circleCenterY;
    }

    public void setCircleCenterY(int circleCenterY) {
        this.circleCenterY = circleCenterY;
    }

    private int circleCenterX;
    private int circleCenterY;

    public Circle(CanvasPanel panel){
        this.panel = panel;
        CIRCLE_RADIUS = 30;
        CIRCLE_DIAMETER = 2 * CIRCLE_RADIUS;
    }

    @Override
    public void draw() {
        getCircleParameters(panel.myMouseListener.mousePressedX, panel.myMouseListener.mousePressedY, panel.myMouseListener.mouseCurrentX, panel.myMouseListener.mouseCurrentY);
        panel.g2d.setColor(panel.currentColor);
        panel.g2d.fillOval(getCircleCenterX() - CIRCLE_RADIUS, getCircleCenterY() - CIRCLE_RADIUS, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
    }

    public void getCircleParameters(int x1, int y1, int x2, int y2){
        CIRCLE_DIAMETER = panel.distance.calculateDistance(x1, y1, x2, y2);
        CIRCLE_RADIUS = CIRCLE_DIAMETER / 2;
    }

    public void dummyCircle(Graphics g, int x1, int y1, int x2, int y2){
        Graphics2D g2 = panel.cast(g);
        g2.setStroke(new BasicStroke(panel.strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        setCircleCenterX(x1 + (x2-x1)/2);
        setCircleCenterY(y1 + (y2-y1)/2);
        getCircleParameters(panel.myMouseListener.mousePressedX, panel.myMouseListener.mousePressedY, panel.myMouseListener.mouseCurrentX, panel.myMouseListener.mouseCurrentY);
        g.drawOval(getCircleCenterX() - CIRCLE_RADIUS, getCircleCenterY() - CIRCLE_RADIUS, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        panel.repaint();
    }

}
