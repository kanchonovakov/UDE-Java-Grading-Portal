import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int idCounter = 0;

    private int userId;
    private Role role; //0 - Student, 1 - Teacher, 2 - Parent, 3 - Admin
    private String firstName;
    private String lastName;
    private String username;
    private String password; // It's a bad idea to use the password like this, but I'll leave it for now.

    // Constructor
    public User(String firstName, String lastName, String username,
                String password, Role role) {
        this.userId = ++idCounter;

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password; // Set password initially

        this.role = role != null ? role : Role.ENTITY; // Default role
    }

    // Getter and Setter Methods

    public int getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    } // Username is immutable

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    } // Change password

    public String getPassword() {
        return password;
    }

    // Full Name
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    // Functions
    /*public boolean checkPasswordNew(String inputPassword) {
        if (this.password == null) {
            return inputPassword == null;
        }
        return this.password.equals(inputPassword);
    }*/
    // Password method for login is handled on the Server**

    // toString()
    @Override
    public String toString() {
        return "User [ID=" + userId +
                ", Role=" + role +
                ", Name=" + firstName +
                " " + lastName + "]";

    }

    // equals(Object obj)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId == user.userId;
    }

    // hashCode()
    @Override
    public int hashCode(){
        return Integer.hashCode(userId);
    }
}