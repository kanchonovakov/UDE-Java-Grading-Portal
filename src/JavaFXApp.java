import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXApp extends Application {

    private NetworkClient networkClient;
    private TextArea outputArea; // Output area for status/logs

    @Override
    public void start(Stage primaryStage) {
        // 1. Prepare Network Client
        networkClient = new NetworkClient("localhost", 8080);

        // 2. Create GUI Layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // --- Top: Login ---
        VBox topBox = new VBox(10);
        TextField userField = new TextField();
        userField.setPromptText("Username");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");

        Button loginButton = new Button("Login");
        topBox.getChildren().addAll(new Label("Login"), userField, passField, loginButton);
        root.setTop(topBox);

        // --- Center: Logs/Status ---
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);
        root.setCenter(outputArea);

        // --- Bottom: Close Connection ---
        Button disconnectButton = new Button("Disconnect / Exit");
        root.setBottom(disconnectButton);

        // 3. Link Logic

        // LOGIN BUTTON
        loginButton.setOnAction(e -> {
            String user = userField.getText();
            String pass = passField.getText();
            log("Attempting login for: " + user + "...");

            new Thread(() -> performLogin(user, pass)).start();
        });

        // DISCONNECT BUTTON
        disconnectButton.setOnAction(e -> {
            networkClient.disconnect();
            Platform.exit();
        });

        // 4. Set Scene and Show
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Homework Client (JavaFX)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method - Login logic in background thread
    private void performLogin(String user, String pass) {
        try {
            // Connect if not already connected
            networkClient.connect();

            // Build request object
            LoginRequest request = new LoginRequest(user, pass);

            // Send and receive response
            Object responseObj = networkClient.sendRequest(request);
            Platform.runLater(() -> {
                if (responseObj instanceof LoginResponse) {
                    LoginResponse resp = (LoginResponse) responseObj;
                    log("Server Response: " + resp.getStatus());
                    log("Message: " + resp.getMessage());

                    if (resp.getStatus() == Status.LOGIN_SUCCESS) {
                        User n = resp.getUser();
                        log("--> Logged in as: " + n.getFullName() + " (" + n.getRole() + ")");
                        // You could switch to the next view here
                    }
                } else {
                    log("Unknown response received.");
                }
            });

        } catch (Exception ex) {
            Platform.runLater(() -> log("Error: " + ex.getMessage()));
        }
    }

    // Helper method to write text to the TextArea
    private void log(String message) {
        outputArea.appendText(message + "\n");
    }

    @Override
    public void stop() {
        // Cleanup when closing the window
        if (networkClient != null) {
            networkClient.disconnect();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}