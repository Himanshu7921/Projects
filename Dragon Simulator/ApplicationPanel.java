import javax.swing.*;
import java.awt.*;

public class ApplicationPanel extends JPanel {
    int SCREEN_WIDTH = 1000;
    int SCREEN_HEIGHT = 700;
    MoveDragon dragon;
    Thread gameThread;
    ApplicationPanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.WHITE);
        dragon = new MoveDragon(this);
        this.addMouseMotionListener(new MyMouseMotionListener(this, dragon));
    }

   @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillOval(dragon.dragonX, dragon.dragonY, 20, 20);
   }
}
