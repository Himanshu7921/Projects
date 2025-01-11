import java.util.Random;

public class CreateMap {
    static final int WIDTH = 70;
    static final int HEIGHT = 70;
    static final int WALL = 1;
    static final int GRASS = 2;
    static final int WATER = 3;
    static final int SAND = 4;

    static int[][] map = new int[HEIGHT][WIDTH];
    static Random random = new Random();

    public static void main(String[] args) {
        initializeMap();
        surroundWithWalls();
        createWaterPools();
        printMap();
    }

    // Initialize map with grass
    public static void initializeMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                map[i][j] = GRASS;
            }
        }
    }

    // Surround the map with walls
    public static void surroundWithWalls() {
        for (int i = 0; i < WIDTH; i++) {
            map[0][i] = WALL;
            map[HEIGHT - 1][i] = WALL;
        }
        for (int i = 0; i < HEIGHT; i++) {
            map[i][0] = WALL;
            map[i][WIDTH - 1] = WALL;
        }
    }

    // Create water pools with sand around them
    public static void createWaterPools() {
        int numPools = random.nextInt(5) + 3;  // Random number of pools
        for (int i = 0; i < numPools; i++) {
            int poolWidth = random.nextInt(5) + 3;
            int poolHeight = random.nextInt(5) + 3;
            int x = random.nextInt(WIDTH - poolWidth - 2) + 1;
            int y = random.nextInt(HEIGHT - poolHeight - 2) + 1;

            // Create water pool
            for (int row = y; row < y + poolHeight; row++) {
                for (int col = x; col < x + poolWidth; col++) {
                    map[row][col] = WATER;
                }
            }

            // Surround water pool with sand
            for (int row = y - 1; row <= y + poolHeight; row++) {
                for (int col = x - 1; col <= x + poolWidth; col++) {
                    if (row >= 0 && row < HEIGHT && col >= 0 && col < WIDTH) {
                        if (map[row][col] != WATER && map[row][col] != WALL) {
                            map[row][col] = SAND;
                        }
                    }
                }
            }
        }
    }

    // Print the map with a space between numbers
    public static void printMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
