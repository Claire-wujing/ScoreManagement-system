package pkg17.ScoreManagement.dao;

import pkg17.ScoreManagement.model.CourseInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseInfoDao {

    /*
     * 获取所有课程信息，支持模糊查询
     */
    public static List<CourseInfo> getCourseList(String courseName) {
        //执行数据查询逻辑,查询条件为空，则查询所有数据，否则执行模糊匹配查询
        ResultSet rSet = null;

        //查询数据
        String sql = "select * from course where status = 1";
        if (!courseName.equals("")) {
            sql += " and courseName like ?";
            rSet = DatabaseUtil.query(sql, courseName+"%");
        }
        else {
            rSet = DatabaseUtil.query(sql);
        }

        //转换为List<CourseInfo>集合
        List<CourseInfo> list = new ArrayList<CourseInfo>();
        try {
            while (rSet.next()) {
                //将获取每列数据，构建对象，并给对象赋值，
                CourseInfo sInfo = new CourseInfo();
                sInfo.setCourseNo(rSet.getString("courseNo"));
                sInfo.setCourseName(rSet.getString("courseName"));
                sInfo.setStatus(rSet.getString("status").charAt(0));
                sInfo.setSemster(rSet.getString("semster"));

                //添加到集合
                list.add(sInfo);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回结果
        return list;
    }

    /**
     * 查找单个课程SS信息
     *
     * @param
     * @return
     */
    public static CourseInfo getCourseInfo(String courseNo) {
        CourseInfo sInfo = null;
        // sql根据courseNo查询单课程信息
        String sql = "select * from course where status = 1 and courseNo = ?";
        //执行查询逻辑逻辑
        ResultSet rSet = DatabaseUtil.query(sql, courseNo);

        try {
            while (rSet.next()){
                sInfo = new CourseInfo();
                sInfo.setCourseNo(rSet.getString("courseNo"));
                sInfo.setCourseName(rSet.getString("courseName"));
                sInfo.setStatus(rSet.getString("status").charAt(0));
                sInfo.setSemster(rSet.getString("semster"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sInfo;
    }

    /**
     * 录入课程信息
     *
     * @param courseInfo
     */
    public static void saveCourseInfo(CourseInfo courseInfo){
        //执行保存逻辑
        // 1. 取出形参courseInfo中的各种属性
        String courseNo = courseInfo.getCourseNo();
        String courseName = courseInfo.getCourseName();
        String status = String.valueOf(courseInfo.getStatus());
        String semster = courseInfo.getSemster();
        // 2. 编写插入课程信息sql
        String sql = "insert into course values(?,?,?,?)";
        // 3. 执行数据库插入
        boolean insert = DatabaseUtil.insert(sql, courseNo, courseName,status, semster);
        // 判断是否插入成功
        if (insert) System.out.println("插入成功");
        else System.out.println("插入失败");

    }


    public static void udpateCourseInfo(CourseInfo courseInfo){
        // 执行更新逻辑
        String courseNo = courseInfo.getCourseNo();
        String courseName = courseInfo.getCourseName();
        char status = courseInfo.getStatus(); // 确保 status 是 char 类型
        String semster = courseInfo.getSemster();

        // 编写更新课程信息sql
        String sql = "update Course set courseName = ?, status = ?, semster = ? where courseNo = ?";
        // 执行数据库更新
        boolean update = DatabaseUtil.update(sql, courseName, String.valueOf(status), semster, courseNo);
        if (update) System.out.println("更新成功");
        else System.out.println("更新失败");
    }

    // 直接删除
    public static void deleteCourseInfo(String courseNo) {
        //执行删除逻辑
        // 1. 编写根据教师id进行删除sql
        String sql = "delete from Course where courseNo = ?";
        // 2. 执行删除sql
        boolean delete = DatabaseUtil.delete(sql, courseNo);
        // 判断是删除成功
        if (delete) System.out.println("删除成功");
        else System.out.println("删除失败");
    }

    public static void alterCourseStatus(String courseNo){
        // 更改指定老师的status属性为0
        String sql = "update course set status = 0 where courseNo = ?";
        boolean update = DatabaseUtil.update(sql, courseNo);
        if (update) System.out.println("更新成功");
        else System.out.println("更新失败");
    }
}