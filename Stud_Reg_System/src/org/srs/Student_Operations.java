package org.srs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Student_Operations {
	String sql;
	Statement stmt;
	PreparedStatement pstmt;
	Connection con;
	ResultSet rs;

	public ResultSet profile_info(Connection con, String p1) throws IOException, SQLException {
		sql = "select count(*)"+
				"from students s, enrollments e, classes c \r\n" + 
				"where s.B_no=e.B_no and e.classid=c.classid and s.B_no=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				System.out.println("nulla");
				return null;
			} else {
				break;
			}
		}
		sql = "select s.B_no, s.first_name, s.last_name, s.status, \r\n" + 
				"s.gpa, s.email, s.bdate, s.deptname, c.classid, c.dept_code, \r\n" + 
				"c.course_no, c.sect_no, c.year, c.semester, e.lgrade \r\n" + 
				"from students s, enrollments e, classes c \r\n" + 
				"where s.B_no=e.B_no and e.classid=c.classid and s.B_no=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();
	
		return rs;
	}
	
	
	public String enroll_stud(Connection con, String p1, String p2) throws IOException, SQLException {
		sql = "SELECT count(B_no) FROM Students WHERE B_no = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				return "B# is Invalid";
			} else {
				break;
			}
		}

		sql = "SELECT count(classid) FROM Classes WHERE classid = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p2);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				return "classid is invalid";
			} else {
				break;
			}
		}

		sql = "SELECT class_limit, class_size FROM Classes WHERE classid = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p2);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == rs.getInt(2)) {
				return "The class is already full";
			} else {
				break;
			}
		}

		sql = "SELECT count(*) FROM Enrollments WHERE B_no = ? AND classid = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		pstmt.setString(2, p2);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) >= 1) {
				return "The student is already in the class";
			} else {
				break;
			}
		}

		sql = "SELECT COUNT(e.classid) FROM Enrollments e, Classes c WHERE e.B_no = ? AND e.classid = c.classid";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();
		rs.next();
		int enrolled_courses = rs.getInt(1);

		if (enrolled_courses == 4) {
			return "Maximum course enrollment reached with the new enrollment";
		} else if (enrolled_courses == 5) {
			return "Students cannot be enrolled in more than five classes in the same semester";
		}

		sql = "INSERT INTO enrollments VALUES (?, ?, null)";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		pstmt.setString(2, p2);
		int i = pstmt.executeUpdate();
		System.out.println("No. of records inserted: " +i);
		return "Student Enrollment Successful";
	}

	public String drop_stud(Connection con, String p1, String p2) throws IOException, SQLException {
		System.out.println("1a");
		sql = "SELECT count(B_no) FROM Students WHERE B_no = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				return "B# is Invalid";
			} else {
				break;
			}
		}

		sql = "SELECT count(classid) FROM Classes WHERE classid = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p2);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				return "classid is invalid";
			} else {
				break;
			}
		}

		sql = "SELECT count(*) FROM Enrollments WHERE B_no = ? AND classid = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		pstmt.setString(2, p2);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				return "The student is not enrolled in the class";
			} else {
				break;
			}
		}

		sql = "SELECT count(*) FROM Enrollments \r\n" + "WHERE B_no = ? AND classid IN \r\n"
				+ "(SELECT classid FROM classes\r\n"
				+ "WHERE semester = 'Fall' AND year = 2018 AND (dept_code,course_no) IN \r\n"
				+ "(SELECT dept_code, course_no FROM prerequisites \r\n" + "WHERE (pre_dept_code,pre_course_no) IN\r\n"
				+ "(SELECT dept_code, course_no FROM classes \r\n"
				+ "WHERE semester = 'Fall' AND year = 2018 AND classid IN\r\n" + "(SELECT classid FROM Enrollments \r\n"
				+ "WHERE  B_no = ? AND classid = ?))))";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		pstmt.setString(2, p1);
		pstmt.setString(3, p2);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) > 0) {
				return "The drop is not permitted because another class the student registered uses it as a prerequisite.";
			}
		}
		sql = "DELETE FROM enrollments WHERE B_no = ? AND classid = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		pstmt.setString(2, p2);
		int i = pstmt.executeUpdate();
		System.out.println("No. of records deleted: " +i);
		sql = "SELECT count(*) FROM enrollments WHERE B_no = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				return "Student successfully dropped from the course. The student is not enrolled in any class";
			} else {
				break;
			}
		}

		sql = "SELECT count(*) FROM enrollments WHERE classid = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p2);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				return "Student successfully dropped from the course. The class now has no students";
			} else {
				break;
			}
		}
		return "Student successfully dropped from the course.";
	}
}
