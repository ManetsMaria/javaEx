package mypackage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class DemoChart {
        private static final int radius=300;
        public static void main(String[] args) {
            JFrame f = new JFrame("Chart");
            f.add(new Chart());
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(radius*2, radius*2);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }
}