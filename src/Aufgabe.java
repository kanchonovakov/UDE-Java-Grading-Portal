import java.time.LocalDateTime;
import java.util.Objects;
import java.io.Serializable;


public class Aufgabe implements Serializable{
    private static final long serialVersionUID = 1L;

    private String titel;
    private String beschreibung; //statt File*
    private LocalDateTime abgabefrist; //Änderung von String zu LocalDateTime
    private Nutzer ersteller; //neu
    private String bewertung; //neu

    public Aufgabe(String titel, String beschreibung, LocalDateTime abgabefrist, Nutzer ersteller, String bewertung) {
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.abgabefrist = abgabefrist;
        this.ersteller = ersteller;
        this.bewertung = bewertung;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public LocalDateTime getAbgabefrist() {
        return abgabefrist;
    }

    public void setAbgabefrist(LocalDateTime abgabefrist) {
        this.abgabefrist = abgabefrist;
    }

    public Nutzer getErsteller() {
        return ersteller;
    }

    public void setErsteller(Nutzer ersteller) {
        this.ersteller = ersteller;
    }

    public String getBewertung() {
        return bewertung;
    }

    public void setBewertung(String bewertung) {
        this.bewertung = bewertung;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Aufgabe aufgabe = (Aufgabe) o;
        return Objects.equals(titel, aufgabe.titel) && Objects.equals(beschreibung, aufgabe.beschreibung) && Objects.equals(abgabefrist, aufgabe.abgabefrist) && Objects.equals(ersteller, aufgabe.ersteller) && Objects.equals(bewertung, aufgabe.bewertung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titel, beschreibung, abgabefrist, ersteller, bewertung);
    }

    @Override
    public String toString() {
        return "Aufgabe{" +
                "titel='" + titel + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", abgabefrist=" + abgabefrist +
                ", ersteller=" + ersteller +
                ", bewertung='" + bewertung + '\'' +
                '}';
    }
}
