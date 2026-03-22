package pkg17.ScoreManagement.ui.editForm;

import pkg17.ScoreManagement.dao.CourseInfoDao;
import pkg17.ScoreManagement.dao.ScoreInfoDao;
import pkg17.ScoreManagement.dao.TeacherInfoDao;
import pkg17.ScoreManagement.model.CourseInfo;
import pkg17.ScoreManagement.model.ScoreInfo;
import pkg17.ScoreManagement.model.TeacherInfo;
import pkg17.ScoreManagement.ui.mListForm.ScoreListForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * 编辑学生成绩
 *
 * @author Administrator
 */
public class EditScoreInfoForm extends JFrame {
    private JTextField studentNameField, studentIdField, scoreField, courseNameField;
    private JComboBox<String> courseNoComboBox;
    private JButton saveButton, cleanButton;
    private String studentId, courseNo; // 学生学号
    private ScoreListForm parentForm; // 父窗体
    private String currentTeacherId; // 教师ID
    private String userType; // 用户类型

    /**
     * 设置窗体的父窗体，当前窗体显示在父窗体中间 作为模态窗口时使用
     *
     * @param parentForm 父窗体
     */
    public EditScoreInfoForm(ScoreListForm parentForm, String studentId, String courseNo, String currentTeacherId, String userType) {
        this.parentForm = parentForm;
        this.studentId = studentId;
        this.courseNo = courseNo;
        this.currentTeacherId = currentTeacherId; // 初始化 currentTeacherId
        this.userType = userType; // 初始化 userType
        initComponents();
        setLocationRelativeTo(parentForm); // 设置相对于父窗体居中显示
        setAlwaysOnTop(true); // 设置窗体始终置顶
        setVisible(true);
    }

    // 布局组件
    private void initComponents() {
        setTitle("学生成绩编辑");
        setSize(500, 400);

        // 关闭子窗体时不关闭父窗体，应按如下方式设置，重写关闭事件
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                // 刷新父窗体的数据
                parentForm.displayTable(ScoreInfoDao.getScoreInfoList());
                // 关闭子窗体
                dispose();
            }
        });

        // 查询初始化数据
        ScoreInfo sInfo = ScoreInfoDao.getScoreInfo(courseNo, studentId).get(0);

        // 窗体布局
        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(null);

        // 姓名
        JLabel studentNameLabel = new JLabel("姓名:");
        studentNameLabel.setBounds(10, 20, 80, 25);
        panel.add(studentNameLabel);
        studentNameField = new JTextField(20);
        studentNameField.setBounds(100, 20, 185, 25);
        studentNameField.setText(sInfo.getName());
        panel.add(studentNameField);

        // 学号
        JLabel studentIdLabel = new JLabel("学号:");
        studentIdLabel.setBounds(10, 50, 80, 25);
        panel.add(studentIdLabel);
        studentIdField = new JTextField(20);
        studentIdField.setBounds(100, 50, 185, 25);
        studentIdField.setText(sInfo.getStudentId());
        studentIdField.setEditable(false); // 学号，不可编辑
        panel.add(studentIdField);

        // 课程号
        JLabel courseNoLabel = new JLabel("课程号:");
        courseNoLabel.setBounds(10, 80, 80, 25);
        panel.add(courseNoLabel);
        courseNoComboBox = new JComboBox<>();
        courseNoComboBox.setBounds(100, 80, 185, 25);
        panel.add(courseNoComboBox);

        // 成绩
        JLabel scoreLabel = new JLabel("成绩:");
        scoreLabel.setBounds(10, 110, 80, 25);
        panel.add(scoreLabel);
        scoreField = new JTextField(20);
        scoreField.setBounds(100, 110, 185, 25);
        scoreField.setText(sInfo.getScore());
        panel.add(scoreField);

        // 课程名
        JLabel courseNameLabel = new JLabel("课程名:");
        courseNameLabel.setBounds(10, 140, 80, 25);
        panel.add(courseNameLabel);
        courseNameField = new JTextField(20);
        courseNameField.setBounds(100, 140, 185, 25);
        courseNameField.setEditable(false);
        panel.add(courseNameField);

        // 加载课程号数据到下拉框
        loadCourseNos();

        // 添加事件监听器，当选择课程号时，更新课程名
        courseNoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCourseName();
            }
        });

        // 设置初始选中的课程号
        courseNoComboBox.setSelectedItem(sInfo.getCourseNo());

        saveButton = new JButton("保存");
        saveButton.setBounds(100, 170, 80, 25);
        panel.add(saveButton);

        cleanButton = new JButton("清除");
        cleanButton.setBounds(210, 170, 80, 25);
        panel.add(cleanButton);

        // 保存
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == saveButton) {
                    String studentName = studentNameField.getText();
                    String studentId = studentIdField.getText();
                    String newCourseNo = (String) courseNoComboBox.getSelectedItem();
                    String score = scoreField.getText();
                    String courseName = courseNameField.getText();

                    // 使用原始的 courseNo 和 studentId 作为条件来更新记录
                    if (!courseNo.equals(newCourseNo)) {
                        // 更换课程号，调用新的更新方法
                        ScoreInfoDao.updateScoreInfoWithNewCourse(courseNo, studentId, newCourseNo, score);
                    } else {
                        // 未更换课程号，调用原有的更新方法
                        ScoreInfo saveScore = new ScoreInfo(studentName, studentId, newCourseNo, score, courseName);
                        ScoreInfoDao.updateScoreInfo(saveScore);
                    }

                    try {
                        JOptionPane.showMessageDialog(panel, "保存成功", "消息", JOptionPane.INFORMATION_MESSAGE);

                        // 刷新父窗体的数据
                        parentForm.displayTable(ScoreInfoDao.getScoreInfoList());
                        // 关闭子窗体
                        dispose();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(panel, "保存失败", "消息", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (e.getSource() == cleanButton) { // 清除
                    studentNameField.setText("");
                    studentIdField.setText("");
                    courseNoComboBox.setSelectedIndex(-1); // 清除选中项
                    scoreField.setText("");
                }
            }
        });
    }

    private void loadCourseNos() {
        if (currentTeacherId == null || currentTeacherId.equals("0")) {
            // 管理员加载所有课程
            List<CourseInfo> courseList = CourseInfoDao.getCourseList("");
            for (CourseInfo course : courseList) {
                courseNoComboBox.addItem(course.getCourseNo());
            }
        } else {
            // 教师加载其负责的课程
            TeacherInfo teacherInfo = TeacherInfoDao.getTeacherInfo(currentTeacherId);
            if (teacherInfo != null) {
                courseNoComboBox.addItem(teacherInfo.getCourseNo());
            }
        }
    }

    private void updateCourseName() {
        String selectedCourseNo = (String) courseNoComboBox.getSelectedItem();
        if (selectedCourseNo != null) {
            CourseInfo courseInfo = CourseInfoDao.getCourseInfo(selectedCourseNo);
            if (courseInfo != null) {
                courseNameField.setText(courseInfo.getCourseName());
            }
        }
    }
}

