import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 12345;
    private DatenbankManager dbManager;

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            dbManager = new DatenbankManager();
            // Testdaten nur laden, wenn nötig (Fehler abfangen falls schon da)
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

    // Anfrage-Bearbeitung
    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            System.out.println("Client verbunden: " + clientSocket.getInetAddress());
            out.println("Verbindung hergestellt. Bereit für Befehle.");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Empfangen: " + inputLine);

                //String zerlegen
                // "LOGIN max 1234" -> parts[0]="LOGIN", parts[1]="max", parts[2]="1234"
                String[] parts = inputLine.split(" ");
                String commandName = parts[0].toUpperCase();

                try {
                    // String in Enum umwandeln
                    Command cmd = Command.valueOf(commandName);

                    switch (cmd) {
                        case PING:
                            out.println(Status.OK + " PONG");
                            break;

                        case QUIT:
                            out.println(Status.OK + " Bye bye");
                            return; // Methode beenden - Verbindung trennen

                        case LOGIN:
                            //LOGIN benutzername passwort
                            if (parts.length < 3) {
                                out.println(Status.ERROR + " Fehlende Parameter");
                            } else {
                                String user = parts[1];
                                String pass = parts[2];

                                // Datenbank fragen
                                Nutzer nutzer = dbManager.login(user, pass);

                                if (nutzer != null) {
                                    out.println(Status.LOGIN_SUCCESS + " Willkommen " + nutzer.getVorname());
                                } else {
                                    out.println(Status.INVALID_CREDENTIALS);
                                }
                            }
                            break;

                        default:
                            out.println(Status.ERROR + " Unbekannter Befehl (in Switch)");
                    }

                } catch (IllegalArgumentException e) {
                    // Falls der Client Quatsch sendet wie Bla Bla
                    out.println(Status.ERROR + " Ungültiges Kommando");
                }
            }

        } catch (IOException e) {
            System.out.println("Fehler bei Client-Kommunikation: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}