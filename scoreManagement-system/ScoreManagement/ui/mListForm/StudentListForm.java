package pkg17.ScoreManagement.ui.mListForm;

import pkg17.ScoreManagement.dao.StudentInfoDao;
import pkg17.ScoreManagement.model.StudentInfo;
import pkg17.ScoreManagement.ui.editForm.EditStudentInfoForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class StudentListForm extends JFrame {
	private JLabel labelStudentName;
	private JTextField searchStudentNameField;
	private JButton searchButton;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton editButton;
	private JButton deleteButton;

	public StudentListForm() {
		initComponent();
	}

	private void initComponent() {
		setTitle("学生信息列表");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLayout(new BorderLayout());

		JPanel searchPanel = new JPanel();
		// searchPanel.setLayout(new BoxLayout(searchPanel,
		// BoxLayout.X_AXIS));//布局方式1：按X方向布局
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 布局方式2：从左边开始
		labelStudentName = new JLabel("学生名称");
		searchStudentNameField = new JTextField(20);
		searchButton = new JButton("查询");
		editButton = new JButton("修改");
		deleteButton = new JButton("删除");
		searchPanel.add(labelStudentName);
		searchPanel.add(searchStudentNameField);
		searchPanel.add(searchButton);
		searchPanel.add(editButton);
		searchPanel.add(deleteButton);

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

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new Label());// 占位

		add(searchPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String studentName = searchStudentNameField.getText();
				// 根据搜索条件进行学生信息查询，更新表格数据
				List<StudentInfo> stuList = StudentInfoDao.getStudentList(studentName);
				displayTable(stuList);
			}
		});

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// 获取选中行的学号，打开编辑页面进行修改
					String studentId = (String) table.getValueAt(selectedRow, 0);
					editStudentInfo(studentId);
				} else {
					JOptionPane.showMessageDialog(StudentListForm.this, "请选择要修改的学生");
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// 获取选中行的学生学号，进行更改状态操作
					String studentId = (String) table.getValueAt(selectedRow, 0);
					try {
						StudentInfoDao.alterStudentStatus(studentId);
						JOptionPane.showMessageDialog(StudentListForm.this, "删除成功", "消息", JOptionPane.INFORMATION_MESSAGE);
						// 刷新表格数据
						displayTable(StudentInfoDao.getStudentList(""));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(StudentListForm.this, "删除失败", "消息", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(StudentListForm.this, "请选择要删除的学生");
				}
			}
		});


		// 显示初始化数据
		this.displayTable(StudentInfoDao.getStudentList(""));
	}

	/**
	 * 显示表格信息
	 * 
	 * @param stuList
	 */
	public void displayTable(List<StudentInfo> stuList) {
		if(stuList==null)return;
		tableModel.setRowCount(0);
		for (StudentInfo stuInfo : stuList) {
			Object[] rowData = { stuInfo.getStudentId(), stuInfo.getName(), stuInfo.getGender(),stuInfo.getAge(),stuInfo.getClazzNo()};
			tableModel.addRow(rowData);
		}
	}

	/**
	 * 编辑学生信息
	 * 
	 * @param studentId
	 */
	private void editStudentInfo(String studentId) {
		// 打开编辑页面并传递学号
		EditStudentInfoForm editForm = new EditStudentInfoForm(this, studentId);
		editForm.setVisible(true);
	}



}
