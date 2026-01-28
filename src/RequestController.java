import java.util.LinkedList;
import java.util.Queue;
import javafx.application.Platform;

public class RequestController {
    private final Queue<Object> requestQueue = new LinkedList<>();
    private final NetworkClient networkClient;
    private TaskUpdateListener listener;
    private boolean running = true;

    public RequestController(NetworkClient client, TaskUpdateListener listener) {
        this.networkClient = client;
        this.listener = listener;
        startConsumerThread();
    }

    // Producer: GUI calls this to add a request to the buffer
    public void enqueueRequest(Object request) {
        synchronized (requestQueue) { //Protecting the shared buffer
            requestQueue.add(request);
            requestQueue.notify(); // Wake up consumer
        }
    }

    // Consumer: Background thread processing requests
    private void startConsumerThread() {
        Thread consumer = new Thread(() -> {
            while (running) {
                Object request;
                synchronized (requestQueue) { // accessing shared queue
                    while (requestQueue.isEmpty()) {
                        try {
                            requestQueue.wait(); // No active waiting
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    request = requestQueue.poll();
                }

                processRequest(request);
            }
        });
        consumer.setDaemon(true);
        consumer.start();
    }

    private void processRequest(Object request) {
        try {
            Platform.runLater(() -> listener.onStatusUpdate("Processing request..."));

            networkClient.connect();
            Object response = networkClient.sendRequest(request);

            // Update GUI via MVC Events
            Platform.runLater(() -> listener.onTaskCompleted(response));
        } catch (Exception e) {
            Platform.runLater(() -> listener.onError("Communication Error: " + e.getMessage()));
        }
    }
}