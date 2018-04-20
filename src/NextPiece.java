
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alu20924612v
 */
public class NextPiece extends JPanel {

    public static final int MATRIX_SIZE = 4;

    Shape nextShape;
    private Tetrominoes[][] nextMatrix;

    public NextPiece() {

        super();
        nextShape = new Shape();
        nextMatrix = new Tetrominoes[MATRIX_SIZE][MATRIX_SIZE];
        initGame();
      
    }

    public void initGame() {

        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int col = 0; col < MATRIX_SIZE; col++) {
                nextMatrix[row][col] = Tetrominoes.NoShape;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        if (nextShape != null) {
            nextShape.draw(g, 1, 1, squareWidth(), squareHeight());
        }

    }

    public void drawBoard(Graphics g) {

        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int col = 0; col < MATRIX_SIZE; col++) {

                Util.drawSquare(g, row, col, nextMatrix[row][col], squareWidth(), squareHeight());
            }
        }
    }

    

    public void changeShape(Shape shape) {
        nextShape = shape;
        repaint();
    }

    public Shape getShape() {

        return nextShape;
    }
    
    private int squareWidth() {

        return getWidth() / 4;
    }

    private int squareHeight() {

        return getHeight() / 4;
    }

}
