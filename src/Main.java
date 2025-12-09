/*
void main() {
    IO.println(String.format("Hello and welcome!"));

    for (int i = 1; i <= 5; i++) {
      IO.println("i = " + i);
    }
}
*/
//Kommentare sind wichtig*
//Sinvolle Benennung


void main(){
    System.out.println("Hello World! ");
    IO.println("Projektidee 1: Hausaufgaben abgeben und verwalten.");

    System.out.println(" --- Nutzer erstellen ---");
    //ID muss 1 sein
    Admin mainAdmin = new Admin("Max", "Mustermann", "mAdmin", "xcvbvcdrtghhjnbvfre456yu");
    System.out.println("Main Admin hingefügt" + mainAdmin);


}



























//TODO Atrubuten
//TODO Klassenkonstruktoren
//TODO Getter- und Setter-Methoden
//TODO Funktionen der Klassen
//TODO Funktionsprototypen -Methodenname, Übergabeparameter und Rückgabetyp, nur ohne die eigentliche Funktionalität
//TODO Zugriffsmodifikatoren (public, protected, no-modifier, private) ---private als Standard---

//TODO Kommentieren Sie bei jeder Methode, die nicht private ist, warum sie keinen Modifier hat oder public protected sein muss.
//TODO Überlegen Sie, wie Ihre bestehenden Klassen sinnvoll miteinander verknüpft werden können, und implementieren Sie diese Beziehungen.
//TODO Vererbung
//TODO Schnittstelle (Interface) Welche Klassen sind betroffen und welche Informationen werden übergeben

//TODO toString()
//Überschreiben Sie die Methode so, dass sie eine aussagekräftige Beschreibung des Objekts
//liefert. Geben Sie alle relevanten Attribute in lesbarer Form aus.

//TODO equals(Object obj)
//Überschreiben Sie die Methode so, dass zwei Objekte als gleich gelten, wenn alle relevanten
//Attribute übereinstimmen. Achten Sie auf: Typprüfung (instanceof), Nullprüfung,
//Vergleich der Attribute mit equals().

//TODO hashCode()
//Implementieren Sie die Methoden so, dass sie einheitlich und konsistent mit equals() sind.
//Orientieren Sie sich dabei an den Beispielen aus den Vorlesungsfolien.
