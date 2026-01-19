import java.io.Serializable;
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String benutzername;
    private String passwort;
    public LoginRequest(String benutzername, String passwort) {
        this.benutzername = benutzername;
        this.passwort = passwort;
    }
    public String getBenutzername() {
        return benutzername;
    }
    public String getPasswort() {
        return passwort;
    }
}