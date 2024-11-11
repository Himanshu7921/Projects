import java.util.*;
import java.awt.*;

public class Ball extends Rectangle{
    private final int BALL_DIAMETER = 20;
    Color [] ballColor = {Color.red, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GREEN};
    int xDirection;
    int yDirection;
    int speed = 3;
    int indexOfRandomColor;
    Random random;
    Ball(int x, int y, int width, int height){
        super(x,y,width,height);
        random = new Random();
        indexOfRandomColor = random.nextInt(ballColor.length);
        setInitialDirection();
    }
    public void setInitialDirection(){
        setXDirection((xDirection = random.nextInt(2) == 0 ? -1 : 1) * speed);
        setYDirection((yDirection = random.nextInt(2) == 0 ? -2 : 1) * speed);
    }
    public void move(){
         x += xDirection;
         y += yDirection;
    }
    public void draw(Graphics g){
        g.setColor(ballColor[indexOfRandomColor]);
        g.fillOval(x,y,width,height);
    }
    public void setXDirection(int xVelocity){
        xDirection = xVelocity;
    }
    public void setYDirection(int yVelocity){
        yDirection = yVelocity;
    }
    public void changeColorAfterCollision(int index){
        indexOfRandomColor = index;
    }
}