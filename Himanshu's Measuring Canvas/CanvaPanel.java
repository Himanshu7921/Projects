import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CanvaPanel extends JPanel {
    final int CANVAS_WIDTH = 1000;
    final int CANVAS_HEIGHT = 700;
    MyMouseListener mouseListener;
    DrawOnCanvas drawOnCanvas;
    Random random;
    Color currentColor = Color.BLACK;

    CanvaPanel(){
        mouseListener = new MyMouseListener(this);
        drawOnCanvas = new DrawOnCanvas(this);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.addMouseMotionListener(mouseListener);
        this.addMouseListener(mouseListener);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(currentColor);
        g.fillOval(drawOnCanvas.circleCurrentPositionX - 5, drawOnCanvas.circleCurrentPositionY - 5, 10, 10);
        g.fillOval(mouseListener.mousePressedX - 5, mouseListener.mousePressedY - 5,10,10);
        String str = "("+ (mouseListener.mousePressedX - 5) + ", " + (mouseListener.mousePressedY - 5) + ")";
        String str2 = "("+ (mouseListener.mouseCurrentPositionX - 5) + ", " + (mouseListener.mouseCurrentPositionY - 5) + ")";
        g.drawString(str, mouseListener.mousePressedX, mouseListener.mousePressedY - 10);
        // follow the Mouse
        g.drawLine(mouseListener.mousePressedX, mouseListener.mousePressedY, drawOnCanvas.circleCurrentPositionX, drawOnCanvas.circleCurrentPositionY);
        g.drawString(str2, mouseListener.mouseCurrentPositionX - 5, mouseListener.mouseCurrentPositionY - 5);
    }

    public int selectRandomNumber(){
        random = new Random();
        return random.nextInt(5);
    }
}
