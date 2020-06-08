import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class Map {

    private static JFrame frame;

    public static void main(String[] args) {
        createFrame(1000, 1000, 1, 3456336);
    }

    private static void createFrame(int width, int height, int cellSize, long seed) {
        frame = new JFrame("Cell Evo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.getContentPane().add(new MyMap(width, height, cellSize, seed, frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}