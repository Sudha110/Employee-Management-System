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
import java.util.List;

public class CustomerServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDao;

    public CustomerServlet() {
        this.customerDao = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        if (verifyToken(request,response) == null) return;
        
        int page = 1;
        int pageSize = 100;
        String sortBy = "uuid";
        String sortOrder = "asc";
        String search = "";

        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }

            String pageSizeParam = request.getParameter("pageSize");
            if (pageSizeParam != null) {
                pageSize = Integer.parseInt(pageSizeParam);
            }

            String sortByParam = request.getParameter("sortBy");
            if (sortByParam != null) {
                sortBy = sortByParam;
            }

            String sortOrderParam = request.getParameter("sortOrder");
            if (sortOrderParam != null) {
                sortOrder = sortOrderParam;
            }

            String searchParam = request.getParameter("search");
            if (searchParam != null) {
                search = searchParam;
            }

            List<Customer> customers = customerDao.getAllCustomers(page, pageSize, sortBy, sortOrder, search);
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(customers);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving customers", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (verifyToken(request, response) == null) return;

        ObjectMapper mapper = new ObjectMapper();
        try {
            Customer customer = mapper.readValue(request.getInputStream(), Customer.class);
            System.out.println("Received customer: " + customer);
            customerDao.addCustomer(customer);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (verifyToken(request, response) == null) return;

        ObjectMapper mapper = new ObjectMapper();
        try {
            Customer customer = mapper.readValue(request.getInputStream(), Customer.class);
            System.out.println("Received customer: " + customer);
            customerDao.updateCustomer(customer);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        } catch (SQLException e) {
            throw new ServletException("Error updating customer", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (verifyToken(request, response) == null) return;

        try {
            String id = request.getParameter("id");
            System.out.println("Deleting customer with ID: " + id);
            customerDao.deleteCustomer(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID");
        } catch (SQLException e) {
            throw new ServletException("Error deleting customer", e);
        }
    }
}
