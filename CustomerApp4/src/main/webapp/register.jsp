<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<link rel="stylesheet" type="text/css" href="styles.css">
<style>
.container {
	width: 40%;
}
</style>
</head>
<body>
	<header>
		<h1>Employee Management System</h1>
		<a href="${pageContext.request.contextPath}/index.jsp"
			class="home-button">Login/Register</a>
	</header>
	<div class="container">
		<h1>User Registration</h1>
		<form id="registrationForm" action="register" method="post">
			<label for="username">Username:</label> <input type="text"
				id="username" name="username" required><br>
			<br> <label for="password">Password:</label> <input
				type="password" id="password" name="password" required><br>
			<br> <label for="confirmPassword">Confirm Password:</label> <input
				type="password" id="confirmPassword" name="confirmPassword" required><br>
			<br> <input type="submit" value="Register">
		</form>
	</div>
	<footer>
		<p>&copy; 2024 Blog Application. All rights reserved.</p>
	</footer>
	<script>
		document.getElementById("registrationForm").addEventListener(
				"submit",
				function(event) {
					var password = document.getElementById("password").value;
					var confirmPassword = document
							.getElementById("confirmPassword").value;

					if (password.length < 6) {
						event.preventDefault();
						alert("Password must be at least 6 characters long!");
						return;
					}

					if (password !== confirmPassword) {
						event.preventDefault();
						alert("Passwords do not match!");
					}
				});
	</script>
</body>
</html>
