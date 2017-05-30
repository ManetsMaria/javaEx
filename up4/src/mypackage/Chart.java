package mypackage;

import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;

class Slice {
   double value;
   String name;
   Color color;
   public Slice(double value, Color color, String name)throws MyException { 
      if (value<0)
        throw new MyException("under zero data"); 
      this.value = value;
      this.color = color;
      this.name=name;
   }
}

public class Chart extends JPanel {
        Slice[] slices=new Slice[0];

        public Chart(){
            JMenuBar menuBar = new JMenuBar();
            add(menuBar, BorderLayout.NORTH);
            menuBar.setBackground(Color.GRAY);
            JMenu fileMenu = new JMenu("Menu");
            menuBar.add(fileMenu);
            JMenuItem downloadFile = new JMenuItem ("Download File");
            fileMenu.add(downloadFile);
            downloadFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    try {
                        Scanner in = new Scanner(file);
                        int n=in.nextInt();
                        slices=new Slice [n];
                        int i=0;
                        while (in.hasNext()&&i<n){
                            slices[i]=(new Slice(in.nextDouble(),new Color(i*3+30, i*3+15, i*3+20), in.next()));
                            i++;
                        }
                        if (i!=n)
                        {
                            JOptionPane.showMessageDialog(null, "no full data");
                            slices=new Slice[0];
                        }
                    } catch (IOException e) {
                      JOptionPane.showMessageDialog(null, "don't find file");
                      slices=new Slice[0];
                    } catch (InputMismatchException b){
                        JOptionPane.showMessageDialog(null, "check types");
                        slices=new Slice[0];
                    } catch(NoSuchElementException c){
                        JOptionPane.showMessageDialog(null, "no full data");
                        slices=new Slice[0];
                    }
                    catch (MyException t){
                        JOptionPane.showMessageDialog(null, "under zero data");
                        slices=new Slice[0];
                    }
                }
                repaint(); 
                        }
        });
        }
        public void paintComponent(Graphics g) {
            double total = 0.0D;
      
      for (int i = 0; i < slices.length; i++) {
         total += slices[i].value;
      }
      double curValue = 0.0D;
      int startAngle = 0;
      for (int i = 0; i < slices.length; i++) {
         startAngle = (int) (curValue * 360 / total);
         int arcAngle = (int) (slices[i].value * 360 / total);
         g.setColor(slices[i].color);
         //JButton label = new JButton(slices[i].name);
         g.fillArc(0, 0, getSize().width, getSize().height, startAngle, arcAngle);
         //System.out.println(getSize().width/2);         
        g.setColor(Color.red);
        curValue += slices[i].value;
         g.drawString(slices[i].name,getSize().width/2+(int)((getSize().width/8)*Math.cos(curValue*2*Math.PI/total)), getSize().height/2-(int)((getSize().width/3)*Math.sin(curValue*2*Math.PI/total)-10));
      }
        }
}