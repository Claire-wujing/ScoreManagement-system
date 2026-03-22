package pkg17.ScoreManagement.ui.teacherForm;

import pkg17.ScoreManagement.dao.DatabaseUtil;
import pkg17.ScoreManagement.dao.TeacherInfoDao;
import pkg17.ScoreManagement.model.TeacherInfo;
import pkg17.ScoreManagement.ui.Login;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TeacherChangeForm extends JFrame implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JTextField usernameField;
    JPasswordField newpasswordField, surepasswordField2;
    JButton sureButton;
    ButtonGroup btuGroup;

    public TeacherChangeForm() {
        this.setTitle("修改密码");
        this.setSize(300, 300);
        this.setLocation(200, 200);
        // 设置默认关闭操作，点击窗口关闭按钮时退出程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // 创建中间面板并设置为null布局，以便后续使用setBounds进行绝对定位
        JPanel pCenter = new JPanel();
        pCenter.setLayout(null);
        this.add(pCenter, BorderLayout.CENTER);

        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField();
        usernameLabel.setBounds(10, 10, 80, 25);
        usernameField.setBounds(100, 10, 150, 25);
        usernameField.setText("");

        // 添加文本框内容变化监听器，用于实时验证输入情况并控制按钮状态
        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtonState();
            }

            private void updateButtonState() {
                String username = usernameField.getText();
                sureButton.setEnabled(!username.isEmpty());
            }
        });

        JLabel newpasswordLabel = new JLabel("新密码:");
        newpasswordField = new JPasswordField();
        newpasswordLabel.setBounds(10, 40, 80, 25);
        newpasswordField.setBounds(100, 40, 150, 25);
        newpasswordField.setText("");

        JLabel surepasswordLabel = new JLabel("确认新密码:");
        surepasswordField2 = new JPasswordField();
        surepasswordLabel.setBounds(10, 70, 80, 25);
        surepasswordField2.setBounds(100, 70, 150, 25);
        surepasswordField2.setText("");

        sureButton = new JButton("确定");
        sureButton.addActionListener(this);
        sureButton.setBounds(110, 130, 80, 30);
        // 初始时按钮设为禁用状态，因为刚开始用户名文本框为空
        sureButton.setEnabled(false);

        pCenter.add(usernameLabel);
        pCenter.add(usernameField);
        pCenter.add(newpasswordLabel);
        pCenter.add(newpasswordField);
        pCenter.add(surepasswordLabel);
        pCenter.add(surepasswordField2);
        pCenter.add(sureButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取用户输入的各项信息
        String username = usernameField.getText();
        char[] newpasswordChars = newpasswordField.getPassword();
        String newpassword = new String(newpasswordChars);
        char[] surepasswordChars = surepasswordField2.getPassword();
        String surepassword = new String(surepasswordChars);

        // 验证用户名是否为空
        if (username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先输入账户", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 验证新密码和确认密码是否一致
        if (!newpassword.equals(surepassword)) {
            JOptionPane.showMessageDialog(this, "新密码和确认密码不一致", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 验证新密码是否为空
        if (newpassword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "新密码不能为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 根据用户名查询教师信息
            TeacherInfo teacherInfo = TeacherInfoDao.getTeacherInfo(username);
            if (teacherInfo != null) {
                String sql = "update teacher set password =? where teacherId =?";
                // 进行数据库更新操作
                boolean update = DatabaseUtil.update(sql, newpassword, teacherInfo.getTeacherId());
                if (update) {
                    JOptionPane.showMessageDialog(this, "修改完成");
                    new Login();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "更新错误", "提示", JOptionPane.WARNING_MESSAGE);
                }
            } else { // 账号不存在
                JOptionPane.showMessageDialog(this, "账号不存在", "提示", JOptionPane.WARNING_MESSAGE);
            }

        } finally {
            // 安全处理，清空所有获取密码的字符数组
            for (int i = 0; i < newpasswordChars.length; i++) {
                newpasswordChars[i] = '\0';
            }
            for (int i = 0; i < surepasswordChars.length; i++) {
                surepasswordChars[i] = '\0';
            }
        }
    }
}
