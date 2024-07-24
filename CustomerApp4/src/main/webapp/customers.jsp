<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer Management</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/view.css">
<style>

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
	<div id="searchBar" style="display: block;">
			<input type="text" class="search-input" id="searchInput"
				placeholder="Search any column data..." oninput="searchCustomers()">
		</div>
	<button class="box"
		onclick="window.location.href='${pageContext.request.contextPath}/addCustomer.jsp'">Add
		Customer</button>
	
	<button class="box" onclick="syncCustomers()">Sync Customers</button>
	
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
                            confirmDelete(id);
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
            const k = id;
            const url = "editCustomer.jsp?id=" + k;
            console.log("Redirecting to URL:", url);
            window.location.href = url;
        }

        function deleteCustomer(id) {
            console.log("delete customer with id:", id);
            const k = id;
            const url = "deleteCustomer?id=" + k;
            console.log("Redirecting to URL:", url);
            window.location.href = url;
        }

        function syncCustomers() {
            fetch('http://localhost:8080/CustomerApp/SyncCustomerServlet', {
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
                console.log("Synced data:", data); // Log the data to ensure it is being received
                customersData = data; // Save the fetched data for searching
                renderCustomers(data);
            })
            .catch(error => {
                console.error('There was a problem with your fetch operation:', error);
            });
        }
        function searchCustomers() {
            const query = document.getElementById('searchInput').value.toLowerCase();
            console.log(customersData);
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
        function confirmDelete(uuid) {
            const confirmation = confirm("Are you sure you want to delete this customer?");
            if (confirmation) {
                deleteCustomer(uuid);
            }
        }
    </script>
    
</body>
</html>
