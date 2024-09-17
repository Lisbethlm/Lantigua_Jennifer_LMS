import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Library library = new Library();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Library Management System");
            System.out.println("1. Load books from file");
            System.out.println("2. Add a book");
            System.out.println("3. Remove a book");
            System.out.println("4. Show all books");
            System.out.println("5. Exit");
            System.out.print("Enter your Selection: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the file path: ");
                    String filePath = scanner.nextLine();
                    loadBooksFromFile(filePath);
                    break;
                case 2:
                    addSingleBook(scanner);
                    break;
                case 3:
                    System.out.print("Enter the title of the book to remove: ");
                    String title = scanner.nextLine();
                    library.deleteBook(title);
                    break;
                case 4:
                    printLibraryContents();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void loadBooksFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(",");
                if (bookData.length == 4) {
                    String title = bookData[0].trim();
                    String author = bookData[1].trim();
                    String genre = bookData[2].trim();
                    boolean availability = Boolean.parseBoolean(bookData[3].trim());
                    library.addBook(title, author, genre, availability);
                } else {
                    System.out.println("Invalid book data: " + line);
                }
            }
            System.out.println("Books loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
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
        scanner.nextLine(); // consume newline

        library.addBook(title, author, genre, availability);
        System.out.println("Book added successfully.");
    }

    private static void printLibraryContents() {
        System.out.println("Current Library Contents:");
        for (Book book : library.getBooks()) {
            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() +
                    ", Genre: " + book.getGenre() + ", Available: " + book.isAvailable());
        }
    }
}
