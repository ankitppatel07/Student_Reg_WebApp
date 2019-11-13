package org.srs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class General_Operations {
	String sql;
	Statement stmt;
	PreparedStatement pstmt;
	Connection con;
	ResultSet rs;
	
	public ResultSet show_courses(Connection con) throws IOException, SQLException {
		System.out.println("bbb");
		sql = "SELECT dept_code, course_no, title FROM courses";
		stmt = con.createStatement();
		
		return stmt.executeQuery(sql);
	}
	
	public ResultSet find_ta(Connection con, String p1) throws IOException, SQLException {
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

	public ResultSet find_prereq(Connection con, String p1, int p2) throws IOException, SQLException {
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
}
