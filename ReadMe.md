# Projektidee 1: Hausaufgaben abgeben und verwalten.
- Klassennammen und Funktionen sind auf Deutsch 
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
   - ClientHandler ==> Übernimmt die Kommunikation mit einem einzelnen Client in einem separaten Thread.
   - LoginRequest ==> Transport-Objekt für Login-Daten (User, Passwort).
   - LoginResponse ==> Transport-Objekt für die Server-Antwort (Status + Nutzer Objekt).
   - Command (enum) ==> Definiert alle erlaubten Befehle (LOGIN, PING, QUIT, etc)
   - Status (enum) ==> Definiert alle möglichen Antworten (OK, ERROR, LOGIN_SUCCESS, etc)
   - Aufgabe ==> Objekt zur Speicherung von Titel, Beschreibung, Frist und Ersteller.
   - Nutzer ==> Verwaltet Basisdaten (ID, Name, Login) und Hierarchie (Rollen: Lehrer, Schüler, Eltern).
   - DatenbankManager ==> Speichern von Aufgaben, Nutzer und die Verbindungen (Eltern-Kind) dauerhaft (SQLite). 
   - PasswortUtils ==> Neue Hilfsklasse zur Sicherheit. Wandelt Passwörter mittels SHA-256 in Hashes um.
   

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
### 1. Verbindung & Protokoll
Das System nutzt TCP-Sockets auf Port 8080 und Java Object Serialization:
- Es werden Java-Objekte (DTOs) übertragen statt Strings.
- DTOs - Klassen wie LoginRequest dienen nur dem Transport und enthalten keine Logik.
- Ablauf - Client sendet LoginRequest -> Server prüft DB -> Server sendet LoginResponse. 

### 2. Socket-Wahl (Begründung)
Ich habe mich für die Klasse Socket (TCP) entschieden und nicht für DatagramSocket (UDP).
- Grund: TCP garantiert eine zuverlässige Übertragung.
- Bei einem Hausaufgaben-System ist es wichtig, dass keine Daten (Login-Informationen oder Aufgabentexte) verloren gehen.
- UDP wäre zwar schneller, aber Pakete könnten verloren gehen.

### 3. Nebenläufigkeit (Multi-Threading)
Der Server wurde erweitert, um mehrere Clients gleichzeitig bedienen zu können:
1. Server-Loop - Der Haupt-Server wartet nur auf eingehende Verbindungen accept().
2. ClientHandler - Sobald eine Verbindung steht, wird ein ClientHandler-Objekt erstellt.
3. Threads -Der ClientHandler wird in einem eigenen Java-Thread new Thread(handler).start() ausgeführt.

### 4. Synchronisation & Race Condition Analyse
**Absicherung:**
Da mehrere Threads auf den DatenbankManager zugreifen, wurden alle schreibenden Methoden nutzerSpeichern, aufgabeSpeichern, etc

**Race Condition Szenario:**
Zwei Clients ändern gleichzeitig denselben Datensatz.
- Ohne Synchronisation: Datenbank-Sperrfehler "Database is locked".
- Mit Synchronisation: Threads müssen warten, bis der erste Thread fertig ist

## Projekt-Historie & Updates
### Alte Klassen und Methoden (Stand bis 21.11.2025): 
- **Klasse** // **Methode** // **Beschreibung**
  - AufgabeManager // createAssignment() // Erstellt eine neue Hausaufgabe. **work in progress** 
  - AufgabeManager // deleteAssignment() // Löscht eine bestehende Hausaufgabe.
  - Hierarchiemanager // assignRole() // Weist einem Nutzer eine Rolle zu (Lehrer, Schüler, Eltern).
  - Datenbankmanager // saveOldAssignments() // Speichert alte Hausaufgaben in der Datenbank.
  - Server // sendAssignment() // Sendet eine Hausaufgabe an den entsprechenden Nutzer.
    
### Update Log:
1) Update - 09.12.2025: Basis-Klassen und Rollen implementiert.
2) Update - Mitte Dez 2025: Datenbank-Grundgerüst erstellt.
3) Update - 15.12.2025: Datenbank finalisiert (CRUD), Sicherheit (Hashing) integriert.
4) Update: 19.01.2026
   - Umstellung auf Netzwerk-Architektur (Server/Client) mit DTOs.
   - Implementierung von Multi-Threading ClientHandler.
   - Synchronisation der Datenbank-Zugriffe synchronized.
   

## TODOs 
- Dokumentation aktualisieren ja
- INSERT-Methoden im DatenbankManager fertigstellen ja
- Login-Prozess mit der Datenbank verbinden ja 
- Tests für alle Klassen und Methoden schreiben ja
- Netzwerk-Protokoll definieren (Commands/Status) ja
- Login über Netzwerk implementieren ja
- Nebenläufigkeit (Threads) und Synchronisation einbauen ja

