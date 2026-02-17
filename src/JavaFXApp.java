import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class JavaFXApp extends Application {

    private NetworkClient networkClient;
    private RequestController controller;
    private Stage primaryStage;

    // GUI Elemente
    private ListView<String> taskListView;
    private TextArea statusArea;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        networkClient = new NetworkClient("localhost", 8080);

        controller = new RequestController(networkClient, new TaskUpdateListener() {
            @Override
            public void onStatusUpdate(String message) { log(message); }
            @Override
            public void onTaskCompleted(Object result) {
                Platform.runLater(() -> {
                    if (result instanceof LoginResponse) handleLogin((LoginResponse) result);
                    else if (result instanceof List) updateList((List<?>) result);
                    else log("Server: " + result);
                });
            }
            @Override
            public void onError(String err) { log("Error: " + err); }
        });

        showLoginScreen();
    }

    // --- LOGIN ---
    private void showLoginScreen() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        TextField userF = new TextField(); userF.setPromptText("Username");
        PasswordField passF = new PasswordField(); passF.setPromptText("Password");
        Button btn = new Button("Login");
        statusArea = new TextArea(); statusArea.setPrefHeight(100);

        btn.setOnAction(e -> controller.enqueueRequest(new LoginRequest(userF.getText(), passF.getText())));

        root.getChildren().addAll(new Label("Schul-Portal Login"), userF, passF, btn, statusArea);
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void handleLogin(LoginResponse r) {
        if (r.getStatus() == Status.LOGIN_SUCCESS) {
            User u = r.getUser();
            if (u.getRole() == Role.TEACHER) showTeacherView(u);
            else if (u.getRole() == Role.STUDENT) showStudentView(u);
            else showParentView(u);
        } else {
            log(r.getMessage());
        }
    }

    // --- TEACHER VIEW ---
    private void showTeacherView(User u) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label lbl = new Label("Teacher: " + u.getFullName());

        TextField targetF = new TextField(); targetF.setPromptText("Student Username (z.B. max)");
        TextField titleF = new TextField(); titleF.setPromptText("Aufgabe Titel");
        TextField descF = new TextField(); descF.setPromptText("Beschreibung");
        DatePicker dateF = new DatePicker();
        Button sendBtn = new Button("Aufgabe senden");

        taskListView = new ListView<>();
        Button historyBtn = new Button("Aktualisieren (Antworten sehen)");

        sendBtn.setOnAction(e -> {
            if (dateF.getValue() == null) { log("Bitte Datum wählen!"); return; }
            // Format: FOR:student;description
            String content = "FOR:" + targetF.getText() + ";" + descF.getText();
            Task t = new Task(titleF.getText(), content, dateF.getValue().toString(), u.getId());
            controller.enqueueRequest(t);
        });
        historyBtn.setOnAction(e -> controller.enqueueRequest("GET_CREATED_TASKS:" + u.getId()));

        Button logout = new Button("Logout");
        logout.setOnAction(e -> showLoginScreen());

        root.getChildren().addAll(lbl, new Separator(), new Label("Neue Aufgabe:"), targetF, titleF, descF, dateF, sendBtn,
                new Separator(), new Label("Gesendete Aufgaben & Antworten:"), historyBtn, taskListView, logout);
        primaryStage.setScene(new Scene(root, 500, 650));
    }

    // --- STUDENT VIEW ---
    private void showStudentView(User u) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        Label lbl = new Label("Student: " + u.getFullName());

        taskListView = new ListView<>();
        Button loadBtn = new Button("Meine Aufgaben laden");

        TextArea answerArea = new TextArea();
        answerArea.setPromptText("Schreibe hier deine Lösung...");
        answerArea.setPrefRowCount(3);

        Button submitBtn = new Button("Lösung absenden");

        loadBtn.setOnAction(e -> controller.enqueueRequest("GET_TASKS:" + u.getUsername()));

        submitBtn.setOnAction(e -> {
            String selected = taskListView.getSelectionModel().getSelectedItem();
            if (selected == null) { log("Bitte erst eine Aufgabe auswählen!"); return; }

            String answerText = answerArea.getText();
            if (answerText.isEmpty()) { log("Bitte eine Lösung schreiben!"); return; }

            String title = selected.split(" \\(")[0];

            // Format: SUBMIT_ANSWER:student:AntwortText:TaskTitel
            controller.enqueueRequest("SUBMIT_ANSWER:" + u.getUsername() + ":" + answerText + ":" + title);

            answerArea.clear();
        });

        Button logout = new Button("Logout");
        logout.setOnAction(e -> showLoginScreen());

        root.getChildren().addAll(lbl, taskListView, loadBtn, new Separator(),
                new Label("Lösung einreichen:"), answerArea, submitBtn, logout);
        primaryStage.setScene(new Scene(root, 400, 550));
    }

    // --- PARENT VIEW ---
    private void showParentView(User u) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        Label lbl = new Label("Eltern-Portal: " + u.getFullName());

        taskListView = new ListView<>();
        Button loadBtn = new Button("Aufgaben des Kindes laden");

        loadBtn.setOnAction(e -> controller.enqueueRequest("GET_CHILD_TASKS:" + u.getUsername()));

        Button logout = new Button("Logout");
        logout.setOnAction(e -> showLoginScreen());

        root.getChildren().addAll(lbl, new Label("Ansicht (Keine Bearbeitung möglich):"), taskListView, loadBtn, logout);
        primaryStage.setScene(new Scene(root, 400, 500));
    }

    private void log(String m) { if(statusArea!=null) statusArea.appendText(m + "\n"); System.out.println(m); }
    private void updateList(List<?> l) { taskListView.getItems().clear(); for(Object o:l) taskListView.getItems().add(o.toString()); }

    public static void main(String[] args) { launch(args); }
}