package com.example.controller;

import com.example.dao.CustomerDAO;
import com.example.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SingleCustomerServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDao;

    public SingleCustomerServlet() {
        this.customerDao = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (verifyToken(request, response) == null) return;

        String id =request.getParameter("id");

        try {
            Customer customer = customerDao.getCustomerById(id);
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(customer);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving customer", e);
        }
    }
}
