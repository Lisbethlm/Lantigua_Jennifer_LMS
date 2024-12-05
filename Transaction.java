package com.example;

import java.time.LocalDateTime;

/**
 * Represents a transaction involving a book in the Library Management System (LMS).
 * <p>
 * A transaction tracks actions like borrowing or returning a book, including the user who performed the action,
 * the book involved, and the date and time of the transaction.
 * </p>
 *
 * <p>
 * Transaction types:
 * - **borrow**: When a user borrows a book.
 * - **return**: When a user returns a borrowed book.
 * </p>
 */
class Transaction {
    private String transactionID;  // Unique identifier for the transaction
    private String userID;         // ID of the user who initiated the transaction
    private String bookID;         // ID of the book involved in the transaction
    private String transactionType; // Type of transaction (borrow or return)
    private LocalDateTime date;     // Date and time when the transaction took place

    /**
     * Constructs a new Transaction object.
     *
     * @param transactionID  the unique identifier for the transaction
     * @param userID         the ID of the user initiating the transaction
     * @param bookID         the ID of the book involved in the transaction
     * @param transactionType the type of transaction (borrow or return)
     */
    public Transaction(String transactionID, String userID, String bookID, String transactionType) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.bookID = bookID;
        this.transactionType = transactionType;
        this.date = LocalDateTime.now(); // Current date and time
    }

    /**
     * Gets the type of the transaction.
     *
     * @return the transaction type (either "borrow" or "return")
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Gets the date and time when the transaction occurred.
     *
     * @return the date and time of the transaction
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Gets the unique transaction ID.
     *
     * @return the transaction ID
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Gets the ID of the user who performed the transaction.
     *
     * @return the user ID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Gets the ID of the book involved in the transaction.
     *
     * @return the book ID
     */
    public String getBookID() {
        return bookID;
    }
}