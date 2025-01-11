import javax.swing.*;
import java.util.*;
import java.awt.*;
public class GameFrame extends JFrame {
    GameFrame(){
        this.add(new GamePanel());
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}