# Projektidee 1: Hausaufgaben abgeben und verwalten.

## Projektbeschreibung
1) Ziel des Projekts: 
- Das Projekt soll eine Plattform bieten, auf der Schüler ihre Hausaufgaben digital abgeben können und Lehrer sie verwalten, korrigieren und bewerten können. 
  - a) Hauptaufgabe: Hausaufgaben werden in dem Programm erstellt, hochgeladen, überprüft und angesehen. 
  - b) Zielgruppe/Nutzen: Schüler, Lehrer und Eltern 
  - c) Motivation: Digitale Speicherung von Hausaufgaben. Digitales ist einfacher als in Papier, es ist auch Umweltfreundlicher. (Mehrwert – Zeit sparen).
  
 - **Geplante Funktionalitäten**:
   - Must have: Informationen über Lehrer und ihre Schüler verwalten und bereitstellen. Datennutzungshierarchie und Hierarchiemanagement. Die Schüler dürfen Datei senden und lesen, Eltern dürfen nur die Datei lesen und die Lehrer dürfen Dateien korrigieren, lesen und neue Aufgaben erstellen. 
   - Nice to have: Erstellung von Profilen durch verschiedene Codes, damit die Nutzer einfach verknüpft im System sein können. Versendung von Erklärungen ist wichtig. 
   - Should have: Speicherung von alten Hausaufgaben (Aufgaben) und eine flexible Hierarchie. Informationen über Schüler und die Verbindung zu ihren Eltern.
 
- **Bereits Implementierte Funktionen**:
   - Must have: Erstellung von Aufgaben durch Lehrer, Hochladen von Dateien durch Schüler, Korrektur und Bewertung durch Lehrer.
   - Nice to have: Nutzerprofile für Schüler, Lehrer und Eltern.
   - Should have: Speicherung von alten Aufgaben und Verbindungen zwischen Nutzern.

## Modul-Übersicht
 - **Modul/Klasse** // **Aufgabe/Funktion**
   - Aufgabe ==> Objekt zur Speicherung von Titel, Beschreibung, Frist und Ersteller.
   - Nutzer ==> Verwaltet Basisdaten (ID, Name, Login) und Hierarchie (Rollen: Lehrer, Schüler, Eltern).
   - DatenbankManager ==> Speichern von Aufgaben, Nutzer und die Verbindungen (Eltern-Kind) dauerhaft (SQLite). 
   - PasswortUtils ==> Neue Hilfsklasse zur Sicherheit. Wandelt Passwörter mittels SHA-256 in Hashes um.
   - Main ==> Startpunkt des Programms, Initialisierung der Datenbank und Testdaten.

## Datenbank-Struktur (Dauerhafte Speicherung)

| Tabelle | Primary Key | Gespeicherte Informationen |
|---------|-------------|----------------------------|
| Nutzer  | nutzer_id   | Vorname, Nachname, Benutzername, Passwort, Rolle      |
| Aufgaben| aufgabe_id  | Titel, Beschreibung, Frist, Ersteller (nutzer_id)     |
|eltern_kind | elter_id, kind_id | Verknüpfung zwischen Eltern und ihren Kindern (nutzer_id) |

### Beziehungen:
- Lehrer zu Aufgaben (1:n): Ein Lehrer erstellt viele Aufgaben.
- Eltern zu Schüler (n:m): Ein Schüler kann mehrere Eltern haben, und ein Elternteil kann mehrere Kinder haben. Dies wird über die Tabelle `eltern_kind` gelöst.


## Projekt-Historie & Updates
### Alte Klassen und Methoden (Stand bis 21.11.2025): 
- **Klasse** // **Methode** // **Beschreibung**
  - AufgabeManager // createAssignment() // Erstellt eine neue Hausaufgabe. **work in progress** 
  - AufgabeManager // deleteAssignment() // Löscht eine bestehende Hausaufgabe.
  - Hierarchiemanager // assignRole() // Weist einem Nutzer eine Rolle zu (Lehrer, Schüler, Eltern).
  - Datenbankmanager // saveOldAssignments() // Speichert alte Hausaufgaben in der Datenbank.
  - Server // sendAssignment() // Sendet eine Hausaufgabe an den entsprechenden Nutzer.

### Update Log:
1) Update : - 09.12.2025: Code funktioniert.
   - Alle Basis Klassen sind implementiert.
   - Admin Änderungen. 
   - Roles sind implementiert.
   - Änderungen in der Server Klasse, keine AufgabenManager und keine Files mehr. 
   - Beschreibung in ReadMe.md aktualisiert.
2) Aktuell 
   - Datenbank (SQLite) wird implementiert.
   - Tests durchführen und Datenbank-Speicherung abschließen.
3) Update: - 15.12.2025:
   - Datenbank finalisiert: CRUD-Methoden
   - Sicherheit: Passwort-Hashing implementiert.
   - Main-Klasse: Testdaten und Initialisierung abgeschlossen.
## TODOs 
- Immer noch besser dokumentieren (?) 
- INSERT-Methoden im DatenbankManager fertigstellen. Ja
- Login-Prozess mit der Datenbank verbinden. Ja
- Tests für alle Klassen und Methoden schreiben. Ja
