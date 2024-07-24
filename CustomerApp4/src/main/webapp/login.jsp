<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles.css">
<style>
.container {
	width: 40%;
}
</style>
<script>
        function validateInput() {
            var username = document.getElementById("username").value;
            var password = document.getElementById("password").value;

            // Username validation: only alphanumeric characters and underscores, 3-15 characters long
            var usernameRegex = /^[a-zA-Z0-9_]{3,15}$/;
            if (!usernameRegex.test(username)) {
                alert("Invalid username. It should be 3-15 characters long and contain only alphanumeric characters and underscores.");
                return false;
            }

            if (password.length < 6) {
                event.preventDefault();
                alert("Password must be at least 6 characters long!");
                return;
            }

         
            return true;
        }

        function login() {
            if (!validateInput()) {
                return;
            }
            
            var username = document.getElementById("username").value;
            var password = document.getElementById("password").value;

            fetch('login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username: username, password: password })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('inavlid user or password ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                if (data.token) {
                    localStorage.setItem('jwtToken', data.token);
                    window.location.href = 'home.jsp';
                } else {
                    alert("Login failed");
                }
            })
            .catch(error => {
                console.error("Error during login:", error);
                alert("Login failed: " + error.message);
            });
        }
    </script>
</head>
<body>
	<header>
		<h1>Employee Management System</h1>
		<a href="${pageContext.request.contextPath}/index.jsp"
			class="home-button">Login/Register</a>
	</header>
	<div class="container">
		<div class="login-container">
			<h1>User Login!</h1>
			<form id="loginForm" onsubmit="login(); return false;">
				<label for="username">Username:</label> <input type="text"
					id="username" name="username" required><br>
				<br> <label for="password">Password:</label> <input
					type="password" id="password" name="password" required><br>
				<br> <input type="submit" value="Login">
			</form>
			<p class="message">
				Don't have an account? <a href="register.jsp">Sign up here</a>
			</p>
		</div>
	</div>
	<footer>
		<p>&copy; 2024 Blog Application. All rights reserved.</p>
	</footer>
</body>
</html>
