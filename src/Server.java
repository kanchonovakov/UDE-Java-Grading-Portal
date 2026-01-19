import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 12345;
    private DatenbankManager dbManager;

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            dbManager = new DatenbankManager();
            // Testdaten nur laden, wenn nötig
            try { dbManager.erstelleTestDaten(); } catch (Exception e) {}

            System.out.println("Server gestartet auf Port " + PORT);

            while (true) {
                System.out.println("Warte auf Client...");
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }

        } catch (IOException e) {
            System.out.println("Server-Fehler: " + e.getMessage());
        }
    }

    // Anfrage-Bearbeitung mit Objekten
    private void handleClient(Socket clientSocket) {
        try (
                // Streams erstellen (Reihenfolge erst Out, dann In)
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            System.out.println("Client verbunden: " + clientSocket.getInetAddress());

            // Wir lesen dauerhaft Objekte, bis der Client die Verbindung trennt
            while (true) {
                try {
                    // Warten auf ein Objekt vom Client
                    Object empfangenesObjekt = in.readObject();

                    // Prüfen, was für ein Objekt das ist
                    if (empfangenesObjekt instanceof LoginRequest) {
                        LoginRequest req = (LoginRequest) empfangenesObjekt;
                        System.out.println("Login-Versuch für: " + req.getBenutzername());

                        // Datenbank fragen
                        Nutzer nutzer = dbManager.login(req.getBenutzername(), req.getPasswort());

                        if (nutzer != null) {
                            // Erfolg - LoginResponse mit Nutzer-Objekt senden
                            LoginResponse resp = new LoginResponse(Status.LOGIN_SUCCESS, nutzer, "Login erfolgreich!");
                            out.writeObject(resp);
                        } else {
                            // Fehler - LoginResponse ohne Nutzer senden
                            LoginResponse resp = new LoginResponse(Status.INVALID_CREDENTIALS, null, "Benutzername oder Passwort falsch.");
                            out.writeObject(resp);
                        }
                    }
                    // Hier später weitere 'else if' für andere Requests

                } catch (EOFException e) {
                    // Client hat die Verbindung getrennt
                    System.out.println("Client hat die Verbindung beendet.");
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Unbekanntes Objekt empfangen.");
                }
            }

        } catch (IOException e) {
            System.out.println("Verbindung unterbrochen: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}