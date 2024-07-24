package com.example.controller;

import com.example.util.JWTUtil;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public abstract class BaseServlet extends HttpServlet {

	protected Claims verifyToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String authHeader = request.getHeader("Authorization");
	   
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing or invalid");
	      
	        return null;
	    }
	    
	    String token = authHeader.substring(7);
	    try {
	        return JWTUtil.verifyToken(token);
	    } catch (Exception e) {
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
	        return null;
	    }
	}

}
