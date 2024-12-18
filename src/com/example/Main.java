import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Jennifer Lantigua
 * Course: CEN 3024C
 * Date: October 4, 2024
 * Class Name: Main
 * This class contains the main method for the Library Management System (LMS), allowing users to manage books and transactions.
 */
public class Main {
    private static final Library library = new Library();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Load books from file");
            System.out.println("2. Add a book");
            System.out.println("3. Remove a book by title");
            System.out.println("4. Remove a book by ID");
            System.out.println("5. Update a book");
            System.out.println("6. Show all books");
            System.out.println("7. Check out a book");
            System.out.println("8. Check in a book");
            System.out.println("9. Exit");
            System.out.print("Enter your selection: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the file path: ");
                    String filePath = scanner.nextLine();
                    library.loadBooksFromFile(filePath);
                    break;
                case 2:
                    addSingleBook(scanner);
                    break;
                case 3:
                    System.out.print("Enter the title of the book to remove: ");
                    String titleToRemove = scanner.nextLine();
                    library.deleteBook(titleToRemove);
                    System.out.println("Book removed successfully.");
                    break;
                case 4:
                    System.out.print("Enter the ID of the book to remove: ");
                    String idToRemove = scanner.nextLine();
                    library.deleteBookByID(idToRemove);
                    System.out.println("Book removed successfully.");
                    break;
                case 5:
                    updateBook(scanner);
                    break;
                case 6:
                    printLibraryContents();
                    break;

                case 7:
                    System.out.print("Enter your user ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter the title of the book to check out: ");
                    String titleToCheckOut = scanner.nextLine();
                    Book bookToCheckOut = library.searchBookByTitle(titleToCheckOut);
                    if (bookToCheckOut != null && library.borrowBook(userId, bookToCheckOut.getBookID())) {
                        System.out.println("Book checked out successfully.");
                    } else {
                        System.out.println("Book not available or not found.");
                    }
                    break;
                case 8:
                    System.out.print("Enter your user ID: ");
                    String returnUserId = scanner.nextLine();
                    System.out.print("Enter the title of the book to check in: ");
                    String titleToCheckIn = scanner.nextLine();
                    Book bookToCheckIn = library.searchBookByTitle(titleToCheckIn);
                    if (bookToCheckIn != null && library.returnBook(returnUserId, bookToCheckIn.getBookID())) {
                        System.out.println("Book checked in successfully.");
                    } else {
                        System.out.println("Book not found or already available.");
                    }
                    break;
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void addSingleBook(Scanner scanner) {
        System.out.print("Enter the title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author: ");
        String author = scanner.nextLine();
        System.out.print("Enter the genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter availability (true/false): ");
        boolean availability = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        library.addBook(title, author, genre, availability);
        System.out.println("Book added successfully.");
    }

    private static void updateBook(Scanner scanner) {
        System.out.print("Enter the ID of the book to update: ");
        String bookId = scanner.nextLine();
        Map<String, String> newDetails = new HashMap<>();

        System.out.print("Enter new title (leave empty to skip): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.isEmpty()) newDetails.put("title", newTitle);

        System.out.print("Enter new author (leave empty to skip): ");
        String newAuthor = scanner.nextLine();
        if (!newAuthor.isEmpty()) newDetails.put("author", newAuthor);

        System.out.print("Enter new genre (leave empty to skip): ");
        String newGenre = scanner.nextLine();
        if (!newGenre.isEmpty()) newDetails.put("genre", newGenre);

        System.out.print("Enter new availability (true/false, leave empty to skip): ");
        String newAvailability = scanner.nextLine();
        if (!newAvailability.isEmpty()) newDetails.put("availability", newAvailability);

        library.updateBook(bookId, newDetails);
        System.out.println("Book updated successfully.");
    }

    private static void printLibraryContents() {
        System.out.println("Current Library Contents:");
        for (Book book : library.getBooks()) {
            System.out.println("ID: " + book.getBookID() + ", Title: " + book.getTitle() +
                    ", Author: " + book.getAuthor() + ", Genre: " + book.getGenre() +
                    ", Available: " + book.isAvailable());
        }
    }
}
