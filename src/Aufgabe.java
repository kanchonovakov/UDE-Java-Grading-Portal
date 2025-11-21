
public class Aufgabe {  //Aufgaben, wie macht man sie.
                        //Erstellt von Lehrer/Admin, korrigiert von Lehrer/Admin,
                        //gelesen von allen, Schüler können Aufgaben (-Datei) senden.
    //TODO Wie erstellt man einen File?
    //TODO Titel der File, Ersteller, Korigierer.
    //Attribute
    private String titel;
    //Konstruktor
    public Aufgabe(String titel) {
        this.titel = titel;
    }

    //Getter Setter Methoden
    public String getTitel() {
        return titel;
    }
    public void setTitel(String titel) { //TODO will be used
        this.titel = titel;
    }
}
