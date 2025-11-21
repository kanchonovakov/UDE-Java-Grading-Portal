import java.util.ArrayList;
import java.util.List;
public class Server {  //Koordiniert die Versendung von Hausaufgaben, Nutzerprofile
                        //Kommuniziert zwischen Nutzer und Datenbank und AufgabenManager
    //Attributen
    private DatenbankManager dbManager;
    private AufgabeManager aufgabeManager;

    //Konstruktor
    public Server() {
        this.dbManager = new DatenbankManager();
        this.aufgabeManager = new AufgabeManager();

    }
}