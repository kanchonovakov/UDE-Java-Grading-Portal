import java.util.List;
import java.util.ArrayList;
//Änderung mit *extends*
public class Lehrer extends Nutzer { //Datei lesen, korrigieren und Aufgaben erstellen.
    //Attributen
    private List<Schüler> zugewieseneSchüler; // Списък с учениците, които преподава

    //Konstruktor
    public Lehrer(int nutzerId, String vorname, String nachname, String benutzername,
                  String passwort, String erkennungscode) {
        super(nutzerId, vorname, nachname, benutzername, passwort, erkennungscode);
        this.zugewieseneSchüler = new ArrayList<>();
    }

    //Getter und Setter Methoden    }
    public List<Schüler> getZugewieseneSchüler() {
        return zugewieseneSchüler;
    }

    //Funktionen

    //Aufgabe Erstellen
    public Aufgabe erstelleAufgabe(String titel, String beschreibung, String abgabefrist) {
        System.out.println("Lehrer " + getVollerName() + " erstellt neue Aufgabe: " + titel);
        // bis jetzt ergibt null.
        return null;
    }

    //Korriegieren Fuktion TODO*


    //toString()

    @Override
    public String toString() {
        return super.toString() + " [Lehrer - Anzahl Schüler: " + zugewieseneSchüler.size() + "]";
    }

    //equals() und hashCode() sind von Nutzer geerbt
}
