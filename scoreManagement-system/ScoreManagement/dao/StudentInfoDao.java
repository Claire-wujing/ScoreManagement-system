package pkg17.ScoreManagement.dao;



import pkg17.ScoreManagement.model.StudentInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentInfoDao {

	/*
	 * 获取所有学生信息，支持模糊查询
	 */
	public static List<StudentInfo> getStudentList(String studuentName) {
		//执行数据查询逻辑,查询条件为空，则查询所有数据，否则执行模糊匹配查询
		ResultSet rSet = null;

		//查询数据
		String sql = "select * from student where status = 1";
		if (!studuentName.equals("")) {
			sql += " and Name like ?";
			rSet = DatabaseUtil.query(sql, studuentName+"%");
		}
		else {
			rSet = DatabaseUtil.query(sql);
		}

		//转换为List<StudentInfo>集合
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		try {
			while (rSet.next()) {
				//将获取每列数据，构建学生对象，并给对象赋值，
				StudentInfo sInfo = new StudentInfo();
				sInfo.setStudentId(rSet.getString("studentId"));
				sInfo.setName(rSet.getString("name"));
				sInfo.setGender(rSet.getString("gender").charAt(0));
				sInfo.setAge(Integer.parseInt(rSet.getString("age")));
				sInfo.setClazzNo(rSet.getString("clazzNo"));

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
	 * 查找单个学生信息
	 *
	 * @param studentId
	 * @return
	 */
	public static StudentInfo getStudentInfo(String studentId) {
		StudentInfo sInfo = null;
		// sql根据studentId查询单个学生信息
		String sql = "select * from student where status = 1 and studentId = ?";
		//执行查询逻辑逻辑
		ResultSet rSet = DatabaseUtil.query(sql, studentId);

        try {
			while (rSet.next()){
				sInfo = new StudentInfo();
				sInfo.setStudentId(rSet.getString("studentId"));
				sInfo.setName(rSet.getString("name"));
				sInfo.setGender(rSet.getString("gender").charAt(0));
				sInfo.setAge(Integer.parseInt(rSet.getString("age")));
				sInfo.setClazzNo(rSet.getString("clazzNo"));
				sInfo.setPassword(rSet.getString("password"));
			}

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

		return sInfo;
	}

	/**
	 * 录入学生信息
	 *
	 * @param studentInfo
	 */
	public static void saveStudentInfo(StudentInfo studentInfo){
		//执行保存逻辑
		// 1. 取出形参studentInfo中的各种属性
		String studentId = studentInfo.getStudentId();
		String studentName = studentInfo.getName();
		String gender = String.valueOf(studentInfo.getGender());
		int age = studentInfo.getAge();
		String classId = studentInfo.getClazzNo();
		// 2. 编写插入学生信息sql
		String sql = "insert into student values(?,?,?,?,?,?,?)";
		// 3. 执行数据库插入
		String password = studentId;
		String status = "1";
		boolean insert = DatabaseUtil.insert(sql, studentId, studentName, age, gender,password, status, classId);
		// 判断是否插入成功
		if (insert) System.out.println("插入成功");
		else System.out.println("插入失败");

	}

	/**
	 * 更新学生信息信息
	 *
	 * @param studentInfo
	 */
	public static void udpateStudentInfo(StudentInfo studentInfo){
		//执行更新逻辑
		// 1. 取出形参studentInfo中的各种属性
		String studentId = studentInfo.getStudentId();
		String name = studentInfo.getName();
		String gender = String.valueOf(studentInfo.getGender());
		int age = studentInfo.getAge();
		String clazzNo = studentInfo.getClazzNo();
		// 2. 编写更新学生信息sql
		String sql = "update student set name = ?, gender = ?, age = ?, clazzNo = ? where studentId = ?";
		// 3. 执行数据库插入
		boolean update = DatabaseUtil.update(sql,name, gender, age, clazzNo, studentId);
		// 判断是否更新成功
		if (update) System.out.println("更新成功");
		else System.out.println("更新失败");
	}

	// 直接删除
	public static void deleteStudentInfo(String studentId) {
		//执行删除逻辑
		// 1. 编写根据学生id进行删除sql
		String sql = "delete from student where studentId = ?";
		// 2. 执行删除sql
		boolean delete = DatabaseUtil.delete(sql, studentId);
		// 判断是删除成功
		if (delete) System.out.println("删除成功");
		else System.out.println("删除失败");
	}

	// 更改学生状态
	public static void alterStudentStatus(String studentId){
		// 更改指定学生的status属性为0
		String sql = "update student set status = 0 where studentId = ?";
		boolean update = DatabaseUtil.update(sql, studentId);
		if (update) System.out.println("更新成功");
		else System.out.println("更新失败");
	}


		/**
		 * 根据学号查询单个学生信息
		 *
		 * @param studentId
		 * @return 包含单个学生信息的列表
		 */
		public static List<StudentInfo> getStudentListById(String studentId) {
			List<StudentInfo> list = new ArrayList<>();
			StudentInfo sInfo = null;
			// SQL 根据 studentId 查询单个学生信息
			String sql = "select * from student where status = 1 and studentId = ?";
			// 执行查询逻辑
			ResultSet rSet = DatabaseUtil.query(sql, studentId);

			try {
				while (rSet.next()) {
					sInfo = new StudentInfo();
					sInfo.setStudentId(rSet.getString("studentId"));
					sInfo.setName(rSet.getString("name"));
					sInfo.setGender(rSet.getString("gender").charAt(0));
					sInfo.setAge(Integer.parseInt(rSet.getString("age")));
					sInfo.setClazzNo(rSet.getString("clazzNo"));
					sInfo.setPassword(rSet.getString("password"));

					// 添加到集合
					list.add(sInfo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return list;
	}

	/**
	 * 根据姓名获取学生信息
	 *
	 * @param name 学生姓名
	 * @return 学生信息对象（若存在）或null（若不存在）
	 */
	public static StudentInfo getStudentInfoByName(String name) {
		StudentInfo studentInfo = null;
		String sql = "SELECT * FROM student WHERE name = ?";
		ResultSet rSet = pkg17.ScoreManagement.dao.DatabaseUtil.query(sql, name);

		try {
			if (rSet.next()) {
				studentInfo = new StudentInfo();
				studentInfo.setStudentId(rSet.getString("studentId"));
				studentInfo.setName(rSet.getString("name"));
				studentInfo.setAge(rSet.getInt("age"));
				studentInfo.setGender(rSet.getString("gender").charAt(0));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentInfo;
	}

}
