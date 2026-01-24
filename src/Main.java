void main(){
    System.out.println("Hello World! ");
    IO.println("Projektidee 1: Hausaufgaben abgeben und verwalten.");

    DatenbankManager db = new DatenbankManager();

    db.erstelleTestDaten();
    System.out.println("\n--- Lehrer erstellt Aufgaben ---");

    db.aufgabeSpeichern("Mathe S. 42", "Bitte Nr. 1-5 bearbeiten.", "2025-12-20", 1);
    db.aufgabeSpeichern("Deutsch Aufsatz", "Thema: Mein schönster Urlaub.", "2025-12-22", 1);

    System.out.println("Erfolg: Datenbank wurde initialisiert und Aufgaben gespeichert.");
    System.out.println("Du kannst jetzt 'Server' und 'Client' starten.");
}
