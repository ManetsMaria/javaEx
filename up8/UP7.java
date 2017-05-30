import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

interface Observable{
    public void attach(Observer obj);
    public void notif();
}
interface Observer{
    public void update(String str);
}

class Big extends JLabel implements Observer{
    private String str;
    private UP7 up7;
    public Big(UP7 up7){
        this.up7=up7;
        System.out.println("Big create");
        Font font = new Font("Verdana", Font.BOLD, 30);
        this.setFont(font);
        up7.add(this, BorderLayout.SOUTH);
    }
    public void update(String str){
        this.str=str;
        System.out.println("Big update");
        display();
    }
    private void display(){
        this.setText(str);
        System.out.println("Big display");
    }
}
class Log extends JLabel implements Observer{
    private String str;
    private UP7 up7;
    public Log(UP7 up7){
        this.up7 =up7;
        up7.add(this,BorderLayout.NORTH);
        System.out.println("Log create");
    }
    public void update(String str){
        this.str=str;
        System.out.println("Log update");
        display();
    }
    private void display(){
        System.out.println("Log display");
        StringBuilder stB=new StringBuilder (this.getText());
        stB.append(" ");
        stB.append(str);
        this.setText(stB.toString());
    }
}

public class UP7 extends JPanel implements Observable {
        private List<Observer> observers;
        private String str;
        public UP7(){
            System.out.println("Up7 create");
            setLayout(new BorderLayout());
            observers = new ArrayList<Observer>();
        }
        public void attach(Observer o){
            System.out.println("Up7 attach");
            if (o!=null){
                observers.add(o);
            }
        }
        public void notif(){
            System.out.println("up7 start notify");
            for (Observer observer : observers){
                System.out.println("up7 notify");
                observer.update(str);
            }
        }
        public void somethingChange(String str){
            System.out.println("up7 somethingChange");
            this.str=str;
            notif();
        }
        public static void main(String[] args) {
            JFrame f = new JFrame("Observer");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
            f.setSize(520, 80);
            f.setLocationRelativeTo(null);
            UP7 up7 = new UP7();
            up7.attach(new Big(up7));
            up7.attach(new Log(up7));
            f.add(up7);
            f.addKeyListener(new KeyAdapter() {
 
            public void keyReleased(KeyEvent e) {
                String str=e.getKeyText(e.getKeyCode());
                System.out.println("KeyEvent");
                up7.somethingChange(str);
            }
             
        });
        }
}