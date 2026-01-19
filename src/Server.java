import java.io.*;
import java.net.*;

public class Server {
    // Muss > 1024 sein.
    private static final int PORT = 12345;

    // Unser DatenbankManager lebt hier im Server
    private DatenbankManager dbManager;

    public Server() {
        try {
            // 1. Server starten
            ServerSocket serverSocket = new ServerSocket(PORT);
            dbManager = new DatenbankManager(); // Datenbank laden

            System.out.println("Server gestartet auf Port " + PORT);
            System.out.println("Warte auf Clients");

            // 2. Endlosschleife. Der Server soll immer laufen
            while (true) {
                // accept() blockiert bis ein Client anklopft
                Socket clientSocket = serverSocket.accept();
                System.out.println("Neue Verbindung von: " + clientSocket.getInetAddress());

                // Hier würden wir später die Anfrage bearbeiten
                // Für jetzt nur eine Begrüßung senden
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Hallo Client, hier ist der Server.");

                // Verbindung sauber schließen
                clientSocket.close();
            }

        } catch (IOException e) {
            System.out.println("Server-Fehler: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
