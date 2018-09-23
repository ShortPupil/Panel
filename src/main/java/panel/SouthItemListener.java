package panel;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import fileoperation.OpenFile;
import fileoperation.SaveFile;
import graph.MyShape;

/**
 * 
 * */
public class SouthItemListener implements ActionListener {

	private String saveAs = "另存为";
	private String open = "打开";
	private String _new = "新建";
	private String save = "保存";
	
	
	private DrawFunction listener;
	private OpenFile openFile = new OpenFile(listener);
	private SaveFile saveFile = new SaveFile(listener);
	
	
	public SouthItemListener(DrawFunction paint) {
		this.listener = paint;
	}

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		if (_new.equals(command)) {
			int value = JOptionPane.showConfirmDialog(null, "是否需要保存当前文件？", "提示信息", 0);
			if (value == 0) {
				saveFile.save(this.listener.getShapes(), this.listener.getMyShapes());
			}
			clearAll();
		}

		else if (open.equals(command)) {
			openFile.open();
			clearAll();
		} else if (saveAs.equals(command)) {
			saveFile.saveAsPicture();
		} else if (save.equals(command)) {
			saveFile.save(this.listener.getShapes(), this.listener.getMyShapes());
		}

	}

	private void clearAll() {
		// TODO Auto-generated method stub
		listener.setMyShapes(new ArrayList<MyShape>());
		listener.setShapes(new ArrayList<Shape>());
		listener.getDrawView().setMyShapes(new ArrayList<MyShape>());
		listener.getDrawView().setShapes(new ArrayList<Shape>());
		listener.getDrawView().repaint();
	}

	/**打开某张保存的jpg文件*/
	/*private void open() {
		// TODO Auto-generated method stub
		int value = JOptionPane.showConfirmDialog(null, "是否需要保存当前文件？", "提示信息", 0);
		if (value == 0) {
			save(this.listener.getShapes(), this.listener.getMyShapes());
		}
		// 清空容器里面的东西
		listener.setShapes(new ArrayList<Shape>());
		listener.setMyShapes(new ArrayList<MyShape>());
		listener.getDrawView().repaint();
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

	/**保存正在编辑的图片*/
	/*private void save(ArrayList<Shape> s,ArrayList<MyShape> ss) {
		// TODO Auto-generated method stub
		String filename = "./savefile/" + recordBackford
				+ ".txt";
		recordBackford++;
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
	}*/
	
	/**将正在编辑的面板保存为jpg文件*/
	/*private void saveAsPicture() {
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
*/
}