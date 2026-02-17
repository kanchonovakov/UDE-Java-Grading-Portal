import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUser);
            stmt.execute(sqlTasks);
            stmt.execute(sqlParentChild);
        } catch (SQLException e) {
            System.out.println("Init Error: " + e.getMessage());
        }
    }

    // user manager
    public synchronized void saveUser(String fName, String lName, String uName, String pwd, String role) {
        String sql = "INSERT INTO users(first_name, last_name, username, password, role) VALUES(?,?,?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fName);
            pstmt.setString(2, lName);
            pstmt.setString(3, uName);
            pstmt.setString(4, PasswordUtils.hashPassword(pwd));
            pstmt.setString(5, role);
            pstmt.executeUpdate();
            System.out.println("User saved: " + uName);
        } catch (Exception e) { System.out.println("Save User Error: " + e.getMessage()); }
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("password").equals(PasswordUtils.hashPassword(password))) {
                    return new User(rs.getInt("id"), rs.getString("first_name"),
                            rs.getString("last_name"), rs.getString("username"),
                            Role.valueOf(rs.getString("role")));
                }
            }
        } catch (Exception e) { System.out.println("Login Error: " + e.getMessage()); }
        return null;
    }

    // task manager
    public synchronized void saveTask(String title, String desc, String deadline, int creatorId) {
        String sql = "INSERT INTO tasks(title, description, deadline, creator_id) VALUES(?,?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, desc);
            pstmt.setString(3, deadline);
            pstmt.setInt(4, creatorId);
            pstmt.executeUpdate();
            System.out.println("Task saved: " + title);
        } catch (SQLException e) { System.out.println("Save Task Error: " + e.getMessage()); }
    }

    public List<String> getTasksCreatedBy(int creatorId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT title, description, deadline FROM tasks WHERE creator_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, creatorId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("title") + " (" + rs.getString("deadline") + ") - " + rs.getString("description"));
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    public List<String> getTasksForStudent(String studentName) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT title, description, deadline FROM tasks WHERE description LIKE ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%[" + studentName + "]%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String d = rs.getString("description");
                String displayDesc = d.replaceFirst("\\[.*?\\]", "").trim();
                list.add(rs.getString("title") + " (Due: " + rs.getString("deadline") + ") | " + displayDesc);
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    // -------- text answer --------
    public synchronized boolean submitSolution(String studentName, String taskTitle, String answerText) {
        String sql = "UPDATE tasks SET description = description || ? WHERE title = ? AND description LIKE ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Wir hängen einfach [ANTWORT: ...] an die Beschreibung an
            pstmt.setString(1, " [ANTWORT: " + answerText + "]");
            pstmt.setString(2, taskTitle);
            pstmt.setString(3, "%[" + studentName + "]%");
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    // --- PARENT / CHILD LINKING ---
    public synchronized void linkParentToStudent(String parentUser, String childUser) {
        int pId = getUserId(parentUser);
        int cId = getUserId(childUser);
        if (pId == -1 || cId == -1) return;

        String sql = "INSERT OR IGNORE INTO parent_child(parent_id, child_id) VALUES(?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pId);
            pstmt.setInt(2, cId);
            pstmt.executeUpdate();
            System.out.println("Linked " + parentUser + " -> " + childUser);
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

    public String getChildOfParent(String parentUser) {
        String sql = "SELECT u.username FROM users u JOIN parent_child pc ON u.id = pc.child_id JOIN users p ON p.id = pc.parent_id WHERE p.username = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, parentUser);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("username");
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return null;
    }

    private int getUserId(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {}
        return -1;
    }

    public synchronized void createTestData() {
        boolean exists = false;
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT count(*) FROM users")) {
            if (rs.next() && rs.getInt(1) > 0) exists = true;
        } catch (SQLException e) {}

        if (exists) return;

        System.out.println("Creating FRESH Test Data...");
        saveUser("Hans", "Müller", "h.mueller", "1234", "TEACHER");
        saveUser("Max", "Mustermann", "max", "1234", "STUDENT");
        saveUser("Lisa", "Schlau", "lisa", "1234", "STUDENT");
        saveUser("Sabine", "Mustermann", "s.muster", "1234", "PARENT");
        linkParentToStudent("s.muster", "max");
        saveTask("Mathe Grundlagen", "[max] Bitte Seite 10 lösen", "2026-03-01", 1);
        System.out.println("--- Setup Complete ---");
    }
}