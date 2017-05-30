package mypackage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;

public class DemoRunImage {
        private static int radius=100;
        public static void main(String[] args) {
            JFrame f = new JFrame("RunImage");
            RunImage panel = new RunImage(radius);
            f.add(panel);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(radius*2+100, radius*2+100);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            f.addWindowListener(new WindowAdapter() {
                public void  windowStateChanged(WindowEvent e) {
                    panel.setRadius(Math.min(f.getSize().height, f.getSize().width));
                    System.out.println("g");
            }
            });

       }
}