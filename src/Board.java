
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alu20924612v
 */
public class Board extends JPanel implements ActionListener {

    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;
    public static final int INIT_ROWS = -2;
    private MyKeyAdapter keyAdapter;
    private Tetrominoes[][] matrix;
    private int deltaTime;
    private Shape currentShape;
    private int currentRow;
    private int currentCol;
    private Timer timer;
    private boolean isPaused;
    private NextPiece nextPiece;
    private SavePiece savePiece;
    private boolean canSavePiece;
    private boolean isGameOver = false;
    private JFrame parentFrame;

    public IncrementScorer scorerDelegate;

    public Board() {

        super();
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];
        keyAdapter = new MyKeyAdapter();
        initValues();
        timer = new Timer(deltaTime, this);
        canSavePiece = true;
        
    }

    public void setScorer(IncrementScorer scorer) {
        this.scorerDelegate = scorer;
    }

    public void initValues() {

        setFocusable(true);
        cleanBoard();
        deltaTime = 500;
        currentShape = null;
        nextPiece = new NextPiece();
        currentRow = -2;
        currentCol = NUM_COLS / 2;
        isPaused = false;

    }

    public void initGame() {
        initValues();
        timer.start();
        removeKeyListener(keyAdapter);
        if (scorerDelegate != null) {

            scorerDelegate.reset();
        }
        addKeyListener(keyAdapter);
        currentShape = Shape.getRandomShape();
        
        
        

    }

    private boolean canMoveTo(Shape shape, int newRow, int newCol) {

        if ((newCol + shape.getXMin() < 0) || (newCol + shape.getXMax() >= NUM_COLS) || (newRow + shape.getYMax() >= NUM_ROWS)
                || (hitWithMatrix(shape, newRow, newCol))) {

            return false;

        }
        return true;
    }

    private void checkColumns() {

        for (int row = 0; row < NUM_ROWS; row++) {
            int acc = NUM_COLS;
            for (int col = 0; col < NUM_COLS; col++) {
                if (matrix[row][col] != Tetrominoes.NoShape) {
                    acc--;
                }

            }
            if (acc == 0) {
                removeLine(row);
                repaint();
                scorerDelegate.increment(1);

                if (scorerDelegate.getScore() % 5 == 0) {
                    deltaTime -= 100;
                }
            }

        }
    }

    private void removeLine(int numRow) {

        for (int row = numRow; row > 0; row--) {

            for (int col = 0; col < NUM_COLS; col++) {
                matrix[row][col] = matrix[row - 1][col];

            }
        }

        for (int col = 0; col < NUM_COLS; col++) {
            matrix[0][col] = Tetrominoes.NoShape;
        }
    }

    private boolean hitWithMatrix(Shape shape, int newRow, int newCol) {

        int[][] squaresArray = shape.getCoordinates();
        for (int point = 0; point <= 3; point++) {
            int row = newRow + squaresArray[point][1];
            int col = newCol + squaresArray[point][0];
            if (row >= 0 && col >= 0) {
                if (matrix[row][col] != Tetrominoes.NoShape) {
                    return true;
                }
            }

        }
        return false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        if (currentShape != null) {
            currentShape.draw(g, currentRow, currentCol, squareWidth(), squareHeight());

        }
        drawBorder(g);

    }

    public void drawBorder(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(0, 0, NUM_COLS * squareWidth(), NUM_ROWS * squareHeight());
    }

    public void drawBoard(Graphics g) {

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                Util.drawSquare(g, row, col, matrix[row][col], squareWidth(), squareHeight());
            }
        }
    }

    private int squareWidth() {

        return getWidth() / NUM_COLS;
    }

    private int squareHeight() {

        return getHeight() / NUM_ROWS;
    }

    //Main game Loop
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (canMoveTo(currentShape, currentRow + 1, currentCol)) {
            currentRow++;
            repaint();
        } else {

            moveCurrentShapeToMatrix();
            currentShape = nextPiece.getShape();
            nextPiece.changeShape(Shape.getRandomShape());
            canSavePiece = true;
            changeCurrentPos();

        }
    }

    private void changeCurrentPos() {
        currentRow = INIT_ROWS;
        currentCol = NUM_COLS / 2;
    }

    public void setNextPiece(NextPiece np) {

        nextPiece = np;
    }
    
    public void setParentFrame(){
        
        this.parentFrame=parentFrame;
    }

    public void setSavePiece(SavePiece sp) {

        savePiece = sp;
    }

    public void moveCurrentShapeToMatrix() {

        int[][] squaresArray = currentShape.getCoordinates();
        for (int point = 0; point <= 3; point++) {
            int row = currentRow + squaresArray[point][1];
            int col = currentCol + squaresArray[point][0];
            if (row < 0) {
                gameOver();

                ScoreBoard.pointsFile();

                return;
            } else {

                matrix[row][col] = currentShape.getShape();
            }

        }
        checkColumns();
    }

    private void gameOver() {

        timer.stop();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int row = 0; row < NUM_ROWS; row++) {
                    for (int col = 0; col < NUM_COLS; col++) {
                        matrix[row][col] = Tetrominoes.LineShape;

                        repaint();

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
                isGameOver = true;
                showRestart();

            }
        });

        t.start();
        RecordsDialog r = new RecordsDialog(parentFrame, true, scorerDelegate.getScore());
        r.setVisible(true);
    }

    private void showRestart() {

        int n = JOptionPane.showConfirmDialog(
                this, "Points:" + scorerDelegate.getScore() + "\n Play again?", "GAME OVER", JOptionPane.YES_NO_OPTION);

        if (n == 0) {

            initGame();
        } else {
            System.exit(0);
        }
    }

    private void cleanBoard() {

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                matrix[row][col] = Tetrominoes.NoShape;
            }
        }

    }


    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (canMoveTo(currentShape, currentRow, currentCol - 1) && !isPaused) {
                        currentCol--;

                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (canMoveTo(currentShape, currentRow, currentCol + 1) && !isPaused) {
                        currentCol++;

                    }
                    break;
                case KeyEvent.VK_UP:
                    Shape rotShape = currentShape.rotateRight();
                    if (canMoveTo(rotShape, currentRow, currentCol) && !isPaused) {
                        currentShape = rotShape;

                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (canMoveTo(currentShape, currentRow + 1, currentCol) && !isPaused) {
                        currentRow++;

                    }
                    break;
                case KeyEvent.VK_P:
                    if (timer.isRunning() && !isPaused) {
                        timer.stop();
                        isPaused = true;

                    } else {

                        timer.start();
                        isPaused = false;
                    }
                case KeyEvent.VK_C:

                    if (canSavePiece) {
                        Shape temp = savePiece.setPieceShape(currentShape);
                        if (temp == null) {

                            currentShape = nextPiece.getShape();

                            nextPiece.changeShape(Shape.getRandomShape());

                            changeCurrentPos();
                        } else {
                            currentShape = temp;
                            changeCurrentPos();

                        }
                        canSavePiece = false;
                    }

                default:
                    break;
            }
            repaint();
        }
    }
}
