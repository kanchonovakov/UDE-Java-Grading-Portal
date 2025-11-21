import java.util.List;
import java.util.ArrayList;
public class Elter extends Nutzer { //Datei lesen.
    //Attribute
    private List<Schüler> kinder;

    //Konstruktor
    public Elter(int nutzerId, String vorname, String nachname, String benutzername,
                 String passwort, String erkennungscode) {
        super(nutzerId, vorname, nachname, benutzername, passwort, erkennungscode);
        this.kinder = new ArrayList<>();
    }

    //Funktionen

    //Verbindung zwischen Schülern-Eltern, setElter
    public void addKind(Schüler kind) {
        if (!this.kinder.contains(kind)) {
            this.kinder.add(kind);
            System.out.println(getVollerName() + " wurde mit Kind " + kind.getVollerName() + " verbunden.");
        } else {
            System.out.println(kind.getVollerName() + " ist bereits verknüpft.");
        }
    }

    /*
    public void zeigeKinderAufgaben(Schüler kind) {
        if (this.kinder.contains(kind)) {
            System.out.println("Elter " + getVollerName() + " zeigt Aufgaben von " + kind.getVollerName());
        } else {
            System.out.println(kind.getVollerName() + " ist kein verknüpftes Kind.");
        }
    }*/ //Idee*

    //Getter und Setter Methoden
    public List<Schüler> getKinder() {
        return kinder;
    }

    //toString()
    @Override
    public String toString() {
        return super.toString() + " [Elter, Anzahl Kinder: " + kinder.size() + "]";
    }
}
