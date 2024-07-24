<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Customer Management System</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles.css">

</head>
<body>
	<header>
		<h1>Employee Management System</h1>
		<a
			href="${pageContext.request.contextPath}/index.jsp"
			class="home-button">Login/Register</a>
	</header>
	<div class="container">
		<h1>Welcome to Employee Management System</h1>
		<div class="links">
			<button class="box"
				onclick="window.location.href='${pageContext.request.contextPath}/login.jsp'">User
				Login</button>
			<button class="box"
				onclick="window.location.href='${pageContext.request.contextPath}/register.jsp'">User
				Register</button>
		</div>
	</div>
	<footer>
		<p>&copy; 2024 Blog Application. All rights reserved.</p>
	</footer>
</body>
</html>
