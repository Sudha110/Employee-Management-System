package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Customer;
import com.example.model.User;
import com.example.util.DbUtil;

public class UserDAO {
	 private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers";
	    private static final String SORT_CUSTOMERS = "SELECT * FROM customers ORDER BY ";

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DbUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Store the plaintext password
            stmt.executeUpdate();
        }
    }

    public boolean validateUser(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (Connection conn = DbUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return password.equals(storedPassword); // Compare plaintext passwords
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Customer> getSortedCustomers(String sort) {
        List<Customer> customers = new ArrayList<>();
        String sql = SELECT_ALL_CUSTOMERS;
        if (sort != null && !sort.isEmpty()) {
            sql = SORT_CUSTOMERS + sort;
        }

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
            	  Customer customer = new Customer();
                String id = rs.getString("uuid");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String street = rs.getString("street");
                String address = rs.getString("address");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                customer.setAddress(address);
                customer.setCity(city);
                customer.setEmail(email);
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setState(state);
                customer.setStreet(street);
                customer.setUuid(id);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }


}
