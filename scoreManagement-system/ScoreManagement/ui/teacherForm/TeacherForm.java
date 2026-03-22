package pkg17.ScoreManagement.ui.teacherForm;

import pkg17.ScoreManagement.model.TeacherInfo;
import pkg17.ScoreManagement.ui.Login;
import pkg17.ScoreManagement.ui.addForm.AddScoreInfoForm;
import pkg17.ScoreManagement.ui.mListForm.ScoreListForm;
import pkg17.ScoreManagement.ui.studentForm.StudentForm;
import pkg17.ScoreManagement.ui.tListForm.CouListForm;
import pkg17.ScoreManagement.ui.tListForm.TeaListForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherForm extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu studentMenu, teacherMenu, courseMenu, scoreMenu, classMenu;
    JMenuItem viewTeacherItem, viewCourseItem, addScoreItem, viewScoreItem;
    JPanel childForm; // 用于存放子窗体的容器
    private String currentTeacherId;
    private String userType;

    public TeacherForm(String currentTeacherId, String userType) {
        this.currentTeacherId = currentTeacherId;
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
                TeacherForm.this.dispose(); // 关闭当前窗口
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
        studentMenu = new JMenu("个人信息");
        courseMenu = new JMenu("课程信息");
        scoreMenu = new JMenu("成绩信息");

        // 添加学生菜单项及动作监听器
        viewTeacherItem = new JMenuItem("查询个人");
        viewTeacherItem.addActionListener(this);
        studentMenu.add(viewTeacherItem);

        // 添加课程菜单项及动作监听器
        viewCourseItem = new JMenuItem("查询课程");
        viewCourseItem.addActionListener(this);
        courseMenu.add(viewCourseItem);

        // 添加成绩菜单项及动作监听器
        addScoreItem = new JMenuItem("添加成绩");
        addScoreItem.addActionListener(this);
        viewScoreItem = new JMenuItem("查询成绩");
        viewScoreItem.addActionListener(this);
        scoreMenu.add(addScoreItem);
        scoreMenu.add(viewScoreItem);

        // 添加到主菜单
        menuBar.add(studentMenu);
        menuBar.add(courseMenu);
        menuBar.add(scoreMenu);
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
        if (e.getSource() == viewTeacherItem) {
            TeaListForm tlf = new TeaListForm(currentTeacherId);
            addChildFormToContainer(tlf);// 放到容器中
        } else if (e.getSource() == viewScoreItem) {
            ScoreListForm scoreListForm = new ScoreListForm(currentTeacherId, "0", userType);
            addChildFormToContainer(scoreListForm);
        } else if (e.getSource() == addScoreItem) {
            new AddScoreInfoForm(this, currentTeacherId);
        } else if (e.getSource() == viewCourseItem) {
            CouListForm couListForm = new CouListForm(currentTeacherId);
            addChildFormToContainer(couListForm);
        }
    }
}
