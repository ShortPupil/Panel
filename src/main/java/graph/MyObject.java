package graph;

import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JPanel;

/**对象父类，用于增加画图形状类型*/
public abstract class MyObject {
	protected double startx;
	protected double starty;
	
	protected double endx;
	protected double endy;
	
	protected Shape shape;
	
	protected JPanel view;
	
	protected boolean over = false;
	
	public boolean isOver() {
		return over;
	}
	public void initial(double x,double y){
		this.endx = this.startx = x;
		this.endy = this.starty = y;
	}
	public void setView(JPanel view){
		this.view = view;
	}
	
	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	//public abstract void setData(double startx, double starty, double endx, double endy);
	public abstract void setData(double x, double y);       //����ƶ������е���
	
	public void draw(Graphics2D g){
		if(shape != null)
			g.draw(shape);
	}
	
}
