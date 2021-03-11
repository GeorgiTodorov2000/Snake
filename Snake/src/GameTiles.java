import java.awt.*;

public class GameTiles {
    public static final int TILE_SIZE = 50;


    private int row;
    private int col;



    public GameTiles(int row, int col) {
        this.row = row;
        this.col = col;

    }

    public void render(Graphics g) {
        int tileX = this.col * this.TILE_SIZE;
        int tileY = this.row * this.TILE_SIZE;

        g.setColor(Color.WHITE);

        g.fillRect(tileX, tileY, this.TILE_SIZE, this.TILE_SIZE);
    }
}
