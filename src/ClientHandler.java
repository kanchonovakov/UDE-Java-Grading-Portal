import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private DatabaseManager dbManager;

    // Constructor
    public ClientHandler(Socket socket, DatabaseManager dbManager) {
        this.clientSocket = socket;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        try (
                // Open streams
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            System.out.println("Handler started for: " + clientSocket.getInetAddress());

            while (true) {
                try {
                    // Waiting for request
                    Object receivedObject = in.readObject();

                    // Logic
                    if (receivedObject instanceof LoginRequest) {
                        LoginRequest req = (LoginRequest) receivedObject;

                        // DB Access
                        User user = dbManager.login(req.getUsername(), req.getPassword());

                        if (user != null) {
                            out.writeObject(new LoginResponse(Status.LOGIN_SUCCESS, user, "Login successful!"));
                        } else {
                            out.writeObject(new LoginResponse(Status.INVALID_CREDENTIALS, null, "Incorrect credentials."));
                        }
                        out.flush();
                    } else {
                        // Unknown object
                        out.writeObject(new LoginResponse(Status.ERROR, null, "Unknown request!"));
                        out.flush();
                    }

                } catch (EOFException e) {
                    // Client disconnected
                    System.out.println("Client has disconnected.");
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Error: Unknown object received.");
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error in handler: " + e.getMessage());
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