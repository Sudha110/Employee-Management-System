## Employee Management System Application

## Project Overview
This project is a CRUD (Create, Read, Update, Delete) application for managing customer data. It utilizes MySQL for the database, JSP and Servlets for the backend, and HTML/CSS/JS for the frontend. The application supports JWT authentication and includes features for adding, updating, viewing, and deleting customers. Additionally, it includes a synchronization feature to fetch and update customer data from a remote API.

## Technologies Used
- Backend: Java Servlets
- Frontend: JSP (JavaServer Pages)
- Database: MySQL

## Features
- User Authentication with JWT
- Create a customer
- Update a customer
- Get a list of customers with pagination, sorting, and searching
- Get a single customer by ID
- Delete a customer
- Sync customer data from a remote API

## Prerequisites
- IDE with support for Java EE (e.g., Eclipse)
- Java Development Kit (JDK)
- Apache Tomcat or any other Java web server
- MySQL Database
- Maven

## Project Phases

### Phase 1: Basic CRUD Functionality
- Implement user authentication using JWT.
- Develop API endpoints for creating, updating, retrieving (single and list with pagination, sorting, and searching), and deleting customers.
- Build frontend screens for:
  - Login
  - Customer List
  - Add/Edit Customer

### Phase 2: Synchronization Feature
- Add a button named "Sync" on the customer list screen.
- Implement API call to a remote server to fetch the customer list.
- Save fetched customers into the local database:
  - If a customer already exists, update their details.
  - If a customer does not exist, insert them into the database.

#### Remote API Details
- **Authenticate User:**
  - **URL:** `https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp`
  - **Method:** POST
  - **Body:**
    ```json
    {
      "login_id" : "test@sunbasedata.com",
      "password" :"Test@123"
    }
    ```
  - **Response:** Bearer token to be used in subsequent API calls.

- **Get Customer List:**
  - **URL:** `https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp`
  - **Method:** GET
  - **Parameters:** `cmd=get_customer_list`
  - **Headers:** `Authorization: Bearer {token}`
  - **Response:**
    ```json
    [
      {
        "uuid" :"tytyytytyyyy345ryeyey",
        "first_name": "Jane",
        "last_name": "Doe",
        "street": "Elvnu Street",
        "address": "H no 2",
        "city": "Delhi",
        "state": "Delhi",
        "email": "sam@gmail.com",
        "phone": "12345678"
      }
    ]
    ```

## Deliveries

### Phase 1 Deliverables
- Source code for backend and frontend implementation.
- SQL scripts to set up the database and tables.
- API documentation for CRUD operations.
- Basic frontend for login, customer list, and customer management.
- README file with setup instructions.

### Phase 2 Deliverables
- Implementation of the Sync button.
- Code to integrate with the remote API for fetching and updating customer data.
- Detailed instructions on how to use the sync feature.
- Updated README file with phase 2 details.

## Database Setup

```sql
CREATE DATABASE customer_db;

USE customer_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE customers (
    uuid VARCHAR(36) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    street VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL,
    city VARCHAR(50),
    state VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20)
);

```

## Running the Application

1. **Clone the repository:**
   ```bash
   git clone <repository_url>
   cd <repository_directory>
   ```

2. **Import the project into Eclipse:**
   - Open Eclipse IDE.
   - File -> Import -> Existing Maven Projects -> Browse to the project directory -> Finish.

3. **Configure the MySQL database:**
   - Create a MySQL database named `customer_db` and tables `users` and `customers` using the provided SQL queries.

4. **Update database configurations:**
   - Update the database connection details in the `DbUtil.java` file (or wherever the database connection is managed).

5. **Run the project:**
   - Right-click on the project in Eclipse.
   - Run As -> Run on Server -> Choose Apache Tomcat -> Finish.

6. **Access the application:**
   - Open a web browser and navigate to `http://localhost:8080/CustomerApp`.


## Project Structure

