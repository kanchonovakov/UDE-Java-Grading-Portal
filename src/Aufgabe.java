
public class Aufgabe {
    private String titel;
    private String beschreibung; //statt File*
    private String abgabefrist;

    //Konstruktor
    public Aufgabe(String titel, String beschreibung, String aufgabefrist) {
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.abgabefrist = aufgabefrist;
    }
    //Getter Setter Methoden
    public String getTitel() {
        return titel;
    }
    @Override
    public String toString() {
        return titel + " (bis: " + abgabefrist + ")";
    }
}
