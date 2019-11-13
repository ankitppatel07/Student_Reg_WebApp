package org.srs;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * @author Ankit's Omen
 */

public class Stud_Reg_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String sql;
	Statement stmt;
	PreparedStatement pstmt;
	Connection con;
	ResultSet rs;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		DBConnection dbc = new DBConnection();
		try {
			con = dbc.db_connect();	
			String value;
			value = request.getParameter("call_value");

			Student_Operations stud_op = new Student_Operations();
			General_Operations gen_op = new General_Operations();
			
			if (value.equals("show_courses")) {
				System.out.println("aaa");
				rs = gen_op.show_courses(con);
				request.setAttribute("Result", rs);
				request.setAttribute("func_call", "show_courses");
				RequestDispatcher rd = request.getRequestDispatcher("r_home.jsp");
				rd.forward(request, response);
			} 
			else if (value.contentEquals("profile_info")) {
				String p1 = request.getParameter("b_no").toString();
				rs = stud_op.profile_info(con, p1);

				request.setAttribute("Result", rs);
				request.setAttribute("func_call", "profile_info");
				RequestDispatcher rd = request.getRequestDispatcher("r_profile.jsp");
				rd.forward(request, response);
			}
			else if (value.contentEquals("find_ta")) {
				String p1 = request.getParameter("classid").toString();
				rs = gen_op.find_ta(con, p1);
				request.setAttribute("Result", rs);
				request.setAttribute("func_call", "find_ta");
				RequestDispatcher rd = request.getRequestDispatcher("r_class_info.jsp");
				rd.forward(request, response);
			} else if (value.contentEquals("find_prereq")) {
				String p1 = request.getParameter("dept_code").toString();
				int p2 = Integer.parseInt(request.getParameter("course_no"));
				rs = gen_op.find_prereq(con, p1, p2);

				request.setAttribute("Result", rs);
				request.setAttribute("func_call", "find_prereq");
				RequestDispatcher rd = request.getRequestDispatcher("r_course_req.jsp");
				rd.forward(request, response);
			} else if (value.contentEquals("enroll_stud")) {
				String p1 = request.getParameter("b_no").toString();
				String p2 = request.getParameter("classid").toString();
				String output = stud_op.enroll_stud(con, p1, p2);

				request.setAttribute("Result", output);
				request.setAttribute("func_call", "enroll_stud");
				RequestDispatcher rd = request.getRequestDispatcher("r_enroll.jsp");
				rd.forward(request, response);
			} else if (value.contentEquals("drop_stud")) {
				String p1 = request.getParameter("b_no").toString();
				String p2 = request.getParameter("classid").toString();
				System.out.println("call from drop_stud");
				String output = stud_op.drop_stud(con, p1, p2);
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
}