```
CustomerApp/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── controller/
│   │   │           │   ├── AddCustomerServlet.java
│   │   │           │   ├── AuthServlet.java
│   │   │           │   ├── BaseServlet.java
│   │   │           │   ├── BCryptTest.java
│   │   │           │   ├── CustomerServlet.java
│   │   │           │   ├── DeleteCustomerServlet.java
│   │   │           │   ├── GetCustomerListServlet.java
│   │   │           │   ├── LoginServlet.java
│   │   │           │   ├── RegisterServlet.java
│   │   │           │   ├── SingleCustomerServlet.java
│   │   │           │   ├── SyncCustomerServlet.java
│   │   │           │   └── UpdateCustomerServlet.java
│   │   │           ├── dao/
│   │   │           │   ├── CustomerDAO.java
│   │   │           │   └── UserDAO.java
│   │   │           ├── model/
│   │   │           │   ├── Customer.java
│   │   │           │   └── User.java
│   │   │           └── util/
│   │   │               ├── DbUtil.java
│   │   │               ├── JWTFilter.java
│   │   │               └── JWTUtil.java
│   │   └── webapp/
│   │       ├── META-INF/
│   │       ├── WEB-INF/
│   │       │   └── web.xml
│   │       ├── addCustomer.jsp
│   │       ├── customers.jsp
│   │       ├── editCustomer.jsp
│   │       ├── error.jsp
│   │       ├── home.jsp
│   │       ├── index.jsp
│   │       ├── login.jsp
│   │       ├── register.jsp
│   │       ├── sort.jsp
│   │       ├── styles.css
│   │       └── view.css
├── build/
├── target/
├── pom.xml
└── README.md

```
## API Endpoints

### Authentication
- **URL:** `/login`
- **Method:** POST
- **Body:**
  ```json
  {
    "username": "your_username",
    "password": "your_password"
  }
  ```

### Customer Management
- **Create a Customer**
  - **URL:** `/addCustomer`
  - **Method:** POST
  - **Body:** Customer object (JSON)

- **Update a Customer**
  - **URL:** `/updateCustomer`
  - **Method:** PUT
  - **Body:** Customer object (JSON)

- **Get List of Customers**
  - **URL:** `/customers`
  - **Method:** GET
  - **Parameters:** Pagination, sorting, and searching parameters

- **Get Single Customer**
  - **URL:** `/customer?id={id}`
  - **Method:** GET

- **Delete a Customer**
  - **URL:** `/deleteCustomer?id={id}`
  - **Method:** DELETE

- **Sync Customers**
  - **URL:** `/syncCustomers`
  - **Method:** GET

## Comments
- Ensure JWT tokens are stored securely and included in the Authorization header for API requests.
- Customize the CSS and HTML as needed for a better UI/UX.


## ScreenShots

Here are some screenshots of the project
## addCustomers.jsp
![addcustomer1](https://github.com/user-attachments/assets/8e5e50f4-a02b-41c3-9624-021746cbbe70)

![addCustomer4](https://github.com/user-attachments/assets/971e210f-244a-4496-b71d-d248a88c78d5)

![addCustomer3](https://github.com/user-attachments/assets/aadf7b58-bc1d-4253-9293-76d13a73637a)

## customers.jsp
![customers](https://github.com/user-attachments/assets/4b1cb5c2-e8db-4695-bb67-2cb596721b1b)

## editCustomer.jsp
![edit](https://github.com/user-attachments/assets/204ca588-e6e4-4d37-9691-f770d67a8218)

## home.jsp
![home](https://github.com/user-attachments/assets/c252d363-9740-4bd7-8165-8264488c9c5e)

## index.jsp
![index](https://github.com/user-attachments/assets/3076344a-81a0-452c-a271-1bbc515d0ec0)

## login.jsp
![login](https://github.com/user-attachments/assets/b91259de-9722-4dd2-a44e-0f55bfc0746d)

## register.jsp
![register](https://github.com/user-attachments/assets/70946d05-1968-441c-94e1-4eb0860cbff9)

## search Results
![search results](https://github.com/user-attachments/assets/9323e33f-0f7d-408d-93b3-dc6d651725f8)

## sort.jsp
![sort](https://github.com/user-attachments/assets/ebd53a93-4f15-4ce8-90f6-699f56d2fd71)

## Sample video
https://github.com/user-attachments/assets/6f79db15-d753-4ea4-85be-4e8e66d97562

## Contact
For any questions or support, please contact [gunisudharani492@gmail.com](mailto:gunisudharani492@gmail.com).
