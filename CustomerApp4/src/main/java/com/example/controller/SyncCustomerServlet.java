package com.example.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.model.Customer;
import com.example.util.DbUtil;

public class SyncCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token ="dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=";

        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token is missing");
            return;
        }

        try {
            // Create the URL with the required parameters
            URL url = new URL("https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            // Check the response code
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == 200) {
                // Read the response
                StringBuilder sb = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getInputStream())) {
                    while (scanner.hasNext()) {
                        sb.append(scanner.next());
                    }
                }

                // Parse the JSON response
                JSONArray jsonArray = new JSONArray(sb.toString());
                List<Customer> customers = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Customer customer = new Customer();
                    customer.setUuid(jsonObject.getString("uuid"));
                    customer.setFirstName(jsonObject.getString("first_name"));
                    customer.setLastName(jsonObject.getString("last_name"));
                    customer.setStreet(jsonObject.getString("street"));
                    customer.setAddress(jsonObject.getString("address"));
                    customer.setCity(jsonObject.getString("city"));
                    customer.setState(jsonObject.getString("state"));
                    customer.setEmail(jsonObject.getString("email"));
                    customer.setPhone(jsonObject.getString("phone"));
                    customers.add(customer);
                }

                // Sync the customers in the database
                syncCustomersInDatabase(customers);

                // Retrieve all customers from the database to display
                List<Customer> allCustomers = getAllCustomersFromDatabase();

                response.setContentType("application/json");
                response.getWriter().write(new JSONArray(allCustomers).toString());
            } else {
                response.sendError(responseCode, "Failed to fetch customer data");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while syncing customers");
        }
    }

    private void syncCustomersInDatabase(List<Customer> customers) throws Exception {
        String selectSql = "SELECT uuid FROM customers WHERE uuid = ?";
        String updateSql = "UPDATE customers SET first_name = ?, last_name = ?, street = ?, address = ?, city = ?, state = ?, email = ?, phone = ? WHERE uuid = ?";
        String insertSql = "INSERT INTO customers (uuid, first_name, last_name, street, address, city, state, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbUtil.getConnection(); 
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            for (Customer customer : customers) {
                selectStmt.setString(1, customer.getUuid());
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) {
                    // Customer exists, update it
                    updateStmt.setString(1, customer.getFirstName());
                    updateStmt.setString(2, customer.getLastName());
                    updateStmt.setString(3, customer.getStreet());
                    updateStmt.setString(4, customer.getAddress());
                    updateStmt.setString(5, customer.getCity());
                    updateStmt.setString(6, customer.getState());
                    updateStmt.setString(7, customer.getEmail());
                    updateStmt.setString(8, customer.getPhone());
                    updateStmt.setString(9, customer.getUuid());
                    updateStmt.addBatch();
                } else {
                    // Customer does not exist, insert it
                    insertStmt.setString(1, customer.getUuid());
                    insertStmt.setString(2, customer.getFirstName());
                    insertStmt.setString(3, customer.getLastName());
                    insertStmt.setString(4, customer.getStreet());
                    insertStmt.setString(5, customer.getAddress());
                    insertStmt.setString(6, customer.getCity());
                    insertStmt.setString(7, customer.getState());
                    insertStmt.setString(8, customer.getEmail());
                    insertStmt.setString(9, customer.getPhone());
                    insertStmt.addBatch();
                }
            }

            updateStmt.executeBatch();
            insertStmt.executeBatch();
        }
    }

    private List<Customer> getAllCustomersFromDatabase() throws Exception {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DbUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setUuid(rs.getString("uuid"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setStreet(rs.getString("street"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customers.add(customer);
            }
        }

        return customers;
    }
}
