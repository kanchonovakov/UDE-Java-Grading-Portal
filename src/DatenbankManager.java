import java.util.ArrayList;
import java.util.List;
public class DatenbankManager { //Alten Aufgaben speichern
                                //Verbindungen (auch alte Verbindungen) zwischen Eltern, Schüler und Lehrer
                                //alle alten Nutzer speichern
                                //Liste von nutzern muss hier stehen.
                                //Nur Lehrer und ihrer Schüler und die Eltern der Schüler können die Aufgaben lesen
    //Attribute
    private List<Nutzer> alleNutzer;
    //Konstruktor
    public DatenbankManager() {
        this.alleNutzer = new ArrayList<>();
    }

    public List<Nutzer> getAlleNutzer() { //TODO will be used
        return alleNutzer;
    }
}
