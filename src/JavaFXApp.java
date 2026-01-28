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
    private TextArea outputArea;
    private RequestController controller;

    @Override
    public void start(Stage primaryStage) {
        // 1. Initialize Network and Controller
        networkClient = new NetworkClient("localhost", 8080);

        // This connects the Model logic to the GUI via the Listener
        controller = new RequestController(networkClient, new TaskUpdateListener() {
            @Override
            public void onStatusUpdate(String message) {
                log("[STATUS]: " + message);
            }

            @Override
            public void onTaskCompleted(Object result) {
                if (result instanceof LoginResponse) {
                    LoginResponse resp = (LoginResponse) result;
                    log("Server Message: " + resp.getMessage());
                    if (resp.getStatus() == Status.LOGIN_SUCCESS) {
                        log("Welcome, " + resp.getUser().getFullName());
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                log("[ERROR]: " + errorMessage);
            }
        });

        // 2. GUI Layout (Same as before)
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        VBox topBox = new VBox(10);
        TextField userField = new TextField();
        userField.setPromptText("Username");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        Button loginButton = new Button("Login");
        topBox.getChildren().addAll(new Label("Login"), userField, passField, loginButton);
        root.setTop(topBox);

        outputArea = new TextArea();
        outputArea.setEditable(false);
        root.setCenter(outputArea);

        // 3. The New Event Handling
        loginButton.setOnAction(e -> {
            LoginRequest req = new LoginRequest(userField.getText(), passField.getText());

            log("Login request put into queue...");
            controller.enqueueRequest(req);
        });

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Homework Client (MVC)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void log(String message) {
        // Platform.runLater ensures the UI updates are on the correct thread
        Platform.runLater(() -> outputArea.appendText(message + "\n"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}