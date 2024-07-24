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
<style>
header .customer-button {
	position: absolute;
	left: 20px;
	top: 10px;
	background-color: #ecf0f1;
	color: #2c3e50;
	padding: 10px 20px;
	border-radius: 5px;
	text-decoration: none;
	transition: background-color 0.3s ease;
}

header .home-customer:hover {
	background-color: #2980b9;
	color: #ecf0f1;
}
</style>
</head>
<body>
	<header>
		<h1>Employee Management System</h1>
		 <a href="${pageContext.request.contextPath}/home.jsp"
			class="customer-button">Home</a> <a
			href="${pageContext.request.contextPath}/index.jsp"
			class="home-button">Login/Register</a>
	</header>
	<div class="container">
		<h1>Welcome to Employee Management System</h1>
		<div class="links">
			<button class="box"
				onclick="window.location.href='${pageContext.request.contextPath}/customers.jsp'">Customer Details </button>
			<button class="box"
				onclick="window.location.href='${pageContext.request.contextPath}/customers.jsp'">Read or update or add Customer details</button>
				<button class="box"
				onclick="window.location.href='${pageContext.request.contextPath}/customers.jsp'">Searching</button>
				<button class="box"
				onclick="window.location.href='${pageContext.request.contextPath}/sort.jsp'">Sorting</button>
		</div>
	</div>
	<footer>
		<p>&copy; 2024 Blog Application. All rights reserved.</p>
	</footer>
</body>
</html>
