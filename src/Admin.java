public class Admin extends User {
    //Constructor
    public Admin(String firstName, String lastName, String username,
                 String password) {
        super(firstName, lastName, username, password, Role.ADMIN);
    }

    //Functions

    //TODO* Delete user ----Seldom Used----
    public void deleteUser(int userId) {
        System.out.println("Admin " + getFullName() + " deletes user with ID: " + userId);
    }

    public void showAllUsers() {
        //TODO DatabaseManager needed*
        System.out.println("Admin " + getFullName() + " displays all registered users.");
    }

    //toString()
    @Override
    public String toString() {
        return super.toString() + " [Admin - System Access: Full]";
    }
}