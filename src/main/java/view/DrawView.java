package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.JPanel;

import graph.MyCurve;
import graph.MyShape;

public class DrawView extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2355369622542017944L;
	
	private MyCurve curve ;
	private ArrayList<MyShape> myShapes = new ArrayList<MyShape>();
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	/*public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
		repaint();
	}*/

	public void setMyShapes(ArrayList<MyShape> myShapes){
		this.myShapes = myShapes;
		repaint();
	}
	
	public void setCurve(MyCurve curve) {
		this.curve = curve;
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(int i=0 ; i<shapes.size() ; i++) {
			g2.draw(shapes.get(i));
		}
		System.out.println("myshape size "+myShapes.size());
		for(int i = 0 ; i < myShapes.size() ; i ++){
			MyShape myShape = myShapes.get(i);	
			
			if(myShape.isOver())
				g2.setColor(myShape.getColor());	
				int size = myShape.getShapes().size();
				System.out.println("shape size "+size);
				for(int j=0 ; j<size ; j++) {
					g2.draw(myShape.getShapes().get(j));
					if(myShape.getIsIdentify()!=null)
						g2.drawString(myShape.getIsIdentify(), 
							(int)myShape.getShapes().get(size-1).getBounds2D().getX(), 
							(int)myShape.getShapes().get(size-1).getBounds2D().getX());
						System.out.println(myShape.getShapes().get(size-1).getBounds2D().getX()+ 
							 " " + myShape.getShapes().get(size-1).getBounds2D().getX());
				}
			}
		if(curve != null)
			curve.draw(g2);
	}

	public ArrayList<MyShape> getMyShapes() {
		return myShapes;
	}


	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
		repaint();
	}
	
}
