package Pointer;
import UICanvas.*;

import java.util.Random;

public class Pointer {
    public final int POINTER_WIDTH = 12;
    public final int POINTER_HEIGHT = 12;
    public final int POINTER_SPEED = 5;
    public int pointerX;
    public int pointerY;
    Random random;
    CanvasPanel panel;

    public Pointer(CanvasPanel panel){
        this.panel = panel;
        pointerX = panel.CANVAS_WIDTH / 2;
        pointerY = panel.CANVAS_HEIGHT / 2;
        random = new Random();
    }

    public void calculatePointerCoordinates(int x, int y){
//        int dx = x - pointerCurrentPositionX;
//        int dy = y - pointerCurrentPositionY;

//           x = random.nextInt(panel.CANVAS_WIDTH - POINTER_HEIGHT / 2);
//           y = random.nextInt(panel.CANVAS_HEIGHT - POINTER_HEIGHT / 2);
           pointerX = x;
           pointerY = y;
           panel.repaint();

    }
}
