import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int idCounter = 0; // Simple counter for new objects
    private int userId;
    private Role role;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    // Constructor for creating new users
    public User(String firstName, String lastName, String username, String password, Role role) {
        //In a real DB app the DB assigns the ID,but we keep this for object logic
        this.userId = ++idCounter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role != null ? role : Role.STUDENT;
    }

    // Constructor for loading from DB
    public User(int id, String firstName, String lastName, String username, Role role) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role != null ? role : Role.STUDENT;
    }

    public int getId() { return userId; } // CHANGED TO INT

    public Role getRole() { return role; }
    public String getUsername() { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPassword() { return password; }

    public String getFullName() { return this.firstName + " " + this.lastName; }

    @Override
    public String toString() {
        return "User [ID=" + userId + ", Role=" + role + ", Name=" + firstName + " " + lastName + "]";
    }
}