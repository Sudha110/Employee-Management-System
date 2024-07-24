<%@ page import="com.example.model.Customer"%>
<%@ page import="com.example.dao.CustomerDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
// Retrieve the customer ID from the request
String customerIdStr = request.getParameter("id");
CustomerDAO customerDao = new CustomerDAO();
Customer customer = null;

if (customerIdStr != null && !customerIdStr.isEmpty()) {
	try {
		String customerId = customerIdStr; // Convert string to integer
		// Print the customer ID to the server console
		customer = customerDao.getCustomerById(customerId);

	} catch (NumberFormatException e) {
		// Handle the case where the ID is not a valid integer
		request.setAttribute("errorMesysage", "Invalid customer ID format: " + customerIdStr);
	} catch (Exception e) {
		// Handle any exceptions that occur during database access
		request.setAttribute("errorMessage", "Error retrieving customer: " + e.getMessage());
	}
}

// The customer object will be null if the ID was not valid or if there was an error
if (customer == null && request.getAttribute("errorMessage") == null) {
	// Handle the case where no customer was found
	request.setAttribute("errorMessage", "No customer found with ID: " + customerIdStr);
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Customer</title>

<link rel="stylesheet" type="text/css" href="styles.css">
<style>
body {
	margin: 100px;
}

.container {
	width: 50%;
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
		<h1>Edit Customer</h1>
		<%
		if (request.getAttribute("errorMessage") != null) {
		%>
		<p style="color: red;"><%=request.getAttribute("errorMessage")%></p>
		<%
		} else {
		%>
		<form action="updateCustomer" method="post">
			<input type="hidden" name="id"
				value="<%=customer != null ? customer.getUuid() : ""%>"> <label
				for="firstName">First Name:</label> <input type="text"
				id="firstName" name="firstName"
				value="<%=customer != null ? customer.getFirstName() : ""%>"
				required><br> <br> <label for="lastName">Last
				Name:</label> <input type="text" id="lastName" name="lastName"
				value="<%=customer != null ? customer.getLastName() : ""%>" required><br>
			<br> <label for="street">Street:</label> <input type="text"
				id="street" name="street"
				value="<%=customer != null ? customer.getStreet() : ""%>" required><br>
			<br> <label for="address">Address:</label> <input type="text"
				id="address" name="address"
				value="<%=customer != null ? customer.getAddress() : ""%>" required><br>
			<br> <label for="city">City:</label> <input type="text"
				id="city" name="city"
				value="<%=customer != null ? customer.getCity() : ""%>" required><br>
			<br> <label for="state">State:</label> <input type="text"
				id="state" name="state"
				value="<%=customer != null ? customer.getState() : ""%>" required><br>
			<br> <label for="email">Email:</label> <input type="email"
				id="email" name="email"
				value="<%=customer != null ? customer.getEmail() : ""%>" required><br>
			<br> <label for="phone">Phone:</label> <input type="text"
				id="phone" name="phone"
				value="<%=customer != null ? customer.getPhone() : ""%>" required><br>
			<br>
			<button class="box" type="submit">Update Customer</button>
		</form>
		<%
		}
		%>
	</div>
	<footer>
		<p>&copy; 2024 Blog Application. All rights reserved.</p>
	</footer>
</body>
</html>
