import java.time.LocalDateTime;
import java.util.Objects;
import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String description; //instead of File*
    private LocalDateTime deadline; //Change from String to LocalDateTime
    private User creator; //new
    private String grade; //new

    public Task(String title, String description, LocalDateTime deadline, User creator, String grade) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.creator = creator;
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(deadline, task.deadline) &&
                Objects.equals(creator, task.creator) &&
                Objects.equals(grade, task.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, deadline, creator, grade);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", creator=" + creator +
                ", grade='" + grade + '\'' +
                '}';
    }
}