public class CalculateDistance {

    // Get The Distance between 2 Points in Space
    public int getDistanceBetweenTwoPoints(int x1, int y1, int x2, int y2){
        // Get The Difference Between 2 Points
        int dx = x2 -x1;
        int dy = y2 - y1;
        // Pythagoras Theorem
        return (int) Math.sqrt((dx * dx) + (dy * dy));
    }
}
