import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatenbankManager {
    private static final String URL = "jdbc:sqlite:Mydatabase.db";

    public DatenbankManager() {
        initialisiereTabellen();
    }



    // 3)



    // Connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void initialisiereTabellen() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            //Nutzer Tabelle
            String sqlNutzer = "CREATE TABLE IF NOT EXISTS nutzer (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "vorname TEXT NOT NULL, " +
                    "nachname TEXT NOT NULL, " +
                    "benutzername TEXT UNIQUE NOT NULL, " +
                    "passwort TEXT NOT NULL, " +
                    "rolle TEXT NOT NULL" +
                    ");";
            stmt.execute(sqlNutzer);

            // Aufgabe Tabelle
            String sqlAufgaben = "CREATE TABLE IF NOT EXISTS aufgaben (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "titel TEXT NOT NULL, " +
                    "beschreibung TEXT, " +
                    "abgabefrist TEXT, " +
                    "ersteller_id INTEGER, " +
                    "FOREIGN KEY(ersteller_id) REFERENCES nutzer(id)" +
                    ");";
            stmt.execute(sqlAufgaben);

            // Eltern - Kinder Tabelle
            String sqlRel = "CREATE TABLE IF NOT EXISTS eltern_kind_zuordnung (" +
                    "elter_id INTEGER, " +
                    "kind_id INTEGER, " +
                    "PRIMARY KEY (elter_id, kind_id), " +
                    "FOREIGN KEY(elter_id) REFERENCES nutzer(id), " +
                    "FOREIGN KEY(kind_id) REFERENCES nutzer(id)" +
                    ");";
            stmt.execute(sqlRel);

            System.out.println("Datenbank-Tabellen erfolgreich initialisiert.");

        } catch (SQLException e) {
            System.out.println("Fehler bei der Datenbank-Initialisierung: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // INSERT

    // Erste anwendung ohne Passwort-Hashing
    /*public void nutzerSpeichern(Nutzer nutzer) {
        String sql = "INSERT INTO nutzer(vorname, nachname, benutzername, passwort, rolle) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nutzer.getVorname());
            pstmt.setString(2, nutzer.getNachname());
            pstmt.setString(3, nutzer.getBenutzername());
            pstmt.setString(4, nutzer.getPasswort());
            pstmt.setString(5, nutzer.getRole().toString());

            pstmt.executeUpdate();
            System.out.println("Gespeichert: " + nutzer.getBenutzername());

        } catch (SQLException e) {
            System.out.println("Fehler beim Speichern: " + e.getMessage());
        }
    }*/


    // READ


    public void zeigeAlleNutzer() {
        String sql = "SELECT id, vorname, nachname, rolle FROM nutzer";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Liste aller Nutzer in DB ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                String vorname = rs.getString("vorname");
                String nachname = rs.getString("nachname");
                String rolle = rs.getString("rolle");

                System.out.println("ID: " + id + " | " + vorname + " " + nachname + " (" + rolle + ")");
            }
            System.out.println("--------------------------------\n");

        } catch (SQLException e) {
            System.out.println("Fehler beim Lesen: " + e.getMessage());
        }
    }

    // UPDATE


    public void updatePasswort(int nutzerId, String neuesPasswort) {
        String sql = "UPDATE nutzer SET passwort = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String gehashtesPasswort = PasswortUtils.hashPasswort(neuesPasswort);

            pstmt.setString(1, neuesPasswort);
            pstmt.setInt(2, nutzerId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Passwort für ID " + nutzerId + " aktualisiert.");
            } else {
                System.out.println("Nutzer mit ID " + nutzerId + " nicht gefunden.");
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Update: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    // DELETE


    public void loescheNutzer(int nutzerId) {
        String sql = "DELETE FROM nutzer WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, nutzerId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Nutzer ID " + nutzerId + " wurde gelöscht.");
            } else {
                System.out.println("Löschen fehlgeschlagen: ID nicht gefunden.");
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen: " + e.getMessage());
        }
    }


    // Dummy Daten


    public void erstelleTestDaten() {
        System.out.println("--- Erstelle 5 Dummy-Nutzer ---"); // Test Namen aus dem Internet
        nutzerSpeichern(new Lehrer("Hans", "Müller", "h.mueller", "1234"));
        nutzerSpeichern(new Lehrer("Gaby", "Schmidt", "g.schmidt", "abcd"));
        nutzerSpeichern(new Schueler("Max", "Mustermann", "max.m", "pass"));
        nutzerSpeichern(new Schueler("Lisa", "Lustig", "lisa.l", "pass"));
        nutzerSpeichern(new Elter("Petra", "Mustermann", "mama.m", "secure"));
    }




    // 4)


    // Zweiter Insert mit Passwort-Hashing
    public void nutzerSpeichern(Nutzer nutzer) {
        String sql = "INSERT INTO nutzer(vorname, nachname, benutzername, passwort, rolle) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nutzer.getVorname());
            pstmt.setString(2, nutzer.getNachname());
            pstmt.setString(3, nutzer.getBenutzername());

            // Haschen vor dem Speichern
            String gehashtesPasswort = PasswortUtils.hashPasswort(nutzer.getPasswort());

            pstmt.setString(4, gehashtesPasswort);
            pstmt.setString(5, nutzer.getRole().toString());

            pstmt.executeUpdate();
            System.out.println("Registriert: " + nutzer.getBenutzername());

        } catch (SQLException e) {
            System.out.println("Fehler beim Speichern: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Login
    public Nutzer login(String benutzername, String eingabePasswort) {
        String sql = "SELECT * FROM nutzer WHERE benutzername = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, benutzername);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Datenbank Hash holen
                String dbHash = rs.getString("passwort");
                // Eingabe haschen
                String eingabeHash = PasswortUtils.hashPasswort(eingabePasswort);
                // Vergleichen
                if (dbHash.equals(eingabeHash)) {
                    System.out.println("Login erfolgreich!");

                    return new Nutzer(
                            rs.getString("vorname"),
                            rs.getString("nachname"),
                            rs.getString("benutzername"),
                            "***", // Passwort nicht gezeigt
                            Role.valueOf(rs.getString("rolle"))
                    );
                } else {
                    System.out.println("Falsches Passwort! (Hash Vergleich fehlgeschlagen)");
                }
            } else {
                System.out.println("Benutzer nicht gefunden.");
            }

        } catch (SQLException e) {
            System.out.println("Login Fehler: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void aufgabeSpeichern(String titel, String beschreibung, String frist, int erstellerId) {
        String sql = "INSERT INTO aufgaben(titel, beschreibung, abgabefrist, ersteller_id) VALUES(?, ?, ?, ?)";

        try (Connection conn = getConnection();
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


    public void zeigeAlleAufgaben() {
        String sql = "SELECT a.id, a.titel, a.beschreibung, a.abgabefrist, n.vorname, n.nachname " +
                     "FROM aufgaben a " +
                     "JOIN nutzer n ON a.ersteller_id = n.id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Liste aller Aufgaben in DB ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                String titel = rs.getString("titel");
                String beschreibung = rs.getString("beschreibung");
                String abgabefrist = rs.getString("abgabefrist");
                String erstellerName = rs.getString("vorname") + " " + rs.getString("nachname");

                System.out.println("ID: " + id + " | Titel: " + titel + " | Beschreibung: " + beschreibung +
                                   " | Abgabefrist: " + abgabefrist + " | Erstellt von: " + erstellerName);
            }
            System.out.println("--------------------------------\n");

        } catch (SQLException e) {
            System.out.println("Fehler beim Lesen der Aufgaben: " + e.getMessage());
        }
    }
}