package drawpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

import beautytool.JTextFieldHintListener;
import beautytool.MyTextFieldBorder;
import fileoperation.OpenFile;
import fileoperation.SaveFile;
import graph.IdentifiedShape;
import graph.MyCurve;
import graph.MyObject;
import graph.MyShape;
import identify.ShapeIdentifier;

/**画图功能实现*/
public class DrawFunction {
	private DrawView view;
	private MyCurve object;
	
	private OpenFile openFile  = new OpenFile(this);
	private SaveFile saveFile  = new SaveFile(this);
	/**点shape*/
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	/**自构建的myshape*/
	private ArrayList<MyShape> myShapes = new ArrayList<MyShape>();
	/**myobject 特指mycurve*/
	private ArrayList<MyObject> objects = new ArrayList<MyObject>();
	/**已被识别的shape*/
	private ArrayList<Shape> identifyShape = new ArrayList<Shape>();
	private MyShape myShape = new MyShape(shapes);
	
	/**标注文本框*/
	private JTextField tagText = null;
	/**标注确认按钮*/
	private JButton tagBtn = null;
	/**识别按钮*/
	private JButton identifyBtn = null;
	/**撤回按钮，只允许撤回标注了的内容*/
	private JButton backBtn = null;
	/**识别的形状文本框*/
	private JTextField shapeText = null;
	/**警告信息*/
	private JLabel warnInfo = null;
	
	/**打开前一张图片 按钮*/
	private JButton forward = null;
	/**打开后一张图片 按钮*/
	private JButton backward = null;
	
	private int recordBackford;
	
	private int rocordLine = 0;
	
	private IdentifiedShape resultOfIdentify = null;
	
	private String filePath = "./savefile";
	
