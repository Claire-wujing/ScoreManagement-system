package pkg17.ScoreManagement.dao;

import pkg17.ScoreManagement.model.ClazzInfo;
import pkg17.ScoreManagement.model.StudentInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClazzInfoDao {
	
	/*
	 * 获取所有班级信息，支持模糊查询
	 */
	public static List<ClazzInfo> getClazzList(String clazzNo) {
		//执行数据查询逻辑,查询条件为空，则查询所有数据，否则执行模糊匹配查询
		ResultSet rSet = null;
		ResultSet rSet2 = null;

		//查询数据 TODO 课程人数查询
		String sql = "select * from clazz where status = 1"; // 根据课程号查询课程表中所有的课程信息
		String sql2 = "select count(*) studentCount from student "; // 根据课程号查询学生表中的人数
		if (!clazzNo.equals("")) { // 查询条件不为空
			String search1 = " and clazzNo like ?"; // 查询条件
			String search2 = " where clazzNo like ?"; // 查询条件2
			sql += search1;
			sql2 += search2;
			sql2 += "group by clazzNo"; // 分组查询
			rSet = DatabaseUtil.query(sql, clazzNo+"%");
			rSet2 = DatabaseUtil.query(sql2, clazzNo+"%");

		}
		else { // 查询条件为空
			sql2 += "group by clazzNo"; // 分组查询
			rSet = DatabaseUtil.query(sql);
			rSet2 = DatabaseUtil.query(sql2);
		}


		//转换为List<ClazzInfo>集合
		List<ClazzInfo> list = new ArrayList<ClazzInfo>();
		try {
			while (rSet.next()) {
				//将获取每列数据，构建学生对象，并给对象赋值，
				ClazzInfo clazzInfo = new ClazzInfo();
				clazzInfo.setStatus(rSet.getString("status"));
				clazzInfo.setClazzNo(rSet.getString("clazzNo"));
				if (!rSet2.next()) clazzInfo.setStudentCount("0"); // 如果班级中没有学生，学生人数为0
				else clazzInfo.setStudentCount(rSet2.getString("studentCount"));
				//添加到集合
				list.add(clazzInfo);
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
	 * 查找单个班级信息
	 * 
	 * @param clazzNo
	 * @return
	 */
	public static ClazzInfo getClazzInfo(String clazzNo) {
		ClazzInfo clazzInfo = null;
		// sql根据studentId查询单个学生信息
		String sql = "select * from clazz where clazzNo = ?";
		//执行查询逻辑逻辑
		ResultSet rSet = DatabaseUtil.query(sql, clazzNo);

        try {
			while (rSet.next()){
				clazzInfo = new ClazzInfo();
				clazzInfo.setStatus(rSet.getString("status"));
				clazzInfo.setClazzNo(rSet.getString("clazzNo"));
			}

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

		return clazzInfo;
	}

	/**
	 * 录入班级信息
	 * 
	 * @param clazzInfo
	 */
	public static void saveClazzInfo(ClazzInfo clazzInfo){
		//执行保存逻辑
		// 1. 取出形参studentInfo中的各种属性
		String clazzNo = clazzInfo.getClazzNo();

		String status = clazzInfo.getStatus();
		// 2. 编写插入班级信息sql
		String sql = "insert into clazz values(?,?)";
		// 3. 执行数据库插入
		boolean insert = DatabaseUtil.insert(sql, clazzNo, status);
		// 判断是否插入成功
		if (insert) System.out.println("插入成功");
		else System.out.println("插入失败");

	}
	
	/**
	 * 更新班级信息信息
	 * 
	 * @param clazzInfo
	 */
	public static void udpateClazzInfo(ClazzInfo clazzInfo){
		//执行更新逻辑
		// 1. 取出形参studentInfo中的各种属性
		String status = clazzInfo.getStatus();
		String clazzNo = clazzInfo.getClazzNo();

		// 2. 编写更新学生信息sql
		String sql = "update clazz set status = ? where clazzNo = ?";
		// 3. 执行数据库插入
		boolean update = DatabaseUtil.update(sql, status, clazzNo);
		// 判断是否更新成功
		if (update) System.out.println("更新成功");
		else System.out.println("更新失败");
	}

	// 更改学生状态
	public static void alterClazzStatus(String clazzNo){
		// 更改指定学生的status属性为0
		String sql = "update clazz set status = 0 where clazzNo = ?";
		boolean update = DatabaseUtil.update(sql, clazzNo);
		if (update) System.out.println("更新成功");
		else System.out.println("更新失败");
	}
}