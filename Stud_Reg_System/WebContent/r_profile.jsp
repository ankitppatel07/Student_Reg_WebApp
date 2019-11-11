<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
<style type="text/css">
@font-face {
    font-family: 'JosefinSans';
    src: url('font/JosefinSans-Regular.ttf');
}
h1{
 font-family: JosefinSans;
}

</style>
<title>Student Registration System</title>
</head>

<body class="bg-light text-white">
	<div class="bg-dark text-white">
		<header>
			<h1 class="pt-4 pb-3" style="text-align: center;">Student
				Registration</h1>
		</header>
	</div>

	<div class="container">
		<div class="row" style="height: 700px">
			<div class="card col-md-4">
				<div class="pt-4">
					<div class="nav flex-column nav-pills" id="v-pills-tab"
						role="tablist" aria-orientation="vertical">
						<a class="nav-link" id="v-pills-home-tab"
							data-toggle="pill" href="home.jsp" role="tab"
							aria-controls="v-pills-home" aria-selected="true">Home</a> 
						<a	class="nav-link active" id="v-pills-profile-tab" data-toggle="pill"
							href="profile.jsp" role="tab" aria-controls="v-pills-profile"
							aria-selected="false">Profile</a> 
						<a class="nav-link"
							id="v-pills-profile-tab" data-toggle="pill" href="class_info.jsp"
							role="tab" aria-controls="v-pills-profile" aria-selected="false">Class
							Information</a> 
						<a class="nav-link" id="v-pills-messages-tab"
							data-toggle="pill" href="course_req.jsp" role="tab"
							aria-controls="v-pills-messages" aria-selected="false">Course
							Requirements</a> 
						<a class="nav-link " id="v-pills-settings-tab"
							data-toggle="pill" href="enroll_disenroll.jsp" role="tab"
							aria-controls="v-pills-settings" aria-selected="false">Enroll in a Class</a>
						<a class="nav-link" id="v-pills-settings-tab"
							data-toggle="pill" href="disenroll.jsp" role="tab"
							aria-controls="v-pills-settings" aria-selected="false">Dis-enroll from a Class</a>
					</div>
				</div>
			</div>

			<div class="card col-md-8">
				<div class="form-group">
					<form method="get" action="Stud_Reg_Servlet">
						<h5 class="mt-4 text-dark">Enter B# to display information</h5>
						<input type="text" class="form-control form-control-sm"
							name="b_no" maxlength="4"> <br>
						<button type="submit" class="btn btn-primary" name="call_value"
							value="profile_info">Submit</button>
						<br>
					</form>
					<hr>

				</div>
				<div class="card-header">
					<h5 class="text-dark">Student Information:</h5>
				</div>
				<div class="card-body">
					<%@ page import="java.sql.*"%>
					<%
						if (request.getAttribute("func_call").equals("profile_info")) {
							ResultSet rs = (ResultSet) request.getAttribute("Result");
							if (rs == null) {
								out.println("<h5 class=\"mb-0 text-dark\">" + "Student not found!!!"
										+ "</h5><br>");
							} else {
								if (rs.next()) {
									out.println("<h7 class=\"mb-0 text-dark\">B#: " + rs.getString(1) + "</h7><br>");
									out.println("<h7 class=\"mb-0 text-dark\">First Name: " + rs.getString(2) + "</h7><br>");
									out.println("<h7 class=\"mb-0 text-dark\">Last Name: " + rs.getString(3) + "</h7><br>");
									out.println("<h7 class=\"mb-0 text-dark\">Status: " + rs.getString(4) + "</h7><br>");
									out.println("<h7 class=\"mb-0 text-dark\">GPA: " + rs.getString(5) + "</h7><br>");
									out.println("<h7 class=\"mb-0 text-dark\">Email: " + rs.getString(6) + "</h7><br>");
									out.println("<h7 class=\"mb-0 text-dark\">Birth Date: " + rs.getString(7) + "</h7><br>");
									out.println("<h7 class=\"mb-0 text-dark\">Department Name: " + rs.getString(8) + "</h7><br>");
								}
								if (rs.first()) {
									out.println("<table class=\"table\">" + "<thead class=\"thead-dark\">" + "<tr>"
											+ "	 <th scope=\"col\">Class ID</th>" + "	<th scope=\"col\">Dept Code</th>"
											+ "	<th scope=\"col\">Course No.</th>" + "	<th scope=\"col\">Section No.</th>" 
											+ "	<th scope=\"col\">Year</th>" + "	<th scope=\"col\">Semester</th>" 
											+ "	<th scope=\"col\">Grade</th>" + " </tr>" + "</thead>");
									do  {
										out.println("<tbody>" + 
												"<tr>" + 
												"<td>" + rs.getString(9) + "</td>" + 
												"<td>" + rs.getString(10) + "</td>" + 
												"<td>" + rs.getString(11) + "</td>" + 
												"<td>" + rs.getString(12) + "</td>" + 
												"<td>" + rs.getString(13) + "</td>" + 
												"<td>" + rs.getString(14) + "</td>" + 
												"<td>" + rs.getString(15) + "</td>" + 
												"</tr>" + 
												"</tbody>");
									}while(rs.next());
								}
							}
						} 
						
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>