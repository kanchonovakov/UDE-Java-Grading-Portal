import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
public class Elter extends Nutzer implements Serializable{
    private static final long serialVersionUID = 1L;
    //Attribute
    private List<Schueler> kinder;

    //Konstruktor
    public Elter(int id, String vorname, String nachname, String benutzername,
                 String passwort) {
        super(vorname, nachname, benutzername, passwort, Role.ELTERNTEIL);
        this.kinder = new ArrayList<>();
    }

    //Funktionen

    //Verbindung zwischen Schülern-Eltern, setElter
    public void addKind(Schueler kind) {
        if (kind != null && !this.kinder.contains(kind)) {
            this.kinder.add(kind);
            kind.addElter(this);
            System.out.println(getVollerName() + " wurde mit Kind " + kind.getVollerName() + " verbunden.");
        } else {
            System.out.println("Kind ist bereits verknüpft.");
        }
    }


    public void zeigeKinderAufgaben(Schueler kind) {
        System.out.println("--- Aufgaben Übersicht für " + getVollerName() + " ---");

        if (kinder.isEmpty()) {
            System.out.println("Keine Kinder zugewiesen.");
        }

        for (Schueler s : kinder) {
            System.out.println("Kind: " + s.getVollerName());
            System.out.println("Abgegebene Aufgaben: " + s.getAbgegebeneAufgaben());
        }
    }

    //Getter und Setter Methoden
    public List<Schueler> getKinder() {
        return kinder;
    }

    //toString()
    @Override
    public String toString() {
        return super.toString() + " [Elter, Anzahl Kinder: " + kinder.size() + "]";
    }
}
