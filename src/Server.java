import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 8080;
    private DatabaseManager dbManager;

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Establish connection to DB
            dbManager = new DatabaseManager();

            // Load test data only if necessary
            try { dbManager.createTestData(); } catch (Exception e) {}

            System.out.println(" Server started on Port " + PORT + " (Multi-Threaded)");

            while (true) {
                System.out.println("Waiting for new connection...");

                // 1. Accept connection
                Socket clientSocket = serverSocket.accept();

                // 2. Create Handler
                ClientHandler handler = new ClientHandler(clientSocket, dbManager);

                // 3. START NEW THREAD
                // This is the key to concurrency!
                // The main server loop immediately returns to the start of the loop.
                new Thread(handler).start();
            }

        } catch (IOException e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}