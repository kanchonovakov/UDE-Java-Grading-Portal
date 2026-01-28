import java.io.Serializable;

public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Status status;
    private User user;
    private String message;

    public LoginResponse(Status status, User user, String message) {
        this.status = status;
        this.user = user;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}