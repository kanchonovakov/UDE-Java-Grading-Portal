import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Schueler extends Nutzer { //Datei lesen und senden
    //Attribute
    private List<Elter> eltern; //Verbindung mit dem Eltern*
    private List<Aufgabe> abgegebeneAufgaben; //Aufgaben

    public Schueler(String vorname, String nachname, String benutzername, String passwort) {
        super(vorname, nachname, benutzername, passwort, Role.SCHUELER);
        this.eltern = new ArrayList<>();
        this.abgegebeneAufgaben = new ArrayList<>();
    }

    public List<Aufgabe> getAbgegebeneAufgaben() {
        return abgegebeneAufgaben;
    }

    public void setAbgegebeneAufgaben(List<Aufgabe> abgegebeneAufgaben) {
        this.abgegebeneAufgaben = (abgegebeneAufgaben == null) ? new ArrayList<>() : abgegebeneAufgaben;
    }

    public List<Elter> getEltern() {
        return eltern;
    }

    public void setEltern(List<Elter> eltern) {
        this.eltern = (eltern == null) ? new ArrayList<>() : eltern;
    }

    @Override
    public String getVollerName() {
        return super.getVollerName();
    }

    //Funktionen
    //Aufgabe senden
    public void sendeAufgabe(Aufgabe aufgabe) {
        if (aufgabe != null) {
            this.abgegebeneAufgaben.add(aufgabe);
            System.out.println(getVollerName() + " hat Aufgabe '" + aufgabe.getTitel() + "' abgegeben.");
        }
    }
    public void addElter(Elter elter) {
        if (elter != null && this.eltern.size() < 2 && !this.eltern.contains(elter)) {
            this.eltern.add(elter);
        }
    }

    @Override
    public String toString() {
        String elternNamen = "";
        if (eltern == null || eltern.isEmpty()) {
            elternNamen = "Keine Eltern";
        } else {
            for (Elter e : eltern) {
                if (!elternNamen.isEmpty()) {
                    elternNamen += ", ";
                }
                elternNamen += e.getVollerName();
            }
        }

        return super.toString() + " [Schueler - Eltern: " + elternNamen + "]";
    }
}