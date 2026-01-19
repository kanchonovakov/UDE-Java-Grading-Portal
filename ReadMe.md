# Projektidee 1: Hausaufgaben abgeben und verwalten.
- Klassen   nammen und Funktionen sind auf Deutsch 
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
   - Netzwerk: Client-Server-Kommunikation - Commands & Status.

## Modul-Übersicht
 - **Modul/Klasse** // **Aufgabe/Funktion**
   - Server ==> Zentraler Knotenpunkt. Hält die Verbindung zur Datenbank und lauscht auf Port 12345 auf Anfragen der Clients.
   - Client ==> Das Programm für den Nutzer (Lehrer/Schüler). Verbindet sich mit dem Server, um Daten zu senden oder abzufragen.
   - LoginRequest ==> Transport-Objekt für Login-Daten (User, Passwort).
   - LoginResponse ==> Transport-Objekt für die Server-Antwort (Status + Nutzer Objekt).
   - Command (enum) ==> Definiert alle erlaubten Befehle (LOGIN, PING, QUIT, etc)
   - Status (enum) ==> Definiert alle möglichen Antworten (OK, ERROR, LOGIN_SUCCESS, etc)
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

## Technische Architektur (Netzwerk)
Das System nutzt TCP-Sockets und Java Object Serialization
1. Verbindung - Server Port 12345 und Client nutzen ObjectOutputStream und ObjectInputStream.
2. Datenübertragung - Java-Objekte (DTOs) übertragen.
   - Vorteil - Typensicherheit und strukturierte Daten.
   - DTOs - Daten-Klassen wie LoginRequest dienen nur dem Transport und enthalten keine Logik.
3. Ablauf:
   - Client sendet LoginRequest.
   - Server prüft DB und sendet LoginResponse zurück.
   - Die Response enthält direkt das eingeloggte Nutzer-Objekt Lehrer oder Schüler.

## Projekt-Historie & Updates
### Alte Klassen und Methoden (Stand bis 21.11.2025): 
- **Klasse** // **Methode** // **Beschreibung**
  - AufgabeManager // createAssignment() // Erstellt eine neue Hausaufgabe. **work in progress** 
  - AufgabeManager // deleteAssignment() // Löscht eine bestehende Hausaufgabe.
  - Hierarchiemanager // assignRole() // Weist einem Nutzer eine Rolle zu (Lehrer, Schüler, Eltern).
  - Datenbankmanager // saveOldAssignments() // Speichert alte Hausaufgaben in der Datenbank.
  - Server // sendAssignment() // Sendet eine Hausaufgabe an den entsprechenden Nutzer.

1. Anfrage-Format (Client -> Server): COMMAND [Parameter] [Parameter] ...
- PING: Testet Verbindung. 
- QUIT: Beendet Verbindung. 
- LOGIN [user] [pass]: Versucht einen Login mit Benutzername und Passwort.
- GET_AUFGABEN: Fragt Liste aller Aufgaben ab (geplant).

2. Antwort-Format (Server -> Client): STATUS [Nachricht]
- OK / ERROR: Allgemeine Rückmeldungen.
- LOGIN_SUCCESS: Login erfolgreich, Benutzer erkannt. 
- INVALID_CREDENTIALS: Falsches Passwort oder Benutzername.

    
### Update Log:
1) Update - 09.12.2025: Basis-Klassen und Rollen implementiert.
2) Update - Mitte Dez 2025: Datenbank-Grundgerüst erstellt.
3) Update - 15.12.2025: Datenbank finalisiert (CRUD), Sicherheit (Hashing) integriert.
4) Update: 19.01.2026
   - Umstellung auf Netzwerk-Architektur (Server/Client).
   - Implementierung von (Data Transfer Objects).
   - Kommunikation auf Object-Streams umgestellt.
## TODOs 
- Dokumentation aktualisieren ja
- INSERT-Methoden im DatenbankManager fertigstellen ja
- Login-Prozess mit der Datenbank verbinden ja 
- Tests für alle Klassen und Methoden schreiben ja
- Netzwerk-Protokoll definieren (Commands/Status) ja
- Login über Netzwerk implementieren ja
- Aufgaben-Abruf über das Netzwerk implementieren Nein
