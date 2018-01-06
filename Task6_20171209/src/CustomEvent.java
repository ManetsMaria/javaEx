import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.EventListenerList;

public class CustomEvent
{
    public static void main(String[] args)
    {
        CustomEventFrame frame = new CustomEventFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}

/**
 A frame with a panel that displays falling raindrops
 */
class CustomEventFrame extends JFrame
{
    public CustomEventFrame()
    {
        setTitle("CustomEvent");
        setSize(WIDTH, HEIGHT);

        // add frame to panel

        CustomEventPanel panel = new CustomEventPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);
    }

    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;
}

class CustomEventPanel extends JPanel
{
    public CustomEventPanel()
    {
        circles = new ArrayList<Point>();
        add(button);
        CustomEventPanel customEventPanel = this;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                synchronized (circles) {
                    int index = circles.size();
                    circles.add(new Point());
                    Generator generator = new Generator(index, customEventPanel);
                    generator.addListener(new SquareEventListener());
                    new Thread(generator).start();
                }
            }
        });
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        synchronized (circles) {
            for (int i = 0; i < circles.size(); i++) {
                g2.setColor(new Color((i*20)%250, (i*20)%250, (i*20)%250));
                if (!circles.get(i).equals(new Point()))
                g2.fillRect(circles.get(i).x, circles.get(i).y, circles.get(i).x + 20, circles.get(i).y + 20);
            }
        }
    }

    public static volatile ArrayList <Point> circles;
    private JButton button  = new JButton("Start new generator");
}
class Generator implements Runnable{
    int index;
    CustomEventPanel customEventPanel;
    final int a = 500;
    final int b = 3500;
    EventListenerList listenerList;

    public Generator(int index, CustomEventPanel customEventPanel) {
        this.index = index;
        this.customEventPanel = customEventPanel;
        listenerList = new EventListenerList();

    }

    public void addListener(SquareEventListener listener){
        listenerList.add(SquareEventListener.class,listener);
    }

    @Override
    public void run() {
        System.out.println(index);
        while (true){
            try {
                Thread.sleep((long)(Math.random()*(b - a) + a));
                System.out.println(index+" generate");
                SquareEvent squareEvent = new SquareEvent(customEventPanel, index, new Point((int)(Math.random()*(CustomEventFrame.WIDTH - 20)), (int)(Math.random()*(CustomEventFrame.HEIGHT - 20))));
                EventListener[] listeners = listenerList.getListeners(
                        SquareEventListener.class);
                for (int i = 0; i < listeners.length; i++)
                    ((SquareEventListener)listeners[i]).squareGenerate(
                            (SquareEvent) squareEvent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class SquareEvent extends AWTEvent{
    public SquareEvent(Object o, int i, Point point) {
        super(o, i);
        this.point = point;
        this.i = i;
        customEventPanel = (CustomEventPanel) o;
    }
    public Point point;
    public int i;
    public CustomEventPanel customEventPanel;
}
interface MyListener extends EventListener{
    public void squareGenerate(SquareEvent event);
}
class SquareEventListener implements MyListener{


    @Override
    public void squareGenerate(SquareEvent event) {
        event.customEventPanel.circles.set(event.i, event.point);
        event.customEventPanel.repaint();
    }
}


