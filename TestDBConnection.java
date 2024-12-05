package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** This class is a simple test program to check the connection to the MySQL database.
 * It attempts to connect to the "library_db" database using the provided credentials
 * and prints a success or failure message to the console.
 */



public class TestDBConnection {

    /**
     * The main method that attempts to establish a connection to the MySQL database.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Attempting to connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "Mypassword123");
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            // Handling connection failure
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}