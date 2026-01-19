public class Admin extends Nutzer {
    //Konstruktor
    public Admin(String vorname, String nachname, String benutzername,
                 String passwort) {
        super(vorname, nachname, benutzername, passwort, Role.ADMIN);
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

    //toString()
    @Override
    public String toString() {
        return super.toString() + " [Admin - Systemzugriff: Vollständig]";
    }
}
