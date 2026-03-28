# Digitales Hausaufgaben-Portal (Client-Server Architektur)

## Projektziel & Motivation
Dieses Projekt bietet eine digitale Plattform, auf der Schüler ihre Hausaufgaben empfangen und digital beantworten können, während Lehrer diese verwalten und kontrollieren. Eltern erhalten zudem einen transparenten Lesezugriff auf den Lernfortschritt ihrer Kinder.

**Motivation:** Die Digitalisierung von Hausaufgaben spart Zeit, schont die Umwelt (papierlos) und erleichtert die Kommunikation zwischen der Schule und dem Elternhaus durch eine klare, rollenbasierte Struktur.

---

## Build and Run Instructions

**Voraussetzungen:** Java 25, JavaFX SDK 25, SQLite-JDBC Treiber.

1. **Kompilieren (Beispiel via Command Line):**
   `javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml *.java`
2. **Server starten:**
   Führe die `Server.java` aus. Der Server lauscht fortan auf Port 8080.
3. **Client starten:**
   Führe die `JavaFXApp.java` aus (VM Options für JavaFX nicht vergessen!).
4. **Test-Accounts (Login / Passwort):**
   * Lehrer: `h.mueller` / `1234`
   * Schüler: `max` / `1234` oder `lisa` / `1234`
   * Eltern: `s.muster` / `1234`


---

## Kernfunktionen nach Nutzerrollen

* **Teacher (Lehrer):** Erstellen neuer Aufgaben, Zuweisung an spezifische Schüler, Einsehen des Verlaufs aller erstellten Aufgaben und Prüfen der eingereichten Text-Lösungen.
* **Student (Schüler):** Abrufen der persönlichen Aufgabenliste und direktes Einreichen von Text-Lösungen über die App.
* **Parent (Eltern):** "Read-Only"-Zugriff auf das Dashboard des eigenen Kindes, um den Status der Aufgaben einzusehen (ohne Bearbeitungsrechte).

---

## Technische Architektur & Highlights

Das System wurde als **verteilte Client-Server-Anwendung** in Java entwickelt und demonstriert fortgeschrittene Programmierkonzepte.

* **GUI-Trennung (MVC-Pattern):** Die Benutzeroberfläche (JavaFX) ist strikt von der Netzwerklogik getrennt. Fehler und Updates werden sauber über ein `TaskUpdateListener`-Interface an die View übergeben.
* **Multi-Threading & Responsivität:** Der Client nutzt Background-Threads und das Producer-Consumer-Pattern (`RequestController` mit `BlockingQueue`), sodass die Benutzeroberfläche während der Serverkommunikation niemals einfriert.
* **Server-Nebenläufigkeit:** Der Server kann durch den Einsatz von Sockets und separaten `ClientHandler`-Threads beliebig viele Nutzer gleichzeitig bedienen.
* **Thread-Sicherheit:** Alle schreibenden Datenbankzugriffe sind durch `synchronized`-Blöcke gegen Race Conditions abgesichert.
* **Sicherheit:** Passwörter werden nicht im Klartext gespeichert, sondern über die `PasswordUtils` kryptografisch gehasht.

---

## Datenbank-Struktur (SQLite)
Die Datenpersistenz wird durch eine relationale SQLite-Datenbank gewährleistet.

| Tabelle | Primärschlüssel | Gespeicherte Informationen |
| :--- | :--- | :--- |
| **users** | id | Vorname, Nachname, Username, Passwort-Hash, Rolle |
| **tasks** | id | Titel, Beschreibung (inkl. Antwort), Deadline, Creator-ID |
| **parent_child** | parent_id, child_id | Mapping-Tabelle für die n:m Beziehung zwischen Eltern und Kindern |

---

## Release Historie

* **v1.0 (Jan 2026):** Implementierung der JavaFX-GUI, MVC-Pattern und Trennung von UI/Netzwerk.
* **v0.8 (Jan 2026):** Umstellung auf Netzwerk-Architektur (Sockets, DTOs, Multi-Threading).
* **v0.5 (Dez 2025):** Finalisierung der SQLite-Datenbank (CRUD) und Passwort-Hashing.
* **v0.1 (Dez 2025):** Grundgerüst, Base-Klassen und Rollen-Konzept.
