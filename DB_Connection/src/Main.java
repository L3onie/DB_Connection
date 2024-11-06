import javax.swing.*;
import java.sql.*;

public class Main {

    private static Connection connect = null; // Datenbankverbindung
    private static Statement statement = null; // Statement-Objekt für SQL-Abfragen
    private static PreparedStatement preparedStatement = null; // PreparedStatement für parametrische SQL-Abfragen (nicht genutzt)
    private static ResultSet resultSet = null; // Speichert das Ergebnis von SQL-Abfragen

    public static void main(String[] args) throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Laden des MySQL-Treibers
            connect = DriverManager.getConnection("jdbc:mysql://localhost/java","root",""); // Aufbau der DB-Verbindung
            statement = connect.createStatement(); // Initialisieren eines Statement-Objekts
            resultSet = statement.executeQuery("select * from emp"); // Ausführen einer Abfrage und Speichern des Ergebnisses
            writeResultSet(resultSet); // Ausgabe der emp-Tabelle
            resultSet = statement.executeQuery("select * from dept"); // Ausführen einer weiteren Abfrage auf dept-Tabelle
        } catch (Exception e) {
            throw e; // Fehler weiterleiten
        } finally {
            close(); // Schließen aller Ressourcen
        }
    }

    private static void writeMetaData(ResultSet resultSet) throws SQLException {
        System.out.println("The columns in the table are: "); // Ausgabe der Tabellenspalten

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1)); // Tabellenname anzeigen
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i)); // Namen der Spalten ausgeben
        }
    }

    private static void writeResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) { // Schleife durch jede Zeile der Ergebnismenge
            // Ausgabe der Spaltenwerte der aktuellen Zeile
            String empno = resultSet.getString("empno"); // Mitarbeiternummer
            String ename = resultSet.getString("ename"); // Mitarbeitername
            String job = resultSet.getString("job"); // Jobbezeichnung
            String mgr = resultSet.getString("mgr"); // Manager-ID
            String sal = resultSet.getString("sal"); // Gehalt
            String deptno = resultSet.getString("deptno"); // Abteilungsnummer
            String hiredate = resultSet.getString("hiredate"); // Einstellungsdatum
            System.out.println("EmpNo: " + empno);
            System.out.println("EmpName: " + ename);
            System.out.println("Job: " + job);
            System.out.println("Manager: " + mgr);
            System.out.println("Salary: " + sal);
            System.out.println("Department: " + deptno);
            System.out.println("Hiredate: " + hiredate);
            System.out.println("------------------"); // Trennlinie für bessere Lesbarkeit
        }
    }

    private static void close() {
        try {
            if (resultSet != null) {
                resultSet.close(); // Schließen der Ergebnismenge
            }

            if (statement != null) {
                statement.close(); // Schließen des Statements
            }

            if (connect != null) {
                connect.close(); // Schließen der DB-Verbindung
            }
        } catch (Exception e) {
            // Fehler bei Schließvorgang ignorieren
        }
    }
}
