public class Server {  //Koordiniert die Versendung von Hausaufgaben, Nutzerprofile
                        //Kommuniziert zwischen Nutzer und Datenbank und AufgabenManager
    //Attribute
    private DatenbankManager dbManager;
    private AufgabeManager aufgabeManager;

    //Konstruktor
    public Server() {
        this.dbManager = new DatenbankManager();
        this.aufgabeManager = new AufgabeManager();

    }
}