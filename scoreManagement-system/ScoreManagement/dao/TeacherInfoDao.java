package pkg17.ScoreManagement.dao;



import pkg17.ScoreManagement.model.StudentInfo;
import pkg17.ScoreManagement.model.TeacherInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherInfoDao {

	/*
	 * 获取所有学生信息，支持模糊查询
	 */
	public static List<TeacherInfo> getTeacherList(String teacherName) {
		//执行数据查询逻辑,查询条件为空，则查询所有数据，否则执行模糊匹配查询
		ResultSet rSet = null;

		//查询数据
		String sql = "select * from teacher where status = 1";
		if (!teacherName.equals("")) {
			sql += " and name like ?";
			rSet = DatabaseUtil.query(sql, teacherName+"%");
		}
		else {
			rSet = DatabaseUtil.query(sql);
		}

		//转换为List<StudentInfo>集合
		List<TeacherInfo> list = new ArrayList<TeacherInfo>();
		try {
			while (rSet.next()) {
				//将获取每列数据，构建学生对象，并给对象赋值，
				TeacherInfo teacherInfo = new TeacherInfo();
				teacherInfo.setTeacherId(rSet.getString("teacherId"));
				teacherInfo.setName(rSet.getString("name"));
				teacherInfo.setGender(rSet.getString("gender").charAt(0));
				teacherInfo.setAge(Integer.parseInt(rSet.getString("age")));
				teacherInfo.setCourseNo(rSet.getString("courseNo"));

				//添加到集合
				list.add(teacherInfo);
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
	 * 查找单个教师信息
	 *
	 * @param teacherId
	 * @return
	 */
	public static TeacherInfo getTeacherInfo(String teacherId) {
		TeacherInfo teacherInfo = null;
		// sql根据TeacherId查询单个教师信息
		String sql = "select * from teacher where teacherId = ?";
		//执行查询逻辑逻辑
		ResultSet rSet = DatabaseUtil.query(sql, teacherId);

		try {
			while (rSet.next()){
				teacherInfo = new TeacherInfo();
				teacherInfo.setTeacherId(rSet.getString("teacherId"));
				teacherInfo.setName(rSet.getString("name"));
				teacherInfo.setGender(rSet.getString("gender").charAt(0));
				teacherInfo.setAge(Integer.parseInt(rSet.getString("age")));
				teacherInfo.setCourseNo(rSet.getString("courseNo"));
				teacherInfo.setPassword(rSet.getString("password"));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return teacherInfo;
	}

	/**
	 * 录入教师信息
	 *
	 * @param
	 */
	public static void saveTeacherInfo(TeacherInfo teacherInfo){
		//执行保存逻辑
		// 1. 取出形参teacherInfo中的各种属性
		String teacherId = teacherInfo.getTeacherId();
		String name = teacherInfo.getName();
		String gender = String.valueOf(teacherInfo.getGender());
		int age = teacherInfo.getAge();
		String password = teacherInfo.getPassword();
		String status = String.valueOf(teacherInfo.getStatus());
		String courseNo = teacherInfo.getCourseNo();
		// 2. 编写插入教师信息sql
		String sql = "insert into Teacher values(?,?,?,?,?,?,?)";
		// 3. 执行数据库插入
		boolean insert = DatabaseUtil.insert(sql, teacherId, name, age, gender,password,status, courseNo);
		// 判断是否插入成功
		if (insert) System.out.println("插入成功");
		else System.out.println("插入失败");

	}

	/**
	 * 更新教师信息信息
	 *
	 * @param
	 */
	public static void udpateTeacherInfo(TeacherInfo teacherInfo){
		//执行更新逻辑
		// 1. 取出形参TeacherInfo中的各种属性
		String teacherId = teacherInfo.getTeacherId();
		String name = teacherInfo.getName();
		String gender = String.valueOf(teacherInfo.getGender());
		int age = teacherInfo.getAge();
		String courseNo = teacherInfo.getCourseNo();
		// 2. 编写更新教师信息sql
		String sql = "update teacher set name = ?, gender = ?, age = ?, courseNo = ? where teacherId = ?";
		// 3. 执行数据库插入
		boolean update = DatabaseUtil.update(sql,name, gender, age, courseNo, teacherId);
		// 判断是否更新成功
		if (update) System.out.println("更新成功");
		else System.out.println("更新失败");
	}

	// 直接删除
	public static void deleteStudentInfo(String teacherId) {
		//执行删除逻辑
		// 1. 编写根据教师id进行删除sql
		String sql = "delete from teacher where teacherId = ?";
		// 2. 执行删除sql
		boolean delete = DatabaseUtil.delete(sql, teacherId);
		// 判断是删除成功
		if (delete) System.out.println("删除成功");
		else System.out.println("删除失败");
	}

	// 更改老师状态
	public static void alterTeacherStatus(String teacherId){
		// 更改指定老师的status属性为0
		String sql = "update teacher set status = 0 where teacherId = ?";
		boolean update = DatabaseUtil.update(sql, teacherId);
		if (update) System.out.println("更新成功");
		else System.out.println("更新失败");
	}

	public static List<TeacherInfo> getTeacherListById(String teacherId) {
		List<TeacherInfo> list = new ArrayList<>();
		TeacherInfo tInfo = null;
		// SQL 根据 studentId 查询单个学生信息
		String sql = "select * from teacher where status = 1 and teacherId = ?";
		// 执行查询逻辑
		ResultSet rSet = DatabaseUtil.query(sql, teacherId);

		try {
			while (rSet.next()) {
				tInfo = new TeacherInfo();
				tInfo.setTeacherId(rSet.getString("teacherId"));
				tInfo.setName(rSet.getString("name"));
				tInfo.setGender(rSet.getString("gender").charAt(0));
				tInfo.setAge(Integer.parseInt(rSet.getString("age")));
				tInfo.setPassword(rSet.getString("password"));

				// 添加到集合
				list.add(tInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
}