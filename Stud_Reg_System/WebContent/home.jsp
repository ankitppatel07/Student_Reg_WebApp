<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>
		<title>Student Registration System</title>
	</head>
	
	<body class="bg-dark text-white">
		<div>
			<header>
				<h1 class="pt-4 pb-3" style="text-align: center;">Student Registration</h1>
			</header>
		</div>
		
		<div class="container">
			<div class="row">
				<div class="card col-md-4 form-group">			
					<form method="get" action="Stud_Reg_Servlet">
						<h5 class="mt-4 text-dark">Click to show all courses offered this semester </h5>
						<button type="submit" class="btn btn-primary" name="call_value" value="show_courses">Show Courses</button>
						<br>
					</form>
					<hr>

					<form method="get" action="Stud_Reg_Servlet">
						<p class="mb-0 text-dark">Enter Classid to find TA:</p> <input type="text" class="form-control form-control-sm" name="classid" maxlength="5"> <br>
						<button type="submit" class="btn btn-primary" name="call_value" value="find_ta">Submit</button>
						<br>
					</form>
					<hr>

					<form method="get" action="Stud_Reg_Servlet">
						<h5 class="mb-0 text-dark">Find Pre-requisite course:</h5>
						<br>
						<p class="mb-0 text-dark">Enter the Department Code:</p><input type="text" class="form-control form-control-sm" name="dept_code" maxlength="2"> 
						<p class="mb-0 text-dark">Enter the Course Number:</p><input type="text" class="form-control form-control-sm" name="course_no" maxlength="4"> 
						<br>
						<button type="submit" class="btn btn-primary" name="call_value" value="find_prereq">Submit</button>
						<br>
					</form>
					<hr>

					<form method="get" action="Stud_Reg_Servlet">
						<h5 class="mb-0 text-dark">Enroll Student to a class:</h5>
						<br>
						<p class="mb-0 text-dark">Enter the B#:</p><input type="text" class="form-control form-control-sm" name="b_no" maxlength="4"> 
						<p class="mb-0 text-dark">Enter the Class ID:</p><input type="text" class="form-control form-control-sm" name="classid" maxlength="5"> 
						<br>
						<button type="submit" class="btn btn-primary" name="call_value" value="enroll_stud">Submit</button>
						<br>
					</form>
					<hr>

					<form method="get" action="Stud_Reg_Servlet">
						<h5 class="mb-0 text-dark">Drop Student from a class:</h5>
						<br>
						<p class="mb-0 text-dark">Enter the B#:</p><input type="text" class="form-control form-control-sm" name="b_no" maxlength="4"> 
						<p class="mb-0 text-dark">Enter the Class ID:</p><input type="text" class="form-control form-control-sm" name="classid" maxlength="5"> 
						<br>
						<button type="button" class="btn btn-primary" name="call_value" value="drop_stud">Submit</button>
						<br>
					</form>
					<hr>
				</div>
				
				
				<div class="card col-md-8">
					<div class="card-header">
						<h5 class="text-dark">Result:</h5>
					</div>
					<div class="card-body">
					</div>
				</div>
			</div>
		</div>

	</body>
	</html>