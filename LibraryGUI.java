package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LibraryGUI extends JFrame {
    private final List<Book> books;

    public LibraryGUI() {
        books = new ArrayList<>(); // In-memory storage for books

        // Frame settings
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Header
        JLabel header = new JLabel("Library Management System", SwingConstants.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Kohinoor Bangla", Font.BOLD, 24));
        mainPanel.add(header);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new GridLayout(7, 1, 10, 10));

        // Buttons
        JButton importButton = createButton("Import Books from File", e -> importBooksFromFile());
        JButton printButton = createButton("Print Database", e -> printDatabase());
        JButton removeBarcodeButton = createButton("Remove Book by Barcode", e -> removeBookByBarcode());
        JButton removeTitleButton = createButton("Remove Book by Title", e -> removeBookByTitle());
        JButton checkOutButton = createButton("Check Out Book", e -> checkOutBook());
        JButton checkInButton = createButton("Check In Book", e -> checkInBook());
        JButton exitButton = createButton("Exit", e -> System.exit(0));

        // Add buttons to panel
        buttonPanel.add(importButton);
        buttonPanel.add(printButton);
        buttonPanel.add(removeBarcodeButton);
        buttonPanel.add(removeTitleButton);
        buttonPanel.add(checkOutButton);
        buttonPanel.add(checkInButton);
        buttonPanel.add(exitButton);

        // Add components to frame
        mainPanel.add(buttonPanel);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates a styled button with a black background and black text.
     */
    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Kohinoor Bangla", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    /**
     * Imports books from a user-specified file and adds them to the in-memory list.
     */
    private void importBooksFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] bookData = line.split(",");
                    if (bookData.length == 4) {
                        String title = bookData[0].trim();
                        String author = bookData[1].trim();
                        String genre = bookData[2].trim();
                        boolean availability = Boolean.parseBoolean(bookData[3].trim());
                        books.add(new Book(title, author, genre, availability));
                    }
                }
                JOptionPane.showMessageDialog(this, "Books imported successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, StringTemplate.STR."Error reading file: \{e.getMessage()}");
            }
        }
    }

    /**
     * Prints the in-memory book list to the GUI.
     */
    private void printDatabase() {
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book).append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Kohinoor Bangla", Font.PLAIN, 18));
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Books in Library", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Removes a book by barcode.
     */
    private void removeBookByBarcode() {
        String barcode = JOptionPane.showInputDialog(this, "Enter the book's barcode:");
        boolean removed = books.removeIf(book -> book.getBarcode().equals(barcode));
        if (removed) {
            JOptionPane.showMessageDialog(this, "Book removed successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Book not found.");
        }
    }

    /**
     * Removes a book by title.
     */
    private void removeBookByTitle() {
        String title = JOptionPane.showInputDialog(this, "Enter the book's title:");
        boolean removed = books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
        if (removed) {
            JOptionPane.showMessageDialog(this, "Book removed successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Book not found.");
        }
    }

    /**
     * Checks out a book by title.
     */
    private void checkOutBook() {
        String title = JOptionPane.showInputDialog(this, "Enter the book's title to check out:");
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (!book.isAvailable()) {
                    JOptionPane.showMessageDialog(this, "Book is already checked out.");
                } else {
                    book.setAvailable(false);
                    book.setDueDate("4 weeks from now.");
                    JOptionPane.showMessageDialog(this, "Book checked out successfully.");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Book not found.");
    }

    /**
     * Checks in a book by title.
     */
    private void checkInBook() {
        String title = JOptionPane.showInputDialog(this, "Enter the book's title to check in:");
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isAvailable()) {
                    JOptionPane.showMessageDialog(this, "Book is already checked in.");
                } else {
                    book.setAvailable(true);
                    book.setDueDate(null);
                    JOptionPane.showMessageDialog(this, "Book checked in successfully.");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Book not found.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryGUI::new);
    }
}

class Book {
    private String title, author, genre, barcode, dueDate;
    private boolean available;

    public Book(String title, String author, String genre, boolean available) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = available;
        this.barcode = String.valueOf(System.nanoTime()); // Unique barcode
        this.dueDate = null;
    }

    public String getTitle() {
        return title;
    }

    public String getBarcode() {
        return barcode;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return String.format("Barcode: %s | Title: %s | Author: %s | Genre: %s | Available: %s | Due: %s",
                barcode, title, author, genre, available ? "Yes" : "No", dueDate != null ? dueDate : "N/A");
    }

}