package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager{
    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        mapTileNum = new int[gp.maxWorldWidth][gp.maxWorldHeight];
        int TOTAL_TILES = 6;
        tile = new Tile[TOTAL_TILES];
        getTileImages();
        // Loading maps
        loadMap("/Maps/world01.txt");
    }

    public void loadMap(String mapAddress){
        // try-with-resources
        try(InputStream file = getClass().getResourceAsStream(mapAddress);
        BufferedReader lineReader = new BufferedReader(new InputStreamReader(file))){
            int col = 0;
            int row = 0;
            while(col < gp.worldCol && row < gp.worldRow){
                String line = lineReader.readLine();
                while(col < gp.worldCol){
                    String [] number = line.split(" ");
                    // Check if the length of number[] matches the expected column size
                    int num = Integer.parseInt(number[col]);
                    mapTileNum[row][col] = num;
                    col++;
                }
                if(col == gp.worldCol){
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getTileImages(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Images/Tile/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Images/Tile/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Images/Tile/water.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Images/Tile/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Images/Tile/tree.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/Images/Tile/sand.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int worldRow = 0;
        int worldCol = 0;

        while (worldRow < gp.worldRow && worldCol < gp.worldCol) {

            int worldX = worldRow * gp.TILE_SIZE;
            int worldY = worldCol * gp.TILE_SIZE;
            int screenXx = worldX - gp.player.worldX + gp.player.screenX;
            int screenYy = worldY - gp.player.worldY + gp.player.screenY;

            int tileNum = mapTileNum[worldRow][worldCol];
            g2.drawImage(tile[tileNum].image, screenXx, screenYy, gp.TILE_SIZE, gp.TILE_SIZE, null);
            worldCol++;
            if (worldCol == gp.worldCol) {
                // Reset column and move to the next row
                worldCol = 0;
                worldRow++;
            }
        }
    }

}