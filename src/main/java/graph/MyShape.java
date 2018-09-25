package graph;

import java.awt.Color;
import java.awt.Shape;
import java.io.Serializable;
import java.util.ArrayList;

public class MyShape implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3293608133353014708L;
	
	private String identify = null;
	private String tag = null;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Color color;
	private boolean isOver = false;
	
	public MyShape(ArrayList<Shape> shapes) {
		// TODO Auto-generated constructor stub
		this.setShapes(shapes);
	}
	public String getIsIdentify() {
		return identify;
	}
	
	public void setIsIdentify(String identify) {
		this.identify = identify;

		if(identify.equals(IdentifiedShape.Line.toString()))
			color = Color.blue;
		else if(identify.equals(IdentifiedShape.Round.toString()))
			color = Color.pink;
		else if(identify.equals(IdentifiedShape.Angle.toString()))
			color = Color.orange;
		else if(identify.equals(IdentifiedShape.Triangle.toString()))
			color = Color.magenta;
		else if(identify.equals(IdentifiedShape.Rectangle.toString()))
			color = Color.darkGray;
		else if(identify.equals(IdentifiedShape.Square.toString()))
			color = Color.green;
		else if(identify.equals(IdentifiedShape.Pentagon.toString()))
			color = Color.BLACK;
		else if(identify.equals(IdentifiedShape.Unidentify.toString()))
			color = Color.yellow;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}
	public Color getColor() {
		return color;
	}
	public boolean isOver() {
		return isOver;
	}
	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}
}
