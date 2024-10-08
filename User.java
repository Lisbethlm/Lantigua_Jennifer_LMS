/**
 * Jennifer Lantigua
 * Course: CEN 3024C
 * Date: October 4, 2024
 * Class Name: User
 * This class represents a user in the LMS. It includes user information like user ID, name, role, and password.
 * This class also provides methods for user authentication and authorization based on their roles.
 */

class User {
    private String userId;
    private String name;
    private String role;
    private String password;

    public User(String userId, String name, String role, String password) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    // Authentication...
    public boolean authenticateUser(String username, String password) {
        return this.name.equals(username) && this.password.equals(password);
    }

    // Authorization...
    public boolean authorizeUser(String task) {
        if (this.role.equals("admin") || (this.role.equals("librarian") && !task.equals("deleteUser"))) {
            return true;
        }
        return false;
    }
}
