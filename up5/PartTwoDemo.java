import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PartTwoDemo {
        public static void main(String[] args) {
            JFrame f = new JFrame("Date");
            f.add(new PartTwoPanel("edrftgyh edrtfgyhuj fghjhgtf fghjgytf fghgytr dfghhgf 23.09.1234 fghjg g vbgfn fhb fg ghf 76.05.1234 fghgfghjhgf 23.05.1234 ghjkghbnj 30.10.2017"));
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(400,400);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }
}