package entity;

import main.GamePanel;
import main.MyKeyListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    GamePanel gp;
    MyKeyListener keyListener;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, MyKeyListener keyListener){
        this.gp = gp;
        screenX = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);
        screenY = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);
        this.keyListener = keyListener;
        setDefaultValues();
        getPlayerImages();
    }
    public void setDefaultValues(){
        worldX = 21 * gp.TILE_SIZE;
        worldY = 23 * gp.TILE_SIZE;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImages(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Images/Player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Images/Player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Images/Player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Images/Player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Images/Player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Images/Player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Images/Player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Images/Player/boy_right_2.png"));
        } catch (Exception e) {
            System.out.println("Error loading player images: " + e);
            e.printStackTrace();
        }
    }
    public void update(){
        if(keyListener.upPressed || keyListener.downPressed || keyListener.leftPressed || keyListener.rightPressed){
            if(keyListener.upPressed){
                worldY -= speed;
                direction = "up";
            }
            if(keyListener.downPressed){
                worldY += speed;
                direction = "down";
            }
            if(keyListener.leftPressed){
                worldX -= speed;
                direction = "left";
            }
            if(keyListener.rightPressed){
                worldX += speed;
                direction = "right";
            }
            spriteCounter++;
            if(spriteCounter > 18){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        BufferedImage image = null;
        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
//        g2.setColor(Color.white);
//        g2.fillRect(playerX, playerY, gp.TILE_SIZE, gp.TILE_SIZE);
    }
}
