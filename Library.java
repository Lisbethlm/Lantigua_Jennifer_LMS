package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manages a collection of books in the Library Management System (LMS).
 * <p>
 * Provides functionalities to add, delete, update, and search for books.
 * It also tracks borrowing and returning transactions.
 * </p>
 */
class Library {
    private List<Book> books;
    private List<Transaction> transactions;

    /**
     * Constructs a Library object with an initial collection of sample books.
     */
    public Library() {
        this.books = new ArrayList<>();
        this.transactions = new ArrayList<>();
        // Adding some sample books
        books.add(new Book(UUID.randomUUID().toString(), "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", true));
        books.add(new Book(UUID.randomUUID().toString(), "1984", "George Orwell", "Dystopian", true));
        books.add(new Book(UUID.randomUUID().toString(), "Moby Dick", "Herman Melville", "Adventure", true));
    }

    /**
     * Loads books from a specified file.
     * <p>
     * The file should contain book details in the format:
     * <code>title,author,genre,availability</code>.
     * </p>
     *
     * @param filePath the path to the file containing book data
     */
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

    /**
     * Adds a new book to the library.
     *
     * @param title       the title of the book
     * @param author      the author of the book
     * @param genre       the genre of the book
     * @param availability the availability status of the book (true if available, false otherwise)
     */
    public void addBook(String title, String author, String genre, boolean availability) {
        books.add(new Book(UUID.randomUUID().toString(), title, author, genre, availability));
    }

    /**
     * Deletes a book from the library by its title.
     *
     * @param title the title of the book to be removed
     */
    public void deleteBook(String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    /**
     * Deletes a book from the library by its unique ID.
     *
     * @param bookID the ID of the book to be removed
     */
    public void deleteBookByID(String bookID) {
        books.removeIf(book -> book.getBookID().equals(bookID));
    }

    /**
     * Borrows a book by marking it as unavailable and logging the transaction.
     *
     * @param userId the ID of the user borrowing the book
     * @param bookId the ID of the book to borrow
     * @return true if the book was successfully borrowed, false otherwise
     */
    public boolean borrowBook(String userId, String bookId) {
        Book book = getBookByID(bookId);
        if (book != null && book.isAvailable()) {
            transactions.add(new Transaction(UUID.randomUUID().toString(), userId, bookId, "borrow"));
            book.setAvailable(false); // Marks book as borrowed
            return true;
        }
        return false;
    }

    /**
     * Returns a borrowed book by marking it as available and logging the transaction.
     *
     * @param userId the ID of the user returning the book
     * @param bookId the ID of the book to return
     * @return true if the book was successfully returned, false otherwise
     */
    public boolean returnBook(String userId, String bookId) {
        Book book = getBookByID(bookId);
        if (book != null && !book.isAvailable()) {
            transactions.add(new Transaction(UUID.randomUUID().toString(), userId, bookId, "return"));
            book.setAvailable(true); // Marks book as available again
            return true;
        }
        return false;
    }

    /**
     * Updates the details of a book by its ID.
     *
     * @param bookId     the ID of the book to update
     * @param newDetails a map containing the new details for the book
     */
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

    /**
     * Retrieves a book by its unique ID.
     *
     * @param bookId the ID of the book to retrieve
     * @return the book with the specified ID, or null if not found
     */
    public Book getBookByID(String bookId) {
        for (Book book : books) {
            if (book.getBookID().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Retrieves all books in the library.
     *
     * @return a list of books in the library
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Searches for a book by its title.
     *
     * @param title the title of the book to search for
     * @return the book with the specified title, or null if not found
     */
    public Book searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}