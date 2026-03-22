package pkg17.ScoreManagement.ui;

import pkg17.ScoreManagement.dao.DatabaseUtil;
import pkg17.ScoreManagement.dao.StudentInfoDao;
import pkg17.ScoreManagement.dao.TeacherInfoDao;
import pkg17.ScoreManagement.model.StudentInfo;
import pkg17.ScoreManagement.model.TeacherInfo;
import pkg17.ScoreManagement.ui.studentForm.StudentChangeForm;
import pkg17.ScoreManagement.ui.studentForm.StudentForm;
import pkg17.ScoreManagement.ui.teacherForm.TeacherChangeForm;
import pkg17.ScoreManagement.ui.teacherForm.TeacherForm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, resetButton, changeButton;
    ButtonGroup btuGroup;
    JRadioButton radioStu, radioTec, radioMrg;

    public Login() {
        this.setTitle("登录");
        this.setSize(300, 300);
        this.setLocation(200, 200);
        this.setLayout(new BorderLayout()); // 按方位布局
        this.add(new JPanel(), BorderLayout.WEST); // 西占位
        this.add(new JPanel(), BorderLayout.EAST); // 东占位
        this.add(new JPanel(), BorderLayout.NORTH); // 北占位
        this.add(new JPanel(), BorderLayout.SOUTH); // 南占位
        // 中间显示布局内容，使用网格布局
        JPanel pCenter = new JPanel();
        pCenter.setLayout(new GridBagLayout());
        this.add(pCenter, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件之间的间距

        JLabel usernameLabel = new JLabel("用户名:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        pCenter.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(70, 25));
        usernameField.setText("admin");
        gbc.gridx = 1;
        gbc.gridy = 0;
        pCenter.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("密码:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        pCenter.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(70, 25));
        passwordField.setText("123456");
        gbc.gridx = 1;
        gbc.gridy = 1;
        pCenter.add(passwordField, gbc);

        JLabel usertypeLabel = new JLabel("用户类型:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        pCenter.add(usertypeLabel, gbc);

        btuGroup = new ButtonGroup();
        radioMrg = new JRadioButton("管理员", true);
        radioTec = new JRadioButton("教师", false);
        radioStu = new JRadioButton("学生", false);
        btuGroup.add(radioMrg);
        btuGroup.add(radioTec);
        btuGroup.add(radioStu);

        gbc.gridx = 1;
        gbc.gridy = 2;
        pCenter.add(radioMrg, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        pCenter.add(radioTec, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        pCenter.add(radioStu, gbc);

        loginButton = new JButton("登录");
        resetButton = new JButton("重置密码");
        changeButton = new JButton("修改密码");
        // 添加事件
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        changeButton.addActionListener(this);

        // 在页面添加控件
        gbc.gridx = 1;
        gbc.gridy = 3;
        pCenter.add(loginButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        pCenter.add(resetButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        pCenter.add(changeButton, gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String uType = radioMrg.isSelected() ? "M" : (radioTec.isSelected() ? "T" : "S");

            // 判断输入框为空
            if (username.equals("")) {
                JOptionPane.showMessageDialog(this, "请先输入账户");
            } else {
                if (password.equals("")) JOptionPane.showMessageDialog(this, "请输入密码");
                else {
                    if (uType.equals("M")) { // 管理员登陆
                        // 在这里编写验证用户名和密码的逻辑
                        if (username.equals("admin") && password.equals("123456")) {
                            // 登录成功后打开主界面
                            new MainForm(uType);
                            this.dispose(); // 关闭登录窗体
                        } else {
                            JOptionPane.showMessageDialog(this, "用户名或密码错误");
                        }
                    } else if (uType.equals("T")) {
                        // 1. 根据username查询教师是否存在
                        TeacherInfo teacherInfo = TeacherInfoDao.getTeacherInfo(username);
                        if (teacherInfo != null) {
                            // 2. 判断密码是否正确
                            if (teacherInfo.getPassword().equals(password)) {
                                // 3. 登陆成功
                                new TeacherForm(teacherInfo.getTeacherId(), uType);
                                this.dispose(); // 关闭登录窗体
                            } else {
                                JOptionPane.showMessageDialog(this, "用户名或密码错误");
                            }
                        } else { // 账号不存在
                            JOptionPane.showMessageDialog(this, "账号不存在");
                        }
                    } else if (uType.equals("S")) {
                        // 1. 根据username查询学生是否存在
                        StudentInfo studentInfo = StudentInfoDao.getStudentInfo(username);
                        if (studentInfo != null) {
                            // 2. 判断密码是否正确
                            if (studentInfo.getPassword().equals(password)) {
                                // 3. 登陆成功
                                new StudentForm(studentInfo.getStudentId()); // 传递当前登录学生的学号
                                this.dispose(); // 关闭登录窗体
                            } else {
                                JOptionPane.showMessageDialog(this, "用户名或密码错误");
                            }
                        } else { // 账号不存在
                            JOptionPane.showMessageDialog(this, "账号不存在");
                        }
                    }
                }
            }
        } else if (e.getSource() == resetButton) {
            String username = usernameField.getText();
            // 获取用户类型（这里假设你有对应的RadioButton等组件来选择用户类型）
            String uType = radioMrg.isSelected() ? "M" : (radioTec.isSelected() ? "T" : "S");

            if (uType.equals("T")) {
                // 1. 根据username查询教师是否存在（这里可以复用之前查询教师信息的方法来验证是否存在）
                TeacherInfo teacherInfo = TeacherInfoDao.getTeacherInfo(username);
                if (teacherInfo != null) {
                    // 调用修改密码的方法，将密码重置为用户名
                    String sql = "update teacher set password =? where teacherId =?";
                    // 执行数据库更新
                    boolean update = DatabaseUtil.update(sql, teacherInfo.getTeacherId(), teacherInfo.getTeacherId());
                    if (update) {
                        JOptionPane.showMessageDialog(this, "教师密码已成功重置为用户名", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "教师密码重置失败，请检查相关信息或联系管理员", "错误提示", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "账号不存在", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            } else if (uType.equals("S")) {
                // 1. 根据username查询学生是否存在（这里可以复用之前查询学生信息的方法来验证是否存在）
                StudentInfo studentInfo = StudentInfoDao.getStudentInfo(username);
                if (studentInfo != null) {
                    // 调用修改密码的方法，将密码重置为用户名
                    String sql = "update student set password =? where studentId =?";
                    // 执行数据库更新
                    boolean update = DatabaseUtil.update(sql, studentInfo.getStudentId(), studentInfo.getStudentId());
                    if (update) {
                        JOptionPane.showMessageDialog(this, "学生密码已成功重置为用户名", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "学生密码重置失败，请检查相关信息或联系管理员", "错误提示", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "账号不存在", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == changeButton) {
            String uType = radioMrg.isSelected() ? "M" : (radioTec.isSelected() ? "T" : "S");

            if (uType.equals("M")) { // 管理员登陆
                JOptionPane.showMessageDialog(this, "管理员不能修改", "提示", JOptionPane.WARNING_MESSAGE);
            } else if (uType.equals("S")) {
                // 直接打开学生修改密码表单
                new StudentChangeForm();
                this.dispose(); // 关闭登录窗体
            } else if (uType.equals("T")) {
                // 直接打开教师修改密码表单
                new TeacherChangeForm();
                this.dispose(); // 关闭登录窗体
            }
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
