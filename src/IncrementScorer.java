/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu20924612v
 */
public interface IncrementScorer {
    
    public void increment(int points);
        public void reset();
        public int getScore();
    
}
