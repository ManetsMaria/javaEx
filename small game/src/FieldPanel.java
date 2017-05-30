import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FieldPanel extends JPanel implements Observer{
	Tank tank;
	Color color;
	public FieldPanel(Tank myTank, Color color){
		tank=myTank;
		this.color=color;
		this.setPreferredSize(new Dimension(294,190));
		this.setBackground(color);
		repaint();
	}
	@Override
	public void paintComponent(Graphics g){
		//super.paintComponent(g);
		g.setColor(color);
		g.fillRect(0, 0, 300, 200);
		g.setColor(new Color(0,0,0));
		Point pos=tank.getPosition();
		int x=pos.x*98;
		int y=pos.y*38;
        g.drawImage(tank.getBuffer(), x, y, this);
        g.drawRect(0, 0, 294, 190);
        for (int i=1; i<3; i++){
        	g.drawLine(i*98, 0, i*98, 190);
        }
        for (int i=1; i<5; i++){
        	g.drawLine(0, i*38, 294, i*38);
        }
	}
	public void Left(){
		Point point=tank.getPosition();
		System.out.println("Left()");
		if (point.x>0){
			point.x-=1;
			tank.changePosition(point);
			repaint();
		}
	}
	public void Up(){
		Point point=tank.getPosition();
		if (point.y>0){
			point.y-=1;
			tank.changePosition(point);
			repaint();
		}
	}
	public void Down(){
		Point point=tank.getPosition();
		if (point.y<4){
			point.y+=1;
			tank.changePosition(point);
			repaint();
		}
	}
	public void Right(){
		Point point=tank.getPosition();
		if (point.x<2){
			point.x+=1;
			tank.changePosition(point);
			repaint();
		}
	}
	@Override
	public void update(Object y) {
		System.out.println("update");
		System.out.println(tank.getPosition());
		int i=(int) y;
		switch(i){
		case 1:{
			Left();
			break;
		}
		case 2:{
			Up();
			break;
		}
		case 3:{
			Right();
			break;
		}
		case 4:{
			Down();
			break;
		}
		default: {break;}
		}
		
	}
}

