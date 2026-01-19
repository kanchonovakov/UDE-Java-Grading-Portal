import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        System.out.println("--- Client startet ---");

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            // 1. Handshake
            System.out.println("[Server]: " + in.readLine());

            // 2. Interaktive Schleife
            boolean running = true;
            while (running) {
                System.out.print("Befehl (LOGIN [User] [Pass] oder QUIT): ");
                String input = scanner.nextLine();

                // Eingabe an Server senden
                // Beispiel "LOGIN h.mueller 1234"
                out.println(input);

                // Antwort lesen
                String response = in.readLine();
                System.out.println("[Server Antwort]: " + response);

                // Wenn wir QUIT senden beenden wir auch den Client
                if (input.equalsIgnoreCase("QUIT")) {
                    running = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Verbindungsfehler: Ist der Server gestartet?");
            System.out.println(e.getMessage());
        }
    }
}
