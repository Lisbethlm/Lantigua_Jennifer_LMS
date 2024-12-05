package com.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class TestDBConnection {
        public static void main(String[] args) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "Mypassword123");
                System.out.println("Connection successful!");
            } catch (SQLException e) {
                System.err.println("Connection failed: " + e.getMessage());
            }
        }
    }
