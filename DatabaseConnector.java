package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnector class is responsible for establishing a connection to the MySQL database.
 * It encapsulates the database connection details and provides a method to get a connection object.
 *
 * <p>This class uses the JDBC API to connect to the MySQL database.</p>
 *
 * <p>Database URL: jdbc:mysql://localhost:3306/library_db</p>
 * <p>Database User: root</p>
 * <p>Database Password: Mypassword123</p>
 *
 * @author Jennifer Lantigua
 * @version 1.0
 * @since November 24, 2024
 */
public class DatabaseConnector {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Mypassword123";

    /**
     * Establishes a connection to the database.
     *
     * <p>This method uses JDBC to connect to the MySQL database with the provided database URL,
     * username, and password. It throws a {@link SQLException} if the connection fails.</p>
     *
     * @return a {@link Connection} object representing the database connection
     * @throws SQLException if a database access error occurs or the URL is incorrect
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}