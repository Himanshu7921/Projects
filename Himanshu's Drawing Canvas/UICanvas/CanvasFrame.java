package UICanvas;

import javax.swing.*;
import java.awt.*;

public class CanvasFrame extends JFrame {
    CanvasPanel canvasPanel;
    CanvasFrame(){
        canvasPanel = new CanvasPanel();
        this.add(canvasPanel.toolbar, BorderLayout.NORTH);
        this.add(canvasPanel, BorderLayout.CENTER);
        this.setTitle("Himanshu CanvasApplication");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // Request focus for the canvasPanel after adding components
        SwingUtilities.invokeLater(() -> canvasPanel.requestFocusInWindow());
    }
}
