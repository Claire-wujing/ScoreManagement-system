package pkg17.ScoreManagement.ui.editForm;




import pkg17.ScoreManagement.dao.CourseInfoDao;
import pkg17.ScoreManagement.dao.TeacherInfoDao;
import pkg17.ScoreManagement.model.CourseInfo;
import pkg17.ScoreManagement.model.TeacherInfo;
import pkg17.ScoreManagement.ui.mListForm.TeacherListForm;

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
public class EditTeacherInfoForm extends JFrame {
	private JTextField teacherIdField, teacherNameField, genderField, ageField;
	private JButton saveButton, cleanButton;
	private  JComboBox courseNoField;
	private JRadioButton maleRadioButton, femaleRadioButton;
	private String teacherId;// 教师号
	private TeacherListForm parentForm;// 父窗体

	/**
	 * 设置窗体的父窗体，当前窗体显示在父窗体中间 作为模态窗口时使用
	 *
	 * @param parentForm 父窗体
	 */
	public EditTeacherInfoForm(TeacherListForm parentForm, String teacherId) {
		this.parentForm = parentForm;
		this.teacherId = teacherId;
		initComponents();
		setLocationRelativeTo(parentForm); // 设置相对于父窗体居中显示
		setAlwaysOnTop(true); // 设置窗体始终置顶
		setVisible(true);
	}

	// 布局组件
	private void initComponents() {
		setTitle("教师信息编辑");
		setSize(500, 400);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使用此方法会退出应用程序

		// 关闭子窗体时不关闭父窗体，应按如下方式设置，重写关闭事件
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				// 刷线父窗体的数据
				parentForm.displayTable(TeacherInfoDao.getTeacherList(""));
				// 关闭子窗体
				dispose();
			}
		});
	

		// 查询初始化数据
		TeacherInfo tInfo = TeacherInfoDao.getTeacherInfo(teacherId);

		// 窗体布局
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(null);

		// 教师号
		JLabel teacherIdLabel = new JLabel("教师号:");
		teacherIdLabel.setBounds(10, 20, 80, 25);
		panel.add(teacherIdLabel);
		teacherIdField = new JTextField(25);
		teacherIdField.setBounds(100, 20, 185, 25);
		teacherIdField.setText(tInfo.getTeacherId());// 初始化数据
		teacherIdField.setEditable(false); //教师号，不可编辑
		panel.add(teacherIdField);

		JLabel teacherNameLabel = new JLabel("姓名:");
		teacherNameLabel.setBounds(10, 50, 80, 25);
		panel.add(teacherNameLabel);
		teacherNameField = new JTextField(20);
		teacherNameField.setBounds(100, 50, 185, 25);
		teacherNameField.setText(tInfo.getName()); // 初始化数据
		panel.add(teacherNameField);

		JLabel ageJLabel = new JLabel("年龄:");
		ageJLabel.setBounds(10, 80, 80, 25);
		panel.add(ageJLabel);
		ageField = new JTextField(20);
		ageField.setBounds(100, 80, 185, 25);
		ageField.setText(String.valueOf(tInfo.getAge()));
		panel.add(ageField);

		JLabel genderLabel = new JLabel("性别:");
		genderLabel.setBounds(10, 110, 80, 25);
		panel.add(genderLabel);

		maleRadioButton = new JRadioButton("男");
		maleRadioButton.setBounds(100, 110, 50, 25);
		femaleRadioButton = new JRadioButton("女");
		femaleRadioButton.setBounds(160, 110, 50, 25);

		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(maleRadioButton);
		genderGroup.add(femaleRadioButton);

		panel.add(maleRadioButton);
		panel.add(femaleRadioButton);


		JLabel courseNoJLabel = new JLabel("课程:");
		courseNoJLabel.setBounds(10, 140, 80, 25);
		panel.add(courseNoJLabel);
		courseNoField = new JComboBox();
		courseNoField.setBounds(100, 140, 185, 25);
		panel.add(courseNoField);
		//给下拉框赋值
		List<CourseInfo> courseInfoList = CourseInfoDao.getCourseList("");
		for(CourseInfo courseInfo: courseInfoList)
		{
			courseNoField.addItem(courseInfo);
		}

		saveButton = new JButton("保存");
		saveButton.setBounds(100, 200, 80, 25);
		panel.add(saveButton);

		cleanButton = new JButton("清除");
		cleanButton.setBounds(210, 200, 80, 25);
		panel.add(cleanButton);

		// 保存
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == saveButton) {
					String teacherId = teacherIdField.getText();
					String teacherName = teacherNameField.getText();
					char gender = '男'; // 默认为男
					if (femaleRadioButton.isSelected()) {
						gender = '女';
					}
					int age = Integer.parseInt(ageField.getText());
					String courseNo = "";

					if(courseNoField.getSelectedItem() !=null)
					{
						CourseInfo courseInfo = ((CourseInfo)courseNoField.getSelectedItem());
						courseNo = courseInfo.getCourseNo();
					}

					// 保存到数据库
					String password = teacherId;
					char status = '1';

					TeacherInfo saveTeacherInfo = new TeacherInfo(teacherId, teacherName, age, gender,password,status, courseNo);
					try {
						TeacherInfoDao.udpateTeacherInfo(saveTeacherInfo);
						JOptionPane.showMessageDialog(panel, "保存成功", "消息", JOptionPane.INFORMATION_MESSAGE);

						// 刷线父窗体的数据
						parentForm.displayTable(TeacherInfoDao.getTeacherList(""));
						// 关闭子窗体
						dispose();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(panel, "保存失败", "消息", JOptionPane.INFORMATION_MESSAGE);
					}

					
				} else if (e.getSource() == cleanButton) {// 清除
					teacherNameField.setText("");
					genderField.setText("");
					ageField.setText("");
				} // end if
			}// end actionPerformed
		});// end addActionListener

	}
}
