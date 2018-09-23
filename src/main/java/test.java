import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;

public class test extends JFrame {

	private JTextField searchText = null;
	private JButton searchBtn = null;
	private JButton editBtn = null;
	private JButton saveBtn = null;

	public test() {
		setLookAndFeel();
		init();
	}

	/**
	 * 为主面板设置皮肤
	 */
	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 增加工具栏的组件，并设置样式
	 */
	private JMenuBar addMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new FlowLayout(40, 4, 0));
		// 增加文件选择按钮
		menuBar.add(createBtn("File", "./src/folder_up.png"));

		// 增加编辑按钮
		editBtn = createBtn("Edit", "./src/page_edit.png");
		//editBtn.setEnabled(false);
		menuBar.add(editBtn);

		// 增加保存文件按钮
		saveBtn = createBtn("Save", "./src/folder_down.png");
		//saveBtn.setEnabled(false);
		menuBar.add(saveBtn);

		// 增加查询输入文本框
		searchText = new JTextField();
		searchText.setPreferredSize(new Dimension(140, 27));
		menuBar.add(searchText);

		// 增加查询按钮
		searchBtn = createBtn("Search", "./src/search.png");
		//searchBtn.setEnabled(false);
		menuBar.add(searchBtn);

		return menuBar;
	}

	/**
	 * 创建工具栏按钮
	 * 
	 * @author lqj
	 * @param text
	 *            按钮名称
	 * @param icon
	 *            按钮图标所在路径
	 * @return 返回添加样式和监听器后的按钮
	 */
	private JButton createBtn(String text, String icon) {
		JButton btn = new JButton(text, new ImageIcon(icon));
		btn.setUI(new BasicButtonUI());// 恢复基本视觉效果
		btn.setPreferredSize(new Dimension(80, 27));// 设置按钮大小
		btn.setContentAreaFilled(false);// 设置按钮透明
		btn.setFont(new Font("粗体", Font.PLAIN, 15));// 按钮文本样式
		btn.setMargin(new Insets(0, 0, 0, 0));// 按钮内容与边框距离
		btn.addMouseListener(new MyMouseListener(this));
		return btn;
	}

	/**
	 * @author lqj
	 * 初始化主面板和工作栏内容
	 */
	private void init() {

		JMenuBar menuBar = addMenuBar();
		// menuBar.setBackground(Color.WHITE);
		menuBar.setPreferredSize(new Dimension(1400, 30));
		this.setJMenuBar(menuBar);
		this.setSize(1320, 750);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("TestButton");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// this.setBackground(Color.gray);
	}

	// main
	public static void main(String[] args) {
		new test();
	}

}