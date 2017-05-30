package mypackage;

import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JList;
import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JList;
import javax.swing.JFileChooser;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListCellRenderer;
import java.awt.image.BufferedImage;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class RunImage extends JPanel {
        private int radius;
        private int second;
        private final double angle= 2*Math.PI/60;
        private Point point;
        private BufferedImage image;
        private int c;
        private JSlider slider;
        private Timer timer;

        public RunImage(int radius){
            second=0;
            c=1;
            ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                repaint();
                second+=c*1;  
            }
             };
            slider = new JSlider(JSlider.VERTICAL, 0, 1000, 0);
            slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                //timer.stop();
                //timer= new Timer(1000- slider.getValue(), taskPerformer);
                //timer.start();
                timer.setDelay(1000- slider.getValue());
                }
            });

            String[] data = {"right", "left"};
            final JList list = new JList(data);
            this.setLayout(new BorderLayout());
            JMenuBar menuBar = new JMenuBar();
            add(menuBar, BorderLayout.NORTH);
            add(list, BorderLayout.SOUTH);
            add(slider, BorderLayout.WEST);
            menuBar.setBackground(Color.GRAY);
            JMenu fileMenu = new JMenu("Menu");
            menuBar.add(fileMenu);
            JMenuItem downloadImage = new JMenuItem ("Download Image");
            fileMenu.add(downloadImage);
            downloadImage.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        JFileChooser fileopen = new JFileChooser();
                        int ret = fileopen.showDialog(null, "Open file");
                        if (ret == JFileChooser.APPROVE_OPTION) {
                            File file= fileopen.getSelectedFile();
                            image=ImageIO.read(file);
                            second=0;
                        }
                    }
                    catch(IOException e)
                    {
                        JOptionPane.showMessageDialog(null, e);
                    }
                    catch(NullPointerException w)
                    {
                        JOptionPane.showMessageDialog(null, w);
                    }
                }
            });
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            list.addListSelectionListener(
                    new ListSelectionListener() {
                         public void valueChanged(ListSelectionEvent e) {
                              Object element = list.getSelectedValue();
                              if (element=="right")
                                c=1;
                                else
                                    c=-1;
                         }
                    });
            this.radius=radius;
            point=new Point();
        timer =new Timer(1000, taskPerformer);
        timer.start();
        }
        public void setRadius(int radius){
            this.radius=radius;
            repaint();
        }
        public void paintComponent(Graphics g) {
            g.setColor(new Color(250, 250, 250));
            double currentAngle = second * angle;
            radius=Math.min(this.getSize().height, this.getSize().width)-120;
            int fill = Math.max(this.getSize().height, this.getSize().width);
            g.fillRect(0, 0, fill, fill);
            radius=radius/2;
            point.x = radius
                    + Math.round((float) (radius * Math.sin(currentAngle)));
            point.y = radius
                    - Math.round((float) (radius * Math.cos(currentAngle)));
            g.drawImage(image, point.x, point.y, null);
        }
}