//Änderung mit *extends*
public class Admin extends Lehrer {     //Datei lesen, korrigieren und Aufgaben erstellen.
                                        //Rektor oder IT Developer
                                        //der einzige, der die Kodes von allen Nutzern sehen/erstellen kann.
                                        //Wie benutzt man Setter als Admin mit dem Server
    //Konstruktor
    public Admin(int nutzerId, String vorname, String nachname, String benutzername,
                 String passwort, String erkennungscode) {
        super(nutzerId, vorname, nachname, benutzername, passwort, erkennungscode);
    }

    //Funktionen

    //TODO* Nutzer löschen  ----Selten Benutzt----
    public void loescheNutzer(int nutzerId) {
        System.out.println("Admin " + getVollerName() + " löscht Nutzer mit ID: " + nutzerId);
    }


    public void zeigeAlleNutzer() {
        //TODO DatenBankManager gebraucht*
        System.out.println("Admin " + getVollerName() + " zeigt alle registrierten Nutzer an.");
    }
    //TODO
    //TODO IDEE - alle Setter Methoden sind nur beim Admin möglich?
    //TODO
    //toString()
    @Override
    public String toString() {
        return "Nutzer [ID=" + getNutzerId() +
                "Admin mit Name=" + getVollerName() +
                ", Username=" + getBenutzername() +
                ", Code=" + getErkennungscode() +
                "]";
    }
}
