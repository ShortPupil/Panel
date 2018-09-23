import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
     * view转换成Mat
     * 中间通过bufferimage
	 * @throws IOException 
     */
public class test_2{
    private Mat BufImg2Mat() throws IOException {
    	ArrayList<Shape> s = drawView.getShapes();
    	for(int i=0 ; i<s.size() ; i++) {
    		System.out.println(s.get(i).getBounds2D().getX()+" "+ s.get(i).getBounds2D().getY());
    	}
    	
    	int imgType = BufferedImage.TYPE_3BYTE_BGR;
    	int matType = CvType.CV_8U;
    	Mat mat = null;
		try{
			Rectangle tRectangle = drawView.getBounds();
			//BufferedImage original = (new Robot())
           //     .createScreenCapture(new Rectangle((int) (tRectangle.getX()), (int) (tRectangle.getY()), 1000,1000)); 
			//System.out.println(original.getType());
			File file = new File("./image/test.bmp");
			File file_2 = new File("./image/test_2.bmp");
			BufferedImage bfimage = (BufferedImage) drawView.createImage(drawView.getWidth(),drawView.getHeight());
			
			ImageIO.write(bfimage, "BMP", file);
			
			//System.out.println(original.getType());
			
			//BufferedImage convertedImg = new BufferedImage(original.getWidth(), original.getHeight(), imgType);
			BufferedImage image = new BufferedImage(bfimage.getWidth(), bfimage.getHeight(), imgType);
			ImageIO.write(image, "BMP", file_2);
            // Draw the image onto the new buffer
			Graphics2D g = (Graphics2D) image.getGraphics();
            try {
				g.setColor(Color.WHITE);
                g.setComposite(AlphaComposite.Src);
                g.drawImage(bfimage, 0, 0, null);
                g.fillRect(0, 0, 571 - 26, 428 - 36);
            } finally {
                g.dispose();
            }
            //System.out.println(original.getType());
	        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	        mat = Mat.eye(bfimage.getHeight(), bfimage.getWidth(), matType);
	        
	        mat.put(0, 0, pixels);
	        Imgcodecs.imwrite("./image/test_1.png", mat);
	        
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        return mat;
    }

    /**根据获得的mat判断形状*/
    private String detect(Mat img){
    	
    	 List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
         Imgproc.findContours(img,contours,new Mat(),Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
        // System.out.println(contours.size());
         System.out.println(contours.size());
        // List<Moments> momList = new ArrayList<>(contours.size());
         MatOfPoint2f  mp2f = new MatOfPoint2f( contours.get(0).toArray());
    	
         String shape = "不确定" ;
         double peri;
         peri = Imgproc.arcLength(mp2f,true);
         //对图像轮廓点进行多边形拟合
         MatOfPoint2f polyShape = new MatOfPoint2f();
         Imgproc.approxPolyDP(mp2f,polyShape,0.04*peri,true);
         int shapeLen = polyShape.toArray().length;
         //根据轮廓凸点拟合结果，判断属于那个形状
         switch (shapeLen){
        	case 1:
        		shape = "线";
            case 3:
                shape ="三角形";
            break;
            case 4:
                Rect rect = Imgproc.boundingRect(contours.get(0));
                float width = rect.width;
                float height = rect.height;
                float ar = width/height;
                //计算宽高比，判断是矩形还是正方形
                if (ar>=0.95 && ar <=1.05) {
                    shape="正方形";
                }else {
                    shape="矩形";
                }
                break;
            case 5:
                shape ="五边型";
                break;
            default:
                shape="圆";
            break;
        }
        return shape;
    }
}