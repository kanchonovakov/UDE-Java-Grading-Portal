import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "localhost"; // localhost = mein eigener PC
    private static final int SERVER_PORT = 12345;        // Muss zum Server passen

    public static void main(String[] args) {
        System.out.println("--- Client startet ---");

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {

            // Verbindung steht. Jetzt hören wir was der Server sagt

            // Stream zum Lesen von dem Server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Nachricht vom Server empfangen
            String nachrichtVomServer = in.readLine();
            System.out.println("Server sagt: " + nachrichtVomServer);

        } catch (IOException e) {
            System.out.println("Verbindungsfehler: Ist der Server gestartet?");
            System.out.println(e.getMessage());
        }
    }
}
