package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.CustomerDAO;

public class DeleteCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDao;

    public DeleteCustomerServlet() {
        this.customerDao = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("id");
        System.out.println("im in delete " + customerId);

        if (customerId != null && !customerId.isEmpty()) {
            try {
                boolean isDeleted = customerDao.deleteCustomer(customerId);
                System.out.println("deleted: " + isDeleted);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Customer ID is null or empty.");
        }

        response.sendRedirect("customers.jsp");
    }
}
