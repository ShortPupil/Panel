package identify;


import java.awt.Shape;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;

import graph.IdentifiedShape;

/**
 * 图形识别
 * 要求绘图者沿着同一时针画图，可连续，可不连续
 */
public class ShapeIdentifier {
	
	private ArrayList<Shape> shapes;
	
	private final static int judgeLineByPoint = 0;
	private final static int judgeAngleByPoint = 1;
	private final static int judgeTriangleByPoint = 2;
	private final static int judgeRectangleByPoint = 3;
	private final static int judgePentagonByPoint = 4;
	
	private final static int judgeLineByRecordLine = 1;
	private final static int judgeAngleByRecordLine = 2;
	private final static int judgeTriangleByRecordLine = 3;
	private final static int judgeRectangleByRecordLine = 4;
	private final static int judgePentagonByRecordLine = 5;
	
	public ShapeIdentifier(ArrayList<Shape> shapes2) {
		this.shapes = shapes2;
		System.out.println(shapes2.size());
	}
	
	/**结合边数和曲率判断形状*/
	public IdentifiedShape identify(int rocordLine) throws IOException {	
		//TreeMap<Double, ArrayList<Double>> collection = conform(shapes, rocordLine);
		//int protrudingPoints = findProtrudingPoints().size();
		if(rocordLine == judgeLineByRecordLine 
				&& findProtrudingPoints() == judgeLineByPoint) {
			return IdentifiedShape.Line;
		}
		if(rocordLine == judgeLineByRecordLine) {
			return IdentifiedShape.Round;
		}		
		if(rocordLine == judgeAngleByRecordLine 
				|| findProtrudingPoints() == judgeAngleByPoint) {
			return IdentifiedShape.Angle;
		}
		if(rocordLine == judgeTriangleByRecordLine 
				|| findProtrudingPoints() == judgeTriangleByPoint) {
			return IdentifiedShape.Triangle;
		}
		if(rocordLine == judgeRectangleByRecordLine 
				|| findProtrudingPoints() == judgeRectangleByPoint) {
			return isRectangleOrSquare();
		}
		if(rocordLine == judgePentagonByRecordLine 
				|| findProtrudingPoints() == judgePentagonByPoint) {
			return IdentifiedShape.Pentagon;
		}
		//findProtrudingPoint();
	    return IdentifiedShape.Unidentify;
	}
	
	
	/**判断矩形与正方形的方法
	 * 正方形边长比在0.9～1.1*/
	private IdentifiedShape isRectangleOrSquare() {
		double []  xs = new double[shapes.size()];
		double []  ys = new double[shapes.size()];
		for(int i=1 ; i<shapes.size() ; i++) {
			if(shapes.get(i).getBounds2D().getCenterX() != 0 
					|| shapes.get(i).getBounds2D().getCenterY() != 0)
				xs[i] = shapes.get(i).getBounds2D().getCenterX();
				ys[i] = shapes.get(i).getBounds2D().getCenterY();
				//System.out.println(xs[i] + " " + ys[i]);
		}
		Arrays.sort(xs); Arrays.sort(ys);
		for(int i=0 ; i<xs.length ; i++) {
			System.out.println(xs[i] + " " + ys[i]);
		}
		double x_maxDValue = xs[xs.length-1]-xs[1];
		double y_maxDValue = ys[ys.length-1]-ys[1];
		
		System.out.println(x_maxDValue +  " " + y_maxDValue);
		if((x_maxDValue >= 0.9*y_maxDValue) && (x_maxDValue <= 1.1*y_maxDValue)) {
			return IdentifiedShape.Square;
		}
		return IdentifiedShape.Rectangle;
	}
	
	/**通过曲率寻找曲率峰值,以确定曲点数量*/
	private int findProtrudingPoints() {
		TreeMap<Integer, Double> curvature = new TreeMap<Integer, Double>();
		
		for(int i=0 ; i<shapes.size()-7 ; i+=5) {
			
			double k_1 = 0.0;
			double k_2 = 0.0;
			
			double x_1 = shapes.get(i).getBounds2D().getX();
			double x_2 = shapes.get(i+1).getBounds2D().getX();
			double x_3 = shapes.get(i+2).getBounds2D().getX();
			double x_4 = shapes.get(i+3).getBounds2D().getX();
			
			double y_1 = shapes.get(i).getBounds2D().getY();
			double y_2 = shapes.get(i+1).getBounds2D().getY();
			double y_3 = shapes.get(i+2).getBounds2D().getY();
			double y_4 = shapes.get(i+3).getBounds2D().getY();
			
			double distance = Math.sqrt(Math.pow(x_1-x_3, 2)+Math.pow(y_1-y_3, 2));
			
			//计算斜率为0的情况，防止除0错误
			if(x_1 == x_2 && x_3 == x_4) 
				curvature.put(i, 0.0);
			else if(x_1 == x_2) {
				k_2 = (y_3-y_4)/(x_3-x_4);
				curvature.put(i,Math.abs((90-Math.atan(k_2)*(180/Math.PI))/distance));}
			else if(x_3 == x_4) {
				k_1 = (y_1-y_2)/(x_1-x_2);
				curvature.put(i,Math.abs((90-Math.atan(k_1)*(180/Math.PI))/distance));}
			else {
				k_1 = (y_1-y_2)/(x_1-x_2);
				k_2 = (y_3-y_4)/(x_3-x_4);
				curvature.put(i,Math.abs(Math.atan((k_2-k_1)/(1+k_1*k_2))*(180/Math.PI)/distance));
			
			}
		}
		
		
		Set<Integer> keys = curvature.keySet();
		double arr[] = new double[keys.size()+1]; 
		int count = 0;
		for(Integer key : keys) {
			System.out.println(curvature.get(key));
			arr[count] = curvature.get(key);
			count++;
		}
		System.out.println(count);
		
		ArrayList<Integer> peak = new ArrayList<Integer>();
	     for (int index = 1; index <= arr.length - 2; ) {
	         //判断是否峰点,而且峰点周围差距要大于该点的0.2
	         if ((arr[index] - arr[index - 1]>0.2*arr[index- 1] )
	        		 && (arr[index] - arr[index + 1] > 0.2*arr[index+1]) ) {
	             peak.add(index);
	             index += 3; //此为 曲点之间存在3～5个非曲点
	         } else {
	             index += 1;
	         }
	     }
	     //输出
	     String output = "";
	     int x = 0;
	     for (int index : peak) {
	         output += index + " " + arr[index]+ ",";
	         x++;
	     }
	     System.out.println("峰值是 "+output);
	     System.out.println("个数是 " + x);
	     return x;
	}
 
}
