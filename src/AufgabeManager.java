import java.util.ArrayList;
import java.util.List;
public class AufgabeManager {   //Aufgaben werden hier erstellt, bearbeitet, korrigiert und gelöscht.
                                //Funktioniert mit dem Server.

    //Attributen
    private List<Aufgabe> alleAufgaben;

    //konstruktor
    public AufgabeManager() {
        this.alleAufgaben = new ArrayList<>();
    }
}
