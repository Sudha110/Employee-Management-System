<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, java.sql.*, com.example.dao.UserDAO, com.example.model.Customer"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Search Customers</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/view.css">
<style>
.box {
	background-color: #3498db;
	color: white;
	margin: 3px;
	padding: 5px 12px;
	border-radius: 5px;
	text-align: center;
	font-size: 1.1em;
	transition: background-color 0.3s ease, transform 0.3s ease;
	flex: 1 1 200px;
}

.search-input {
	padding: 10px;
	font-size: 1em;
	width: 80%;
	border: 1px solid #ddd;
	border-radius: 5px;
}
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
		<h1>Customer Management</h1>

		<div id="sortButtons" style="display: block;">
			<form method="get" action="sort.jsp">
				<button type="submit" class="box" name="sort" value="uuid"
					class="sort-button">Sort by ID</button>
				<button type="submit" class="box" name="sort" value="first_name"
					class="sort-button">Sort by First Name</button>
				<button type="submit" class="box" name="sort" value="last_name"
					class="sort-button">Sort by Last Name</button>
				<button type="submit" class="box" name="sort" value="city"
					class="sort-button">Sort by City</button>
				<button type="submit" class="box" name="sort" value="state"
					class="sort-button">Sort by State</button>
				<button type="submit" class="box" name="sort" value="email"
					class="sort-button">Sort by Email</button>
			</form>
		</div>

		<table id="customerTable">
			<thead>
				<tr>
					<th>ID</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Street</th>
					<th>Address</th>
					<th>City</th>
					<th>State</th>
					<th>Email</th>
					<th>Phone</th>
				</tr>
			</thead>
			<tbody>
				<%
				String sort = request.getParameter("sort");
				UserDAO userDAO = new UserDAO();
				List<Customer> customers = userDAO.getSortedCustomers(sort);
				for (Customer customer : customers) {
				%>
				<tr>
					<td><%=customer.getUuid()%></td>
					<td><%=customer.getFirstName()%></td>
					<td><%=customer.getLastName()%></td>
					<td><%=customer.getStreet()%></td>
					<td><%=customer.getAddress()%></td>
					<td><%=customer.getCity()%></td>
					<td><%=customer.getState()%></td>
					<td><%=customer.getEmail()%></td>
					<td><%=customer.getPhone()%></td>

				</tr>
				<%
				}
				%>
			</tbody>
		</table>

	</div>
	<footer>
		<p>&copy; 2024 Blog Application. All rights reserved.</p>
	</footer>
</body>
</html>
