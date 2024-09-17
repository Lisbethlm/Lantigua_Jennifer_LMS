import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class Library {
    private List<User> users;
    private List<Book> books;
    private List<Transaction> transactions;

    public Library() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
        this.transactions = new ArrayList<>();

        // Adding some books
        books.add(new Book(UUID.randomUUID().toString(), "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", true));
        books.add(new Book(UUID.randomUUID().toString(), "1984", "George Orwell", "Dystopian", true));
        books.add(new Book(UUID.randomUUID().toString(), "Moby Dick", "Herman Melville", "Adventure", true));
    }

    // User Management
    public void addUser(User user) {
        users.add(user);
    }

    public void deleteUser(String userID) {
        users.removeIf(user -> user.getUserId().equals(userID));
    }

    // Book Management
    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(String bookId) {
        books.removeIf(book -> book.getBookID().equals(bookId));
    }

    public void updateBook(String bookId, Map<String, String> newDetails) {
        for (Book book : books) {
            if (book.getBookID().equals(bookId)) {
                if (newDetails.containsKey("title")) {
                    book.setTitle(newDetails.get("title"));
                }

            }
        }
    }

    // Transaction Management...
    public boolean borrowBook(String userId, String bookId) {
        Book book = getBookByID(bookId);
        if (book != null && book.isAvailable()) {
            transactions.add(new Transaction(UUID.randomUUID().toString(), userId, bookId, "borrow"));
            book.setAvailable(false); // Marks book as borrowed
            return true;
        }
        return false;
    }



    public boolean returnBook(String userId, String bookId) {
        Book book = getBookByID(bookId);
        if (book != null && !book.isAvailable()) {
            transactions.add(new Transaction(UUID.randomUUID().toString(), userId, bookId, "return"));
            book.setAvailable(true); // Marks book as available again
            return true;
        }
        return false;
    }

    public boolean renewBook(String userId, String bookId) {
        Book book = getBookByID(bookId);
        if (book != null && !book.isAvailable()) {
            transactions.add(new Transaction(UUID.randomUUID().toString(), userId, bookId, "renew"));
            return true;
        }
        return false;
    }

    // Search and Filter Books...
    public List<Book> searchBooks(String criteria, String value) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            switch (criteria) {
                case "title":
                    if (book.getTitle().toLowerCase().contains(value.toLowerCase())) results.add(book);
                    break;
                case "author":
                    if (book.getAuthor().toLowerCase().contains(value.toLowerCase())) results.add(book);
                    break;
                case "genre":
                    if (book.getGenre().toLowerCase().equals(value.toLowerCase())) results.add(book);
                    break;
                default:
                    break;
            }
        }
        return results;
    }

    // Helper method...
    private Book getBookByID(String bookId) {
        for (Book book : books) {
            if (book.getBookID().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    // Generate Report...
    public void generateReport() {
        System.out.println("Generating Report...");
        for (Transaction transaction : transactions) {
            System.out.println("Transaction: " + transaction.getTransactionType() +
                    " | Date: " + transaction.getDate());
        }
    }
}
