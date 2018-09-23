package graph;

import java.awt.geom.Line2D;

/**曲线，继承MyObject*/
public class MyCurve extends MyObject {

	public MyCurve(){
		super();
		shape = new Line2D.Double();
		over = true;
	}

	@Override
	public void setData(double x, double y) {
		// TODO Auto-generated method stub
		this.shape = new Line2D.Double(startx, starty, x , y);
		startx = x;
		starty = y;
		view.repaint();
	}
}
