package org.srs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.Arrays;

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
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter(); 
		try{  
			System.out.println("bbbb");
			Class.forName("com.mysql.jdbc.Driver");  
			con = DriverManager.getConnection(url, username, password);  
			
			String value;
			value = request.getParameter("call_value");
			
			if(value.equals("show_courses")) {
				rs = show_courses();
				
				while(rs.next()) {  
					out.println(rs.getString(1)+"  "+rs.getInt(2)+"  "+rs.getString(3) + "<br>"); 
				}
			}
			else if(value.contentEquals("find_ta")) {
				String p1 = request.getParameter("classid").toString();
				rs = find_ta(p1);
				if(rs==null) {
					out.println("Either the classid is invalid or the class has no TA");					
				}
				else {
					while(rs.next()) {  
						out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3) + "<br>"); 
					}
				}
			}
			else if(value.contentEquals("find_prereq")) {
				String p1 = request.getParameter("dept_code").toString();
				int p2 = Integer.parseInt(request.getParameter("course_no"));
				rs = find_prereq(p1, p2);
				
				if(rs==null) {
					out.println("Prerequisites do not exist or invalid course");					
				}
				else {
					while(rs.next()) {  
						out.println(rs.getString(3)+"  "+rs.getInt(4) + "<br>"); 
					}
				}
			}
			else if(value.contentEquals("enroll_stud")) {
				String p1 = request.getParameter("b_no").toString();
				String p2 = request.getParameter("classid");
				String output = enroll_stud(p1, p2);
				
				out.println(output + "<br>"); 										
			}
			else if(value.contentEquals("drop_stud")){
				
			}
			
			
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);  
		} 
	}
	
	
	public ResultSet show_courses() throws IOException, SQLException {
		System.out.println("aaaa");
		sql = "SELECT dept_code, course_no, title FROM courses";
		stmt=con.createStatement();
		return stmt.executeQuery(sql); 		
	}
	
	
	public ResultSet find_ta(String v_cid) throws IOException, SQLException {
		sql = "SELECT count(*) FROM classes WHERE classid=?";
		pstmt = con.prepareStatement(sql);	
		pstmt.setString(1, v_cid);	
		rs = pstmt.executeQuery(); 
		
		while (rs.next()) {
			 if(rs.getInt(1) == 0) {
				return null;
			 }
			 else {
				 break;
			 }
		}
	
		sql = "SELECT count(*) FROM students s, classes c	WHERE c.classid = ? AND s.B_no = c.ta_B_no" ;
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, v_cid);
		rs = pstmt.executeQuery(); 
		
		while (rs.next()) {
			 if(rs.getInt(1) == 0) {		 
				return null;
			 }
			 else {
				 break;
			 }
		}
		
		sql = "SELECT c.ta_B_no, s.first_name, s.last_name FROM students s, classes c "
				+ "WHERE c.classid = ? AND s.B_no = c.ta_B_no ;" ;
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, v_cid);
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
			 if(rs.getInt(1) == 0) {		 
				return null;
			 }
			 else {
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
		System.out.println("1");
		sql = "SELECT count(B_no) FROM Students WHERE B_no = ?";
		pstmt = con.prepareStatement(sql);	
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();
	
		while (rs.next()) {
			 if(rs.getInt(1) == 0) {		 
				return "B# is Invalid";
			 }
			 else {
				 break;
			 }
		}
		
		System.out.println("2");
		sql = "SELECT count(classid) FROM Classes WHERE classid = ?";
		pstmt = con.prepareStatement(sql);	
		pstmt.setString(1, p2);
		rs = pstmt.executeQuery();
	
		while (rs.next()) {
			 if(rs.getInt(1) == 0) {		 
				return "classid is invalid";
			 }
			 else {
				 break;
			 }
		}
		
		System.out.println("3");
		sql = "SELECT class_limit, class_size FROM Classes WHERE classid = ?";
		pstmt = con.prepareStatement(sql);	
		pstmt.setString(1, p2);
		rs = pstmt.executeQuery();
	
		while (rs.next()) {
			 if(rs.getInt(1) == rs.getInt(2)) {		 
				return "The class is already full";
			 }
			 else {
				 break;
			 }
		}
		
		System.out.println("4");
		sql = "SELECT count(*) FROM Enrollments WHERE B_no = ? AND classid = ?";
		pstmt = con.prepareStatement(sql);	
		pstmt.setString(1, p1);
		pstmt.setString(2, p2);
		rs = pstmt.executeQuery();
	
		while (rs.next()) {
			if(rs.getInt(1) >= 1) {		 
				return "The student is already in the class";
			 }
			 else {
				 break;
			 }
		}
		
		System.out.println("5");
		sql = "SELECT COUNT(e.classid) FROM Enrollments e, Classes c WHERE e.B_no = ? AND e.classid = c.classid";
		pstmt = con.prepareStatement(sql);	
		pstmt.setString(1, p1);
		rs = pstmt.executeQuery();
		rs.next();
		int enrolled_courses = rs.getInt(1);
		
		if(enrolled_courses==4) {
			return "Maximum course enrollment reached with the new enrollment";
		}
		else if(enrolled_courses==5) {
			return "Students cannot be enrolled in more than five classes in the same semester";			
		}
		
		System.out.println("6");
		sql = "INSERT INTO enrollments VALUES (?, ?, null)";
		pstmt = con.prepareStatement(sql);	
		pstmt.setString(1, p1);
		pstmt.setString(2, p2);
		int i = pstmt.executeUpdate();
		
		return "Student Enrollment Successful";
	}
	
	public void drop_stud() {
		
	}
	
}
