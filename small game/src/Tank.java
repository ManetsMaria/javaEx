import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Tank implements Observable{
	private Point position;
	private Point forAnimation;
	private BufferedImage image;
	Observer observer;
	public Tank(Point pos, BufferedImage image){
		position=pos;
		this.image=image;
		forAnimation=new Point (0,0);
	}
	public void changePosition(Point newPos){
		position=newPos;
		notif();
	}
	public Point getPosition(){
		return position;
	}
	public void changeForAnimation(Point newPos){
		position=newPos;
	}
	public BufferedImage getBuffer(){
		return image;
	}
	@Override
	public void attach(Observer obj) {
		observer=obj;
		
	}
	@Override
	public void notif() {
		observer.update(position);
		
	}
}
