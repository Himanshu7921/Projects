package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int worldX;
    public int worldY;
    int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    int spriteCounter;
    int spriteNum = 1;
}