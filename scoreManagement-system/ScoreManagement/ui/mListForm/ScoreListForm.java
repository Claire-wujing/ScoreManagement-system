package pkg17.ScoreManagement.ui.mListForm;

import pkg17.ScoreManagement.dao.ScoreInfoDao;
import pkg17.ScoreManagement.dao.TeacherInfoDao;
import pkg17.ScoreManagement.model.ScoreInfo;
import pkg17.ScoreManagement.model.TeacherInfo;
import pkg17.ScoreManagement.ui.editForm.EditScoreInfoForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class ScoreListForm extends JFrame {
    private JLabel labelStudentId;
    private JTextField searchStudentIdField;
    private JLabel labelCourseNo;
    private JTextField searchCourseNoField;
    private JButton searchButton, totalButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton editButton;
    private JButton deleteButton;
    private static String currentTeacherId; // 用于根据教师id查询指定课程
    private static String currentStudentId; // 用于根据学号查询指定成绩
    private String userType; // 用户类型

    public ScoreListForm() {
        initComponent();
    }

    public ScoreListForm(String currentTeacherId, String currentStudentId, String userType) {
        this.currentTeacherId = currentTeacherId;
        this.currentStudentId = currentStudentId;
        this.userType = userType; // 初始化 userType
        initComponent();
    }

    private void initComponent() {
        setTitle("成绩信息列表");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 布局方式2：从左边开始
        if (currentStudentId == null || currentStudentId.equals("0")) { // 学生端不能有 则studentId = null 或 studentId = 0有按钮
            labelStudentId = new JLabel("学号");
            searchStudentIdField = new JTextField(20);
            labelCourseNo = new JLabel("课程号");
            searchCourseNoField = new JTextField(20);
            searchButton = new JButton("查询");
            editButton = new JButton("修改");
            deleteButton = new JButton("删除");
            totalButton = new JButton("统计");
        }

        if (currentStudentId == null || currentStudentId.equals("0")) {
            searchPanel.add(labelStudentId);
            searchPanel.add(searchStudentIdField);
            searchPanel.add(labelCourseNo);
            searchPanel.add(searchCourseNoField);
            searchPanel.add(searchButton);
            searchPanel.add(editButton);
            searchPanel.add(deleteButton);
            searchPanel.add(totalButton);
        }

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("姓名");
        tableModel.addColumn("学号");
        tableModel.addColumn("课程号");
        tableModel.addColumn("成绩");
        tableModel.addColumn("课程名");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new Label());// 占位

        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        if (currentStudentId == null || currentStudentId.equals("0")) {
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String studentId = searchStudentIdField.getText();
                    String courseNo = searchCourseNoField.getText();

                    // 根据搜索条件进行学生成绩查询，更新表格数据
                    List<ScoreInfo> scoreList = null;
                    if (!studentId.equals("") && !courseNo.equals("")) {
                        scoreList = ScoreInfoDao.getScoreInfo(courseNo, studentId);
                    } else if (!studentId.equals("")) {
                        scoreList = ScoreInfoDao.getScoreInfoByStudentId(studentId);
                    } else if (!courseNo.equals("")) {
                        scoreList = ScoreInfoDao.getScoreInfoByCourseNo(courseNo);
                    } else {
                        scoreList = ScoreInfoDao.getScoreInfoList();
                    }
                    displayTable(scoreList);
                }
            });
        }

        if (currentStudentId == null || currentStudentId.equals("0")) {
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        String studentId = (String) table.getValueAt(selectedRow, 1);
                        String courseNo = (String) table.getValueAt(selectedRow, 2);
                        editStudentInfo(studentId, courseNo, currentTeacherId, userType); // 传递 currentTeacherId 和 userType
                    } else {
                        JOptionPane.showMessageDialog(ScoreListForm.this, "请选择要修改的学生");
                    }
                }
            });
        }

        if (currentStudentId == null || currentStudentId.equals("0")) {
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        int confirm = JOptionPane.showConfirmDialog(ScoreListForm.this, "确定要删除这条记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            String studentId = (String) table.getValueAt(selectedRow, 1);
                            String courseNo = (String) table.getValueAt(selectedRow, 2);
                            ScoreInfoDao.deleteScoreInfo(courseNo, studentId);
                            displayTable(ScoreInfoDao.getScoreInfoList());
                        }
                    } else {
                        JOptionPane.showMessageDialog(ScoreListForm.this, "请选择要删除的学生");
                    }
                }
            });
        }

        if (currentStudentId == null || currentStudentId.equals("0")) {
            totalButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    calculateStatistics();
                }
            });
        }

        this.displayTable(ScoreInfoDao.getScoreInfoList());
    }

    /**
     * 显示表格信息
     *
     * @param scoreList
     */
    public void displayTable(List<ScoreInfo> scoreList) {
        if (currentStudentId == null) {
            if (scoreList == null) return;
            tableModel.setRowCount(0);
            for (ScoreInfo stuInfo : scoreList) {
                Object[] rowData = {stuInfo.getName(), stuInfo.getStudentId(), stuInfo.getCourseNo(), stuInfo.getScore(), stuInfo.getCourseName()};
                tableModel.addRow(rowData);
            }
        } else if (!currentTeacherId.equals("0")) {  // 根据教师号查询教师信息
            TeacherInfo teacherInfo = TeacherInfoDao.getTeacherInfo(currentTeacherId);
            if (scoreList == null) return;
            tableModel.setRowCount(0);
            for (ScoreInfo stuInfo : scoreList) {
                if (teacherInfo.getCourseNo().equals(stuInfo.getCourseNo())) {
                    Object[] rowData = {stuInfo.getName(), stuInfo.getStudentId(), stuInfo.getCourseNo(), stuInfo.getScore(), stuInfo.getCourseName()};
                    tableModel.addRow(rowData);
                }
            }
        } else if (!currentStudentId.equals("0")) { // 根据学号查询成绩
            if (scoreList == null) return;
            tableModel.setRowCount(0);
            for (ScoreInfo stuInfo : scoreList) {
                if (currentStudentId.equals(stuInfo.getStudentId())) {
                    Object[] rowData = {stuInfo.getName(), stuInfo.getStudentId(), stuInfo.getCourseNo(), stuInfo.getScore(), stuInfo.getCourseName()};
                    tableModel.addRow(rowData);
                }
            }
        }
    }

    /**
     * 编辑学生信息
     *
     * @param studentId
     * @param courseNo
     */
    private void editStudentInfo(String studentId, String courseNo, String currentTeacherId, String userType) {
        // 打开编辑页面并传递学号和用户类型
        EditScoreInfoForm editScoreInfoForm = new EditScoreInfoForm(this, studentId, courseNo, currentTeacherId, userType);
        editScoreInfoForm.setVisible(true);
    }

    /**
     * 计算并显示统计信息
     */




    private void calculateStatistics() {
        List<ScoreInfo> scoreList = ScoreInfoDao.getScoreInfoList();
        if (currentTeacherId != null) {
            scoreList = ScoreInfoDao.getScoreInfoListByTeacherId(currentTeacherId);
        }
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

        StringBuilder result = new StringBuilder();
        result.append("统计结果:\n");
        String s = ": ";
        String unit = "人";
        if (currentTeacherId == null) {
            s = "课程数： ";
            unit = "科";
        }
        result.append("优").append(s).append(excellentCount).append(unit).append("\n");
        result.append("良").append(s).append(goodCount).append(unit).append("\n");
        result.append("中").append(s).append(mediumCount).append(unit).append("\n");
        result.append("及格").append(s).append(passCount).append(unit).append("\n");
        result.append("不及格").append(s).append(failCount).append(unit).append("\n");

        JOptionPane.showMessageDialog(this, result.toString(), "统计结果", JOptionPane.INFORMATION_MESSAGE);
    }
}
