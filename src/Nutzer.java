import java.io.Serializable;
public class Nutzer implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int idCounter = 0;

    private int nutzerId;
    private Role role; //0 - Schüler, 1 - Lehrer, 2 - Elternteil, 3 - Admin
    private String vorname;
    private String nachname;
    private String benutzername;
    private String passwort; //Es ist schlechte Idee so das Passwort zu verwenden aber für jetzt lasse ich es so.

    //Konstruktor
    public Nutzer(String vorname, String nachname, String benutzername,
                  String passwort, Role role) {
        this.nutzerId = ++idCounter;

        this.vorname = vorname;
        this.nachname = nachname;
        this.benutzername = benutzername;
        this.passwort = passwort; //Als Erster Passwort setzen

        this.role = role != null ? role : Role.ENTITY; //Standard Rolle
    }

    //Getter und Setter Methoden

    public int getNutzerId() {
        return nutzerId;
    }

    public Role getRole() {
        return role;
    }

    public String getBenutzername() {
        return benutzername;
    } //Username ist unveränderbar

    public String getVorname() {
        return vorname;
    }
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    } //Passwort ändern
    public String getPasswort() {
        return passwort;
    }

    //VollerName
    public String getVollerName() {
        return this.vorname + " " + this.nachname;
    }
    //Funktionen
    /*public boolean passwortPruefenNeu(String eingabePasswort) {
        if (this.passwort == null) {
            return eingabePasswort == null;
        }
        return this.passwort.equals(eingabePasswort);
    }*/
    //Passwort methode für Login wird in Server gemacht**

    //toString()
    @Override
    public String toString() {
        return "Nutzer [ID=" + nutzerId +
                    ", Role=" + role +
                    ", Name=" + vorname +
                    " " + nachname + "]";

    }
    //equals(Object obj)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Nutzer nutzer = (Nutzer) obj;
        return nutzerId == nutzer.nutzerId;
    }
    //hashCode()
    @Override
    public int hashCode(){
        return Integer.hashCode(nutzerId);
    }
}
