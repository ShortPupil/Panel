package fileoperation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import graph.MyShape;
import panel.DrawFunction;

public class SaveFile {
	
	private DrawFunction listener;
	
	public SaveFile(DrawFunction paint) {
		this.listener = paint;
	}
	
	/**将正在编辑的面板保存为jpg文件*/
	public void saveAsPicture() {
		// 选择要保存的位置以及文件名字和信息
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("文本文件(*.jpeg,*.png,*.bmp)",
				"jpg");
		chooser.setFileFilter(fileNameExtensionFilter);
		chooser.showSaveDialog(null);
		File file = chooser.getSelectedFile();

		if (file == null) {
			JOptionPane.showMessageDialog(null, "没有选择文件");
		} else {

			try {
				String fname = chooser.getName(file);
				file = new File(chooser.getCurrentDirectory(), fname + ".jpg");
				Dimension imageSize = listener.getDrawView().getSize();     
				 
				BufferedImage image = new BufferedImage(imageSize.width,imageSize.height, BufferedImage.TYPE_INT_BGR);  
				Graphics2D graphics = image.createGraphics();    
				listener.getDrawView().paint(graphics);     
				graphics.dispose();     
       
				ImageIO.write(image, "jpg", file);
				JOptionPane.showMessageDialog(null, "保存成功！");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**保存正在编辑的图片*/
	public void save(ArrayList<Shape> s,ArrayList<MyShape> ss, int recordBackford) {
		// TODO Auto-generated method stub
		
		String filename = "./savefile/" + (recordBackford + 1)
				+ ".txt";
		ObjectOutputStream oos = null;
        try {
        	File file = new File(filename);
        	if (!file.exists()) { //文件不存在则创建文件，先创建目录 
        		File dir = new File(file.getParent()); 
        		dir.mkdirs(); 
        		file.createNewFile(); 
        	}
            oos = new ObjectOutputStream(new FileOutputStream(file));
            for(int i= 0 ; i<s.size() ; i++) {
            	oos.writeObject(s.get(i));
            }   
            for(int i= 0 ; i<ss.size() ; i++) {
            	oos.writeObject(ss.get(i));
            }          
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
