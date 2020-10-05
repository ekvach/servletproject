<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="myprefix" uri="../WEB-INF/taglibrary.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
	integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="container-fluid m-lg-2" align="right">
		<form action="logout" method="post">
			<div>
				Admin ${sessionScope.user.firstName}
				<button type="submit" class="btn btn-link">(Logout)</button>
			</div>
		</form>

	</div>

	<div class="container-fluid m-lg-2" align="left">
		<p>
			<a href="addnewuserform">Add new User</a>
		</p>
	</div>

	<%
		int rowNumber = 1;
	%>

	<div class="container-fluid">
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">Login</th>
						<th scope="col">FirstName</th>
						<th scope="col">LastName</th>
						<th scope="col">Age</th>
						<th scope="col">Role</th>
						<th scope="col">Actions</th>
					</tr>
				</thead>
				<tbody>
					<myprefix:userList userList="${userList}">
						<tr>
							<th><%=rowNumber++%></th>
							<td>${login}</td>
							<td>${firstName}</td>
							<td>${lastName}</td>
							<td>${age}</td>
							<td>${roleName}</td>
							<td><a href="edituserform?id=${userId}">Edit</a> <a
								href="deleteuser?id=${userId}"
								onclick="return confirm('Are yousure?')">Delete</a></td>
						</tr>
					</myprefix:userList>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>