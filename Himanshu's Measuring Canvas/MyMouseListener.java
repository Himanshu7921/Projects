import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseMotionListener , MouseListener {
    CanvaPanel panel;
    JLabel position;
    CalculateDistance calculateDistance;

    // get the Co-ordinates of Mouse Pressed Position
    int mousePressedX;
    int mousePressedY;

    // get the Co-ordinates of Mouse Released Position
    int mouseReleasedX;
    int mouseReleasedY;

    int mouseCurrentPositionX;
    int mouseCurrentPositionY;

    MyMouseListener(CanvaPanel panel){
        this.panel = panel;
        position = new JLabel();
        calculateDistance = new CalculateDistance();
        panel.add(position);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        position.setText("Distance Between (" + (mousePressedX - 5 )+ ", " + (mousePressedY - 5) + ") and (" + (e.getX() - 5) + ", " + (e.getY() - 5) + ") is " + calculateDistance.getDistanceBetweenTwoPoints(mousePressedX, mousePressedY, e.getX(), e.getY()));
        mouseCurrentPositionX = e.getX();
        mouseCurrentPositionY = e.getY();
        panel.drawOnCanvas.draw(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        mouseCurrentPositionX = e.getX();
        mouseCurrentPositionY = e.getY();

//        position.setText("Mouse Dragged To (" + e.getX() + ", " + e.getY() + ")");
//        panel.drawOnCanvas.draw(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        position.setText("Mouse Pressed At (" + e.getX() + ", " + e.getY() + ")");
        int randomNum = panel.selectRandomNumber();
        panel.currentColor = panel.drawOnCanvas.colorSelector(randomNum);
        mousePressedX = e.getX();
        mousePressedY = e.getY();
//        panel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseReleasedX = e.getX();
        mouseReleasedY = e.getY();
    }

    // No Need To Implement These Methods!
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}

}
