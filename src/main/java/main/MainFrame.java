package main;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.UIManager;

import identify.ShapeIdentifier;
import panel.DrawFunction;
import panel.DrawView;

public class MainFrame extends JFrame{
	/**
	 * 
	 */

	private static final long serialVersionUID = -2167696510722505313L;
	private DrawView view;
	private DrawFunction ctrl;
	
	private void setLookAndFeel() {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public MainFrame(){
		setLookAndFeel();
		
		view = new DrawView();
		ctrl = new DrawFunction(view);
		this.setJMenuBar(ctrl.getJMenu());

		this.getContentPane().add(ctrl.getWest(), BorderLayout.WEST);
		this.getContentPane().add(ctrl.getNorth(), BorderLayout.NORTH);
		this.getContentPane().add(ctrl.getSouth(), BorderLayout.SOUTH);
		this.getContentPane().add(ctrl.getEast(), BorderLayout.EAST);
		this.getContentPane().add(view);
		
		this.setSize(1200, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		new MainFrame();
	}
		
}
