package mypackage;

import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Color;

public class Clock extends JPanel {
        private int radius;
        private int second;
        private final double angle= 2*Math.PI/60;
        private Point point;

        public Clock(int radius){
            this.radius=radius;
            point=new Point();
            ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                repaint();
                second++;  
            }
        };
            second=0;
            new Timer(1000, taskPerformer).start();
        }
        public void paintComponent(Graphics g) {
            g.setColor(new Color(250, 250, 250));
            g.fillOval(0, 0, radius * 2, radius * 2);
            g.setColor(new Color(0, 0, 0));
            g.drawOval(0, 0, radius * 2, radius * 2);
            double currentAngle = 3*Math.PI/2 - second * angle;

            point.x = radius
                    + Math.round((float) (radius * Math.sin(currentAngle)));
            point.y = radius
                    - Math.round((float) (radius * Math.cos(currentAngle)));
            g.setColor(new Color(0, 0, 0));
            g.drawLine(radius, radius, point.y, point.x);
        }
}