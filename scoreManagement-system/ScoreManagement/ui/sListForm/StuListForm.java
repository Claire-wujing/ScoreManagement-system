package pkg17.ScoreManagement.ui.sListForm;

import pkg17.ScoreManagement.dao.StudentInfoDao;
import pkg17.ScoreManagement.model.StudentInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class StuListForm extends JFrame {
	private JTable table;
	private DefaultTableModel tableModel;
	private String studentId; // 当前登录学生的学号

	public StuListForm(String studentId) {
		this.studentId = studentId;
		initComponent();
	}

	private void initComponent() {
		setTitle("个人信息列表");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLayout(new BorderLayout());

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tableModel = new DefaultTableModel();
		tableModel.addColumn("学号");
		tableModel.addColumn("姓名");
		tableModel.addColumn("性别");
		tableModel.addColumn("年龄");
		tableModel.addColumn("班级");
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, BorderLayout.CENTER);

		add(tablePanel, BorderLayout.CENTER);

		// 根据学号查询学生信息并显示
		List<StudentInfo> stuList = StudentInfoDao.getStudentListById(studentId);
		displayTable(stuList);
	}

	/**
	 * 显示表格信息
	 *
	 * @param stuList
	 */
	public void displayTable(List<StudentInfo> stuList) {
		if(stuList == null) return;
		tableModel.setRowCount(0);
		for (StudentInfo stuInfo : stuList) {
			Object[] rowData = { stuInfo.getStudentId(), stuInfo.getName(), stuInfo.getGender(), stuInfo.getAge(), stuInfo.getClazzNo() };
			tableModel.addRow(rowData);
		}
	}
}
