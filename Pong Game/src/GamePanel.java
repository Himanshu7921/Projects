import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.*;


public class GamePanel extends JPanel implements Runnable {
    private final int GAME_WIDTH = 1000;
    private final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    private final int PADDLE_WIDTH = 40;
    private final int PADDLE_HEIGHT = 90;
    private final int BALL_DIAMETER = 20;
    private final int NUM_BALLS = 10;
    static Paddle paddle1;
    static Paddle paddle2;
    SoundEffects soundEffect;
    Ball ball;
    Score score;
    Random random;
    Thread thread;

    GamePanel() {
        soundEffect = new SoundEffects();
        this.setBackground(Color.black);
        soundEffect.playSoundLoop("src/SoundTracks/background_music.wav");
        this.setPreferredSize(SCREEN_SIZE);
        this.setFocusable(true);
        paddle1 = new Paddle(0, GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 2);
        newBall();
        score = new Score(0, 0, GAME_WIDTH , GAME_HEIGHT);
        thread = new Thread(this);
        thread.start();
        this.addKeyListener(new MyKeyAdapter());
    }

    private void newBall() {
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2, GAME_HEIGHT / 2 - BALL_DIAMETER / 2, BALL_DIAMETER, BALL_DIAMETER);
    }

    @Override
    public void run() {
        try {
            long lastTime = System.nanoTime();
            double amountOfTicks = 60.0;
            double nanoSec = 1000000000 / amountOfTicks;
            double delta = 0;
            while (true) {
                long now = System.nanoTime();
                delta += (now - lastTime) / nanoSec;
                lastTime = now;
                if (delta >= 1) {
                    move();
                    checkCollisions();
                    repaint();
                    delta--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollisions() {
        soundEffect = new SoundEffects();
        // upper and lower walls
        if(ball.y <= 0 || ball.y >= GAME_HEIGHT - BALL_DIAMETER){
            ball.setXDirection(ball.xDirection < 0 ? ball.xDirection-- : ball.xDirection++);
            ball.setYDirection(-ball.yDirection);
        }
        if(ball.x <= 0){
            soundEffect.playSound("src/SoundTracks/new_ball_spawn.wav");
            score.player2++;
            newBall();
        }
        if(ball.x >= GAME_WIDTH - BALL_DIAMETER){
            soundEffect.playSound("src/SoundTracks/new_ball_spawn.wav");
            score.player1++;
            newBall();
        }

        // collision of paddle with upper and lower walls
        if(paddle1.y <= 0){
            paddle1.y = 0;
        }
        if(paddle2.y <= 0){
            paddle2.y = 0;
        }
        if(paddle1.y >= GAME_HEIGHT - PADDLE_HEIGHT){
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }
        if(paddle2.y >= GAME_HEIGHT - PADDLE_HEIGHT){
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }

        // collisions of paddles & ball
        if (ball.intersects(paddle1)) {
            soundEffect.playSound("src/SoundTracks/ball_hit.wav");
            changeColorOfBall();
            ball.xDirection = Math.abs(ball.xDirection) + 1;
            ball.setXDirection(ball.xDirection);
        }
        if (ball.intersects(paddle2)) {
            soundEffect.playSound("src/SoundTracks/ball_hit.wav");
            changeColorOfBall();
            ball.xDirection = -(Math.abs(ball.xDirection) + 1);
            ball.setXDirection(ball.xDirection);
        }
    }

    public void changeColorOfBall(){
        random = new Random();
        int colorIndexAfterCollision = random.nextInt(ball.ballColor.length);
        if(colorIndexAfterCollision != ball.indexOfRandomColor){
            ball.changeColorAfterCollision(colorIndexAfterCollision);
        }
        else{
            ball.changeColorAfterCollision(4);
        }
    }

    public void displayScore() {
        // Implement score display logic here

    }


    // Inner class implementing KeyListener
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_W) {
                paddle1.moveUp();
            }
            if (key == KeyEvent.VK_S) {
                paddle1.moveDown();
            }
            if (key == KeyEvent.VK_UP) {
                paddle2.moveUp();
            }
            if (key == KeyEvent.VK_DOWN) {
                paddle2.moveDown();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
                paddle1.stopMoving();
            }
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                paddle2.stopMoving();
            }
        }
    }
}