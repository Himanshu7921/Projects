package ListenerManager;

import UICanvas.CanvasPanel;
import java.awt.*;import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseListener, MouseMotionListener {

    CanvasPanel panel;

    public boolean pen = true;
    public boolean circle = false;
    public boolean rectangle = false;
    public boolean triangle = false;
    public boolean rectEraser = false;
    public boolean line = false;

    public int mousePressedX;
    public int mousePressedY;

    public int mouseReleasedX;
    public int mouseReleasedY;

    public int mouseCurrentX;
    public int mouseCurrentY;

    public int mouseClickedX;
    public int mouseClickedY;

    public int lastX;
    public int lastY;

    public MyMouseListener(CanvasPanel panel){
        this.panel = panel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClickedX = e.getX();
        mouseClickedY = e.getY();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int randomNum = panel.selectRandomNumber();
        panel.currentColor = panel.colorSelector.colorSelector(randomNum);
        mousePressedX = e.getX();
        mousePressedY = e.getY();

        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseReleasedX = e.getX();
        mouseReleasedY = e.getY();

        if(line){
            panel.line.drawPermanentLine(mousePressedX, mousePressedY, mouseCurrentX, mouseCurrentY);
        }
        if(rectangle){
            panel.rectangle.draw(mousePressedX, mousePressedY, mouseCurrentX, mouseCurrentY);
        }
        if(rectEraser){
//            if(lastX != -1 && lastY != -1) {
//                panel.g.setColor(Color.WHITE);
//                panel.g.drawLine(lastX, lastY, mouseCurrentX, mouseCurrentY);
//                panel.myMouseListener.lastX = mouseCurrentX;
//                panel.myMouseListener.lastY = mouseCurrentY;
//                panel.repaint();
//            }
        }
        if(circle){
            panel.circle.draw();
        }

        // resetting the co-ordinates
        lastX = -1;
        lastY = -1;
        panel.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseCurrentX = e.getX();
        mouseCurrentY = e.getY();

        if (rectEraser) {
            // Call the erase method to clear at the current pointer location
            panel.eraser.draw(lastX, lastY, mouseCurrentX, mouseCurrentY);
        } else if (pen) {
            panel.pen.draw(lastX, lastY, mouseCurrentX, mouseCurrentY);
        }

        lastX = mouseCurrentX;
        lastY = mouseCurrentY;
        panel.pointer.calculatePointerCoordinates(e.getX(), e.getY());
        panel.repaint();
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        panel.pointer.calculatePointerCoordinates(e.getX(), e.getY());
        if (rectEraser) {
            // write the code here
//            panel.eraser.followPointer(e.getX(), e.getY());
        }
        panel.repaint();
    }

}
