import java.util.List;
import java.util.ArrayList;
public class Schüler extends Nutzer { //Datei lesen und senden
    //Attribute
    private Elter elter; //Verbindung mit dem Elter
    private List<Aufgabe> abgegebeneAufgaben; //Aufgaben

    //Konstruktor
    public Schüler(int nutzerId, String vorname, String nachname, String benutzername,
                   String passwort, String erkennungscode) {
        super(nutzerId, vorname, nachname, benutzername, passwort, erkennungscode); //super*
        this.abgegebeneAufgaben = new ArrayList<>();
        this.elter = null;
    }
    //Aufgabe senden
    public void sendeAufgabe(Aufgabe aufgabe) {
        this.abgegebeneAufgaben.add(aufgabe);
        System.out.println(getVollerName() + " hat Aufgabe '" + aufgabe.getTitel() + "' abgegeben.");
    }

    //TODO verbindung mit den Eltern*
}//Basis fr Schüler.