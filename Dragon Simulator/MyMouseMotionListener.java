import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MyMouseMotionListener implements MouseMotionListener{
    JLabel statusLabel;
    ApplicationPanel panel;
    MoveDragon dragon;

    MyMouseMotionListener(ApplicationPanel panel, MoveDragon dragon){
        this.panel = panel;
        this.dragon = dragon;
        statusLabel = new JLabel();
        panel.add(statusLabel);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // No Need To Implement this section
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        statusLabel.setText("Mouse Current Position (" + e.getX() + ", " + e.getY() + ")");
        dragon.updateDragonCoordinates(e.getX(), e.getY());
    }
}
