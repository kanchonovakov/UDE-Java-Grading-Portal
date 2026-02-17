import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final DatabaseManager dbManager;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket, DatabaseManager dbManager) {
        this.socket = socket;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Object request;
            while ((request = in.readObject()) != null) {

                // login
                if (request instanceof LoginRequest) {
                    LoginRequest loginReq = (LoginRequest) request;
                    User user = dbManager.login(loginReq.getUsername(), loginReq.getPassword());
                    if (user != null) {
                        out.writeObject(new LoginResponse(Status.LOGIN_SUCCESS, user, "Welcome!"));
                        System.out.println("Logged in: " + user.getUsername());
                    } else {
                        out.writeObject(new LoginResponse(Status.LOGIN_FAILED, null, "Invalid Login"));
                    }
                }

                // new task (teacher)
                else if (request instanceof Task) {
                    Task t = (Task) request;
                    String target = "all";
                    String desc = t.getDescription();
                    if(desc.startsWith("FOR:")) {
                        String[] parts = desc.split(";", 2);
                        target = parts[0].replace("FOR:", "");
                        desc = parts[1];
                    }
                    dbManager.saveTask(t.getTitle(), "[" + target + "] " + desc, t.getDeadline(), t.getCreatorId());
                    out.writeObject("Aufgabe gesendet an: " + target);
                }

                // text commands
                else if (request instanceof String) {
                    String cmd = (String) request;

                    if (cmd.startsWith("GET_CREATED_TASKS:")) {
                        int teacherId = Integer.parseInt(cmd.split(":")[1]);
                        out.writeObject(dbManager.getTasksCreatedBy(teacherId));
                    }
                    else if (cmd.startsWith("GET_TASKS:")) {
                        String student = cmd.split(":")[1];
                        out.writeObject(dbManager.getTasksForStudent(student));
                    }
                    else if (cmd.startsWith("GET_CHILD_TASKS:")) {
                        String parent = cmd.split(":")[1];
                        String child = dbManager.getChildOfParent(parent);
                        if (child != null) out.writeObject(dbManager.getTasksForStudent(child));
                        else out.writeObject("Kein Kind gefunden.");
                    }
                    // save text answaer
                    else if (cmd.startsWith("SUBMIT_ANSWER:")) {
                        // Format: SUBMIT_ANSWER:student:AntwortText:TaskTitel
                        String[] parts = cmd.split(":");
                        if (parts.length >= 4) {
                            String student = parts[1];
                            String answerText = parts[2];
                            String taskTitle = parts[3];

                            boolean success = dbManager.submitSolution(student, taskTitle, answerText);
                            if (success) out.writeObject("Antwort erfolgreich gespeichert!");
                            else out.writeObject("Fehler: Aufgabe nicht gefunden.");
                        }
                    }
                    else {
                        out.writeObject("Unbekannter Befehl.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Client Disconnected");
        }
    }
}