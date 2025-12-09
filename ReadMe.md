# Projektidee 1: Hausaufgaben abgeben und verwalten.

## Projektbeschribung
1) Ziel des Projekts: 
- Das Projekt soll eine Plattform bieten, auf der Schüler ihre Hausaufgaben digital abgeben können und Lehrer sie verwalten, korrigieren und bewerten können. 
  - a) Hauptaufgabe: Hausaufgaben werden in dem Programm erstellt, hochgeladen, überprüft und angesehen. 
  - b) Zielgruppe/Nutzen: Schüler, Lehrer und Eltern 
  - c) Motivation: Digitale Speicherung von Hausaufgaben. Digitales ist einfacher als in Papier, es ist auch Umweltfreundlicher. (Mehrwert – Zeit speichern).
  
 - **Geplante Funktionalitäten**:
   - Must have: Informationen über Lehrer und ihre Schüler verwalten und bereitstellen. Datennutzungshierarchie und Hierarchiemanagement. Die Schüler dürfen Datei senden und lesen, Eltern dürfen nur die Datei lesen und die Lehrer dürfen Dateien korrigieren, lesen und neue Aufgaben erstellen. 
   - Nice to have: Erstellung von Profilen durch verschiedene Codes, damit die Nutzer einfach verknüpft im System sein können. Versendung von Erklärungen ist wichtig. 
   - Should have: Speicherung von alten Hausaufgaben (Aufgaben) und eine flexible Hierarchie. Informationen über Schüler und die Verbindung zu ihren Eltern.
 
- **Bereits Implementierte Funktionen**:
   - Must have: Erstellung von Aufgaben durch Lehrer, Hochladen von Dateien durch Schüler, Korrektur und Bewertung durch Lehrer.
   - Nice to have: Nutzerprofile für Schüler, Lehrer und Eltern.
   - Should have: **work in progress** (*: Speicherung von alten Aufgaben und Verbindungen zwischen Nutzern.)

## Tabelle 
 - **Modul/Klasse** // **Aufgabe/Funktion**
   - Aufgabe Manager ==> Erstellen, Bearbeiten und Löschen von Aufgaben.
   - Hierarchiemanager ==> Zuweisung von Benutzerrollen (Lehrer, Schüler, Eltern).
   - Erstellte Aufgaben ==> Nachrichtendatei (Text, Bilder) und Textkommentare.
   - Datenbankmanager ==> Speichern von alten Aufgaben und alte Verbindungen zwischen Eltern, Schüler und Lehrer.
   - Server ==> Koordiniert die Versendung von Hausaufgaben, Nutzerprofile. Kommuniziert zwischen Client und Datenbank.

## Alte Klassen und Methoden (Stand bis 21.11.2025): 
- **Klasse** // **Methode** // **Beschreibung**
  - AufgabeManager // createAssignment() // Erstellt eine neue Hausaufgabe. **work in progress** 
  - AufgabeManager // deleteAssignment() // Löscht eine bestehende Hausaufgabe.
  - Hierarchiemanager // assignRole() // Weist einem Nutzer eine Rolle zu (Lehrer, Schüler, Eltern).
  - Datenbankmanager // saveOldAssignments() // Speichert alte Hausaufgaben in der Datenbank.
  - Server // sendAssignment() // Sendet eine Hausaufgabe an den entsprechenden Nutzer.


 - Update : - 09.12.2025: Code funktioniert.
 - Alle Klassen sind implementiert.
 - Admin Änderungen. 
 - Roles sind implementiert.
 - Änderungen in der Server Klasse, keine AufgabenManager und keine Files mehr. 
 - Beschreibung in ReadMe.md aktualisiert.
   - Nächste Schritte: Tests durchführen und die Aufgabe für die Datenbank abschließen.


 ** TODO: **
- Klassen besser dokumentieren.
