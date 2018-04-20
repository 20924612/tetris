
import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu20924612v
 */
public class Shape {

    private Tetrominoes pieceShape;
    private int[][] coordinates;

    private static int[][][] coordsTable = new int[][][]{
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
        {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
        {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
        {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
        {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
        {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
        {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
        {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
    };

    public Shape(Tetrominoes pieceShape) {
        this.pieceShape = pieceShape;
        coordinates = new int[4][2];
        for (int point = 0; point < coordinates.length; point++) {
            coordinates[point][0] = coordsTable[pieceShape.ordinal()][point][0];
            coordinates[point][1] = coordsTable[pieceShape.ordinal()][point][1];
        }

    }

    public Shape() {

        int randomNumber = (int) (Math.random() * 7) + 1;
        pieceShape = Tetrominoes.values()[randomNumber];
        coordinates = new int[4][2];
        for (int point = 0; point < coordinates.length; point++) {
            coordinates[point][0] = coordsTable[randomNumber][point][0];
            coordinates[point][1] = coordsTable[randomNumber][point][1];
        }
    }

    public Shape rotateRight() {

        Shape rotateShape = new Shape(pieceShape);
        

        for (int point = 0; point <= 3; point++) {
            rotateShape.coordinates[point][0] = coordinates[point][0];
            rotateShape.coordinates[point][1] = coordinates[point][1];
        }

        if (pieceShape != Tetrominoes.SquareShape) {

            for (int point = 0; point <= 3; point++) {
                int temp = rotateShape.coordinates[point][0];
                rotateShape.coordinates[point][0] = -rotateShape.coordinates[point][1];
                rotateShape.coordinates[point][1] = temp;
            }
        }
        return rotateShape;
    }

    public static Shape getRandomShape() {

        return new Shape();
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }

    public int getXMin() {
        int min = coordinates[0][0];
        for (int i = 1; i < coordinates.length; i++) {

            if (min > coordinates[i][0]) {
                min = coordinates[i][0];
            }

        }
        return min;
    }

    public int getXMax() {

        int max = coordinates[0][0];
        for (int i = 1; i < coordinates.length; i++) {

            if (max < coordinates[i][0]) {
                max = coordinates[i][0];
            }

        }
        return max;
    }

    public int getYMin() {

        int min = coordinates[0][1];
        for (int i = 1; i < coordinates.length; i++) {
            if (min > coordinates[i][1]) {
                min = coordinates[i][1];
            }
        }

        return min;
    }

    public int getYMax() {

        int max = coordinates[0][1];
        for (int i = 1; i < coordinates.length; i++) {

            if (max < coordinates[i][1]) {
                max = coordinates[i][1];
            }

        }
        return max;
    }
    
    public void draw(Graphics g, int row, int col, int squareWidth, int squareHeight) {


        for (int point = 0; point <= 3; point++) {
            Util.drawSquare(g, row + coordinates[point][1], col + coordinates[point][0], pieceShape, squareWidth, squareHeight);
        }
    }
}
