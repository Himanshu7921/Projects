import java.awt.event.KeyEvent;
import java.awt.*;
public class Paddle extends Rectangle{
    int speed = 15;
    int yVelocity;
    int id;
    Paddle(int x, int y, int width, int height, int id){
        super(x,y,width,height);
        this.id = id;
    }
    public void move(){
        y += yVelocity;
    }
    public void draw(Graphics g){
        if(id == 1){
            g.setColor(Color.red);
        }
        else{
            g.setColor(Color.blue);
        }
        g.fillRect(x,y,width,height);
    }
    public void moveUp(){
        yVelocity = -speed;
    }
    public void moveDown(){
        yVelocity = speed;
    }

    public void stopMoving() {
        yVelocity = 0;
    }

}