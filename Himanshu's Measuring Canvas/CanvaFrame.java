import javax.swing.*;

public class CanvaFrame extends JFrame {
    CanvaFrame(){
        this.add(new CanvaPanel());
        this.setTitle("Himanshu Measuring Canvas");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
