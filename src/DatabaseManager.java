import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:Mydatabase.db";

    public DatabaseManager() {
        initializeTables();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
        return conn;
    }

    private void initializeTables() {
        String sqlUser = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "first_name TEXT NOT NULL,"
                + "last_name TEXT NOT NULL,"
                + "username TEXT UNIQUE NOT NULL,"
                + "password TEXT NOT NULL,"
                + "role TEXT NOT NULL"
                + ");";

        String sqlTasks = "CREATE TABLE IF NOT EXISTS tasks ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT NOT NULL,"
                + "description TEXT,"
                + "deadline TEXT,"
                + "creator_id INTEGER,"
                + "FOREIGN KEY(creator_id) REFERENCES users(id)"
                + ");";

        String sqlParentChild = "CREATE TABLE IF NOT EXISTS parent_child ("
                + "parent_id INTEGER,"
                + "child_id INTEGER,"
                + "PRIMARY KEY (parent_id, child_id),"
                + "FOREIGN KEY(parent_id) REFERENCES users(id),"
                + "FOREIGN KEY(child_id) REFERENCES users(id)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlUser);
            stmt.execute(sqlTasks);
            stmt.execute(sqlParentChild);

        } catch (SQLException e) {
            System.out.println("Error during initialization: " + e.getMessage());
        }
    }

    // Saves a new user
    public synchronized void saveUser(String firstName, String lastName, String username, String password, String role) {
        String sql = "INSERT INTO users(first_name, last_name, username, password, role) VALUES(?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, username);
            pstmt.setString(4, PasswordUtils.hashPassword(password));
            pstmt.setString(5, role);

            pstmt.executeUpdate();
            System.out.println("User saved: " + username);

        } catch (SQLException e) {
            System.out.println("Error while saving: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Saves a task
    public synchronized void saveTask(String title, String description, String deadline, int creatorId) {
        String sql = "INSERT INTO tasks(title, description, deadline, creator_id) VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, deadline);
            pstmt.setInt(4, creatorId);

            pstmt.executeUpdate();
            System.out.println("Task saved: " + title);

        } catch (SQLException e) {
            System.out.println("Error while saving task: " + e.getMessage());
        }
    }

    // Updates the password
    public synchronized void updatePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, PasswordUtils.hashPassword(newPassword));
            pstmt.setString(2, username);

            pstmt.executeUpdate();
            System.out.println("Password updated for: " + username);

        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Deletes a user
    public synchronized void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("User deleted ID: " + id);

        } catch (SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }
    // Creates test data if DB is empty
    public synchronized void createTestData() {
        boolean dataExists = false;
        String checkSql = "SELECT count(*) FROM users";

        //Nur prüfen
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {

            if (rs.next() && rs.getInt(1) > 0) {
                dataExists = true;
            }
        } catch (SQLException e) {
            System.out.println("Check error: " + e.getMessage());
            return; // Abbrechen bei fehler
        }
        if (dataExists) {
            return;
        }

        //Jetzt ist die Bahn frei zum Speichern
        System.out.println("Creating test data...");

        saveUser("Hans", "Müller", "h.mueller", "1234", "TEACHER");
        saveUser("Max", "Mustermann", "max", "1234", "STUDENT");
        saveUser("Lisa", "Schlau", "lisa", "1234", "STUDENT");
        saveUser("Sabine", "Mustermann", "s.muster", "1234", "PARENT");

        saveTask("Math P.42", "Solve Nr 1-5", "2026-02-01", 1);

        System.out.println("--- Test Data Created Successfully ---");
    }

    // Login Logic
    public User login(String username, String passwordInput) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String inputHash = PasswordUtils.hashPassword(passwordInput);

                if (storedHash.equals(inputHash)) {
                    int id = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String roleStr = rs.getString("role");
                    Role role = Role.valueOf(roleStr);

                    User user = null;
                    switch (role) {
                        case TEACHER:
                            user = new Teacher(id, firstName, lastName, username, storedHash);
                            break;
                        case STUDENT:
                            user = new Student(id, firstName, lastName, username, storedHash);
                            break;
                        case PARENT:
                            user = new Parent(id, firstName, lastName, username, storedHash);
                            break;
                        default:
                            user = new Student(id, firstName, lastName, username, storedHash);
                    }
                    return user;
                }
            }

        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }
}