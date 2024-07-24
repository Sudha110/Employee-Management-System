package com.example.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.example.model.User;
import com.example.util.DbUtil;
import com.example.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            User credentials = mapper.readValue(request.getInputStream(), User.class);

            if (verifyCredentials(credentials)) {
                String token = JWTUtil.createToken(credentials.getUsername());
                response.setContentType("application/json");
                response.getWriter().write("{\"token\":\"" + token + "\"}");
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
            }
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        }
    }

    private boolean verifyCredentials(User credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
       
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "SELECT password FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String storedHash = rs.getString("password");
                       
                      if(storedHash.equals(password)) {
                    	  return true;
                      }
                        
                    } else {
                        System.out.println("Username not found: " + username);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
