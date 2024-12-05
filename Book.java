package com.example;

/**
 * Represents a book in the Library Management System (LMS).
 * <p>
 * A book has a unique ID, title, author, genre, and availability status.
 * </p>
 */
public class Book {
    private String bookID;
    private String title;
    private String author;
    private String genre;
    private boolean availability;

    /**
     * Constructs a new Book object with the specified details.
     *
     * @param bookID      the unique identifier for the book
     * @param title       the title of the book
     * @param author      the author of the book
     * @param genre       the genre of the book
     * @param availability the availability status of the book (true if available, false otherwise)
     */
    public Book(String bookID, String title, String author, String genre, boolean availability) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
    }

    /**
     * Retrieves the unique ID of the book.
     *
     * @return the book ID
     */
    public String getBookID() {
        return bookID;
    }

    /**
     * Retrieves the title of the book.
     *
     * @return the book title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Updates the title of the book.
     *
     * @param title the new title for the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the author of the book.
     *
     * @return the book author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Updates the author of the book.
     *
     * @param author the new author for the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Retrieves the genre of the book.
     *
     * @return the book genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Updates the genre of the book.
     *
     * @param genre the new genre for the book
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Checks whether the book is available.
     *
     * @return true if the book is available, false otherwise
     */
    public boolean isAvailable() {
        return availability;
    }

    /**
     * Updates the availability status of the book.
     *
     * @param availability the new availability status for the book (true if available, false otherwise)
     */
    public void setAvailable(boolean availability) {
        this.availability = availability;
    }
}