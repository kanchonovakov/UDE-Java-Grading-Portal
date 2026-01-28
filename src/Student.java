import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Student extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributes
    private List<Parent> parents; // Connection with parents*
    private List<Task> submittedTasks; // Tasks

    public Student(int id, String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password, Role.STUDENT);
        this.parents = new ArrayList<>();
        this.submittedTasks = new ArrayList<>();
    }

    public List<Task> getSubmittedTasks() {
        return submittedTasks;
    }

    public void setSubmittedTasks(List<Task> submittedTasks) {
        this.submittedTasks = (submittedTasks == null) ? new ArrayList<>() : submittedTasks;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = (parents == null) ? new ArrayList<>() : parents;
    }

    @Override
    public String getFullName() {
        return super.getFullName();
    }

    // Functions
    // Submit task
    public void submitTask(Task task) {
        if (task != null) {
            this.submittedTasks.add(task);
            System.out.println(getFullName() + " has submitted task '" + task.getTitle() + "'.");
        }
    }

    public void addParent(Parent parent) {
        if (parent != null && this.parents.size() < 2 && !this.parents.contains(parent)) {
            this.parents.add(parent);
        }
    }

    @Override
    public String toString() {
        String parentNames = "";
        if (parents == null || parents.isEmpty()) {
            parentNames = "No parents";
        } else {
            for (Parent p : parents) {
                if (!parentNames.isEmpty()) {
                    parentNames += ", ";
                }
                parentNames += p.getFullName();
            }
        }

        return super.toString() + " [Student - Parents: " + parentNames + "]";
    }
}