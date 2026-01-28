import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8080; // Task requires Port 8080

    public static void main(String[] args) {
        System.out.println("--- Client starting ---");
        Scanner scanner = new Scanner(System.in);

        // Establish connection and open streams
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connection established.");

            boolean running = true;
            while (running) {
                System.out.println("\nWhat would you like to do?");
                System.out.println("1: Login");
                System.out.println("2: Exit (QUIT)");
                System.out.print("Selection: ");

                String selection = scanner.nextLine();

                if (selection.equals("1")) {
                    System.out.print("Username: ");
                    String user = scanner.nextLine();
                    System.out.print("Password: ");
                    String pass = scanner.nextLine();

                    // Create object
                    LoginRequest request = new LoginRequest(user, pass);

                    // Send object and call FLUSH (Important for Task 3c)
                    out.writeObject(request);
                    out.flush();
                    System.out.println("[Client]: Login request sent...");

                    // Receive response
                    try {
                        Object responseObj = in.readObject();

                        if (responseObj instanceof LoginResponse) {
                            LoginResponse response = (LoginResponse) responseObj;
                            System.out.println("[Server Response]: " + response.getMessage());

                            if (response.getStatus() == Status.LOGIN_SUCCESS) {
                                User myUser = response.getUser();
                                System.out.println("--> Logged in as: " + myUser.getFullName());
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error reading the response.");
                    }

                } else if (selection.equalsIgnoreCase("2") || selection.equalsIgnoreCase("QUIT")) {
                    running = false;
                    System.out.println("Client is terminating.");
                } else {
                    System.out.println("Invalid input.");
                }
            }
            // At the end, try-with-resources closes the socket automatically (Task 3c fulfilled)

        } catch (IOException e) {
            System.out.println("Connection error: Is the server started?");
            System.out.println(e.getMessage());
        }
    }
}