import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Traectory extends JPanel implements Observer{
	private Point start;
	private Point end;
	private boolean flag;
	public Traectory(Point s){
		start=new Point();
		start.x=s.x;
		start.y=s.y;
		end=new Point();
		end.x=start.x+3;
		end.y=start.y;
		flag=false;
		repaint();
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(new Color(0,0,0));
		int x1=(start.x+3)*98;
		int y1=start.y*38+17;
		int x2=end.x*98+49;
		int y2=end.y*38+17;
		Graphics2D g2 = ((Graphics2D)g);
		g2.setStroke(
		        new BasicStroke(
		                3.0f,
		                BasicStroke.CAP_ROUND,
		                BasicStroke.JOIN_ROUND,
		                0,
		                new float[]{2,6},
		                0
		        )
		        );
		g2.drawLine(x1, y1, x2, y2);
		if (flag)
			try{
			 g.drawImage(ImageIO.read(new File("Thumb.jpg")), end.x*98, end.y*38, this);
			 //String[]str=new String [1];
			 //Demo.main(str);
			 flag=false;
			}
		catch(IOException e){}
	}
//
	@Override
	public void update(Object obj) {
		Point point=(Point) obj;
		System.out.println(end);
		end.x+=point.x-start.x;
		end.y+=point.y-start.y;
		System.out.println(point.x-start.x);
		System.out.println(point.y-start.y);
		start.x=point.x;
		start.y=point.y;
		repaint();
	}
	public void makeEnd(double force, double angle, double size){
		int m=10;
		int g=10;
		//angle=angle+Math.PI;
		if (size==0)
			size=1;
		double x=(start.x+3)*size-(Math.cos(angle)*force*force)/(m*m*g)-(Math.cos(angle)*force*force*g)/(2*m*m*g*g);
		double y=start.y*size+(Math.sin(angle)*force*force)/(m*m*g)+(Math.sin(angle)*force*force*g)/(2*m*m*g*g);
		x=x/size;
		y=y/size;
		end.x=(int)x;
		end.y=(int)y;
		repaint();
	}
	public void changeFlag(boolean flag){
		this.flag=flag;
		repaint();
	}
	public Point getEnd(){
		return end;
	}
}
