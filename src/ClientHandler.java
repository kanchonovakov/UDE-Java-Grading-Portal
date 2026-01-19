import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private DatenbankManager dbManager;

    // Konstruktor
    public ClientHandler(Socket socket, DatenbankManager dbManager) {
        this.clientSocket = socket;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        try (
                // Streams öffnen
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            System.out.println("Handler gestartet für: " + clientSocket.getInetAddress());

            while (true) {
                try {
                    // Warten auf Anfrage
                    Object empfangenesObjekt = in.readObject();

                    // Logik
                    if (empfangenesObjekt instanceof LoginRequest) {
                        LoginRequest req = (LoginRequest) empfangenesObjekt;

                        // DB Zugriff
                        Nutzer nutzer = dbManager.login(req.getBenutzername(), req.getPasswort());

                        if (nutzer != null) {
                            out.writeObject(new LoginResponse(Status.LOGIN_SUCCESS, nutzer, "Login erfolgreich!"));
                        } else {
                            out.writeObject(new LoginResponse(Status.INVALID_CREDENTIALS, null, "Falsche Daten."));
                        }
                        out.flush();
                    } else {
                        // Unbekanntes Objekt
                        out.writeObject(new LoginResponse(Status.ERROR, null, "Unbekannte Anfrage!"));
                        out.flush();
                    }

                } catch (EOFException e) {
                    // Client hat Verbindung getrennt
                    System.out.println("Client hat die Verbindung getrennt.");
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Fehler: Unbekanntes Objekt empfangen.");
                }
            }
        } catch (IOException e) {
            System.out.println("Verbindungsfehler im Handler: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}