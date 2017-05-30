import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import java.awt.event.ActionListener;

import javafx.beans.InvalidationListener;
public class forMenuPanel extends JPanel implements Observable{
	JTextArea force;
	JTextArea angle;
	JTextArea size;
	JLabel forSize;
	JLabel forForce;
	JLabel forAngle;
	JButton buttonSet1;
	JButton buttonSet2;
	JButton buttonSet3;
	JButton fire;
	String[] itemsForce = {
		    "H",
		    "мН",
		    "кН"
		};
	JComboBox comboBoxForce;
	String[] itemsSize = {
		    "м",
		    "мм",
		    "км"
		};
	JComboBox comboBoxSize;
	String[] itemsAngle = {
		    "гр.",
		    "р"
		};
	JComboBox comboBoxAngle;
	JButton upB;
	JButton downB;
	JButton left;
	JButton right;
	ArrayList <Observer> observer;
	int flag=0;
	Traectory traectory;
	public forMenuPanel(Traectory panel){
		observer=new ArrayList();
		traectory=panel;
		this.setLayout(new GridLayout(2, 1));
		JPanel sizePanel=new JPanel();
		JPanel forcePanel=new JPanel();
		JPanel anglePanel=new JPanel();
		JPanel up=new JPanel();
		JPanel down=new JPanel();
		this.add(up);
		this.add(down);
		up.setLayout(new GridLayout(1,3));
		up.add(forcePanel);
		up.add(sizePanel);
		up.add(anglePanel);
		force=new JTextArea("0",13,13);
		force.setSize(100, 100);
		forForce=new JLabel("Input force and choose FW");
		comboBoxForce = new JComboBox(itemsForce);
		comboBoxForce.setEditable(false);
		buttonSet1=new JButton("set and look");
		forcePanel.setLayout(new BorderLayout());
		forcePanel.add(forForce, BorderLayout.NORTH);
		forcePanel.add(force, BorderLayout.WEST);
		forcePanel.add(buttonSet1, BorderLayout.SOUTH);
		forcePanel.add(comboBoxForce, BorderLayout.EAST);
		buttonSet1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	connectWithTraectory();	
            }
       });
		
		size=new JTextArea("1",13,13);
		size.setSize(100, 100);
		forSize=new JLabel(" Input size and choose FW");
		comboBoxSize = new JComboBox(itemsSize);
		comboBoxSize.setEditable(false);
		buttonSet2=new JButton("set and look");
		sizePanel.setLayout(new BorderLayout());
		sizePanel.add(forSize, BorderLayout.NORTH);
		sizePanel.add(size, BorderLayout.WEST);
		sizePanel.add(buttonSet2, BorderLayout.SOUTH);
		sizePanel.add(comboBoxSize, BorderLayout.EAST);
		buttonSet2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	connectWithTraectory();	
            }
       });
		
		angle=new JTextArea("0",13,13);
		angle.setSize(100, 100);
		forAngle=new JLabel(" Input ang and choose FW");
		comboBoxAngle = new JComboBox(itemsAngle);
		comboBoxAngle.setEditable(false);
		buttonSet3=new JButton("set and look");
		anglePanel.setLayout(new BorderLayout());
		anglePanel.add(forAngle, BorderLayout.NORTH);
		anglePanel.add(angle, BorderLayout.WEST);
		anglePanel.add(buttonSet3, BorderLayout.SOUTH);
		anglePanel.add(comboBoxAngle, BorderLayout.EAST);
		buttonSet3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	connectWithTraectory();	
            }
       });
		
		fire=new JButton("Attack!");
		fire.setBackground(new Color(250, 0, 0));
		down.setLayout(new GridLayout(1,2));
		down.add(fire);
		JPanel drive=new JPanel();
		upB=new JButton("up");
		downB=new JButton("down");
		left=new JButton("left");
		right=new JButton("right");
		drive.setLayout(new BorderLayout());
		drive.add(upB, BorderLayout.NORTH);
		drive.add(downB, BorderLayout.CENTER);
		drive.add(left, BorderLayout.WEST);
		drive.add(right, BorderLayout.EAST);
		down.add(drive);
		upB.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                  flag=2;
                  notif();
             }
        });
		downB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 flag=4;
                 notif();
            }
       });
		left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 flag=1;
                 notif();
            }
       });
		right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 flag=3;
                 notif();
            }
       });
		fire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 Sound.playSound("thumb.wav");
                 traectory.changeFlag(true);
                 flag=5;
                 notif();
                 force.setText("0");
                 size.setText("1");
                 angle.setText("0");
                 traectory.makeEnd(0, 1, 0);
            }
       });
	}
	@Override
	public void attach(Observer obj) {
		observer.add(obj);
		
	}
	@Override
	public void notif() {
		for (int i=0; i<observer.size(); i++){
			observer.get(i).update(flag);
		}
		System.out.println("notify");
	}
	private void connectWithTraectory(){
		double f=0;
		double s=1;
		double a=0;
     try{
    	 f=Double.valueOf(force.getText());
    	 s=Double.valueOf(size.getText());
    	 System.out.println(s);
    	 a=Double.valueOf(angle.getText());
     }
     catch(NumberFormatException n){
    	 JOptionPane.showMessageDialog(new JFrame(), "input only number format", "Dialog",
    		        JOptionPane.ERROR_MESSAGE);
    	 if (f==0){
    	 force.setText("0");
    	 }
    	 if (s==1){
    		 size.setText("1");
    	 }
    	 if (a==0)
    		 angle.setText("0");
     }
     if (s<=0){
		 System.out.println("here");
		 size.setText("1");
		 s=1;
		 JOptionPane.showMessageDialog(new JFrame(), "can not be size<=0", "Dialog",
 		        JOptionPane.ERROR_MESSAGE);
	 }
     String strSize=(String) comboBoxSize.getSelectedItem();
     String strForce=(String) comboBoxForce.getSelectedItem();
     String strAngle=(String) comboBoxAngle.getSelectedItem();
     if (strSize.equals("км")){
    	 s=s*1000;
     }
     if (strSize.equals("мм")){
    	 s=s*0.001;
     }
     if (strForce.equals("мН")){
    	 f=f*0.001;
     }
     if (strForce.equals("кН")){
    	 f=f*1000;
     }
     if (strAngle.equals("гр.")){
    	 a=(Math.PI/180)*a;
     }
     traectory.makeEnd(f,a,s);
	}
}
