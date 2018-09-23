package fileoperation;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import graph.MyShape;
import panel.DrawFunction;

/*文件操作分离出来，方便管理*/

public class OpenFile {
	private DrawFunction listener;
	private SaveFile saveFile;
	
	private String filePath = "./savefile/";

	public OpenFile(DrawFunction paint) {
		this.listener = paint;
	}
	
	/**打开某张保存的jpg文件*/
	public void open() {
		// TODO Auto-generated method stub
		int value = JOptionPane.showConfirmDialog(null, "是否需要保存当前文件？", "提示信息", 0);
		if (value == 0) {
			saveFile.save(this.listener.getShapes(), this.listener.getMyShapes());
		}
		try {
			// 弹出选择对话框，选择需要读入的文件
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			File file = chooser.getSelectedFile();
			// 如果为选中文件
			if (file == null) {
				JOptionPane.showMessageDialog(null, "没有选择文件");
			} else {
				// 选中了相应的文件，则柑橘选中的文件创建对象输入流
				FileInputStream fis = new FileInputStream(file);
				BufferedImage bufferedImage = ImageIO.read(file);
				Graphics2D graphics2d = (Graphics2D) listener.getDrawView().getGraphics();
				graphics2d.drawImage(bufferedImage, 0, 0, listener.getDrawView());
				fis.close();
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**打开反序列化文件中的对象*/
	public void openSavefileObject(int recordBackford) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		ArrayList<Object> allshapes = new ArrayList<Object>();
		ArrayList<MyShape> myShapes = new ArrayList<MyShape>();
		ArrayList<Shape> shapes = new ArrayList<Shape>();
	    try {
	    	System.out.println(recordBackford);
	    	fis = new FileInputStream("./savefile/" + recordBackford
	    			+ ".txt");
	    	ois = new ObjectInputStream(fis);
	        while (true) {
	        	allshapes.add(ois.readObject());
	        }
	    } catch (FileNotFoundException e) {
	    	JOptionPane.showMessageDialog(null, "没有更多了！");
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace(); //此处解决序列化完成后的异常
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("输出结束");
	    }
		for(int i=0 ; i<allshapes.size() ; i++) {
			if(allshapes.get(i).getClass().getName().equals("graph.MyShape")) {
				myShapes.add((MyShape)allshapes.get(i));
			}
			else {
				shapes.add((Shape)allshapes.get(i));
			}
			/*view.setShapes(shapes);
			view.setMyShapes(myShapes);
			view.repaint();*/
			listener.getDrawView().setShapes(shapes);
			listener.getDrawView().setMyShapes(myShapes);
			listener.getDrawView().repaint();
		}
	}
}
