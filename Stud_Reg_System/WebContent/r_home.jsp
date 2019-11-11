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
						<a class="nav-link active" id="v-pills-home-tab"
							data-toggle="pill" href="home.jsp" role="tab"
							aria-controls="v-pills-home" aria-selected="true">Home</a> 
						<a	class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
							href="profile.jsp" role="tab" aria-controls="v-pills-profile" 
							aria-selected="false">Profile</a>
						<a class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
							href="class_info.jsp" role="tab"
							aria-controls="v-pills-profile" aria-selected="false">Class
							Information</a> 
						<a class="nav-link" id="v-pills-messages-tab"
							data-toggle="pill" href="course_req.jsp" role="tab"
							aria-controls="v-pills-messages" aria-selected="false">Course
							Requirements</a> 
						<a class="nav-link" id="v-pills-settings-tab"
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
						<h5 class="mt-4 text-dark">Click to show all courses offered
							this semester</h5>
						<button type="submit" class="btn btn-primary" name="call_value"
							value="show_courses">Show Courses</button>
						<br> <br>
					</form>
				</div>
				<div class="card-header">
					<h5 class="text-dark">Below is a list of courses offered this semester:</h5>
				</div>
				<div class="card-body">
					<%@ page import="java.sql.*"%>
					<%
						if (request.getAttribute("func_call").equals("show_courses")) {
							ResultSet rs = (ResultSet) request.getAttribute("Result");
							out.println("<table class=\"table\">" + "<thead class=\"thead-dark\">" + "<tr>"
									+ "	 <th scope=\"col\">Dept Code</th>" + "	<th scope=\"col\">Course No.</th>"
									+ "	<th scope=\"col\">Course Title</th>" + " </tr>" + "</thead>");
							while (rs.next()) {
								out.println("<tbody>" + "<tr>" + "<td>" + rs.getString(1) + "</td>" + "<td>" + rs.getInt(2)
										+ "</td>" + "<td>" + rs.getString(3) + "</td>" + "</tr>" + "</tbody>");
							}
						} else if (request.getAttribute("func_call").equals("find_ta")) {
							ResultSet rs = (ResultSet) request.getAttribute("Result");
							if (rs == null) {
								out.println("<h5 class=\"mb-0 text-dark\">" + "Either the classid is invalid or the class has no TA"
										+ "</h5><br>");
							} else {
								while (rs.next()) {
									out.println("<h5 class=\"mb-0 text-dark\">" + rs.getString(1) + "  " + rs.getString(2) + "  "
											+ rs.getString(3) + "</h5><br>");
								}
							}
						} else if (request.getAttribute("func_call").equals("find_prereq")) {
							ResultSet rs = (ResultSet) request.getAttribute("Result");
							if (rs == null) {
								out.println("<h5 class=\"mb-0 text-dark\">" + "Prerequisites do not exist or invalid course"
										+ "</h5><br>");
							} else {
								while (rs.next()) {
									out.println(
											"<h5 class=\"mb-0 text-dark\">" + rs.getString(3) + "  " + rs.getInt(4) + "</h5><br>");
								}
							}
						} else if (request.getAttribute("func_call").equals("enroll_stud")) {
							String rs = (String) request.getAttribute("Result");
							out.println("<h5 class=\"mb-0 text-dark\">" + rs + "</h5><br>");
						} else if (request.getAttribute("func_call").equals("drop_stud")) {
							String rs = (String) request.getAttribute("Result");
							out.println("<h5 class=\"mb-0 text-dark\">" + rs + "</h5><br>");
						}
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>