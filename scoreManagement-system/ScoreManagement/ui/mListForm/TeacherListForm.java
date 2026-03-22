package pkg17.ScoreManagement.ui.mListForm;



import pkg17.ScoreManagement.dao.TeacherInfoDao;
import pkg17.ScoreManagement.model.TeacherInfo;
import pkg17.ScoreManagement.ui.editForm.EditTeacherInfoForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class TeacherListForm extends JFrame {
	private JLabel labelTeacherName;
	private JTextField searchTeacherNameField;
	private JButton searchButton;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton editButton;
	private JButton deleteButton;

	public TeacherListForm() {
		initComponent();
	}

	private void initComponent() {
		setTitle("教师信息列表");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLayout(new BorderLayout());

		JPanel searchPanel = new JPanel();
		// searchPanel.setLayout(new BoxLayout(searchPanel,
		// BoxLayout.X_AXIS));//布局方式1：按X方向布局
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 布局方式2：从左边开始
		labelTeacherName = new JLabel("教师名称");
		searchTeacherNameField = new JTextField(20);
		searchButton = new JButton("查询");
		editButton = new JButton("修改");
		deleteButton = new JButton("删除");
		searchPanel.add(labelTeacherName);
		searchPanel.add(searchTeacherNameField);
		searchPanel.add(searchButton);
		searchPanel.add(editButton);
		searchPanel.add(deleteButton);

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tableModel = new DefaultTableModel();
		tableModel.addColumn("教师号");
		tableModel.addColumn("姓名");
		tableModel.addColumn("年龄");
		tableModel.addColumn("性别");
		tableModel.addColumn("课程号");
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
				String teacherName = searchTeacherNameField.getText();
				// 根据搜索条件进行教师信息查询，更新表格数据
				List<TeacherInfo> teaList = TeacherInfoDao.getTeacherList(teacherName);
				displayTable(teaList);
			}
		});

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// 获取选中行的教师号，打开编辑页面进行修改
					String teacherId = (String) table.getValueAt(selectedRow, 0);
					editTeacherInfo(teacherId);
				} else {
					JOptionPane.showMessageDialog(TeacherListForm.this, "请选择要修改的教师");
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// 获取选中行的教师号，进行更改状态操作
					String teacherId = (String) table.getValueAt(selectedRow, 0);
					try {
						TeacherInfoDao.alterTeacherStatus(teacherId);
						JOptionPane.showMessageDialog(TeacherListForm.this, "删除成功", "消息", JOptionPane.INFORMATION_MESSAGE);
						// 刷新表格数据
						displayTable(TeacherInfoDao.getTeacherList(""));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(TeacherListForm.this, "删除失败", "消息", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(TeacherListForm.this, "请选择要删除的教师");
				}
			}
		});

		// 显示初始化数据
		this.displayTable(TeacherInfoDao.getTeacherList(""));
	}

	/**
	 * 显示表格信息
	 * 
	 * @param teaList
	 */
	public void displayTable(List<TeacherInfo> teaList) {
		if(teaList==null)return;
		tableModel.setRowCount(0);
		for( TeacherInfo teaInfo : teaList) {
			Object[] rowData = { teaInfo.getTeacherId(), teaInfo.getName(), teaInfo.getAge(), teaInfo.getGender(), teaInfo.getCourseNo()};
			tableModel.addRow(rowData);
		}
	}

	/**
	 * 编辑学生信息
	 * 
	 * @param teacherId
	 */
	private void editTeacherInfo(String teacherId) {
		// 打开编辑页面并传递学号
		EditTeacherInfoForm editForm = new EditTeacherInfoForm(this, teacherId);
		editForm.setVisible(true);
	}



}
