package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Mypassword123";

    // Method to get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}