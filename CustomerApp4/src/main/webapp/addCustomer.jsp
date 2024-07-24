<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer Management</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles.css">
<style>
body {
	margin: 100px;
}

form {
	width: 50%;
	margin: 0 auto;
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

		<button class="box" onclick="showAddCustomerForm()">Add
			Customer</button>

		<div id="addCustomerForm" style="display: none;">
			<h1>Add Customer</h1>
			<form id="newCustomerForm">
				<label for="uuid">User ID:</label> <input type="text" id="uuid"
					name="uuid" required><br>
				<br> <label for="firstName">First Name:</label> <input
					type="text" id="firstName" name="firstName" required><br>
				<br> <label for="lastName">Last Name:</label> <input
					type="text" id="lastName" name="lastName" required><br>
				<br> <label for="street">Street:</label> <input type="text"
					id="street" name="street" required><br>
				<br> <label for="address">Address:</label> <input type="text"
					id="address" name="address" required><br>
				<br> <label for="city">City:</label> <input type="text"
					id="city" name="city" required><br>
				<br> <label for="state">State:</label> <input type="text"
					id="state" name="state" required><br>
				<br> <label for="email">Email:</label> <input type="email"
					id="email" name="email" required><br>
				<br> <label for="phone">Phone:</label> <input type="text"
					id="phone" name="phone" required><br>
				<br>
				<button class="box" type="button" onclick="addCustomer()">Add
					Customer</button>
				<button class="box" type="button" onclick="hideAddCustomerForm()">Cancel</button>
			</form>
		</div>
		<table id="customerTable">
			<!-- Table rows will be populated dynamically -->
		</table>
	</div>
	<footer>
		<p>&copy; 2024 Blog Application. All rights reserved.</p>
	</footer>
	<script>
    let customersData = [];

    function fetchCustomers() {
        fetch('http://localhost:8080/CustomerApp/customers', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken'),
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log("Fetched data:", data); // Log the data to ensure it is being received
            customersData = data; // Save the fetched data for searching
            renderCustomers(data);
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });
    }

    function renderCustomers(customers) {
        const table = document.getElementById('customerTable');
        table.innerHTML = ''; // Clear existing content

        // Extract keys (column names) from JSON objects
        const columns = [];
        customers.forEach(obj => {
            Object.keys(obj).forEach(key => {
                if (!columns.includes(key)) {
                    columns.push(key);
                }
            });
        });
        columns.push('Actions');

        // Create table header row
        const headerRow = document.createElement('tr');
        columns.forEach(col => {
            const headerCell = document.createElement('th');
            headerCell.textContent = col;
            headerRow.appendChild(headerCell);
        });
        table.appendChild(headerRow);

        // Create table rows
        customers.forEach(obj => {
            const row = document.createElement('tr');
            columns.forEach(col => {
                const cell = document.createElement('td');
                const value = obj[col];
                if (col === 'Actions') {
                    console.log("Customer object:", obj);
                    console.log("Customer ID:", obj.uuid);

                    // Add Edit and Delete buttons with a unique ID for debugging
                    const editButton = document.createElement('button');
                    editButton.textContent = 'Edit';
                    editButton.setAttribute('data-id', obj.uuid);
                    editButton.onclick = function() {
                        const id = this.getAttribute('data-id');
                        console.log('Edit button clicked for customer ID:', id);
                        editCustomer(id);
                    };

                    const deleteButton = document.createElement('button');
                    deleteButton.textContent = 'Delete';
                    deleteButton.setAttribute('data-id', obj.uuid);
                    deleteButton.onclick = function() {
                        const id = this.getAttribute('data-id');
                        console.log('Delete button clicked for customer ID:', id);
                        deleteCustomer(id);
                    };

                    cell.appendChild(editButton);
                    cell.appendChild(deleteButton);
                } else if (typeof value === 'object' && value !== null) {
                    if (Array.isArray(value)) {
                        cell.textContent = value.join(', ');
                    } else {
                        cell.textContent = JSON.stringify(value);
                    }
                } else {
                    cell.textContent = value !== undefined ? value : '';
                }
                row.appendChild(cell);
            });
            table.appendChild(row);
        });
    }

    function editCustomer(id) {
        console.log("Edit customer with id:", id);
        const k=id;
        const url = "editCustomer.jsp?id="+k;
        console.log("Redirecting to URL:", url);
        window.location.href = url;
    }

    function deleteCustomer(id) {
        console.log("delete customer with id:", id);
        const k=id;
        const url = "deleteCustomer?id="+k;
        console.log("Redirecting to URL:", url);
        window.location.href = url;
    }

    function showAddCustomerForm() {
        document.getElementById('addCustomerForm').style.display = 'block';
    }

    function hideAddCustomerForm() {
        document.getElementById('addCustomerForm').style.display = 'none';
    }

    function addCustomer() {
        const form = document.getElementById('newCustomerForm');
        const formData = new FormData(form);
        console.log([...formData.entries()]);
        let jsonData = {};
        formData.forEach((value, key) => {
            jsonData[key] = value;
        });

        // Form validation
        if (!validateForm(jsonData)) {
            return;
        }

        // Convert JSON to query parameters
        const queryParams = new URLSearchParams(jsonData).toString();
        console.log(queryParams);

        fetch('http://localhost:8080/CustomerApp/addCustomer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded', // Ensure correct content type
            },
            body: queryParams
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            location.reload();
        })
        .catch(error => {
            console.error('Error adding customer:', error);
        });
    }

    function validateForm(data) {
        const { uuid, firstName, lastName, street, address, city, state, email, phone } = data;
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const phonePattern = /^[0-9]{10}$/;

        if (!uuid || !firstName || !lastName || !street || !address || !city || !state || !email || !phone) {
            alert('All fields are required.');
            return false;
        }

        if (!emailPattern.test(email)) {
            alert('Invalid email format.');
            return false;
        }

        if (!phonePattern.test(phone)) {
            alert('Invalid phone number. It should be 10 digits.');
            return false;
        }

        return true;
    }

    function searchCustomers() {
        const query = document.getElementById('searchInput').value.toLowerCase();
        console.log(customerData);
        const filteredCustomers = customersData.filter(customer => {
            return Object.values(customer).some(value => 
                String(value).toLowerCase().includes(query)
            );
        });
        renderCustomers(filteredCustomers);
    }

    document.addEventListener('DOMContentLoaded', function() {
        fetchCustomers();
    });
</script>
</body>
</html>
