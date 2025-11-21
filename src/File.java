public class File { //Bild(pdf), Text(txt), Presentation(pptx)
//TODO Erstelle einen File Attribute? Konstruktor? Funktionen?
    //Attribute
    private String titel;
    //Konstruktor
    public File(String titel) {
        this.titel = titel;
    }
    //Getter Setter Methoden
    public String getTitel() {  //TODO will be used in the future*
        return titel;
    }
    public void setTitel(String titel) { //TODO will be set in the future*
        this.titel = titel;
    }
}
