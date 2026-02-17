import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    private String deadline;
    private int creatorId; // Changed from 'User' to 'int' to match Database logic

    public Task(String title, String description, String deadline, int creatorId) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.creatorId = creatorId;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDeadline() { return deadline; }
    public int getCreatorId() { return creatorId; }

    @Override
    public String toString() {
        return title + " (Due: " + deadline + ")";
    }
}