import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatenbankManager {

    private static final String URL = "jdbc:sqlite:Mydatabase.db";

    public DatenbankManager() {
        initialisiereTabellen();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Verbindungsfehler: " + e.getMessage());
        }
        return conn;
    }

    private void initialisiereTabellen() {
        String sqlNutzer = "CREATE TABLE IF NOT EXISTS nutzer ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "vorname TEXT NOT NULL,"
                + "nachname TEXT NOT NULL,"
                + "benutzername TEXT UNIQUE NOT NULL,"
                + "passwort TEXT NOT NULL,"
                + "rolle TEXT NOT NULL"
                + ");";

        String sqlAufgaben = "CREATE TABLE IF NOT EXISTS aufgaben ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titel TEXT NOT NULL,"
                + "beschreibung TEXT,"
                + "frist TEXT,"
                + "ersteller_id INTEGER,"
                + "FOREIGN KEY(ersteller_id) REFERENCES nutzer(id)"
                + ");";

        String sqlElternKind = "CREATE TABLE IF NOT EXISTS eltern_kind ("
                + "elter_id INTEGER,"
                + "kind_id INTEGER,"
                + "PRIMARY KEY (elter_id, kind_id),"
                + "FOREIGN KEY(elter_id) REFERENCES nutzer(id),"
                + "FOREIGN KEY(kind_id) REFERENCES nutzer(id)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlNutzer);
            stmt.execute(sqlAufgaben);
            stmt.execute(sqlElternKind);

        } catch (SQLException e) {
            System.out.println("Fehler beim Initialisieren: " + e.getMessage());
        }
    }

    // Speichert einen neuen Nutzer
    public synchronized void nutzerSpeichern(String vorname, String nachname, String benutzername, String passwort, String rolle) {
        String sql = "INSERT INTO nutzer(vorname, nachname, benutzername, passwort, rolle) VALUES(?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vorname);
            pstmt.setString(2, nachname);
            pstmt.setString(3, benutzername);
            pstmt.setString(4, PasswortUtils.hashPasswort(passwort));
            pstmt.setString(5, rolle);

            pstmt.executeUpdate();
            System.out.println("Nutzer gespeichert: " + benutzername);

        } catch (SQLException e) {
            System.out.println("Fehler beim Speichern: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Speichert eine Aufgabe
    public synchronized void aufgabeSpeichern(String titel, String beschreibung, String frist, int erstellerId) {
        String sql = "INSERT INTO aufgaben(titel, beschreibung, frist, ersteller_id) VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titel);
            pstmt.setString(2, beschreibung);
            pstmt.setString(3, frist);
            pstmt.setInt(4, erstellerId);

            pstmt.executeUpdate();
            System.out.println("Aufgabe gespeichert: " + titel);

        } catch (SQLException e) {
            System.out.println("Fehler beim Speichern der Aufgabe: " + e.getMessage());
        }
    }

    // Aktualisiert das Passwort
    public synchronized void updatePasswort(String benutzername, String neuesPasswort) {
        String sql = "UPDATE nutzer SET passwort = ? WHERE benutzername = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, PasswortUtils.hashPasswort(neuesPasswort));
            pstmt.setString(2, benutzername);

            pstmt.executeUpdate();
            System.out.println("Passwort aktualisiert für: " + benutzername);

        } catch (SQLException e) {
            System.out.println("Update Fehler: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Löscht einen Nutzer
    public synchronized void loescheNutzer(int id) {
        String sql = "DELETE FROM nutzer WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Nutzer gelöscht ID: " + id);

        } catch (SQLException e) {
            System.out.println("Lösch-Fehler: " + e.getMessage());
        }
    }

    // Erstellt Testdaten wenn DB leer ist
    public synchronized void erstelleTestDaten() {
        String checkSql = "SELECT count(*) FROM nutzer";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {

            if (rs.next() && rs.getInt(1) > 0) {
                return; // Daten schon da
            }

            System.out.println("Erstelle Testdaten...");
            nutzerSpeichern("Hans", "Müller", "h.mueller", "1234", "LEHRER");
            nutzerSpeichern("Max", "Mustermann", "max", "1234", "SCHUELER");
            nutzerSpeichern("Lisa", "Schlau", "lisa", "1234", "SCHUELER");
            nutzerSpeichern("Sabine", "Mustermann", "s.muster", "1234", "ELTER");

            aufgabeSpeichern("Mathe S.42", "Nr 1-5 lösen", "2026-02-01", 1);

        } catch (SQLException e) {
            System.out.println("Testdaten Fehler: " + e.getMessage());
        }
    }

    // Login Logik
    public Nutzer login(String benutzername, String passwortEingabe) {
        String sql = "SELECT * FROM nutzer WHERE benutzername = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, benutzername);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String gespeicherterHash = rs.getString("passwort");
                String eingegebenerHash = PasswortUtils.hashPasswort(passwortEingabe);

                if (gespeicherterHash.equals(eingegebenerHash)) {
                    int id = rs.getInt("id");
                    String vorname = rs.getString("vorname");
                    String nachname = rs.getString("nachname");
                    String rolleStr = rs.getString("rolle");
                    Role rolle = Role.valueOf(rolleStr);

                    Nutzer nutzer = null;
                    switch (rolle) {
                        case LEHRER:
                            nutzer = new Lehrer(id, vorname, nachname, benutzername, gespeicherterHash);
                            break;
                        case SCHUELER:
                            nutzer = new Schueler(id, vorname, nachname, benutzername, gespeicherterHash);
                            break;
                        case ELTERNTEIL:
                            nutzer = new Elter(id, vorname, nachname, benutzername, gespeicherterHash);
                            break;
                        default:
                            nutzer = new Schueler(id, vorname, nachname, benutzername, gespeicherterHash);
                    }
                    return nutzer;
                }
            }

        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println("Login Fehler: " + e.getMessage());
        }
        return null;
    }
}