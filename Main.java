import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize library...
        Library library = new Library();

        // Sample users...
        User librarian = new User("1", "John Doe", "librarian");
        User customer = new User("2", "Jane Smith", "customer");
        library.addUser(librarian);
        library.addUser(customer);

        // Sample books with availability status set to true
        Book book1 = new Book("101", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", true);
        Book book2 = new Book("102", "To Kill a Mockingbird", "Harper Lee", "Fiction", true);
        library.addBook(book1);
        library.addBook(book2);

        // Borrow book...
        boolean borrowed = library.borrowBook(customer.getUserId(), book1.getBookID());
        System.out.println("Borrowed 'The Great Gatsby': " + borrowed);

        // Search for books by title...
        List<Book> searchResults = library.searchBooks("title", "Mockingbird");
        for (Book book : searchResults) {
            System.out.println("Found Book: " + book.getTitle());
        }

        // Generates reports...
        library.generateReport();

        // Return book...
        boolean returned = library.returnBook(customer.getUserId(), book1.getBookID());
        System.out.println("Returned 'The Great Gatsby': " + returned);
    }
}
