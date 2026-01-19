import java.io.Serializable;
public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Status status;
    private Nutzer nutzer;
    private String nachricht;
    public LoginResponse(Status status, Nutzer nutzer, String nachricht) {
        this.status = status;
        this.nutzer = nutzer;
        this.nachricht = nachricht;
    }
    public Status getStatus() {
        return status;
    }

    public Nutzer getNutzer() {
        return nutzer;
    }
    public String getNachricht() {
        return nachricht;
    }
}