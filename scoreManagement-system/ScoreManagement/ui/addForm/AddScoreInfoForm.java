package pkg17.ScoreManagement.ui.addForm;

import pkg17.ScoreManagement.dao.CourseInfoDao;
import pkg17.ScoreManagement.dao.ScoreInfoDao;
import pkg17.ScoreManagement.dao.StudentInfoDao;
import pkg17.ScoreManagement.dao.TeacherInfoDao;
import pkg17.ScoreManagement.model.CourseInfo;
import pkg17.ScoreManagement.model.ScoreInfo;
import pkg17.ScoreManagement.model.StudentInfo;
import pkg17.ScoreManagement.model.TeacherInfo;
import pkg17.ScoreManagement.ui.MainForm;
import pkg17.ScoreManagement.ui.teacherForm.TeacherForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * 添加成绩信息
 *
 * @author Administrator
 */
public class AddScoreInfoForm extends JFrame {
    private JTextField studentIdField, scoreField, courseNameField;
    private JComboBox<String> courseNoComboBox;
    private JComboBox<String> studentNameComboBox;
    private JButton saveButton, cleanButton;
    private String currentTeacherId; // 添加 currentTeacherId 字段

    /**
     * 设置窗体的父窗体，当前窗体显示在父窗体中间 作为模态窗口时使用
     *
     * @param parentForm 父窗体
     */
    public AddScoreInfoForm(MainForm parentForm, String userType) {
        initComponents();
        setLocationRelativeTo(parentForm); // 设置相对于父窗体居中显示
        setAlwaysOnTop(true); // 设置窗体始终置顶
        setVisible(true);
    }

    public AddScoreInfoForm(TeacherForm teacherForm, String currentTeacherId) {
        this.currentTeacherId = currentTeacherId; // 初始化 currentTeacherId
        initComponents();
        setLocationRelativeTo(teacherForm); // 设置相对于父窗体居中显示
        setAlwaysOnTop(true); // 设置窗体始终置顶
        setVisible(true);
    }

    // 布局组件
    private void initComponents() {
        setTitle("成绩信息录入");
        setSize(500, 400);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                dispose();
            }
        });

        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(null);

        // 姓名
        JLabel studentNameLabel = new JLabel("姓名:");
        studentNameLabel.setBounds(10, 20, 80, 25);
        panel.add(studentNameLabel);
        studentNameComboBox = new JComboBox<>();
        studentNameComboBox.setBounds(100, 20, 185, 25);
        panel.add(studentNameComboBox);

        // 学号
        JLabel studentIdLabel = new JLabel("学号:");
        studentIdLabel.setBounds(10, 50, 80, 25);
        panel.add(studentIdLabel);
        studentIdField = new JTextField(20);
        studentIdField.setBounds(100, 50, 185, 25);
        studentIdField.setEditable(false); // 设置为只读
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
        panel.add(scoreField);

        // 课程名
        JLabel courseNameLabel = new JLabel("课程名:");
        courseNameLabel.setBounds(10, 140, 80, 25);
        panel.add(courseNameLabel);
        courseNameField = new JTextField(20);
        courseNameField.setBounds(100, 140, 185, 25);
        courseNameField.setEditable(false);
        panel.add(courseNameField);

        // 加载学生姓名数据到下拉框
        loadStudentNames();
        // 加载课程号数据到下拉框
        loadCourseNos();

        // 添加事件监听器，当选择姓名时，更新学号
        studentNameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudentId();
            }
        });
        // 添加事件监听器，当选择课程号时，更新课程名
        courseNoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCourseName();
            }
        });

        saveButton = new JButton("保存");
        saveButton.setBounds(100, 200, 80, 25);
        panel.add(saveButton);

        cleanButton = new JButton("清除");
        cleanButton.setBounds(210, 200, 80, 25);
        panel.add(cleanButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == saveButton) {
                    String studentName = (String) studentNameComboBox.getSelectedItem();
                    String studentId = studentIdField.getText();
                    String courseNo = (String) courseNoComboBox.getSelectedItem();
                    String score = scoreField.getText();
                    String courseName = courseNameField.getText();

                    // 检查是否有空值
                    if (studentName.isEmpty() || studentId.isEmpty() || courseNo == null || score.isEmpty() || courseName == null) {
                        JOptionPane.showMessageDialog(panel, "请填写所有必填项", "消息", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 检查学生是否存在
                    if (!ScoreInfoDao.isStudentExists(studentId)) {
                        JOptionPane.showMessageDialog(panel, "学生不存在，请先添加学生信息", "消息", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 判断数据是否存在，如存在，则提示，且不执行增加操作
                    List<ScoreInfo> scoreInfoList = ScoreInfoDao.getScoreInfo(courseNo, studentId);
                    if (scoreInfoList.size() != 0) {
                        JOptionPane.showMessageDialog(panel, "该学号在该课程中已存在成绩记录", "消息", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 如不存在，则保存到数据库
                    ScoreInfo saveScoreInfo = new ScoreInfo(studentName, studentId, courseNo, score, courseName);
                    try {
                        ScoreInfoDao.saveScoreInfo(saveScoreInfo);
                        JOptionPane.showMessageDialog(panel, "保存成功", "消息", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(panel, "保存失败", "消息", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (e.getSource() == cleanButton) { // 清除
                    studentIdField.setText("");
                    scoreField.setText("");
                    courseNoComboBox.setSelectedIndex(-1); // 清除选中项
                    courseNameField.setText(""); // 清除选中项
                }
            }
        });
    }

    private void loadStudentNames() {
        List<StudentInfo> studentList = StudentInfoDao.getStudentList("");
        for (StudentInfo student : studentList) {
            studentNameComboBox.addItem(student.getName());
        }
    }

    private void updateStudentId() {
        String selectedStudentName = (String) studentNameComboBox.getSelectedItem();
        if (selectedStudentName != null) {
            StudentInfo studentInfo = StudentInfoDao.getStudentInfoByName(selectedStudentName);
            if (studentInfo != null) {
                studentIdField.setText(studentInfo.getStudentId());
            }
        }
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
