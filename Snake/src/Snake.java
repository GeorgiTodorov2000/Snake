import java.awt.*;

public class Snake {
    private static final int TILE_SIZE = 50;
    private int row;
    private int col;
    private int id;
    private Color color;

    public Snake(int row, int col, int id, Color color) {
        this.row = row;
        this.col = col;
        this.id = id;
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getId() {
        return id;
    }

    public void move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void render(Graphics g) {
        int tileX = this.col * this.TILE_SIZE;
        int tileY = this.row * this.TILE_SIZE;

        g.setColor(this.color);
        g.fillRect(tileX, tileY, this.TILE_SIZE, this.TILE_SIZE);
    }
}

