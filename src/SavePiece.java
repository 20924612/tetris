
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
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
public class SavePiece extends JPanel {

    private Shape saveShape;

    private Tetrominoes[][] saveMatrix;

    public SavePiece() {

        super();
        saveShape = null;
        saveMatrix = new Tetrominoes[NextPiece.MATRIX_SIZE][NextPiece.MATRIX_SIZE];

        for (int row = 0; row < NextPiece.MATRIX_SIZE; row++) {
            for (int col = 0; col < NextPiece.MATRIX_SIZE; col++) {

                saveMatrix[row][col] = Tetrominoes.NoShape;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        if (saveShape != null) {
            drawSavePiece(g);
        }

    }

    public void drawBoard(Graphics g) {

        for (int row = 0; row < NextPiece.MATRIX_SIZE; row++) {
            for (int col = 0; col < NextPiece.MATRIX_SIZE; col++) {

                drawSquare(g, row, col, saveMatrix[row][col]);
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

        return getWidth() / NextPiece.MATRIX_SIZE;
    }

    private int squareHeight() {

        return getHeight() / NextPiece.MATRIX_SIZE;
    }

    private void drawSavePiece(Graphics g) {

        int[][] coordinates = saveShape.getCoordinates();
        for (int i = 0; i <= 3; i++) {

            int row = coordinates[i][1];
            int col = coordinates[i][0];

            drawSquare(g, row + NextPiece.MATRIX_SIZE / 2 - 1, col + NextPiece.MATRIX_SIZE / 2 - 1, saveShape.getShape());
        }
    }

    public Shape setPieceShape(Shape saveShape) {

        if (this.saveShape == null) {

            this.saveShape = saveShape;
            repaint();

            return null;
        }else{
            Shape temp = this.saveShape;
            
            this.saveShape = saveShape;
            repaint();
            return temp;
            
        }

        
    }
}
