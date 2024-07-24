package com.example.controller;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptTest {
    public static void main(String[] args) {
        // Replace these with the actual values
    	String enteredPassword = "Admin User";
        String storedHash = "$2a$10$EIX/8FEoBpbn/Zd4B/UD7.PGZJwHV1FDhOd5LvQxYkKoZ0bhKGG2y";
        boolean matches = BCrypt.checkpw(enteredPassword, storedHash);
        System.out.println("Password matches: " + matches);
      }
}
