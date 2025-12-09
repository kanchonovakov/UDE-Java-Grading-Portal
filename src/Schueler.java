import java.util.List;
import java.util.ArrayList;
public class Schueler extends Nutzer { //Datei lesen und senden
    //Attribute
    private Elter elter; //Verbindung mit dem Elter
    private List<Aufgabe> abgegebeneAufgaben; //Aufgaben

    //Konstruktor
    public Schueler(String vorname, String nachname, String benutzername,
                   String passwort) {
        super(vorname, nachname, benutzername, passwort, Role.SCHUELER); //super*
        this.abgegebeneAufgaben = new ArrayList<>();
        this.elter = null;
    }

    //Getter und Setter Methoden
    public List<Aufgabe> getAbgegebeneAufgaben() {
        return abgegebeneAufgaben;
    }
    public Elter getElter() {
        return elter;
    }
    public void setElter(Elter elter) {
        this.elter = elter;
    }

    //Funktionen

    //Aufgabe senden
    public void sendeAufgabe(Aufgabe aufgabe) {
        if (aufgabe != null) {
            this.abgegebeneAufgaben.add(aufgabe);
            System.out.println(getVollerName() + " hat Aufgabe '" + aufgabe.getTitel() + "' abgegeben.");
        }
    }
    @Override
    public String toString() {
        String elterName = (elter != null) ? elter.getVollerName() : "Kein Elter";
        return super.toString() + " [Schueler - Elter: " + elterName + "]";
    }
}