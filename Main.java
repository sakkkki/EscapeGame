/**
 * EscapeGameのメインプログラム
 * 
 * @param args
 * 
 * @author M.Umeoka, S.Iwamoto
 * O.Honbou(Reviewer)
 */

import javax.swing.JFrame;

public class Main {   
    public static void main(String[] args) {
        JFrame frame = new JFrame("Escape Game");
        EscapeGame game = new EscapeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }  
}
