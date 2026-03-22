package pkg17.ScoreManagement.ui.tListForm;


import pkg17.ScoreManagement.dao.ScoreInfoDao;
import pkg17.ScoreManagement.dao.StudentInfoDao;
import pkg17.ScoreManagement.dao.TeacherInfoDao;
import pkg17.ScoreManagement.model.ScoreInfo;
import pkg17.ScoreManagement.model.StudentInfo;
import pkg17.ScoreManagement.model.TeacherInfo;
import pkg17.ScoreManagement.ui.editForm.EditScoreInfoForm;
import pkg17.ScoreManagement.ui.editForm.EditTeacherInfoForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class TeaListForm extends JFrame {
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton totalButton;
	private String teacherId;

	public TeaListForm(String teacherId) {
		this.teacherId = teacherId;
		initComponent();
	}

	private void initComponent() {
		setTitle("教师信息列表");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLayout(new BorderLayout());

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 布局方式2：从左边开始
		totalButton = new JButton("统计");
		searchPanel.add(totalButton);

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tableModel = new DefaultTableModel();
		tableModel.addColumn("教师号");
		tableModel.addColumn("姓名");
		tableModel.addColumn("年龄");
		tableModel.addColumn("性别");
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new Label());// 占位

		add(tablePanel, BorderLayout.CENTER);

		// 根据学号查询学生信息并显示
		List<TeacherInfo> teaList = TeacherInfoDao.getTeacherListById(teacherId);
		displayTable(teaList);

		// TODO 不需要的代码
/*		totalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("进入统计按钮");
				// 调用统计方法
				calculateStatistics();
			}
		});*/

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
			Object[] rowData = { teaInfo.getTeacherId(), teaInfo.getName(), teaInfo.getAge(), teaInfo.getGender()};
			tableModel.addRow(rowData);
		}
	}



	/**
	 * 计算并显示统计信息
	 */
/*	private void calculateStatistics() {
		System.out.println("进入统计方法按钮");
		List<ScoreInfo> scoreList = ScoreInfoDao.getScoreInfoListByTeacherId(teacherId);

		if (scoreList == null || scoreList.isEmpty()) {
			JOptionPane.showMessageDialog(this, "没有数据可供统计", "统计结果", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int excellentCount = 0; // 优
		int goodCount = 0; // 良
		int mediumCount = 0; // 中
		int passCount = 0; // 及格
		int failCount = 0; // 不及格

		for (ScoreInfo scoreInfo : scoreList) {
			String score = scoreInfo.getScore();
			int scoreInt = Integer.parseInt(score);
			if (scoreInt >= 90 && scoreInt <= 100) {
				excellentCount++;
			} else if (scoreInt >= 80 && scoreInt < 90) {
				goodCount++;
			} else if (scoreInt >= 70 && scoreInt < 80) {
				mediumCount++;
			} else if (scoreInt >= 60 && scoreInt < 70) {
				passCount++;
			} else {
				failCount++;
			}
		}

		// 构建统计结果字符串
		StringBuilder result = new StringBuilder();
		result.append("统计结果:\n");
		result.append("优: ").append(excellentCount).append("人").append("\n");
		result.append("良: ").append(goodCount).append("人").append("\n");
		result.append("中: ").append(mediumCount).append("人").append("\n");
		result.append("及格: ").append(passCount).append("人").append("\n");
		result.append("不及格: ").append(failCount).append("人").append("\n");

		// 显示统计结果
		JOptionPane.showMessageDialog(this, result.toString(), "统计结果", JOptionPane.INFORMATION_MESSAGE);
	}*/

}
