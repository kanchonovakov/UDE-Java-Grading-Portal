import java.util.ArrayList;
import java.util.List;
public class DatenbankManager { //Alten Aufgaben speichern
                                //Verbindungen (auch alte Verbindungen) zwischen Eltern, Schüler und Lehrer
                                //alle alten Nutzer speichern
                                //Liste von nutzern muss hier stehen.
    //Attributen
    private List<Nutzer> alleNutzer;
    //Konstruktor
    public DatenbankManager() {
        this.alleNutzer = new ArrayList<>();
    }
}
