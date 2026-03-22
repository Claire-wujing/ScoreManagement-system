package pkg17.ScoreManagement.ui;

import pkg17.ScoreManagement.ui.addForm.*;
import pkg17.ScoreManagement.ui.mListForm.*;
import pkg17.ScoreManagement.ui.studentForm.StudentForm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainForm extends JFrame implements ActionListener {
	JMenuBar menuBar;
	JMenu studentMenu, teacherMenu, courseMenu, scoreMenu, classMenu;
	JMenuItem addStudentItem, viewStudentItem, addTeacherItem, viewTeacherItem, addCourseItem, viewCourseItem, addScoreItem, viewScoreItem, addClassItem, viewClassItem;
	JPanel childForm; // 用于存放子窗体的容器
	private String userType;

	public MainForm(String userType) {
		this.userType = userType;
		this.setTitle("学生成绩查询系统");
		this.setSize(400, 300);

		this.initComponent();

		// 新增：初始化返回按钮
		initReturnButton();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); // 将窗口设置为最大化状态
		this.setVisible(true);
	}

	// 新增：初始化返回按钮的方法
	private void initReturnButton() {
		JButton returnButton = new JButton("返回登录");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 关闭当前窗口并打开登录窗口
				MainForm.this.dispose(); // 关闭当前窗口
				new Login().setVisible(true); // 打开登录窗口
			}
		});

		// 将返回按钮添加到某个合适的位置，比如窗口底部
		JPanel buttonPanel = new JPanel(); // 创建一个面板来放置按钮
		buttonPanel.add(returnButton);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH); // 将面板添加到窗口底部
	}

	// 将子窗体添加到父窗体（本窗体）的容器中
	private void addChildFormToContainer(JFrame childJFrame) {
		// 清空以前的内容
		if(childForm.getComponentCount()>0)
			childForm.removeAll();
		childForm.add(childJFrame.getContentPane());
		childJFrame.pack();//自适应父窗体
		this.revalidate();
		this.repaint();
	}

	private void initComponent() {
		menuBar = new JMenuBar();// 菜单
		studentMenu = new JMenu("学生管理");
		teacherMenu = new JMenu("教师管理");
		courseMenu = new JMenu("课程管理");
		scoreMenu = new JMenu("成绩管理");
		classMenu = new JMenu("班级管理");

		// 添加学生菜单项及动作监听器
		addStudentItem = new JMenuItem("添加学生");
		addStudentItem.addActionListener(this);
		viewStudentItem = new JMenuItem("查询学生");
		viewStudentItem.addActionListener(this);
		studentMenu.add(addStudentItem);
		studentMenu.add(viewStudentItem);

		// 添加教师菜单项及动作监听器
		addTeacherItem = new JMenuItem("添加教师");
		addTeacherItem.addActionListener(this);
		viewTeacherItem = new JMenuItem("查询教师");
		viewTeacherItem.addActionListener(this);
		teacherMenu.add(addTeacherItem);
		teacherMenu.add(viewTeacherItem);

		// 添加课程菜单项及动作监听器
		addCourseItem = new JMenuItem("添加课程");
		addCourseItem.addActionListener(this);
		viewCourseItem = new JMenuItem("查询课程");
		viewCourseItem.addActionListener(this);
		courseMenu.add(addCourseItem);
		courseMenu.add(viewCourseItem);

		// 添加成绩菜单项及动作监听器
		addScoreItem = new JMenuItem("添加成绩");
		addScoreItem.addActionListener(this);
		viewScoreItem = new JMenuItem("查询成绩");
		viewScoreItem.addActionListener(this);
		scoreMenu.add(addScoreItem);
		scoreMenu.add(viewScoreItem);

		// 添加班级菜单项及动作监听器
		addClassItem = new JMenuItem("添加班级");
		addClassItem.addActionListener(this);
		viewClassItem = new JMenuItem("查询班级");
		viewClassItem.addActionListener(this);
		classMenu.add(addClassItem);
		classMenu.add(viewClassItem);

		// 添加到主菜单
		menuBar.add(studentMenu);
		menuBar.add(teacherMenu);
		menuBar.add(courseMenu);
		menuBar.add(scoreMenu);
		menuBar.add(classMenu);
		this.setJMenuBar(menuBar);

		// 创建一个容器用于放置子窗口
		childForm = new JPanel();
		childForm.setLayout(new BoxLayout(childForm, BoxLayout.Y_AXIS));// 表示垂直方向布局
		// 将子窗口添加到容器中(默认放班级信息)
		//childForm.add(new ClassListForm().getContentPane());
		// 将容器添加到父窗口中
		this.add(childForm);
	}

	/**
	 * 监听事件：点击菜单打开子窗体
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addStudentItem) {
			new AddStudentInfoForm(this);
		} else if (e.getSource() == viewStudentItem) {
			StudentListForm slf = new StudentListForm();
			addChildFormToContainer(slf);// 放到容器中

		} else if (e.getSource() == addClassItem){
			new AddClazzInfoForm(this);
		}else if (e.getSource() == viewClassItem) {
			ClazzListForm clf = new ClazzListForm();
			addChildFormToContainer(clf);// 放到容器中

		} else if (e.getSource() == addTeacherItem){
			new AddTeacherInfoForm(this);
		} else if (e.getSource() == viewTeacherItem) {
			TeacherListForm teacherListForm = new TeacherListForm();
			addChildFormToContainer(teacherListForm);

		} else if (e.getSource() == addCourseItem) {
			new AddCourseInfoForm(this);
		} else if (e.getSource() == viewCourseItem) {
			CourseListForm courseListForm = new CourseListForm();
			addChildFormToContainer(courseListForm);

		} else if (e.getSource() == addScoreItem) {
			new AddScoreInfoForm(this, userType); // 传递 userType
		} else if (e.getSource() == viewScoreItem) {
			ScoreListForm scoreListForm = new ScoreListForm();
			addChildFormToContainer(scoreListForm);
		}
	}
}
