package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LibraryTest {

    private Library library;

    @BeforeEach
    public void setUp() {
        library = new Library(); // Initializes a new library before each test
    }

    @Test
    public void testAddBook() {
        library.addBook("Test Book", "Test Author", "Test Genre", true);
        Book book = library.searchBookByTitle("Test Book");
        assertNotNull(book);
        assertEquals("Test Author", book.getAuthor());
        assertTrue(book.isAvailable());
    }

    @Test
    public void testDeleteBook() {
        library.addBook("Delete Me", "Author", "Genre", true);
        library.deleteBook("Delete Me");
        assertNull(library.searchBookByTitle("Delete Me"));
    }

    @Test
    public void testDeleteBookByID() {
        Book book = library.getBooks().get(0); // Get the first book
        String bookId = book.getBookID();
        library.deleteBookByID(bookId);
        assertNull(library.getBookByID(bookId));
    }

    @Test
    public void testBorrowBook_Success() {
        Book book = library.getBooks().get(0); // Get the first book
        boolean result = library.borrowBook("user1", book.getBookID());
        assertTrue(result);
        assertFalse(book.isAvailable()); // Ensure book is marked as borrowed
    }

    @Test
    public void testBorrowBook_Failure_AlreadyBorrowed() {
        Book book = library.getBooks().get(0); // Get the first book
        library.borrowBook("user1", book.getBookID()); // First borrow attempt
        boolean result = library.borrowBook("user2", book.getBookID()); // Second borrow attempt
        assertFalse(result); // Should fail
    }

    @Test
    public void testReturnBook_Success() {
        Book book = library.getBooks().get(0); // Get the first book
        library.borrowBook("user1", book.getBookID());
        boolean result = library.returnBook("user1", book.getBookID());
        assertTrue(result);
        assertTrue(book.isAvailable()); // Ensure book is marked as available again
    }

    @Test
    public void testReturnBook_Failure_NotBorrowed() {
        Book book = library.getBooks().get(0); // Get the first book
        boolean result = library.returnBook("user1", book.getBookID());
        assertFalse(result); // Should fail since the book wasn't borrowed
    }

    @Test
    public void testUpdateBook() {
        Book book = library.getBooks().get(0); // Get the first book
        String bookId = book.getBookID();

        Map<String, String> updates = new HashMap<>();
        updates.put("title", "Updated Title");
        updates.put("availability", "false");

        library.updateBook(bookId, updates);

        Book updatedBook = library.getBookByID(bookId);
        assertEquals("Updated Title", updatedBook.getTitle());
        assertFalse(updatedBook.isAvailable());
    }

    @Test
    public void testLoadBooksFromFile_InvalidPath() {
        library.loadBooksFromFile("nonexistent_file.csv"); // Invalid path
        assertEquals(3, library.getBooks().size()); // No new books should be added
    }

    @Test
    public void testSearchBookByTitle() {
        Book book = library.searchBookByTitle("The Great Gatsby");
        assertNotNull(book);
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
    }

    @Test
    public void testGetBookByID() {
        Book book = library.getBooks().get(0);
        String bookId = book.getBookID();
        assertEquals(book, library.getBookByID(bookId));
    }

    @Test
    public void testGetBooks() {
        List<Book> books = library.getBooks();
        assertEquals(3, books.size()); // Initially, there are 3 books
    }
}
