import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8080; // Aufgabe verlangt Port 8080

    public static void main(String[] args) {
        System.out.println("--- Client startet ---");
        Scanner scanner = new Scanner(System.in);

        // Verbindung aufbauen und Streams öffnen
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Verbindung hergestellt.");

            boolean running = true;
            while (running) {
                System.out.println("\nWas möchtest du tun?");
                System.out.println("1: Einloggen");
                System.out.println("2: Beenden (QUIT)");
                System.out.print("Auswahl: ");

                String auswahl = scanner.nextLine();

                if (auswahl.equals("1")) {
                    System.out.print("Benutzername: ");
                    String user = scanner.nextLine();
                    System.out.print("Passwort: ");
                    String pass = scanner.nextLine();

                    // Objekt erstellen
                    LoginRequest request = new LoginRequest(user, pass);

                    // Objekt senden und FLUSH aufrufen (Wichtig für Aufgabe 3c)
                    out.writeObject(request);
                    out.flush();
                    System.out.println("[Client]: Login-Anfrage gesendet...");

                    // Antwort empfangen
                    try {
                        Object antwort = in.readObject();

                        if (antwort instanceof LoginResponse) {
                            LoginResponse response = (LoginResponse) antwort;
                            System.out.println("[Server Antwort]: " + response.getNachricht());

                            if (response.getStatus() == Status.LOGIN_SUCCESS) {
                                Nutzer meinNutzer = response.getNutzer();
                                System.out.println("--> Eingeloggt als: " + meinNutzer.getVollerName());
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println("Fehler beim Lesen der Antwort.");
                    }

                } else if (auswahl.equalsIgnoreCase("2") || auswahl.equalsIgnoreCase("QUIT")) {
                    running = false;
                    System.out.println("Client wird beendet.");
                } else {
                    System.out.println("Ungültige Eingabe.");
                }
            }
            // Am Ende schließt try-with-resources den Socket automatisch (Aufgabe 3c erfüllt)

        } catch (IOException e) {
            System.out.println("Verbindungsfehler: Ist der Server gestartet?");
            System.out.println(e.getMessage());
        }
    }
}