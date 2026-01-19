import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
public class Lehrer extends Nutzer implements Serializable{
    private static final long serialVersionUID = 1L;
    //Attribute
    private List<Schueler> zugewieseneSchueler; //Liste mit Schüler

    //Konstruktor
    public Lehrer(String vorname, String nachname, String benutzername,
                   String passwort) {
        super(vorname, nachname, benutzername, passwort, Role.LEHRER);
        this.zugewieseneSchueler = new ArrayList<>();
    }

    //Getter und Setter Methoden
    public List<Schueler> getZugewieseneSchueler() {
        return zugewieseneSchueler;
    }
    public void addSchueler(Schueler s) {
        this.zugewieseneSchueler.add(s);
    }

    //Funktionen

    //Aufgabe Erstellen
    public Aufgabe erstelleAufgabe(String titel, String beschreibung, String abgabefrist) {
        System.out.println("Lehrer " + getVollerName() + " erstellt neue Aufgabe: " + titel);
        // bis jetzt ergibt null.
        return null;
    }
    //Korrigieren Function TODO*
    //toString()
    @Override
    public String toString() {
        return super.toString() + " [Lehrer - Anzahl Schüler: " + zugewieseneSchueler.size() + "]";
    }
    //equals() und hashCode() sind von Nutzer geerbt
}
