package pkg17.ScoreManagement.ui.addForm;


import pkg17.ScoreManagement.dao.ClazzInfoDao;
import pkg17.ScoreManagement.model.ClazzInfo;
import pkg17.ScoreManagement.ui.MainForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


/**
 * 添加班级信息
 *
 * @author Administrator
 *
 */
public class AddClazzInfoForm extends JFrame {
	private JTextField clazzNoField;
	private JButton saveButton, cleanButton;

	/**
	 * 设置窗体的父窗体，当前窗体显示在父窗体中间 作为模态窗口时使用
	 */
	public AddClazzInfoForm(MainForm parentForm) {
		initComponents();
		setLocationRelativeTo(parentForm); // 设置相对于父窗体居中显示
		setAlwaysOnTop(true); // 设置窗体始终置顶
		setVisible(true);
	}


	// 布局组件
	private void initComponents() {
		setTitle("班级信息录入");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使用此方法会退出应用程序

		// 关闭子窗体时不关闭父窗体，应按如下方式设置，重写关闭事件
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				// 关闭子窗体
				dispose();
			}
		});

		// 窗体布局
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(null);

		// 班级号
		JLabel clazzNOLabel = new JLabel("班级号:");
		clazzNOLabel.setBounds(10, 20, 80, 25);
		panel.add(clazzNOLabel);
		clazzNoField = new JTextField(25);
		clazzNoField.setBounds(100, 20, 185, 25);
		panel.add(clazzNoField);


		saveButton = new JButton("保存");
		saveButton.setBounds(100, 170, 80, 25);
		panel.add(saveButton);

		cleanButton = new JButton("清除");
		cleanButton.setBounds(210, 170, 80, 25);
		panel.add(cleanButton);

		// 保存
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == saveButton) {
					String clazzNo = clazzNoField.getText();


					//判断数据是否存在，如存在，则提示，且不执行增加操作，
					ClazzInfo sInfo = ClazzInfoDao.getClazzInfo(clazzNo);
					if(sInfo != null)
						return;

					// 如不存在，则保存到数据库
					String status = "1";
					ClazzInfo saveClazzInfo = new ClazzInfo(clazzNo, status);
					try {
						ClazzInfoDao.saveClazzInfo(saveClazzInfo);
						JOptionPane.showMessageDialog(panel, "保存成功", "消息", JOptionPane.INFORMATION_MESSAGE);

						dispose();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(panel, "保存失败", "消息", JOptionPane.INFORMATION_MESSAGE);
					}


				} else if (e.getSource() == cleanButton) {// 清除
					clazzNoField.setText("");
				} // end if
			}// end actionPerformed
		});// end addActionListener

	}
}