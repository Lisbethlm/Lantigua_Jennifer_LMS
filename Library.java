import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class Library {
    private List<Book> books;
    private List<Transaction> transactions;

    public Library() {
        this.books = new ArrayList<>();
        this.transactions = new ArrayList<>();

        // Adding some books
        books.add(new Book(UUID.randomUUID().toString(), "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", true));
        books.add(new Book(UUID.randomUUID().toString(), "1984", "George Orwell", "Dystopian", true));
        books.add(new Book(UUID.randomUUID().toString(), "Moby Dick", "Herman Melville", "Adventure", true));
    }

    // Book Management
    public void addBook(String title, String author, String genre, boolean availability) {
        books.add(new Book(UUID.randomUUID().toString(), title, author, genre, availability));
    }

    public void deleteBook(String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    public void updateBook(String bookId, Map<String, String> newDetails) {
        for (Book book : books) {
            if (book.getBookID().equals(bookId)) {
                if (newDetails.containsKey("title")) {
                    book.setTitle(newDetails.get("title"));
                }
                if (newDetails.containsKey("author")) {
                    book.setAuthor(newDetails.get("author"));
                }
                if (newDetails.containsKey("genre")) {
                    book.setGenre(newDetails.get("genre"));
                }
                if (newDetails.containsKey("availability")) {
                    book.setAvailable(Boolean.parseBoolean(newDetails.get("availability")));
                }
                return;
            }
        }
    }

    // Transaction Management
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

    // Search and Filter Books
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

    // Helper method
    private Book getBookByID(String bookId) {
        for (Book book : books) {
            if (book.getBookID().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    // Generate Report
    public void generateReport() {
        System.out.println("Generating Report...");
        for (Transaction transaction : transactions) {
            System.out.println("Transaction: " + transaction.getTransactionType() +
                    " | Date: " + transaction.getDate());
        }
    }

    // Getter for books
    public List<Book> getBooks() {
        return books;
    }
}
