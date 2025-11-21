public class Nutzer {

    private int nutzerId;
    private String vorname;
    private String nachname;
    private String benutzername;
    private String passwort; //Es ist schlechte Idee so das Passwort zu verwenden aber für jetzt lasse ich es so.
    private String erkennungscode; //Verbindung mit anderen Nutzer, z.B. Eltern - Kinder, Kinder - Lehrer

    //Konstruktor
    public Nutzer(){
    }
    //Main Konstruktor
    public Nutzer(int nutzerId, String vorname, String nachname, String benutzername,
                  String passwort, String erkennungscode) {
        this.nutzerId = nutzerId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.benutzername = benutzername;
        this.passwort = passwort; //Schlechte Idee*
        this.erkennungscode = erkennungscode;
    }

    //Getter und Setter Methoden
    //nutzerId
    public int getNutzerId() {return nutzerId;}
    public void setNutzerId(int nutzerId) {this.nutzerId = nutzerId;}
    //benutzername
    public String getBenutzername() {return benutzername;}
    public void setBenutzername(String benutzername) {this.benutzername = benutzername;}
    //erkennungscode
    public String getErkennungscode() {return erkennungscode;}
    public void setErkennungscode(String erkennungscode) {this.erkennungscode = erkennungscode;}
    //vorname
    public String getVorname() {return vorname;}
    public void setVorname(String vorname) {this.vorname = vorname;}
    //nachname
    public String getNachname() {return nachname;}
    public void setNachname(String nachname) {this.nachname = nachname;}
    //passwort
    public void setPasswort(String passwort) {this.passwort = passwort;}
    //Hier gibt es kein Getter für passwort**
    //VollerName
    public String getVollerName() {return this.vorname + " " + this.nachname;}
    //Funktionen
    public boolean passwortPruefenNeu(String eingabePasswort) {
        if (this.passwort == null) {
            return eingabePasswort == null;
        }
        return this.passwort.equals(eingabePasswort);
    }

    //toString()
    @Override
    public String toString() {
        return "Nutzer [ID=" + nutzerId +
                ", Name=" + vorname + " " + nachname +
                ", Username=" + benutzername +
                ", Code=" + erkennungscode +
                "]";
    }
    //equals(Object obj)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Nutzer nutzer = (Nutzer) obj;
        return nutzerId == nutzer.nutzerId;
    }
    //hashCode()
    @Override
    public int hashCode(){
        return Integer.hashCode(nutzerId);
    }










































    /*//Kontobenutzer, Kontonummer
                        //List von Nutzern
    //Attribute
    private String vorname;
    private String nachname;

    private int nutzerID;
    private static int idCounter = 0;
    private String benutzername;
    private String passwordHash; //wichtig für Security

    //Konstruktor
    Nutzer()










    Nutzer(){
        nutzerID = idCounter++;
    }
*/
    /*public Nutzer(String vorname,  String nachname, int nutzerID, String benutzername, String password) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.nutzerID = nutzerID;
        this.benutzername = benutzername;
    }*/

    //Admin Konstruktor

    //Getter Methoden
    /*public String getName(){
        return vorname + nachname;
    }
    public int getNutzerID() {
        return nutzerID;
    }*/

    /*public String getBenutzername() {
        return benutzername;
    }
    public String getPassword() {
        return password;
    }*/
    //bad idea


}
