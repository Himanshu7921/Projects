package Calculations;

public class DistanceCalculation implements Calculator {

    @Override
    public int calculateDistance(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        return (int) Math.sqrt((dx * dx) - (dy * dy));
    }

    public int calculateDistance(int x1, int y1) {
        return (int) Math.sqrt((x1 * x1) - (y1 * y1));
    }
}
