package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao;

    public AuthServlet() {
        this.userDao = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(request.getInputStream(), User.class);

        if (userDao.validateUser(user.getUsername(), user.getPassword())) {
            String token = JWTUtil.createToken(user.getUsername());
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            response.setContentType("application/json");
            response.getWriter().write(mapper.writeValueAsString(tokenMap));
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
        }
    }
}
