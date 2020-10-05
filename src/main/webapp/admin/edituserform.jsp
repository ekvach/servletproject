<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
</head>
<body>
	<div class="container mt-5">
		<h2>Edit User</h2>
		<form action="edituser" method="post" name="mainform" id="mainform"
			class="form-horizontal" action="edituser" method="post">

			<div class="form-group">
				<label class="control-label col-sm-2" for="id">ID</label>
				<div class="col-sm-10">
					<input type="text" class="form-control"
						placeholder="Enter Username" name="id" value="${user.id}" readonly>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="uname">Username</label>
				<div class="col-sm-10">
					<input type="text" class="form-control"
						placeholder="Enter Username" name="uname" value="${user.login}"
						readonly>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="pass">Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control"
						placeholder="Enter Password" name="pass">
				</div>
			</div>

			<div>
				<c:if test="${passError != null}">
					<p style="color: red">Invalid value: ${passError[1]}</p>
				</c:if>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="pass">Confirm
					Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control"
						placeholder="Enter Password again" name="confpass">
				</div>
			</div>

			<div>
				<c:if test="${isPasswordTheSame == false}">
					<p style="color: red">Password is not the same</p>
				</c:if>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="email">Email</label>
				<div class="col-sm-10">
					<input type="email" class="form-control" placeholder="Enter Email"
						name="email" value="${user.email}" required>
				</div>
			</div>

			<div>
				<c:if test="${emailError != null}">
					<p style="color: red">Invalid value: ${emailError[0]};
						${emailError[1]}</p>
				</c:if>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="fname">First Name</label>
				<div class="col-sm-10">
					<input type="text" class="form-control"
						placeholder="Enter First Name" name="fname"
						value="${user.firstName}" required>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="lname">Last Name</label>
				<div class="col-sm-10">
					<input type="text" class="form-control"
						placeholder="Enter Last Name" name="lname"
						value="${user.lastName}" required>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="birthday">Birthday</label>
				<div class="col-sm-10">
					<input type="date" class="form-control"
						placeholder="Enter Birthday" name="birthday"
						value="${user.birthday}" required>
				</div>
			</div>

			<div>
				<c:if test="${birthdayError != null}">
					<p style="color: red">Invalid value: ${birthdayError[0]};
						${birthdayError[1]}</p>
				</c:if>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-2" for="role">Role</label>
				<div class="col-sm-10">
					<select name="role" class="form-control" required>
						<option value="${user.roleName}" selected>${user.roleName}</option>

						<c:forEach items="${roleList}" var="role">
							<c:if test="${role.name ne user.roleName}">
								<option>${role.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>


		</form>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10" style="display: inline">
				<div style="display: inline-block">
					<form action="edituser" method="post">
						<button type="submit" form="mainform" class="btn btn-primary"
							name="ok" value="ok">OK</button>
					</form>
				</div>
				<div style="display: inline-block">
					<form action="homepageadmin" method="post">
						<button type="submit" class="btn btn-danger" name="cancel"
							value="cancel" formnovalidate>Cancel</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>