public class Server {  //Koordiniert die Versendung von Hausaufgaben, Nutzerprofile
                        //Kommuniziert zwischen Nutzer und Datenbank und AufgabenManager
    //Attribute
    private DatenbankManager dbManager;

    //Konstruktor
    public Server() {
        this.dbManager = new DatenbankManager();
    }
}