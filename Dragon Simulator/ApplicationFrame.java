import javax.swing.*;

public class ApplicationFrame extends JFrame{
    ApplicationFrame(){
        this.add(new ApplicationPanel());
        this.setTitle("Dragon Simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
