package src;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.Timer;

public class GamePanel extends JPanel implements ActionListener {

    final static int SCREEN_WIDTH = 600;
    final static int SCREEN_HEIGHT = 600;
    final static int UNIT_SIZE = 30;
    final int DELAY = 100;
    final int GAME_COMPONENTS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    int x[] = new int[GAME_COMPONENTS];
    int y[] = new int[GAME_COMPONENTS];
    Timer timer;
    Random random = new Random();
    char direction = 'R';
    int BODY_PARTS = 3;
    int mousex;
    int mousey;
    int mouseEaten = 0;
    Font scoreFont = new Font("Arial", Font.BOLD, 25);
    Font gameOverFont = new Font("Arial", Font.BOLD, 50);

    boolean isRunning = false;
    Image mouseImage; // Field to store the mouse image
    JLabel score;
    GamePanel() {
        // score of player
        score = new JLabel("SCORE " + mouseEaten);
        // Load the mouse image
        ImageIcon icon = new ImageIcon("D:\\Code Playground\\Projects\\Snake_Game\\src\\Images\\mouse.png");
        mouseImage = icon.getImage();
        // Resize the image if necessary
        mouseImage = mouseImage.getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_SMOOTH);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
//        this.setBackground(Color.white);
        this.setBackground(new Color(246, 224, 181));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        // timer DELAY for Snake Movements
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                actionPerformed(null);
            }
        }, 0, DELAY);
        newMouse();
        isRunning = true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
//         Drawing vertical lines
//        for (int i = 1; i <= SCREEN_WIDTH / UNIT_SIZE; i++) {
//            g.drawLine(i * UNIT_SIZE, 1, i * UNIT_SIZE, SCREEN_HEIGHT);
//            g.drawLine(1, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//        }

        if(isRunning){
            // Draw the mouse image
            g.drawImage(mouseImage, mousex, mousey, this);
            // Draw the snake
            for (int i = 0; i <  BODY_PARTS; i++) {
                // Head of Snake
                if(i == 0){
                    g.setColor(new Color(221, 198, 198));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                // Body of Snake
                else{
                    g.setColor(new Color(253, 228, 242));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
            g.setColor(new Color(102, 84, 94));
            g.setFont(new Font("Verdana.", Font.BOLD, 20));
            g.drawString("Score  " + mouseEaten, 275 ,g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        // Game Over Text
        g.setColor(new Color(74, 123, 166));
        g.setFont(new Font("Arial",Font.BOLD,70));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        // Show Final Score
        g.setColor(new Color(100, 1, 16));
        g.setFont(new Font("Arial",Font.BOLD,35));
        FontMetrics Scoremetrics = getFontMetrics(g.getFont());
        g.drawString("Score  " + mouseEaten, (SCREEN_WIDTH - Scoremetrics.stringWidth("Score  " + mouseEaten)) / 2, g.getFont().getSize());
    }

    public void checkCollisions() {
        // Snake's Head collide with its body
        for (int i = BODY_PARTS; i > 0; i--) {
            if(x[0] == x[i] && y[0] == y[i]){
                isRunning = false;
                timer.cancel();
            }
        }
        // Snake's head collide with Walls
        // collision with Right/left wall
        if(x[0] < 0 || x[0] > SCREEN_WIDTH){
            isRunning = false;
            timer.cancel();
        }
        // collision with upper/lower wall
        if(y[0] < 0 || y[0] > SCREEN_HEIGHT){
            isRunning = false;
            timer.cancel();
        }
    }

    // update the length of snake and respawn the next mouse
    public void checkMouse() {
        if(x[0] == mousex && y[0] == mousey){
            BODY_PARTS++;
            mouseEaten++;
            this.add(score);// Score
            newMouse();
        }
    }

    public void move() {
        for (int i = BODY_PARTS; i > 0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
        }
    }

    public void newMouse() {
        mousex = random.nextInt((int)((SCREEN_WIDTH / UNIT_SIZE)))*UNIT_SIZE;
        mousey = random.nextInt((int)((SCREEN_HEIGHT/ UNIT_SIZE)))*UNIT_SIZE;
    }

    // Inner class implementation
    class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                // case - 1
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction = 'L';
                    }
                    break;
                // case - 2
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction = 'R';
                    }
                    break;
                // case - 3
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction = 'U';
                    }
                    break;
                // case - 4
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isRunning){
            move();
            checkMouse();
            checkCollisions();
        }
        repaint();
    }
}