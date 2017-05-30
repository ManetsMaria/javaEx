import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PartOneDemo {
        public static void main(String[] args) {
            JFrame f = new JFrame("Data");
            f.add(new PartOnePanel());
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(520, 80);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }
}