package pkg17.ScoreManagement.ui.sListForm;


import pkg17.ScoreManagement.dao.CourseInfoDao;
import pkg17.ScoreManagement.dao.ScoreInfoDao;
import pkg17.ScoreManagement.dao.StudentInfoDao;
import pkg17.ScoreManagement.model.CourseInfo;
import pkg17.ScoreManagement.model.StudentInfo;
import pkg17.ScoreManagement.ui.editForm.EditCourseInfoForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class CouListForm extends JFrame {
	private JTable table;
	private DefaultTableModel tableModel;
	private String studentId; // 当前登录学生的学号

	public CouListForm(String studentId) {
		this.studentId = studentId;
		initComponent();
	}

	private void initComponent() {
		setTitle("课程信息列表");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLayout(new BorderLayout());

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tableModel = new DefaultTableModel();
		tableModel.addColumn("课程号");
		tableModel.addColumn("名称");
		tableModel.addColumn("教师");
		tableModel.addColumn("学期");
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new Label());// 占位

		add(tablePanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);


		// 根据学生ID查询课程信息并显示
		List<CourseInfo> couList = ScoreInfoDao.getCoursesByStudentId(studentId);
		displayTable(couList);
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





}
