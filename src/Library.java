package com.example;
import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Your Name: [Your Name]
 * Course: [Your Course]
 * Date: [Date]
 * Class Name: Library
 * This class manages a collection of books and transactions for borrowing and returning books.
 */
class Library {
    private List<Book> books;
    private List<Transaction> transactions;

    public Library() {
        this.books = new ArrayList<>();
        this.transactions = new ArrayList<>();
        // Adding some sample books
        books.add(new Book(UUID.randomUUID().toString(), "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", true));
        books.add(new Book(UUID.randomUUID().toString(), "1984", "George Orwell", "Dystopian", true));
        books.add(new Book(UUID.randomUUID().toString(), "Moby Dick", "Herman Melville", "Adventure", true));
    }

    public void loadBooksFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(",");
                if (bookData.length == 4) {
                    String title = bookData[0].trim();
                    String author = bookData[1].trim();
                    String genre = bookData[2].trim();
                    boolean availability = Boolean.parseBoolean(bookData[3].trim());
                    addBook(title, author, genre, availability);
                } else {
                    System.out.println("Invalid book data: " + line);
                }
            }
            System.out.println("Books loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
    public void addBook(String title, String author, String genre, boolean availability) {
        books.add(new Book(UUID.randomUUID().toString(), title, author, genre, availability));
    }

    public void deleteBook(String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    public void deleteBookByID(String bookID) {
        books.removeIf(book -> book.getBookID().equals(bookID));
    }

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
                return; // Exit after updating the book
            }
        }
    }

    public Book getBookByID(String bookId) {
        for (Book book : books) {
            if (book.getBookID().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}
