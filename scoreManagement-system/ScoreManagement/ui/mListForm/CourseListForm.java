package pkg17.ScoreManagement.ui.mListForm;


import pkg17.ScoreManagement.dao.CourseInfoDao;
import pkg17.ScoreManagement.model.CourseInfo;
import pkg17.ScoreManagement.ui.editForm.EditCourseInfoForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class CourseListForm extends JFrame {
	private JLabel labelCourseName;
	private JTextField searchCourseNameField;
	private JButton searchButton;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton editButton;
	private JButton deleteButton;

	public CourseListForm() {
		initComponent();
	}

	private void initComponent() {
		setTitle("课程信息列表");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLayout(new BorderLayout());

		JPanel searchPanel = new JPanel();
		// searchPanel.setLayout(new BoxLayout(searchPanel,
		// BoxLayout.X_AXIS));//布局方式1：按X方向布局
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 布局方式2：从左边开始
		labelCourseName = new JLabel("课程名称");
		searchCourseNameField = new JTextField(20);
		searchButton = new JButton("查询");
		editButton = new JButton("修改");
		deleteButton = new JButton("删除");
		searchPanel.add(labelCourseName);
		searchPanel.add(searchCourseNameField);
		searchPanel.add(searchButton);
		searchPanel.add(editButton);
		searchPanel.add(deleteButton);

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tableModel = new DefaultTableModel();
		tableModel.addColumn("课程号");
		tableModel.addColumn("名称");
		tableModel.addColumn("状态");
		tableModel.addColumn("学期");
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new Label());// 占位

		add(searchPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String courseName = searchCourseNameField.getText();
				// 根据搜索条件进行学生信息查询，更新表格数据
				List<CourseInfo> couList = CourseInfoDao.getCourseList(courseName);
				displayTable(couList);
			}
		});

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// 获取选中行的学号，打开编辑页面进行修改
					String courseNo = (String) table.getValueAt(selectedRow, 0);
					editStudentInfo(courseNo);
				} else {
					JOptionPane.showMessageDialog(CourseListForm.this, "请选择要修改的课程");
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// 获取选中行的课程号，进行更改状态操作
					String courseNo = (String) table.getValueAt(selectedRow, 0);
					try {
						CourseInfoDao.alterCourseStatus(courseNo);
						JOptionPane.showMessageDialog(CourseListForm.this, "删除成功", "消息", JOptionPane.INFORMATION_MESSAGE);
						// 刷新表格数据
						displayTable(CourseInfoDao.getCourseList(""));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(CourseListForm.this, "删除失败", "消息", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(CourseListForm.this, "请选择要删除的课程");
				}
			}
		});

		// 显示初始化数据
		this.displayTable(CourseInfoDao.getCourseList(""));
	}

	/**
	 * 显示表格信息
	 * 
	 * @param couList
	 */
	public void displayTable(List<CourseInfo> couList) {
		if(couList==null)return;
		tableModel.setRowCount(0);
		for (CourseInfo couInfo : couList) {
			String status = couInfo.getStatus() == '1' ? "在学" : "结课";
			Object[] rowData = { couInfo.getCourseNo(), couInfo.getCourseName(), status, couInfo.getSemster()};
			tableModel.addRow(rowData);
		}
	}

	/**
	 * 编辑学生信息
	 * 
	 * @param courseNo
	 */
	private void editStudentInfo(String courseNo) {
		// 打开编辑页面并传递学号
		EditCourseInfoForm editForm = new EditCourseInfoForm(this, courseNo);
		editForm.setVisible(true);
	}



}
