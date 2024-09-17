public class Book {
    private String bookID;
    private String title;
    private String author;
    private String genre;
    private boolean availability;

    public Book(String bookID, String title, String author, String genre, boolean availability) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailable(boolean availability) {
        this.availability = availability;
    }
}
