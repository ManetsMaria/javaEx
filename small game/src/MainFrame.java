import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements Observer {
	static FieldPanel me;
	static FieldPanel enemy;
	forMenuPanel formenu;
	Traectory forgame;
	Sound sound;
	public MainFrame(String str){
		super(str);
		try{
		    int random_number1 = (int) (Math.random() * 14);
		    enemy=new FieldPanel(new Tank(new Point(random_number1%3,random_number1/3), ImageIO.read(new File("neprozrachnyiy.jpg"))), new Color(250,250,250));
		    //System.out.println(random_number1);
		    me=new FieldPanel(new Tank(new Point(1,3), ImageIO.read(new File("neprSvoy.jpg"))), new Color(233,233,233));
		    }
		    catch(IOException e){
		    	this.add(enemy);
		    }
		forgame=new Traectory(me.tank.getPosition());
		this.setLayout(new GridLayout(2, 1));
		formenu=new forMenuPanel(forgame);
		this.add(formenu);
		formenu.setPreferredSize(new Dimension(588,80));
		me.tank.attach(forgame);
		this.add(forgame);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    forgame.setLayout(new GridLayout(1, 2));
	    this.setResizable(false);
	    formenu.attach(me);
	    formenu.attach(this);
	    forgame.add(enemy);
	    forgame.add(me);
	    this.pack();
	    this.setVisible(true);
	    this.setLocation(330, 100);
	    sound=new Sound(new File("game.wav"));
	    sound.play();
	    //forgame.repaint();
	    //repaint();
	}
	/*@Override
	public void paint(Graphics g){
		super.paint(g);
		g.drawLine(0, 0, 588, 500);
	}*/
	@Override
	public void update(Object obj) {
		int i=(int)obj;
		if (i==5){
			sound.stop();
			String dialog="";
			if (forgame.getEnd().equals(enemy.tank.getPosition()))
				dialog="You are win";
			else
				dialog="You loose";
			JOptionPane.showMessageDialog(new JFrame(), dialog, "Dialog",
    		        JOptionPane.OK_OPTION);
			sound.play();
			int random_number1 = (int) (Math.random() * 14);
			enemy.tank.changeForAnimation(new Point(random_number1%3,random_number1/3));
			me.tank.changePosition(new Point(2,0));
		}
	}
}
interface Observable{
    public void attach(Observer obj);
    public void notif();
}
interface Observer{
    public void update(Object obj);
}

