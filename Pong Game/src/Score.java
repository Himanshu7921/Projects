import java.awt.*;

public class Score {
    int player1;
    int player2;
    int width;
    int height;
    Score(int x, int y, int width, int height){
        this.height = height;
        this.width = width;
        this.player1 = 0;
        this.player2 = 0;
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 60)); // Set font for the score
        g.drawString(String.valueOf(player1), (width / 2) - 85, 50);
        g.drawString(String.valueOf(player2), (width / 2) + 20, 50);
    }
}