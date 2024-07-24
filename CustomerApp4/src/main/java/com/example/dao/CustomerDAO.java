package com.example.dao;

import com.example.model.Customer;
import com.example.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
	
	 public boolean addCustomer(Customer customer) {
	        String sql = "INSERT INTO customers (uuid, first_name, last_name, street, address, city, state, email, phone) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        try (Connection conn = DbUtil.getConnection(); 
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	        	stmt.setString(1, customer.getUuid());
	        	System.out.println(customer.getUuid());
	            stmt.setString(2, customer.getFirstName());
	            stmt.setString(3, customer.getLastName());
	            stmt.setString(4, customer.getStreet());
	            stmt.setString(5, customer.getAddress());
	            stmt.setString(6, customer.getCity());
	            stmt.setString(7, customer.getState());
	            stmt.setString(8, customer.getEmail());
	            stmt.setString(9, customer.getPhone());
	            int rowsAffected = stmt.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	

    public List<Customer> getAllCustomers(int page, int pageSize, String sortBy, String sortOrder, String search) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE first_name LIKE ? OR last_name LIKE ? ORDER BY " + sortBy + " " + sortOrder + " LIMIT ? OFFSET ?";
        try (Connection conn = DbUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            stmt.setInt(3, pageSize);
            stmt.setInt(4, (page - 1) * pageSize);
            try (ResultSet rs = stmt.executeQuery()) {
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
        }
        return customers;
    }

    public Customer getCustomerById(String id) throws SQLException {
        Customer customer = null;
        System.out.println("id."+id);
        String sql = "SELECT * FROM customers WHERE uuid = ?";
        try (Connection conn = DbUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setUuid(rs.getString("uuid"));
                    customer.setFirstName(rs.getString("first_name"));
                    customer.setLastName(rs.getString("last_name"));
                    customer.setStreet(rs.getString("street"));
                    customer.setAddress(rs.getString("address"));
                    customer.setCity(rs.getString("city"));
                    customer.setState(rs.getString("state"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                }
            }
        }
        return customer;
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, street = ?, address = ?, city = ?, state = ?, email = ?, phone = ? WHERE uuid = ?";
        try (Connection conn = DbUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getStreet());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getCity());
            stmt.setString(6, customer.getState());
            stmt.setString(7, customer.getEmail());
            stmt.setString(8, customer.getPhone());
            stmt.setString(9, customer.getUuid());
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("im in update");
            return rowsUpdated > 0; // Return true if at least one row was updated
        }
    }


    public boolean deleteCustomer(String id) throws SQLException {
        String sql = "DELETE FROM customers WHERE uuid = ?";
        try (Connection conn = DbUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    

}
