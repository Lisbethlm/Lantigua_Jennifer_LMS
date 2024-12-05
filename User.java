package com.example;

/**
 * Represents a user in the Library Management System (LMS).
 * <p>
 * This class includes information about the user, such as their unique ID, name, role, and password.
 * It provides methods for user authentication and role-based authorization.
 * </p>
 * <p>
 * Roles:
 * - **admin**: Full access, including the ability to delete users.
 * - **librarian**: Restricted access, cannot perform certain tasks like user deletion.
 * </p>
 */
class User {
    private String userId;
    private String name;
    private String role;
    private String password;

    /**
     * Constructs a new User object.
     *
     * @param userId   the unique identifier for the user
     * @param name     the name of the user
     * @param role     the role of the user (e.g., admin, librarian, member)
     * @param password the password for the user
     */
    public User(String userId, String name, String role, String password) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.password = password;
    }

    /**
     * Gets the user's unique ID.
     *
     * @return the user's ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the user's name.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user's role.
     *
     * @return the user's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Authenticates the user based on the provided username and password.
     *
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return true if the username and password match the user's credentials, false otherwise
     */
    public boolean authenticateUser(String username, String password) {
        return this.name.equals(username) && this.password.equals(password);
    }

    /**
     * Authorizes the user to perform a specific task based on their role.
     * <p>
     * - **Admin**: Can perform all tasks.
     * - **Librarian**: Cannot delete users.
     * - **Member**: Limited functionality, typically read-only or borrowing permissions.
     * </p>
     *
     * @param task the task the user wants to perform
     * @return true if the user is authorized to perform the task, false otherwise
     */
    public boolean authorizeUser(String task) {
        if (this.role.equals("admin")) {
            return true; // Admin can perform all tasks.
        }
        if (this.role.equals("librarian")) {
            return !task.equals("deleteUser"); // Librarian cannot delete users.
        }
        return false; // Other roles are not authorized for critical tasks.
    }
}