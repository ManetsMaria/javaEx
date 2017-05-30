import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JComboBox;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartOnePanel extends JPanel {
       JComboBox editComboBox;
       JTextField tf;
       Color color=new Color(50,50,50);
       String[] items = {
        "натуральное число",
        "целое число",
        "число с плавающей запятой",
        "дата",
        "время", 
        "email"
        };
        JButton button;

        public PartOnePanel(){
            tf = new JTextField("Input your datum");
            tf.setSize(40,40);
            button=new JButton("check");
            editComboBox = new JComboBox(items);
            editComboBox.setEditable(true);
            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        String item = (String)editComboBox.getSelectedItem();
                        String x = tf.getText();
                        if (x==null){
                            color=new Color(50,50,50);
                            return;
                        }
                        color=new Color(250,0,0);
                        if (item.equals("натуральное число")){
                            String regexp = 
                            "^([1-9]([0-9]+))|([1-9])$";
                            if (Pattern.matches(regexp, x))
                                color=new Color(0,250,0);
                        }
                        if (item.equals("целое число")){
                            String regexp = "^[-+]?\\d+$";
                            if (Pattern.matches(regexp, x))
                                color=new Color(0,250,0);
                        }
                        if (item.equals("число с плавающей запятой")){
                            String regexp = "^(([-+](\\d+)?)?((\\.\\d{1,})|(\\d{1,}\\.))(e[+-]?\\d{1,})?)|(\\d{1,}e[+-]?\\d{1,})$";
                            if (Pattern.matches(regexp, x))
                                color=new Color(0,250,0);
                        }
                        if (item.equals("дата")){
                            DateValidator dv=new DateValidator();
                            if (dv.validate(x))
                                color=new Color(0,250,0);
                        }
                        if (item.equals("время")){
                            String regexp = "^([0-2][0-3]|[0-1][0-9]):[0-5][0-9]$";
                            if (Pattern.matches(regexp, x))
                                color=new Color(0,250,0);
                        }
                        if (item.equals("email")){
                            String regexp = "^[_A-Za-z0-9]+\\.?[_A-Za-z0-9]+@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})$";
                            if (Pattern.matches(regexp, x))
                                color=new Color(0,250,0);
                        }
                        repaint();
                }
            };
            button.addActionListener(actionListener);
            this.add(editComboBox);
            this.add(tf);
            this.add(button);
        }
        public void paintComponent(Graphics g) {
            g.setColor(color);
            g.fillOval(0, 0, 30, 30);
        }
}
// 2.0 0.2 .2 2. 2e3 2.0e3 0.2e-3