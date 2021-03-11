import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.invoke.SwitchPoint;
import java.util.Random;

public class GameBoard extends JFrame implements MouseListener {
    public static final int TILE_X_COUNT = 9;
    public static final int TILE_Y_COUNT = 9;
    private Object[][] pieceCollection;
    private Object selectedPiece;
    private Object clickedTile;
    private Object lostTile;
    private Random RANDOM = new Random();
    public int SCORE = 0;
    public int STARTING_ROW = 0;
    public int STARTING_COL = 0;
    public int CURRENT_ROW = 0;
    public int CURRENT_COL = 0;
    private int N = CURRENT_COL--;
    private int E = CURRENT_ROW++;
    private int S = CURRENT_COL++;
    private int W = CURRENT_ROW--;
    private int POINTS = 0;

    public GameBoard() {
        this.pieceCollection = new Object[TILE_X_COUNT][TILE_Y_COUNT];
        RANDOM = new Random();
        pieceCreator();

        JButton moveN=new JButton("UP");
        JButton moveE=new JButton("RIGHT");
        JButton moveS=new JButton("DOWN");
        JButton moveW=new JButton("LEFT");

//        moveN.addActionListener(e ->
//        {
//
//        });
//        moveE.addActionListener(e -> {
//
//        });
//        moveS.addActionListener(e -> {
//
//        });
//        moveW.addActionListener(e -> {
//
//        });

        moveN.setBounds(520,50,95,30);
        moveE.setBounds(520,100,95,30);
        moveS.setBounds(520,150,95,30);
        moveW.setBounds(520,200,95,30);

        this.add(moveN);this.add(moveE);this.add(moveS);this.add(moveW);
        this.setSize(750, 450);
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMouseListener(this);
    }









    // Painting the game board
    public void paint(Graphics g) {
        super.paint(g);

        for(int row = 1; row < 8; ++row) {
            for(int col = 1; col < 8; ++col) {
                GameTiles tile = new GameTiles(row, col);
                tile.render(g);

                if(this.hasBoardPiece(row, col)) {
                    Snake snake = (Snake) this.getBoardPiece(row, col);
                    snake.render(g);

                }

            }
        }
    }




    private void pieceCreator () {
        STARTING_ROW = RANDOM.nextInt(8);
        if (STARTING_ROW == 0) STARTING_ROW++;
        STARTING_COL = RANDOM.nextInt(8);
        if (STARTING_COL == 0) STARTING_COL++;
        CURRENT_ROW = STARTING_ROW;
        CURRENT_COL = STARTING_COL;

        this.pieceCollection[STARTING_ROW][STARTING_COL] = new Snake(STARTING_ROW, STARTING_COL, 1, Color.green);

        if (STARTING_ROW != 1 && STARTING_COL != 1) this.pieceCollection[1][1] = new Snake(1, 1, 2, Color.yellow);
        if (STARTING_ROW != 3 && STARTING_COL != 5) this.pieceCollection[3][5] = new Snake(3, 5, 2, Color.yellow);
        if (STARTING_ROW != 5 && STARTING_COL != 7) this.pieceCollection[5][7] = new Snake(5, 7, 2, Color.yellow);
        if (STARTING_ROW != 7 && STARTING_COL != 4) this.pieceCollection[7][4] = new Snake(6, 4, 2, Color.yellow);
        if (STARTING_ROW != 2 && STARTING_COL != 6) this.pieceCollection[2][6] = new Snake(2, 6, 2, Color.yellow);

        for(int row = 1; row < 8; ++row) {
            for(int col = 1; col < 8; ++col) {

                int id = RANDOM.nextInt(6);
                if (!hasBoardPiece(row,col)) {
                    switch (id) {
                        //Obstacle creator id = 0
                        case 0: {
                            this.pieceCollection[row][col] = new Snake(row, col, id, Color.red);
                            break;
                        }
                        //Food random creator id = 3
                        case 3: {
                            this.pieceCollection[row][col] = new Snake(row, col, id, Color.blue);
                            break;
                        }
                    }
                }
            }
        }

    }



    private boolean isTileEmpty(int row, int col) {
        if(this.pieceCollection[row][col] != null) {

            return true;
        }
        return false;
    }

    private void clickedSquare(int row, int col) {
        this.pieceCollection[row][col] = new Snake(row, col, 1, Color.green);
        this.pieceCollection[CURRENT_ROW][CURRENT_COL] = new Snake(CURRENT_ROW, CURRENT_COL, 1, Color.green);
        CURRENT_ROW = row;
        CURRENT_COL = col;
    }

    private boolean isMoveValid(int row,int col) {
        if(CURRENT_COL + 1 == col && CURRENT_ROW == row ||
            CURRENT_COL - 1 == col && CURRENT_ROW == row ||
                CURRENT_COL == col && CURRENT_ROW + 1 == row ||
                CURRENT_COL == col && CURRENT_ROW - 1 == row
        ) {return true;}
        return false;
    }

    private boolean lost(int row, int col) {

        return true;

    }

    private void win() {
        if (POINTS >= 300) {
            System.out.println("You won");
            System.exit(1);
        }
    }


    public void mouseClicked(MouseEvent e) {
        int row = this.getBoardDimensionBasedOnCoordinates(e.getY());
        int col = this.getBoardDimensionBasedOnCoordinates(e.getX());
        System.out.println(row + "" + col);
        if (this.selectedPiece != null) {


            Snake snake = (Snake) this.selectedPiece;
            if(isMoveValid(row,col)) {
                if (this.hasBoardPiece(row, col)) {
                    this.clickedTile = this.getBoardPiece(row, col);
                }
                Snake nextTile = (Snake) this.clickedTile;
                if (this.clickedTile != null) {
                    switch (nextTile.getId()) {
                        case 0 -> {
                            System.out.println("That was an obstacle, you died");
                            System.exit(1);
                        }
                        case 1 -> {
                            System.out.println("You ate yourself");
                            System.exit(1);

                        }
                        case 2 -> {
                            System.out.println("10 pts");
                            POINTS += 10;
                            win();
                            snake.move(row, col);
                            clickedSquare(row, col);

                        }
                        case 3 -> {
                            System.out.println("15 pts");
                            POINTS += 15;
                            win();
                            snake.move(row, col);
                            clickedSquare(row, col);

                        }
                    }
                } else {
                    snake.move(row, col);
                    clickedSquare(row, col);
                }
            }
//            else {
//                snake = (Snake) this.selectedPiece
                this.clickedTile = null;
                this.selectedPiece = null;
//            }
        }

        if (this.hasBoardPiece(row, col)) {
            this.selectedPiece = this.getBoardPiece(row, col);
        }
        this.repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private Object getBoardPiece(int row, int col) {
        return this.pieceCollection[row][col];
    }
    private boolean hasBoardPiece(int row, int col) {
        return this.getBoardPiece(row, col) != null;
    }
    private int getBoardDimensionBasedOnCoordinates(int coordinates) {
        return coordinates / 50;
    }
}
