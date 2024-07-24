package com.example.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class GetCustomerListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = (String) request.getSession().getAttribute("authToken");

        if (token == null) {
            response.sendRedirect("login.jsp");
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

                // Store the customers in the database
                storeCustomersInDatabase(customers);

                // Output the response
                response.setContentType("application/json");
                response.getWriter().write(jsonArray.toString());
            } else {
                response.sendRedirect("error.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void storeCustomersInDatabase(List<Customer> customers) throws Exception {
        String sql = "INSERT INTO customers (uuid, first_name, last_name, street, address, city, state, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Customer customer : customers) {
                stmt.setString(1, customer.getUuid());
                stmt.setString(2, customer.getFirstName());
                stmt.setString(3, customer.getLastName());
                stmt.setString(4, customer.getStreet());
                stmt.setString(5, customer.getAddress());
                stmt.setString(6, customer.getCity());
                stmt.setString(7, customer.getState());
                stmt.setString(8, customer.getEmail());
                stmt.setString(9, customer.getPhone());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
}
