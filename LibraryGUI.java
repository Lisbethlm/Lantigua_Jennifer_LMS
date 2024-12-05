package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;


/**This class contains a functional GUI that allows the user to navigate through books (provided by a connected database in the system).
 * You can also delete books, check-in and out, and print the catalog.
 * Jennifer Lantigua
 * Course: CEN 3024C
 * Date: November 24, 2024
 * Class Name: LibraryGUI
 */


public class LibraryGUI extends JFrame {
    private Connection connection;

    /**
     * Constructs a LibraryGUI object, initializes the database connection, and sets up the UI.
     */

    public LibraryGUI() {
        connectToDatabase();
        setupUI();
    }

    /**
     * Establishes a connection to the database.
     * If the connection fails, an error message is shown.
     */
    private void connectToDatabase() {
        try {
            connection = DatabaseConnector.getConnection();
            JOptionPane.showMessageDialog(this, "Connected to the database successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
            connection = null;
        }
    }

    /**
     * Sets up the user interface for the library management system, including buttons for various actions.
     */
    private void setupUI() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        JButton importButton = createButton("Import Books from File", e -> importBooksFromFile());
        JButton printButton = createButton("Print Database", e -> printDatabase());
        JButton removeButton = createButton("Remove Book", e -> removeBook());
        JButton checkoutButton = createButton("Check Out Book", e -> checkOutBook());
        JButton checkinButton = createButton("Check In Book", e -> checkInBook());
        JButton exitButton = createButton("Exit", e -> System.exit(0));

        buttonPanel.add(importButton);
        buttonPanel.add(printButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(checkinButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Creates a button with the given text and action listener.
     *
     * @param text           the text to display on the button
     * @param actionListener the action listener to handle the button click event
     * @return the created JButton object
     */
    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.addActionListener(actionListener);
        return button;
    }

    /**
     * Imports books from a file selected by the user and adds them to the database.
     * Displays a message when the books are successfully imported or if there is an error.
     */
    private void importBooksFromFile() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "No database connection.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] bookData = line.split(",");
                    if (bookData.length == 4) {
                        String title = bookData[0].trim();
                        String author = bookData[1].trim();
                        String genre = bookData[2].trim();
                        String status = "checked in"; // Default status
                        String barcode = java.util.UUID.randomUUID().toString(); // Unique barcode

                        String query = "INSERT INTO books (barcode, title, author, genre, status) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement stmt = connection.prepareStatement(query)) {
                            stmt.setString(1, barcode);
                            stmt.setString(2, title);
                            stmt.setString(3, author);
                            stmt.setString(4, genre);
                            stmt.setString(5, status);
                            stmt.executeUpdate();
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "Books imported successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error importing books: " + e.getMessage());
            }
        }
    }

    /**
     * Prints the database contents in a table format, displaying all books in the system.
     * <p>
     * If there is a problem fetching the data, an error message is shown.
     */
    private void printDatabase() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "No database connection.");
            return;
        }

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            Vector<String> columnNames = new Vector<>();
            columnNames.add("Barcode");
            columnNames.add("Title");
            columnNames.add("Author");
            columnNames.add("Genre");
            columnNames.add("Status");
            columnNames.add("Due Date");

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("barcode"));
                row.add(rs.getString("title"));
                row.add(rs.getString("author"));
                row.add(rs.getString("genre"));
                row.add(rs.getString("status"));
                row.add(rs.getDate("due_date"));
                data.add(row);
            }

            JTable table = new JTable(new DefaultTableModel(data, columnNames));
            adjustColumnWidths(table);

            JOptionPane.showMessageDialog(this, new JScrollPane(table), "Books in Database", JOptionPane.PLAIN_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching database: " + e.getMessage());
        }
    }

    /**
     * Adjusts the column widths of the table to improve readability.
     *
     * @param table the JTable whose columns will be resized
     */
    private void adjustColumnWidths(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Prevent automatic resizing
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Barcode
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Title
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Author
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Genre
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Status
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // Due Date
    }

    /**
     * Removes a book from the database by its barcode.
     * Prompts the user for the barcode of the book to remove, and shows an appropriate message.
     */
    private void removeBook() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "No database connection.");
            return;
        }

        String barcode = JOptionPane.showInputDialog(this, "Enter the Barcode of the Book to Remove:");
        if (barcode != null && !barcode.trim().isEmpty()) {
            String query = "DELETE FROM books WHERE barcode = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, barcode);
                int rowsAffected = stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, rowsAffected > 0 ? "Book removed successfully." : "Book not found.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error removing book: " + e.getMessage());
            }
        }
    }

    /**
     * Checks out a book from the library by updating its status to 'checked out' and setting a due date.
     *
     * @throws SQLException if there is an error while updating the book's status in the database
     */
    private void checkOutBook() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "No database connection.");
            return;
        }

        String barcode = JOptionPane.showInputDialog(this, "Enter the Barcode of the Book to Check Out:");
        if (barcode != null && !barcode.trim().isEmpty()) {
            String query = "UPDATE books SET status = 'checked out', due_date = ? WHERE barcode = ? AND status = 'checked in'";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setDate(1, Date.valueOf(java.time.LocalDate.now().plusWeeks(4)));
                stmt.setString(2, barcode);
                int rowsAffected = stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, rowsAffected > 0 ? "Book checked out successfully." : "Book not found or already checked out.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error checking out book: " + e.getMessage());
            }
        }
    }

    /**
     * Checks in a book by updating its status to 'checked in' and clearing the due date.
     *
     * @throws SQLException if there is an error while updating the book's status in the database
     */
    private void checkInBook() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "No database connection.");
            return;
        }

        String barcode = JOptionPane.showInputDialog(this, "Enter the Barcode of the Book to Check In:");
        if (barcode != null && !barcode.trim().isEmpty()) {
            String query = "UPDATE books SET status = 'checked in', due_date = NULL WHERE barcode = ? AND status = 'checked out'";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, barcode);
                int rowsAffected = stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, rowsAffected > 0 ? "Book checked in successfully." : "Book not found or already checked in.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error checking in book: " + e.getMessage());
            }
        }
    }
}
