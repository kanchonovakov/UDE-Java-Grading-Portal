public interface TaskUpdateListener {
    void onStatusUpdate(String message);
    void onTaskCompleted(Object result);
    void onError(String errorMessage);
}