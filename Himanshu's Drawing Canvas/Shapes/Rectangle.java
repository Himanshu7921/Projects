package Shapes;

import Calculations.*;
import Pointer.Pointer;
import UICanvas.CanvasPanel;

import java.awt.*;

public class Rectangle implements Shapes {

    public CanvasPanel panel;
    public DistanceCalculation distanceCalculation;
    public int DIAGONAL;
    public int RECT_WIDTH;
    public int RECT_HEIGHT;

    public Rectangle(CanvasPanel panel){
        this.panel = panel;
        distanceCalculation = new DistanceCalculation();
    }

    @Override
    public void draw() {
        panel.g2d.setColor(panel.currentColor);
        if(!isPointerInside(panel.myMouseListener.mousePressedX, panel.myMouseListener.mousePressedY, panel.myMouseListener.mouseCurrentX, panel.myMouseListener.mouseCurrentY)){
            panel.g2d.fillRect(panel.myMouseListener.mousePressedX, panel.myMouseListener.mousePressedY, getWidth(), getHeight());
        }
    }

    public void draw(int x1, int y1, int x2, int y2) {
        // Calculate width and height as absolute values
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        // Set dimensions for the rectangle
        setWidth(dx);
        setHeight(dy);

        // Determine the top-left corner based on drag direction
        int startX = Math.min(x1, x2);
        int startY = Math.min(y1, y2);

        // Draw the rectangle as a preview on the panel's Graphics object
        panel.g2d.setColor(panel.currentColor);
        // Temporary Triangle for previewing the Direction
        panel.g2d.drawRect(startX, startY, getWidth(), getHeight());
        panel.g2d.fillRect(startX, startY, getWidth(), getHeight());

        // Update panel to show the temporary drawing
        panel.repaint();
    }

    public void dummyRect(Graphics g,int x1, int y1, int x2, int y2){
        Graphics2D g2 = panel.cast(g);
        g2.setStroke(new BasicStroke(panel.strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // Calculate width and height as absolute values
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        // Set dimensions for the rectangle
        setWidth(dx);
        setHeight(dy);

        // Determine the top-left corner based on drag direction
        int startX = Math.min(x1, x2);
        int startY = Math.min(y1, y2);

        // Temporary Triangle for previewing the Direction
        g.drawRect(startX, startY, getWidth(), getHeight());

        // Update panel to show the temporary drawing
        panel.repaint();
    }

    public void setDiagonalL(int DIAGONAL) {
        this.DIAGONAL = DIAGONAL;
    }

    public void setWidth(int width){
        this.RECT_WIDTH = width;
    }

    public void setHeight(int height){
        this.RECT_HEIGHT = height;
    }


    public int getDiagonal() {
        return DIAGONAL;
    }

    public int getWidth(){
        return RECT_WIDTH;
    }

    public int getHeight(){
        return RECT_HEIGHT;
    }

    public boolean isPointerInside(int x1, int y1, int x2, int y2){
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2);
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1, y2);

        // Ensure both x and y of pointer are within bounds
        return (panel.pointer.pointerX > left && panel.pointer.pointerX < right) &&
                (panel.pointer.pointerY > top && panel.pointer.pointerY < bottom);
    }

}