	public DrawFunction(DrawView view){
		this.recordBackford = getLastRecordBackford();
		System.out.println("recordBackford"+recordBackford);
		this.view = view;
		
		view.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(object != null)
					objects.add(object);
					object.initial(arg0.getX(), arg0.getY());
					System.out.println(arg0.getX() + " press " + arg0.getY() );
					shapes.add(object.getShape());
					System.out.println(shapes.size());
					identifyShape.add(object.getShape());
					myShape.setShapes(identifyShape);
					view.setShapes(shapes);
					view.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(object != null){
					objects.add(object);
					object.setData(arg0.getX(), arg0.getY());
					System.out.println(arg0.getX() + " release " + arg0.getY() );
					shapes.add(object.getShape());
					identifyShape.add(object.getShape());				
					myShape.setShapes(identifyShape);
					view.setShapes(shapes);
					view.repaint();
					rocordLine++;
				}
			}
		});
		
		view.addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(object != null){
					objects.add(object);
					object.setData(arg0.getX(), arg0.getY());
					System.out.println(arg0.getX() + " dragged " + arg0.getY() );
					//if(object.getClass() == DrawCurve.class)
						//view.shapes.add(object.getShape());
					if(object.isOver())
						shapes.add(object.getShape());
						identifyShape.add(object.getShape());
						myShape.setShapes(identifyShape);
					
					view.setShapes(shapes);
					view.repaint();
				}
			}
		});
	}	
	
	/**北边*/
	public JPanel getNorth(){
		JPanel operation = new JPanel();
		//operation.setBackground(Color.gray);
		operation.setBorder(BorderFactory.createEtchedBorder());
		// 增加保存文件按钮
		identifyBtn = createBtn("识别", "./image/identify.png");
		identifyBtn.setVerticalTextPosition(JButton.BOTTOM);
		identifyBtn.setHorizontalTextPosition(JButton.CENTER); 
		
		// 增加识别文本
		shapeText = new JTextField();
		shapeText.setPreferredSize(new Dimension(140, 27));
		shapeText.setBorder(new MyTextFieldBorder());
		shapeText.addFocusListener(new JTextFieldHintListener("您图片的识别结果", shapeText));
		
		tagText = new JTextField();
		tagText.setPreferredSize(new Dimension(200, 27));
		tagText.setBorder(new MyTextFieldBorder());
		tagText.addFocusListener(new JTextFieldHintListener("此处添加您的标注信息", tagText));
		
		// 增加标注按钮
		tagBtn = createBtn("确认标注", "./image/right.png");
		tagBtn.setVerticalTextPosition(JButton.BOTTOM);
		tagBtn.setHorizontalTextPosition(JButton.CENTER);	
		
		backBtn = createBtn("撤回", "./image/back.png");
		backBtn.setVerticalTextPosition(JButton.BOTTOM);
		backBtn.setHorizontalTextPosition(JButton.CENTER);	
		
		warnInfo = new JLabel();
		Font font = new Font("宋体", Font.PLAIN, 12);//创建1个字体实例
		warnInfo.setFont(font);//设置JLabel的字体
		warnInfo.setForeground(Color.red);//设置文字的颜色
		identifyBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {	
					ShapeIdentifier identfier = new ShapeIdentifier(identifyShape);
					resultOfIdentify = identfier.identify(rocordLine);	
				
					shapeText.setText(resultOfIdentify.toString());
					warnInfo.setText("警告：若不确认，下次识别将包含未确认的图形，确认后无法再次修改关于该图形的信息");
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});		
		
		tagBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {	
					warnInfo.setText("");
					
					myShape.setShapes(identifyShape);
					myShape.setOver(true);
					
					if(!shapeText.equals(null) && !shapeText.equals(""))
						myShape.setIsIdentify(shapeText.getText());
					if(!tagText.equals(null) && !tagText.equals(""))
						myShape.setTag(tagText.getText());
					
					else
						JOptionPane.showMessageDialog(null, "没有画图");
					
					myShapes.add(myShape);
					view.setMyShapes(myShapes);
					
					identifyShape = new ArrayList<Shape>();
					myShape = new MyShape(identifyShape);
					view.repaint();
					
					rocordLine = 0;
					//删除这部分shape
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
		});
		
		backBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(myShapes.size()!=0) {
					shapes.removeAll(myShapes.get(myShapes.size()-1).getShapes());
					myShapes.remove(myShapes.size()-1);	
				
					view.setMyShapes(myShapes);
					view.setShapes(shapes);
					view.repaint();		
				}else {
					JOptionPane.showMessageDialog(null, "不可撤回");}		
			}
		});

		operation.add(identifyBtn);
		operation.add(shapeText);
		operation.add(tagText);
		operation.add(tagBtn);
		operation.add(backBtn);
		operation.add(warnInfo, BorderLayout.SOUTH);
		return operation;
	}
	
	
	/**南边*/
    public JPanel getSouth(){	
		JPanel south = new JPanel();
		south.setBackground(Color.gray);
		south.setBorder(BorderFactory.createEtchedBorder());
		JMenuBar bar = new JMenuBar();
		bar.setLayout(new FlowLayout(40, 4, 0));
		JMenuItem menu_new = new JMenuItem("新建");
		JMenuItem menu_save = new JMenuItem("保存");
		JMenuItem menu_open = new JMenuItem("打开");
		JMenuItem menu_saveAs = new JMenuItem("另存为");
		bar.add(menu_new);
		bar.add(menu_save);
		bar.add(menu_open);
		bar.add(menu_saveAs);
		// 给菜单添加添加监听事件
		menu_new.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {	
					int value = JOptionPane.showConfirmDialog(null, "是否需要保存当前文件？", "提示信息", 0);
					if (value == 0) {
						saveFile.save(shapes,myShapes, recordBackford);
					}
					recordBackford = getLastRecordBackford()+1;
					clearAll();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});			
		menu_open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {	
					openFile.open();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});		
		menu_saveAs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveFile.saveAsPicture();
			}
		});		
		menu_save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveFile.save(shapes,myShapes, recordBackford);
			}
		});		
		
		south.add(bar);
		return south;
	}

    /**西边*/
    public JPanel getWest() {
    	JPanel west = new JPanel();
    	forward = createBtn("", "./image/forward.png");
    	forward.setVerticalTextPosition(JButton.BOTTOM);
    	forward.setHorizontalTextPosition(JButton.CENTER);	
    	west.add(forward, BorderLayout.WEST);
    	
    	forward.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {	
					recordBackford--;
					System.out.println("recordBackford"+recordBackford);
					openFile.openSavefileObject(recordBackford);					
				}catch (Exception e1) {
					// TODO Auto-generated catch block
					recordBackford = 1;
					e1.printStackTrace();
				}
			}
    	});
		return west;
    }
    
    /**东边*/
    public JPanel getEast() {
    	JPanel east = new JPanel();
    	backward = createBtn("", "./image/backward.png");
    	backward.setVerticalTextPosition(JButton.BOTTOM);
    	backward.setHorizontalTextPosition(JButton.CENTER);	
    	east.add(backward, BorderLayout.CENTER);
    	
    	backward.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {	
					recordBackford++;
					openFile.openSavefileObject(recordBackford);
					
				}catch (Exception e1) {
					// TODO Auto-generated catch block
					recordBackford = getLastRecordBackford();
					e1.printStackTrace();
				}
			}
    	});
		return east;
    }
    
    /**获取菜单*/
	public JMenuBar getJMenu() {
		String classname_draw = "graph.MyCurve";
		String itemname_draw = "曲线画图";
		JMenu menu = new JMenu("开 始 画 图");
		JMenuItem item_draw = new JMenuItem(itemname_draw);
		item_draw.setActionCommand(classname_draw);
		item_draw.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					String classname = item_draw.getActionCommand();  
					try {
						object = (MyCurve)Class.forName(classname).newInstance();
						view.setCurve(object);
						object.setView(view);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 		
			}
		});
		menu.add(item_draw);		
		JMenuItem item6 = new JMenuItem("退出");
		menu.addSeparator();
		menu.add(item6);
		item6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});	
		JMenuBar bar = new JMenuBar();
		bar.add(menu);
		return bar;	
	}
	
	/**
	 * 创建工具栏按钮
	 * @param text
	 *            按钮名称
	 * @param icon
	 *            按钮图标所在路径
	 * @return 返回添加样式和监听器后的按钮
	 */
	private JButton createBtn(String text, String icon) {
		JButton btn = new JButton(text, new ImageIcon(icon));
		ImageIcon i = new ImageIcon(icon);
		btn.setIcon(i);
		// rover时图片颜色变成红色
		btn.setRolloverIcon(filterWithRescaleOp(i, 2f, 1f, 1f, 1f));
		// press时图片颜色变成淡红色
		btn.setPressedIcon(filterWithRescaleOp(i, 2f, 1f, 1f, 0.3f));
		//System.out.println( new ImageIcon(icon).getIconWidth() + " " +  new ImageIcon(icon).getIconHeight());
		btn.setUI(new BasicButtonUI());// 恢复基本视觉效果
		btn.setPreferredSize(new Dimension(80, 80));// 设置按钮大小
		btn.setContentAreaFilled(false);// 设置按钮透明
		btn.setFont(new Font("楷体", Font.PLAIN, 15));// 按钮文本样式
		btn.setMargin(new Insets(0, 0, 0, 0));// 按钮内容与边框距离
		//btn.addMouseListener(new MyMouseListener(this));
		return btn;
	}
	
	/**图片处理*/
	private static ImageIcon filterWithRescaleOp(ImageIcon iconBottom
            , float redFilter, float greenFilter, float blueFilter, float alphaFilter){
		try{
            int w = iconBottom.getIconWidth(), h = iconBottom.getIconHeight();

            // 原图
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gg = (Graphics2D)bi.getGraphics();
            gg.drawImage(iconBottom.getImage(), 0, 0,w, h,null);

            // 设置滤镜效果
            float[] scales = { redFilter, greenFilter, blueFilter,alphaFilter };
            float[] offsets = new float[4];
            RescaleOp rop = new RescaleOp(scales, offsets, null);

            // 执行
            rop.filter(bi, bi);
            return new ImageIcon(bi);
		}catch (Exception e){
            e.printStackTrace();
            return new ImageIcon();
		}
	}
	
	public DrawView getDrawView() {
		return this.view;
	}

	public ArrayList<MyShape> getMyShapes() {
		return myShapes;
	}

	public void setMyShapes(ArrayList<MyShape> myShapes) {
		this.myShapes = myShapes;
	}

	public void setShapes(ArrayList<Shape> arrayList) {
		// TODO Auto-generated method stub
		this.shapes = arrayList;
	}

	public ArrayList<Shape> getShapes() {
		// TODO Auto-generated method stub
		return this.shapes;
	}

	public ArrayList<MyObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<MyObject> objects) {
		this.objects = objects;
	}
	public int getRecordBackford() {
		return recordBackford;
	}
	public void setRecordBackford(int i) {
		this.recordBackford = i;
	}
	
	private void clearAll() {
		// TODO Auto-generated method stub
		this.setMyShapes(new ArrayList<MyShape>());
		this.setShapes(new ArrayList<Shape>());
		this.getDrawView().setMyShapes(new ArrayList<MyShape>());
		this.getDrawView().setShapes(new ArrayList<Shape>());
		this.getDrawView().repaint();
	}
	
	private int getLastRecordBackford() {
		File file = new File(filePath);   
		// 获得该文件夹内的所有文件   
		File[] array = file.listFiles();  
		int [] names = new int[array.length];
		for(int i=0 ; i<array.length ; i++) {
			String name = array[i].getName();
			System.out.println("last" + name);
			String lastRecordBackford = name.substring(0, name.length()-4);
			int x = Integer.valueOf(lastRecordBackford);
			names[i] = x;
			System.out.println(x);
		}
		Arrays.sort(names);

			//System.out.println(names[names.length-1]);

		return names[names.length-1];
	}
}
