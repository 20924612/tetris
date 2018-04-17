
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
            drawNextPiece(g);
        }

    }

    public void drawBoard(Graphics g) {

        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int col = 0; col < MATRIX_SIZE; col++) {

                drawSquare(g, row, col, nextMatrix[row][col]);
            }
        }
    }

    private void drawSquare(Graphics g, int row, int col, Tetrominoes shape) {
        Color colors[] = {new Color(0, 0, 0),
            new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = colors[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2,
                squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1,
                y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    private int squareWidth() {

        return getWidth() / MATRIX_SIZE;
    }

    private int squareHeight() {

        return getHeight() / MATRIX_SIZE;
    }

    public void drawNextPiece(Graphics g) {
        int[][] squaresArray = nextShape.getCoordinates();
        for (int point = 0; point <= 3; point++) {
            drawSquare(g, squaresArray[point][1] + MATRIX_SIZE /2, squaresArray[point][0] + MATRIX_SIZE /2, nextShape.getShape());
        }
    }

    public void changeShape(Shape shape) {
        nextShape = shape;
        repaint();
    }

    public Shape getShape() {

        return nextShape;
    }

}
