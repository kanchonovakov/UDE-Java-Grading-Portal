import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Teacher extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributes
    private List<Student> assignedStudents; // List of students

    // Constructor
    public Teacher(int id, String firstName, String lastName, String username,
                   String password) {
        super(firstName, lastName, username, password, Role.TEACHER);
        this.assignedStudents = new ArrayList<>();
    }

    // Getter and Setter Methods
    public List<Student> getAssignedStudents() {
        return assignedStudents;
    }

    public void addStudent(Student s) {
        this.assignedStudents.add(s);
    }

    // Functions

    // Create Task
    public Task createTask(String title, String description, String deadline) {
        System.out.println("Teacher " + getFullName() + " creates a new task: " + title);
        // returns null so far.
        return null;
    }

    // Correction Function TODO*

    // toString()
    @Override
    public String toString() {
        return super.toString() + " [Teacher - Number of students: " + assignedStudents.size() + "]";
    }
    // equals() and hashCode() are inherited from User
}