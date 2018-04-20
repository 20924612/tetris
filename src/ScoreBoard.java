
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alu20924612v
 */
public class ScoreBoard extends JLabel implements IncrementScorer {

    private static int score;

    public ScoreBoard() {

        super();
        score = 0;
        setText("Score: 0");

    }

    @Override
    public void increment(int points) {

        score += points;
        setText("Score: " + score);
    }

    public void reset() {

        score = 0;
        setText("Score: 0");
    }

    public int getScore() {
        return score;
    }

    public static void pointsFile() {

        File file = new File("/home/INFORMATICA/alu20924612v/Escriptori/pointsfile.txt");
        try {
            FileWriter f = new FileWriter(file);
            PrintWriter input = new PrintWriter(f);
            input.write("Points: " + score);
            //System.out.println(file.exists());
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
