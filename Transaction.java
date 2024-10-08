import java.time.LocalDateTime;

/**
 * Jennifer Lantigua
 * Course: CEN 3024C
 * Date: October 4, 2024
 * Class Name: Transaction
 * This class represents a transaction of a book, including borrowing and returning activities.
 */
class Transaction {
    private String transactionID;
    private String userID;
    private String bookID;
    private String transactionType;
    private LocalDateTime date;

    public Transaction(String transactionID, String userID, String bookID, String transactionType) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.bookID = bookID;
        this.transactionType = transactionType;
        this.date = LocalDateTime.now(); // Current date and time
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getUserID() {
        return userID;
    }

    public String getBookID() {
        return bookID;
    }
}
