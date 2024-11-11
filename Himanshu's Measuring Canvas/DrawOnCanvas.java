import java.awt.*;

public class DrawOnCanvas {
    CanvaPanel panel;
    String [] inkColors = {"Red", "Blue", "Green", "Dark Grey", "Purple"};
    CalculateDistance calculateDistance;
    int CIRCLE_VELOCITY = 5;
    int circleCurrentPositionX;
    int circleCurrentPositionY;
    double distance;

    DrawOnCanvas(CanvaPanel panel){
        this.panel = panel;
        calculateDistance = new CalculateDistance();
        circleCurrentPositionX = panel.CANVAS_WIDTH / 2;
        circleCurrentPositionY = panel.CANVAS_HEIGHT / 2;
    }

    public Color colorSelector(int randomNum){
        String currentColor = inkColors[randomNum];
        if(currentColor.equalsIgnoreCase("Red")){
            return Color.RED;
        }
        else if(currentColor.equalsIgnoreCase("Blue")){
            return Color.BLUE;
        }
        else if(currentColor.equalsIgnoreCase("Green")){
            return Color.GREEN;
        }
        else if(currentColor.equalsIgnoreCase("Dark Grey")){
            return Color.DARK_GRAY;
        }
        else if(currentColor.equalsIgnoreCase("Purple")){
            return new Color(160, 32, 240);
        }
        return Color.BLACK;
    }

    public void draw(int x, int y){
        int dx = x - circleCurrentPositionX;
        int dy = y - circleCurrentPositionY;

        double distance = calculateDistance(dx, dy);

//        if(distance > CIRCLE_VELOCITY){
//            circleCurrentPositionX += (int) ((dx / distance) * CIRCLE_VELOCITY);
//            circleCurrentPositionY += (int) ((dy / distance) * CIRCLE_VELOCITY);
//        }
//        else{
//            circleCurrentPositionX = x;
//            circleCurrentPositionY = y;
//        }
//        System.out.println("Circle Position: (" + circleCurrentPositionX + ", " + circleCurrentPositionY + ")");

        circleCurrentPositionX = x;
        circleCurrentPositionY = y;
        panel.repaint();
    }

    // Calculate the Distance between 2 Points
    public double calculateDistance(int dx, int dy){
        return Math.sqrt((dx * dx) + (dy * dy));
    }

}
