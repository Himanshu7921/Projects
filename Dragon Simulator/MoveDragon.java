public class MoveDragon {
    ApplicationPanel panel;
    int dragonX;
    int dragonY;

    final int SPEED = 5;

    MoveDragon(ApplicationPanel panel){
        this.panel = panel;
        this.dragonX = panel.SCREEN_WIDTH / 2;
        this.dragonY = panel.SCREEN_HEIGHT / 2;
    }

    public double getDistance(int deltaX, int deltaY){
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    public void updateDragonCoordinates(int targetX, int targetY){

        // Get the Difference in Co-ordinates
        int deltaX = targetX - dragonX;
        int deltaY = targetY - dragonY;

        // Get The Distance it needs To Travel (head of the dragon)
        double distance = getDistance(deltaX,deltaY);

        // Normalize the direction vector (deltaX, deltaY) and scale by SPEED to ensure smooth movement.
        if(distance > SPEED){
            dragonX += (int) ((deltaX / distance) * SPEED);
            dragonY += (int) ((deltaY / distance) * SPEED);
        }
        else{
            dragonX = targetX;
            dragonY = targetY;
        }
        panel.repaint();
    }
}
