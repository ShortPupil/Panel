package identify;


import java.awt.Shape;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import graph.MyCurve;

/**
 * 图形识别类
 */
public class ShapeIdentifier {
	private final static int pointToJudgeRound = 20;
	
	private final static int pointOfLine = 15;
	private final static double Difference = 5.0;
	private final static double minOfRightAngle = 80.0;
	private final static double maxOfRightAngle = 100.0;
	private final static double minOfParallel = 10.0;
	private final static double maxOfParallel = 170.0;
	
	private ArrayList<Shape> shapes;
	
	private int numOfLine = 0;
	private int judgeTriangle = 1;
	private int judgeRectangle = 2;
	
	public ShapeIdentifier(ArrayList<Shape> shapes2) {
		this.shapes = shapes2;
		System.out.println(shapes2.size());
	}
	
	public IdentifiedShape identify(int rocordLine) throws IOException {	
		//TreeMap<Double, ArrayList<Double>> collection = conform(shapes, rocordLine);
		if(rocordLine == 3) {
			return IdentifiedShape.Triangle;
		}
		if(rocordLine == 4) {
			return IdentifiedShape.Rectangle;
		}
		if(rocordLine == 1) {
			return IdentifiedShape.Round;
		}
		//findProtrudingPoint();
		
	    return IdentifiedShape.Unidentify;
	}
	
	
	/**对shapes数组处理，以便对坐标的扫描
	 * return TreeMap 处理结果，同横坐标的放在一起*/
	private TreeMap<Double, ArrayList<Double>> conform(ArrayList<Shape> s) {
		TreeMap<Double, ArrayList<Double>> collection = new TreeMap<Double, ArrayList<Double>>();
		for(int i=0 ; i<s.size() ; i++) {
			double point_x = s.get(i).getBounds2D().getX();
			double point_y = s.get(i).getBounds2D().getY();
			if(collection.keySet().contains(point_x)) {
				ArrayList<Double> ys = collection.get(point_x);
				ys.add(point_y);
				collection.put(point_x, ys);
			}else {
				ArrayList<Double> newYs = new ArrayList<Double>();
				newYs.add(point_y);
				collection.put(point_x, newYs);
			}
		}
		return collection;
	}
	
	/**判断是否为圆*/
	private ArrayList<Shape> findProtrudingPoint() {
		TreeMap<Integer, Double> curvature = new TreeMap<Integer, Double>();
		
		Mean mean = new Mean(); // 算术平均值  
        ArrayList<Double> s = new ArrayList<Double>();
        double res[] = new double[pointToJudgeRound];
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
			
			double d_i = Math.atan((y_3-y_1)/(x_3-x_1));
			
			double distance = Math.sqrt(Math.pow(x_1-x_3, 2)+Math.pow(y_1-y_3, 2));
			
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
	         //判断是否峰点
	         if (arr[index] > arr[index - 1] && arr[index] > arr[index + 1]&&arr[index]>40) {
	             peak.add(index);
	             index += 2;
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
	     return shapes;
	}
 
}
