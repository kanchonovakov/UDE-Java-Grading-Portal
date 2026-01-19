import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 8080;
    private DatenbankManager dbManager;

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Verbindung zur DB herstellen
            dbManager = new DatenbankManager();

            // Testdaten nur laden, wenn nötig
            try { dbManager.erstelleTestDaten(); } catch (Exception e) {}

            System.out.println("✅ Server gestartet auf Port " + PORT + " (Multi-Threaded)");

            while (true) {
                System.out.println("Warte auf neue Verbindung...");

                // 1. Verbindung annehmen (blockiert, bis jemand kommt)
                Socket clientSocket = serverSocket.accept();

                // 2. Handler erstellen (Übergabe von Socket und DB)
                ClientHandler handler = new ClientHandler(clientSocket, dbManager);

                // 3. NEUEN THREAD STARTEN
                // Das ist der Schlüssel zur Nebenläufigkeit!
                // Der Haupt-Server geht sofort zurück zum Anfang der Schleife.
                new Thread(handler).start();
            }

        } catch (IOException e) {
            System.out.println("Server-Fehler: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}