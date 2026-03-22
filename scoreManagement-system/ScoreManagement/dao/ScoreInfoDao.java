package pkg17.ScoreManagement.dao;



import pkg17.ScoreManagement.model.ClazzInfo;
import pkg17.ScoreManagement.model.CourseInfo;
import pkg17.ScoreManagement.model.ScoreInfo;
import pkg17.ScoreManagement.model.TeacherInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreInfoDao {

    /**
     * 获取所有分数信息
     *
     * @return 分数信息列表
     */
    public static List<ScoreInfo> getScoreInfoList() {
        List<ScoreInfo> list = new ArrayList<>();
        String sql = "SELECT\n" +
                "    st.`name`,\n" +
                "    s.*,\n" +
                "    c.courseName \n" +
                "FROM\n" +
                "    score s\n" +
                "    JOIN student st ON s.studentId = st.studentId\n" +
                "    JOIN course c ON s.courseNo = c.courseNo\n" +
                "    order by s.studentId Asc;"; // 假设分数表名为score
        ResultSet rSet = DatabaseUtil.query(sql);

        try {
            while (rSet.next()) {
                ScoreInfo scoreInfo = new ScoreInfo();
                scoreInfo.setName(rSet.getString("name"));
                scoreInfo.setCourseNo(rSet.getString("courseNo"));
                scoreInfo.setStudentId(rSet.getString("studentId"));
                scoreInfo.setScore(rSet.getString("score"));
                scoreInfo.setCourseName(rSet.getString("courseName"));
                list.add(scoreInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 根据课程号和学生ID查找分数信息
     *
     * @param courseNo  课程号
     * @param studentId 学生ID
     * @return 分数信息对象（若存在）或null（若不存在）
     */
    public static List<ScoreInfo> getScoreInfo(String courseNo, String studentId) {
        ScoreInfo scoreInfo = null;
        List<ScoreInfo> list = new ArrayList<>();
        String sql = "SELECT\n" +
                "\tst.`name`,\n" +
                "\ts.*,\n" +
                "\tc.courseName \n" +
                "FROM\n" +
                "\tscore s\n" +
                "\tJOIN student st ON s.studentId = st.studentId\n" +
                "\tJOIN course c ON s.courseNo = c.courseNo \n" +
                "WHERE\n" +
                "\ts.courseNo = ? \n" +
                "\tAND s.studentId = ?;";
        ResultSet rSet = DatabaseUtil.query(sql, courseNo, studentId);

        try {
            if (rSet.next()) {
                scoreInfo = new ScoreInfo();
                scoreInfo.setName(rSet.getString("name"));
                scoreInfo.setCourseNo(rSet.getString("courseNo"));
                scoreInfo.setStudentId(rSet.getString("studentId"));
                scoreInfo.setScore(rSet.getString("score"));
                scoreInfo.setCourseName(rSet.getString("courseName"));
                list.add(scoreInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 录入分数信息
     *
     * @param scoreInfo 分数信息对象
     */
    public static void saveScoreInfo(ScoreInfo scoreInfo) {
        String sql = "INSERT INTO score (courseNo, studentId, score) VALUES (?, ?, ?)";
        boolean insert = DatabaseUtil.insert(sql, scoreInfo.getCourseNo(), scoreInfo.getStudentId(), scoreInfo.getScore());

        if (insert) {
            System.out.println("分数信息插入成功");
        } else {
            System.out.println("分数信息插入失败");
        }
    }

    /**
     * 根据学生ID查找分数信息
     *
     * @param studentId 学生ID
     * @return 分数信息对象（若存在）或null（若不存在）
     */
    public static List<ScoreInfo> getScoreInfoByStudentId(String studentId) {
        ScoreInfo scoreInfo = null;
        List<ScoreInfo> list = new ArrayList<>();
        String sql = "SELECT\n" +
                "\tst.`name`,\n" +
                "\ts.*,\n" +
                "\tc.courseName \n" +
                "FROM\n" +
                "\tscore s\n" +
                "\tJOIN student st ON s.studentId = st.studentId\n" +
                "\tJOIN course c ON s.courseNo = c.courseNo \n" +
                "WHERE\n" +
                "\ts.studentId = ? ;";
        ResultSet rSet = DatabaseUtil.query(sql, studentId);

        try {
            while (rSet.next()) {
                scoreInfo = new ScoreInfo();
                scoreInfo.setName(rSet.getString("name"));
                scoreInfo.setCourseNo(rSet.getString("courseNo"));
                scoreInfo.setStudentId(rSet.getString("studentId"));
                scoreInfo.setScore(rSet.getString("score"));
                scoreInfo.setCourseName(rSet.getString("courseName"));
                list.add(scoreInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 根据课程号查找分数信息
     *
     * @param courseNo 课程号
     * @return 分数信息对象（若存在）或null（若不存在）
     */
    public static List<ScoreInfo> getScoreInfoByCourseNo(String courseNo) {
        ScoreInfo scoreInfo = null;
        List<ScoreInfo> list = new ArrayList<>();
        String sql = "SELECT\n" +
                "\tst.`name`,\n" +
                "\ts.*,\n" +
                "\tc.courseName \n" +
                "FROM\n" +
                "\tscore s\n" +
                "\tJOIN student st ON s.studentId = st.studentId\n" +
                "\tJOIN course c ON s.courseNo = c.courseNo \n" +
                "WHERE\n" +
                "\ts.courseNo = ? ;";
        ResultSet rSet = DatabaseUtil.query(sql, courseNo);

        try {
            while (rSet.next()) {
                scoreInfo = new ScoreInfo();
                scoreInfo.setName(rSet.getString("name"));
                scoreInfo.setCourseNo(rSet.getString("courseNo"));
                scoreInfo.setStudentId(rSet.getString("studentId"));
                scoreInfo.setScore(rSet.getString("score"));
                scoreInfo.setCourseName(rSet.getString("courseName"));
                list.add(scoreInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 更新分数信息
     *
     * @param scoreInfo 分数信息对象（包含要更新的字段值）
     */
    public static void updateScoreInfo(ScoreInfo scoreInfo) {
        String sql = "UPDATE score SET score = ? WHERE courseNo = ? AND studentId = ?";
        boolean update = DatabaseUtil.update(sql, scoreInfo.getScore(), scoreInfo.getCourseNo(), scoreInfo.getStudentId());

        if (update) {
            System.out.println("分数信息更新成功");
        } else {
            System.out.println("分数信息更新失败");
        }
    }

    /**
     * 删除分数信息
     *
     * @param courseNo  课程号
     * @param studentId 学生ID
     */
    public static void deleteScoreInfo(String courseNo, String studentId) {
        String sql = "DELETE FROM score WHERE courseNo = ? AND studentId = ?";
        boolean delete = DatabaseUtil.delete(sql, courseNo, studentId);

        if (delete) {
            System.out.println("分数信息删除成功");
        } else {
            System.out.println("分数信息删除失败");
        }
    }

    public static boolean isStudentExists(String studentId) {
        String sql = "select count(*) from student where studentId = ?";
        ResultSet rSet = DatabaseUtil.query(sql, studentId);

        try {
            if (rSet.next()) {
                int count = rSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateScoreInfoWithNewCourse(String oldCourseNo, String studentId, String newCourseNo, String score) {
        // 先删除旧的记录
        String deleteSql = "DELETE FROM score WHERE courseNo = ? AND studentId = ?";
        boolean delete = DatabaseUtil.delete(deleteSql, oldCourseNo, studentId);

        if (delete) {
            // 再插入新的记录
            String insertSql = "INSERT INTO score (courseNo, studentId, score) VALUES (?, ?, ?)";
            boolean insert = DatabaseUtil.insert(insertSql, newCourseNo, studentId, score);

            if (insert) {
                System.out.println("分数信息更新成功");
            } else {
                System.out.println("分数信息更新失败");
            }
        } else {
            System.out.println("删除旧记录失败");
        }
    }

    /**
     * 根据学生ID查询关联的课程信息
     *
     * @param studentId
     * @return
     */
    public static List<CourseInfo> getCoursesByStudentId(String studentId) {
        List<CourseInfo> courseList = new ArrayList<>();
        String sql = "SELECT\n" +
                "    c.courseNo,\n" +
                "    c.courseName,\n" +
                "    c.status,\n" +
                "    c.semster\n" +
                "FROM\n" +
                "    score s\n" +
                "    JOIN course c ON s.courseNo = c.courseNo\n" +
                "WHERE\n" +
                "    s.studentId = ?\n" +
                "    AND c.status = 1";
        ResultSet rSet = DatabaseUtil.query(sql, studentId);

        try {
            while (rSet.next()) {
                CourseInfo courseInfo = new CourseInfo();
                courseInfo.setCourseNo(rSet.getString("courseNo"));
                courseInfo.setCourseName(rSet.getString("courseName"));
                courseInfo.setStatus(rSet.getString("status").charAt(0));
                courseInfo.setSemster(rSet.getString("semster"));
                courseList.add(courseInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList;
    }


    /**
     * 根据教师ID获取分数信息
     *
     * @param teacherId 教师ID
     * @return 分数信息列表
     */



    public static List<ScoreInfo> getScoreInfoListByTeacherId(String teacherId) {
        // 1. 先根据teacherId查询到courseNo
        String courseNo = TeacherInfoDao.getTeacherInfo(teacherId).getCourseNo();
        // 2. 根据courseNo查询学生成绩
        List<ScoreInfo> scoreInfoByCourseNo = ScoreInfoDao.getScoreInfoByCourseNo(courseNo);
        return scoreInfoByCourseNo;
    }
}