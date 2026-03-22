package pkg17.ScoreManagement.ui.mListForm;



import pkg17.ScoreManagement.dao.ClazzInfoDao;
import pkg17.ScoreManagement.model.ClazzInfo;
import pkg17.ScoreManagement.ui.editForm.EditClazzInfoForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ClazzListForm extends JFrame {
	private JLabel labelClazzNo;
	private JTextField searchClazzNoField;
	private JButton searchButton;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton editButton;
	private JButton deleteButton;

	public ClazzListForm() {
		initComponent();
	}

	private void initComponent() {
		setTitle("班级信息列表");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLayout(new BorderLayout());

		JPanel searchPanel = new JPanel();
		// searchPanel.setLayout(new BoxLayout(searchPanel,
		// BoxLayout.X_AXIS));//布局方式1：按X方向布局
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 布局方式2：从左边开始
		labelClazzNo = new JLabel("班级编号");
		searchClazzNoField = new JTextField(20);
		searchButton = new JButton("查询");
		editButton = new JButton("修改");
		deleteButton = new JButton("删除");
		searchPanel.add(labelClazzNo);
		searchPanel.add(searchClazzNoField);
		searchPanel.add(searchButton);
		searchPanel.add(editButton);
		searchPanel.add(deleteButton);

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tableModel = new DefaultTableModel();
		tableModel.addColumn("班级号");
		tableModel.addColumn("状态");
		tableModel.addColumn("人数");
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
				String clazzNo= searchClazzNoField.getText();
				// 根据搜索条件进行班级信息查询，更新表格数据
				List<ClazzInfo> claList = ClazzInfoDao.getClazzList(clazzNo);
				displayTable(claList);
			}
		});

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// 获取选中行的学号，打开编辑页面进行修改
					String clazzNo = (String) table.getValueAt(selectedRow, 0);
					editStudentInfo(clazzNo);
				} else {
					JOptionPane.showMessageDialog(ClazzListForm.this, "请选择要修改的班级");
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// 获取选中行的班级号，进行更改状态操作
					String clazzNo = (String) table.getValueAt(selectedRow, 0);
					try {
						ClazzInfoDao.alterClazzStatus(clazzNo);
						JOptionPane.showMessageDialog(ClazzListForm.this, "删除成功", "消息", JOptionPane.INFORMATION_MESSAGE);
						// 刷新表格数据
						displayTable(ClazzInfoDao.getClazzList(""));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(ClazzListForm.this, "删除失败", "消息", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(ClazzListForm.this, "请选择要删除的班级");
				}
			}
		});

		// 显示初始化数据
		this.displayTable(ClazzInfoDao.getClazzList(""));
	}

	/**
	 * 显示表格信息
	 * 
	 * @param claList
	 */
	public void displayTable(List<ClazzInfo> claList) {
		if(claList==null)return;
		tableModel.setRowCount(0);
		for (ClazzInfo claInfo : claList) {
			Object[] rowData = {claInfo.getClazzNo(), claInfo.getStatus(),claInfo.getStudentCount()};
			tableModel.addRow(rowData);
		}
	}

	/**
	 * 编辑学生信息
	 * 
	 * @param clazzNo
	 */
	private void editStudentInfo(String clazzNo) {
		// 打开编辑页面并传递学号
		EditClazzInfoForm editForm = new EditClazzInfoForm(this, clazzNo);
		editForm.setVisible(true);
	}



}
