import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 8080;
    private DatenbankManager dbManager;

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Verbindung zur DB herstellen
            dbManager = new DatenbankManager();
            try { dbManager.erstelleTestDaten(); } catch (Exception e) {}

            System.out.println("Server gestartet auf Port " + PORT);

            while (true) {
                System.out.println("Warte auf Client...");
                Socket clientSocket = serverSocket.accept(); // Aufgabe 4: accept()
                handleClient(clientSocket);
            }

        } catch (IOException e) {
            System.out.println("Server-Fehler: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            System.out.println("Client verbunden: " + clientSocket.getInetAddress());

            while (true) {
                try {
                    // Anfragen lesen
                    Object empfangenesObjekt = in.readObject();

                    //validierung und verarbeitung

                    if (empfangenesObjekt instanceof LoginRequest) {
                        LoginRequest req = (LoginRequest) empfangenesObjekt;
                        System.out.println("Login-Versuch für: " + req.getBenutzername());

                        // server ruft DatabaseHandler auf
                        Nutzer nutzer = dbManager.login(req.getBenutzername(), req.getPasswort());

                        if (nutzer != null) {
                            LoginResponse resp = new LoginResponse(Status.LOGIN_SUCCESS, nutzer, "Login erfolgreich!");
                            out.writeObject(resp);
                        } else {
                            LoginResponse resp = new LoginResponse(Status.INVALID_CREDENTIALS, null, "Falsche Daten.");
                            out.writeObject(resp);
                        }
                        out.flush();
                    }

                    else {
                        System.out.println("Ungültiges Objekt empfangen.");
                        LoginResponse errorResp = new LoginResponse(Status.ERROR, null, "Unbekannte Anfrage!");
                        out.writeObject(errorResp);
                        out.flush();
                    }

                } catch (EOFException e) {
                    System.out.println("Client hat die Verbindung beendet.");
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Fehler: Unbekannte Klasse empfangen.");
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