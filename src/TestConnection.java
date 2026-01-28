import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:Mydatabase.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Create table if it doesn't exist
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS test (id INTEGER PRIMARY KEY, name TEXT)");
            IO.println("Table created or already exists.");
            IO.println("Connection successful!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}