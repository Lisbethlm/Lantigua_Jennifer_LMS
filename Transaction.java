import java.time.LocalDateTime;

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
