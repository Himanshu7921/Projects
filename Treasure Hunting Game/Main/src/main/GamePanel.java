package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    static final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 pixels
    static final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48 x 48
    public final int NO_OF_COL = 18;
    public final int NO_OF_ROW = 12;
    public final int SCREEN_WIDTH = TILE_SIZE * NO_OF_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * NO_OF_ROW;

    // World Settings
    public final int worldRow = 50;
    public final int worldCol = 50;
    public final int maxWorldWidth = worldRow * TILE_SIZE;
    public final int maxWorldHeight =  worldCol * TILE_SIZE;
    private final int FPS = 60;

    public Player player;
    MyKeyListener keyListener;
    Thread gameThread;
    TileManager tileManager;

    GamePanel(){
        tileManager = new TileManager(this);
        gameThread = new Thread(this);
        keyListener = new MyKeyListener();
        player = new Player(this, keyListener);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyListener);
        gameThread.start();
    }

    private void update() {
        player.update();
    }
    private void draw(Graphics g) {
         tileManager.draw(g);
        player.draw(g);
    }

    @Override
    public void run() {
        long currentTime = System.nanoTime();
        long timeGap = 1000000000/FPS;   // 0.016667 sec
        long nextDraw = currentTime + timeGap;
        while(gameThread != null){
            update();
            repaint();
            try{
                long remainigTime = nextDraw - currentTime;
                remainigTime = remainigTime / 1000000;
                if(remainigTime < 0){
                    remainigTime = 0;
                }
               Thread.sleep((long)remainigTime);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
}
