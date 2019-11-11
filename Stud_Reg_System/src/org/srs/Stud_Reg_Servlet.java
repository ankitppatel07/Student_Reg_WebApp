package org.srs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

public class Stud_Reg_Servlet extends HttpServlet {
	String sql;
	String url = "jdbc:mysql://localhost:3306/srs_db?autoReconnect=true&useSSL=false";

	String username = "root";
	String password = "root";
	Statement stmt;
	PreparedStatement pstmt;
	Connection con;
	ResultSet rs;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);

			String value;
			value = request.getParameter("call_value");

			if (value.equals("show_courses")) {
				rs = show_courses();
				request.setAttribute("Result", rs);
				request.setAttribute("func_call", "show_courses");
				RequestDispatcher rd = request.getRequestDispatcher("r_home.jsp");
				rd.forward(request, response);
			} 
			else if (value.contentEquals("profile_info")) {
				String p1 = request.getParameter("b_no").toString();
				rs = profile_info(p1);

				request.setAttribute("Result", rs);
				request.setAttribute("func_call", "profile_info");
				RequestDispatcher rd = request.getRequestDispatcher("r_profile.jsp");
				rd.forward(request, response);
			}
			else if (value.contentEquals("find_ta")) {
				String p1 = request.getParameter("classid").toString();
				rs = find_ta(p1);
				request.setAttribute("Result", rs);
				request.setAttribute("func_call", "find_ta");
				RequestDispatcher rd = request.getRequestDispatcher("r_class_info.jsp");
				rd.forward(request, response);
			} else if (value.contentEquals("find_prereq")) {
				String p1 = request.getParameter("dept_code").toString();
				int p2 = Integer.parseInt(request.getParameter("course_no"));
				rs = find_prereq(p1, p2);

				request.setAttribute("Result", rs);
				request.setAttribute("func_call", "find_prereq");
				RequestDispatcher rd = request.getRequestDispatcher("r_course_req.jsp");
				rd.forward(request, response);
			} else if (value.contentEquals("enroll_stud")) {
				String p1 = request.getParameter("b_no").toString();
				String p2 = request.getParameter("classid").toString();
				String output = enroll_stud(p1, p2);

				request.setAttribute("Result", output);
				request.setAttribute("func_call", "enroll_stud");
				RequestDispatcher rd = request.getRequestDispatcher("r_enroll.jsp");
				rd.forward(request, response);
			} else if (value.contentEquals("drop_stud")) {
				String p1 = request.getParameter("b_no").toString();
				String p2 = request.getParameter("classid").toString();
				System.out.println("call from drop_stud");
				String output = drop_stud(p1, p2);
				request.setAttribute("Result", output);
				request.setAttribute("func_call", "drop_stud");
				RequestDispatcher rd = request.getRequestDispatcher("r_disenroll.jsp");
				rd.forward(request, response);
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ResultSet show_courses() throws IOException, SQLException {
		sql = "SELECT dept_code, course_no, title FROM courses";
		stmt = con.createStatement();
		return stmt.executeQuery(sql);
	}

	
	public ResultSet profile_info(String p1) throws IOException, SQLException {
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
	
	public ResultSet find_ta(String p1) throws IOException, SQLException {
		sql = "SELECT count(*) FROM classes WHERE classid=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				return null;
			} else {
				break;
			}
		}

		sql = "SELECT count(*) FROM students s, classes c	WHERE c.classid = ? AND s.B_no = c.ta_B_no";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				return null;
			} else {
				break;
			}
		}

		sql = "SELECT c.ta_B_no, s.first_name, s.last_name FROM students s, classes c "
				+ "WHERE c.classid = ? AND s.B_no = c.ta_B_no ;";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();
		return rs;
	}

	public ResultSet find_prereq(String p1, int p2) throws IOException, SQLException {
		sql = "SELECT count(*) FROM prerequisites WHERE dept_code = ? AND course_no = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		pstmt.setInt(2, p2);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getInt(1) == 0) {
				return null;
			} else {
				break;
			}
		}

		sql = "SELECT * FROM prerequisites WHERE dept_code = ? AND course_no = ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, p1);
		pstmt.setInt(2, p2);
		rs = pstmt.executeQuery();
		return rs;
	}

	public String enroll_stud(String p1, String p2) throws IOException, SQLException {
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

		return "Student Enrollment Successful";
	}

	public String drop_stud(String p1, String p2) throws IOException, SQLException {
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
