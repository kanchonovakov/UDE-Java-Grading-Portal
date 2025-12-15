void main(){
    System.out.println("Hello World! ");
    IO.println("Projektidee 1: Hausaufgaben abgeben und verwalten.");


    DatenbankManager db = new DatenbankManager();

    db.erstelleTestDaten();
    /*
    db.zeigeAlleNutzer();

    System.out.println("Update Test: Passwort ändern...");
    db.updatePasswort(1, "NeuesGeheimesPasswort123");

    System.out.println("Delete Test: Nutzer löschen...");
    db.loescheNutzer(3);

    db.zeigeAlleNutzer();

*/

    System.out.println("\n--- Lehrer erstellt Aufgaben ---");
    // Parameter: Titel, Beschreibung, Frist, Lehrer-ID (1 = Hans Müller)
    db.aufgabeSpeichern("Mathe S. 42", "Bitte Nr. 1-5 bearbeiten.", "2025-12-20", 1);
    db.aufgabeSpeichern("Deutsch Aufsatz", "Thema: Mein schönster Urlaub.", "2025-12-22", 1);

    // 3. Ein Schüler schaut sich die Aufgaben an
    System.out.println("\n--- Schüler prüft Aufgaben ---");
    db.zeigeAlleAufgaben();
   /* System.out.println(" --- Nutzer erstellen ---");
    //ID muss 1 sein
    Admin mainAdmin = new Admin("Max", "Mustermann", "mAdmin", "xcvbvcdrtghhjnbvfre456yu");
    System.out.println("Main Admin hingefügt" + mainAdmin);
*/

}
