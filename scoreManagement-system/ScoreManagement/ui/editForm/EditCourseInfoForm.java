package pkg17.ScoreManagement.ui.editForm;

import pkg17.ScoreManagement.dao.CourseInfoDao;
import pkg17.ScoreManagement.model.CourseInfo;
import pkg17.ScoreManagement.ui.mListForm.CourseListForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 编辑班级信息
 *
 * @author Administrator
 *
 */
public class EditCourseInfoForm extends JFrame {
	private JTextField courseNoField, courseNameField;
	private JComboBox<String> semsterComboBox;
	private JButton saveButton, cleanButton;
	private String courseNo; // 课程号
	private CourseListForm parentForm; // 父窗体

	/**
	 * 设置窗体的父窗体，当前窗体显示在父窗体中间 作为模态窗口时使用
	 *
	 * @param parentForm 父窗体
	 */
	public EditCourseInfoForm(CourseListForm parentForm, String courseNo) {
		this.parentForm = parentForm;
		this.courseNo = courseNo;
		initComponents();
		setLocationRelativeTo(parentForm); // 设置相对于父窗体居中显示
		setAlwaysOnTop(true); // 设置窗体始终置顶
		setVisible(true);
	}

	// 布局组件
	private void initComponents() {
		setTitle("课程信息编辑");
		setSize(500, 400);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使用此方法会退出应用程序

		// 关闭子窗体时不关闭父窗体，应按如下方式设置，重写关闭事件
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				// 刷新父窗体的数据
				parentForm.displayTable(CourseInfoDao.getCourseList(""));
				// 关闭子窗体
				dispose();
			}
		});

		// 查询初始化数据
		CourseInfo cInfo = CourseInfoDao.getCourseInfo(courseNo);

		// 窗体布局
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(null);

		// 课程号
		JLabel courseNoLabel = new JLabel("课程号:");
		courseNoLabel.setBounds(10, 20, 80, 25);
		panel.add(courseNoLabel);
		courseNoField = new JTextField(25);
		courseNoField.setBounds(100, 20, 185, 25);
		courseNoField.setText(cInfo.getCourseNo());
		panel.add(courseNoField);

		JLabel courseNameLabel = new JLabel("名称:");
		courseNameLabel.setBounds(10, 50, 80, 25);
		panel.add(courseNameLabel);
		courseNameField = new JTextField(20);
		courseNameField.setBounds(100, 50, 185, 25);
		courseNameField.setText(cInfo.getCourseName());
		panel.add(courseNameField);

		JLabel semsterLabel = new JLabel("学期:");
		semsterLabel.setBounds(10, 80, 80, 25);
		panel.add(semsterLabel);

		// 学期下拉框
		String[] semsterOptions = {"2024-2025-1", "2024-2025-2", "2025-2026-1", "2025-2026-2", "2027-2028-1", "2027-2028-2"};
		semsterComboBox = new JComboBox<>(semsterOptions);
		semsterComboBox.setBounds(100, 80, 185, 25);
		semsterComboBox.setSelectedItem(cInfo.getSemster()); // 设置选中项为当前课程的学期
		panel.add(semsterComboBox);

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
					String courseNo = courseNoField.getText();
					String courseName = courseNameField.getText();
					String semster = (String) semsterComboBox.getSelectedItem();

					// 保存到数据库
					char status = '1';
					CourseInfo saveCourseInfo = new CourseInfo(courseNo, courseName, status, semster);
					try {
						CourseInfoDao.udpateCourseInfo(saveCourseInfo);
						JOptionPane.showMessageDialog(panel, "保存成功", "消息", JOptionPane.INFORMATION_MESSAGE);

						// 刷新父窗体的数据
						parentForm.displayTable(CourseInfoDao.getCourseList(""));
						// 关闭子窗体
						dispose();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(panel, "保存失败", "消息", JOptionPane.ERROR_MESSAGE);
					}
				} else if (e.getSource() == cleanButton) { // 清除
					courseNoField.setText("");
					courseNameField.setText("");
					semsterComboBox.setSelectedIndex(0); // 重置下拉框到第一个选项
				} // end if
			} // end actionPerformed
		}); // end addActionListener
	}
}
