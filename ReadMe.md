# Projektidee 1: Hausaufgaben abgeben und verwalten.

## Projektbeschreibung
1) Ziel des Projekts:
- Das Projekt soll eine Plattform bieten, auf der Student ihre Task digital abgeben können und Teacher sie verwalten, korrigieren und bewerten können.
    - a) Hauptaufgabe: Task werden in dem Programm erstellt, hochgeladen, überprüft und angesehen.
    - b) Zielgruppe/Nutzen: Student, Teacher und Parent
    - c) Motivation: Digitale Speicherung von Hausaufgaben. Digitales ist einfacher als in Papier, es ist auch Umweltfreundlicher. (Mehrwert – Zeit sparen).

- **Geplante Funktionalitäten**:
    - Must have: Informationen über Teacher und ihre Student verwalten und bereitstellen. Datennutzungshierarchie und Hierarchiemanagement. Die Student dürfen Dateien senden und lesen, Parent dürfen nur die Dateien lesen und die Teacher dürfen Dateien korrigieren, lesen und neue Task erstellen.
    - Nice to have: Erstellung von Profilen durch verschiedene Codes, damit die Nutzer einfach verknüpft im System sein können. Versendung von Erklärungen ist wichtig.
    - Should have: Speicherung von alten Task und eine flexible Hierarchie. Informationen über Student und die Verbindung zu ihren Parent.

- **Bereits Implementierte Funktionen**:
    - Must have: Erstellung von Task durch Teacher, Hochladen von Dateien durch Student, Korrektur und Bewertung durch Teacher.
    - Nice to have: Nutzerprofile für Student, Teacher und Parent.
    - Should have: Speicherung von alten Task und Verbindungen zwischen Nutzern.
    - Netzwerk: Client-Server-Kommunikation mittels Java Serialisierung (Multi-Threading & Synchronisation)
    - GUI: JavaFX Oberfläche für den Client.

## Modul-Übersicht
- **Modul/Klasse** // **Aufgabe/Funktion**
    - JavaFXApp ==> Grafische Benutzeroberfläche (GUI). Startpunkt für den Nutzer.
    - NetworkClient ==> Kapselt die Netzwerk-Logik (connect, sendRequest) für die GUI.
    - Server ==> Zentraler Knotenpunkt. Hält die Verbindung zur DatabaseManager und lauscht auf Port 8080 auf Anfragen der Clients.
    - Client ==> Das Programm für den Nutzer (Teacher/Student). Verbindet sich mit dem Server, um Daten zu senden oder abzufragen.
    - ClientHandler ==> Übernimmt die Kommunikation mit einem einzelnen Client in einem separaten Thread.
    - LoginRequest ==> Transport-Objekt für Login-Daten (username, password).
    - LoginResponse ==> Transport-Objekt für die Server-Antwort (Status + User Objekt).
    - Command (enum) ==> Definiert alle erlaubten Befehle (LOGIN, PING, QUIT, etc)
    - Status (enum) ==> Definiert alle möglichen Antworten (OK, ERROR, LOGIN_SUCCESS, etc)
    - Task ==> Objekt zur Speicherung von title, description, deadline und creator.
    - User ==> Verwaltet Basisdaten (userId, Name, Login) und Hierarchie (Rollen: TEACHER, STUDENT, PARENT, ADMIN).
    - DatabaseManager ==> Speichern von Task, User und die Verbindungen (parent_child) dauerhaft (SQLite).
    - PasswordUtils ==> Neue Hilfsklasse zur Sicherheit. Wandelt Passwörter mittels hashPassword in Hashes um.


## Datenbank-Struktur (Dauerhafte Speicherung)

| Tabelle | Primary Key | Gespeicherte Informationen |
|---------|-------------|----------------------------|
| users   | id          | first_name, last_name, username, password, role |
| tasks   | id          | title, description, deadline, creator_id |
| parent_child | parent_id, child_id | Verknüpfung zwischen Parent und ihren Student |

### Beziehungen:
- Teacher zu Task (1:n): Ein Teacher erstellt viele Task.
- Parent zu Student (n:m): Ein Student kann mehrere Parent haben, und ein Parent kann mehrere Kinder haben. Dies wird über die Tabelle parent_child gelöst.

## Technische Architektur (Netzwerk)
### 1. JavaFX Client (Responsivität)
Der Client verfügt nun über eine grafische Oberfläche (JavaFXApp).
- Trennung: Die GUI enthält keine Netzwerklogik. Diese wurde in NetworkClient ausgelagert.
- Responsivität: Netzwerk-Operationen (wie performLogin) laufen in separaten Threads, um das Einfrieren der Oberfläche zu verhindern.
### 2. Verbindung & Protokoll
Das System nutzt TCP-Sockets auf Port 8080 und Java Object Serialization:

### 3. Nebenläufigkeit (Multi-Threading)
Der Server nutzt Multi-Threading:
- Server-Loop: Wartet auf Verbindungen accept().
- ClientHandler: Startet für jeden Client einen eigenen Thread new Thread(handler).start().

### 4. Synchronisation
Um Race Conditions zu vermeiden, sind alle schreibenden Methoden im DatabaseManager synchronized.

## Projekt-Historie & Updates
### Alte Klassen und Methoden (Stand bis 21.11.2025):
- **Klasse** // **Methode** // **Beschreibung**
    - AssignmentManager // createAssignment() // Erstellt eine neue Hausaufgabe. **work in progress** - AssignmentManager // deleteAssignment() // Löscht eine bestehende Hausaufgabe.
    - HierarchyManager // assignRole() // Weist einem Nutzer eine Rolle zu (Teacher, Student, Parent).
    - DatabaseManager // saveOldAssignments() // Speichert alte Hausaufgaben in der Datenbank.
    - Server // sendAssignment() // Sendet eine Hausaufgabe an den entsprechenden Nutzer.

### Update Log:
1) Update - 09.12.2025: Base-Klassen und Rollen implementiert.
2) Update - Mitte Dez 2025: Datenbank-Grundgerüst erstellt.
3) Update - 15.12.2025: Datenbank finalisiert (CRUD), Sicherheit (hashPassword) integriert.
4) Update: 19.01.2026
    - Umstellung auf Netzwerk-Architektur (Server/Client) mit DTOs.
    - Implementierung von Multi-Threading ClientHandler.
    - Synchronisation der Datenbank-Zugriffe synchronized.
5) Update: 28.01.2026
- Implementierung einer JavaFX-GUI JavaFXApp.
- Trennung von GUI und Netzwerklogik NetworkClient.
- Responsives Design durch Nutzung von Background-Threads im Client.

## TODOs
- Dokumentation aktualisieren ja
- saveUser Methoden im DatabaseManager fertigstellen ja
- Login-Prozess mit der Datenbank verbinden ja
- Tests für alle Klassen und Methoden schreiben ja
- Netzwerk-Protokoll definieren (Command/Status) ja
- Login über Netzwerk implementieren ja
- Nebenläufigkeit (Threads) und Synchronisation einbauen ja