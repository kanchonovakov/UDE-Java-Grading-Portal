import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Parent extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributes
    private List<Student> children;

    // Constructor
    public Parent(int id, String firstName, String lastName, String username,
                  String password) {
        super(firstName, lastName, username, password, Role.PARENT);
        this.children = new ArrayList<>();
    }

    // Functions

    // Connection between Student-Parent, setParent
    public void addChild(Student child) {
        if (child != null && !this.children.contains(child)) {
            this.children.add(child);
            child.addParent(this);
            System.out.println(getFullName() + " has been linked with child " + child.getFullName() + ".");
        } else {
            System.out.println("Child is already linked.");
        }
    }

    public void showChildrenTasks(Student child) {
        System.out.println("--- Task Overview for " + getFullName() + " ---");

        if (children.isEmpty()) {
            System.out.println("No children assigned.");
        }

        for (Student s : children) {
            System.out.println("Child: " + s.getFullName());
            System.out.println("Submitted Tasks: " + s.getSubmittedTasks());
        }
    }

    // Getter and Setter Methods
    public List<Student> getChildren() {
        return children;
    }

    // toString()
    @Override
    public String toString() {
        return super.toString() + " [Parent, Number of children: " + children.size() + "]";
    }
}