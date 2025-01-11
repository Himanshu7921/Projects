package main;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.add(new GamePanel());
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Tomiyoka's Adventure");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}