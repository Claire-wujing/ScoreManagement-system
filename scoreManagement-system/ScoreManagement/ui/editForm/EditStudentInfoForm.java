package pkg17.ScoreManagement.ui.editForm;

import pkg17.ScoreManagement.dao.ClazzInfoDao;
import pkg17.ScoreManagement.dao.StudentInfoDao;
import pkg17.ScoreManagement.model.ClazzInfo;
import pkg17.ScoreManagement.model.StudentInfo;
import pkg17.ScoreManagement.ui.mListForm.StudentListForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * 编辑班级信息
 *
 * @author Administrator
 *
 */
public class EditStudentInfoForm extends JFrame {
	private JTextField studentIdField, studentNameField, ageField;
	private JRadioButton maleRadioButton, femaleRadioButton;
	private JButton saveButton, cleanButton;
	private JComboBox<String> classComboBox;
	private String studentId; // 学生学号
	private StudentListForm parentForm; // 父窗体

	/**
	 * 设置窗体的父窗体，当前窗体显示在父窗体中间 作为模态窗口时使用
	 *
	 * @param parentForm 父窗体
	 */
	public EditStudentInfoForm(StudentListForm parentForm, String studentId) {
		this.parentForm = parentForm;
		this.studentId = studentId;
		initComponents();
		setLocationRelativeTo(parentForm); // 设置相对于父窗体居中显示
		setAlwaysOnTop(true); // 设置窗体始终置顶
		setVisible(true);
	}

	// 布局组件
	private void initComponents() {
		setTitle("学生信息编辑");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// 关闭子窗体时不关闭父窗体，应按如下方式设置，重写关闭事件
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				// 刷新父窗体的数据
				parentForm.displayTable(StudentInfoDao.getStudentList(""));
				// 关闭子窗体
				dispose();
			}
		});

		// 查询初始化数据
		StudentInfo sInfo = StudentInfoDao.getStudentInfo(studentId);

		// 窗体布局
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(null);

		// 学号
		JLabel studentIdLabel = new JLabel("学号:");
		studentIdLabel.setBounds(10, 20, 80, 25);
		panel.add(studentIdLabel);
		studentIdField = new JTextField(25);
		studentIdField.setBounds(100, 20, 185, 25);
		studentIdField.setText(String.valueOf(sInfo.getStudentId())); // 初始化数据
		studentIdField.setEditable(false); // 学号，不可编辑
		panel.add(studentIdField);

		JLabel studentNameLabel = new JLabel("姓名:");
		studentNameLabel.setBounds(10, 50, 80, 25);
		panel.add(studentNameLabel);
		studentNameField = new JTextField(20);
		studentNameField.setBounds(100, 50, 185, 25);
		studentNameField.setText(sInfo.getName()); // 初始化数据
		panel.add(studentNameField);

		JLabel genderLabel = new JLabel("性别:");
		genderLabel.setBounds(10, 80, 80, 25);
		panel.add(genderLabel);

		maleRadioButton = new JRadioButton("男");
		maleRadioButton.setBounds(100, 80, 50, 25);
		femaleRadioButton = new JRadioButton("女");
		femaleRadioButton.setBounds(160, 80, 50, 25);

		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(maleRadioButton);
		genderGroup.add(femaleRadioButton);

		// 初始化单选框
		if (sInfo.getGender() == '男') {
			maleRadioButton.setSelected(true);
		} else if (sInfo.getGender() == '女') {
			femaleRadioButton.setSelected(true);
		}

		panel.add(maleRadioButton);
		panel.add(femaleRadioButton);

		JLabel ageJLabel = new JLabel("年龄:");
		ageJLabel.setBounds(10, 110, 80, 25);
		panel.add(ageJLabel);
		ageField = new JTextField(20);
		ageField.setBounds(100, 110, 185, 25);
		ageField.setText(String.valueOf(sInfo.getAge())); // 初始化数据
		panel.add(ageField);

		JLabel classIdJLabel = new JLabel("班级:");
		classIdJLabel.setBounds(10, 140, 80, 25);
		panel.add(classIdJLabel);

		// 获取班级信息并填充到下拉框
		List<ClazzInfo> clazzList = ClazzInfoDao.getClazzList("");
		String[] classArray = new String[clazzList.size()];
		for (int i = 0; i < clazzList.size(); i++) {
			classArray[i] = clazzList.get(i).getClazzNo();
		}
		classComboBox = new JComboBox<>(classArray);
		classComboBox.setBounds(100, 140, 185, 25);
		panel.add(classComboBox);

		saveButton = new JButton("保存");
		saveButton.setBounds(100, 200, 80, 25);
		panel.add(saveButton);

		cleanButton = new JButton("清除");
		cleanButton.setBounds(210, 200, 80, 25);
		panel.add(cleanButton);

		// 保存
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String studentId = studentIdField.getText();
				String studentName = studentNameField.getText();
				char gender = '男'; // 默认为男
				if (femaleRadioButton.isSelected()) {
					gender = '女';
				}
				int age = Integer.parseInt(ageField.getText());
				String classId = (String) classComboBox.getSelectedItem();

				// 保存到数据库
				StudentInfo saveStudentInfo = new StudentInfo(studentId, studentName, age, gender, classId);
				try {
					StudentInfoDao.udpateStudentInfo(saveStudentInfo);
					JOptionPane.showMessageDialog(panel, "保存成功", "消息", JOptionPane.INFORMATION_MESSAGE);

					// 刷新父窗体的数据
					parentForm.displayTable(StudentInfoDao.getStudentList(""));
					// 关闭子窗体
					dispose();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(panel, "保存失败", "消息", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// 清除
		cleanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentNameField.setText("");
				maleRadioButton.setSelected(true); // 默认选中“男”
				ageField.setText("");
				classComboBox.setSelectedIndex(0); // 默认选中第一个班级
			}
		});
	}
}
