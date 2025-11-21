import java.util.ArrayList;
import java.util.List;
public class AufgabeManager {   //Aufgaben werden hier erstellt, bearbeitet, korrigiert und gelöscht.
                                //Funktioniert mit dem Server.

    //Attribute
    private List<Aufgabe> alleAufgaben;

    //konstruktor
    public AufgabeManager() {
        this.alleAufgaben = new ArrayList<>();
    }
    //Getter und Setter Methoden

    public List<Aufgabe> getAlleAufgaben() { //TODO will be used
        return alleAufgaben;
    }
}
